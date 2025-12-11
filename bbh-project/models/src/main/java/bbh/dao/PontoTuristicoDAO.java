package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bbh.common.PersistenciaException;
import bbh.domain.PontoTuristico;
import bbh.service.util.ConexaoBD;

public class PontoTuristicoDAO implements GenericDeleteDAO<PontoTuristico, Long> {

    private static PontoTuristicoDAO instance;

    private PontoTuristicoDAO() {
    }

    public static PontoTuristicoDAO getInstance() {
        if (instance == null) {
            instance = new PontoTuristicoDAO();
        }
        return instance;
    }

    @Override
    public void inserir(PontoTuristico pt) throws PersistenciaException {
        // ATUALIZADO: Incluindo a coluna 'tag'
        String sql = "INSERT INTO ponto_turistico (nome, endereco, descricao, imagem_url, tag) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pt.getNome());
            ps.setString(2, pt.getEndereco());
            ps.setString(3, pt.getDescricao());
            ps.setString(4, pt.getImagemUrl());
            
            // Se a tag for nula, salva como string vazia ou um valor padrão para não quebrar
            ps.setString(5, pt.getTag() != null ? pt.getTag() : ""); 

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pt.setId(rs.getLong(1));
                }
            }
            pt.setAtivo(true);

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir ponto turístico: " + e.getMessage(), e);
        }
    }

    @Override
    public PontoTuristico pesquisar(Long id) throws PersistenciaException {
        PontoTuristico pt = null;
        
        // ATUALIZADO: Trazendo a 'tag' no SELECT
        String sql = "SELECT id, nome, endereco, descricao, imagem_url, tag, ativo FROM ponto_turistico WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pt = new PontoTuristico();
                    pt.setId(rs.getLong("id"));
                    pt.setNome(rs.getString("nome"));
                    pt.setEndereco(rs.getString("endereco"));
                    pt.setDescricao(rs.getString("descricao"));
                    pt.setImagemUrl(rs.getString("imagem_url"));
                    
                    // Preenche a tag
                    pt.setTag(rs.getString("tag"));
                    
                    pt.setAtivo(rs.getBoolean("ativo"));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar ponto turístico por ID: " + e.getMessage(), e);
        }

        return pt;
    }

    public List<PontoTuristico> listarTodos() throws PersistenciaException {
        List<PontoTuristico> lista = new ArrayList<>();
        
        // ATUALIZADO: Trazendo a 'tag' no SELECT
        String sql = "SELECT id, nome, endereco, descricao, imagem_url, tag, ativo FROM ponto_turistico";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PontoTuristico pt = new PontoTuristico();
                pt.setId(rs.getLong("id"));
                pt.setNome(rs.getString("nome"));
                pt.setEndereco(rs.getString("endereco"));
                pt.setDescricao(rs.getString("descricao"));
                pt.setImagemUrl(rs.getString("imagem_url")); 
                pt.setTag(rs.getString("tag")); // Preenche a tag
                pt.setAtivo(rs.getBoolean("ativo"));

                lista.add(pt);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar todos: " + e.getMessage(), e);
        }

        return lista;
    }

    public List<PontoTuristico> listarAtivos() throws PersistenciaException {
        List<PontoTuristico> lista = new ArrayList<>();
        
        // ATUALIZADO: Trazendo a 'tag' no SELECT
        String sql = "SELECT id, nome, endereco, descricao, imagem_url, tag, ativo FROM ponto_turistico WHERE ativo = TRUE";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PontoTuristico pt = new PontoTuristico();
                pt.setId(rs.getLong("id"));
                pt.setNome(rs.getString("nome"));
                pt.setEndereco(rs.getString("endereco"));
                pt.setDescricao(rs.getString("descricao"));
                pt.setImagemUrl(rs.getString("imagem_url"));
                pt.setTag(rs.getString("tag")); // Preenche a tag
                pt.setAtivo(rs.getBoolean("ativo"));
                
                lista.add(pt);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar ativos: " + e.getMessage(), e);
        }
        return lista;
    }
    
    public List<PontoTuristico> pesquisarPorNome(String termo) throws PersistenciaException {
        List<PontoTuristico> lista = new ArrayList<>();
        
        // ATUALIZADO: Trazendo a 'tag' no SELECT
        String sql = "SELECT id, nome, endereco, descricao, imagem_url, tag, ativo FROM ponto_turistico WHERE nome LIKE ? AND ativo = TRUE";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + termo + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PontoTuristico pt = new PontoTuristico();
                    pt.setId(rs.getLong("id"));
                    pt.setNome(rs.getString("nome"));
                    pt.setEndereco(rs.getString("endereco"));
                    pt.setDescricao(rs.getString("descricao"));
                    pt.setImagemUrl(rs.getString("imagem_url")); 
                    pt.setTag(rs.getString("tag")); // Preenche a tag
                    pt.setAtivo(rs.getBoolean("ativo"));
                    
                    lista.add(pt);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar ponto turístico por nome: " + e.getMessage(), e);
        }

        return lista;
    }

    public void atualizar(PontoTuristico pt) throws PersistenciaException {
        // ATUALIZADO: Incluí tag, imagem_url e ativo no UPDATE (estava faltando no seu código antigo)
        String sql = "UPDATE ponto_turistico SET nome=?, endereco=?, descricao=?, imagem_url=?, tag=?, ativo=? WHERE id=?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pt.getNome());
            ps.setString(2, pt.getEndereco());
            ps.setString(3, pt.getDescricao());
            ps.setString(4, pt.getImagemUrl());
            ps.setString(5, pt.getTag());
            ps.setBoolean(6, pt.isAtivo());
            ps.setLong(7, pt.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao atualizar ponto turístico: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws PersistenciaException {
        String sql = "UPDATE ponto_turistico SET ativo = FALSE WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new PersistenciaException("Ponto Turístico não encontrado para desativar.");
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao desativar ponto turístico: " + e.getMessage(), e);
        }
    }
}