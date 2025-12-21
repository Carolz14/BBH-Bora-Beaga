package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.Usuario;
import bbh.service.AvaliacaoService;
import bbh.service.GestaoUsuariosService;
import bbh.service.VisitaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/bbh/painel")
public class PainelEstabelecimentoController extends HttpServlet {

    private final AvaliacaoService avaliacaoService = new AvaliacaoService();
    private final GestaoUsuariosService gestaoUsuariosService = new GestaoUsuariosService();
    private final VisitaService visitaService = new VisitaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario sessaoUsuario = (Usuario) request.getSession().getAttribute("usuario");
        if (sessaoUsuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Long idEstabelecimento = sessaoUsuario.getId();

            Usuario estabelecimento = gestaoUsuariosService.pesquisarPorId(idEstabelecimento);
            if (estabelecimento == null) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }

            String categoria = "Estabelecimento";

            double media = 0.0;
            int totalAvaliacoes = 0;
            int totalVisitas = 0;

            try {
                media = avaliacaoService.calcularMedia(idEstabelecimento, categoria);
                totalAvaliacoes = avaliacaoService
                        .getNumeroAvaliacoesPorEstabelecimento(idEstabelecimento, categoria);
            } catch (PersistenciaException e) {
                System.err.println("Erro ao buscar avaliações: " + e.getMessage());
            }

            try {
              
                totalVisitas = visitaService.contarVisitasDoMes(idEstabelecimento);
            } catch (PersistenciaException e) {
                System.err.println("Erro ao contar visitas do mês: " + e.getMessage());
            }

            request.setAttribute("estabelecimento", estabelecimento);
            request.setAttribute("mediaAvaliacoes", media);
            request.setAttribute("totalAvaliacoes", totalAvaliacoes);
            request.setAttribute("totalVisitas", totalVisitas);

            request.getRequestDispatcher("/jsps/estabelecimento/painel.jsp")
                   .forward(request, response);

        } catch (PersistenciaException e) {
            throw new ServletException(
                "Erro ao carregar painel do estabelecimento: " + e.getMessage(), e
            );
        }
    }
}
