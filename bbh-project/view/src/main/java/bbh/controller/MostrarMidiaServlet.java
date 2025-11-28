package bbh.controller;

import bbh.domain.MidiaAvaliacao;
import bbh.service.MidiaAvaliacaoService;
import bbh.common.PersistenciaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.*;

@WebServlet("/midia/serve")
public class MostrarMidiaServlet extends HttpServlet {

    private final MidiaAvaliacaoService service = new MidiaAvaliacaoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' é obrigatório.");
            return;
        }

        long idMidia;
        try {
            idMidia = Long.parseLong(idParam);
            if (idMidia <= 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' inválido: formato incorreto.");
            return;
        }

        try {
            MidiaAvaliacao midia = service.buscarPorId(idMidia);
            if (midia == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Mídia não encontrada.");
                return;
            }

            String raizStr = System.getProperty("bbh.upload.dir");
            if (raizStr == null) {
                String home = System.getProperty("user.home");
                String sep = System.getProperty("file.separator");
                raizStr = home + sep + "bbh-uploads";
            }
            Path raiz = service.getRaizUpload();
            Path file = raiz.resolve(midia.getCaminho()).normalize();

            if (!file.startsWith(raiz)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Caminho inválido.");
                return;
            }

            if (!Files.exists(file) || !Files.isReadable(file) || Files.isDirectory(file)) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Arquivo não encontrado.");
                return;
            }
            String contentType = midia.getMime();
            if (contentType == null || contentType.isBlank()) {
                contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
            }

            resp.setContentType(contentType);
            long size = Files.size(file);
            if (size <= Integer.MAX_VALUE) {
                resp.setContentLength((int) size);
            } else {
                resp.setContentLengthLong(size);
            }

            String filename = midia.getNomeOriginal();
            if (filename == null || filename.isBlank()) {
                filename = midia.getNomeArmazenado();
            }
            resp.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");

            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            resp.setHeader("Pragma", "no-cache");
            resp.setHeader("Expires", "0");

            try (OutputStream out = resp.getOutputStream();
                    InputStream in = Files.newInputStream(file, StandardOpenOption.READ)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                out.flush();
            }

        } catch (PersistenciaException pe) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar mídia: " + pe.getMessage());
        } catch (IOException ioe) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao ler arquivo: " + ioe.getMessage());
        }
    }
}
