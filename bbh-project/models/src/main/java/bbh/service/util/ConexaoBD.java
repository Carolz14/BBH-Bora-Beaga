package bbh.service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://localhost:3306/bbh?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // garante que o driver está carregado
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado!", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}