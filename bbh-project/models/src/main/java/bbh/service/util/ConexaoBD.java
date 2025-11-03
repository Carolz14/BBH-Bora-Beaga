package bbh.service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "usuarios";  // mantém o nome do DB já existente
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        // certifica-se de carregar o driver (ajuste se necessário)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado!", e);
        }

        String fullUrl = URL + DB_NAME + "?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(fullUrl, USER, PASSWORD);
    }
}
