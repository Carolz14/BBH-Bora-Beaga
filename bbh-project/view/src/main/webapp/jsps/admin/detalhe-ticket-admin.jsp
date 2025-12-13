<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:if test="${empty sessionScope.usuario || sessionScope.usuario.usuarioTipo != 'ADMINISTRADOR'}">
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

        <p><strong>Status:</strong>
            <span class="${ticket.status}">
                ${ticket.status}
            </span>
        </p>

        <p><strong>Aberto em:</strong> ${ticket.dataAbertura}</p>

        <hr>

        <h2>Conversa</h2>

        <div class="mensagem usuario">
            <strong>Usuário:</strong><br>
            ${ticket.mensagem}
        </div>

        <c:forEach var="m" items="${ticket.mensagens}">
            <div class="mensagem ${m.autorTipo == 'ADMIN' ? 'admin' : 'usuario'}">
                <strong>${m.autorTipo}:</strong><br>
                ${m.mensagem}
                <div class="data">${m.dataFormatada}</div>
            </div>
        </c:forEach>

        <c:if test="${ticket.status != 'CONCLUIDO'}">
            <hr>

            <form action="${pageContext.request.contextPath}/bbh/suporte" method="post">
                <input type="hidden" name="acao" value="mensagem">
                <input type="hidden" name="ticketId" value="${ticket.id}">

                <textarea name="mensagem"
                          placeholder="Digite sua resposta..."
                          required></textarea>

                <button class="btn-responder">Responder</button>
            </form>

            <form action="${pageContext.request.contextPath}/bbh/suporte"
                  method="post" style="margin-top:15px;">
                <input type="hidden" name="acao" value="concluir">
                <input type="hidden" name="id" value="${ticket.id}">

                <button class="btn-concluir">Marcar como concluído</button>
            </form>
        </c:if>

        <hr>

        <a href="${pageContext.request.contextPath}/bbh/suporte"
           class="btn-voltar">Voltar</a>

    </div>
</main>

<%@ include file="../footer.jsp" %>
</body>
</html>