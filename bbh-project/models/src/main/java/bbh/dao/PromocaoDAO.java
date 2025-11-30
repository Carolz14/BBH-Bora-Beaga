package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.Promocao;
import bbh.service.util.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromocaoDAO {

    private static PromocaoDAO instance;

    public static PromocaoDAO getInstance() {
        if (instance == null) {
            instance = new PromocaoDAO();
        }
        return instance;
    }

    private PromocaoDAO() {
    }

    public void inserir(Promocao promocao) throws PersistenciaException {
        String sqlPromocao
                = "INSERT INTO promocao (nome, descricao, data) VALUES (?, ?, ?)";

        String sqlVinculo
                = "INSERT INTO promocao_estabelecimento (id_usuario, id_promocao) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.getConnection()) {
            conn.setAutoCommit(false);

            // insere promoção
            try (PreparedStatement ps = conn.prepareStatement(sqlPromocao, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, promocao.getNome());
                ps.setString(2, promocao.getDescricao());
                ps.setDate(3, java.sql.Date.valueOf(promocao.getData()));
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    promocao.setId(rs.getLong(1));
                }
            }

            // insere vínculo
            try (PreparedStatement ps2 = conn.prepareStatement(sqlVinculo)) {
                ps2.setLong(1, promocao.getIdEstabelecimento());
                ps2.setLong(2, promocao.getId());
                ps2.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir promoção: " + e.getMessage(), e);
        }
    }

    public Promocao pesquisar(Long id) throws PersistenciaException {
        String sql = "SELECT * FROM promocao WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Promocao p = new Promocao();
                    p.setId(rs.getLong("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setData(rs.getDate("data").toLocalDate());
                    return p;
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar promoção: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Promocao> listarPorEstabelecimento(Long idEstabelecimento) throws PersistenciaException {
        String sql
                = "SELECT p.id, p.nome, p.descricao, p.data "
                + "FROM promocao p "
                + "JOIN promocao_estabelecimento pe ON pe.id_promocao = p.id "
                + "WHERE pe.id_usuario = ?";

        List<Promocao> lista = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEstabelecimento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Promocao p = new Promocao();
                    p.setId(rs.getLong("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setData(rs.getDate("data").toLocalDate());
                    p.setIdEstabelecimento(idEstabelecimento);
                    lista.add(p);
                }
            }

        } catch (Exception e) {
            throw new PersistenciaException("Erro ao listar promoções: " + e.getMessage(), e);
        }

        return lista;
    }

    public void removerVinculo(Long idUsuario, Long idPromocao) throws PersistenciaException {
        String sql = "DELETE FROM promocao_estabelecimento WHERE id_usuario = ? AND id_promocao = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idPromocao);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao remover vínculo da promoção: " + e.getMessage(), e);
        }
    }

    public Long buscarEstabelecimentoDaPromocao(Long idPromocao) throws PersistenciaException {
        String sql = "SELECT id_usuario FROM promocao_estabelecimento WHERE id_promocao = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idPromocao);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id_usuario");
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar estabelecimento da promoção: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Promocao> listarTodas() throws PersistenciaException {
        String sql = "SELECT p.id, p.nome, p.descricao, p.data, pe.id_usuario "
                + "FROM promocao p "
                + "JOIN promocao_estabelecimento pe ON pe.id_promocao = p.id "
                + "ORDER BY p.data ASC";

        List<Promocao> lista = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Promocao p = new Promocao();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));

                if (rs.getDate("data") != null) {
                    p.setData(rs.getDate("data").toLocalDate());
                }
                p.setIdEstabelecimento(rs.getLong("id_usuario"));

                lista.add(p);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar todas as promoções: " + e.getMessage(), e);
        }

        return lista;
    }
}
