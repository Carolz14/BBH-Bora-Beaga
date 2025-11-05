package bbh.service;

import java.util.List;

import bbh.common.PersistenciaException;
import bbh.dao.UsuarioDAO;
import bbh.domain.Usuario;

public class GestaoEstabelecimentosService {
    private final UsuarioDAO usuarioDAO;

    public GestaoEstabelecimentosService() {
        this.usuarioDAO = UsuarioDAO.getInstance();
    }

    public List<Usuario> listarEstabelecimentos() throws PersistenciaException {
        return UsuarioDAO.getInstance().listarEstabelecimentos();
    }
}
