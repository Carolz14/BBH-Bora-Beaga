package bbh.service;

import java.util.List;

import bbh.common.PersistenciaException;
import bbh.dao.RoteiroDAO;
import bbh.domain.Roteiro;

public class GestaoRoteirosService {

    private final RoteiroDAO roteiroDAO;

    public GestaoRoteirosService() {
        this.roteiroDAO = RoteiroDAO.getInstance();
    }

    public void salvarRoteiro(Roteiro roteiro) throws PersistenciaException {
        roteiroDAO.inserir(roteiro);
    }

    public void excluirRoteiro(Roteiro roteiro) throws PersistenciaException {
        roteiroDAO.excluir(roteiro.getId());
    }

    public List<Roteiro> pesquisarPorAutor(Long usuarioId) throws PersistenciaException {
        return roteiroDAO.pesquisarAutor(usuarioId);
    }

    public List<Roteiro> listarRoteiros() throws PersistenciaException {
        return roteiroDAO.listar();
    }

    public Roteiro pesquisarPorId(Long id) throws PersistenciaException {
        return roteiroDAO.pesquisar(id);
    }
}