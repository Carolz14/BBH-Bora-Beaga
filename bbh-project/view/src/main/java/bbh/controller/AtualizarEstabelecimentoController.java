
package bbh.controller;

import java.io.File;
import java.io.IOException;

import bbh.domain.Usuario;

import bbh.service.GestaoUsuariosService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/bbh/AtualizarEstabelecimentoController")
@MultipartConfig
public class AtualizarEstabelecimentoController extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "C:/bbh_uploads/perfis";
    private GestaoUsuariosService gestaoUsuariosService = new GestaoUsuariosService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/jsps/login.jsp");
            return;
        }

        try {
          
            String descricao = request.getParameter("descricao");

    
            Part filePart = request.getPart("imagem");
            String fileName = getSubmittedFileName(filePart);
            String imagemUrl = usuarioLogado.getImagemUrl(); // Mantém a antiga por padrão


            if (fileName != null && !fileName.isEmpty()) {
                
               
                String uploadPath = UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists())
                    uploadDir.mkdirs();

                String filePath = uploadPath + File.separator + fileName;
                filePart.write(filePath);
                
            
                imagemUrl = "perfis/" + fileName; 
            }

          
            gestaoUsuariosService.atualizarPerfilEstabelecimento(usuarioLogado.getId(), descricao, imagemUrl);

           
            usuarioLogado.setDescricao(descricao);
            usuarioLogado.setImagemUrl(imagemUrl);
            session.setAttribute("usuario", usuarioLogado);

       
            response.sendRedirect(request.getContextPath() + "/jsps/estabelecimento/perfil.jsp");

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("erroLogin", "Ocorreu um erro inesperado: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/jsps/criar-conta.jsp");
            rd.forward(request, response);
        }

    }

    private String getSubmittedFileName(Part part) {
        if (part == null)
            return null; // Retorna nulo se nenhuma parte foi enviada

        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                if (fileName.isEmpty()) {
                    return null; // Nenhum arquivo foi selecionado no input
                }
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
}