package bbh.controller;

import bbh.service.AvaliacaoService;
import bbh.common.PersistenciaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/avaliacao/deletar")
public class RemoverAvaliacaoServlet extends BaseServlet {

    private final AvaliacaoService service = new AvaliacaoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            long idAvaliacao = Long.parseLong(req.getParameter("id_avaliacao"));
            long idEstabelecimento = Long.parseLong(req.getParameter("id_estabelecimento"));

            // opcional: verificar usuario dono antes de remover no service
            service.removerAvaliacao(idAvaliacao);

            resp.sendRedirect(req.getContextPath() + "/avaliacao/listar?estabelecimentoId=" + idEstabelecimento);
        } catch (NumberFormatException e) {
            throw new ServletException(
                    "Erro ao remover avaliação, problema de formato nos parâmetros" + e.getMessage());
        } catch (PersistenciaException e) {
            throw new ServletException("Erro ao remover avaliação, erro de persistência" + e.getMessage());
        }
    }
}
