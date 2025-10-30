<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.service.GestaoUsuariosService"%>
<%@page import="bbh.domain.Usuario"%>
<%@page import="java.util.List"%>

<%@ include file="../seguranca.jsp" %>

<%
    GestaoUsuariosService service = new GestaoUsuariosService();
    List<Usuario> usuariosAtivos = null;

    try {
        usuariosAtivos = service.pesquisarAtivos();
    } catch (Exception e) {
        e.printStackTrace();
    }

    List<Usuario> turistas = new java.util.ArrayList<>();
    List<Usuario> guias = new java.util.ArrayList<>();
    List<Usuario> estabelecimentos = new java.util.ArrayList<>();

    if (usuariosAtivos != null) {
    for (Usuario u : usuariosAtivos) {
        if (u.getUsuarioTipo() != null && u.getUsuarioTipo().equalsIgnoreCase("TURISTA")) {
            turistas.add(u);
        } else if (u.getUsuarioTipo() != null && u.getUsuarioTipo().equalsIgnoreCase("GUIA")) {
            guias.add(u);
        } else if (u.getCNPJ() != null) {
            estabelecimentos.add(u);
        }
    }
}

%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bora Beagá</title>
    <link rel="stylesheet" href="../../css/style-geral.css">
    <link rel="stylesheet" href="../../css/usuarios.css">
</head>

<body>
    <%@ include file="../header.jsp" %>

    <main>
        <div class="container">
            <h1 class="pagina-titulo">Usuários do Sistema</h1>

            <!-- Seção Turistas -->
            <section class="user-section">
                <h2 class="section-titulo">Turistas</h2>
                <div class="user-list">
                    <% if (!turistas.isEmpty()) { 
                        for (Usuario t : turistas) { %>
                            <div class="user-card">
                                <p class="user-name"><%= t.getNome() %></p>
                                <p class="user-email"><%= t.getEmail() %></p>
                            </div>
                    <%  } 
                    } else { %>
                        <p class="sem-usuarios">Nenhum turista ativo encontrado.</p>
                    <% } %>
                </div>
            </section>

            <!-- Seção Guias -->
            <section class="user-section">
                <h2 class="section-titulo">Guias</h2>
                <div class="user-list">
                    <% if (!guias.isEmpty()) { 
                        for (Usuario g : guias) { %>
                            <div class="user-card">
                                <p class="user-name"><%= g.getNome() %></p>
                                <p class="user-email"><%= g.getEmail() %></p>
                            </div>
                    <%  } 
                    } else { %>
                        <p class="sem-usuarios">Nenhum guia ativo encontrado.</p>
                    <% } %>
                </div>
            </section>

            <!-- Seção Estabelecimentos -->
            <section class="user-section">
                <h2 class="section-titulo">Estabelecimentos</h2>
                <div class="user-list">
                    <% if (!estabelecimentos.isEmpty()) { 
                        for (Usuario e : estabelecimentos) { %>
                            <div class="user-card">
                                <p class="user-name"><%= e.getNome() %></p>
                                <p class="user-email"><%= e.getEmail() %></p>
                            </div>
                    <%  } 
                    } else { %>
                        <p class="sem-usuarios">Nenhum estabelecimento ativo encontrado.</p>
                    <% } %>
                </div>
            </section>

        </div>
    </main>

    <%@ include file="../footer.jsp" %>
</body>
</html>
