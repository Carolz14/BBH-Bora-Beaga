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

        <p class="status">
            Status:
            <span class="${ticket.status}">${ticket.status}</span>
        </p>

        <p class="data">
            Aberto em: ${ticket.dataAbertura}
        </p>

        <hr>

        <h2>Conversa</h2>

        <div class="conversa">

            <div class="mensagem outro">
                <div class="autor">Usuário</div>
                ${ticket.mensagem}
                <div class="data">${ticket.dataAbertura}</div>
            </div>

            <c:forEach var="m" items="${ticket.mensagens}">
                <c:set var="minha"
                       value="${m.autorTipo == 'ADMIN'}"/>

                <div class="mensagem ${minha ? 'eu' : 'outro'}">
                    <div class="autor">
                        ${minha ? 'Você' : 'Usuário'}
                    </div>

                    ${m.mensagem}

                    <div class="data">${m.dataFormatada}</div>
                </div>
            </c:forEach>

        </div>

        <c:if test="${ticket.status != 'CONCLUIDO'}">

            <form class="form-resposta"
                  action="${pageContext.request.contextPath}/bbh/suporte"
                  method="post">

                <input type="hidden" name="acao" value="mensagem">
                <input type="hidden" name="ticketId" value="${ticket.id}">

                <textarea name="mensagem"
                          placeholder="Responder ao usuário..."
                          required></textarea>

                <button class="btn-responder">Enviar</button>
            </form>

            <form action="${pageContext.request.contextPath}/bbh/suporte"
                  method="post">
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