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
    <link rel="stylesheet" href="../../css/style-estab.css">
</head>

<body>
    
    <%@ include file="../header.jsp" %>
    
    <main>
        <div class="conteudo-promo-event">
            <h1 class="texto-apresentacao">Gerenciar Eventos</h1>
            <div class="display-gerenciar-eventos">
                <p>Nenhum evento cadastrado</p>
            </div>
            
            <h1 class="texto-apresentacao">Novo Evento</h1>
            <div class="display-cadastro-evento">
                
                <form method="POST" action="GerenciarEvento">
                    <label for="nomeEvento">Nome:</label>
                    <input id="nomeEvento" type="text" name="nomeEvento" required>

                    <label for="dataEvento">Data:</label>
                    <input id="dataEvento" type="date" name="dataEvento" required>

                    <label for="descricaoEvento">Descrição:</label>
                    <input id="descricaoEvento" type="text" name="descricaoEvento">
                    
                    <button class="botao-submit" type="submit">Criar Evento</button>
                </form>

            </div>
        </div>
    </main>
    
    <%@ include file="../footer.jsp" %>
    
</body>
</html>