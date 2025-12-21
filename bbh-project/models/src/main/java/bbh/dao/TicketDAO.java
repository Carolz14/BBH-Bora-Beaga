package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.Ticket;
import bbh.domain.TicketMensagem;
import bbh.service.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    private static TicketDAO instancia;

    private TicketDAO() {
    }

    public static TicketDAO getInstance() {
        if (instancia == null) {
            instancia = new TicketDAO();
        }
        return instancia;
    }

    public Long inserir(Ticket t) throws PersistenciaException {
        String sql = "INSERT INTO tickets (usuario_id, usuario_email, assunto, mensagem, status, criado_em, ativo) VALUES (?, ?, ?, ?, 'ABERTO', NOW(), TRUE)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, t.getUsuarioId());
            ps.setString(2, t.getUsuarioEmail());
            ps.setString(3, t.getAssunto());
            ps.setString(4, t.getMensagem());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir ticket: " + e.getMessage(), e);
        }
    }

    public List<Ticket> listarTodos() throws PersistenciaException {
        String sql = "SELECT * FROM tickets WHERE ativo = TRUE ORDER BY criado_em DESC";
        List<Ticket> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRowToTicket(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar tickets: " + e.getMessage(), e);
        }
    }

    public List<Ticket> listarPorUsuario(Long usuarioId) throws PersistenciaException {
        String sql = "SELECT * FROM tickets WHERE usuario_id = ? AND ativo = TRUE ORDER BY criado_em DESC";
        List<Ticket> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRowToTicket(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar tickets do usuário: " + e.getMessage(), e);
        }
    }

    public Ticket buscarPorId(Long id) throws PersistenciaException {
        String sql = "SELECT * FROM tickets WHERE id = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ticket t = mapRowToTicket(rs);
                    TicketMensagemDAO msgDao = TicketMensagemDAO.getInstance();
                    List<TicketMensagem> msgs = msgDao.listarPorTicket(id);
                    t.setMensagens(msgs);
                    return t;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar ticket: " + e.getMessage(), e);
        }
    }

    public void atualizarStatus(Long id, String status) throws PersistenciaException {
        String sql = "UPDATE tickets SET status = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao atualizar status: " + e.getMessage(), e);
        }
    }

    public void marcarConcluido(Long id) throws PersistenciaException {
        atualizarStatus(id, "CONCLUIDO");
    }

    private Ticket mapRowToTicket(ResultSet rs) throws SQLException {
        Ticket t = new Ticket();
        t.setId(rs.getObject("id", Long.class));
        t.setUsuarioId(rs.getObject("usuario_id", Long.class));
        t.setUsuarioEmail(rs.getString("usuario_email"));
        t.setAssunto(rs.getString("assunto"));
        t.setMensagem(rs.getString("mensagem"));
        t.setStatus(rs.getString("status"));

        Timestamp ts = rs.getTimestamp("criado_em");
        if (ts != null) {
            t.setCriadoEm(ts.toLocalDateTime());
        }

        t.setAtivo(rs.getBoolean("ativo"));
        return t;
    }
}