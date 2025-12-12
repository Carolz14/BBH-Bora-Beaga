package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.Ticket;
import bbh.service.util.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    private static TicketDAO instancia;

    private TicketDAO() {}

    public static TicketDAO getInstance() {
        if (instancia == null) instancia = new TicketDAO();
        return instancia;
    }

    public Long inserir(Ticket t) throws PersistenciaException {
        String sql = "INSERT INTO tickets (usuario_id, usuario_email, assunto, mensagem, status, criado_em, ativo) VALUES (?, ?, ?, ?, ?, NOW(), TRUE)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, t.getUsuarioId());
            ps.setString(2, t.getUsuarioEmail());
            ps.setString(3, t.getAssunto());
            ps.setString(4, t.getMensagem());
            ps.setString(5, t.getStatus() != null ? t.getStatus() : "ABERTO");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            return null;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir ticket: " + e.getMessage(), e);
        }
    }

    public Ticket buscarPorId(Long id) throws PersistenciaException {
        String sql = "SELECT * FROM tickets WHERE id = ? AND ativo = TRUE";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRowToTicket(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar ticket: " + e.getMessage(), e);
        }
    }

    public List<Ticket> listarPorUsuario(Long usuarioId) throws PersistenciaException {
        String sql = "SELECT * FROM tickets WHERE ativo = TRUE AND usuario_id = ? ORDER BY criado_em DESC";
        List<Ticket> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRowToTicket(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar tickets do usuário: " + e.getMessage(), e);
        }
    }

    public List<Ticket> listarTodos() throws PersistenciaException {
        String sql = "SELECT * FROM tickets WHERE ativo = TRUE ORDER BY criado_em DESC";
        List<Ticket> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRowToTicket(rs));
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar tickets: " + e.getMessage(), e);
        }
    }

    public void responderTicket(Long id, String resposta, Long adminId, String novoStatus) throws PersistenciaException {
        String sql = "UPDATE tickets SET resposta = ?, respondido_por = ?, status = ? WHERE id = ? AND ativo = TRUE";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, resposta);
            if (adminId != null) ps.setLong(2, adminId); else ps.setNull(2, java.sql.Types.BIGINT);
            ps.setString(3, novoStatus != null ? novoStatus : "EM_ANDAMENTO");
            ps.setLong(4, id);
            int at = ps.executeUpdate();
            if (at == 0) throw new PersistenciaException("Ticket não encontrado ou já inativo.");
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao responder ticket: " + e.getMessage(), e);
        }
    }

    public void marcarConcluido(Long id) throws PersistenciaException {
        String sql = "UPDATE tickets SET status = 'CONCLUIDO' WHERE id = ? AND ativo = TRUE";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int at = ps.executeUpdate();
            if (at == 0) throw new PersistenciaException("Ticket não encontrado ou já inativo.");
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao concluir ticket: " + e.getMessage(), e);
        }
    }

    public void excluirLogico(Long id) throws PersistenciaException {
        String sql = "UPDATE tickets SET ativo = FALSE WHERE id = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inativar ticket: " + e.getMessage(), e);
        }
    }

    private Ticket mapRowToTicket(ResultSet rs) throws SQLException {
        Ticket t = new Ticket();
        t.setId(rs.getObject("id", Long.class));
        t.setUsuarioId(rs.getObject("usuario_id", Long.class));
        t.setUsuarioEmail(rs.getString("usuario_email"));
        t.setAssunto(rs.getString("assunto"));
        t.setMensagem(rs.getString("mensagem"));
        t.setResposta(rs.getString("resposta"));
        t.setRespondidoPor(rs.getObject("respondido_por", Long.class));
        t.setStatus(rs.getString("status"));
        Timestamp ts = rs.getTimestamp("criado_em");
        if (ts != null) t.setCriadoEm(ts.toLocalDateTime());
        t.setAtivo(rs.getBoolean("ativo"));
        return t;
    }
}