<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@page import="bbh.domain.Usuario"%>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bora Beagá</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-principal.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-estab.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
              integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
       <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
</head>



    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <section class="welcome-section">
                <h1>Bem vindo, ${sessionScope.usuario.nome}</h1>
                <h2 class="subtitle">Venha explorar Belo Horizonte conosco</h2>

                <div class="search-bar">
                    <form action="${pageContext.request.contextPath}/pesquisarLocais" method="get">
                        <input type="text" name="nome" placeholder="Pesquise locais de toda a grande BH!"
                               value="${param.nome != null ? param.nome : ''}">
                    </form>
                </div>

                <section class="resultados-section ${not empty sessionScope.resultados || not empty sessionScope.erro ? 'mostrar' : ''}">

                    <c:if test="${not empty sessionScope.erro}">
                        <h3 class="resultados-titulo" style="color: #d9534f;">${sessionScope.erro}</h3>
                    </c:if>

                    <c:if test="${not empty sessionScope.resultados}">
                        <h2 class="resultados-titulo">Resultados da pesquisa</h2>
                        <div class="resultados-grid">
                            <c:forEach var="local" items="${sessionScope.resultados}">

                                <c:choose>
                                    <c:when test="${local.categoria == 'Estabelecimento'}">
                                        <c:url var="linkDetalhe" value="/bbh/DetalheEstabelecimentoController">
                                            <c:param name="id" value="${local.id}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="linkDetalhe" value="/bbh/DetalhePontoTuristico">
                                            <c:param name="id" value="${local.id}"/>
                                        </c:url>
                                    </c:otherwise>
                                </c:choose>

                                <a href="${linkDetalhe}" class="resultado-card">
                                    <div class="resultado-img-placeholder">
                                        <i class="fa-solid fa-map-location-dot resultado-icon-placeholder"></i>
                                    </div>

                                    <div class="resultado-info">
                                        <p>${local.nome}</p>
                                        <span>${local.categoria}</span>
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                    </c:if>
                </section>

                <div class="quick-filters">
                    <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController?tag=restaurante" class="category-item">
                        <i class="fa-solid fa-utensils"></i>
                        <span>Restaurantes</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController?tag=museus" class="category-item">
                        <i class="fa-solid fa-landmark"></i>
                        <span>Museus</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController?tag=bar" class="category-item">
                        <i class="fa-solid fa-martini-glass"></i>
                        <span>Bares</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController?tag=parque" class="category-item">
                        <i class="fa-solid fa-tree"></i>
                        <span>Parques</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController?tag=monumentos" class="category-item">
                        <i class="fa-solid fa-monument"></i>
                        <span>Monumentos</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/bbh/todasPromocoes" class="category-item">
                        <i class="fa-solid fa-tags"></i>
                        <span>Promoções</span>
                    </a>
                </div>
            </section>

            <section class="nearby-section">
                <h1 class="tituloPromocao">Promoções da Galera</h1>

                <div class="promocoes-list">
                    <c:choose>
                        <c:when test="${not empty promocoes}">
                            <c:forEach var="p" items="${promocoes}" end="2">

                                <div class="promocao-card">
                                    <div class="card-topo">
                                        <p class="promocao-nome"><c:out value="${p.nome}"/></p>
                                        <p class="promocao-descricao"><c:out value="${p.descricao}"/></p>
                                    </div>

                                    <p class="promocao-data">Válido até: <c:out value="${p.data}"/></p>

                                    <a href="${pageContext.request.contextPath}/bbh/DetalheEstabelecimentoController?id=${p.idEstabelecimento}"
                                       class="botao-submit">
                                        Ver Local
                                    </a>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div id="nenhumaPromocao">
                                <p id="textoPromocao">Nenhuma promoção rolando hoje :(</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>
            <h1>Locais do momento</h1>
            <jsp:include page="/rankingHome/listar" flush="true" />
        </main>
        
        <%@ include file="../footer.jsp" %>
      
    </body>
</html>
