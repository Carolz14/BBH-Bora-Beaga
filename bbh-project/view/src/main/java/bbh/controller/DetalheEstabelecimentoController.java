package bbh.controller;

import java.io.IOException;

import bbh.common.PersistenciaException;
import bbh.domain.Avaliacao;
import bbh.domain.Usuario;
import bbh.service.AvaliacaoService;
import bbh.service.GestaoUsuariosService;
import bbh.service.MidiaAvaliacaoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/bbh/DetalheEstabelecimentoController")
public class DetalheEstabelecimentoController extends HttpServlet {
    private final AvaliacaoService avaliacaoService = new AvaliacaoService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-estabelecimento.jsp");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            GestaoUsuariosService service = new GestaoUsuariosService();
            Usuario estabelecimento = service.pesquisarPorId(id);
            double media = avaliacaoService.calcularMedia(id);
            List <Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesPorEstabelecimento(id);

            if (estabelecimento != null) {
                request.setAttribute("estabelecimento", estabelecimento);
                request.setAttribute("media", media);
                request.setAttribute("avaliacoes", avaliacoes);
                request.getRequestDispatcher("/jsps/turista/detalhe-estabelecimento.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-estabelecimento.jsp");
            }

        } catch (NumberFormatException | PersistenciaException e) {
            throw new ServletException("Erro ao buscar estabelecimento: " + e.getMessage(), e);
        }
    }

}