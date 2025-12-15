package bbh.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/imagem")
public class ImagemServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "C:" + File.separator + "bbh_imagens";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomeArquivo = request.getParameter("nome");
        System.out.println("--------------------------------------------------");
        System.out.println("[ImagemServlet] Chamado!");
        System.out.println("[ImagemServlet] Nome recebido da URL: " + nomeArquivo);

        if (nomeArquivo == null || nomeArquivo.isEmpty()) {
            System.out.println("[ImagemServlet] ERRO: Nome veio vazio.");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File arquivo = new File(UPLOAD_DIR, nomeArquivo);
        
        System.out.println("[ImagemServlet] Procurando em: " + arquivo.getAbsolutePath());
        System.out.println("[ImagemServlet] Arquivo existe? " + arquivo.exists());

        if (!arquivo.exists()) {
            System.out.println("[ImagemServlet] ERRO: Arquivo não encontrado no disco.");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = getServletContext().getMimeType(arquivo.getAbsolutePath());
        if (mimeType == null) mimeType = "application/octet-stream";
        
        response.setContentType(mimeType);
        response.setContentLength((int) arquivo.length());

        try (FileInputStream in = new FileInputStream(arquivo);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("[ImagemServlet] SUCESSO: Bytes enviados!");
        } catch (Exception e) {
            System.out.println("[ImagemServlet] ERRO DE IO: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------");
    }
}