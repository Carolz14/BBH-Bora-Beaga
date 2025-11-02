package bbh.controller;

import bbh.domain.Tag;
import bbh.common.*;
import bbh.service.TagService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/estabelecimento/tags")
public class GerenciarTagsServlet extends HttpServlet {

    private final TagService tagService = new TagService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Tag> todasAsTags = tagService.listarTodasAsTags();
            request.setAttribute("tags", todasAsTags);
            Long idUsuario = getIdUsuario(request);
            if (idUsuario != null) {
                List<Tag> tagsSelecionadas = tagService.listarTagsDoUsuario(idUsuario);
                request.setAttribute("tagsSelecionadas", tagsSelecionadas);
            }

            request.getRequestDispatcher("/jsps/estabelecimento/tags.jsp")
                    .forward(request, response);

        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao carregar tags: " + e.getMessage(), e);
        } catch(NaoEncontradoException e){
            throw new ServletException("Erro ao tentar acessar o id do usuário");
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            Long idUsuario = getIdUsuario(request);
            if (idUsuario == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuário não autenticado");
                return;
            }

            String[] tagIdsArray = request.getParameterValues("tagIds");

            List<Long> idsTags = new ArrayList<>();
            if (tagIdsArray != null) {
                for (String s : tagIdsArray) {
                    try {
                        idsTags.add(Long.parseLong(s));
                    } catch (NumberFormatException ex) {
                    }
                }
            }
        } catch (NaoEncontradoException e) {
            throw new ServletException("o id do usuário não foi encontrado");
        }
    }

    private Long getIdUsuario(HttpServletRequest request) throws NaoEncontradoException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object obj = session.getAttribute("usuarioId");
            if (obj instanceof Long)
                return (Long) obj;
            if (obj instanceof Integer)
                return ((Integer) obj).longValue();
            if (obj instanceof String) {
                try {
                    return Long.parseLong((String) obj);
                } catch (NumberFormatException ignore) {
                }
            }
        }
        throw new NaoEncontradoException("O id do usuário não foi encontrado");
    }
}
