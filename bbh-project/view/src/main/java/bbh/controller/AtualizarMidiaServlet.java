package bbh.controller;

import bbh.domain.Usuario;
import bbh.service.GestaoUsuariosService;
import bbh.domain.util.UsuarioTipo;
import bbh.common.NaoEncontradoException;
import bbh.common.PersistenciaException;
import bbh.domain.MidiaAvaliacao;
import bbh.domain.Avaliacao;
import bbh.service.AvaliacaoService;
import bbh.service.MidiaAvaliacaoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/midia/atualizar")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10L * 1024 * 1024, maxRequestSize = 12L * 1024 * 1024)
public class AtualizarMidiaServlet extends BaseServlet {
    private final MidiaAvaliacaoService service = new MidiaAvaliacaoService();
    private final GestaoUsuariosService gestaoUsuariosService = new GestaoUsuariosService();
    private final AvaliacaoService avaliacaoService = new AvaliacaoService();
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Long idUsuario;
        Usuario usuarioLogado;
        try {
            idUsuario = getIdUsuario(req);
            usuarioLogado = gestaoUsuariosService.pesquisarPorId(idUsuario);

        } catch (NaoEncontradoException e) {
            throw new ServletException("Erro: Usúario não identificado ou ID do usúario não encontrado na sessão");
        } catch (PersistenciaException e) {
            throw new ServletException("Erro: Usuário com o ID disponivel na sessão não foi encontrado");
        }

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
        String categoria = req.getParameter("categoria");
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

        try {
            MidiaAvaliacao midiaAv = service.buscarPorId(idMidia);
             if(midiaAv == null){
                throw new ServletException("Erro: mídia com esse ID não existe");
            }
            Avaliacao av = avaliacaoService.buscarPorId(midiaAv.getIdAvaliacao());
            if(av == null){
                throw new ServletException("Erro: avaliação com ID presente no objeto mídia avaliação não existe");
            }
            boolean ehAdmin = usuarioLogado.getUsuarioTipo() == UsuarioTipo.ADMINISTRADOR;
            boolean donoMidiaAvaliacao = idUsuario == av.getIdUsuario();
            if(!ehAdmin && !donoMidiaAvaliacao){
                throw new ServletException("Erro: usuário sem permissão pra realizar esse tipo de operação");
            }

        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao verificar permissão" + e.getMessage() + e);
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
