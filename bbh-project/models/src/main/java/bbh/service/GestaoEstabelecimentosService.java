package bbh.service;

import java.util.List;

import bbh.common.PersistenciaException;
import bbh.dao.EstabelecimentoDAO;
import bbh.domain.Usuario;

public class GestaoEstabelecimentosService {
    private final EstabelecimentoDAO estabelecimentoDAO;

    public GestaoEstabelecimentosService() {
        this.estabelecimentoDAO = EstabelecimentoDAO.getInstance();
    }

    public List<Usuario> listarEstabelecimentos() throws PersistenciaException {
        return EstabelecimentoDAO.getInstance().listarEstabelecimentos();
    }
}
