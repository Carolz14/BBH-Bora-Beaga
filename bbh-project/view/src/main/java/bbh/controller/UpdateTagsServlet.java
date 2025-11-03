package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.common.NaoEncontradoException;
import bbh.service.TagService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UpdateTagsServlet", urlPatterns = { "/estabelecimento/tags/update" })
public class UpdateTagsServlet extends BaseServlet {

    private final TagService tagService = new TagService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long idUsuario;
        try {
            idUsuario = getIdUsuario(request);
        } catch (NaoEncontradoException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuário não autenticado");
            return;
        }

        String adicionar = request.getParameter("adicionarTagId");
        String remover = request.getParameter("removerTagId");
        List<Long> idsParaAdicionar = new ArrayList<>();
        List<Long> idsParaRemover = new ArrayList<>();

        if (adicionar != null && !adicionar.isBlank()) {
            try {
                idsParaAdicionar.add(Long.parseLong(adicionar.trim()));
            } catch (NumberFormatException ignore) {
            }
        }
        if (remover != null && !remover.isBlank()) {
            try {
                idsParaRemover.add(Long.parseLong(remover.trim()));
            } catch (NumberFormatException ignore) {
            }
        }

        if (idsParaAdicionar.isEmpty() && idsParaRemover.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nenhuma tag válida informada.");
            return;
        }

        try {
            if (!idsParaRemover.isEmpty()) {
                tagService.removerTagsEstabelecimento(idUsuario, idsParaRemover);
            }
            if (!idsParaAdicionar.isEmpty()) {
                tagService.adicionarTagsEstabelecimento(idUsuario, idsParaAdicionar);
            }
            response.sendRedirect(request.getContextPath() + "/estabelecimento/tags?sucesso=1");
        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao atualizar tags: " + e.getMessage(), e);
        }
    }
} 