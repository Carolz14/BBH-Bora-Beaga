<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Evento"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Evento</title>

    <link rel="stylesheet" href="../../css/eventos.css">
    <link rel="stylesheet" href="../../css/style-geral.css">
</head>

<body>

<%@ include file="../header.jsp" %>

<%
    Evento evento = (Evento) request.getAttribute("evento");
    if (evento == null) {
        response.sendRedirect("evento");
        return;
    }
%>

<main class="main-detalhe">

    <section class="card-detalhe-evento">

        <div class="evento-img grande"></div>

        <div class="detalhe-info">
            <h1><%= evento.getNome() %></h1>

            <p class="detalhe-data">
                <strong>Data:</strong> <%= evento.getData() %>
            </p>

            <p class="detalhe-horario">
                <strong>Horário:</strong> <%= evento.getHorario() %>
            </p>

            <p class="detalhe-desc">
                <%= evento.getDescricao() %>
            </p>

            <a class="btn-voltar" href="evento">
                Voltar para Eventos
            </a>
        </div>

    </section>

</main>

<%@ include file="../footer.jsp" %>

</body>
</html>