<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:if test="${empty sessionScope.usuario || sessionScope.usuario.usuarioTipo != 'ADMINISTRADOR'}">
    <c:redirect url="${pageContext.request.contextPath}/login.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Suporte - Administração</title>
 <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/suporte.css">
</head>
<body>

<%@ include file="../header.jsp" %>

<div class="cabecalho-pagina">
     <h1 class="titulo">Suporte - Tickets</h1>
    
  
    </div>

<main class="container-suporte">

    
    <p class="descricao">Clique em um ticket para visualizar a conversa.</p>

    <c:choose>
        <c:when test="${empty tickets}">
            <p>Nenhum ticket encontrado.</p>
        </c:when>

        <c:otherwise>
            <div class="lista-ticket">

                <h2>Em aberto / Em andamento</h2>

                <c:forEach var="t" items="${tickets}">
                    <c:if test="${t.status != 'CONCLUIDO'}">

                        <a class="card-ticket link-card"
                           href="${pageContext.request.contextPath}/bbh/suporte?acao=ver&id=${t.id}">

                            <h3>#${t.id} — ${t.assunto}</h3>

                            <p><strong>Usuário:</strong> ${t.usuarioEmail}</p>
                            <p><strong>Aberto em:</strong> ${t.dataAbertura}</p>

                            <p class="status">
                                Status:
                                <span class="${t.status}">
                                    ${t.status}
                                </span>
                            </p>

                        </a>

                    </c:if>
                </c:forEach>

                <h2 style="margin-top:40px;">Concluídos</h2>

                <c:forEach var="t" items="${tickets}">
                    <c:if test="${t.status == 'CONCLUIDO'}">

                        <a class="card-ticket link-card concluido"
                           href="${pageContext.request.contextPath}/bbh/suporte?acao=ver&id=${t.id}">

                            <h3>#${t.id} — ${t.assunto}</h3>

                            <p><strong>Usuário:</strong> ${t.usuarioEmail}</p>
                            <p><strong>Aberto em:</strong> ${t.dataAbertura}</p>

                            <p class="status">
                                <span class="CONCLUIDO">CONCLUÍDO</span>
                            </p>

                        </a>

                    </c:if>
                </c:forEach>

            </div>
        </c:otherwise>
    </c:choose>

</main>

<%@ include file="../footer.jsp" %>
</body>
</html>