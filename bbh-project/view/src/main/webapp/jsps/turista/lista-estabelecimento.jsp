<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@page import="bbh.domain.util.UsuarioTipo" %>
        <%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
            <%@taglib uri="jakarta.tags.core" prefix="c" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <link rel="stylesheet" href="../../css/style-listas.css">
                    <link rel="stylesheet" href="../../css/style-geral.css">
                    <title>Estabelecimentos</title>
                </head>

                <body>

                    <%@ include file="../header.jsp" %>
                        
                        <h1>Estabelecimentos</h1>

                        <main class="selecao">

                            <c:forEach var="estab" items="${sessionScope.estabelecimentos}">
                                <a href="${pageContext.request.contextPath}/bbh/DetalheEstabelecimentoController?id=${estab.id}"
                                    class="estabelecimentos">
                                    <img src="/imagens-bbh/${estab.imagemUrl}"
         alt="Imagem de ${estab.nome}" class="ilustracao">
                                    <h3>${estab.nome}</h3>

                                </a>
                            </c:forEach>
                            <c:if test="${empty estabelecimentos}">
                                <p>Nenhum estabelecimento cadastrado.</p>
                            </c:if>

                        </main>

                        <%@ include file="../footer.jsp" %>
                </body>

                </html>