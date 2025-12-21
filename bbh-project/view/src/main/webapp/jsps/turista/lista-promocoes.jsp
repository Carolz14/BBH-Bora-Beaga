<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Todas as Promoções - Bora Beagá</title>
<link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-principal.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-estab.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-listas.css"> <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
    </head>
    <body>

        <%@ include file="../header.jsp" %>

        <a href="${pageContext.request.contextPath}/bbh/feed" 
           onclick="if (document.referrer) {
               history.back();
               return false;
           }" 
           class="back-link">
            <i class="fa-solid fa-arrow-left"></i> Voltar
        </a>

        <h1>Todas as Promoções</h1>

        <main class="selecao">
            <c:choose>
                <c:when test="${not empty promocoes}">
                    <c:forEach var="p" items="${promocoes}">

                        <div class="promocao-card">

                            <div class="card-topo">
                                <p class="promocao-nome">${p.nome}</p>
                                <p class="promocao-descricao">${p.descricao}</p>
                            </div>

                            <div class="card-bottom">
                                <p class="promocao-data">
                                    <i class="fa-regular fa-calendar"></i> Válido até: ${p.data}
                                </p>

                                <a href="${pageContext.request.contextPath}/bbh/DetalheEstabelecimentoController?id=${p.idEstabelecimento}" 
                                   class="botao-submit btn-ver-local">
                                    Ver Local
                                </a>
                            </div>
                        </div>

                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p class="mensagem-vazio">Nenhuma promoção disponível no momento.</p>
                </c:otherwise>
            </c:choose>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>