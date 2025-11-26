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
    <link rel="stylesheet" href="../../css/eventos.css">
    <link rel="stylesheet" href="../../css/style-geral.css">
</head>

<body>

<%@ include file="../header.jsp" %>

<%
    String ctx = request.getContextPath();
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    UsuarioTipo tipo = usuario != null ? usuario.getUsuarioTipo() : null;

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
        <h2>Próximos 4 eventos</h2>
        <div class="eventos-grid">
            <% if (proximos4 != null && !proximos4.isEmpty()) {
               for (Evento e : proximos4) { %>

                <a class="evento evento-card" href="<%= ctx %>/evento?action=detalhe&id=<%= e.getId() %>">
                    <div class="evento-img"></div>
                    <div class="evento-info">
                        <h3><%= e.getNome() %></h3>
                        <p><%= e.getData() %> às <%= e.getHorario() %></p>
                    </div>
                </a>

            <%   }
               } else { %>
                <div class="sem-resultados">
                    <p>Nenhum evento próximo encontrado.</p>
                </div>
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

                <a class="evento evento-card" href="<%= ctx %>/evento?action=detalhe&id=<%= e.getId() %>">
                    <div class="evento-img"></div>
                    <div class="evento-info">
                        <h3><%= e.getNome() %></h3>
                        <p><%= e.getData() %> às <%= e.getHorario() %></p>
                    </div>
                </a>

            <%   }
               } else { %>
                <div class="sem-resultados">
                    <p>Nenhum evento futuro cadastrado.</p>
                </div>
            <% } %>
        </div>
    </section>

</main>

<%@ include file="../footer.jsp" %>

<script>
    (function(){
        var btn = document.getElementById('btn-ver-todos');
        var section = document.getElementById('todos-eventos-section');
        var shown = false;
        btn.addEventListener('click', function(){
            shown = !shown;
            if (shown) {
                section.classList.remove('hidden');
                btn.textContent = 'Ocultar eventos';
                section.scrollIntoView({behavior: 'smooth', block: 'start'});
            } else {
                section.classList.add('hidden');
                btn.textContent = 'Ver todos os eventos futuros';
            }
        });
    })();
</script>

</body>
</html>
