package bbh.controller;

import bbh.domain.Promocao;
import bbh.service.GestaoPromocoesService;
import bbh.common.PersistenciaException;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/todasPromocoes")
public class ControleTodasPromocoes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        GestaoPromocoesService service = new GestaoPromocoesService();

        try {
            List<Promocao> listaGeral = service.listarTodas();
            request.setAttribute("promocoes", listaGeral);

        } catch (PersistenciaException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar lista completa.");
        }

        request.getRequestDispatcher("/jsps/turista/lista-promocoes.jsp").forward(request, response);
    }
}