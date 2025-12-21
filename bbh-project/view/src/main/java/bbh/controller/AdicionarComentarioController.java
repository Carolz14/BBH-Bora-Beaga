package bbh.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import bbh.common.PersistenciaException;
import bbh.domain.Comentario;
import bbh.domain.Usuario;
import bbh.service.GestaoComentarioRoteiroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/bbh/AdicionarComentarioController")
@MultipartConfig
public class AdicionarComentarioController extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "C:/bbh_uploads/comentarios";
    private GestaoComentarioRoteiroService comentarioService = new GestaoComentarioRoteiroService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = request.getSession(false);
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Long roteiroId = Long.parseLong(request.getParameter("roteiroId"));
            String texto = request.getParameter("texto");
            Comentario comentario = new Comentario(roteiroId, usuario.getId(), texto);

            Part filePart = request.getPart("anexo");

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String ext = "";
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    ext = fileName.substring(i).toLowerCase();
                }

                if (ext.equals(".png") || ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".gif")) {

                    String novoNome = UUID.randomUUID().toString() + ext;

                    File uploadDir = new File(UPLOAD_DIRECTORY);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    filePart.write(UPLOAD_DIRECTORY + File.separator + novoNome);
                    comentario.setImagemUrl(novoNome);
                }

            }
            comentarioService.inserirComentario(comentario);
            response.sendRedirect(request.getContextPath() + "/bbh/DetalheRoteiroController?id=" + roteiroId);

        } catch (NumberFormatException | PersistenciaException e) {
            throw new ServletException("Erro ao fazer comentario: " + e.getMessage(), e);
        }
    }

}