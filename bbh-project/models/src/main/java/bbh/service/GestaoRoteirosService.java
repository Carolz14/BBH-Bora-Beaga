package bbh.service;

import bbh.dao.RoteiroDAO;
import bbh.domain.Roteiro;
import bbh.domain.Usuario;

import java.util.List;

import bbh.common.PersistenciaException;

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

    public Usuario pesquisarPorId(Long id) throws PersistenciaException {
        return roteiroDAO.pesquisar(id);
    }
}