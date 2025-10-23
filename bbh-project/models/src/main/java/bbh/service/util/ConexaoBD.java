package bbh.service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/"; //provisorio
    private static final String USER = "root"; // provisorio
    private static final String PASSWORD = "";// provisorio
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

