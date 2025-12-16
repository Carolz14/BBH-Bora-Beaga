package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.PontoTuristico;
import bbh.service.GestaoPontoTuristico;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bbh/DetalhePontoTuristico")
public class DetalhePontoTuristicoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Long id = Long.valueOf(idStr);
                GestaoPontoTuristico service = new GestaoPontoTuristico();       
                PontoTuristico ponto = service.buscarPorId(id);
                request.setAttribute("ponto", ponto);
                request.getRequestDispatcher("/jsps/turista/detalhe-pontoturistico.jsp").forward(request, response);
                
            } catch (PersistenciaException | NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/bbh/feed");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/bbh/feed");
        }
    }
}