package bbh.service.util;

import bbh.common.PersistenciaException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import bbh.service.TagService;
import java.util.ArrayList;
import bbh.domain.Tag;
import java.util.List;


public class CriarTabelas {

    public static void criarTabelaTag() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tag("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "nome VARCHAR(50) NOT NULL UNIQUE,"
                + "slug VARCHAR(50) NOT NULL UNIQUE,"
                + "contador INT DEFAULT 0"
                + ");";
        Connection con = ConexaoBD.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.printf("Tabela tag criada (ou já existia previamente).\n");
    }

    public static void criarTabelaUsuario() throws SQLException {
        /// apenas pro teste do meu csu
        String sql = "CREATE TABLE IF NOT EXISTS usuario("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "nome VARCHAR(100) NOT NULL,"
                + "email VARCHAR(100) UNIQUE NOT NULL"
                + ");";
        Connection con = ConexaoBD.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.printf("Tabela usuario criada (ou já existia previamente).\n");
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
                + ");";
        Connection con = ConexaoBD.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.printf("Tabela correspondência já criada (ou já existia previamente).\n");
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
            {"Música ao vivo", "musicao-ao-vivo"},
            {"Noturno", "noturno"},
            {"Pagode", "pagode"},
            {"Teatro", "teatro"},
            {"Zoológico", "zoologico"},
            {"Esportes", "esportes"},
            {"Igrejas", "igrejas"},
            {"Natureza", "natureza"},
            {"Cultura mineira", "cultura-mineira"}
        };
        List<Tag> listaTags = new ArrayList(stringTagsPadroes.length);
        for(String[] tagDados : stringTagsPadroes){
            String nome = tagDados[0];
            String slug = tagDados[1];
            listaTags.add(new Tag(nome,slug,null));
        }
        TagService tagService = new TagService();
        tagService.inserirTagsEmLote(listaTags);
        System.out.println("Tags padrão inseridas com sucesso!");
    }

    public static void criarTodasAsTabelas() throws PersistenciaException, SQLException {
        criarTabelaTag();
        criarTabelaUsuario();
        criarTabelaCorrespondencia();
        inserirTagsPadroes();
    }
}
