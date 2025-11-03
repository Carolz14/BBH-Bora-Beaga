<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bora Beagá</title>

        <link rel="stylesheet" href="../../css/style-geral.css">
        <link rel="stylesheet" href="../../css/detalhe-roteiro.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    </head>

    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="container">
                <a href="lista-roteiros.jsp" class="back-link">Voltar</a>

                <div class="roteiro">

                    <div class="roteiro-imagem">
                        <img src="../imgs/roteiro.jpeg" alt="Imagem roteiro">
                    </div>

                    <div class="roteiro-detalhes">

                        <h1>Nome roteiro<br></h1>

                        <div class="rating">
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                        </div>

                        <div class="informacao">
                            <p>Por: Artur Dias</p>
                            <p>Esse belo roteiro inclui.........</p>
                        </div>

                        <div class="roteiro-paradas">
                            <h2>Paradas:</h2>

                            <div class="parada">
                                <img src="../imgs/parada1.jpeg" alt="Imagem da parada">
                                <h3>Savassi</h3>
                            </div>

                            <div class="parada">
                                <img src="../imgs/parada2.jpeg" alt="Imagem da parada">
                                <h3>Edificio JK </h3>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="forum-container">
                    <h2>Fórum</h2>
                </div>

            </div>

        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>