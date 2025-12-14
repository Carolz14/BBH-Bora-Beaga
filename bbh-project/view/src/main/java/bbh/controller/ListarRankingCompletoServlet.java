package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.RankingEstabelecimento;
import bbh.service.RankingEstabelecimentoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/rankingCompleto/listar")
public class ListarRankingCompletoServlet extends BaseServlet {
    private final RankingEstabelecimentoService rankingEstabelecimentoService = new RankingEstabelecimentoService();
    private final int limiteBuscas = 10;
    private final int avalicoesMinimasNecessarias = 3;
    private int janelaDeTempoDias;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String metric = req.getParameter("metric");
        if (metric == null || metric.isBlank()) {
            throw new ServletException(
                    "Erro: não foi possivel acessar o parâmetro metric para a exibição dos rankings");
        }
        try {
            if ("medias".equals(metric)) {
                janelaDeTempoDias = 365;
                List<RankingEstabelecimento> topMedias = rankingEstabelecimentoService.buscarRankings(metric,
                        limiteBuscas, avalicoesMinimasNecessarias, janelaDeTempoDias);
                req.setAttribute("topMedias", topMedias);
                req.getRequestDispatcher("/jsps/turista/ranking-media.jsp").forward(req, resp);
            }
            if ("visitacoes".equals(metric)) {
                janelaDeTempoDias = 7;
                List<RankingEstabelecimento> topVisitacoes = rankingEstabelecimentoService.buscarRankings(metric,
                        limiteBuscas, avalicoesMinimasNecessarias, janelaDeTempoDias);
                req.setAttribute("topVisitacoes", topVisitacoes);
                req.getRequestDispatcher("/jsps/turista/ranking-visitacoes.jsp").forward(req, resp);
            }
        } catch (PersistenciaException e) {
            throw new ServletException(
                    "Erro ao tentar exibir os rankings completos de visitação ou de melhor nota média");

        }
    }
}
