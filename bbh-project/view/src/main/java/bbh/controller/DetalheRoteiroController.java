package bbh.controller;

import java.io.IOException;

import bbh.common.PersistenciaException;
import bbh.domain.Roteiro;
import bbh.domain.Usuario;
import bbh.service.GestaoRoteirosService;
import bbh.service.GestaoUsuariosService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/DetalheRoteiroController")
public class DetalheRoteiroController extends HttpServlet {

    private GestaoRoteirosService roteiroService = new GestaoRoteirosService();
    private GestaoUsuariosService usuarioService = new GestaoUsuariosService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/turista/lista-roteiro.jsp");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
         
            Roteiro roteiro = roteiroService.pesquisarPorId(id);

            if (roteiro != null) {
                Usuario autor = usuarioService.pesquisarPorId(roteiro.getUsuarioId());
                request.setAttribute("roteiro", roteiro);
                request.setAttribute("autor", autor);
        
               response.sendRedirect(request.getContextPath() + "/jsps/turista/detalhe-roteiro.jsp?id=" + id); 
             
            } else {
                response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-roteiros.jsp");
            }

        } catch (NumberFormatException | PersistenciaException e) {
            throw new ServletException("Erro ao buscar roteiro: " + e.getMessage(), e);
        }
    }

}