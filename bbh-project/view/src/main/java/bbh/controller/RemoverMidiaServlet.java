package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.service.MidiaAvaliacaoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet("/midia/deletar")
public class RemoverMidiaServlet extends HttpServlet {

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

            String referer = req.getHeader("Referer");
            if (referer != null && !referer.isBlank()) {
                resp.sendRedirect(referer);
            } else {
                resp.sendRedirect(req.getContextPath() + "/");
            }

        } catch (PersistenciaException pe) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao remover mídia: " + pe.getMessage());
        }
    }
}
