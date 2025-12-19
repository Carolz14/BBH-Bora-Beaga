<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Evento"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Evento</title>

    <!-- CSS correto -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-detalhe-evento.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
</head>

<body>

<%@ include file="../header.jsp" %>

<%
    Evento evento = (Evento) request.getAttribute("evento");
    if (evento == null) {
        response.sendRedirect(request.getContextPath() + "/evento");
        return;
    }
%>

<main class="main-detalhe-evento">

    <section class="card-detalhe-evento">
        
        <div class="imagem-evento"></div>

        <div class="conteudo-detalhe">
            <h1 class="titulo-evento"><%= evento.getNome() %></h1>

            <p class="linha-info"><strong>Data:</strong> <%= evento.getData() %></p>
            <p class="linha-info"><strong>Horário:</strong> <%= evento.getHorario() %></p>

            <p class="descricao-evento">
                <%= evento.getDescricao() %>
            </p>

            <a class="botao-voltar" href="${pageContext.request.contextPath}/evento">
                Voltar para Eventos
            </a>
        </div>

    </section>

</main>

<%@ include file="../footer.jsp" %>

</body>
</html>
