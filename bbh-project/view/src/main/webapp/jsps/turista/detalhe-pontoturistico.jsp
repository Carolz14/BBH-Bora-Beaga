<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><c:out value="${ponto.nome}" /> - Bora Beagá</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detalhe-estabelecimento.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    </head>

    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="container">

                <a href="${pageContext.request.contextPath}/bbh/feed" class="back-link">
                    <i class="fa-solid fa-arrow-left"></i> Voltar
                </a>

                <c:if test="${not empty ponto}">
                    <div class="estabelecimento">
                        <c:set var="chaveUnica" value="${ponto.id}_PONTO_TURISTICO" />

                        <a href="${pageContext.request.contextPath}/bbh/InteresseController?id=${ponto.id}&categoria=Ponto_Turistico" 
                           class="btn-favorito-detalhe" 
                           title="Salvar na lista de interesse">

                            <c:choose>
                                <c:when test="${sessionScope.idsInteresse.contains(chaveUnica)}">
                                    <i class="fa-solid fa-heart" style="color: #e74c3c;"></i> 
                                </c:when>
                                <c:otherwise>
                                    <i class="fa-regular fa-heart" style="color: #ccc;"></i> 
                                </c:otherwise>
                            </c:choose>
                        </a>
                        <div class="estabelecimento-imagem">
                            <c:choose>
                                <c:when test="${not empty ponto.imagemUrl}">
                                    <img id="imagem-ponto" src="${pageContext.request.contextPath}/imagem?nome=${ponto.imagemUrl}"
                                         alt="Imagem de ${ponto.nome}">
                                </c:when>
                                <c:otherwise>
                                    <div>
                                        <i class="fa-solid fa-camera"></i>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="estabelecimento-detalhes">
                            <h1><c:out value="${ponto.nome}" /></h1>

                            <div class="rating">
                                <span>
                                    Ponto Turístico
                                </span>
                            </div>

                            <div class="informacao">
                                <p><strong><i class="fa-solid fa-align-left"></i> Descrição:</strong> <br> ${ponto.descricao}</p>
                                <p><strong><i class="fa-solid fa-location-dot"></i> Endereço:</strong> <br> ${ponto.endereco}</p>
                            </div>
                
                            </a>
                        </div>
                    </div>
                </c:if>

                <c:if test="${empty ponto}">
                    <div>
                        <h2>Ponto Turístico não encontrado.</h2>
                        <a href="${pageContext.request.contextPath}/bbh/feed">Voltar para o início</a>
                    </div>
                </c:if>

            </div>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>