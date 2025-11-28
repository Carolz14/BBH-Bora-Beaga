package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.MidiaAvaliacao;
import bbh.service.MidiaAvaliacaoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/midia/atualizar")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10L * 1024 * 1024, maxRequestSize = 12L * 1024 * 1024)
public class AtualizarMidiaServlet extends HttpServlet {
    private final MidiaAvaliacaoService service = new MidiaAvaliacaoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Part filePart = null;
        try {
            filePart = req.getPart("file");
        } catch (IllegalStateException e) {
            resp.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE,
                    "Upload muito grande, maior do que permitido.");
            return;
        } catch (ServletException | IOException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar multipart: " + e.getMessage());
            return;
        }

        if (filePart == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Campo 'file' não enviado, nulo.");
            return;
        }
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "id da mídia é obrigatório");
            return;
        }
        long idMidia;
        try {
            idMidia = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "id inválido");
            return;
        }

        try (InputStream input = filePart.getInputStream()) {
            MidiaAvaliacao atualizada = service.atualizarMidia(idMidia, input, filePart.getSubmittedFileName(),
                    filePart.getContentType(),
                    filePart.getSize());
            String referer = req.getHeader("Referer");
            if (referer != null && !referer.isBlank()) {
                resp.sendRedirect(referer);
            } else {
                resp.sendRedirect(req.getContextPath() + "/");
            }
        } catch (PersistenciaException pe) {
            throw new ServletException("Erro ao atualizar mídia: " + pe.getMessage(), pe);
        }
    }
}
