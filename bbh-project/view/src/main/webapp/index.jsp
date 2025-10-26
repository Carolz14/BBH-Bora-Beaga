<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

    <!DOCTYPE html>
    <html lang="pt">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bora Beagá</title>
        <link rel="stylesheet" href="css/style-login.css">
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
                    <form name="formLogin" method="post" >
                        <input type="email" name="email" id="email" placeholder="Email">
                        <input type="password" name="senha" id="senha" placeholder="Senha">
                        <button type="button"  onclick="validarCamposLogin(document.formLogin)">Entrar</button>
                        <button type="button" onclick="window.location.href='jsps/criar-conta.html'">Criar conta</button>
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