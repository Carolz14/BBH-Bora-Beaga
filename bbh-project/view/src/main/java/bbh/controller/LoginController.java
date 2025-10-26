package bbh.controller;

import java.io.IOException;

import bbh.domain.Pessoa;
import bbh.service.GestaoPessoasService;
import bbh.service.util.Util; // tem que colocar o codigo no lugar certo ainda
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/LoginController")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String jsp = "";
        try {
            String login = request.getParameter("email");

            String senha = request.getParameter("senha");

            // Gerar hash SHA-256 da senha digitada
            String senhaHash = Util.gerarHashSHA256(senha);
            GestaoPessoasService manterPessoa = new GestaoPessoasService();

            Pessoa pessoa = manterPessoa.pesquisarConta(login, senhaHash);

            if (pessoa != null) {

                response.sendRedirect(request.getContextPath() + "/jsps/turista/pagina-principal.html");
                return;
            } else {

                request.setAttribute("erroLogin", "Login ou senha incorretos");
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("erroLogin", "Ocorreu um erro inesperado: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }

        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        rd.forward(request, response);
    }

    public static void validarSessao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Pessoa usuario = (Pessoa) request.getSession().getAttribute("usuario");
        if (usuario == null) {

            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
    }

}