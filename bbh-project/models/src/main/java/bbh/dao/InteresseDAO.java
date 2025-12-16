package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.Local;
import bbh.service.util.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InteresseDAO {
    
    private static InteresseDAO instance;
    public static InteresseDAO getInstance() {
        if (instance == null) {
            instance = new InteresseDAO();
        }
        return instance;
    }
    
    public boolean alternarInteresse(Long idTurista, Long idItem, String tipoItem) throws PersistenciaException {
        if (jaEstaSalvo(idTurista, idItem, tipoItem)) {
            remover(idTurista, idItem, tipoItem);
            return false;
        } else {
            adicionar(idTurista, idItem, tipoItem);
            return true;
        }
    }

    public void adicionar(Long idTurista, Long idItem, String tipoItem) throws PersistenciaException {
        String sql = "INSERT INTO lista_interesse (id_turista, id_item, tipo_item) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idTurista);
            ps.setLong(2, idItem);
            ps.setString(3, tipoItem);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao salvar interesse", e);
        }
    }

    public void remover(Long idTurista, Long idItem, String tipoItem) throws PersistenciaException {
        String sql = "DELETE FROM lista_interesse WHERE id_turista=? AND id_item=? AND tipo_item=?";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idTurista);
            ps.setLong(2, idItem);
            ps.setString(3, tipoItem);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao remover interesse", e);
        }
    }

    public boolean jaEstaSalvo(Long idTurista, Long idItem, String tipoItem) throws PersistenciaException {
        String sql = "SELECT id FROM lista_interesse WHERE id_turista=? AND id_item=? AND tipo_item=?";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idTurista);
            ps.setLong(2, idItem);
            ps.setString(3, tipoItem);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao verificar interesse", e);
        }
    }

    // Aquela lógica de UNION que fizemos no LocalDAO, mas filtrando pela tabela de interesse
    public List<Local> listarMeusInteresses(Long idTurista) throws PersistenciaException {
        List<Local> lista = new ArrayList<>();
        String sql = """
            SELECT u.id, u.nome, u.endereco, u.descricao, u.imagem_url, 'ESTABELECIMENTO' as tipo_origem
            FROM lista_interesse li
            JOIN usuarios u ON li.id_item = u.id
            WHERE li.id_turista = ? AND li.tipo_item = 'ESTABELECIMENTO'
            
            UNION ALL
            
            SELECT pt.id, pt.nome, pt.endereco, pt.descricao, pt.imagem_url, 'PONTO_TURISTICO' as tipo_origem
            FROM lista_interesse li
            JOIN ponto_turistico pt ON li.id_item = pt.id
            WHERE li.id_turista = ? AND li.tipo_item = 'PONTO_TURISTICO'
        """;

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idTurista);
            ps.setLong(2, idTurista);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Local l = new Local();
                    l.setId(rs.getLong("id"));
                    l.setNome(rs.getString("nome"));
                    l.setImagemUrl(rs.getString("imagem_url"));
                    l.setCategoria(rs.getString("tipo_origem").equals("ESTABELECIMENTO") ? "Estabelecimento" : "Ponto Turístico");
                    lista.add(l);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar interesses", e);
        }
        return lista;
    }

    // Retorna uma lista de Strings "ID_TIPO" para facilitar a verificação no JSP
    // Ex: ["10_ESTABELECIMENTO", "5_PONTO_TURISTICO"]
    public List<String> carregarIdsSalvos(Long idTurista) throws PersistenciaException {
        List<String> salvos = new ArrayList<>();
        String sql = "SELECT id_item, tipo_item FROM lista_interesse WHERE id_turista = ?";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idTurista);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    salvos.add(rs.getLong("id_item") + "_" + rs.getString("tipo_item"));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException(e.getMessage());
        }
        return salvos;
    }
}
