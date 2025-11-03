package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.Local;
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.LocalService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet responsável pela pesquisa de locais.
 * Somente usuários do tipo ESTABELECIMENTO podem utilizar esta funcionalidade.
 */
@WebServlet("/pesquisarLocais")
public class PesquisaLocalServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final LocalService localService = new LocalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null)
                ? (Usuario) session.getAttribute("usuarioLogado")
                : null;

        String termoBusca = request.getParameter("nome");
        List<Local> resultados = null;
        String erro = null;

        // Verifica se o usuário está autenticado
        if (usuarioLogado == null) {
            erro = "Você precisa estar autenticado para realizar uma pesquisa.";
        }
        // Regra de negócio: apenas usuários do tipo ESTABELECIMENTO podem pesquisar
        else if (usuarioLogado.getUsuarioTipo() != UsuarioTipo.ESTABELECIMENTO) {
            erro = "Somente usuários do tipo ESTABELECIMENTO podem acessar esta funcionalidade.";
        }
        // Se o termo de busca for válido, executa a pesquisa
        else if (termoBusca != null && !termoBusca.trim().isEmpty()) {
            termoBusca = termoBusca.trim();

            if (termoBusca.length() < 3) {
                erro = "Digite pelo menos 3 letras para pesquisar.";
            } else {
                try {
                    resultados = localService.pesquisarPorNome(termoBusca);
                    if (resultados == null || resultados.isEmpty()) {
                        erro = "Nenhum estabelecimento encontrado para o termo: " + termoBusca;
                    }
                } catch (PersistenciaException e) {
                    erro = "Erro ao pesquisar estabelecimentos: " + e.getMessage();
                    e.printStackTrace();
                }
            }
        }

        // Define os atributos para a página JSP
        request.setAttribute("resultados", resultados);
        request.setAttribute("nomeBusca", termoBusca);
        request.setAttribute("erro", erro);
        request.setAttribute("usuarioLogado", usuarioLogado);

        // Encaminha de volta para a página principal
        RequestDispatcher dispatcher = request.getRequestDispatcher("/paginas-principais/pagina-principal.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
