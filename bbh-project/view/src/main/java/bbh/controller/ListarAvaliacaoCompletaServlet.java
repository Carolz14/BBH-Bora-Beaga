package bbh.controller;

import bbh.domain.Avaliacao;
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.GestaoUsuariosService;
import bbh.domain.MidiaAvaliacao;
import bbh.service.AvaliacaoService;
import bbh.service.MidiaAvaliacaoService;
import bbh.common.NaoEncontradoException;
import bbh.common.PersistenciaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/avaliacao/listar")
public class ListarAvaliacaoCompletaServlet extends BaseServlet {

    private final AvaliacaoService avaliacaoService = new AvaliacaoService();
    private final MidiaAvaliacaoService midiaAvaliacaoService = new MidiaAvaliacaoService();
    private final GestaoUsuariosService gestaoUsuariosService = new GestaoUsuariosService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String categoria = req.getParameter("categoria");
        if (idParam == null || idParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' (estabelecimento) obrigatório.");
            return;
        }

        long idEstabelecimento;
        try {
            idEstabelecimento = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'id' inválido.");
            return;
        }
        long idUsuario;
        Usuario usuarioLogado;
        try {
            idUsuario = getIdUsuario(req);
            usuarioLogado = gestaoUsuariosService.pesquisarPorId(idUsuario);
        } catch (NaoEncontradoException e) {
            throw new ServletException("Erro: Usúario não identificado ou ID do usúario não encontrado na sessão");
        } catch (PersistenciaException e) {
            throw new ServletException("Erro: Usuário com o ID disponivel na sessão não foi encontrado");
        }

        try {
            List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesPorEstabelecimento(idEstabelecimento, categoria);

            Map<Long, List<MidiaAvaliacao>> midiasPorAvaliacao = new HashMap<>();
            for (Avaliacao av : avaliacoes) {
                long idAvaliacao = av.getIdAvaliacao();
                List<MidiaAvaliacao> listaMidias = midiaAvaliacaoService.listarMidiasPorAvaliacao(idAvaliacao);
                midiasPorAvaliacao.put(idAvaliacao, listaMidias);
            }
            boolean ehAdmin = usuarioLogado.getUsuarioTipo() == UsuarioTipo.ADMINISTRADOR;
            double media = avaliacaoService.calcularMedia(idEstabelecimento, categoria);

            req.setAttribute("avaliacoes", avaliacoes);
            req.setAttribute("midiasPorAvaliacao", midiasPorAvaliacao);
            req.setAttribute("media", media);
            req.setAttribute("estabelecimentoId", idEstabelecimento);
            req.setAttribute("categoria", categoria);
            req.setAttribute("idUsuario", idUsuario);
            req.setAttribute("ehAdmin", ehAdmin);

            req.getRequestDispatcher("/jsps/turista/avaliacao.jsp").include(req, resp);

        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao carregar avaliações/mídias: " + e.getMessage(), e);
        }
    }
}
