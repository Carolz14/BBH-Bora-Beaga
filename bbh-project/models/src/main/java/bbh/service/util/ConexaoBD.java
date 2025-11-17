package bbh.service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL_BASE = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "bbh";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Conecta ao servidor (sem escolher banco)
    public static Connection getServerConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado!", e);
        }
        return DriverManager.getConnection(URL_BASE + "?useSSL=false&serverTimezone=UTC", USER, PASSWORD);
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado!", e);
        }
        return DriverManager.getConnection(URL_BASE + DB_NAME + "?useSSL=false&serverTimezone=UTC", USER, PASSWORD);
    }
}
