<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="bbh.domain.util.UsuarioTipo" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@taglib uri="jakarta.tags.functions" prefix="fn" %>



<!DOCTYPE html>
<html lang="pt">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roteiros</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roteiros.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/avaliacao-roteiro.css">
    <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
    <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
</head>

<body>

    <%@ include file="../header.jsp" %>

        <main>
            <div class="container">
                <a href="${pageContext.request.contextPath}/bbh/ListarRoteiroController" class="back-link">Voltar</a>

                <div class="roteiro">
                    <div class="roteiro-detalhes">

                        <h1>${roteiro.nome}<br></h1>

                        <div class="rating">
                            <span style="font-size: 1.2em; font-weight: bold; margin-right: 10px;">
                                ${mediaNota}
                            </span>
                            <form
                                action="${pageContext.request.contextPath}/bbh/AvaliarRoteiroController"
                                method="POST" class="avaliacao-form">
                                <input type="hidden" name="roteiroId" value="${roteiro.id}">

                                <div class="estrelas">
                                    <input type="radio" id="e5" name="nota" value="5"
                                        onchange="this.form.submit()" ${minhaNota==5 ? 'checked' : '' }>
                                    <label for="e5" title="5 estrelas"><i
                                            class="fa-solid fa-star"></i></label>

                                    <input type="radio" id="e4" name="nota" value="4"
                                        onchange="this.form.submit()" ${minhaNota==4 ? 'checked' : '' }>
                                    <label for="e4" title="4 estrelas"><i
                                            class="fa-solid fa-star"></i></label>

                                    <input type="radio" id="e3" name="nota" value="3"
                                        onchange="this.form.submit()" ${minhaNota==3 ? 'checked' : '' }>
                                    <label for="e3" title="3 estrelas"><i
                                            class="fa-solid fa-star"></i></label>

                                    <input type="radio" id="e2" name="nota" value="2"
                                        onchange="this.form.submit()" ${minhaNota==2 ? 'checked' : '' }>
                                    <label for="e2" title="2 estrelas"><i
                                            class="fa-solid fa-star"></i></label>

                                    <input type="radio" id="e1" name="nota" value="1"
                                        onchange="this.form.submit()" ${minhaNota==1 ? 'checked' : '' }>
                                    <label for="e1" title="1 estrela"><i
                                            class="fa-solid fa-star"></i></label>
                                    <input type="radio" id="t" name="nota" value="0"
                                        onchange="this.form.submit()" ${minhaNota==0 ? 'checked' : '' }>
                                    <label id="t" for="t" title="excluir"><i
                                            class="fa-solid fa-trash"></i></label>
                                </div>


                            </form>
                        </div>

                        <div class="informacao">
                            <p>Por: ${autor.nome}</p>
                            <p>${roteiro.descricao}</p>
                        </div>


                        <div class="roteiro-paradas">
                            <h2>Paradas:</h2>
                            <c:if test="${not empty roteiro.paradas}">
                                <ul>
                                    <c:forEach var="parada" items="${fn:split(roteiro.paradas, ',')}">
                                        <li> ${fn:trim(parada)} </li>
                                    </c:forEach>
                                </ul>
                            </c:if>

                            <c:if test="${empty roteiro.paradas}">
                                <p>Este roteiro não tem paradas cadastradas.</p>
                            </c:if>
                        </div>



                    </div>
                </div>


                <%@ include file="forum.jsp" %>
            </div>

        </main>

        <%@ include file="../footer.jsp" %>

</body>

</html>