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
    <title>Eventos</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/eventos.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style-geral.css">
</head>

<body>

<%@ include file="../header.jsp" %>

<%
    List<Evento> proximos4 = (List<Evento>) request.getAttribute("proximos4");
    List<Evento> eventosFuturos = (List<Evento>) request.getAttribute("eventosFuturos");

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

    <header class="eventos-header">
        <h1>Eventos</h1>
        <p class="subtitle">Fique por dentro das atrações e programação perto de você</p>
    </header>

    <section class="proximos4-section">
        <h2>Eventos próximos:</h2>
        <div class="eventos-grid">
            <% if (proximos4 != null && !proximos4.isEmpty()) {
                for (Evento e : proximos4) { %>

                <a class="evento evento-card" href="<%= request.getContextPath() %>/evento?action=detalhe&id=<%= e.getId() %>">
                    <div class="evento-img"></div>
                    <div class="evento-info">
                        <h3><%= e.getNome() %></h3>
                        <p><%= e.getData() %> às <%= e.getHorario() %></p>
                    </div>
                </a>

            <% }
            } else { %>
                <p>Nenhum evento próximo encontrado.</p>
            <% } %>
        </div>
    </section>

    <div class="acoes-eventos">
        <button id="btn-ver-todos" class="btn btn-ver-todos">Ver todos os eventos futuros</button>
    </div>

    <section id="todos-eventos-section" class="todos-eventos-section hidden">
        <h2>Todos os eventos futuros</h2>
        <div class="eventos-grid">
            <% if (eventosFuturos != null && !eventosFuturos.isEmpty()) {
               for (Evento e : eventosFuturos) { %>

                <a class="evento evento-card" href="<%= request.getContextPath() %>/evento?action=detalhe&id=<%= e.getId() %>">
                    <div class="evento-img"></div>
                    <div class="evento-info">
                        <h3><%= e.getNome() %></h3>
                        <p><%= e.getData() %> às <%= e.getHorario() %></p>
                    </div>
                </a>

            <% }
               } else { %>
                <p>Nenhum evento futuro cadastrado.</p>
            <% } %>
        </div>
    </section>

</main>

<%@ include file="../footer.jsp" %>

<script>
    (function(){
        var btn = document.getElementById('btn-ver-todos');
        var section = document.getElementById('todos-eventos-section');
        btn.addEventListener('click', function(){
            section.classList.toggle('hidden');
            btn.textContent = section.classList.contains('hidden')
                ? 'Ver todos os eventos futuros'
                : 'Ocultar eventos';
        });
    })();
</script>

</body>
</html>
