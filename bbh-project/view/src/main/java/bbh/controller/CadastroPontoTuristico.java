package bbh.controller;

import bbh.domain.PontoTuristico;
import bbh.service.GestaoPontoTuristicoService;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.List;

@WebServlet("/bbh/CadastroPontoTuristico")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class CadastroPontoTuristico extends HttpServlet {

    private static final String UPLOAD_DIR = "C:" + File.separator + "bbh_imagens";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String acao = request.getParameter("acao");
            String nome = request.getParameter("nome");
            String endereco = request.getParameter("endereco");
            String descricao = request.getParameter("descricao");
            String tag = request.getParameter("tag");

            GestaoPontoTuristicoService service = new GestaoPontoTuristicoService();

            if ("cadastrar".equals(acao)) {

                String imagemUrl = realizarUpload(request);

                PontoTuristico pt = new PontoTuristico();
                pt.setNome(nome);
                pt.setEndereco(endereco);
                pt.setDescricao(descricao);
                pt.setImagemUrl(imagemUrl);
                pt.setTag(tag);

                service.cadastrar(pt);

            } else if ("atualizar".equals(acao)) {
                String idParam = request.getParameter("id");
                if (idParam != null && !idParam.isEmpty()) {
                    Long id = Long.valueOf(idParam);

                    PontoTuristico pt = new PontoTuristico();
                    pt.setId(id);
                    pt.setNome(nome);
                    pt.setEndereco(endereco);
                    pt.setDescricao(descricao);
                    pt.setTag(tag);

                    Part filePart = request.getPart("imagem");
                    if (filePart != null && filePart.getSize() > 0) {
                        String imagemUrl = realizarUpload(request);
                        pt.setImagemUrl(imagemUrl);
                    } else {
                        PontoTuristico antigo = service.buscarPorId(id);
                        if (antigo != null) {
                            pt.setImagemUrl(antigo.getImagemUrl());
                        }
                    }
                    service.atualizar(pt);
                }
            }

            response.sendRedirect(request.getContextPath() + "/bbh/CadastroPontoTuristico");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/bbh/CadastroPontoTuristico?erro=" + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            GestaoPontoTuristicoService service = new GestaoPontoTuristicoService();
            String acao = request.getParameter("acao");
            String idStr = request.getParameter("id");
            String busca = request.getParameter("busca");

            if ("excluir".equals(acao) && idStr != null) {
                service.excluir(Long.valueOf(idStr));
                response.sendRedirect(request.getContextPath() + "/bbh/CadastroPontoTuristico");
                return;
            } else if ("editar".equals(acao) && idStr != null) {
                PontoTuristico pt = service.buscarPorId(Long.valueOf(idStr));
                request.setAttribute("pontoEdit", pt);
            }
            List<PontoTuristico> lista;

            if (busca != null && !busca.trim().isEmpty()) {
                lista = service.pesquisarPorNome(busca);
            } else {
                lista = service.listarTodos();
            }

            request.setAttribute("pontos", lista);

            request.getRequestDispatcher("/jsps/admin/painel.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("ERRO NO DOGET: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
            request.getRequestDispatcher("/jsps/admin/painel.jsp").forward(request, response);
        }
    }

    private String realizarUpload(HttpServletRequest request) throws IOException, ServletException {

        // 1. Cria a pasta C:\bbh_imagens se não existir
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = null;
        for (Part part : request.getParts()) {
            if (part.getName().equals("imagem")) {
                fileName = getFileName(part);
                if (fileName != null && !fileName.isEmpty()) {
                    // Remove espaços
                    fileName = fileName.replaceAll("\\s+", "_");

                    String novoNome = UUID.randomUUID().toString() + "_" + fileName;

                    // 2. Salva o arquivo na pasta fixa
                    part.write(UPLOAD_DIR + File.separator + novoNome);

                    System.out.println("ARQUIVO SALVO EM: " + UPLOAD_DIR + File.separator + novoNome);

                    // 3. Retorna APENAS o nome do arquivo para salvar no banco
                    return novoNome;
                }
            }
        }
        return null;
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
