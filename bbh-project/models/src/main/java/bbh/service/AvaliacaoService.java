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

    public Avaliacao inserirAvaliacao(Avaliacao avaliacao) throws PersistenciaException {
        if (avaliacao == null) {
            throw new PersistenciaException("Avaliação não pode ser nula");
        }
        if (avaliacao.getNotaAvaliacao() < 1 || avaliacao.getNotaAvaliacao() > 5) {
            throw new PersistenciaException("A nota deve ser entre 1 e 5");
        }
        Avaliacao av = avaliacaoDAO.inserirAvaliacao(avaliacao);
        return av;
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

    public double calcularMedia(long idEstabelecimento, String categoria) throws PersistenciaException {
        return avaliacaoDAO.calcularNotaMedia(idEstabelecimento, categoria);
    }

    public List<Avaliacao> buscarAvaliacoesPorEstabelecimento(Long idEstabelecimento, String categoria) throws PersistenciaException {
        return avaliacaoDAO.buscarAvaliacoesPorEstabelecimento(idEstabelecimento, categoria);
    }

    public int getNumeroAvaliacoesPorEstabelecimento(Long idEstabelecimento, String categoria) throws PersistenciaException {
        List<Avaliacao> avaliacoes = buscarAvaliacoesPorEstabelecimento(idEstabelecimento, categoria);
        int numeroAvaliacoes = avaliacoes.size();
        return numeroAvaliacoes;
    }
    public Avaliacao buscarPorId(Long idAvaliacao) throws PersistenciaException{
        return avaliacaoDAO.buscarPorId(idAvaliacao);
    }
}
