<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bora Beagá</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-estab.css">

        <script src="<%= request.getContextPath()%>/js/validarPromocao.js" defer></script>
    </head>
    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="conteudo-promo-event">
                <h1 class="pagina-titulo">Gerenciar Promoções</h1>

                <div class="promocoes-section">
                    <h2 class="section-titulo">Promoções Cadastradas</h2>

                    <div class="promocoes-list">
                        <c:choose>
                            <c:when test="${not empty promocoes}">
                                <c:forEach var="p" items="${promocoes}">
                                    <div class="promocao-card">
                                        <p class="promocao-nome">${p.nome}</p>
                                        <p class="promocao-descricao">${p.descricao}</p>
                                        <p class="promocao-data">${p.data}</p>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>Nenhuma promoção cadastrada.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="promocao-form-section">
                    <h2 class="section-titulo">Nova Promoção</h2>

                    <form name="formPromocao" method="POST"
                          action="<%= request.getContextPath()%>/bbh/CadastroPromocao">

                        <label for="nomePromocao">Nome:</label>
                        <input id="nomePromocao" type="text" name="nomePromocao" required>

                        <label for="descricaoPromocao">Descrição:</label>
                        <input id="descricaoPromocao" type="text" name="descricaoPromocao">

                        <input type="hidden" name="idEstab" value="${sessionScope.usuario.id}">

                        <label for="dataPromocao">Validade:</label>
                        <input id="dataPromocao" type="date" name="dataPromocao" required>

                        <button class="botao-submit" type="button"
                                onclick="validarCamposPromocao(document.formPromocao)">
                            Criar Promoção
                        </button>
                    </form>
                </div>
            </div>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>
