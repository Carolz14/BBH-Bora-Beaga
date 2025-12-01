package bbh.controller;

import bbh.domain.Usuario;
import bbh.service.GestaoUsuariosService;
import bbh.common.PersistenciaException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/bbh/usuarios")
public class ControleUsuarios extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestaoUsuariosService service = new GestaoUsuariosService();
        List<Usuario> usuariosAtivos = new ArrayList<>();
         try {
            usuariosAtivos = service.pesquisarAtivos();
        } catch (PersistenciaException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao buscar usuários: " + e.getMessage());
        }

        List<Usuario> turistas = new ArrayList<>();
        List<Usuario> guias = new ArrayList<>();
        List<Usuario> estabelecimentos = new ArrayList<>();

        for (Usuario u : usuariosAtivos) {
            if (u.getUsuarioTipo() != null && u.getUsuarioTipo().name().equalsIgnoreCase("TURISTA")) {
                turistas.add(u);
            } else if (u.getUsuarioTipo() != null && u.getUsuarioTipo().name().equalsIgnoreCase("GUIA")) {
                guias.add(u);
            } else if (u.getUsuarioTipo() != null && u.getUsuarioTipo().name().equalsIgnoreCase("ESTABELECIMENTO")) {
                estabelecimentos.add(u);
            }
        }

        request.setAttribute("turistas", turistas);
        request.setAttribute("guias", guias);
        request.setAttribute("estabelecimentos", estabelecimentos);

        request.getRequestDispatcher("/jsps/admin/usuarios.jsp").forward(request, response);
    }   
    
}