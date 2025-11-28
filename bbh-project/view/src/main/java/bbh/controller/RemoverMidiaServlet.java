package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.service.MidiaAvaliacaoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/midia/deletar")
public class RemoverMidiaServlet extends BaseServlet {

    private final MidiaAvaliacaoService service = new MidiaAvaliacaoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' (id da mídia) é obrigatório.");
            return;
        }

        long idMidia;
        try {
            idMidia = Long.parseLong(idParam);
            if (idMidia <= 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' inválido, menor ou igual à zero.");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' inválido: formato incorreto.");
            return;
        }

        try {
            service.removerMidia(idMidia);
            String accept = req.getHeader("Accept");
            String ajax = req.getHeader("X-Requested-With");

            if ("XMLHttpRequest".equalsIgnoreCase(ajax) || (accept != null && accept.contains("application/json"))) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return;
            }

            String referer = req.getHeader("Referer");
            if (referer != null && !referer.isBlank()) {
                resp.sendRedirect(referer);
            } else {
                resp.sendRedirect(req.getContextPath() + "/");
            }

        } catch (PersistenciaException pe) {
            System.err.println("Erro ao remover mídia " + pe.getMessage());
            pe.printStackTrace();

            String ajax = req.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equalsIgnoreCase(ajax)) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("text/plain;charset=UTF-8");
                resp.getWriter().write("Erro ao remover mídia: " + pe.getMessage());
                return;
            }

            // caso contrário, lança ServletException para ver no error page
            throw new ServletException("Erro ao remover mídia. " + pe.getMessage(), pe);
        }
    }
}
