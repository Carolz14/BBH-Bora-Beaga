
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@page import="bbh.domain.Usuario"%>


<!DOCTYPE html>
<html lang="pt">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bora Beagá</title>
    <link rel="stylesheet" href="../../css/style-principal.css">
    <link rel="stylesheet" href="../../css/style-geral.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
        integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>

<body>
    
    <%@ include file="../header.jsp" %>


    <main>
        <section class="welcome-section">
            <h1>Bem vindo, ${sessionScope.usuario.nome}</h1>
            <h2 class="subtitle">Venha explorar Belo Horizonte conosco</h2>

            <!-- Barra de pesquisa -->
            <div class="search-bar">
                <form action="${pageContext.request.contextPath}/pesquisarLocais" method="get">
                    <input type="text" name="nome" placeholder= "Pesquise locais de toda a grande BH!" value="${param.nome != null ? param.nome : ''}">
                </form>
            </div>

        <!-- Resultados da busca (se houver) -->
         
        <section class="resultados-section ${not empty sessionScope.resultados || not empty sessionScope.erro ? 'mostrar' : ''}">
            <c:if test="${not empty sessionScope.resultados}">
                <h2 class="resultados-titulo">Resultados da pesquisa</h2> 
                <div class="resultados-grid">
                    <c:forEach var="local" items="${sessionScope.resultados}">
                        <a href="${pageContext.request.contextPath}/bbh/DetalheEstabelecimentoController?id=${local.id}" class="resultado-card">
                            <p>${local.nome}</p>
                        </a>
                    </c:forEach>
                </div>
            </c:if>

    
    <c:if test="${empty resultados && not empty nomeBusca && empty erro}">
        <h2 class="resultados-titulo">Nenhum estabelecimento encontrado.</h2>
    </c:if>
</section>
        

            <div class="quick-filters">
                <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController" class="category-item">
                    <i class="fa-solid fa-utensils"></i>
                    <span>Restaurantes</span>
                </a>
                <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController" class="category-item">
                    <i class="fa-solid fa-landmark"></i>
                    <span>Museus</span>
                </a>
                <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController" class="category-item">
                    <i class="fa-solid fa-martini-glass"></i>
                    <span>Bares</span>
                </a>
                <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController" class="category-item">
                    <i class="fa-solid fa-tree"></i>
                    <span>Parques</span>
                </a>
                <a href="${pageContext.request.contextPath}/bbh/EstabelecimentosController" class="category-item">
                    <i class="fa-solid fa-monument"></i>
                    <span>Monumentos</span>
                </a>
            </div>
        </section>

        <section class="nearby-section">
            <h1>Perto de você</h1>
            <div class="card-grid">
                
                <a href="detalhe-estabelecimento.jsp" class="card">
                    <img src="../../imagens/restaurante.jpeg" alt="Imagem do local">
                    <p>Nome do Estabelecimento</p>
                </a>
                
                <a href="detalhe-estabelecimento.jsp" class="card">
                    <img src="../../imagens/restaurante.jpeg" alt="Imagem do local">
                    <p>Nome do Estabelecimento</p>
                </a>
                <a href="detalhe-estabelecimento.jsp" class="card">
                    <img src="../../imagens/restaurante.jpeg" alt="Imagem do local">
                    <p>Nome do Estabelecimento</p>
                </a>
                <a href="detalhe-estabelecimento.jsp" class="card">
                    <img src="../../imagens/restaurante.jpeg" alt="Imagem do local">
                    <p>Nome do Estabelecimento</p>
                </a>
                <a href="detalhe-estabelecimento.jsp" class="card">
                    <img src="../../imagens/restaurante.jpeg" alt="Imagem do local">
                    <p>Nome do Estabelecimento</p>
                </a>
                <a href="detalhe-estabelecimento.jsp" class="card">
                    <img src="../../imagens/restaurante.jpeg" alt="Imagem do local">
                    <p>Nome do Estabelecimento</p>
                </a>
            </div>
        </section>

        <section class="ranking">
            <h1>Locais do momento</h1>
            <div class="ranking-list">
                <a href="detalhe-estabelecimento.jsp" class="ranking-item">
                    <span class="rank-number">1</span>
                    <img src="../../imagens/restaurante.jpeg" alt="Imagem do local 1" class="rank-img">
                    <p class="rank-name">Nome do Estabelecimento 1</p>
                    <div class="rank-rating">
                        <i class="fas fa-star"></i>
                        <span>4.9</span>
                    </div>
                </a>
                <a href="detalhe-estabelecimento.jsp" class="ranking-item">
                    <span class="rank-number">2</span>
                    <img src="../../imagens/restaurante.jpeg" alt="Imagem do local 2" class="rank-img">
                    <p class="rank-name">Nome do Estabelecimento 2</p>
                    <div class="rank-rating">
                        <i class="fas fa-star"></i>
                        <span>4.8</span>
                    </div>
                </a>
                <a href="detalhe-estabelecimento.jsp" class="ranking-item">
                    <span class="rank-number">3</span>
                    <img src="../../imagens/restaurante.jpeg" alt="Imagem do local 3" class="rank-img">
                    <p class="rank-name">Nome do Estabelecimento 3</p>
                    <div class="rank-rating">
                        <i class="fas fa-star"></i>
                        <span>4.7</span>
                    </div>
                </a>
            </div>
        </section>
    </main>
    
    <%@ include file="../footer.jsp" %>

</body>
</html>