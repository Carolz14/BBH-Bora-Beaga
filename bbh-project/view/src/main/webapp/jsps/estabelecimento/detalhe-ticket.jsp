<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:if test="${empty sessionScope.usuario}">
    <c:redirect url="${pageContext.request.contextPath}/login.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Ticket #${ticket.id}</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/suporte.css">
</head>
<body>

<%@ include file="../header.jsp" %>

<main class="container-suporte">

    <h1 class="titulo">Ticket #${ticket.id}</h1>

    <div class="card-ticket">

        <p><strong>Assunto:</strong> ${ticket.assunto}</p>

        <p><strong>Mensagem enviada:</strong><br>
            ${ticket.mensagem}
        </p>

        <p class="status">
            <strong>Status:</strong>
            <span class="${ticket.status}">
                ${ticket.status}
            </span>
        </p>

        <p><strong>Aberto em:</strong> ${ticket.dataAbertura}</p>

        <c:if test="${not empty ticket.resposta}">
            <hr>
            <p class="resposta-admin">
                <strong>Resposta do administrador:</strong><br>
                ${ticket.resposta}
            </p>
        </c:if>

        <hr>

        <a href="${pageContext.request.contextPath}/bbh/suporte" class="btn-voltar">
            Voltar
        </a>
    </div>
</main>

<%@ include file="../footer.jsp" %>
</body>
</html>
