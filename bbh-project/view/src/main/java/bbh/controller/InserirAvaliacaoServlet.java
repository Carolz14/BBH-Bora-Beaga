package bbh.controller;

import bbh.domain.Avaliacao;
import bbh.service.AvaliacaoService;
import bbh.common.NaoEncontradoException;
import bbh.common.PersistenciaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/avaliacao/inserir")
public class InserirAvaliacaoServlet extends BaseServlet {

    private final AvaliacaoService service = new AvaliacaoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        try {
            long usuarioId = getIdUsuario(req);
            long estabelecimentoId = Long.parseLong(req.getParameter("id"));
            int nota = Integer.parseInt(req.getParameter("nota"));
            String comentario = req.getParameter("comentario");

            if (nota < 1 || nota > 5) {
                throw new ServletException("Nota inválida (1..5).");
            }

            Avaliacao avaliacao = new Avaliacao(usuarioId, estabelecimentoId, nota, comentario);
            service.inserirAvaliacao(avaliacao);
            resp.sendRedirect(req.getContextPath() + "/bbh/DetalheEstabelecimentoController?id=" + estabelecimentoId);

        } catch (NumberFormatException e) {
            throw new ServletException("Parâmetros numéricos inválidos." + e.getMessage());
        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao inserir avaliação." + e.getMessage());
        } catch(NaoEncontradoException e){
            throw new ServletException("Id do usuario não encontrado" + e.getMessage());
        }
    }
}
