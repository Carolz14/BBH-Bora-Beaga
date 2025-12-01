package bbh.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bbh.service.TagService;
import bbh.common.PersistenciaException;
import bbh.domain.Usuario;
import bbh.service.GestaoEstabelecimentosService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/EstabelecimentosController")
public class EstabelecimentosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            GestaoEstabelecimentosService gestaoEstabelecimentosService = new GestaoEstabelecimentosService();
            TagService tagService = new TagService();
            String tag = request.getParameter("tag");

            List<Long> listaIdEstabelecimentos = tagService.listarEstabelecimentosViaTag(tag);
            List<Usuario> listaEstabelecimentos = new ArrayList<>();
            for (Long id : listaIdEstabelecimentos) {
                try {
                    Usuario estabelecimento = gestaoEstabelecimentosService.listarEstabelecimentoPorId(id);
                    if (estabelecimento != null) {
                        listaEstabelecimentos.add(estabelecimento);
                    }
                } catch (PersistenciaException e) {
                    throw new PersistenciaException("Erro ao buscar estabelecimento por ID através da tag" + e.getMessage(), e);
                }
            }

            request.setAttribute("estabelecimentos", listaEstabelecimentos);
            request.getSession().setAttribute("estabelecimentos", listaEstabelecimentos);
            response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-estabelecimento.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar estabelecimentos");
        }

    }

}