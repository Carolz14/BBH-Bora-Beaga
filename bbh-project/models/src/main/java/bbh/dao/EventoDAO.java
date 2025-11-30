package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.Evento;
import bbh.service.util.ConexaoBD;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    private static EventoDAO instancia;

    private EventoDAO() {
    }

    public static EventoDAO getInstance() {
        if (instancia == null) {
            instancia = new EventoDAO();
        }
        return instancia;
    }

    public Long inserir(Evento evento) throws PersistenciaException {
        String sql = "INSERT INTO eventos (estabelecimento_id, nome, descricao, data_evento, horario_evento, criado_em, ativo) "
                   + "VALUES (?, ?, ?, ?, ?, NOW(), TRUE)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, evento.getEstabelecimentoId());
            ps.setString(2, evento.getNome());
            ps.setString(3, evento.getDescricao());

            if (evento.getData() != null) {
                ps.setDate(4, Date.valueOf(evento.getData()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            if (evento.getHorario() != null) {
                ps.setTime(5, Time.valueOf(evento.getHorario()));
            } else {
                ps.setNull(5, java.sql.Types.TIME);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir evento: " + e.getMessage(), e);
        }
    }

    public void atualizar(Evento evento) throws PersistenciaException {
        String sql = "UPDATE eventos SET nome = ?, descricao = ?, data_evento = ?, horario_evento = ? "
                   + "WHERE id = ? AND estabelecimento_id = ? AND ativo = TRUE";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, evento.getNome());
            ps.setString(2, evento.getDescricao());

            if (evento.getData() != null) {
                ps.setDate(3, Date.valueOf(evento.getData()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            if (evento.getHorario() != null) {
                ps.setTime(4, Time.valueOf(evento.getHorario()));
            } else {
                ps.setNull(4, java.sql.Types.TIME);
            }

            ps.setLong(5, evento.getId());
            ps.setLong(6, evento.getEstabelecimentoId());

            int at = ps.executeUpdate();
            if (at == 0) {
                throw new PersistenciaException("Evento não encontrado ou sem permissão para atualizar.");
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao atualizar evento: " + e.getMessage(), e);
        }
    }

    public void excluirLogico(Long idEvento, Long estabelecimentoId) throws PersistenciaException {
        String sql = "UPDATE eventos SET ativo = FALSE WHERE id = ? AND estabelecimento_id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEvento);
            ps.setLong(2, estabelecimentoId);

            int at = ps.executeUpdate();
            if (at == 0) {
                throw new PersistenciaException("Evento não encontrado ou sem permissão para excluir.");
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao excluir evento: " + e.getMessage(), e);
        }
    }

    public Evento buscarPorId(Long id) throws PersistenciaException {
        String sql = "SELECT * FROM eventos WHERE id = ? AND ativo = TRUE";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEvento(rs);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar evento por id: " + e.getMessage(), e);
        }
    }

    public List<Evento> buscarProximos4() throws PersistenciaException {
        String sql = "SELECT * FROM eventos "
                   + "WHERE ativo = TRUE AND (data_evento > CURRENT_DATE OR (data_evento = CURRENT_DATE AND horario_evento > CURRENT_TIME)) "
                   + "ORDER BY data_evento ASC, horario_evento ASC LIMIT 4";

        List<Evento> lista = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRowToEvento(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar próximos eventos: " + e.getMessage(), e);
        }
    }

    public List<Evento> listarEventosFuturos() throws PersistenciaException {
        String sql = "SELECT * FROM eventos "
                   + "WHERE ativo = TRUE AND (data_evento > CURRENT_DATE OR (data_evento = CURRENT_DATE AND horario_evento > CURRENT_TIME)) "
                   + "ORDER BY data_evento ASC, horario_evento ASC";

        List<Evento> lista = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRowToEvento(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar eventos futuros: " + e.getMessage(), e);
        }
    }

    public List<Evento> listarMeusEventos(Long estabelecimentoId) throws PersistenciaException {
        String sql = "SELECT * FROM eventos "
                   + "WHERE ativo = TRUE AND estabelecimento_id = ? "
                   + "AND (data_evento > CURRENT_DATE OR (data_evento = CURRENT_DATE AND horario_evento > CURRENT_TIME)) "
                   + "ORDER BY data_evento ASC, horario_evento ASC";

        List<Evento> lista = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, estabelecimentoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRowToEvento(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar meus eventos: " + e.getMessage(), e);
        }
    }

    private Evento mapRowToEvento(ResultSet rs) throws SQLException {
        Evento e = new Evento();
        e.setId(rs.getObject("id", Long.class));
        e.setEstabelecimentoId(rs.getObject("estabelecimento_id", Long.class));
        e.setNome(rs.getString("nome"));
        e.setDescricao(rs.getString("descricao"));

        Date d = rs.getDate("data_evento");
        if (d != null) {
            e.setData(d.toLocalDate());
        }

        Time t = rs.getTime("horario_evento");
        if (t != null) {
            e.setHorario(t.toLocalTime());
        }

        Timestamp ts = rs.getTimestamp("criado_em");
        if (ts != null) {
            e.setCriadoEm(ts.toLocalDateTime());
        }

        e.setAtivo(rs.getBoolean("ativo"));
        return e;
    }
}