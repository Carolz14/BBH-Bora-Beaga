<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="pt">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bora Beagá</title>
        <link rel="stylesheet" href="../css/style-login.css">
        <script src="../js/validacao.js" defer></script>
    </head>

    <body>
        <div id="gradient">
            <h1 id="welcome">Bem vindo</h1>
            <h1 id="logo">Bora Beagá</h1>
        </div>
        <div id="container">

            <div id="login">
                <h1>Criar conta</h1>
                <div>
                    <form name="formCadastro" method="post">
                        <select name="tipo" id="tipo">
                            <option value="" selected disabled hidden>Tipo de cadastro</option>
                            <option value="TURISTA" label="Turista"></option>
                            <option value="ESTABELECIMENTO" label="Estabelecimento"></option>
                            <option value="GUIA" label="Guia"></option>
                            <option value="ADMINISTRADOR" label="Administrador"></option>
                        </select>
                        <select name="naturalidade" id="">
                            <option value="" selected disabled hidden>Naturalidade</option>
                            <option value="AC" label="AC"></option>
                            <option value="AL" label="AL"></option>
                            <option value="AP" label="AP"></option>
                            <option value="AM" label="AM"></option>
                            <option value="BA" label="BA"></option>
                            <option value="CE" label="CE"></option>
                            <option value="DF" label="DF"></option>
                            <option value="ES" label="ES"></option>
                            <option value="GO" label="GO"></option>
                            <option value="MA" label="MA"></option>
                            <option value="MT" label="MT"></option>
                            <option value="MS" label="MS"></option>
                            <option value="MG" label="MG"></option>
                            <option value="PA" label="PA"></option>
                            <option value="PB" label="PB"></option>
                            <option value="PR" label="PR"></option>
                            <option value="PE" label="PE"></option>
                            <option value="PI" label="PI"></option>
                            <option value="RJ" label="RJ"></option>
                            <option value="RN" label="RN"></option>
                            <option value="RS" label="RS"></option>
                            <option value="RO" label="RO"></option>
                            <option value="RR" label="RR"></option>
                            <option value="SC" label="SC"></option>
                            <option value="SP" label="SP"></option>
                            <option value="SE" label="SE"></option>
                            <option value="TO" label="TO"></option>
                            <option value="Fora do Brasil" label="Fora do Brasil"></option>
                        </select>
                        <input type="text" name="nome" class="em" placeholder="Nome" required>
                        <input type="email" name="email" class="em" placeholder="Email">
                        <input type="password" name="senha" class="sen" placeholder="Senha">

                        <div id="camposEstab" style="display: none;">
                            <input type="text" name="endereco" class="em" placeholder="Endereço">
                            <input type="text" name="contato" class="em" placeholder="Contato">
                            <input type="text" name="cnpj" class="em" placeholder="CNPJ">
                        </div>
                        <button type="button"  onclick="validarCamposCadastro(document.formCadastro)">Criar conta</button>


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