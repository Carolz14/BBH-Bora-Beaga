package bbh.controller;

import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.domain.Avaliacao;
import bbh.service.AvaliacaoService;
import bbh.service.GestaoUsuariosService;
import bbh.common.NaoEncontradoException;
import bbh.common.PersistenciaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/avaliacao/deletar")
public class RemoverAvaliacaoServlet extends BaseServlet {

    private final AvaliacaoService service = new AvaliacaoService();
    private final GestaoUsuariosService gestaoUsuariosService = new GestaoUsuariosService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        long idUsuario;
        Usuario usuarioLogado;
        try {
            idUsuario = getIdUsuario(req);
            usuarioLogado = gestaoUsuariosService.pesquisarPorId(idUsuario);
        } catch (PersistenciaException e) {
            throw new ServletException("Erro: Usuário com o ID disponivel na sessão não foi encontrado");
        } catch (NaoEncontradoException e) {
            throw new ServletException("Erro: Usúario não identificado ou ID do usúario não encontrado na sessão");
        }

        try {
            long idAvaliacao = Long.parseLong(req.getParameter("id_avaliacao"));
            long idEstabelecimento = Long.parseLong(req.getParameter("id"));

            Avaliacao av = service.buscarPorId(idAvaliacao);
             if(av == null){
                throw new ServletException("Erro: avaliação com esse id não foi encontrada");
            }

            boolean ehAdmin = usuarioLogado.getUsuarioTipo() == UsuarioTipo.ADMINISTRADOR;
            boolean donoAvaliacao = idUsuario == av.getIdUsuario();
            if(!ehAdmin && !donoAvaliacao){
                throw new ServletException("Erro: usuário sem permissão pra realizar esse tipo de operação");
            }

            service.removerAvaliacao(idAvaliacao);

            resp.sendRedirect(req.getContextPath() + "/bbh/DetalheEstabelecimentoController?id=" + idEstabelecimento);
        } catch (NumberFormatException e) {
            throw new ServletException("Parâmetros numéricos inválidos.", e);
        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao remover avaliação.", e);
        }
    }
}
