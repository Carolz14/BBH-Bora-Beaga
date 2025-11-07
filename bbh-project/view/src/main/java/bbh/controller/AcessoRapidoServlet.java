package bbh.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bbh.service.TagService; // seu service/dao que contém listarEstabelecimentosPorTagNome
import bbh.common.PersistenciaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import bbh.domain.Usuario;

@WebServlet("/lista-estabelecimento")
public class AcessoRapidoServlet extends HttpServlet {
    
    private final TagService tagService = new TagService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoria = req.getParameter("categoria");
        List<Long> ids = new ArrayList<>();

        if (categoria != null && !categoria.trim().isEmpty()) {
            try {
                ids = tagService.listarEstabelecimentosViaTag(categoria);
            } catch (PersistenciaException e) {
                req.setAttribute("erro", "Erro ao buscar estabelecimentos pela categoria: " + e.getMessage());
            }
        } else {
        }
        req.setAttribute("categoriaSelecionada", categoria);
        req.setAttribute("idsEstabelecimentos", ids);
        req.getRequestDispatcher("/jsps/lista-estabelecimento.jsp").forward(req, resp); ///ignora 
    }
}
