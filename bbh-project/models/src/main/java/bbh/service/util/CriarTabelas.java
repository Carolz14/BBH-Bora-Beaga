package bbh.service.util;

import bbh.common.PersistenciaException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import bbh.service.TagService;
import bbh.domain.Tag;
import java.util.ArrayList;
import java.util.List;

public class CriarTabelas {

    public static void criarTabelaTag() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS tag (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(50) NOT NULL UNIQUE,
                slug VARCHAR(50) NOT NULL UNIQUE,
                contador INT DEFAULT 0
            ) ENGINE=InnoDB;
        """;

        try (Connection con = ConexaoBD.getConnection(); Statement statement = con.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabela 'tag' criada (ou já existia).");
        }
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
            ) ENGINE=InnoDB;
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
                    SET MESSAGE_TEXT = 'Somente usuários do tipo ESTABELECIMENTO podem ter tags.';
                END IF;
            END;
        """;

        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sqlTabela);
            System.out.println("Tabela 'tag_correspondencia' criada (ou já existia).");

            stmt.executeUpdate(sqlDropTrigger);
            stmt.executeUpdate(sqlTrigger);
            System.out.println("Trigger 'before_insert_tag_corr' criada com sucesso.");

        }
    }

    public static void criarTabelaAvaliacaoMidia() throws SQLException {
    String sql = """
                CREATE TABLE IF NOT EXISTS midia_avaliacao(
                    id_midia BIGINT AUTO_INCREMENT PRIMARY KEY,
                    id_avaliacao BIGINT NOT NULL,
                    nome_original VARCHAR(255) NOT NULL,
                    nome_armazenado VARCHAR(255) NOT NULL,
                    caminho VARCHAR(1024) NOT NULL,
                    mime VARCHAR(100),
                    tamanho_bytes BIGINT,
                    data_midia TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    FOREIGN KEY (id_avaliacao) REFERENCES avaliacao(id_avaliacao)
                        ON DELETE CASCADE ON UPDATE CASCADE
                ) ENGINE=InnoDB;
                """;
    try(Connection conn = ConexaoBD.getConnection(); Statement stmt = conn.createStatement()){
        stmt.executeUpdate(sql);
        System.out.println("Tabela midia_avaliacao criada (ou já existia)");
    }
}

    public static void criarTabelaAvaliacao() throws SQLException {
        String sqlTabela = """
            CREATE TABLE IF NOT EXISTS avaliacao (
                id_avaliacao BIGINT AUTO_INCREMENT PRIMARY KEY,
                id_usuario BIGINT NOT NULL,
                id_estabelecimento BIGINT NOT NULL,
                nota_avaliacao INT CHECK (nota_avaliacao BETWEEN 1 AND 5),
                comentario TEXT,
                data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (id_estabelecimento) REFERENCES usuarios(id)
                    ON DELETE CASCADE ON UPDATE CASCADE
            ) ENGINE=InnoDB;
        """;

        String sqlDropTrigger = "DROP TRIGGER IF EXISTS before_insert_avaliacao;";

        String sqlTrigger = """
            CREATE TRIGGER before_insert_avaliacao
            BEFORE INSERT ON avaliacao
            FOR EACH ROW
            BEGIN
                DECLARE tipo_autor VARCHAR(50);
                DECLARE tipo_alvo VARCHAR(50);

                -- Verifica autor (TURISTA)
                SELECT usuario_tipo INTO tipo_autor FROM usuarios WHERE id = NEW.id_usuario;

                IF tipo_autor IS NULL THEN
                    SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Usuário autor não encontrado.';
                END IF;

                IF UPPER(tipo_autor) <> 'TURISTA' THEN
                    SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Somente TURISTAS podem criar avaliações.';
                END IF;

                -- Verifica alvo (ESTABELECIMENTO)
                SELECT usuario_tipo INTO tipo_alvo FROM usuarios WHERE id = NEW.id_estabelecimento;

                IF tipo_alvo IS NULL THEN
                    SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Estabelecimento não encontrado.';
                END IF;

                IF UPPER(tipo_alvo) <> 'ESTABELECIMENTO' THEN
                    SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Somente ESTABELECIMENTOS podem receber avaliações.';
                END IF;
            END;
        """;

        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sqlTabela);
            System.out.println("Tabela 'avaliacao' criada (ou já existia).");

            stmt.executeUpdate(sqlDropTrigger);
            stmt.executeUpdate(sqlTrigger);
            System.out.println("Trigger 'before_insert_avaliacao' criada com sucesso.");
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
            {"Música ao vivo", "musica-ao-vivo"},
            {"Noturno", "noturno"},
            {"Pagode", "pagode"},
            {"Teatro", "teatro"},
            {"Zoológico", "zoologico"},
            {"Esportes", "esportes"},
            {"Igrejas", "igrejas"},
            {"Natureza", "natureza"},
            {"Cultura mineira", "cultura-mineira"},
            {"Monumentos", "monumentos"},
            {"Museus", "museus"}
        };

        List<Tag> listaTags = new ArrayList<>(stringTagsPadroes.length);

        for (String[] t : stringTagsPadroes) {
            listaTags.add(new Tag(t[0], t[1], null));
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
    private static void criarTabelaPontoTuristico() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS ponto_turistico (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(150) NOT NULL,
                endereco VARCHAR(255) NOT NULL,
                imagem_url VARCHAR(255),
                descricao TEXT,
                ativo BOOLEAN DEFAULT TRUE
            );
        """;

        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'ponto_turistico' criada (ou já existia).");
        }
    }


    public static void criarTodasAsTabelas() throws PersistenciaException, SQLException {

        criarTabelaTag();
        criarTabelaCorrespondencia();
        inserirTagsPadroes();
        criarTabelaPromocao();
        criarTabelaPromocaoEstabelecimento();
        criarTabelaEventos();
        criarTabelaAvaliacao();
        criarTabelaAvaliacaoMidia();
        criarTabelaPontoTuristico();
    }
}
