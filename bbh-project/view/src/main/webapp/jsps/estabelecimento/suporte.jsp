<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:if test="${empty sessionScope.usuario}">
    <c:redirect url="${pageContext.request.contextPath}/login.jsp" />
</c:if>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Suporte</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/suporte.css">
</head>

<body>

<%@ include file="../header.jsp" %>

<main class="container-suporte">

    <h1 class="titulo">Suporte</h1>
    <p class="descricao">Envie dúvidas, problemas ou sugestões ao administrador.</p>

    <section class="abrir-ticket">
        <h2>Abrir novo ticket</h2>

        <form method="POST" action="${pageContext.request.contextPath}/bbh/suporte">
            <input type="hidden" name="acao" value="abrir">

            <label>Assunto:</label>
            <input type="text" name="titulo" required>

            <label>Mensagem:</label>
            <textarea name="mensagem" required></textarea>

            <button class="btn-enviar">Enviar</button>
        </form>
    </section>

    <section class="meus-tickets">
        <h2>Meus Tickets</h2>

        <c:choose>
            <c:when test="${empty meusTickets}">
                <p>Nenhum ticket encontrado.</p>
            </c:when>

            <c:otherwise>
                <div class="lista-ticket">

                    <c:forEach var="ticket" items="${meusTickets}">
                        <a class="card-ticket link-card"
                           href="${pageContext.request.contextPath}/bbh/suporte?acao=ver&id=${ticket.id}">

                            <h3>#${ticket.id} — ${ticket.assunto}</h3>

                            <p class="status">
                                Status:
                                <span class="${ticket.status}">
                                    ${ticket.status}
                                </span>
                            </p>

                            <p class="data">
                                Aberto em: ${ticket.dataAbertura}
                            </p>

                        </a>
                    </c:forEach>

                </div>
            </c:otherwise>
        </c:choose>

    </section>

</main>

<%@ include file="../footer.jsp" %>
</body>
</html>
