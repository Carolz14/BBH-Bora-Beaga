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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        try {
            long usuarioId = getIdUsuario(req);
            long idAvaliacao = Long.parseLong(req.getParameter("id_avaliacao"));
            long estabelecimentoId = Long.parseLong(req.getParameter("id")); // PADRÃO: id
            int nota = Integer.parseInt(req.getParameter("nota"));
            String comentario = req.getParameter("comentario");

            if (nota < 1 || nota > 5) {
                throw new ServletException("Nota inválida (1..5).");
            }

            Avaliacao a = new Avaliacao(idAvaliacao, usuarioId, estabelecimentoId, nota, comentario, null);
            service.atualizarAvaliacao(a);

            resp.sendRedirect(req.getContextPath() + "/bbh/DetalheEstabelecimentoController?id=" + estabelecimentoId);

        } catch (NumberFormatException e) {
            throw new ServletException("Parâmetros numéricos inválidos." + e.getMessage());
        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao atualizar avaliação." + e.getMessage());
        } catch(NaoEncontradoException e){
            throw new ServletException("ID do usuario não encontrado" + e.getMessage());
        }
    }
}
