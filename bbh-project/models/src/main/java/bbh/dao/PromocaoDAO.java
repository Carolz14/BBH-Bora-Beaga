package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.Promocao;
import bbh.service.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PromocaoDAO implements GenericDeleteDAO<Promocao, Long> {

    private static PromocaoDAO instance;

    public static PromocaoDAO getInstance() {
        if (instance == null) {
            instance = new PromocaoDAO();
        }
        return instance;
    }

    @Override
    public void inserir(Promocao promocao) throws PersistenciaException {
        String sql = "INSERT INTO promocao (nome, desconto, descricao, data) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, promocao.getNome());
            ps.setLong(2, promocao.getDesconto());
            ps.setString(3, promocao.getDescricao());
            ps.setDate(4, java.sql.Date.valueOf(promocao.getData()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    promocao.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir promoção: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws PersistenciaException {
        String sql = "DELETE FROM promocao WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int linhas = ps.executeUpdate();

            if (linhas == 0) {
                throw new PersistenciaException("Promoção não encontrada para deletar");
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao deletar promoção: " + e.getMessage(), e);
        }
    }

    @Override
    public Promocao pesquisar(Long id) throws PersistenciaException {
        Promocao promocao = null;

        String sql = "SELECT id, nome, desconto, descricao, data FROM promocao WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    promocao = new Promocao(
                            rs.getString("nome"),
                            rs.getLong("desconto"),
                            rs.getString("descricao"),
                            rs.getDate("data").toLocalDate()
                    );
                    promocao.setId(rs.getLong("id"));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar promoção: " + e.getMessage(), e);
        }

        return promocao;
    }
}
