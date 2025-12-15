<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Evento"%>
<%@page import="bbh.domain.Usuario"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Eventos</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/eventos.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style-estab.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style-geral.css">
    <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
</head>

<body>

<%@ include file="../header.jsp" %>

<%
    List<Evento> meusEventos = (List<Evento>) request.getAttribute("meusEventos");

    String msg = (String) request.getAttribute("msg");
    String erro = (String) request.getAttribute("erro");
%>

<main>

    <% if (msg != null) { %>
        <p class="msg-sucesso"><%= msg %></p>
    <% } %>

    <% if (erro != null) { %>
        <p class="msg-erro"><%= erro %></p>
    <% } %>

    <div class="container-estab">
        <div class="card-gerenciar">
            <h1>Gerenciar Eventos</h1>

            <form action="<%= request.getContextPath() %>/evento?action=add" method="POST" class="form-evento">
                <label for="nome">Nome</label>
                <input id="nome" type="text" name="nome" required>

                <label for="dataEvento">Data</label>
                <input id="dataEvento" type="date" name="dataEvento" required>

                <label for="horarioEvento">Horário</label>
                <input id="horarioEvento" type="time" name="horarioEvento" required>

                <label for="descricao">Descrição</label>
                <textarea id="descricao" name="descricao"></textarea>

                <button type="submit" class="btn criar-evento-btn">Criar Evento</button>
            </form>
        </div>

        <h2>Meus próximos eventos</h2>

        <section class="eventos">
            <% if (meusEventos != null && !meusEventos.isEmpty()) {
               for (Evento e : meusEventos) { %>

            <div class="evento evento-gerenciar">
                <div class="evento-img"></div>
                <div class="info-gerenciar">
                    <h3><%= e.getNome() %></h3>
                    <p><%= e.getData() %> às <%= e.getHorario() %></p>

                    <form action="<%= request.getContextPath() %>/evento?action=update" method="POST" class="form-editar">
                        <input type="hidden" name="id" value="<%= e.getId() %>">

                        <label>Nome:</label>
                        <input type="text" name="nome" value="<%= e.getNome() %>" required>

                        <label>Descrição:</label>
                        <textarea name="descricao"><%= e.getDescricao() %></textarea>

                        <label>Data:</label>
                        <input type="date" name="dataEvento" value="<%= e.getData() %>" required>

                        <label>Horário:</label>
                        <input type="time" name="horarioEvento" value="<%= e.getHorario() %>" required>

                        <button class="btn btn-salvar">Salvar</button>
                    </form>

                    <form action="<%= request.getContextPath() %>/evento?action=delete" method="POST" class="form-excluir">
                        <input type="hidden" name="id" value="<%= e.getId() %>">
                        <button class="btn btn-remover">Excluir</button>
                    </form>
                </div>
            </div>

            <% } } else { %>
                <p>Nenhum evento cadastrado</p>
            <% } %>
        </section>
    </div>

</main>

<%@ include file="../footer.jsp" %>

</body>
</html>
