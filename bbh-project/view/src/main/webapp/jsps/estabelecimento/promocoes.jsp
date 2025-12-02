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
                                        <div class="card-topo">
                                            <p class="promocao-nome">${p.nome}</p>
                                            <p class="promocao-descricao">${p.descricao}</p>
                                        </div>
                                        <p class="promocao-data">Válido até: ${p.data}</p>

                                        <div class="card-actions">
                                            <a href="<%= request.getContextPath()%>/bbh/CadastroPromocao?acao=carregarEdicao&id=${p.id}" class="btn-acao btn-editar">Editar</a>

                                            <a href="<%= request.getContextPath()%>/bbh/CadastroPromocao?acao=excluir&id=${p.id}" class="btn-acao btn-excluir" onclick="return confirm('Tem certeza que deseja excluir esta promoção?');">Excluir</a>
                                        </div>
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
                    <h2 class="section-titulo">${not empty promocaoEdit ? 'Editar Promoção' : 'Nova Promoção'}</h2>

                    <form name="formPromocao" method="POST"
                          action="<%= request.getContextPath()%>/bbh/CadastroPromocao">

                        <input type="hidden" name="idPromocao" value="${promocaoEdit.id}">

                        <input type="hidden" name="acao" value="${not empty promocaoEdit ? 'atualizar' : 'cadastrar'}">

                        <label for="nomePromocao">Nome:</label>
                        <input id="nomePromocao" type="text" name="nomePromocao" value="${promocaoEdit.nome}" required>

                        <label for="descricaoPromocao">Descrição:</label>
                        <input id="descricaoPromocao" type="text" name="descricaoPromocao" value="${promocaoEdit.descricao}">

                        <input type="hidden" name="idEstab" value="${sessionScope.usuario.id}">

                        <label for="dataPromocao">Validade:</label>
                        <input id="dataPromocao" type="date" name="dataPromocao" value="${promocaoEdit.data}" required>

                        <div style="display: flex; gap: 10px; align-items: center;">
                            <button class="botao-submit" type="button"
                                    onclick="validarCamposPromocao(document.formPromocao)">
                                ${not empty promocaoEdit ? 'Salvar Alterações' : 'Criar Promoção'}
                            </button>

                            <c:if test="${not empty promocaoEdit}">
                                <a href="promocoes.jsp" style="color: #666; text-decoration: none; font-size: 14px;">Cancelar</a>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>