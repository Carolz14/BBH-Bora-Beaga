<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Gerenciar Tags</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/css/style-tags.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style-geral.css' />">
     <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
</head>
<body>
   <div class="pagecontainer">
        <h2>Tags do estabelecimento</h2>
        <c:if test="${empty tagsSelecionadas}">
            <p>Nenhuma tag associada.</p>
        </c:if>

        <c:forEach var="tag" items="${tagsSelecionadas}">
            <div class="tag">
                <span>${tag.nome}</span>

                <form method="post" action="${pageContext.request.contextPath}/estabelecimento/tags/update">
                    <input type="hidden" name="removerTagId" value="${tag.id}" />
                    <button type="submit" title="Remover ${tag.nome}">x</button>
                </form>
            </div>
        </c:forEach>

        <hr/>

        <h2>Adicionar tags</h2>

        <c:if test="${empty tags}">
            <p>Não há tags cadastradas.</p>
        </c:if>

        <c:forEach var="tag" items="${tags}">
            <c:set var="selected" value="false" scope="page" />
            <c:if test="${not empty tagsSelecionadas}">
                <c:forEach var="sel" items="${tagsSelecionadas}">
                    <c:if test="${sel.id == tag.id}">
                        <c:set var="selected" value="true" scope="page" />
                    </c:if>
                </c:forEach>
            </c:if>

            <div class="tag">
                <span>${tag.nome}</span>

                <c:choose>
                    <c:when test="${selected}">
                        <span></span>
                    </c:when>
                    <c:otherwise>
                        <form method="post" action="${pageContext.request.contextPath}/estabelecimento/tags/update">
                            <input type="hidden" name="adicionarTagId" value="${tag.id}" />
                            <button type="submit" title="Adicionar ${tag.nome}">+</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
        <div style="margin-top: 20px;">
            <a class="botao-voltar-perfil" href="${pageContext.request.contextPath}/jsps/estabelecimento/perfil.jsp">
                Voltar para o Perfil
            </a>
        </div>
        <%@ include file="../footer.jsp" %>
   </div>
</body>
</html>