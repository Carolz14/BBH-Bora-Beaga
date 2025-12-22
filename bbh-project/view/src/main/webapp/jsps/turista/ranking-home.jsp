<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="abaInicial" value="${param.metric == 'medias' ? 'medias' : 'visitacoes'}" />

<section class="carousel-container" aria-label="Rankings do Momento">


    <div class="carousel-controls">
        <div class="tab-group">
            <button type="button" class="tab-btn" data-target="visitacoes">Mais visitados</button>
            <button type="button" class="tab-btn" data-target="medias">Melhor avaliados</button>
        </div>
    </div>

    <div class="slides-wrapper">

        <div id="visitacoes" class="slide-content">
            <h2 style="margin-bottom:15px">Mais visitados na última semana</h2>

            <div class="ranking-list">
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
                    </a>
                </c:forEach>
            </div>

            <div style="text-align:center; margin-top:20px; display: flex; justify-content: center">
                <a class="botao-submit" style="padding: 15px 20px; width:400px;" href="${pageContext.request.contextPath}/rankingCompleto/listar?metric=visitacoes">
                    Ver ranking completo
                </a>
            </div>
        </div>

        <div id="medias" class="slide-content">
            <h2 style="margin-bottom:15px">Melhor avaliados</h2>

            <div class="ranking-list">
                <c:forEach var="r" items="${topMedias}" varStatus="st">

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

                        <img class="rank-img" src="${pageContext.request.contextPath}/imagem?nome=${r.imagemUrl}"
                             alt="Imagem de ${r.nomeEstabelecimento}">

                        <p class="rank-name"><c:out value="${r.nomeEstabelecimento}" /></p>

                        <div class="rank-rating">
                            <i class="fas fa-star"></i> <span><c:out value="${r.notaMedia}" /></span>
                        </div>

                        <div class="rank-rating subtexto">
                            <span>(<c:out value="${r.numeroAvaliacoes}" /> avaliações)</span>
                        </div>
                    </a>
                </c:forEach>
            </div>

            <div style="text-align:center; margin-top:20px; display: flex; justify-content: center">
                <a class="botao-submit" style=" padding: 15px 20px; width: 400px; " href="${pageContext.request.contextPath}/rankingCompleto/listar?metric=medias">
                    Ver ranking completo
                </a>
            </div>
        </div>

    </div>

    <div id="config-inicial" data-start="${abaInicial}"></div>
    <script src="${pageContext.request.contextPath}/js/carrosel-ranking.js"></script>
</section>