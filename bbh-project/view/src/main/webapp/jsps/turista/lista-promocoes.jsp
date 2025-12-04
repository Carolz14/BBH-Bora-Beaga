<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todas as Promoções - Bora Beagá</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-principal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-estab.css"> <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
</head>
<body>

    <%@ include file="../header.jsp" %>

    <main>
        <section class="nearby-section" style="margin-top: 50px; min-height: 60vh;">
            <div style="display: flex; align-items: center; margin-left: 5%; margin-bottom: 30px;">
                <a href="${pageContext.request.contextPath}/bbh/feed" style="text-decoration: none; font-size: 20px; margin-right: 15px; color: #333;">
                    <i class="fa-solid fa-arrow-left"></i> Voltar
                </a>
                <h1 style="margin: 0;">Todas as Promoções</h1>
            </div>

            <div class="promocoes-list" style="justify-content: center;">
                <c:choose>
                    <c:when test="${not empty promocoes}">
                        <c:forEach var="p" items="${promocoes}">

                            <div class="promocao-card">
                                <div class="card-topo">
                                    <p class="promocao-nome">${p.nome}</p>
                                    <p class="promocao-descricao">${p.descricao}</p>
                                </div>

                                <p class="promocao-data">Válido até: ${p.data}</p>

                                <a href="${pageContext.request.contextPath}/bbh/DetalheEstabelecimentoController?id=${p.idEstabelecimento}" 
                                   class="botao-submit">
                                    Ver Local
                                </a>
                            </div>

                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p style="text-align: center; width: 100%;">Nenhuma promoção disponível no momento.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
    </main>

    <%@ include file="../footer.jsp" %>

</body>
</html>