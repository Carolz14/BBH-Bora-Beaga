<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Usuario"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/style-listas.css">
    <link rel="stylesheet" href="../../css/style-geral.css">
    <title>Roteiros</title>
</head>
<body>
    
    <%@ include file="../header.jsp" %>

    <div class="cabecalho-pagina">
        <h1>Roteiros</h1>
        <a href="criar-roteiro.jsp" class="btn-criar">Criar Roteiro</a>
    </div>

    <main class="selecao">
        <a href="detalhe-roteiro.jsp" class="estabelecimentos">
            <img src="../imgs/roteiro.jpeg" alt="Imagem do roteiro" class="ilustracao">
            <h3>Nome do roteiro</h3>
        </a>

        <a href="detalhe-roteiro.jsp" class="estabelecimentos">
            <img src="../imgs/roteiro.jpeg" alt="Imagem do roteiro" class="ilustracao">
            <h3>Nome do roteiro</h3>
        </a>

        <a href="detalhe-roteiro.jsp" class="estabelecimentos">
            <img src="../imgs/roteiro.jpeg" alt="Imagem do roteiro" class="ilustracao">
            <h3>Nome do roteiro</h3>
        </a>

        <a href="detalhe-roteiro.jsp" class="estabelecimentos">
            <img src="../imgs/roteiro.jpeg" alt="Imagem do roteiro" class="ilustracao">
            <h3>Nome do roteiro</h3>
        </a>
        <a href="detalhe-roteiro.jsp" class="estabelecimentos">
            <img src="../imgs/roteiro.jpeg" alt="Imagem do roteiro" class="ilustracao">
            <h3>Nome do roteiro</h3>
        </a>
        <a href="detalhe-roteiro.jsp" class="estabelecimentos">
            <img src="../imgs/roteiro.jpeg" alt="Imagem do roteiro" class="ilustracao">
            <h3>Nome do roteiro</h3>
        </a>
    </main>

    <%@ include file="../footer.jsp" %>
    
</body>
</html>