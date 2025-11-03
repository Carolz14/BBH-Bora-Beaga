package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbh.domain.Local;


public class LocalDAO {

    private static LocalDAO instancia;

    private LocalDAO() {}

    public static LocalDAO getInstance() {
        if (instancia == null) {
            instancia = new LocalDAO();
        }
        return instancia;
    }

   public List<Local> buscarPorNome(String nomeBuscado) throws SQLException {
    List<Local> locais = new ArrayList<>();
    String sql = "SELECT id, nome, endereco FROM locais WHERE nome LIKE ?";

    try (Connection conn = ConexaoBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, "%" + nomeBuscado + "%");

        try (ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Local local = new Local();
                local.setId(resultSet.getLong("id"));
                local.setNome(resultSet.getString("nome"));
                local.setEndereco(resultSet.getString("endereco"));
                locais.add(local);
            }
        }
    }
    return locais;
    }
}

