package bbh.service;

import bbh.dao.VisitaDAO;
import bbh.common.PersistenciaException;

public class VisitaService {

    private final VisitaDAO visitaDAO;

    public VisitaService() {
        this.visitaDAO = new VisitaDAO();
    }

    public int contarVisitasPorEstabelecimento(long idEstabelecimento) throws PersistenciaException {
        return visitaDAO.contarVisitasPorEstabelecimento(idEstabelecimento);
    }

    public int contarVisitasDoMes(long idEstabelecimento) throws PersistenciaException {
        return visitaDAO.contarVisitasDoMes(idEstabelecimento);
    }

    public void registrarVisita(long idEstabelecimento) throws PersistenciaException {
        visitaDAO.inserirVisita(idEstabelecimento);
    }
}