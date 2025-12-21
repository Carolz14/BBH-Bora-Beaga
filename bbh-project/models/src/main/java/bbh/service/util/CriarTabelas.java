package bbh.service.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bbh.common.PersistenciaException;
import bbh.domain.Tag;
import bbh.service.TagService;

public class CriarTabelas {

    public static void verificarCriarBanco() {
        try (Connection conn = ConexaoBD.getServerConnection(); 
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS bbh");
            System.out.println("Banco de dados 'bbh' verificado/criado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao verificar/criar banco de dados: " + e.getMessage());
        }
    }

    public static void criarTabelaUsuarios() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                senha VARCHAR(255) NOT NULL,
                naturalidade VARCHAR(100),
                endereco VARCHAR(255),
                contato BIGINT,
                habilitado BOOLEAN DEFAULT TRUE,
                usuario_tipo VARCHAR(50) NOT NULL,
                cnpj VARCHAR(18) DEFAULT NULL,
                descricao TEXT DEFAULT NULL,
                imagem_url VARCHAR(255) DEFAULT NULL
            );
        """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'usuarios' verificada/criada com sucesso.");
        }
    }

    public static void inserirUsuariosPadrao() throws SQLException {
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM usuarios")) {
                rs.next();
                if (rs.getInt("total") == 0) {
                    String insert = """
                        INSERT INTO usuarios (nome, email, senha, naturalidade, endereco, contato, habilitado, usuario_tipo)
                        VALUES
                        ('Administrador do Sistema', 'admin@email.com', SHA2('123', 256), 'Brasil', 'Rua Principal, 100', 31999999999, TRUE, 'ADMINISTRADOR'),
                        ('Carol', 'carol@email.com', SHA2('1414', 256), 'Brasil', 'Rua Principal, 100', 31999999999, TRUE, 'TURISTA'),
                        ('Artur', 'artur@email.com', SHA2('2525', 256), 'Brasil', 'Rua Principal, 100', 31999999999, TRUE, 'GUIA'),
                        ('Cozinha Legal', 'george@email.com', SHA2('6363', 256), 'Brasil', 'Rua Principal, 100', 31999999999, TRUE, 'ESTABELECIMENTO');
                    """;
                    stmt.executeUpdate(insert);
                    System.out.println("Usuários padrão inseridos com sucesso!");
                }
            }
        }
    }

    public static void criarTabelaRoteiros() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS roteiros (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                descricao TEXT,
                paradas_texto TEXT DEFAULT NULL,
                usuario_id BIGINT NOT NULL,
                habilitado BOOLEAN DEFAULT TRUE,
                FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
            );
        """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'roteiros' verificada/criada com sucesso.");
        }
    }

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
            System.out.println("Tabela 'tag' criada.");
        }
    }

    public static void criarTabelaCorrespondencia() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS tag_correspondencia (
                id_usuario BIGINT,
                id_tag BIGINT,
                PRIMARY KEY (id_usuario, id_tag),
                FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
                FOREIGN KEY (id_tag) REFERENCES tag(id) ON DELETE CASCADE
            ) ENGINE=InnoDB;
        """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'tag_correspondencia' criada.");
        }
    }

     public static void criarTabelaAvaliacao() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS avaliacao (
                id_avaliacao BIGINT AUTO_INCREMENT PRIMARY KEY,
                id_usuario BIGINT NOT NULL,
                id_estabelecimento BIGINT NOT NULL,
                nota_avaliacao INT CHECK (nota_avaliacao BETWEEN 1 AND 5),
                comentario TEXT,
                categoria VARCHAR(50) NOT NULL,
                data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
                FOREIGN KEY (id_estabelecimento) REFERENCES usuarios(id) ON DELETE CASCADE
            ) ENGINE=InnoDB;
        """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'avaliacao' criada.");
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
        try (Connection conn = ConexaoBD.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela midia_avaliacao criada (ou já existia)");
        }
    }

    public static void criarTabelaComentariosRoteiros() throws SQLException {

        String sqlComentariosRoteiros = """
                CREATE TABLE IF NOT EXISTS comentarios_roteiros (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    roteiro_id BIGINT NOT NULL,
                    usuario_id BIGINT NOT NULL,
                    texto TEXT NOT NULL,
                    imagem_url VARCHAR(255),
                    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (roteiro_id) REFERENCES roteiros(id),
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
                );
                """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sqlComentariosRoteiros);
            System.out.println("Tabela 'comentarios_roteiros' verificada/criada com sucesso.");
        }

    }

    public static void criarTabelaAvaliacaoRoteiros() throws SQLException {

        String sqlAvaliacaoRoteiros = """
                CREATE TABLE IF NOT EXISTS avaliacao_roteiros (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    roteiro_id BIGINT NOT NULL,
                    usuario_id BIGINT NOT NULL,
                    nota BIGINT NOT NULL,
                    FOREIGN KEY (roteiro_id) REFERENCES roteiros(id),
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                    UNIQUE(roteiro_id, usuario_id )
                );
                """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sqlAvaliacaoRoteiros);
            System.out.println("Tabela 'avaliacao_roteiros' verificada/criada com sucesso.");
        }

    }

   

 

    public static void inserirTagsPadroes() throws PersistenciaException {
        String[][] stringTagsPadroes = {
            {"Bar", "bar"}, {"Restaurante", "restaurante"}, {"Lazer", "lazer"}, {"Cultural", "cultural"},
            {"Cafeteria", "cafeteria"}, {"Comida mineira", "comida-mineira"}, {"Parque", "parque"},
            {"Família", "familia"}, {"Arte", "arte"}, {"Hotel", "hotel"}, {"Shopping", "shopping"},
            {"Música ao vivo", "musica-ao-vivo"}, {"Noturno", "noturno"}, {"Pagode", "pagode"},
            {"Teatro", "teatro"}, {"Zoológico", "zoologico"}, {"Esportes", "esportes"},
            {"Igrejas", "igrejas"}, {"Natureza", "natureza"}, {"Cultura mineira", "cultura-mineira"},
            {"Monumentos", "monumentos"}, {"Museus", "museus"}
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
            System.out.println("Tabela 'promocao' criada.");
        }
    }

    public static void criarTabelaPromocaoEstabelecimento() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS promocao_estabelecimento (
                id_usuario BIGINT,
                id_promocao BIGINT,
                PRIMARY KEY (id_usuario, id_promocao),
                FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
                FOREIGN KEY (id_promocao) REFERENCES promocao(id) ON DELETE CASCADE
            );
        """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'promocao_estabelecimento' criada.");
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
                FOREIGN KEY (estabelecimento_id) REFERENCES usuarios(id) ON DELETE CASCADE
            );
        """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'eventos' criada.");
        }
    }

    public static void criarTabelaSuporte() throws SQLException {
        String sqlTickets = """
            CREATE TABLE IF NOT EXISTS tickets (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                usuario_id BIGINT NOT NULL,
                usuario_email VARCHAR(255) NOT NULL,
                assunto VARCHAR(255) NOT NULL,
                mensagem TEXT NOT NULL,
                status VARCHAR(30) NOT NULL DEFAULT 'ABERTO',
                criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                ativo BOOLEAN DEFAULT TRUE,
                FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
            ) ENGINE=InnoDB;
        """;
        String sqlMensagens = """
            CREATE TABLE IF NOT EXISTS ticket_mensagens (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                ticket_id BIGINT NOT NULL,
                autor_id BIGINT NOT NULL,
                autor_tipo VARCHAR(20) NOT NULL,
                mensagem TEXT NOT NULL,
                criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE,
                FOREIGN KEY (autor_id) REFERENCES usuarios(id) ON DELETE CASCADE
            ) ENGINE=InnoDB;
        """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sqlTickets);
            stmt.executeUpdate(sqlMensagens);
            System.out.println("Tabelas de suporte criadas.");
        }
    }

    public static void criarTabelaPontoTuristico() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS ponto_turistico (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(150) NOT NULL,
                endereco VARCHAR(255) NOT NULL,
                imagem_url VARCHAR(255),
                descricao TEXT,
                tag VARCHAR(50),
                ativo BOOLEAN DEFAULT TRUE
            );
        """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'ponto_turistico' criada.");
        }
    }

    public static void criarTabelaListaInteresse() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS lista_interesse (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                id_turista BIGINT NOT NULL,
                id_item BIGINT NOT NULL,
                tipo_item VARCHAR(50) NOT NULL,
                data_salvo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (id_turista) REFERENCES usuarios(id) ON DELETE CASCADE,
                UNIQUE KEY unique_interesse (id_turista, id_item, tipo_item)
            );
        """;
        try (Connection con = ConexaoBD.getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'lista_interesse' criada.");
        }
    }

    public static void criarTodasAsTabelas() throws PersistenciaException, SQLException {
        verificarCriarBanco();
        criarTabelaUsuarios();
        inserirUsuariosPadrao();
        criarTabelaTag();
        criarTabelaCorrespondencia();
        inserirTagsPadroes(); 
        criarTabelaRoteiros(); 
        criarTabelaPromocao();
        criarTabelaPromocaoEstabelecimento();
        criarTabelaEventos(); 
        criarTabelaAvaliacao();
        criarTabelaAvaliacaoMidia();
        criarTabelaSuporte();
        criarTabelaPontoTuristico();
        criarTabelaListaInteresse();
        criarTabelaComentariosRoteiros();
        criarTabelaAvaliacaoRoteiros();
    }
}