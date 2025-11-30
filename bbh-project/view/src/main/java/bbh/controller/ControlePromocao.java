package bbh.controller;

import bbh.domain.Promocao;
import bbh.domain.Usuario;
import bbh.service.GestaoPromocoesService;
import bbh.common.PersistenciaException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/promocoes")
public class ControlePromocao extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        GestaoPromocoesService service = new GestaoPromocoesService();
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        List<Promocao> promocoesAtivas = new ArrayList<>();

        try {
            if (u != null && u.getUsuarioTipo() != null
                    && u.getUsuarioTipo().name().equalsIgnoreCase("ESTABELECIMENTO")) {

                promocoesAtivas = service.listarPorEstabelecimento(u.getId());
            }

        } catch (PersistenciaException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao buscar promoções: " + e.getMessage());
        }

        request.setAttribute("promocoes", promocoesAtivas);
        request.getRequestDispatcher("/jsps/estabelecimento/promocoes.jsp").forward(request, response);

    }
}
