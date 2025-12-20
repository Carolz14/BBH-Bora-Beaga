package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.Avaliacao;
import bbh.domain.PontoTuristico;
import bbh.service.AvaliacaoService;
import bbh.service.GestaoPontoTuristicoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bbh/DetalhePontoTuristico")
public class DetalhePontoTuristicoController extends HttpServlet {
    private final AvaliacaoService avaliacaoService = new AvaliacaoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String categoria = "Ponto"; 
        
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Long id = Long.valueOf(idStr);
                GestaoPontoTuristicoService service = new GestaoPontoTuristicoService();       
                PontoTuristico ponto = service.buscarPorId(id);
                double media = avaliacaoService.calcularMedia(id, categoria);
                List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesPorEstabelecimento(id, categoria);
                request.setAttribute("ponto", ponto);
                request.setAttribute("media", media);
                request.setAttribute("avaliacoes", avaliacoes);
                
                request.getRequestDispatcher("/jsps/turista/detalhe-pontoturistico.jsp").forward(request, response);
                
            } catch (PersistenciaException | NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/bbh/feed");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/bbh/feed");
        }
    }
}