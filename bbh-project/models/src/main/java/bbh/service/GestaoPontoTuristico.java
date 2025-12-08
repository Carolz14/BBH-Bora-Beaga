package bbh.service;

import bbh.dao.PontoTuristicoDAO;
import bbh.domain.PontoTuristico;
import bbh.common.PersistenciaException;
import java.util.List;

public class GestaoPontoTuristico {

    private final PontoTuristicoDAO dao;

    public GestaoPontoTuristico() {
        this.dao = PontoTuristicoDAO.getInstance();
    }

    public void cadastrar(PontoTuristico pt) throws PersistenciaException {
        dao.inserir(pt);
    }
    
    public void atualizar(PontoTuristico pt) throws PersistenciaException {
        dao.atualizar(pt);
    }

    public void excluir(Long id) throws PersistenciaException {
        dao.delete(id);
    }

    public List<PontoTuristico> listarTodos() throws PersistenciaException {
        return dao.listarAtivos();
    }
    
    public PontoTuristico buscarPorId(Long id) throws PersistenciaException {
        return dao.pesquisar(id);
    }
}