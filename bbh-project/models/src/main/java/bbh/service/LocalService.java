package bbh.service;

import java.sql.SQLException;
import java.util.List;
import bbh.dao.LocalDAO;
import bbh.domain.Local;
import bbh.common.PersistenciaException;

public class LocalService {
    private final LocalDAO localDAO = LocalDAO.getInstance();

    public List<Local> pesquisarLocaisPorNome(String nome) throws PersistenciaException, SQLException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new PersistenciaException("Nome de pesquisa inválido.");
        }
        return localDAO.buscarPorNome(nome.trim());
    }
}
