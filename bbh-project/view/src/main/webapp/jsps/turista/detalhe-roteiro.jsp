<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@page import="bbh.domain.util.UsuarioTipo" %>

        <!DOCTYPE html>
        <html lang="pt">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Roteiros</title>
            <link rel="stylesheet" href="../../css/style-geral.css">
            <link rel="stylesheet" href="../../css/detalhe-roteiro.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
        </head>

        <body>

            <%@ include file="../header.jsp" %>

                <main>
                    <div class="container">
                        <a href="lista-roteiros.jsp" class="back-link">Voltar</a>

                        <div class="roteiro">

                            <div class="roteiro-imagem">
                                <img src="../imgs/roteiro.jpeg" alt="Imagem roteiro">
                            </div>

                            <div class="roteiro-detalhes">

                                <h1>${roteiro.nome}<br></h1>

                                <div class="rating">
                                    <i class="fa-solid fa-star"></i>
                                    <i class="fa-solid fa-star"></i>
                                    <i class="fa-solid fa-star"></i>
                                    <i class="fa-solid fa-star"></i>
                                    <i class="fa-solid fa-star"></i>
                                </div>

                                <div class="informacao">
                                    <p>Por: ${roteiro.autor}</p>
                                    <p>${roteiro.descricao}</p>
                                </div>


                                <div class="roteiro-paradas">
                                    <h2>Paradas:</h2>
                                        <c:forEach var="parada" items="${roteiro.paradas}">
                                            <div class="parada">
                                                    <img src="${pageContext.request.contextPath}/${parada.imagemUrl}"
                                                        alt="Imagem da parada">
                                                    <h3>${parada.nome}</h3>
                                            </div>
                                        </c:forEach>

                                        <c:if test="${empty roteiro.paradas}">
                                            <p>Este roteiro não possui paradas cadastradas.</p>
                                        </c:if>

                                </div>


                            </div>
                        </div>



                    </div>

                </main>

                <%@ include file="../footer.jsp" %>

        </body>

        </html>