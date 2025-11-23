package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import bbh.common.PersistenciaException;
import bbh.domain.Evento;
import bbh.service.util.ConexaoBD;

public class EventoDAO {

    private static EventoDAO instancia;

    private EventoDAO() {}

    public static EventoDAO getInstance() {
        if (instancia == null) {
            instancia = new EventoDAO();
        }
        return instancia;
    }

    public void inserir(Evento evento) throws PersistenciaException {

        String sql = """
            INSERT INTO eventos 
            (id_estabelecimento, nome, descricao, data_evento, horario_evento)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, evento.getIdEstabelecimento());
            ps.setString(2, evento.getNome());
            ps.setString(3, evento.getDescricao());
            ps.setDate(4, Date.valueOf(evento.getData()));
            ps.setTime(5, Time.valueOf(evento.getHorario()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir evento: " + e.getMessage(), e);
        }
    }

    public List<Evento> buscarEventosProximos(Long idEstabelecimento) throws PersistenciaException {
        List<Evento> lista = new ArrayList<>();

        String sql = """
            SELECT id, id_estabelecimento, nome, descricao, data_evento, horario_evento
            FROM eventos
            WHERE id_estabelecimento = ?
              AND data_evento >= CURRENT_DATE
            ORDER BY data_evento ASC, horario_evento ASC
            LIMIT 20
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEstabelecimento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Evento e = new Evento();
                    e.setId(rs.getLong("id"));
                    e.setIdEstabelecimento(rs.getLong("id_estabelecimento"));
                    e.setNome(rs.getString("nome"));
                    e.setDescricao(rs.getString("descricao"));
                    e.setData(rs.getDate("data_evento").toLocalDate());
                    e.setHorario(rs.getTime("horario_evento").toLocalTime());

                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar eventos: " + e.getMessage(), e);
        }

        return lista;
    }

    public List<Evento> listarTodos() throws PersistenciaException {
        List<Evento> lista = new ArrayList<>();

        String sql = """
            SELECT id, id_estabelecimento, nome, descricao, data_evento, horario_evento
            FROM eventos
            ORDER BY data_evento DESC
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Evento e = new Evento();
                e.setId(rs.getLong("id"));
                e.setIdEstabelecimento(rs.getLong("id_estabelecimento"));
                e.setNome(rs.getString("nome"));
                e.setDescricao(rs.getString("descricao"));
                e.setData(rs.getDate("data_evento").toLocalDate());
                e.setHorario(rs.getTime("horario_evento").toLocalTime());

                lista.add(e);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar todos os eventos: " + e.getMessage(), e);
        }

        return lista;
    }

    public void deletar(Long idEvento) throws PersistenciaException {

        String sql = "DELETE FROM eventos WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEvento);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao deletar evento: " + e.getMessage(), e);
        }
    }

}