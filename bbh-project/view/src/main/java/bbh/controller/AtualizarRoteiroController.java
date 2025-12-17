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

@WebServlet("/bbh/AtualizarRoteiroController")
public class AtualizarRoteiroController extends HttpServlet {
    private GestaoRoteirosService roteiroService = new GestaoRoteirosService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Long id = Long.parseLong(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");
            String paradasTexto = request.getParameter("paradasTexto");

            HttpSession session = request.getSession();
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
            Roteiro roteiro = new Roteiro();
            roteiro.setId(id);
            roteiro.setNome(nome);
            roteiro.setDescricao(descricao);
            roteiro.setParadas(paradasTexto);
            roteiro.setUsuarioId(usuarioLogado.getId());

            roteiroService.atualizarRoteiro(roteiro);

            response.sendRedirect(request.getContextPath() + "/bbh/ListarRoteiroController");

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("erroLogin", "Ocorreu um erro inesperado: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/jsps/turista/lista-roteiros.jsp");
            rd.forward(request, response);
        }

    }
}