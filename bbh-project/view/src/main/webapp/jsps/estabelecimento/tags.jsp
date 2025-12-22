<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@page import="bbh.domain.Usuario"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Gerenciar Tags</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-tags.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detalhe-estabelecimento.css">
        <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
    </head>
    <body>
        <%@ include file="../header.jsp" %>
         
        <div class="tag-editor">
            <div class="pagina-titulo" style = "align-self: start">
                <h1>Gerenciar Tags</h1> 
            </div> 

            <div class="estabelecimento-detalhes" >

                <div class="informacao">
                    <h1>${sessionScope.usuario.nome}</h1>
                    <strong><p>Email: ${sessionScope.usuario.email}</p></strong>
                    <strong><p>Endereço: ${sessionScope.usuario.endereco}</p></strong>
                    <strong><p>Contato: ${sessionScope.usuario.contato}</p> </strong>
                </div>
            </div>
            <div class ="estabelecimento-tags" >
                <h2 style = "margin-bottom: 15px;">Tags do estabelecimento</h2>

                <c:if test="${empty tagsSelecionadas}">
                    <p style = "margin-left:10px;">Nenhuma tag associada a este estabelecimento no momento.</p>
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
                <h2 style = "margin-top:15px; margin-bottom: 10px;">Adicionar tags</h2>

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
            </div>                
        </div>
        <a class="botao-voltar-perfil" href="${pageContext.request.contextPath}/jsps/estabelecimento/perfil.jsp">
                Voltar para o Perfil
            </a>
        <%@ include file="../footer.jsp" %>
    </body>
</html>