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
        <link rel="stylesheet" href="../../css/style-estab.css">
    </head>
    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="conteudo-promo-event">
                <h1 class="texto-apresentacao">Gerenciar Promoções</h1>
                <div class="display-gerenciar-promocoes">

                    <div class="conteudo-promo-event">
                        <h1 class="texto-apresentacao">Gerenciar Promoções</h1>
                        <div class="display-gerenciar-promocoes">
                            <p>Nenhuma promoção cadastrada</p>
                        </div>

                    </div>

                    <h1 class="texto-apresentacao">Nova Promoção</h1>
                    <div class="display-cadastro-promocao">

                        <form method="POST" action="GerenciarPromocoes" enctype="multipart/form-data">
                            <input type="hidden" name="acao" value="criar">

                            <label for="nomePromocao">Nome:</label>
                            <input id="nomePromocao" type="text" name="nomePromocao" required>

                            <label for="enderecoPromocao">Endereço:</label>
                            <input id="enderecoPromocao" type="text" name="enderecoPromocao">

                            <label for="imagemPromocao">Selecione uma imagem para a promoção:</label>
                            <input type="file" id="imagemPromocao" name="imagemPromocao" accept="image/*">

                            <label for="descricaoPromocao">Descrição:</label>
                            <input id="descricaoPromocao" type="text" name="descricaoPromocao">

                            <button class="botao-submit" type="submit">Criar Promoção</button>
                        </form>
                    </div>  
                </div>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>