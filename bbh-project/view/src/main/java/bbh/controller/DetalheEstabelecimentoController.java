package bbh.controller;

import java.io.IOException;

import bbh.common.PersistenciaException;
import bbh.domain.Usuario;
import bbh.service.GestaoUsuariosService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bbh/DetalheEstabelecimentoController")
public class DetalheEstabelecimentoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/turista/lista-estabelecimento.jsp");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            GestaoUsuariosService service = new GestaoUsuariosService();
            Usuario estabelecimento = service.pesquisarPorId(id);

            if (estabelecimento != null) {
                request.setAttribute("estabelecimento", estabelecimento);
               response.sendRedirect(request.getContextPath() + "/jsps/turista/detalhe-estabelecimento.jsp?id=" + id);
            } else {
                response.sendRedirect(request.getContextPath() + "/jsps/turista/lista-estabelecimento.jsp");
            }

        } catch (NumberFormatException | PersistenciaException e) {
            throw new ServletException("Erro ao buscar estabelecimento: " + e.getMessage(), e);
        }
    }


}