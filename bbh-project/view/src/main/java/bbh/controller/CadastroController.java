package bbh.controller;

import java.io.IOException;

import bbh.common.SenhaHash;
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.GestaoUsuariosService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/CadastroController")
public class CadastroController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String endereco = request.getParameter("endereco");
        String contatoString = request.getParameter("contato");
        String naturalidade = request.getParameter("naturalidade");
        String tipoString = request.getParameter("tipo");
        String cnpjString = request.getParameter("cnpj");

        try {

            UsuarioTipo tipo = UsuarioTipo.strTo(tipoString);

            Usuario usuario = new Usuario(nome, email, SenhaHash.gerarHashSHA256(senha), naturalidade);
            usuario.setEndereco(endereco);
            usuario.setUsuarioTipo(tipo);
            usuario.setHabilitado(true);

            if (tipo == UsuarioTipo.ESTABELECIMENTO) {
                long contato = Long.parseLong(contatoString);
                long cnpj = Long.parseLong(cnpjString);
                usuario.setContato(contato);
                usuario.setCNPJ(cnpj);
            } else {
                usuario.setContato(null);
                usuario.setCNPJ(null);
            }

            GestaoUsuariosService salvarUsuario = new GestaoUsuariosService();
            salvarUsuario.cadastrarUsuario(usuario);

            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("erroLogin", "Ocorreu um erro inesperado: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/jsps/criar-conta.jsp");
            rd.forward(request, response);
        }

    }
}