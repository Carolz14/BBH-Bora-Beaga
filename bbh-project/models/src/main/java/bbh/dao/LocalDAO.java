package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbh.common.PersistenciaException;
import bbh.domain.Local;
import bbh.domain.Roteiro;
import bbh.service.util.ConexaoBD;

/**
 * Classe responsável por buscar apenas os locais (usuários do tipo
 * ESTABELECIMENTO)
 * no banco de dados.
 */
public class LocalDAO {

    private static LocalDAO instancia;

    private LocalDAO() {
    }

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
                    SELECT id, nome, endereco, usuario_tipo, descricao
                    FROM usuarios
                    WHERE LOWER(nome) LIKE LOWER(?)
                      AND usuario_tipo = 'ESTABELECIMENTO'

                    LIMIT 10
                """;

        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nomeBuscado + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Local local = new Local();
                    local.setId(rs.getLong("id"));
                    local.setNome(rs.getString("nome"));
                    local.setCategoria("Estabelecimento");
                    local.setDescricao(rs.getString("descricao"));
                    locais.add(local);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar estabelecimentos: " + e.getMessage(), e);
        }

        return locais;
    }

    public Local buscarPorId(Long id) throws PersistenciaException {
        Local local = null;

        String sql = "SELECT id, nome, endereco, usuario_tipo, descricao "
                + "FROM usuarios WHERE id = ? AND usuario_tipo = 'ESTABELECIMENTO'";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    local = new Local();
                    local.setId(rs.getLong("id"));
                    local.setNome(rs.getString("nome"));
                    local.setEndereco(rs.getString("endereco"));
                    local.setCategoria("Estabelecimento");
                    local.setDescricao(rs.getString("descricao"));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar pelo id: " + e.getMessage(), e);
        }

        return local;
    }
 
}
