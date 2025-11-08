<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Gerenciar Tags</title>
    <link rel="stylesheet" href="<c:url value='/css/style-tags.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style-geral.css' />">
</head>
<body>
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
                    <span> (já associado) </span>
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
</body>
</html>