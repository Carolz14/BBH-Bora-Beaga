package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbh.common.PersistenciaException;
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.util.ConexaoBD;

public class EstabelecimentoDAO {
    private static EstabelecimentoDAO estabelecimentoDAO;

    static {
        EstabelecimentoDAO.estabelecimentoDAO = null;
    }

    public static EstabelecimentoDAO getInstance() {

        if (estabelecimentoDAO == null) {
            estabelecimentoDAO = new EstabelecimentoDAO();
        }

        return estabelecimentoDAO;
    }

    public List<Usuario> listarEstabelecimentos() throws PersistenciaException {
        List<Usuario> estabelecimentos = new ArrayList<>();

        String sql = "SELECT id, nome, endereco, contato, habilitado, usuario_tipo, descricao, imagem_url "
                + "FROM usuarios WHERE usuario_tipo = 'ESTABELECIMENTO' AND habilitado = TRUE";

        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario estabelecimento = new Usuario(
                        rs.getString("nome"));

                estabelecimento.setId(rs.getLong("id"));
                estabelecimento.setEndereco(rs.getString("endereco"));
                estabelecimento.setContato(rs.getObject("contato", Long.class));

                estabelecimento.setHabilitado(rs.getBoolean("habilitado"));

                String tipoStr = rs.getString("usuario_tipo");
                estabelecimento.setUsuarioTipo(UsuarioTipo.strTo(tipoStr));
                estabelecimento.setDescricao(rs.getString("descricao"));
                estabelecimento.setImagemUrl(rs.getString("imagem_url"));
                estabelecimentos.add(estabelecimento);
            }
            System.out.println("Qtd de estabelecimentos encontrados: " + estabelecimentos.size());

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar estabelecimentos: " + e.getMessage(), e);
        }

        return estabelecimentos;
    }
}
