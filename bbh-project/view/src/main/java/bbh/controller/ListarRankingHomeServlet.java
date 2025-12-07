package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.RankingEstabelecimento;
import bbh.service.RankingEstabelecimentoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/rankingHome/listar")
public class ListarRankingHomeServlet extends BaseServlet {

    private final RankingEstabelecimentoService rankingService = new RankingEstabelecimentoService();

    private final int limiteBuscas = 2;
    private final int avalicoesMinimasNecessarias = 1;
    private final int janelaDeTempoDias = 7;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<RankingEstabelecimento> topMedias = rankingService.buscarRankings("medias", limiteBuscas,
                    avalicoesMinimasNecessarias, janelaDeTempoDias);

            List<RankingEstabelecimento> topVisitacoes = rankingService.buscarRankings("visitacoes", limiteBuscas,
                    avalicoesMinimasNecessarias, janelaDeTempoDias);
            req.setAttribute("topMedias", topMedias);
            req.setAttribute("topVisitacoes", topVisitacoes);
            req.getRequestDispatcher("/jsps/turista/ranking-home.jsp").include(req, resp);

        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao carregar rankings para a home: " + e.getMessage(), e);
        }
    }
}
