package bbh.dao;

import bbh.service.util.ConexaoBD;
import bbh.domain.Tag;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bbh.common.PersistenciaException;

public class TagCorrespondenciaDAO {

    public void associarTagEstabelecimento(Long idUsuario, Long idTag) throws PersistenciaException {
        String sql = "INSERT INTO tag_correspondencia(id_usuario, id_tag) VALUES (?,?)";
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idTag);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao associar tag:" + e.getMessage(), e);
        }
    }

    public void removerTagEstabelecimento(Long idUsuario, Long idTag) throws PersistenciaException {
        String sql = "DELETE FROM tag_correspondencia WHERE id_usuario = ? AND id_tag = ?";
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idTag);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao remover tag:" + e.getMessage(), e);
        }
    }

    public List<Tag> listarTagsDoEstabelecimento(Long idUsuario) throws PersistenciaException {
        String sql = "SELECT t.id, t.nome, t.slug " +
                "FROM tag t " +
                "JOIN tag_correspondencia tg ON t.id = tg.id_tag " +
                "WHERE tg.id_usuario = ?";
        List<Tag> tags = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String slug = rs.getString("slug");
                    Long id = rs.getLong("id");
                    Tag tag = new Tag(nome, slug, id);
                    tags.add(tag);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao tentar listar as tags relacionadas ao estabelecimento"
                    + e.getMessage(), e);
        }
        return tags;
    }

    public List<Long> listarEstabelecimentosPorTag(Long idTag) throws PersistenciaException {
        String sql = "SELECT id_usuario FROM tag_correspondencia WHERE id_tag = ?";
        List<Long> ids = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idTag);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    ids.add(rs.getLong("id_usuario"));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao tentar listar os estabelecimentos com essa tag:"
                    + e.getMessage(), e);
        }
        return ids;
    }
}
