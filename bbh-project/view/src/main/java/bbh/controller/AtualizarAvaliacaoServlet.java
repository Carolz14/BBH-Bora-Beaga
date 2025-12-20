package bbh.controller;

import bbh.domain.Usuario;
import bbh.service.GestaoUsuariosService;
import bbh.domain.util.UsuarioTipo;
import bbh.domain.Avaliacao;
import bbh.service.AvaliacaoService;
import bbh.common.NaoEncontradoException;
import bbh.common.PersistenciaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/avaliacao/atualizar")
public class AtualizarAvaliacaoServlet extends BaseServlet {

    private final AvaliacaoService service = new AvaliacaoService();
    private final GestaoUsuariosService gestaoUsuariosService = new GestaoUsuariosService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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
        try {

            long idAvaliacao = Long.parseLong(req.getParameter("id_avaliacao"));
            long localId = Long.parseLong(req.getParameter("id")); // PADRÃO: id
            String categoria = req.getParameter("categoria");
            int nota = Integer.parseInt(req.getParameter("nota"));
            String comentario = req.getParameter("comentario");

            Avaliacao av = service.buscarPorId(idAvaliacao);
            if(av == null){
                throw new ServletException("Erro: avaliação com esse id não foi encontrada");
            }
            boolean ehAdmin = usuarioLogado.getUsuarioTipo() == UsuarioTipo.ADMINISTRADOR;
            boolean donoAvaliacao = idUsuario == av.getIdUsuario();
            if(!ehAdmin && !donoAvaliacao){
                throw new ServletException("Erro: usuário sem permissão pra realizar esse tipo de operação");
            }
            

            if (nota < 1 || nota > 5) {
                throw new ServletException("Nota inválida (1..5).");
            }

            Avaliacao a = new Avaliacao(idAvaliacao, idUsuario, localId, nota, comentario, null, categoria);
            service.atualizarAvaliacao(a);
            if("Estabelecimento".equalsIgnoreCase(categoria) || "Estab".equalsIgnoreCase(categoria)){
                resp.sendRedirect(req.getContextPath() + "/bbh/DetalheEstabelecimentoController?id=" + localId);
            }
            else{
                resp.sendRedirect(req.getContextPath() + "/bbh/DetalhePontoTuristico?id=" + localId);
            }

        } catch (NumberFormatException e) {
            throw new ServletException("Parâmetros numéricos inválidos." + e.getMessage());
        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao atualizar avaliação." + e.getMessage());
        }
    }
}
