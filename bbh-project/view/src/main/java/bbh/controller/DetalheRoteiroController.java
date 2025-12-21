package bbh.controller;

import java.io.IOException;
import java.util.List;

import bbh.common.PersistenciaException;
import bbh.domain.Comentario;
import bbh.domain.Roteiro;
import bbh.domain.Usuario;
import bbh.service.GestaoAvaliacaoRoteiroService;
import bbh.service.GestaoComentarioRoteiroService;
import bbh.service.GestaoRoteirosService;
import bbh.service.GestaoUsuariosService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/bbh/DetalheRoteiroController")
public class DetalheRoteiroController extends HttpServlet {

    private GestaoRoteirosService roteiroService = new GestaoRoteirosService();
    private GestaoUsuariosService usuarioService = new GestaoUsuariosService();
    private GestaoAvaliacaoRoteiroService avaliacaoService = new GestaoAvaliacaoRoteiroService();
    private GestaoComentarioRoteiroService comentarioService = new GestaoComentarioRoteiroService(); 

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
                double media = avaliacaoService.mediaRoteiro(id);
                String mediaFormatada = String.format("%.1f", media);
                HttpSession session = request.getSession(false);
                Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

               int  minhaNota = avaliacaoService.pesquisarNota(id, usuarioLogado.getId());

               List<Comentario>  comentarios  = comentarioService.listarComentarios(id);
               request.setAttribute("roteiro", roteiro);
                request.setAttribute("autor", autor);
                request.setAttribute("mediaNota", mediaFormatada); 
                request.setAttribute("minhaNota", minhaNota);  
                request.setAttribute("comentarios", comentarios);  
                RequestDispatcher rd = request.getRequestDispatcher("/jsps/turista/detalhe-roteiro.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-roteiros.jsp");
            }

        } catch (NumberFormatException | PersistenciaException e) {
            throw new ServletException("Erro ao buscar roteiro: " + e.getMessage(), e);
        }
    }

}