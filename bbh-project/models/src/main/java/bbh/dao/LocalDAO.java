package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbh.common.PersistenciaException;
import bbh.domain.Local;
import bbh.domain.Roteiro;
import bbh.service.util.ConexaoBD;

/**
 * Classe responsável por buscar apenas os locais (usuários do tipo
 * ESTABELECIMENTO) e pontos turisticos
 * no banco de dados.
 */
public class LocalDAO {

    private static LocalDAO instancia;

    private LocalDAO() {
    }

    public static LocalDAO getInstance() {
        if (instancia == null) {
            instancia = new LocalDAO();
        }
        return instancia;
    }

    /**
     * Busca locais (estabelecimentos) cujo nome contenha o termo informado.
     * Ignora usuários de outros tipos (ADMIN, TURISTA, etc).
     */
    public List<Local> buscarPorNome(String nomeBuscado) throws PersistenciaException {
        List<Local> locais = new ArrayList<>();

        // Usamos UNION ALL para juntar os resultados das duas tabelas
        String sql = """
            (SELECT id, nome, endereco, descricao, imagem_url, 'ESTABELECIMENTO' as tipo_origem
             FROM usuarios
             WHERE usuario_tipo = 'ESTABELECIMENTO' AND LOWER(nome) LIKE LOWER(?))
            
            UNION ALL
            
            (SELECT id, nome, endereco, descricao, imagem_url, 'PONTO_TURISTICO' as tipo_origem
             FROM ponto_turistico
             WHERE ativo = TRUE AND LOWER(nome) LIKE LOWER(?))
            
            LIMIT 20
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nomeBuscado + "%");
            ps.setString(2, "%" + nomeBuscado + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Local local = new Local();
                    local.setId(rs.getLong("id"));
                    local.setNome(rs.getString("nome"));
                    local.setEndereco(rs.getString("endereco"));
                    local.setDescricao(rs.getString("descricao"));      
                    // local.setImagemUrl(rs.getString("imagem_url"));
                    String origem = rs.getString("tipo_origem");
                    
                    if ("ESTABELECIMENTO".equals(origem)) {
                        local.setCategoria("Estabelecimento");
                    } else {
                        local.setCategoria("Ponto Turístico");
                    }

                    locais.add(local);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar locais: " + e.getMessage(), e);
        }

        return locais;
    }

    /**
     * Busca por ID tentando encontrar primeiro em Estabelecimentos, depois em Pontos Turísticos.
     */
    public Local buscarPorId(Long id) throws PersistenciaException {
        Local local = null;

        // 1. Tenta buscar em Estabelecimentos
        String sqlEstab = "SELECT id, nome, endereco, descricao, imagem_url FROM usuarios WHERE id = ? AND usuario_tipo = 'ESTABELECIMENTO'";
        
        try (Connection conn = ConexaoBD.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sqlEstab)) {
            
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    local = new Local();
                    local.setId(rs.getLong("id"));
                    local.setNome(rs.getString("nome"));
                    local.setEndereco(rs.getString("endereco"));
                    local.setCategoria("Estabelecimento");
                    local.setDescricao(rs.getString("descricao"));
                    // local.setImagemUrl(rs.getString("imagem_url"));
                    return local;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar estabelecimento por ID: " + e.getMessage(), e);
        }

        // 2. Se não achou, tenta buscar em Pontos Turísticos
        String sqlPonto = "SELECT id, nome, endereco, descricao, imagem_url FROM ponto_turistico WHERE id = ? AND ativo = TRUE";
        
        try (Connection conn = ConexaoBD.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sqlPonto)) {
            
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    local = new Local();
                    local.setId(rs.getLong("id"));
                    local.setNome(rs.getString("nome"));
                    local.setEndereco(rs.getString("endereco"));
                    local.setCategoria("Ponto Turístico");
                    local.setDescricao(rs.getString("descricao"));
                    // local.setImagemUrl(rs.getString("imagem_url"));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar ponto turístico por ID: " + e.getMessage(), e);
        }

        return local;
    }
    
    public List<Local> buscarPorTag(String tagSlug) throws PersistenciaException {
        List<Local> locais = new ArrayList<>();
        // 1. Busca Usuários que têm a tag na tabela de relacionamento tag_correspondencia
        // 2. Busca Pontos Turísticos que têm a tag na coluna 'tag'
        String sql = """
            (SELECT u.id, u.nome, u.endereco, u.descricao, u.imagem_url, 'ESTABELECIMENTO' as tipo_origem
             FROM usuarios u
             JOIN tag_correspondencia tc ON u.id = tc.id_usuario
             JOIN tag t ON tc.id_tag = t.id
             WHERE u.usuario_tipo = 'ESTABELECIMENTO' AND t.slug = ?)
            
            UNION ALL
            
            (SELECT id, nome, endereco, descricao, imagem_url, 'PONTO_TURISTICO' as tipo_origem
             FROM ponto_turistico
             WHERE ativo = TRUE AND tag = ?)
            
            LIMIT 20
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tagSlug);
            ps.setString(2, tagSlug);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Local local = new Local();
                    local.setId(rs.getLong("id"));
                    local.setNome(rs.getString("nome"));
                    local.setEndereco(rs.getString("endereco"));
                    local.setDescricao(rs.getString("descricao"));
                    local.setImagemUrl(rs.getString("imagem_url"));

                    String origem = rs.getString("tipo_origem");
                    if ("ESTABELECIMENTO".equals(origem)) {
                        local.setCategoria("Estabelecimento");
                    } else {
                        local.setCategoria("Ponto Turístico");
                    }

                    locais.add(local);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar locais por tag: " + e.getMessage(), e);
        }

        return locais;
    }
}