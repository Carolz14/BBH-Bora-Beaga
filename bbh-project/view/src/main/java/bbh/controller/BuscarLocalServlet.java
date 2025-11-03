package bbh.controller;

import bbh.dao.LocalDAO;
import bbh.domain.Local;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BuscarLocalServlet extends HttpServlet {

    private LocalDAO localDAO = new LocalDAO();
    private Gson gson = new Gson();

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");

        if (nome != null && nome.length() >= 3) {
            List<Local> lista = localDAO.buscarPorNome(nome);
            String json = gson.toJson(lista);

            response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }
}
