package bbh.controller;

import bbh.domain.Avaliacao;
import bbh.service.AvaliacaoService;
import bbh.common.PersistenciaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/avaliacao/listar")
public class ListarAvaliacaoServlet extends BaseServlet {

    private final AvaliacaoService service = new AvaliacaoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stringEstabelecimento= req.getParameter("id");
        try {
            if (stringEstabelecimento == null || stringEstabelecimento.isBlank()) {
                throw new ServletException("Erro, id do estabelecimento não foi encontrado");
            }

            long idEstabelecimento = Long.parseLong(stringEstabelecimento);
            List<Avaliacao> listaAvaliacoes = service.buscarAvaliacoesPorEstabelecimento(idEstabelecimento);
            double media = service.calcularMedia(idEstabelecimento);
            req.setAttribute("lista-avaliacoes", listaAvaliacoes);
            req.setAttribute("media", media);
            req.setAttribute("estabelecimentoId", idEstabelecimento);
            req.getRequestDispatcher("/WEB-INF/avaliacoes/avaliacoes.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            throw new ServletException("Erro ao listar as avaliações do estabelecimento, problema de formato de parâmetros." + e.getMessage());
        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao listar as avaliações do estabelecimento, problema de persistência." + e.getMessage());
        }
    }
}
