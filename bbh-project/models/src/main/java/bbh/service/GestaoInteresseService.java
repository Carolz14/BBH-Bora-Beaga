package bbh.service;

import bbh.common.PersistenciaException;
import bbh.dao.InteresseDAO;
import java.util.ArrayList;
import java.util.List;
import bbh.domain.Local;

public class GestaoInteresseService {
    private final InteresseDAO dao;

    public GestaoInteresseService() {
        this.dao = InteresseDAO.getInstance();
    }
    
    public boolean alternarInteresse(Long idTurista, Long idItem, String tipoItem) throws PersistenciaException{
        return dao.alternarInteresse(idTurista, idItem, tipoItem);
    }
    
    public void adicionar(Long idTurista, Long idItem, String tipoItem) throws PersistenciaException{
        dao.adicionar(idTurista, idItem, tipoItem);
    }
    
    public void remover(Long idTurista, Long idItem, String tipoItem) throws PersistenciaException{
        dao.remover(idTurista, idItem, tipoItem);
    }
    
    public boolean jaEstaSalvo(Long idTurista, Long idItem, String tipoItem) throws PersistenciaException{
        return dao.jaEstaSalvo(idTurista, idItem, tipoItem);
    }
    
    public List<Local> listar(Long idTurista) throws PersistenciaException{
        return dao.listarMeusInteresses(idTurista);
    }
    
    public List<String> carregarIdsSalvos(Long idTurista) throws PersistenciaException{
        return dao.carregarIdsSalvos(idTurista);
    }
}
