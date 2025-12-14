<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="bbh.domain.Usuario" %>
<%@page import="bbh.domain.util.UsuarioTipo" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

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
    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="../../js/modal.js" defer></script>

</head>

<body>

    <%@ include file="../header.jsp" %>

       <div class="cabecalho-pagina">
        <h1>Roteiros</h1>
        <c:if test="${sessionScope.usuario.usuarioTipo == 'GUIA'}">
            <button type="button" onclick="abrirModalCriar()" class="btn-criar">Criar Roteiro</button>
        </c:if>
    </div>

    <c:if test="${sessionScope.usuario.usuarioTipo == 'GUIA'}">
        <div class="modal-roteiro">
            <div class="modal-conteudo">
                <span class="fechar" onclick="fecharModal()">&times;</span>
                <form id="formRoteiro" method="POST">

                    <input type="hidden" name="id" id="roteiroId">
                    <h2 id="modal-titulo">Criar roteiro</h2>
                    <div class="modal-texto">
                        <h3>Nome do roteiro</h3>
                        <input type="text" name="nome" placeholder="Nome" required id="roteiroNome">
                        <h3>Descrição do roteiro</h3>
                        <input type="text" name="descricao" placeholder="Descrição" required id="roteiroDescricao">
                        
                        <h3>Locais do Roteiro</h3>
                        <label for="paradasTexto"> Digite os locais (separados por vírgula): </label>
                        <input type="text" name="paradasTexto" id="paradasTexto" rows="3" placeholder="Ex: Museu, Parque, Restaurante"></input>
                        
                        <button type="submit" class="btn">Criar Roteiro</button>
                    </div>
                </form>
            </div>
        </div>
    </c:if>
        <main class="selecao">


           <c:if test="${sessionScope.usuario.usuarioTipo == 'TURISTA'}">
           
            <div class="selecao">
                <c:forEach var="roteiro" items="${sessionScope.todosRoteiros}">
                    <a href="${pageContext.request.contextPath}/bbh/DetalheRoteiroController?id=${roteiro.id}" class="roteiros">
                        <h3>${roteiro.nome}</h3>
                    </a>
                </c:forEach>
                <c:if test="${empty todosRoteiros}">
                    <p>Nenhum roteiro cadastrado no momento.</p>
                </c:if>
            </div>
        </c:if>

        <c:if test="${sessionScope.usuario.usuarioTipo == 'GUIA'}">
            <h2>Meus Roteiros</h2>
            <div class="selecao">
                <c:forEach var="roteiro" items="${sessionScope.meusRoteiros}">
                    <div class="roteiros">
                        <a href="${pageContext.request.contextPath}/bbh/DetalheRoteiroController?id=${roteiro.id}" >
                            <h3>${roteiro.nome}</h3>
                        </a>
                        
                       <div>

                        <button type="button" class="btn-atualizar" onclick="abrirModalEditar('${roteiro.id}', '${roteiro.nome}', '${roteiro.descricao}', '${roteiro.paradas}')"> Editar </button>

                        <form action="${pageContext.request.contextPath}/bbh/ExcluirRoteiroController" method="POST" 
                              onsubmit="return confirm('Tem certeza que deseja excluir este roteiro?');">
                            <input type="hidden" name="roteiroId" value="${roteiro.id}">
                            <button type="submit" class="btn-excluir">Excluir</button>
                        </form>
                       </div>
                        
                    </div>
                </c:forEach>
                <c:if test="${empty meusRoteiros}">
                    <p>Você ainda não criou nenhum roteiro.</p>
                </c:if>
            </div>
            
            <hr>
            
            <h2>Outros Roteiros</h2>
            <div class="selecao">
                <c:forEach var="roteiro" items="${sessionScope.outrosRoteiros}">
                    <a href="${pageContext.request.contextPath}/bbh/DetalheRoteiroController?id=${roteiro.id}" class="roteiros">
                        <h3>${roteiro.nome}</h3>
                    </a>
                </c:forEach>
                <c:if test="${empty outrosRoteiros}">
                    <p>Nenhum outro roteiro cadastrado no momento.</p>
                </c:if>
            </div>
        </c:if>

        </main>

        <%@ include file="../footer.jsp" %>

</body>

</html>