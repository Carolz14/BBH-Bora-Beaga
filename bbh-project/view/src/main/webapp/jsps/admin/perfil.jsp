<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="bbh.controller.LoginController"%>
<%@page import="bbh.domain.Usuario"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<%
    LoginController.validarSessao(request, response);
%>


<!DOCTYPE html>
<html lang="pt">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bora Beagá</title>
    <link rel="stylesheet" href="../../css/style-geral.css">
    <link rel="stylesheet" href="../../css/perfil.css">
</head>

<body>
       <%@ include file="../header.jsp" %>

    <main>
        <div class="container">
            <h1 class="pagina-titulo">Perfil</h1>

            <div class="perfil">

                <div class="perfil-avatar-section">
                    
                    <c:choose>
                        <c:when test="${sessionScope.usuario.usuarioTipo == 'ADMINISTRADOR'}">
                            <img src="../../imagens/admin.png" alt="Administrador" class="profile-avatar">
                        </c:when>

                        <c:when test="${sessionScope.usuario.usuarioTipo == 'GUIA'}">
                            <img src="../../imagens/guia.png" alt="Guia Turístico" class="profile-avatar">
                        </c:when>

                        <c:when test="${sessionScope.usuario.usuarioTipo == 'TURISTA'}">
                            <img src="../../imagens/turista.png" alt="Turista" class="profile-avatar">
                        </c:when>
                    </c:choose>
                </div>

                <div id="infos">
                  
                       <p>Nome: ${sessionScope.usuario.nome}</p>

                       <p>Email: ${sessionScope.usuario.email}</p>

                       <p>Naturalidade: ${sessionScope.usuario.naturalidade}</p>

                     
                </div>
            </div>
        </div>
    </main>
       <%@ include file="../footer.jsp" %>

</body>

</html>