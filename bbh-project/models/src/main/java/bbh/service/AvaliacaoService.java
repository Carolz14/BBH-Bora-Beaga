package bbh.service;

import bbh.dao.AvaliacaoDAO;
import bbh.domain.Avaliacao;
import bbh.common.PersistenciaException;

import java.util.List;

public class AvaliacaoService {

    private final AvaliacaoDAO avaliacaoDAO;

    public AvaliacaoService() {
        this.avaliacaoDAO = new AvaliacaoDAO();
    }

    public void inserirAvaliacao(Avaliacao avaliacao) throws PersistenciaException { 
        if (avaliacao == null) {
            throw new PersistenciaException("Avaliação não pode ser nula");
        }
        if (avaliacao.getNotaAvaliacao() < 1 || avaliacao.getNotaAvaliacao() > 5) {
            throw new PersistenciaException("A nota deve ser entre 1 e 5");
        }  
        avaliacaoDAO.inserirAvaliacao(avaliacao);
    }

    public void atualizarAvaliacao(Avaliacao avaliacao) throws PersistenciaException {

        if (avaliacao == null) {
            throw new PersistenciaException("Avaliação não pode ser nula");
        }

        if (avaliacao.getIdAvaliacao() <= 0) {
            throw new PersistenciaException("ID da avaliação inválido");
        }

        if (avaliacao.getNotaAvaliacao() < 1 || avaliacao.getNotaAvaliacao() > 5) {
            throw new PersistenciaException("A nota deve ser entre 1 e 5");
        }
        avaliacaoDAO.atualizarAvaliacao(avaliacao);
    }

    public void removerAvaliacao(long idAvaliacao) throws PersistenciaException {
        if (idAvaliacao <= 0) {
            throw new PersistenciaException("ID inválido para remoção");
        }
        avaliacaoDAO.removerAvaliacao(idAvaliacao);
    }

    public double calcularMedia(long idEstabelecimento) throws PersistenciaException {
        return avaliacaoDAO.calcularNotaMedia(idEstabelecimento);
    }
    public List<Avaliacao> buscarAvaliacoesPorEstabelecimento(Long idEstabelecimento) throws PersistenciaException{
        return avaliacaoDAO.buscarAvaliacoesPorEstabelecimento(idEstabelecimento);
    }
}
