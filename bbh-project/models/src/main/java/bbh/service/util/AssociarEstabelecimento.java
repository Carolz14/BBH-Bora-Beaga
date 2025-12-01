package bbh.service.util;

import bbh.common.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AssociarEstabelecimento {

    public static void inserirAssociacoes(
            String tabela,
            String colunaFixa,
            String colunaVariavel,
            Long idFixo,
            List<Long> idsVariaveis
    ) throws PersistenciaException {

        if (idsVariaveis == null || idsVariaveis.isEmpty()) {
            return; // nada a fazer
        }

        String sql = "INSERT INTO " + tabela + " (" + colunaFixa + ", " + colunaVariavel + ") VALUES (?, ?)";

        try (Connection conn = ConexaoBD.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                for (Long idVar : idsVariaveis) {
                    ps.setLong(1, idFixo);
                    ps.setLong(2, idVar);
                    ps.addBatch();
                }

                ps.executeBatch();
                conn.commit();

            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new PersistenciaException("Erro no rollback: " + ex.getMessage(), ex);
                }
                throw new PersistenciaException("Erro ao executar batch: " + e.getMessage(), e);
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir associações: " + e.getMessage(), e);
        }
    }
}
