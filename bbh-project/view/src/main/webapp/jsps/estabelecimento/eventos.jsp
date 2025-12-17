<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Eventos</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/eventos.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style-estab.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style-geral.css">
    <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
</head>

<body>

<%@ include file="../header.jsp" %>

<main>
    <div class="conteudo-eventos-estab">

        <!-- TÍTULO PRINCIPAL -->
        <h1 class="eventos-titulo">Gerenciar Eventos</h1>

        <!-- LISTA DE EVENTOS CADASTRADOS -->
        <div class="eventos-section">
            <h2 class="eventos-titulo">Eventos Cadastrados</h2>

            <div class="eventos-list">

                <c:choose>
                    <c:when test="${not empty meusEventos}">
                        <c:forEach var="e" items="${meusEventos}">

                            <div class="evento-card">

                                <div class="card-topo">
                                    <p class="evento-nome">${e.nome}</p>
                                    <p class="evento-desc">${e.descricao}</p>
                                </div>

                                <p class="evento-data">
                                    ${e.data} — ${e.horario}
                                </p>

                                <!-- BOTÕES -->
                                <div class="card-actions">

                                    <!-- EDITAR (expande formulário abaixo) -->
                                    <a href="#" class="btn-acao btn-editar"
                                       onclick="document.getElementById('formEdit${e.id}').classList.toggle('show'); return false;">
                                        Editar
                                    </a>

                                    <!-- EXCLUIR (POST real, sem GET) -->
                                    <form action="${pageContext.request.contextPath}/evento?action=delete"
                                          method="POST"
                                          onsubmit="return confirm('Excluir este evento?');"
                                          style="display:inline;">
                                        <input type="hidden" name="id" value="${e.id}">
                                        <button type="submit" class="btn-acao btn-excluir">Excluir</button>
                                    </form>
                                </div>

                                <!-- FORMULÁRIO DE EDIÇÃO -->
                                <form id="formEdit${e.id}"
                                      class="evento-form"
                                      action="${pageContext.request.contextPath}/evento?action=update"
                                      method="POST"
                                      style="display:none; margin-top:15px;">

                                    <input type="hidden" name="id" value="${e.id}">

                                    <label>Nome</label>
                                    <input type="text" name="nome" value="${e.nome}" required>

                                    <label>Descrição</label>
                                    <textarea name="descricao">${e.descricao}</textarea>

                                    <label>Data</label>
                                    <input type="date" name="dataEvento" value="${e.data}" required>

                                    <label>Horário</label>
                                    <input type="time" name="horarioEvento" value="${e.horario}" required>

                                    <button class="btn-criar" type="submit">Salvar Alterações</button>
                                </form>

                            </div>

                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>Nenhum evento cadastrado.</p>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <!-- FORMULÁRIO DE NOVO EVENTO -->
        <div class="evento-form-section">
            <h2 class="eventos-titulo">Criar Novo Evento</h2>

            <form name="formEvento"
                  method="POST"
                  action="${pageContext.request.contextPath}/evento?action=add">

                <label>Nome do evento</label>
                <input type="text" name="nome" required>

                <label>Descrição</label>
                <textarea name="descricao"></textarea>

                <label>Data</label>
                <input type="date" name="dataEvento" required>

                <label>Horário</label>
                <input type="time" name="horarioEvento" required>

                <button class="btn-criar" type="submit">Criar Evento</button>
            </form>
        </div>

    </div>
</main>

<%@ include file="../footer.jsp" %>

</body>
</html>
