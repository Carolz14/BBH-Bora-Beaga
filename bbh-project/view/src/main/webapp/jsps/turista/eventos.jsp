<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bora Beagá - Eventos</title>
    <link rel="stylesheet" href="../../css/eventos.css">
    <link rel="stylesheet" href="../../css/style-geral.css">
</head>
<body>

    <%@ include file="../header.jsp" %>

    <main>
        <h1>Eventos</h1>

        <section class="eventos">
            <div class="evento" 
                 data-nome="Nome do evento" 
                 data-desc="Descrição do evento" 
                 data-img="../imgs/conexao-085-shows.jpeg">
                <img src="../imgs/conexao-085-shows.jpeg" alt="Festival de Inverno">
                <h3>Nome do evento</h3>
            </div>

            <div class="evento" 
                 data-nome="Nome do evento" 
                 data-desc="Descrição do evento" 
                 data-img="../imgs/conexao-085-shows.jpeg">
                <img src="../imgs/conexao-085-shows.jpeg" alt="Festival de Inverno">
                <h3>Nome do evento</h3>
            </div>

            <div class="evento" 
                 data-nome="Nome do evento" 
                 data-desc="Descrição do evento" 
                 data-img="../imgs/conexao-085-shows.jpeg">
                <img src="../imgs/conexao-085-shows.jpeg" alt="Festival de Inverno">
                <h3>Nome do evento</h3>
            </div>

            <div class="evento" 
                 data-nome="Nome do evento" 
                 data-desc="Descrição do evento" 
                 data-img="../imgs/conexao-085-shows.jpeg">
                <img src="../imgs/conexao-085-shows.jpeg" alt="Festival de Inverno">
                <h3>Nome do evento</h3>
            </div>
        </section>

        <div id="modal" class="modal">
            <div class="modal-conteudo">
                <span class="fechar">&times;</span>
                
                <img id="modal-img" src="" alt="Imagem do Evento">
                
                <div class="modal-texto">
                    <h3 id="modal-nome"></h3>
                    <p id="modal-desc"></p>
                    <a href="#" id="add-to-calendar-btn" class="modal-botao">Adicionar à Agenda</a>
                </div>
                
            </div>
        </div>
        
    </main>

    <%@ include file="../footer.jsp" %>
    
    </body>
</html>