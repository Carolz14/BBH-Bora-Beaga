<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="bbh.domain.RankingEstabelecimento" %>
<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <title>Ranking — Melhores Médias</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-principal.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
              integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <%@ include file="../header.jsp" %>

        <main class="container">
            <div class="ranking-block">
                <h2 style="margin-bottom:10px" >Estabelecimentos mais visitados nas ultima semana</h2>
                <div class="ranking-list">
                    <c:choose>
                        <c:when test="${not empty topVisitacoes}">
                            <c:forEach var="r" items="${topVisitacoes}" varStatus="st">
                                <a href="${pageContext.request.contextPath}/bbh/DetalheEstabelecimentoController?id=${r.idEstabelecimento}"
                                   class="ranking-item">
                                    <span class="rank-number"><c:out value="${st.index + 1}" /></span>
                                    <img src="${pageContext.request.contextPath}/imagens/restaurante.jpeg"
                                         alt="Imagem do local ${st.index + 1}" class="rank-img" />
                                    <p class="rank-name"><c:out value="${r.nomeEstabelecimento}" /></p>
                                    <div class="rank-visitacoes">
                                        <i class="fas fa-users"></i>
                                        <span><c:out value="${r.numeroDeVisitacoes}" /></span>
                                    </div>
                                    <div class="rank-rating small">
                                        <i class="fas fa-star"></i>
                                        <span><c:out value="${r.notaMedia}" /></span>
                                    </div>
                                </a>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p>Nenhum estabelecimento com visitas registradas na janela.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
        </main>

        <%@ include file="../footer.jsp" %>
    </body>
</html>
