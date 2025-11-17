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

    public void inserirAvaliacao(Avaliacao avaliacao) throws SQLException {
        String sql = """
                     INSERT INTO avaliacao (id_usuario, id_estabelecimento, avaliacao, comentario, data_avaliacao)
                     VALUES (?, ?, ?, ?, ?)
                     """;
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, avaliacao.getIdUsuario());
            ps.setLong(2, avaliacao.getIdEstabelecimento());
            ps.setInt(3, avaliacao.getNotaAvaliacao());
            ps.setString(4, avaliacao.getComentario());
            ps.setTimestamp(5, avaliacao.getDataAvaliacao());
            ps.executeUpdate();
        }
    }

    public List<Avaliacao> buscarAvaliacaoPorEstabelecimento(Long idEstabelecimento) throws SQLException {
        String sql = """
                     SELECT * FROM avaliacao
                     WHERE id__estabelecimento = ?
                     ORDER BY data_avaliacao DESC
                     """;
        List<Avaliacao> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idEstabelecimento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(gerarObjetoAvaliacao(rs));
            }
        }
        return lista;
    }

    private Avaliacao gerarObjetoAvaliacao(ResultSet rs) throws SQLException {
        return new Avaliacao(
                rs.getLong("id_avaliacao"),
                rs.getLong("id_usuario"),
                rs.getLong("id_estabelecimento"),
                rs.getInt("avaliacao"),
                rs.getString("comentario"),
                rs.getTimestamp("data_avaliacao")
        );
    }
}
