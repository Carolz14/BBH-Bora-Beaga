package bbh.controller;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            long idUsuario = getIdUsuario(req);
            long idAvaliacao = Long.parseLong(req.getParameter("id"));
            long idEstabelecimento = Long.parseLong(req.getParameter("id_estabelecimento"));
            String comentario = req.getParameter("comentario");
            int nota = Integer.parseInt(req.getParameter("nota"));
            
            if (nota < 1 || nota > 5) {
                throw new ServletException("Erro ao atualizar avaliação, nota invalida");
            }

            Avaliacao avaliacao = new Avaliacao(idAvaliacao, idUsuario, idEstabelecimento, nota, comentario, null);
            service.atualizarAvaliacao(avaliacao);
            resp.sendRedirect(req.getContextPath() + "/avaliacao/listar?estabelecimentoId=" + idEstabelecimento);

        } catch (NumberFormatException e) {
            throw new ServletException("Erro ao atualziar avaliação, erro no formato dos parâmetros." + e.getMessage());
        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao atualizar avaliação, erro de persistência." + e.getMessage());
        } catch(NaoEncontradoException e){
            throw new ServletException("Erro ao atualizar avaliação, usuario não encontrado" + e.getMessage());
        }
    }
}
