<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="bbh.domain.RankingEstabelecimento" %>
<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <title>Ranking — Mais visitações</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
              integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ranking.css">
        <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">

    </head>
    <body>
        <%@ include file="../header.jsp" %>

        <main class="container" >
            <a href="${pageContext.request.contextPath}/bbh/feed" 
               onclick="if (document.referrer) {
               history.back();
               return false;
           }" 
               class="back-link">
                <i class="fa-solid fa-arrow-left"></i> Voltar
            </a>
            <c:set var="initialDias" value="${not empty janelaDias ? janelaDias : (param.dias != null ? param.dias : 7)}" />

            <h2 id="ranking-title">
                <c:choose>
                    <c:when test="${initialDias == 1}">
                        Estabelecimentos mais visitados no último dia
                    </c:when>
                    <c:when test="${initialDias == 7}">
                        Estabelecimentos mais visitados na última semana
                    </c:when>
                    <c:otherwise>
                        Estabelecimentos mais visitados nos últimos <c:out value="${initialDias}" /> dias
                    </c:otherwise>
                </c:choose>
            </h2>
            <div class="layout-with-sidebar">
                <!-- SIDEBAR de filtros -->
                <aside class="filter-sidebar">
                    <!-- mantenha aqui o seu markup .filter-dropdown tal como está (botão toggle + .filter-panel) -->
                    <div class="filter-dropdown" data-open="false">
                        <button type="button" class="filter-toggle" aria-expanded="false" aria-controls="filter-panel">
                            <span class="title">Filtros</span>
                            <i class="fas fa-chevron-down arrow" aria-hidden="true"></i>
                        </button>

                        <div id="filter-panel" class="filter-panel" hidden>
                            <!-- aqui vai o form idêntico ao que você já tinha -->
                            <form action="${pageContext.request.contextPath}/rankingCompleto/listar"
                                  method="get"
                                  class="ranking-filter-form">

                                <input type="hidden" name="metric"
                                       value="${param.metric != null ? param.metric : 'medias'}" />

                                <label class="field">
                                    <span class="label-text">Nota mínima:</span>
                                    <input type="number" name="minNota" step="0.1" min="0" max="5"
                                           value="${not empty minNota ? minNota : (param.minNota != null ? param.minNota : '')}" />
                                </label>

                                <label class="field">
                                    <span class="label-text">Nº mínimo de avaliações:</span>
                                    <input type="number" name="minRatings" min="0"
                                           value="${not empty minRatings ? minRatings : (param.minRatings != null ? param.minRatings : '3')}" />
                                </label>

                                <label class="field">
                                    <span class="label-text">Janela (dias):</span>
                                    <input type="number" name="dias" min="0" id="f-dias"
                                           value="${not empty janelaDias ? janelaDias : (param.dias != null ? param.dias : '7')}" />
                                </label>

                                <label class="field">
                                    <span class="label-text">Limite de resultados:</span>
                                    <input type="number" name="limit" min="1" max="200"
                                           value="${not empty limiteDeBuscas ? limiteDeBuscas : (param.limit != null ? param.limit : '10')}" />
                                </label>

                                <div class="form-actions">
                                    <button type="submit" class="btn-primary">Aplicar filtros</button>

                                    <a class="btn-link"
                                       href="${pageContext.request.contextPath}/rankingCompleto/listar?metric=${param.metric != null ? param.metric : 'medias'}">
                                        Limpar filtros
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>
                </aside>
                <div class="ranking-block">
                    <div class="ranking-list">
                        <c:choose>
                            <c:when test="${not empty topVisitacoes}">
                                <c:forEach var="r" items="${topVisitacoes}" varStatus="st">

                                    <c:url var="linkDestino" value="">
                                        <c:choose>
                                            <c:when test="${r.tipo == 'ESTABELECIMENTO'}">
                                                <c:set var="baseUrl" value="/bbh/DetalheEstabelecimentoController" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="baseUrl" value="/bbh/DetalhePontoTuristico" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:set var="finalUrl" value="${baseUrl}" />
                                        <c:param name="id" value="${r.idEstabelecimento}"/>
                                    </c:url>
                                    <c:choose>
                                        <c:when test="${r.tipo == 'ESTABELECIMENTO'}">
                                            <c:url var="linkDestino" value="/bbh/DetalheEstabelecimentoController">
                                                <c:param name="id" value="${r.idEstabelecimento}"/>
                                            </c:url>
                                        </c:when>
                                        <c:otherwise>
                                            <c:url var="linkDestino" value="/bbh/DetalhePontoTuristico">
                                                <c:param name="id" value="${r.idEstabelecimento}"/>
                                            </c:url>
                                        </c:otherwise>
                                    </c:choose>

                                    <a href="${linkDestino}" class="ranking-item">
                                        <span class="rank-number"><c:out value="${st.index + 1}" /></span>
                                        <img class ="rank-img" src="${pageContext.request.contextPath}/imagem?nome=${r.imagemUrl}"
                                             alt="Imagem de ${r.nomeEstabelecimento}">
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
                </div>


        </main>

        <%@ include file="../footer.jsp" %>
        <script src="${pageContext.request.contextPath}/js/ranking-filtro-dropdown.js"></script>
    </body>

</html>
