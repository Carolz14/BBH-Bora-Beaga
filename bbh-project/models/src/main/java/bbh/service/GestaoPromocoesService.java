package bbh.service;

import bbh.dao.PromocaoDAO;
import bbh.domain.Promocao;
import bbh.common.PersistenciaException;
import java.util.List;

public class GestaoPromocoesService {

    private final PromocaoDAO dao;

    public GestaoPromocoesService() {
        this.dao = PromocaoDAO.getInstance();
    }

    public void cadastrarPromocao(Promocao promocao) throws PersistenciaException {
        dao.inserir(promocao);
    }

    public List<Promocao> listarPorEstabelecimento(Long idEstabelecimento) throws PersistenciaException {
        return dao.listarPorEstabelecimento(idEstabelecimento);
    }

    public void removerVinculo(Long idUsuario, Long idPromocao) throws PersistenciaException {
        dao.removerVinculo(idUsuario, idPromocao);
    }

    public Long buscarEstabelecimenoPorPromocao(Long idPromocao) throws PersistenciaException {
        return dao.buscarEstabelecimentoDaPromocao(idPromocao);
    }
}
