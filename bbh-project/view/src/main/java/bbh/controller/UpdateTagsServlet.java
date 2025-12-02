package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.service.TagService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UpdateTagsServlet", urlPatterns = {"/estabelecimento/tags/update"})
public class UpdateTagsServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(UpdateTagsServlet.class.getName());

    protected TagService createTagService() {
        return new TagService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TagService tagService = createTagService();

        try {
            Long idUsuario = getIdUsuario(request);
            String adicionarParam = request.getParameter("adicionarTagId");
            String removerParam = request.getParameter("removerTagId");

            List<Long> toAdd = parseIds(adicionarParam);
            List<Long> toRemove = parseIds(removerParam);

            if (!toAdd.isEmpty()) {
                tagService.adicionarTagsEstabelecimento(idUsuario, toAdd);
            }
            if (!toRemove.isEmpty()) {
                tagService.removerTagsEstabelecimento(idUsuario, toRemove);
            }

            response.sendRedirect(request.getContextPath() + "/estabelecimento/tags?sucesso=1");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar tags", e);
            if (e instanceof PersistenciaException) {
                throw new ServletException("Erro ao atualizar tags: problema de persistência.", e);
            }
            throw new ServletException("Erro ao atualizar tags.", e);
        }
    }

    private List<Long> parseIds(String string ) {
        List<Long> resultados = new ArrayList<>();
        if (string == null) {
            return resultados;
        }
        string = string.trim();
        if (string.isEmpty()) {
            return resultados;
        }

        String[] parts = string.split(",");
        for (String p : parts) {
            if (p == null) {
                continue;
            }
            p = p.trim();
            if (p.isEmpty()) {
                continue;
            }
            try {
                long v = Long.parseLong(p);
                resultados.add(v);
            } catch (NumberFormatException ex) {

                LOGGER.log(Level.FINE, "id inválido ignorado: " + p);
            }
        }
        return resultados;
    }
}
