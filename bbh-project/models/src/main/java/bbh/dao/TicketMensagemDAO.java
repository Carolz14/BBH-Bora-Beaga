package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.TicketMensagem;
import bbh.service.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketMensagemDAO {

    private static TicketMensagemDAO instancia;

    private TicketMensagemDAO() {
    }

    public static TicketMensagemDAO getInstance() {
        if (instancia == null) {
            instancia = new TicketMensagemDAO();
        }
        return instancia;
    }

    public void inserir(TicketMensagem msg) throws PersistenciaException {
        String sql = "INSERT INTO ticket_mensagens (ticket_id, autor_id, autor_tipo, mensagem, criado_em) VALUES (?, ?, ?, ?, NOW())";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, msg.getTicketId());
            if (msg.getAutorId() != null) {
                ps.setLong(2, msg.getAutorId());
            } else {
                ps.setNull(2, java.sql.Types.BIGINT);
            }
            ps.setString(3, msg.getAutorTipo());
            ps.setString(4, msg.getMensagem());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    msg.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir mensagem do ticket: " + e.getMessage(), e);
        }
    }

    public List<TicketMensagem> listarPorTicket(Long ticketId) throws PersistenciaException {
        String sql = "SELECT * FROM ticket_mensagens WHERE ticket_id = ? ORDER BY criado_em ASC";
        List<TicketMensagem> lista = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, ticketId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRowToTicketMensagem(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar mensagens do ticket: " + e.getMessage(), e);
        }
    }

    private TicketMensagem mapRowToTicketMensagem(ResultSet rs) throws SQLException {
        TicketMensagem m = new TicketMensagem();
        m.setId(rs.getObject("id", Long.class));
        m.setTicketId(rs.getObject("ticket_id", Long.class));

        Long autorId = rs.getObject("autor_id", Long.class);
        if (autorId != null) {
            m.setAutorId(autorId);
        }

        m.setAutorTipo(rs.getString("autor_tipo"));
        m.setMensagem(rs.getString("mensagem"));

        Timestamp ts = rs.getTimestamp("criado_em");
        if (ts != null) {
            m.setCriadoEm(ts.toLocalDateTime());
        }

        return m;
    }
}