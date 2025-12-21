package bbh.controller;

import bbh.domain.Usuario;
import bbh.service.GestaoInteresseService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/bbh/InteresseController")
public class InteresseControler extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String idItemStr = request.getParameter("id");
        String categoria = request.getParameter("categoria"); // "Estabelecimento" ou "Ponto Turístico"
        String tipoBanco = "PONTO_TURISTICO";
        if (categoria != null && categoria.equalsIgnoreCase("Estabelecimento")) {
            tipoBanco = "ESTABELECIMENTO";
        }

        if (idItemStr != null) {
            try {
                Long idItem = Long.valueOf(idItemStr);
                GestaoInteresseService service = new GestaoInteresseService();
                service.alternarInteresse(usuario.getId(), idItem, tipoBanco);
                session.setAttribute("idsInteresse", service.carregarIdsSalvos(usuario.getId()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Ve se volta pro feed ou continua
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath() + "/bbh/feed");
        
        
    }
   
}
