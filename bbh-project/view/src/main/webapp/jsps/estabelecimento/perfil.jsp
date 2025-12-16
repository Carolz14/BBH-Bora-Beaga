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
        <link rel="stylesheet" href="../../css/perfil.css">
         <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
    </head>

    <body>

        <%@ include file="../header.jsp" %>

        <main>
           <div class="container">
            <h1 class="pagina-titulo">Perfil</h1>

            <%-- Exibe mensagem de erro, se houver --%>
            <c:if test="${not empty requestScope.erro}">
                <p style="color: red;">${requestScope.erro}</p>
            </c:if>
            
            <form class="perfil" 
                  action="${pageContext.request.contextPath}/bbh/AtualizarEstabelecimentoController" 
                  method="POST" 
                  enctype="multipart/form-data">


                <div id="infos">
                    <p>Nome: ${sessionScope.usuario.nome}</p>
                    <p>Email: ${sessionScope.usuario.email}</p>
                    <p>Naturalidade: ${sessionScope.usuario.naturalidade}</p>
                    <p>CNPJ: ${sessionScope.usuario.CNPJ}</p>
                    <p>Endereço: ${sessionScope.usuario.endereco}</p>
                    <p>Contato: ${sessionScope.usuario.contato}</p>
                    
            
                    <label for="imagem" style="margin-top: 10px; display: block;">Alterar foto:</label>
                    <input type="file" name="imagem" id="imagem" accept="image/*">
                    <label for="descricao"><strong>Descrição:</strong></label>
                    <textarea name="descricao" id="descricao" rows="5">${sessionScope.usuario.descricao}</textarea>
                    
                    <a href="${pageContext.request.contextPath}/estabelecimento/tags" class="botao" style="margin-top: 15px;">
                        Gerenciar Tags
                    </a>
                    
                    <button type="submit" class="botao" style="margin-top: 15px;">Salvar Alterações</button>
                </div>
            </form>
        </div>
        </main>


        <%@ include file="../footer.jsp" %>

    </body>

</html>