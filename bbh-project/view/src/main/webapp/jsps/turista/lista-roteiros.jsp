<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Usuario"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Roteiros</title>

        <link rel="stylesheet" href="../../css/style-geral.css">
        <link rel="stylesheet" href="../../css/style-listas.css">
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
                    <h3 id="modal-nome">teste</h3>
                    <p id="modal-desc">teste</p>
                </div>
            </div>
        </div>

        <main class="selecao">

            <c:forEach var="roteiro" items="${sessionScope.roteiros}">
                <a href="${pageContext.request.contextPath}/bbh/DetalheRoteiroController?id=${roteiro.id}" class="roteiros">
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