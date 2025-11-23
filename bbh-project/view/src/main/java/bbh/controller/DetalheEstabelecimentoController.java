package bbh.controller;

import bbh.domain.Usuario;
import bbh.domain.Avaliacao;
import bbh.service.GestaoUsuariosService;
import bbh.service.AvaliacaoService;
import bbh.common.PersistenciaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/bbh/DetalheEstabelecimentoController")
public class DetalheEstabelecimentoController extends HttpServlet {

    private final GestaoUsuariosService usuariosService = new GestaoUsuariosService();
    private final AvaliacaoService avaliacaoService = new AvaliacaoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/jsps/turista/lista-estabelecimento.jsp");
            return;
        }

        try {
            long id = Long.parseLong(idParam);

            Usuario estabelecimento = usuariosService.pesquisarPorId(id);
            if (estabelecimento == null) {
                resp.sendRedirect(req.getContextPath() + "/jsps/turista/lista-estabelecimento.jsp");
                return;
            }

            List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesPorEstabelecimento(id);
            double media = avaliacaoService.calcularMedia(id);

            req.setAttribute("estabelecimento", estabelecimento);
            req.setAttribute("avaliacoes", avaliacoes);
            req.setAttribute("media", media);

            req.getRequestDispatcher("/jsps/turista/detalhe-estabelecimento.jsp").forward(req, resp);

        } catch (NumberFormatException | PersistenciaException e) {
            throw new ServletException("Erro ao carregar detalhe do estabelecimento.", e);
        }
    }
}
