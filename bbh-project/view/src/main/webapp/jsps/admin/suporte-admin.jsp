<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!-- BLOQUEIO DE ACESSO -->
<c:if test="${empty sessionScope.usuario || sessionScope.usuario.usuarioTipo != 'ADMINISTRADOR'}">
    <c:redirect url='${pageContext.request.contextPath}/login.jsp'/>
</c:if>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Suporte - Administração</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/suporte.css">
</head>
<body>

<!-- HEADER ADMIN CORRETO -->
<%@ include file="../header.jsp" %>

<main class="container-suporte">

    <h1 class="titulo">Suporte - Tickets</h1>
    <p class="descricao">Todos os tickets enviados pelos usuários.</p>

    <c:choose>
        <c:when test="${empty tickets}">
            <p>Nenhum ticket encontrado.</p>
        </c:when>

        <c:otherwise>

            <div class="lista-ticket">
                <c:forEach var="t" items="${tickets}">

                    <div class="card-ticket">

                        <h3>#${t.id} — ${t.assunto}</h3>

                        <p><strong>Usuário:</strong> ${t.usuarioEmail}</p>
                        <p><strong>Aberto em:</strong> ${t.dataAbertura}</p>

                        <p>${t.mensagem}</p>

                        <p class="status">
                            Status:
                            <span class="${t.status}">
                                ${t.status}
                            </span>
                        </p>

                        <!-- Se já houver resposta, exibe -->
                        <c:if test="${not empty t.resposta}">
                            <div class="resposta-admin-bloco">
                                <strong>Resposta enviada:</strong><br>
                                ${t.resposta}
                            </div>
                        </c:if>

                        <!-- Formulário de resposta -->
                        <form action="${pageContext.request.contextPath}/bbh/suporte" method="post">

                            <input type="hidden" name="acao" value="responder">
                            <input type="hidden" name="id" value="${t.id}">

                            <textarea name="resposta" placeholder="Responder..." required></textarea>

                            <!-- Status AGORA É AUTOMÁTICO NO SERVICE -->
                            <button class="btn-responder">Responder</button>
                        </form>

                        <!-- Botão de concluir -->
                        <form action="${pageContext.request.contextPath}/bbh/suporte" method="post" style="margin-top:10px;">
                            <input type="hidden" name="acao" value="concluir">
                            <input type="hidden" name="id" value="${t.id}">

                            <button class="btn-concluir">Marcar concluído</button>
                        </form>

                    </div>

                </c:forEach>
            </div>

        </c:otherwise>
    </c:choose>

</main>

<%@ include file="../footer.jsp" %>
</body>
</html>
