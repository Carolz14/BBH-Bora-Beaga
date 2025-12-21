package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.service.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitaDAO {

    // TOTAL GERAL (
    public int contarVisitasPorEstabelecimento(long idEstabelecimento) throws PersistenciaException {
        String sql = "SELECT COUNT(*) AS total FROM visitas WHERE id_estabelecimento = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEstabelecimento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao contar visitas: " + e.getMessage(), e);
        }
    }

    //TOTAL DO MÊS ATUAL
    public int contarVisitasDoMes(long idEstabelecimento) throws PersistenciaException {
        String sql = """
            SELECT COUNT(*) AS total
            FROM visitas
            WHERE id_estabelecimento = ?
              AND MONTH(data_visita) = MONTH(CURRENT_DATE)
              AND YEAR(data_visita) = YEAR(CURRENT_DATE)
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEstabelecimento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao contar visitas do mês: " + e.getMessage(), e);
        }
    }

    public void inserirVisita(long idEstabelecimento) throws PersistenciaException {
        String sql = "INSERT INTO visitas (id_estabelecimento) VALUES (?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEstabelecimento);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir visita: " + e.getMessage(), e);
        }
    }
}
