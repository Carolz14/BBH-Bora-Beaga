package bbh.controller;

import bbh.common.NaoEncontradoException;
import bbh.common.PersistenciaException;
import bbh.domain.Avaliacao;
import bbh.service.AvaliacaoService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/avaliacao/inserir")
public class InserirAvaliacaoServlet extends BaseServlet {

    private AvaliacaoService service = new AvaliacaoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
        try {
            Long idUsuario = getIdUsuario(req);
            long idEstabelecimento = Long.parseLong(req.getParameter("id_estabelecimento"));
            String comentario = req.getParameter("comentario");
            int nota = Integer.parseInt(req.getParameter("nota"));

            if (nota < 1 || nota > 5) {
                req.setAttribute("erro", "Nota inválida: deve ser entre 1 e 5.");
                req.getRequestDispatcher("/WEB-INF/avaliacoes/erro.jsp").forward(req, resp);
                return;
            }
            Avaliacao avaliacao = new Avaliacao(idUsuario, idEstabelecimento, nota, comentario);
            service.inserirAvaliacao(avaliacao);
            resp.sendRedirect(req.getContextPath() + "/avaliacao/listar?id_estabelecimento=" + idEstabelecimento);
        } catch (NumberFormatException e) {
            throw new ServletException("Erro ao inserir a avaliação, problema de formato de parâmetros." + e.getMessage());
        } catch(PersistenciaException e){
            throw new ServletException("Erro ao inserir a avaliação, problema de persistência." + e.getMessage());
        } catch (NaoEncontradoException e) {
            throw new ServletException("Erro ao inserir a avaliação, id do usuario não encontrado." + e.getMessage());
        }
    }
}
