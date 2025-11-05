package bbh.dao;

import bbh.service.util.ConexaoBD;
import bbh.domain.Tag;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bbh.common.PersistenciaException;

public class TagDAO {

    public void inserirUnidade(Tag tag) throws PersistenciaException {
        String sql = "INSERT INTO tag (nome,slug,contador) VALUES (?,?)";
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tag.getNome());
            ps.setString(2, tag.getSlug());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir tag:" + e.getMessage(), e);
        }
    }

    public void inserirEmLote(List<Tag> tags) throws PersistenciaException {
        if (tags == null || tags.isEmpty())
            return;
        String sql = "INSERT INTO tag (nome,slug,contador) VALUES (?,?)";
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (Tag t : tags) {
                if (t == null)
                    continue;
                ps.setString(1, t.getNome());
                ps.setString(2, t.getSlug());
                ps.setInt(3, 0);
            }
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException duplicata) {

            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {

                }
                throw new PersistenciaException("Erro ao inserir as tags em lote" + e.getMessage(), e);
            }
            conn.commit();
        } catch (Exception e) {
            throw new PersistenciaException("Erro ao inserir as tags em lote" + e.getMessage(), e);
        }
    }

    public Tag pesquisarPorId(Long id) throws PersistenciaException {
        String sql = "SELECT id, nome, slug FROM tag WHERE id = ?";
        Tag tag = null;
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tag = new Tag(rs.getString("nome"), rs.getString("slug"),
                            rs.getLong("id"));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar a tag pelo ID" + e.getMessage(), e);
        }
        return tag;
    }

    public Tag pesquisarPorSlug(String slug) throws PersistenciaException {
        String sql = "SELECT id, nome, slug from tag WHERE slug = ?";
        Tag tag = null;
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, slug);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tag = new Tag(rs.getString("nome"), rs.getString("slug"),
                            rs.getLong("id"));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar a tag pela slug" + e.getMessage(), e);
        }
        return tag;
    }

    public List<Tag> listarTodasAsTags() throws PersistenciaException {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT id, nome, slug FROM tag";
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String slug = rs.getString("slug");
                Tag tag = new Tag(nome, slug, id);
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao tentar listar todas as tags" + e.getMessage(), e);
        }
        return tags;
    }

    public void atualizar(Tag tag) throws PersistenciaException {
        String sql = "UPDATE tag SET nome = ?, slug = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tag.getNome());
            ps.setString(2, tag.getSlug());
            ps.setLong(3, tag.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao tentar atualizar a tag" + e.getMessage(), e);
        }
    }

    public void excluir(Long id) throws PersistenciaException {
        String sql = "DELETE FROM tag WHERE id = ?";
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao tentar excluir a tag" + e.getMessage(), e);
        }
    }
}
