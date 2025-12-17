<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
 <%@taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bora Beagá - Usuários</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/usuarios.css">
     <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
</head>

<body>
    <%@ include file="../header.jsp" %>

    <main>
        <div class="container">
            <h1 class="pagina-titulo">Usuários do Sistema</h1>

            <section class="user-section">
                <h2 class="section-titulo">Turistas</h2>
                <div class="user-list">
                    <c:choose>
                        <c:when test="${not empty turistas}">
                            <c:forEach var="t" items="${turistas}">
                                <div class="user-card">
                                    <p class="user-name">${t.nome}</p>
                                    <p class="user-email">${t.email}</p>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p class="sem-usuarios">Nenhum turista ativo encontrado.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>

            <section class="user-section">
                <h2 class="section-titulo">Guias</h2>
                <div class="user-list">
                    <c:choose>
                        <c:when test="${not empty guias}">
                            <c:forEach var="g" items="${guias}">
                                <div class="user-card">
                                    <p class="user-name">${g.nome}</p>
                                    <p class="user-email">${g.email}</p>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p class="sem-usuarios">Nenhum guia ativo encontrado.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>

            <section class="user-section">
                <h2 class="section-titulo">Estabelecimentos</h2>
                <div class="user-list">
                    <c:choose>
                        <c:when test="${not empty estabelecimentos}">
                            <c:forEach var="e" items="${estabelecimentos}">
                                <div class="user-card">
                                    <p class="user-name">${e.nome}</p>
                                    <p class="user-email">${e.email}</p>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p class="sem-usuarios">Nenhum estabelecimento ativo encontrado.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>
        </div>
    </main>

    <%@ include file="../footer.jsp" %>
</body>
</html>
