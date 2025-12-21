<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@page import="bbh.domain.util.UsuarioTipo" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-listas.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
        <title>${not empty tituloPagina ? tituloPagina : 'Locais'} - Bora Beagá</title>
    </head>

    <body>

        <%@ include file="../header.jsp" %>
            
        <a href="${pageContext.request.contextPath}/bbh/feed" class="back-link">
            <i class="fa-solid fa-arrow-left"></i> Voltar
        </a>
        
        <h1>${not empty tituloPagina ? tituloPagina : 'Locais Encontrados'}</h1>

        <main class="selecao">

            <c:choose>
                <c:when test="${not empty listaLocais}">
                    <c:forEach var="local" items="${listaLocais}">
                        
                        <c:choose>
                            <c:when test="${local.categoria == 'Estabelecimento'}">
                                <c:url var="linkDetalhe" value="/bbh/DetalheEstabelecimentoController">
                                    <c:param name="id" value="${local.id}"/>
                                </c:url>
                            </c:when>
                            <c:otherwise>
                                <c:url var="linkDetalhe" value="/bbh/DetalhePontoTuristico">
                                    <c:param name="id" value="${local.id}"/>
                                </c:url>
                            </c:otherwise>
                        </c:choose>

                        <a href="${linkDetalhe}" class="estabelecimentos">
                            
                            <c:choose>
                                <c:when test="${not empty local.imagemUrl}">
                                    
                                    <c:if test="${local.categoria == 'Ponto Turístico'}">
                                         <img src="${pageContext.request.contextPath}/imagem?nome=${local.imagemUrl}" 
                                              alt="${local.nome}" class="ilustracao">
                                    </c:if>
                                    
                                    <c:if test="${local.categoria == 'Estabelecimento'}">
                                         <img src="/imagens-bbh/${local.imagemUrl}" 
                                              alt="${local.nome}" class="ilustracao">
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <div class="sem-foto">
                                        <i class="fa-solid fa-image"></i>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <h3>${local.nome}</h3>
                            
                            <span class="categoria-label">${local.categoria}</span>
                        </a>

                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p class="mensagem-vazio">Nenhum local encontrado nesta categoria.</p>
                </c:otherwise>
            </c:choose>

        </main>

        <%@ include file="../footer.jsp" %>
    </body>
</html>