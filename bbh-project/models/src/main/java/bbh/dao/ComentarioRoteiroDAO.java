package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import bbh.common.PersistenciaException;
import bbh.domain.Comentario;
import bbh.service.util.ConexaoBD;
import java.util.ArrayList;
import java.util.List;

public class ComentarioRoteiroDAO {

     private static ComentarioRoteiroDAO comentarioDAO;

    static {
      ComentarioRoteiroDAO.comentarioDAO = null;
    }

    public static ComentarioRoteiroDAO getInstance() {

        if (comentarioDAO == null) {
            comentarioDAO = new ComentarioRoteiroDAO();
        }

        return comentarioDAO;
    }
    public void inserir(Comentario comentario) throws PersistenciaException {
        String sql = "INSERT INTO comentarios_roteiro (roteiro_id, usuario_id, texto, arquivo_url) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, comentario.getRoteiroId());
            ps.setLong(2, comentario.getUsuarioId());
            ps.setString(3, comentario.getTexto());
            ps.setString(4, comentario.getImagemUrl());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir comentário: " + e.getMessage(), e);
        }
    }

    public void excluir(Long comentarioId, Long usuarioId) throws PersistenciaException {
        String sql = "DELETE FROM comentarios_roteiro WHERE id = ? AND usuario_id = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, comentarioId);
            ps.setLong(2, usuarioId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao excluir comentário: " + e.getMessage(), e);
        }
    }

    public Comentario pesquisar(Long id) throws PersistenciaException {
        String sql = "SELECT * FROM comentarios_roteiro WHERE id = ?";

        Comentario comentario = null;
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    comentario = new Comentario();
                    comentario.setId(rs.getLong("id"));
                    comentario.setRoteiroId(rs.getLong("roteiro_id"));
                    comentario.setUsuarioId(rs.getLong("usuario_id"));
                    comentario.setTexto(rs.getString("texto"));
                    comentario.setImagemUrl(rs.getString("imagem_url"));
                }
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar comentário: " + e.getMessage(), e);
        }
        return comentario;
    }

    public List<Comentario> listarComentarios(Long roteiroId) throws PersistenciaException {

        List<Comentario> lista = new ArrayList<>();
        String sql = "SELECT c.*, u.nome as nome_usuario" + "FROM comentarios_roteiro c"
                + "JOIN usuarios u ON c.usuario_id = u.id" + "WHERE c.roteiro_id = ?" + "ORDER BY c. data DESC";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, roteiroId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comentario c = new Comentario();
                    c.setId(rs.getLong("id"));
                    c.setRoteiroId(rs.getLong("roteiro_id"));
                    c.setUsuarioId(rs.getLong("usuario_id"));
                    c.setTexto(rs.getString("texto"));
                    c.setImagemUrl(rs.getString("imagem_url"));
                    c.setData(rs.getTimestamp("data").toLocalDateTime());
                    c.setNomeUsuario(rs.getString("nome_usuario"));
                    lista.add(c); 
                }
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar comentários: " + e.getMessage(), e);
        }
        return lista;
    }

}
