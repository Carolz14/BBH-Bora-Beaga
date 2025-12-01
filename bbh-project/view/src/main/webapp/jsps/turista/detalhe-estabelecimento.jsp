<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><c:out value="${estabelecimento.nome}" /> - Bora Beagá</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/avaliacao.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detalhe-estabelecimento.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    </head>

    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="container">

                <a href="${pageContext.request.contextPath}/jsps/turista/pagina-principal.jsp" class="back-link">Voltar</a>

                <c:if test="${not empty estabelecimento}">
                    <div class="estabelecimento">
                        <div class="estabelecimento-imagem">
                            <img src="${pageContext.request.contextPath}/imagens/restaurante.jpeg" alt="Imagem do estabelecimento" />
                        </div>

                        <div class="estabelecimento-detalhes">
                            <h1><c:out value="${estabelecimento.nome}" /></h1>

                            <div class="rating">
                                <span class="nota-badge"><c:out value="${media}" /></span>
                                <c:forEach var="i" begin="1" end="${media}">
                                    <img src="<c:url value='/imagens/estrela-png.png' />"
                                         alt="" aria-hidden="true" class="nota-star" />
                                </c:forEach>
                                <small>(baseada em <c:out value="${avaliacoes.size()}" /> avaliações)</small>
                            </div>

                            <div class="informacao">
                                <p><strong>Contato:</strong> <c:out value="${estabelecimento.contato}" /></p>
                                <p><strong>Endereço:</strong> <c:out value="${estabelecimento.endereco}" /></p>
                            </div>
                        </div>
                    </div>

                    <jsp:include page="/avaliacao/listar" flush="true">
                        <jsp:param name="id" value="${estabelecimento.id}" />
                    </jsp:include>

                </c:if>

                <c:if test="${empty estabelecimento}">
                    <p>Estabelecimento não encontrado.</p>
                </c:if>

            </div>
        </main>
        <%@ include file="../footer.jsp" %>
    </body>
</html>
