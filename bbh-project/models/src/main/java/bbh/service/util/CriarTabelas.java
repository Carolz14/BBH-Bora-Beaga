package bbh.service.util;

import bbh.common.PersistenciaException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import bbh.service.TagService;
import java.util.ArrayList;
import bbh.domain.Tag;
import java.util.List;

public class CriarTabelas {

    public static void criarTabelaTag() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tag("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "nome VARCHAR(50) NOT NULL UNIQUE,"
                + "slug VARCHAR(50) NOT NULL UNIQUE,"
                + "contador INT DEFAULT 0"
                + ");";
        Connection con = ConexaoBD.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.printf("Tabela tag criada (ou já existia previamente).\n");
    }

    public static void criarTabelaCorrespondencia() throws SQLException {
        String sqlTabela = """
            CREATE TABLE IF NOT EXISTS tag_correspondencia (
                id_usuario BIGINT,
                id_tag BIGINT,
                PRIMARY KEY (id_usuario, id_tag),
                FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (id_tag) REFERENCES tag(id)
                    ON DELETE CASCADE ON UPDATE CASCADE
            );
        """;

        String sqlDropTrigger = "DROP TRIGGER IF EXISTS before_insert_tag_corr;";
        String sqlTrigger = """
            CREATE TRIGGER before_insert_tag_corr
            BEFORE INSERT ON tag_correspondencia
            FOR EACH ROW
            BEGIN
                DECLARE tipo_usuario VARCHAR(50);
                SELECT usuario_tipo INTO tipo_usuario
                FROM usuarios
                WHERE id = NEW.id_usuario;

                IF tipo_usuario IS NULL THEN
                    SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Usuário não encontrado.';
                END IF;

                IF tipo_usuario <> 'ESTABELECIMENTO' THEN
                    SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Somente usuários do tipo ESTABELECIMENTO podem ter correspondências de tags.';
                END IF;
            END;
        """;

        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sqlTabela);
            System.out.println("Tabela 'tag_correspondencia' criada (ou já existia).");

            stmt.executeUpdate(sqlDropTrigger);
            stmt.executeUpdate(sqlTrigger);
            System.out.println("Trigger 'before_insert_tag_corr' criada com sucesso.");

        } catch (SQLException e) {
            throw new SQLException("Erro ao criar tabela/trigger: " + e.getMessage(), e);
        }
    }

    public static void inserirTagsPadroes() throws PersistenciaException {
        String[][] stringTagsPadroes = {
            {"Bar", "bar"},
            {"Restaurante", "restaurante"},
            {"Lazer", "lazer"},
            {"Cultural", "cultural"},
            {"Cafeteria", "cafeteria"},
            {"Comida mineira", "comida-mineira"},
            {"Parque", "parque"},
            {"Família", "familia"},
            {"Arte", "arte"},
            {"Hotel", "hotel"},
            {"Shopping", "shopping"},
            {"Música ao vivo", "musicao-ao-vivo"},
            {"Noturno", "noturno"},
            {"Pagode", "pagode"},
            {"Teatro", "teatro"},
            {"Zoológico", "zoologico"},
            {"Esportes", "esportes"},
            {"Igrejas", "igrejas"},
            {"Natureza", "natureza"},
            {"Cultura mineira", "cultura-mineira"},
            {"Monumentos", "monumentos"}
        };
        List<Tag> listaTags = new ArrayList(stringTagsPadroes.length);
        for (String[] tagDados : stringTagsPadroes) {
            String nome = tagDados[0];
            String slug = tagDados[1];
            listaTags.add(new Tag(nome, slug, null));
        }
        TagService tagService = new TagService();
        tagService.inserirTagsEmLote(listaTags);
        System.out.println("Tags padrão inseridas com sucesso!");
    }

    public static void criarTabelaPromocao() throws SQLException {
        String sql = """
        CREATE TABLE IF NOT EXISTS promocao (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            nome VARCHAR(100) NOT NULL,
            descricao TEXT,
            data DATE NOT NULL
        );
        """;

        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'promocao' criada (ou já existia).");
        }
    }

    public static void criarTabelaPromocaoEstabelecimento() throws SQLException {

        String sqlTabela = """
        CREATE TABLE IF NOT EXISTS promocao_estabelecimento (
            id_usuario BIGINT,
            id_promocao BIGINT,
            PRIMARY KEY (id_usuario, id_promocao),
            FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
                ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (id_promocao) REFERENCES promocao(id)
                ON DELETE CASCADE ON UPDATE CASCADE
        );
    """;

        String sqlDropTrigger = "DROP TRIGGER IF EXISTS before_insert_promocao_estab;";

        String sqlTrigger = """
        CREATE TRIGGER before_insert_promocao_estab
        BEFORE INSERT ON promocao_estabelecimento
        FOR EACH ROW
        BEGIN
            DECLARE tipo_usuario VARCHAR(50);

            SELECT usuario_tipo INTO tipo_usuario
            FROM usuarios
            WHERE id = NEW.id_usuario;

            IF tipo_usuario IS NULL THEN
                SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Usuário não encontrado.';
            END IF;

            IF tipo_usuario <> 'ESTABELECIMENTO' THEN
                SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Somente ESTABELECIMENTOS podem ter promoções.';
            END IF;
        END;
    """;

        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sqlTabela);
            System.out.println("Tabela 'promocao_estabelecimento' criada (ou já existia).");

            stmt.executeUpdate(sqlDropTrigger);
            stmt.executeUpdate(sqlTrigger);
            System.out.println("Trigger 'before_insert_promocao_estab' criada com sucesso.");

        } catch (SQLException e) {
            throw new SQLException("Erro ao criar tabela/trigger de promoção: " + e.getMessage(), e);
        }
    }
    
    public static void criarTabelaEventos() throws SQLException {
    String sql = """
        CREATE TABLE IF NOT EXISTS eventos (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            estabelecimento_id BIGINT NOT NULL,
            nome VARCHAR(150) NOT NULL,
            descricao TEXT,
            data_evento DATE NOT NULL,
            horario_evento TIME,
            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            ativo BOOLEAN DEFAULT TRUE,
            FOREIGN KEY (estabelecimento_id) REFERENCES usuarios(id)
                ON DELETE CASCADE ON UPDATE CASCADE
        );
    """;

    try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
        stmt.executeUpdate(sql);
        System.out.println("Tabela 'eventos' criada (ou já existia).");
    }
}


    public static void criarTodasAsTabelas() throws PersistenciaException, SQLException {
        criarTabelaTag();
        criarTabelaCorrespondencia();
        inserirTagsPadroes();
        criarTabelaPromocao();
        criarTabelaPromocaoEstabelecimento();
        criarTabelaEventos();
    }
}
