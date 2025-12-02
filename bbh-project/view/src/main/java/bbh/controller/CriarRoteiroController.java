package bbh.controller;

import java.io.IOException;

import bbh.domain.Roteiro;
import bbh.domain.Usuario;
import bbh.service.GestaoRoteirosService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bbh/CriarRoteiroController")
public class CriarRoteiroController extends HttpServlet {
    private GestaoRoteirosService roteiroService = new GestaoRoteirosService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String paradasTexto = request.getParameter("paradasTexto");
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
        try {
           


            Roteiro roteiro = new Roteiro(nome, descricao);
            roteiro.setParadas(paradasTexto);
            roteiro.setUsuarioId(usuarioLogado.getId());
            roteiro.setHabilitado(true);

        roteiroService.salvarRoteiro(roteiro);

            response.sendRedirect(request.getContextPath() + "/bbh/ListarRoteiroController");

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("erroLogin", "Ocorreu um erro inesperado: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/jsps/turista/lista-roteiros.jsp");
            rd.forward(request, response);
        }

    }
}