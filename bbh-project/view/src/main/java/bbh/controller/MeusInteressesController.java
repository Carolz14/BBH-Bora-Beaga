package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.Local;
import bbh.domain.Usuario;
import bbh.service.GestaoInteresseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/bbh/MeusInteressesController")
public class MeusInteressesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        try {
            GestaoInteresseService service = new GestaoInteresseService();
            List<Local> meusInteresses = service.listar(usuario.getId());
            request.setAttribute("listaLocais", meusInteresses);
            request.getRequestDispatcher("/jsps/turista/lista-interesses.jsp").forward(request, response);

        } catch (PersistenciaException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/bbh/feed");
        }
    }
}