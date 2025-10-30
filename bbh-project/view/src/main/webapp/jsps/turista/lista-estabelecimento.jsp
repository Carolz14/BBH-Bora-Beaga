<%@ include file="../seguranca.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>
<%@page import="bbh.service.ControleAutorizacao"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../../style-listas.css">
        <link rel="stylesheet" href="../../style-geral.css">
        <title>Estabelecimentos</title>
    </head>
    <body>

        <<%@ include file="../header.jsp" %>

        <h1>Estabelecimentos</h1>

        <main class="selecao">

            <a href="../detalhe-estabelecimento.jsp" class="estabelecimentos">
                <img src="../imgs/restaurante.jpeg" alt="Imagem do local" class="ilustracao">
                <h3>Nome do estabelecimento</h3>

            </a>

            <a href="../detalhe-estabelecimento.jsp" class="estabelecimentos">
                <img src="../imgs/restaurante.jpeg" alt="Imagem do local" class="ilustracao">
                <h3>Nome do estabelecimento</h3>

            </a>

            <a href="../detalhe-estabelecimento.jsp" class="estabelecimentos">
                <img src="../imgs/restaurante.jpeg" alt="Imagem do local" class="ilustracao">
                <h3>Nome do estabelecimento</h3>

            </a>

            <a href="../detalhe-estabelecimento.jsp" class="estabelecimentos">
                <img src="../imgs/restaurante.jpeg" alt="Imagem do local" class="ilustracao">
                <h3>Nome do estabelecimento</h3>

            </a>
            <a href="../detalhe-estabelecimento.jsp" class="estabelecimentos">
                <img src="../imgs/restaurante.jpeg" alt="Imagem do local" class="ilustracao">
                <h3>Nome do estabelecimento</h3>

            </a>

            <a href="../detalhe-estabelecimento.jsp" class="estabelecimentos">
                <img src="../imgs/restaurante.jpeg" alt="Imagem do local" class="ilustracao">
                <h3>Nome do estabelecimento</h3>

            </a>
        </main>

        <%@ include file="../footer.jsp" %>
    </body>

</html>
