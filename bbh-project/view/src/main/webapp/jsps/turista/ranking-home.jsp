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
                <c:choose>
                    <c:when test="${not empty topVisitacoes}">
                        <c:forEach var="r" items="${topVisitacoes}" varStatus="st">
                            <a href="${pageContext.request.contextPath}/bbh/DetalheEstabelecimentoController?id=${r.idEstabelecimento}" class="ranking-item">
                                <span class="rank-number"><c:out value="${st.index + 1}" /></span>
                              <img src="/imagens-bbh/${r.imagemUrl}"
                                     alt="Imagem de ${r.nomeEstabelecimento}">
                                <p class="rank-name"><c:out value="${r.nomeEstabelecimento}" /></p>
                                <div class="rank-visitacoes">
                                    <i class="fas fa-users"></i> <span><c:out value="${r.numeroDeVisitacoes}" /></span>
                                </div>
                            </a>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p style="padding:20px; text-align:center;">Nenhuma visitação registrada neste período.</p>
                    </c:otherwise>
                </c:choose>
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
                <c:choose>
                    <c:when test="${not empty topMedias}">
                        <c:forEach var="r" items="${topMedias}" varStatus="st">
                            <a href="${pageContext.request.contextPath}/bbh/DetalheEstabelecimentoController?id=${r.idEstabelecimento}" class="ranking-item">
                                <span class="rank-number"><c:out value="${st.index + 1}" /></span>
                                <img src="/imagens-bbh/${r.imagemUrl}"
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
                    </c:when>
                    <c:otherwise>
                        <p style="padding:20px; text-align:center;">Nenhuma avaliação registrada.</p>
                    </c:otherwise>
                </c:choose>
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