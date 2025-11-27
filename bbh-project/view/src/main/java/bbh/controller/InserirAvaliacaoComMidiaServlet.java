package bbh.controller;

import bbh.common.NaoEncontradoException;
import bbh.common.PersistenciaException;
import bbh.domain.Avaliacao;
import bbh.domain.MidiaAvaliacao;
import bbh.service.AvaliacaoService;
import bbh.service.MidiaAvaliacaoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/avaliacao/inserir-com-midia")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10L * 1024L * 1024L, maxRequestSize = 12L * 1024L
        * 1024L)
public class InserirAvaliacaoComMidiaServlet extends BaseServlet {

    private final AvaliacaoService avaliacaoService = new AvaliacaoService();
    private final MidiaAvaliacaoService midiaService = new MidiaAvaliacaoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 1) ler campos textuais
        String idEstStr = req.getParameter("id");
        String notaStr = req.getParameter("nota");
        String coment = req.getParameter("comentario");

        if (idEstStr == null || notaStr == null || coment == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros obrigatórios faltando.");
            return;
        }

        long idEstabelecimento;
        int nota;
        try {
            idEstabelecimento = Long.parseLong(idEstStr);
            nota = Integer.parseInt(notaStr);
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros numéricos inválidos.");
            return;
        }

        try {
            long idUsuario = getIdUsuario(req);
            Avaliacao av = new Avaliacao(idUsuario, idEstabelecimento, nota, coment);
            Avaliacao avSalva = avaliacaoService.inserirAvaliacao(av);
            long idAvaliacaoSalva = avSalva.getIdAvaliacao();
            Part filePart = req.getPart("file");
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream in = filePart.getInputStream()) {
                    // usa salvarMidiaEmBytes para aceitar stream (opcional)
                    midiaService.salvarMidia(filePart, idAvaliacaoSalva);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/bbh/DetalheEstabelecimentoController?id=" + idEstabelecimento);

        } catch (PersistenciaException pe) {
            throw new ServletException("Erro ao salvar avaliação/mídia: " + pe.getMessage(), pe);
        } catch(NaoEncontradoException e){
            throw new ServletException("Erro: usuário não encontrado " + e.getMessage(), e);
        }
    }
}
