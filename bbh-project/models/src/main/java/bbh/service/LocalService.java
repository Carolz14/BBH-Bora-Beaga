package bbh.service;

import bbh.common.PersistenciaException;
import bbh.dao.LocalDAO;
import bbh.domain.Local;

import java.sql.SQLException;
import java.util.List;

public class LocalService {

    private final LocalDAO dao = LocalDAO.getInstance();

    /**
     * Pesquisa locais pelo nome.
     * @throws SQLException 
     */
    public List<Local> pesquisarPorNome(String nome) throws PersistenciaException, SQLException {
        if (nome == null || nome.trim().length() < 3) {
            throw new PersistenciaException("Digite pelo menos 3 letras para pesquisar.");
        }
        return dao.buscarPorNome(nome.trim());
    }
}
