<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

    <!DOCTYPE html>
    <html lang="pt">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bora Beagá</title>
        <link rel="stylesheet" href="css/style-login.css">
       <link rel="icon" href="imagens/icon-page.png">
        <script src="js/script.js" defer></script>
    </head>

    <body>
        <div id="gradient">
            <h1 id="welcome"> Bem vindo de volta</h1>
            <h1 id="logo">Bora Beagá</h1>
        </div>
        <div id="container">

            <div id="login">
                <h1>Login</h1>
                <div>
                    <form name="formLogin" method="post" action="bbh/LoginController" >
                        <input type="email" name="email" id="email" placeholder="Email" required>
                        <input type="password" name="senha" id="senha" placeholder="Senha" required>
                        <button type="button"  onclick="validarCamposLogin(document.formLogin)">Entrar</button>
                        <button type="button" onclick="window.location.href='jsps/criar-conta.jsp'">Criar conta</button>
                    </form>

                </div>
            </div>


        </div>
        <c:if test="${not empty erroLogin}">
        <script>
            alert('${erroLogin}');
        </script>
    </c:if>
 
    </body>

    </html>