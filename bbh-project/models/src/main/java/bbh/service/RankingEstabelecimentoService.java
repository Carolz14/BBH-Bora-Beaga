package bbh.service;

import bbh.common.PersistenciaException;
import bbh.dao.RankingEstabelecimentoDAO;
import bbh.domain.RankingEstabelecimento;

import java.util.List;

public class RankingEstabelecimentoService {

    private final RankingEstabelecimentoDAO rankingEstabelecimentoDAO;

    public RankingEstabelecimentoService() {
        this.rankingEstabelecimentoDAO = new RankingEstabelecimentoDAO();
    }

    public List<RankingEstabelecimento> buscarRankings(String tipoDoRanking,
            int limiteDeBuscas,
            int numeroAvaliacoesMin,
            int filtroDias) throws PersistenciaException {
        String tipo = tipoDoRanking == null ? "medias" : tipoDoRanking.trim().toLowerCase();
        switch (tipo) {
            case "mais_visitacoes":
            case "mais-visitacoes":
                return rankingEstabelecimentoDAO.listarComOsMaioresNumerosDeVisitacoes(limiteDeBuscas,numeroAvaliacoesMin, filtroDias);

            case "media":
            case "medias":
            default:
                return rankingEstabelecimentoDAO.listarComAsMelhoresMedias(limiteDeBuscas, numeroAvaliacoesMin, filtroDias);
        }
    }
}
