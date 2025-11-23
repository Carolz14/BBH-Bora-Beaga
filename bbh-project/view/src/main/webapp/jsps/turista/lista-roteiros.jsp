<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@page import="bbh.domain.Usuario" %>
        <%@page import="bbh.domain.util.UsuarioTipo" %>

            <!DOCTYPE html>
            <html lang="pt">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Roteiros</title>

                <link rel="stylesheet" href="../../css/style-geral.css">
                <link rel="stylesheet" href="../../css/style-listas.css">
                <link rel="stylesheet" href="../../css/roteiros.css">
                <link rel="icon" href="../../imagens/icon-page.png">
                <script src="../../js/modal.js" defer></script>

            </head>

            <body>

                <%@ include file="../header.jsp" %>
                    <div class="cabecalho-pagina">
                        <h1>Roteiros</h1>
                        <button type="button" onclick="abrirModal()" class="btn-criar">Criar Roteiro</button>
                    </div>

                    <div class="modal-roteiro">
                        <div class="modal-conteudo">
                            <span class="fechar">&times;</span>
                            <div class="modal-texto">
                                <h3>Nome do roteiro</h3>
                                <input type="text" name="nome" id="nome" placeholder="Nome" required>
                                <h3>Descrição do roteiro</h3>
                                <input type="text" name="descricao" id="descricao" placeholder="Descrição" required>
                                <h3>Imagem do roteiro</h3>
                                <input type="file" src="" alt="">
                                <h3>Escolha de locais</h3>
                                <div class="search-bar">
                                    <form action="${pageContext.request.contextPath}/pesquisarLocais" method="get">
                                        <input type="text" name="nome"
                                            placeholder="Adicione locais ao seu roteiro"
                                            value="${param.nome != null ? param.nome : ''}">
                                    </form>
                                </div>

                                <!-- Resultados da busca (se houver) -->

                                <section
                                    class="resultados-section ${not empty sessionScope.resultados || not empty sessionScope.erro ? 'mostrar' : ''}">
                                    <c:if test="${not empty sessionScope.resultados}">

                                        <div class="resultados-grid">
                                            <c:forEach var="local" items="${sessionScope.resultados}"> 
                                                    <p>${local.nome}</p>
                                            </c:forEach>
                                        </div>
                                    </c:if>

                                    <c:if test="${empty resultados && not empty nomeBusca && empty erro}">
                                        <h2 class="resultados-titulo">Nenhum estabelecimento encontrado.</h2>
                                    </c:if>
                                    <button type="button" class="btn-criar">Criar Roteiro</button>
                            </div>
                        </div>
                    </div>

                    <main class="selecao">

                        <c:forEach var="roteiro" items="${sessionScope.roteiros}">
                            <a href="${pageContext.request.contextPath}/bbh/DetalheRoteiroController?id=${roteiro.id}"
                                class="roteiros">
                                <img src="../../imagens/restaurante.jpeg" alt="Imagem do local" class="ilustracao">
                                <h3>${roteiro.nome}</h3>
                            </a>
                        </c:forEach>
                        <c:if test="${empty roteiros}">
                            <p>Nenhum roteiro cadastrado.</p>
                        </c:if>

                    </main>

                    <%@ include file="../footer.jsp" %>

            </body>

            </html>