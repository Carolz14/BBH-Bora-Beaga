package bbh.service.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {

    public static void inicializar() {
        //Conecta no servidor e cria o DB
        try (Connection conn = ConexaoBD.getServerConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS bbh");
            System.out.println("Banco de dados 'bbh' verificado/criado com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao criar banco de dados: " + e.getMessage());
            return;
        }

       //Conecta no banco e cria a tabela, se não exisitir
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement()) {

            String sqlUsuarios = """
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
                    cnpj VARCHAR(18) DEFAULT NULL
                );
                """;
            stmt.executeUpdate(sqlUsuarios);
            System.out.println("Tabela 'usuarios' verificada/criada com sucesso.");

            //verifica se há registros e poem "usuarios iniciais"
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM usuarios")) {
                rs.next();
                int total = rs.getInt("total");

                if (total == 0) {
                    String insert = """
                        INSERT INTO usuarios (nome, email, senha, naturalidade, endereco, contato, habilitado, usuario_tipo, cnpj)
                        VALUES
                        ('Administrador do Sistema', 'admin@sistema.com', SHA2('admin123', 256), 'Brasil', 'Rua Principal, 100', 31999999999, TRUE, 'ADMINISTRADOR', NULL),
                        ('Max', 'max@email.com', SHA2('3333', 256), 'Brasil', 'Rua Principal, 100', 31999999999, TRUE, 'TURISTA', NULL),
                        ('Oscar', 'oscar@email.com', SHA2('8181', 256), 'Brasil', 'Rua Principal, 100', 31999999999, TRUE, 'GUIA', NULL),
                        ('George', 'george@email.com', SHA2('6363', 256), 'Brasil', 'Rua Principal, 100', 31999999999, TRUE, 'ESTABELECIMENTO', NULL);
                        """;
                    stmt.executeUpdate(insert);
                    System.out.println("Usuários padrão inseridos com sucesso!");
                } else {
                    System.out.println("Tabela 'usuarios' já possui registros.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela ou inserir dados: " + e.getMessage());
        }
    }
}
