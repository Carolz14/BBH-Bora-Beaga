package bbh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbh.common.PersistenciaException; //procurar no bd
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.util.ConexaoBD;

public class UsuarioDAO implements GenericDeleteDAO<Usuario, Long> {

    private static UsuarioDAO usuarioDAO;

    static {
        UsuarioDAO.usuarioDAO = null;
    }

    public static UsuarioDAO getInstance() {

        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }

        return usuarioDAO;
    }

    @Override
    public void inserir(Usuario usuario) throws PersistenciaException {
        if (pesquisarEmail(usuario.getEmail()) != null) {
            throw new PersistenciaException("'" + usuario.getEmail() + "' usuario ja existente");
        }

        String sql = "INSERT INTO usuarios (nome, email, senha, naturalidade, endereco, contato, cnpj, habilitado, usuario_tipo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getNaturalidade());
            ps.setString(5, usuario.getEndereco());

            Long contato = usuario.getContato();
            if (contato != null) {
                ps.setLong(6, contato);
            } else {
                ps.setNull(6, java.sql.Types.BIGINT);
            }

            Long cnpj = usuario.getCNPJ();
            if (cnpj != null) {
                ps.setLong(7, cnpj);
            } else {
                ps.setNull(7, java.sql.Types.BIGINT);
            }

            ps.setBoolean(8, usuario.getHabilitado());
            ps.setString(9, usuario.getUsuarioTipo().name());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir usuario: " + e.getMessage(), e);
        }
    }

    public Usuario pesquisarEmail(String email) throws PersistenciaException {
        Usuario usuario = null;

        String sql = "SELECT id, nome, email, senha, naturalidade, endereco, contato, cnpj, habilitado, usuario_tipo "
                + "FROM usuarios WHERE email = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("naturalidade"));
                    usuario.setId(rs.getLong("id"));
                    usuario.setEndereco(rs.getString("endereco"));

                    usuario.setContato(rs.getObject("contato", Long.class));
                    usuario.setCNPJ(rs.getObject("cnpj", Long.class));

                    usuario.setHabilitado(rs.getBoolean("habilitado"));
                    String tipoStr = rs.getString("usuario_tipo");
                    usuario.setUsuarioTipo(UsuarioTipo.strTo(tipoStr));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar email: " + e.getMessage(), e);
        }

        return usuario;
    }

    @Override
    public Usuario pesquisar(Long id) throws PersistenciaException {
        Usuario usuario = null;

        String sql = "SELECT id, nome, email, senha, naturalidade, endereco, contato, habilitado, usuario_tipo "
                + "FROM usuarios WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // cria temporariamente uma instancia de usuario, para poder encontrar se ja tem
                                 // o mesmo login no bd
                    usuario = new Usuario(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("naturalidade"));
                    usuario.setId(rs.getLong("id"));
                    usuario.setEndereco(rs.getString("endereco"));
                    usuario.setContato(rs.getLong("contato"));
                    usuario.setHabilitado(rs.getBoolean("habilitado"));
                    String tipoStr = rs.getString("usuario_tipo");
                    usuario.setUsuarioTipo(UsuarioTipo.strTo(tipoStr));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar pelo id: " + e.getMessage(), e);
        }

        return usuario;
    }

    public List<Usuario> listarUsuarios() throws PersistenciaException {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT id, nome, email, senha, naturalidade, endereco, contato, habilitado, usuario_tipo "
                + "FROM usuarios";

        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("naturalidade"));

                usuario.setId(rs.getLong("id"));
                usuario.setEndereco(rs.getString("endereco"));
                usuario.setContato(rs.getLong("contato"));
                usuario.setHabilitado(rs.getBoolean("habilitado"));
                String tipoStr = rs.getString("usuario_tipo");
                usuario.setUsuarioTipo(UsuarioTipo.strTo(tipoStr));

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar usuários: " + e.getMessage(), e);
        }

        return usuarios;
    }

  

    public List<Usuario> listarAtivos() throws PersistenciaException {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT id, nome, email, senha, naturalidade, endereco, contato, habilitado, usuario_tipo "
                + "FROM usuarios WHERE habilitado = TRUE";

        try (Connection conn = ConexaoBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("naturalidade"));

                usuario.setId(rs.getLong("id"));
                usuario.setEndereco(rs.getString("endereco"));
                usuario.setContato(rs.getLong("contato"));
                usuario.setHabilitado(rs.getBoolean("habilitado"));
                String tipoStr = rs.getString("usuario_tipo");
                usuario.setUsuarioTipo(UsuarioTipo.strTo(tipoStr));

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar pessoas ativas: " + e.getMessage(), e);
        }

        return usuarios;
    }

    @Override
    public void delete(Long id) throws PersistenciaException {
        Usuario usuario = pesquisar(id);
        if (usuario.getUsuarioTipo() == UsuarioTipo.ADMINISTRADOR) {
            throw new PersistenciaException("Não pode deletar administradores");
        }

        String sql = "UPDATE usuarios SET habilitado = false WHERE email = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getEmail());
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas == 0) { // verifica se alguma linha foi alterada
                throw new PersistenciaException("Usuario não encontrada para deletar");
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao desabilitar usuario: " + e.getMessage(), e);
        }
    }

}