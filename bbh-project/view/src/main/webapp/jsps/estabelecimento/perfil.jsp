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
    <link rel="stylesheet" href="../../css/style-estab.css">
</head>

<body>

     <%@ include file="../header.jsp" %>
    <h1 class="texto-apresentacao">Perfil</h1>
    <main class="perfil-container">

        <div class="janela-perfil-flutuante">
            <div class="esquerda">
                <div class="foto-perfil"></div>
                <select class="categorias-estabelecimentos">
                    <option>Categorias</option>
                    <option>Categoria1</option>
                    <option>Categoria2</option>
                </select>
            </div>
            <div class="direita">
                    <p>Nome: ${sessionScope.usuario.nome}</p>

                       <p>Email: ${sessionScope.usuario.email}</p>

                       <p>Naturalidade: ${sessionScope.usuario.naturalidade}</p>
                        <p>CNPJ: ${sessionScope.usuario.CNPJ}</p>

                       <p>Endereço: ${sessionScope.usuario.endereco}</p>

                       <p>Contato: ${sessionScope.usuario.contato}</p>
            </div>
        </div>
    </main>
  <%@ include file="../footer.jsp" %>

</body>

</html>