package bbh.controller;

import java.io.IOException;
import java.util.List;

import bbh.common.PersistenciaException;
import bbh.domain.Local;
import bbh.service.LocalService;
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
            LocalService localService = new LocalService();
            String tag = request.getParameter("tag");
            List<Local> resultados;

            if (tag != null && !tag.isEmpty()) {
                resultados = localService.buscarPorTag(tag);
                
                String tagFormatada = tag.substring(0, 1).toUpperCase() + tag.substring(1);
                request.setAttribute("tituloPagina", tagFormatada);
            } else {
                resultados = localService.pesquisarPorNome(""); 
                request.setAttribute("tituloPagina", "Todos os Locais");
            }

            request.setAttribute("listaLocais", resultados);
     
            request.getRequestDispatcher("/jsps/turista/lista-estabelecimento.jsp").forward(request, response);
            
        } catch (PersistenciaException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar locais: " + e.getMessage());
        }
    }
}