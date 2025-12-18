package bbh.controller;

import java.io.IOException;
import bbh.dao.AvaliacaoRoteiroDAO;
import bbh.common.PersistenciaException;
import bbh.domain.Roteiro;
import bbh.domain.Usuario;
import bbh.service.GestaoAvaliacaoRoteiroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bbh/AvaliarRoteiroController")
public class AvaliarRoteiroController extends HttpServlet {

  private GestaoAvaliacaoRoteiroService avaliarRoteiroService = new GestaoAvaliacaoRoteiroService();
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    HttpSession session = request.getSession();
Usuario usuario = (Usuario) session.getAttribute("usuario");
        try {
            Long roteiroId = Long.parseLong(request.getParameter("roteiroId"));
         
            int nota = Integer.parseInt(request.getParameter("nota")); 

            avaliarRoteiroService.avaliarRoteiro(roteiroId, usuario.getId(), nota);
          
               response.sendRedirect(request.getContextPath() + "/jsps/turista/detalhe-roteiro.jsp?id=" + roteiroId); 
            

        } catch ( PersistenciaException e) {
            throw new ServletException("Erro ao avaliar roteiro: " + e.getMessage(), e);
        }
    }
}
