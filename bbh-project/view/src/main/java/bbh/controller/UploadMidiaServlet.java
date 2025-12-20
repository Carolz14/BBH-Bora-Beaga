package bbh.controller;

import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.GestaoUsuariosService;
import bbh.domain.Avaliacao;
import bbh.service.AvaliacaoService;
import bbh.common.NaoEncontradoException;
import bbh.common.PersistenciaException;
import bbh.domain.MidiaAvaliacao;
import bbh.service.MidiaAvaliacaoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet("/midia/upload")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,        // 1MB em memória antes de gravar em disco
    maxFileSize = 10L * 1024L * 1024L,      // 10 MB por arquivo (ajuste conforme precisa)
    maxRequestSize = 12L * 1024L * 1024L    // tamanho máximo total da request
)
public class UploadMidiaServlet extends BaseServlet {

    private final MidiaAvaliacaoService service = new MidiaAvaliacaoService();
    private final GestaoUsuariosService gestaoUsuariosService = new GestaoUsuariosService();
    private final AvaliacaoService avaliacaoService = new AvaliacaoService();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        long idUsuario;
        Usuario usuarioLogado;
        try{
            idUsuario = getIdUsuario(req);
            usuarioLogado = gestaoUsuariosService.pesquisarPorId(idUsuario);
        }catch(PersistenciaException e){
            throw new ServletException("Erro: Usuário com o ID disponivel na sessão não foi encontrado");
        } catch(NaoEncontradoException e){
            throw new ServletException("Erro: Usúario não identificado ou ID do usúario não encontrado na sessão");
        }

        Part filePart = null;
        try {
            filePart = req.getPart("file");
        } catch (IllegalStateException e) {
            resp.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "Upload muito grande, maior do que permitido.");
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
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' (id da avaliação) é obrigatório.");
            return;
        }

        long idAvaliacao;
        try {
            idAvaliacao = Long.parseLong(idParam);
            if (idAvaliacao <= 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' inválido, menor ou igual à zero.");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' inválido: formato incorreto.");
            return;
        }
        
        try{
            Avaliacao av = avaliacaoService.buscarPorId(idAvaliacao);
            if(av == null){
                throw new ServletException("Erro: avaliação com ID presente no objeto mídia avaliação não existe");
            }
            boolean ehAdmin = usuarioLogado.getUsuarioTipo() == UsuarioTipo.ADMINISTRADOR;
            boolean donoAvaliacao = idUsuario == av.getIdUsuario();
            if(!ehAdmin && !donoAvaliacao){
                throw new ServletException("Erro: usuário sem permissão pra realizar esse tipo de operação");
            }
        }catch(PersistenciaException e){
            throw new ServletException("Erro ao verificar permissão. " + e.getMessage() + e);
        }

        try {
            MidiaAvaliacao midia = service.salvarMidia(filePart, idAvaliacao);

            String referer = req.getHeader("Referer");
            if (referer != null && !referer.isBlank()) {
                resp.sendRedirect(referer);
            } else {
                resp.sendRedirect(req.getContextPath() + "/");
            }

        } catch (PersistenciaException pe) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar mídia: " + pe.getMessage());
        } catch (IOException ioe) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro de I/O ao salvar mídia: " + ioe.getMessage());
        }
    }
}
