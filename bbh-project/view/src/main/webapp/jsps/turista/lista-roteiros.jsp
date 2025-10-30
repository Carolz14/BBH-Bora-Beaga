<%@ include file="../seguranca.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Usuario"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>
<%@page import="bbh.service.ControleAutorizacao"%>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bora Beagá</title>

        <link rel="stylesheet" href="../../css/style-geral.css">
        <link rel="stylesheet" href="../../css/lista-roteiros.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    </head>
    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="container">
                <div class="header-com-botao">
                    <h1>Roteiros</h1>
                    <a href="roteiroGuia.jsp" class="btn">Guia</a>
                </div>

                <div class="grid-container">

                    <div class="card-item">
                        <img src="../imgs/roteiro.jpeg" alt="Imagem roteiro">
                        <h3>Nome do roteiro</h3>
                        <a href="detalhe-roteiro.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/roteiro.jpeg" alt="Imagem roteiro">
                        <h3>Nome do roteiro</h3>
                        <a href="detalhe-roteiro.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/roteiro.jpeg" alt="Imagem roteiro">
                        <h3>Nome do roteiro</h3>
                        <a href="detalhe-roteiro.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/roteiro.jpeg" alt="Imagem roteiro">
                        <h3>Nome do roteiro</h3>
                        <a href="detalhe-roteiro.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/roteiro.jpeg" alt="Imagem roteiro">
                        <h3>Nome do roteiro</h3>
                        <a href="detalhe-roteiro.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/roteiro.jpeg" alt="Imagem roteiro">
                        <h3>Nome do roteiro</h3>
                        <a href="detalhe-roteiro.jsp" class="btn">Ver mais</a>
                    </div>

                </div>
            </div>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>