package bbh.controller;

import java.io.IOException;

import bbh.common.SenhaHash;
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.GestaoUsuariosService; // tem que colocar o codigo no lugar certo ainda
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bbh/LoginController")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        try {
            String login = request.getParameter("email");

            String senha = request.getParameter("senha");

            // Gerar hash SHA-256 da senha digitada
            String senhaHash = SenhaHash.gerarHashSHA256(senha);
            GestaoUsuariosService manterUsuario = new GestaoUsuariosService();

            Usuario usuario = manterUsuario.pesquisarConta(login, senhaHash);

            if (usuario != null) {
                HttpSession sessao = request.getSession();
                sessao.setAttribute("usuario", usuario);
                UsuarioTipo tipo = usuario.getUsuarioTipo();
                switch (tipo) {
                    case ADMINISTRADOR:
                        response.sendRedirect(request.getContextPath() + "/jsps/admin/painel.html");
                        break;
                    case TURISTA:
                        response.sendRedirect(request.getContextPath() + "/jsps/turista/pagina-principal.html");
                        break;
                    case GUIA:
                        response.sendRedirect(request.getContextPath() + "/jsps/turista/pagina-principal.html");
                        break;
                    case ESTABELECIMENTO:
                        response.sendRedirect(request.getContextPath() + "/jsps/estabelecimento/painel.html");
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/index.jsp?erro=usuarioSemAcesso");
                        break;
                }
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

    }

    public static void validarSessao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) {

            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
    }

}