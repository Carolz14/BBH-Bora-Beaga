<%@ include file="../seguranca.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Usuario"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>
<%@page import="bbh.service.ControleAutorizacao"%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bora Beagá</title>
    <link rel="stylesheet" href="../../css/style-geral.css">
    <link rel="stylesheet" href="../../css/painel.css">
</head>
<body>
    
    <%@ include file="../header.jsp" %>

    <main>
        <div class="container">
            <div class="perfil">
                <div class="perfil-form">
                    <h2 class="pagina-titulo">Cadastrar ponto turístico</h2>
                    
                    <form method="POST" action="CadastrarPonto" enctype="multipart/form-data">
                        <div class="form-group">
                            <input style="min-height: 40px;" type="text" id="nome" name="nome" placeholder="Nome" required>
                        </div>
                        <div class="form-group">
                            <input style="min-height: 40px;" type="text" id="endereco" name="endereco" placeholder="Endereço" required>
                        </div>
                        <div class="form-group">
                            <input style="min-height: 100px;" type="text" id="descricao" name="descricao" placeholder="Descrição">
                        </div>
                        <div class="form-group">
                            <input type="file" id="imagem-upload" name="imagem" accept="image/*" style="display: none;">
                            <label for="imagem-upload" class="upload-btn">Escolher Imagem</label>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="submit-btn">Cadastrar Ponto Turístico</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>
    
    <%@ include file="../footer.jsp" %>
    
</body>
</html>