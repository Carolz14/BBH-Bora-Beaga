package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.Local;
import bbh.domain.Usuario;
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

@WebServlet("/pesquisarLocais")
public class PesquisaLocalServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final LocalService localService = new LocalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // sessão e usuário
        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = null;
        if (session != null) {
            usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        }

        String nome = request.getParameter("nome");
        List<Local> resultados = null;
        String erro = null;

        if (nome != null && !nome.trim().isEmpty()) {
            String termo = nome.trim();
            if (termo.length() < 3) {
                erro = "Digite pelo menos 3 letras para pesquisar.";
            } else {
                try {
                    resultados = localService.pesquisarPorNome(termo);
                } catch (PersistenciaException e) {
                    erro = "Erro ao pesquisar locais: " + e.getMessage();
                }
            }
        } else if (nome != null) { // caso o parâmetro tenha sido enviado vazio
           
        }

        request.setAttribute("resultados", resultados);
        request.setAttribute("nomeBusca", nome);
        request.setAttribute("erro", erro);
        request.setAttribute("usuarioLogado", usuarioLogado);

        RequestDispatcher rd = request.getRequestDispatcher("/paginas-principais/pagina-principal.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
