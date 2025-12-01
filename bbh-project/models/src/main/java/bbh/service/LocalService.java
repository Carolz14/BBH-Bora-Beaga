package bbh.service;

import java.util.List;

import bbh.common.PersistenciaException;
import bbh.dao.LocalDAO;
import bbh.domain.Local;

public class LocalService {

    private final LocalDAO dao = LocalDAO.getInstance();

    public List<Local> pesquisarPorNome(String nome) throws PersistenciaException {
        if (nome == null || nome.trim().length() < 3) {
            throw new PersistenciaException("Digite pelo menos 3 caracteres para pesquisar.");
        }
        return dao.buscarPorNome(nome.trim());
    }

    public Local pesquisarPorId(Long id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("ID do local não pode ser nulo.");
        }
        return dao.buscarPorId(id);
    }

  
}
