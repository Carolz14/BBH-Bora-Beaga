package bbh.controller;

import java.io.IOException;
import java.util.List;

import bbh.domain.Usuario;
import bbh.service.GestaoEstabelecimentosService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/EstabelecimentosController")
public class EstabelecimentosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            GestaoEstabelecimentosService pesquisarEstabelecimentos = new GestaoEstabelecimentosService();

            List<Usuario> estabelecimentos = pesquisarEstabelecimentos.listarEstabelecimentos();
            request.setAttribute("estabelecimentos", estabelecimentos);
          
            request.getSession().setAttribute("estabelecimentos", estabelecimentos);
            response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-estabelecimento.jsp");
          

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar estabelecimentos");
        }

    }

}