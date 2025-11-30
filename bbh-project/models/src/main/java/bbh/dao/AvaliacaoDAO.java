/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbh.dao;

import bbh.service.util.ConexaoBD;
import bbh.domain.Avaliacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bbh.common.PersistenciaException;

/**
 *
 * @author aluno
 */
public class AvaliacaoDAO {

    public Avaliacao inserirAvaliacao(Avaliacao avaliacao) throws PersistenciaException {
        String sql = """
                INSERT INTO avaliacao
                (id_usuario, id_estabelecimento, nota_avaliacao, comentario)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, avaliacao.getIdUsuario());
            ps.setLong(2, avaliacao.getIdEstabelecimento());
            ps.setInt(3, avaliacao.getNotaAvaliacao());
            ps.setString(4, avaliacao.getComentario());

            int linhas = ps.executeUpdate();
            if (linhas == 0) {
                throw new PersistenciaException("Erro na inserção, nenhuma linha foi alterada");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    long idCriado = rs.getLong(1);
                    avaliacao.setIdAvaliação(idCriado);
                    return avaliacao;
                } else {
                    throw new PersistenciaException("Inserção realizada mas não foi possível obter o id gerado.");
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir avaliação: " + e.getMessage(), e);
        }
    }

    public List<Avaliacao> buscarAvaliacoesPorEstabelecimento(long idEstabelecimento) throws PersistenciaException {
        String sql = """
                SELECT * FROM avaliacao
                WHERE id_estabelecimento = ?
                ORDER BY data_avaliacao DESC
                """;
        List<Avaliacao> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idEstabelecimento);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(gerarObjetoAvaliacao(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao tentar listar as avaliações do estabelecimento " + e.getMessage() + e);
        }
        return lista;
    }

    public Avaliacao buscarPorId(Long idAvaliacao) throws PersistenciaException {
        String sql = "SELECT * FROM avaliacao WHERE id_avaliacao = ?";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idAvaliacao);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return gerarObjetoAvaliacao(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar a avaliação por ID" + e.getMessage() + e);
        }
    }

    public void atualizarAvaliacao(Avaliacao avaliacao) throws PersistenciaException {
        if (avaliacao.getIdAvaliacao() <= 0) {
            throw new PersistenciaException("ID da avaliação inválido para update");
        }
        String sql = """
                UPDATE avaliacao
                SET nota_avaliacao = ?, comentario = ?, data_avaliacao = CURRENT_TIMESTAMP
                WHERE id_avaliacao = ?
                """;
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, avaliacao.getNotaAvaliacao());
            ps.setString(2, avaliacao.getComentario());
            ps.setLong(3, avaliacao.getIdAvaliacao());
            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new PersistenciaException("Erro: tentou atualizar uma avaliação que não existe, id da avaliação:"
                        + avaliacao.getIdAvaliacao());
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao tentar atualizar a avaliação " + e.getMessage() + e);
        }
    }

    public void removerAvaliacao(long idAvaliacao) throws PersistenciaException {
        String sql = "DELETE FROM avaliacao WHERE id_avaliacao = ?";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idAvaliacao);
            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new PersistenciaException("ID da avaliacao nao existe, zero linhas afetadas");
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao remover avaliação " + e.getMessage() + e);
        }
    }

    public double calcularNotaMedia(long idEstabelecimento) throws PersistenciaException {
        String sql = "SELECT AVG(nota_avaliacao) AS media FROM avaliacao WHERE id_estabelecimento = ?";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idEstabelecimento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("media");
                }
            }
            return 0.0;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao calcular media das notas do estabelecimento" + e.getMessage() + e);
        }
    }

    private Avaliacao gerarObjetoAvaliacao(ResultSet rs) throws SQLException {
        return new Avaliacao(
                rs.getLong("id_avaliacao"),
                rs.getLong("id_usuario"),
                rs.getLong("id_estabelecimento"),
                rs.getInt("nota_avaliacao"),
                rs.getString("comentario"),
                rs.getTimestamp("data_avaliacao"));
    }
}
