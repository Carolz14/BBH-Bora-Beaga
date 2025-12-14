<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<section class="ranking">
    <h1>Locais do momento</h1>

    <div class="ranking-block">
        <h2 style="margin-bottom:10px">Top por Nota</h2>
        <div class="ranking-list">
            <c:choose>
                <c:when test="${not empty topMedias}">
                    <c:forEach var="r" items="${topMedias}" varStatus="st">
                        <a href="${pageContext.request.contextPath}/bbh/DetalheEstabelecimentoController?id=${r.idEstabelecimento}"
                           class="ranking-item">
                            <span class="rank-number"><c:out value="${st.index + 1}" /></span>
                            <img src="${pageContext.request.contextPath}/imagens/restaurante.jpeg"
                                 alt="Imagem do local ${st.index + 1}" class="rank-img" />
                            <p class="rank-name"><c:out value="${r.nomeEstabelecimento}" /></p>
                            <div class="rank-rating">
                                <i class="fas fa-star"></i>
                                <span><c:out value="${r.notaMedia}" /></span>
                            </div>
                            <div class="rank-rating subtexto">
                                
                                <span>(<c:out value="${r.numeroAvaliacoes}" /> avaliações)</span>
                            </div>
                            
                        </a>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>Nenhum estabelecimento com avaliações suficientes por enquanto.</p>
                </c:otherwise>
            </c:choose>
        </div>

        <p>
            <a href="${pageContext.request.contextPath}/rankingCompleto/listar?metric=medias">
                Ver todos por média
            </a>
        </p>
    </div>

    <div class="ranking-block">
        <h2 style="margin-bottom:10px" >Top por Visitação (últimos <c:out value="${param.dias != null ? param.dias : 7}" /> dias)</h2>
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

        <p>
            <a href="${pageContext.request.contextPath}/rankingCompleto/listar?metric=visitacoes">
                Ver todos por visitações
            </a>
        </p>
    </div>
</section>