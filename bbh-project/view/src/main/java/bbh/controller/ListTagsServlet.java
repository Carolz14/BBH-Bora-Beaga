package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.Tag;
import bbh.service.TagService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListTagsServlet", urlPatterns = {"/estabelecimento/tags"})
public class ListTagsServlet extends BaseServlet {

    private final TagService tagService = new TagService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Tag> todas = tagService.listarTodasAsTags();
            request.setAttribute("tags", todas);

            try {
                Long idUsuario = getIdUsuario(request);
                List<Tag> selecionadas = tagService.listarTagsDoUsuario(idUsuario);
                request.setAttribute("tagsSelecionadas", selecionadas);
            } catch (Exception e) {
            }
            request.getRequestDispatcher("/teste.jsp")
                    .forward(request, response);

        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao carregar tags: " + e.getMessage(), e);
        }
    }
}
