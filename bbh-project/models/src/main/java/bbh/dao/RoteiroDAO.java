package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbh.common.PersistenciaException;

import bbh.domain.Roteiro;
import bbh.service.util.ConexaoBD;

public class RoteiroDAO {

    private static RoteiroDAO roteiroDAO;

    static {
        RoteiroDAO.roteiroDAO = null;
    }

    public static RoteiroDAO getInstance() {

        if (roteiroDAO == null) {
            roteiroDAO = new RoteiroDAO();
        }

        return roteiroDAO;
    }

    public void inserir(Roteiro roteiro) throws PersistenciaException {

       String sql = "INSERT INTO roteiros (nome, descricao, paradas_texto, usuario_id, habilitado) "
                + "VALUES (?, ?, ?, ?, ?)";

      
        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, roteiro.getNome());
            ps.setString(2, roteiro.getDescricao());

          ps.setString(3, roteiro.getParadas()); 
            ps.setLong(4, roteiro.getUsuarioId());     
            ps.setBoolean(5, roteiro.getHabilitado());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    roteiro.setId(rs.getLong(1));
                }
            }
           
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir roteiro: " + e.getMessage(), e);
        }
    }

    public List<Roteiro> pesquisarAutor(Long usuarioId) throws PersistenciaException {
        List<Roteiro> roteirosUsuario = new ArrayList<>();

        String sql = "SELECT id, nome, descricao, usuario_id, habilitado "
                + "FROM roteiros WHERE usuario_id = ? AND habilitado = TRUE";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Roteiro roteiro = new Roteiro(
                            rs.getString("nome"),
                            rs.getString("descricao"));
                    roteiro.setId(rs.getLong("id"));
                    roteiro.setUsuarioId(rs.getLong("usuario_id"));
                    roteiro.setHabilitado(rs.getBoolean("habilitado"));

                    roteirosUsuario.add(roteiro);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar pelo id: " + e.getMessage(), e);
        }

        return roteirosUsuario;
    }

    public List<Roteiro> listar() throws PersistenciaException {
        List<Roteiro> roteiros = new ArrayList<>();

        String sql = "SELECT id, nome, descricao, usuario_id, habilitado "
                + "FROM roteiros WHERE habilitado = TRUE";

        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Roteiro roteiro = new Roteiro(
                        rs.getString("nome"),
                        rs.getString("descricao"));
                roteiro.setId(rs.getLong("id"));
                roteiro.setUsuarioId(rs.getLong("usuario_id"));
                roteiro.setHabilitado(rs.getBoolean("habilitado"));

                roteiros.add(roteiro);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar roteiros ativas: " + e.getMessage(), e);
        }

        return roteiros;
    }

    public Roteiro pesquisar(Long id) throws PersistenciaException {
        Roteiro roteiro = null;

    String sql = "SELECT id, nome, descricao, paradas_texto, usuario_id, habilitado "
                + "FROM roteiros WHERE id = ?";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    roteiro = new Roteiro(
                            rs.getString("nome"),
                            rs.getString("descricao"));
                    roteiro.setId(rs.getLong("id"));
                    roteiro.setUsuarioId(rs.getLong("usuario_id"));
                    roteiro.setHabilitado(rs.getBoolean("habilitado"));
roteiro.setParadas(rs.getString("paradas_texto"));
                    
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar pelo id: " + e.getMessage(), e);
        }

        return roteiro;
    }

    public void excluir(Long roteiroId) throws PersistenciaException {
        String sql = "UPDATE roteiros SET habilitado = false WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, roteiroId);

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new PersistenciaException("Roteiro não encontrado para deletar");
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao desabilitar roteiro: " + e.getMessage(), e);
        }
    }

   

    public List<Roteiro> pesquisarOutros(Long usuarioId) throws PersistenciaException {
        List<Roteiro> roteirosOutros = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, usuario_id, habilitado "
                + "FROM roteiros WHERE usuario_id != ? AND habilitado = TRUE"; 

        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Roteiro roteiro = new Roteiro(
                            rs.getString("nome"),
                            rs.getString("descricao"));
                    roteiro.setId(rs.getLong("id"));
                    roteiro.setUsuarioId(rs.getLong("usuario_id"));
                    roteiro.setHabilitado(rs.getBoolean("habilitado"));
                    roteirosOutros.add(roteiro);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar outros roteiros: " + e.getMessage(), e);
        }
        return roteirosOutros;
    }

}
