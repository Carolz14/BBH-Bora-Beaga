<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Usuario"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bora Beagá</title>
        <link rel="stylesheet" href="../../css/style-estab.css">
        <link rel="stylesheet" href="../../css/style-geral.css">
         <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
    </head>
    <body>
        
        <%@ include file="../header.jsp" %>
        
        <h1 class="texto-apresentacao">Bem vindo(a), <%= usuario.getNome() %>!</h1>
        <main class="conteudo">
            
            <div>
                
                <h2 class="titulo-metricas">Desempenho</h2>
                <section class="display-metricas">
                    <div class="imagens-metricas"><p>1k visitas ao perfil</p></div>
                    <div class="imagens-metricas"><p>4.5 média de avaliações</p></div>
                    <div class="imagens-metricas"><p>500 avaliações</p></div>
                </section>
            </div>
        </main>
        
        <%@ include file="../footer.jsp" %>
        
    </body>
</html>