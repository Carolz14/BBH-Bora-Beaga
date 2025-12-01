package bbh.controller;

import java.io.IOException;
import java.util.List;

import bbh.domain.Roteiro;
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;

import bbh.service.GestaoRoteirosService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bbh/ListarRoteiroController")
public class ListarRoteiroController extends HttpServlet {
    private GestaoRoteirosService roteiroService = new GestaoRoteirosService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();

            Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

            if (usuarioLogado == null) {
                response.sendRedirect(request.getContextPath() + "/jsps/login.jsp");
                return;
            }
            Long usuarioId = usuarioLogado.getId();

            if (usuarioLogado.getUsuarioTipo() == UsuarioTipo.TURISTA) {

                List<Roteiro> todosRoteiros = roteiroService.listarRoteiros();
                request.setAttribute("todosRoteiros", todosRoteiros);
                request.getSession().setAttribute("todosRoteiros", todosRoteiros);

            } else if (usuarioLogado.getUsuarioTipo() == UsuarioTipo.GUIA) {

                List<Roteiro> meusRoteiros = roteiroService.pesquisarPorAutor(usuarioId);
                List<Roteiro> outrosRoteiros = roteiroService.pesquisarOutros(usuarioId);
                request.setAttribute("meusRoteiros", meusRoteiros);
                request.setAttribute("outrosRoteiros", outrosRoteiros);
                request.getSession().setAttribute("meusRoteiros", meusRoteiros);
                request.getSession().setAttribute("outrosRoteiros", outrosRoteiros);
            }

            response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-roteiros.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar roteiros");
        }

    }

}