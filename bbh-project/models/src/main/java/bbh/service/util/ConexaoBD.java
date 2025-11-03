package bbh.service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "usuarios";                  
    private static final String USER = "root";
    private static final String PASSWORD = "";


    public static Connection getConnection() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
        }

        Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
        InicializarTabela(conn);

        return conn;
    }
    private static void InicializarTabela(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "nome VARCHAR(100) NOT NULL, "
                + "email VARCHAR(100) UNIQUE NOT NULL, "
                + "senha VARCHAR(255) NOT NULL, "
                + "naturalidade VARCHAR(100), "
                + "endereco VARCHAR(255), "
                + "contato BIGINT, "
                + "habilitado BOOLEAN DEFAULT TRUE, "
                + "pessoa_tipo VARCHAR(50) NOT NULL, "
                + "cnpj VARCHAR(18) DEFAULT NULL"
                + ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
}
