package bbh.service;

import bbh.dao.PromocaoDAO;
import bbh.domain.Promocao;
import bbh.common.PersistenciaException;

public class GestaoPromocoesService {

    private final PromocaoDAO dao;

    public GestaoPromocoesService() {
        this.dao = PromocaoDAO.getInstance();
    }

    public void cadastrarPromocao(Promocao promocao) throws PersistenciaException {
        dao.inserir(promocao);
    }

    public void excluirPromocao(Long id) throws PersistenciaException {
        dao.delete(id);
    }

    public Promocao pesquisar(Long id) throws PersistenciaException {
        return dao.pesquisar(id);
    }
}
