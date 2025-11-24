/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.MidiaAvaliacao;
import bbh.service.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aluno
 */
public class MidiaAvalicaoDAO {

    public MidiaAvaliacao inserir(MidiaAvaliacao midia) throws PersistenciaException {
        String sql = """
                     INSERT INTO midia_avaliacao 
                     (id_avaliacao, nome_original, nome_armazenado, caminho, mime, tamanho_bytes)
                     VALUES (?,?,?,?,?,?)
                     """;
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, midia.getIdAvaliacao());
            ps.setString(2, midia.getNomeOriginal());
            ps.setString(3, midia.getNomeArmazenado());
            ps.setString(4, midia.getCaminho());
            ps.setString(5, midia.getMime());
            ps.setLong(6, midia.getTamanhoEmBytes());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new PersistenciaException("Erro na inserção de mídia, nenhuma linha foi alterada");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long idGerado = rs.getLong(1);

                    MidiaAvaliacao midiaSalva = buscarPorId(idGerado);
                    if (midiaSalva == null) {
                        return midia.criarComIdGerado(idGerado);
                    }
                    return midiaSalva;
                } else {
                    throw new PersistenciaException("Inserção de mídia falhou, não foi possível recuperar o id gerado");
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir mídia: " + e.getMessage(), e);
        }
    }

    public MidiaAvaliacao buscarPorId(Long idMidia) throws PersistenciaException {
        String sql = "SELECT FROM midia_avalicao WHERE id_midia = ?";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idMidia);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return gerarObjetoMidiaAvaliacao(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar midia por ID:" + e.getMessage() + e);
        }
    }
    
    private MidiaAvaliacao gerarObjetoMidiaAvaliacao(ResultSet rs) throws SQLException {
    return new MidiaAvaliacao(
            rs.getLong("id_midia"),
            rs.getLong("id_avaliacao"),
            rs.getString("nome_original"),
            rs.getString("nome_armazenado"),
            rs.getString("caminho"),
            rs.getString("mime"),
            rs.getLong("tamanho_bytes"),
            rs.getTimestamp("data_midia")
    );
}
}
