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
            String acao = request.getParameter("acao");
            String nome = request.getParameter("nomePromocao");
            String descricao = request.getParameter("descricaoPromocao");
            String dataStr = request.getParameter("dataPromocao");
            LocalDate data = (dataStr != null && !dataStr.isEmpty()) ? LocalDate.parse(dataStr) : null;
            
            GestaoPromocoesService service = new GestaoPromocoesService();

            if ("cadastrar".equals(acao)) {
                String idEstabStr = request.getParameter("idEstab");
                Long idEstabelecimento = Long.valueOf(idEstabStr);
                
                Promocao promocao = new Promocao(nome, descricao, data, idEstabelecimento);
                service.cadastrarPromocao(promocao);
                
            } else if ("atualizar".equals(acao)) {
                String idPromocaoStr = request.getParameter("idPromocao");
                Long idPromocao = Long.valueOf(idPromocaoStr);
                String idEstabStr = request.getParameter("idEstab");
                Long idEstabelecimento = Long.valueOf(idEstabStr);

                Promocao promocao = new Promocao();
                promocao.setId(idPromocao);
                promocao.setNome(nome);
                promocao.setDescricao(descricao);
                promocao.setData(data);
                promocao.setIdEstabelecimento(idEstabelecimento);
                
                service.atualizarPromocao(promocao);
            }

            response.sendRedirect(request.getContextPath() + "/bbh/promocoes");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/bbh/promocoes");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String acao = request.getParameter("acao");
            String idStr = request.getParameter("id");
            
            GestaoPromocoesService service = new GestaoPromocoesService();
            
            if (idStr != null && !idStr.isEmpty()) {
                Long idPromocao = Long.valueOf(idStr);
                Long idEstab = service.buscarEstabelecimenoPorPromocao(idPromocao);
                if ("excluir".equals(acao)) {
                    service.removerVinculo(idEstab, idPromocao);
                    
                    response.sendRedirect(request.getContextPath() + "/bbh/promocoes");
                    
                } else if ("carregarEdicao".equals(acao)) {
                    Promocao p = service.buscarPorId(idPromocao);
                    request.setAttribute("promocaoEdit", p);
                    request.getRequestDispatcher("/jsps/estabelecimento/promocoes.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/bbh/promocoes");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/bbh/promocoes");
        }
    }
}