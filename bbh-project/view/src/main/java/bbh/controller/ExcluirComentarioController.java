package bbh.controller;

import java.io.File;
import java.io.IOException;

import bbh.common.PersistenciaException;
import bbh.domain.Comentario;
import bbh.domain.Usuario;
import bbh.service.GestaoComentarioRoteiroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bbh/ExcluirComentarioController")

public class ExcluirComentarioController extends HttpServlet {
 private static final String UPLOAD_DIRECTORY = "C:/bbh_uploads/comentarios";
    private GestaoComentarioRoteiroService comentarioService = new GestaoComentarioRoteiroService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Long roteiroId = Long.parseLong(request.getParameter("roteiroId"));
            Long comentarioId = Long.parseLong(request.getParameter("comentarioId"));
            String texto = request.getParameter("texto");
            Comentario comentario = comentarioService.pesquisarComentario(comentarioId);

            if (comentario != null && comentario.getUsuarioId().equals(usuario.getId())) {

                if (comentario.getImagemUrl() != null && !comentario.getImagemUrl().isEmpty()) {
                    File arquivo = new File(UPLOAD_DIRECTORY + File.separator + comentario.getImagemUrl());
                    if (arquivo.exists()) {
                        arquivo.delete();
                    }
                }

                comentarioService.excluirComentario(comentarioId, usuario.getId());
            }
            response.sendRedirect(request.getContextPath() + "/bbh/DetalheRoteiroController?id=" + roteiroId);

        } catch (NumberFormatException | PersistenciaException e) {
            throw new ServletException("Erro ao fazer comentario: " + e.getMessage(), e);
        }
    }

}