package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.Local;
import bbh.service.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por buscar apenas os locais (usuários do tipo ESTABELECIMENTO)
 * no banco de dados.
 */
public class LocalDAO {

    private static LocalDAO instancia;

    private LocalDAO() {}

    public static LocalDAO getInstance() {
        if (instancia == null) {
            instancia = new LocalDAO();
        }
        return instancia;
    }

    /**
     * Busca locais (estabelecimentos) cujo nome contenha o termo informado.
     * Ignora usuários de outros tipos (ADMIN, TURISTA, etc).
     */
    public List<Local> buscarPorNome(String nomeBuscado) throws PersistenciaException {
        List<Local> locais = new ArrayList<>();

        String sql = """
            SELECT id, nome, endereco, pessoa_tipo, descricao, imagem_url
            FROM usuarios
            WHERE LOWER(nome) LIKE LOWER(?)
              AND pessoa_tipo = 'ESTABELECIMENTO'
              AND ativo = TRUE
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nomeBuscado + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Local local = new Local();
                    local.setId(rs.getLong("id"));
                    local.setNome(rs.getString("nome"));
                    local.setEndereco(rs.getString("endereco"));
                    local.setCategoria("Estabelecimento");
                    local.setDescricao(rs.getString("descricao"));
                    local.setImagemUrl(rs.getString("imagem_url"));
                    locais.add(local);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar estabelecimentos: " + e.getMessage(), e);
        }

        return locais;
    }
}
