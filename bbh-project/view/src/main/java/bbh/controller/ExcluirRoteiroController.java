package bbh.controller;

import java.io.IOException;

import bbh.domain.Roteiro;
import bbh.service.GestaoRoteirosService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/ExcluirRoteiroController")
public class ExcluirRoteiroController extends HttpServlet {
    private GestaoRoteirosService roteiroService = new GestaoRoteirosService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Long roteiroId = Long.parseLong(request.getParameter("roteiroId"));

            Roteiro roteiro = roteiroService.pesquisarPorId(roteiroId);

            roteiroService.excluirRoteiro(roteiro);

          response.sendRedirect(request.getContextPath() + "/bbh/ListarRoteiroController");

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("erroLogin", "Ocorreu um erro inesperado: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/jsps/turista/lista-roteiros.jsp");
            rd.forward(request, response);
        }

    }
}