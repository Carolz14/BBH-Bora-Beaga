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
        <link rel="stylesheet" href="../../css/lista-interesse.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    </head>
    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="container">
                <h1>Lista de Interesse</h1>

                <div class="grid-container">

                    <div class="card-item">
                        <img src="../imgs/mercado-central.jpeg" alt="Imagem Mercado Central">
                        <h3>Mercado Central</h3>
                        <a href="detalhe-estabelecimento.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/ccbb.jpeg" alt="Imagem CCBB">
                        <h3>Centro Cultural Banco do Brasil</h3>
                        <a href="detalhe-estabelecimento.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/lagoa-pampulha.jpeg" alt="Imagem Lagoa da Pampulha">
                        <h3>Lagoa da Pampulha</h3>
                        <a href="detalhe-estabelecimento.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/parque-mangabeiras.jpeg" alt="Imagem Parque das Mangabeiras">
                        <h3>Parque Municipal das Mangabeiras</h3>
                        <a href="detalhe-estabelecimento.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/restaurante.jpeg" alt="Imagem estabelecimento">
                        <h3>Nome do estabelecimento</h3>
                        <a href="detalhe-estabelecimento.jsp" class="btn">Ver mais</a>
                    </div>

                    <div class="card-item">
                        <img src="../imgs/restaurante.jpeg" alt="Imagem estabelecimento">
                        <h3>Nome do estabelecimento</h3>
                        <a href="detalhe-estabelecimento.jsp" class="btn">Ver mais</a>
                    </div>

                </div>
            </div>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>