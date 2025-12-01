package bbh.service;

import bbh.dao.UsuarioDAO;
import bbh.domain.Usuario;
import bbh.common.PersistenciaException;
import java.util.List;

public class GestaoUsuariosService {

    private final UsuarioDAO usuarioDAO;

    public GestaoUsuariosService() {
        this.usuarioDAO = UsuarioDAO.getInstance();
    }

    public Usuario pesquisarConta(String email, String senha) throws PersistenciaException {
        Usuario pessoa = usuarioDAO.pesquisarEmail(email);
        if (pessoa != null && pessoa.getSenha().equals(senha)) {
            return pessoa;
        }
        return null;
    }

    public void cadastrarUsuario(Usuario usuario) throws PersistenciaException {
        usuarioDAO.inserir(usuario);
    }

    public void excluir(Usuario usuario) throws PersistenciaException {
        usuarioDAO.delete(usuario.getId());
    }

    public List<Usuario> pesquisarAtivos() throws PersistenciaException {
        return usuarioDAO.listarAtivos();
    }

    public Usuario pesquisarPorId(Long id) throws PersistenciaException {
        return usuarioDAO.pesquisar(id);
    }

    public Usuario pesquisarPorEmail(String email) throws PersistenciaException {
        Usuario resultado = usuarioDAO.pesquisarEmail(email);
        return resultado;
    }

  public void atualizarPerfilEstabelecimento(Long id, String descricao, String imagemUrl) throws PersistenciaException {
        usuarioDAO.atualizarPerfilEstabelecimento(id, descricao, imagemUrl);
    }
}