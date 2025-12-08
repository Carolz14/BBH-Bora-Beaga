<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Evento"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Eventos</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-eventos-estab.css">
</head>

<body>

<%@ include file="../header.jsp" %>

<main>
    <div class="conteudo-eventos-estab">

        <h1 class="eventos-titulo">Criar novo evento</h1>

        <form class="evento-form" action="${pageContext.request.contextPath}/evento?action=add" method="POST">

            <label>Nome do evento</label>
            <input type="text" name="nome" required>

            <label>Data</label>
            <input type="date" name="dataEvento" required>

            <label>Horário</label>
            <input type="time" name="horarioEvento" required>

            <label>Descrição</label>
            <textarea name="descricao"></textarea>

            <button class="btn-criar" type="submit">Criar Evento</button>
        </form>

       
        <h2 class="eventos-titulo" style="margin-top:40px;">Meus próximos eventos</h2>

        <div class="eventos-list">
            <%
                List<Evento> meus = (List<Evento>) request.getAttribute("meusEventos");

                if (meus != null && !meus.isEmpty()) {
                    for (Evento e : meus) {
            %>

            <div class="evento-card">
                <p class="evento-nome"><%= e.getNome() %></p>
                <p class="evento-desc"><%= e.getDescricao() %></p>
                <p class="evento-data">
                    <%= e.getData() %> — <%= e.getHorario() %>
                </p>

                <div class="card-actions">
                    <a href="#" class="btn-acao btn-editar"
                       onclick="document.getElementById('formEdit<%= e.getId() %>').classList.toggle('show'); return false;">
                        Editar
                    </a>

                    <a href="${pageContext.request.contextPath}/evento?action=delete&id=<%= e.getId() %>"
                       class="btn-acao btn-excluir"
                       onclick="return confirm('Excluir este evento?');">
                        Excluir
                    </a>
                </div>
                
                <form id="formEdit<%= e.getId() %>"
                      class="evento-form"
                      action="${pageContext.request.contextPath}/evento?action=update"
                      method="POST"
                      style="display:none; margin-top:15px;">

                    <input type="hidden" name="id" value="<%= e.getId() %>">

                    <label>Nome</label>
                    <input type="text" name="nome" value="<%= e.getNome() %>" required>

                    <label>Descrição</label>
                    <textarea name="descricao"><%= e.getDescricao() %></textarea>

                    <label>Data</label>
                    <input type="date" name="dataEvento" value="<%= e.getData() %>" required>

                    <label>Horário</label>
                    <input type="time" name="horarioEvento" value="<%= e.getHorario() %>" required>

                    <button class="btn-criar">Salvar alterações</button>
                </form>

            </div>

            <%  }} else { %>

            <p>Nenhum evento cadastrado.</p>

            <% } %>
        </div>
    </div>
</main>

<%@ include file="../footer.jsp" %>

</body>
</html>
