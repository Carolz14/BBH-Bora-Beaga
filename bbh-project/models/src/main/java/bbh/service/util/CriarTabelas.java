package bbh.service.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CriarTabelas {
    public static void criarTabelaTag() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tag("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "nome VARCHAR(50) NOT NULL,"
                + "slug VARCHAR(50) NOT NULL,"
                + "contador INT DEFAULT 0"
                + ");";
        Connection con = ConexaoBD.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.printf("Tabela tag criada (ou já existia previamente).\n");
    }
    public static void criarTabelaCorrespondencia() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tag_correspondencia("
            + "id_usuario BIGINT,"
            + "id_tag BIGINT,"
            + "PRIMARY KEY (id_usuario, id_tag),"
            + "FOREIGN KEY (id_usuario) REFERENCES usuario(id) "
            + "ON DELETE CASCADE ON UPDATE CASCADE,"
            + "FOREIGN KEY (id_tag) REFERENCES tag(id) "
            + "ON DELETE CASCADE ON UPDATE CASCADE"
            +");";
        Connection con = ConexaoBD.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.printf("Tabela correspondência já criada (ou já existia previamente).\n");
    }
    public static void criarTodasAsTabelas() throws SQLException{
        criarTabelaTag();
        criarTabelaCorrespondencia();
    }
}
