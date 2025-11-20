package bbh.controller;

import bbh.domain.Promocao;
import bbh.service.GestaoPromocoesService;

import java.io.IOException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/CadastroPromocao")
public class CadastroPromocao extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nome = request.getParameter("nomePromocao");
            String descricao = request.getParameter("descricaoPromocao");
            String dataStr = request.getParameter("dataPromocao");

            if (dataStr == null || dataStr.isBlank()) {
                throw new RuntimeException("Data não informada!");
            }

            LocalDate data = LocalDate.parse(dataStr);
            Promocao promocao = new Promocao(nome, 0L, descricao, data);

            GestaoPromocoesService service = new GestaoPromocoesService();
            service.cadastrarPromocao(promocao);

            response.sendRedirect(request.getContextPath() + "/jsps/estabelecimento/promocoes.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao cadastrar promoção: " + e.getMessage());
            request.getRequestDispatcher("/jsps/estabelecimento/promocoes.jsp")
                   .forward(request, response);
        }
    }
}
