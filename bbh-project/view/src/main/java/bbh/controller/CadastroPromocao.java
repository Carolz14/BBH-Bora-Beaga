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
            String id = request.getParameter("idEstab");
            Long idEstabelecimento = Long.valueOf(id);

            // Pegando parâmetros
            String nome = request.getParameter("nomePromocao");
            String descricao = request.getParameter("descricaoPromocao");
            LocalDate data = LocalDate.parse(request.getParameter("dataPromocao"));

            // Criando a promoção com o ID do estabelecimento
            Promocao promocao = new Promocao(nome, descricao, data, idEstabelecimento);

            // Chamada correta
            GestaoPromocoesService service = new GestaoPromocoesService();
            service.cadastrarPromocao(promocao);

            response.sendRedirect(request.getContextPath() + "/bbh/promocoes");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao cadastrar promoção: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/bbh/promocoes");
        }
    }
}
