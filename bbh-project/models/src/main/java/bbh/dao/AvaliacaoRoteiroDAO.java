package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bbh.common.PersistenciaException;
import bbh.service.util.ConexaoBD;

public class AvaliacaoRoteiroDAO {


     private static AvaliacaoRoteiroDAO avaliacaoDAO;

    static {
        AvaliacaoRoteiroDAO.avaliacaoDAO = null;
    }

    public static AvaliacaoRoteiroDAO getInstance() {

        if (avaliacaoDAO == null) {
            avaliacaoDAO = new AvaliacaoRoteiroDAO();
        }

        return avaliacaoDAO;
    }


    public void avaliar(Long roteiroId, Long usuarioId, int nota) throws PersistenciaException {
        String sqlDelete = "DELETE FROM avaliacao_roteiros WHERE roteiro_id = ? AND usuario_id = ?";
        String sqlInsert = "INSERT INTO avaliacao_roteiros (roteiro_id, usuario_id, nota) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection()) {

            try (PreparedStatement psD = conn.prepareStatement(sqlDelete)) {
                psD.setLong(1, roteiroId);
                psD.setLong(2, usuarioId);
                psD.executeUpdate();
            }

            try (PreparedStatement psI = conn.prepareStatement(sqlInsert)) {
                psI.setLong(1, roteiroId);
                psI.setLong(2, usuarioId);
                psI.setInt(3, nota);
                psI.executeUpdate();
            }
        }

        catch (SQLException e) {
            throw new PersistenciaException("Erro ao avaliar roteiro: " + e.getMessage(), e);
        }

    }

    public double media(Long roteiroId) throws PersistenciaException {

        String sql = "SELECT AVG(nota) as media FROM avaliacao_roteiros WHERE roteiro_id = ?";
        double media = 0.0;

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, roteiroId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    media = rs.getDouble("media");
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao calcular media: " + e.getMessage(), e);
        }
        return media;
    }

public int pesquisarNota(Long roteiroId, Long usuarioId) throws PersistenciaException {

        String sql = "SELECT nota FROM avaliacao_roteiros WHERE roteiro_id = ? AND usuario_id = ?";
        int nota = 0;

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, roteiroId);
  ps.setLong(2, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   nota = rs.getInt("nota");
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar nota: " + e.getMessage(), e);
        }
        return nota;
    }

}
