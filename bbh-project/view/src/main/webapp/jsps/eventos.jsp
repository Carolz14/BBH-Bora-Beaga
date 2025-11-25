<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Evento"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bora Beagá - Eventos</title>
    <link rel="stylesheet" href="../../css/eventos.css">
    <link rel="stylesheet" href="../../css/style-geral.css">
</head>

<body>
<%@ include file="../header.jsp" %>

<%
    UsuarioTipo tipo = (UsuarioTipo) session.getAttribute("tipoUsuario");

    // TURISTA
    List<Evento> proximos4 = (List<Evento>) request.getAttribute("proximos4");
    List<Evento> todosFuturos = (List<Evento>) request.getAttribute("eventosFuturos");

    // ESTABELECIMENTO
    List<Evento> meusEventos = (List<Evento>) request.getAttribute("meusEventos");

    String msg = (String) request.getAttribute("msg");
%>

<main>

    <% if (msg != null) { %>
        <p class="msg-sucesso"><%= msg %></p>
    <% } %>


    <% if (tipo == UsuarioTipo.ESTABELECIMENTO) { %>

        <div class="card-gerenciar">
            <h1>Gerenciar eventos</h1>

            <h2>Novo evento</h2>

            <form action="evento?action=add" method="POST" class="form-evento">
                <input type="text" name="nome" placeholder="Nome" required>
                <input type="date" name="dataEvento" placeholder="Data" required>
                <input type="time" name="horarioEvento" placeholder="Horário" required>
                <textarea name="descricao" placeholder="Descrição"></textarea>

                <button type="submit" class="btn criar-evento-btn">Criar evento</button>
            </form>
        </div>

        <h2>Meus próximos eventos</h2>
        <section class="eventos">

            <% if (meusEventos != null) {
                for (Evento e : meusEventos) { %>

                    <div class="evento evento-gerenciar">
                        <div class="evento-img"></div>
                        <div class="info-gerenciar">
                            <h3><%= e.getNome() %></h3>
                            <p><%= e.getData() %> às <%= e.getHorario() %></p>
                            
                            <form action="evento?action=update" method="POST" class="form-editar">
                                <input type="hidden" name="id" value="<%= e.getId() %>">
                                
                                <label for="nome-<%= e.getId() %>">Nome:</label>
                                <input type="text" id="nome-<%= e.getId() %>" name="nome" value="<%= e.getNome() %>" required>
                                
                                <label for="desc-<%= e.getId() %>">Descrição:</label>
                                <textarea id="desc-<%= e.getId() %>" name="descricao"><%= e.getDescricao() %></textarea>
                                
                                <label for="data-<%= e.getId() %>">Data:</label>
                                <input type="date" id="data-<%= e.getId() %>" name="dataEvento" value="<%= e.getData() %>" required>
                                
                                <label for="hora-<%= e.getId() %>">Horário:</label>
                                <input type="time" id="hora-<%= e.getId() %>" name="horarioEvento" value="<%= e.getHorario() %>" required>
                                
                                <button class="btn btn-salvar">Salvar</button>
                            </form>

                            <form action="evento?action=delete" method="POST" class="form-excluir">
                                <input type="hidden" name="id" value="<%= e.getId() %>">
                                <button class="btn btn-remover">Excluir</button>
                            </form>
                        </div>
                    </div>

            <%  }
            } %>

        </section>

    <% } %>


    <% if (tipo == UsuarioTipo.TURISTA) { %>

        <h1>Eventos</h1>

        <h2>Próximos 4 eventos</h2>
        <section class="eventos eventos-turista">
            <% if (proximos4 != null) {
                for (Evento e : proximos4) { %>

                    <a class="evento evento-card" href="detalhe?id=<%= e.getId() %>">
                        <div class="evento-img"></div>
                        <div class="evento-info">
                            <h3><%= e.getNome() %></h3>
                            <p><%= e.getData() %> às <%= e.getHorario() %></p>
                        </div>
                    </a>

            <%  }
            } %>
        </section>

        <h2>Todos os eventos futuros</h2>
        <section class="eventos eventos-turista">
            <% if (todosFuturos != null) {
                for (Evento e : todosFuturos) { %>

                    <a class="evento evento-card" href="detalhe?id=<%= e.getId() %>">
                        <div class="evento-img"></div>
                        <div class="evento-info">
                            <h3><%= e.getNome() %></h3>
                            <p><%= e.getData() %> às <%= e.getHorario() %></p>
                        </div>
                    </a>

            <%  }
            } %>
        </section>

    <% } %>

</main>

<%@ include file="../footer.jsp" %>

</body>
</html>