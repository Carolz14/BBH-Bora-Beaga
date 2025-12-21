package bbh.controller;

import java.io.IOException;
import java.util.List;

import bbh.common.PersistenciaException;
import bbh.domain.Avaliacao;
import bbh.domain.Usuario;
import bbh.service.AvaliacaoService;
import bbh.service.GestaoUsuariosService;
import bbh.service.VisitaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/DetalheEstabelecimentoController")
public class DetalheEstabelecimentoController extends HttpServlet {
    private final AvaliacaoService avaliacaoService = new AvaliacaoService();
    private final VisitaService visitaService = new VisitaService();
    private final GestaoUsuariosService gestaoUsuariosService = new GestaoUsuariosService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String categoria = "Estabelecimento";

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-estabelecimento.jsp");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            Usuario estabelecimento = gestaoUsuariosService.pesquisarPorId(id);

            if (estabelecimento == null) {
                response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-estabelecimento.jsp");
                return;
            }
            try {
                visitaService.registrarVisita(id);
            } catch (PersistenciaException e) {
                System.err.println("Erro ao registrar visita para estabelecimento " + id + " : " + e.getMessage());
            }

            double media = avaliacaoService.calcularMedia(id, categoria);
            List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesPorEstabelecimento(id, categoria);

            request.setAttribute("estabelecimento", estabelecimento);
            request.setAttribute("media", media);
            request.setAttribute("avaliacoes", avaliacoes);
            request.setAttribute("categoria", categoria);

            request.getRequestDispatcher("/jsps/turista/detalhe-estabelecimento.jsp").forward(request, response);

        } catch (NumberFormatException | PersistenciaException e) {
            throw new ServletException("Erro ao buscar estabelecimento: " + e.getMessage(), e);
        }
    }
}
