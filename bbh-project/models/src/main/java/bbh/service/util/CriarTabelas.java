package bbh.service.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CriarTabelas {
    public static void criarTabelaTag() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tag("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nome VARCHAR(50) NOT NULL,"
                + "slug VARCHAR(50) NOT NULL,"
                + "contador INT DEFAULT 0"
                + ");";
        Connection con = ConexaoBD.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.printf("Tabela tag criada (ou já existia previamente).\n");
    }

    public static void criarTabelaEstabelecimento() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS estabelecimentos("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "nome VARCHAR(50) NOT NULL,"
                    + "endereco VAR CHAR(100) NOT NULL,"
                    + "telefone VARCHAR(11) NOT NULL,"
                    + "CNPJ VARCHAR(14) NOT NULL,"
                    + ");";
        Connection con = ConexaoBD.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.printf("Tabela estabelecimentos criada (ou já existia previamente).\n");
    }
}
