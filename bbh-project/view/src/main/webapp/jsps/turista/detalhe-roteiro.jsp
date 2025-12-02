<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@page import="bbh.domain.util.UsuarioTipo" %>
        <%@taglib uri="jakarta.tags.core" prefix="c" %>
            <%@taglib uri="jakarta.tags.functions" prefix="fn" %>

            <%@ page import="bbh.service.GestaoRoteirosService" %>
<%@ page import="bbh.domain.Roteiro" %>
 <%@ page import="bbh.service.GestaoUsuariosService" %>
<%@ page import="bbh.domain.Usuario" %>
<%
    String idParam = request.getParameter("id");
    if (idParam != null) {
        bbh.service.GestaoRoteirosService service = new bbh.service.GestaoRoteirosService();
        bbh.domain.Roteiro roteiro = service.pesquisarPorId(Long.parseLong(idParam));
        bbh.service.GestaoUsuariosService serviceU = new bbh.service.GestaoUsuariosService();
        bbh.domain.Usuario autor = serviceU.pesquisarPorId(roteiro.getUsuarioId());
        request.setAttribute("roteiro", roteiro);
        request.setAttribute("autor", autor);
    }
%>
                <!DOCTYPE html>
                <html lang="pt">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Roteiros</title>
                    <link rel="stylesheet" href="../../css/style-geral.css">
                    <link rel="stylesheet" href="../../css/roteiros.css">
                    <link rel="stylesheet"
                        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
                </head>

                <body>

                    <%@ include file="../header.jsp" %>

                        <main>
                            <div class="container">
                                <a href="${pageContext.request.contextPath}/bbh/feed"" class="back-link">Voltar</a>

                                <div class="roteiro">


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
                                            <p>Por: ${autor.nome}</p>
                                            <p>${roteiro.descricao}</p>
                                        </div>


                                        <div class="roteiro-paradas">
                                            <h2>Paradas:</h2>
                                            <c:if test="${not empty roteiro.paradas}">
                                                <ul>

                                                    <c:forEach var="parada"
                                                        items="${fn:split(roteiro.paradas, ',')}">
                                                        ${fn:trim(parada)} <br> </li>
                                                         </c:forEach>
                                                </ul>
                                            </c:if>

                                            <c:if test="${empty roteiro.paradas}">
                                                <p>Este roteiro não tem paradas cadastradas.</p>
                                            </c:if>
                                        </div>


                                    </div>
                                </div>



                            </div>

                        </main>

                        <%@ include file="../footer.jsp" %>

                </body>

                </html>