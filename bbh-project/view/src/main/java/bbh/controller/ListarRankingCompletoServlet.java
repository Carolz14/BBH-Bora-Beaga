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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String metric = req.getParameter("metric");
        if (metric == null || metric.isBlank()) {
            throw new ServletException(
                    "Erro: não foi possivel acessar o parâmetro metric para a exibição dos rankings");
        }
        if (!"medias".equals(metric) && !"visitacoes".equals(metric)) {
            throw new ServletException("Erro: parâmetro inválido foi passado para metric");
        }

        int limit = parseIntOrDefault(req.getParameter("limit"), 10);
        int minRatings = parseIntOrDefault(req.getParameter("minRatings"), 3);
        int dias = parseIntOrDefault(req.getParameter("dias"), 7);
        double minNota = parseDoubleOrDefault(req.getParameter("minNota"), 0.0);
        try {
            if ("medias".equals(metric)) {
                List<RankingEstabelecimento> topMedias = rankingEstabelecimentoService.buscarRankings(
                        "medias", limit, minRatings, dias, minNota);
                req.setAttribute("topMedias", topMedias);
                req.setAttribute("limiteDeBuscas", limit);
                req.setAttribute("minRatings", minRatings);
                req.setAttribute("minNota", minNota);
                req.setAttribute("janelaDias", dias);
                req.getRequestDispatcher("/jsps/turista/ranking-media.jsp").forward(req, resp);
                return;
            }

            if ("visitacoes".equals(metric)) {
                List<RankingEstabelecimento> topVisitacoes = rankingEstabelecimentoService.buscarRankings(
                        "visitacoes", limit, minRatings, dias, minNota);
                req.setAttribute("topVisitacoes", topVisitacoes);
                req.setAttribute("limiteDeBuscas", limit);
                req.setAttribute("minRatings", minRatings);
                req.setAttribute("minNota", minNota);
                req.setAttribute("janelaDias", dias);
                req.getRequestDispatcher("/jsps/turista/ranking-visitacoes.jsp").forward(req, resp);
                return;
            }

        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao tentar exibir os rankings completos: " + e.getMessage(), e);
        }
    }

    private int parseIntOrDefault(String s, int def) throws ServletException {
        if (s == null || s.isBlank())
            return def;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ServletException("Erro de formato no parâemtro" + s);
        }
    }

    private double parseDoubleOrDefault(String s, double def) throws ServletException {
        if (s == null || s.isBlank())
            return def;
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new ServletException("Erro de formato no parâemtro" + s);
        }
    }
}