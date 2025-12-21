<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.controller.LoginController"%>
<%@taglib uri="jakarta.tags.core" prefix="c"%>

<%
    LoginController.validarSessao(request, response);
%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Painel do Estabelecimento - Bora Beagá</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-estab.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/painel.css">
    <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
</head>

<body>

<%@ include file="../header.jsp" %>

<main>
    <div class="container">

        <h1 class="pagina-titulo">
            Bem-vindo(a), <c:out value="${sessionScope.usuario.nome}" />
        </h1>

        <div class="desempenho-box">

            <h2>Desempenho</h2>

            <div class="desempenho-grid">

                <div class="metric-card metric-warning">
                    <h3>Visitas do mês</h3>
                    <div class="metric-numero">
                        <c:out value="${empty totalVisitas ? 0 : totalVisitas}" />
                    </div>
                    <div class="metric-desc">Acessos ao perfil</div>
                </div>

                <div class="metric-card metric-success">
                    <h3>Média de avaliações</h3>
                    <div class="metric-numero">
                        <c:out value="${empty mediaAvaliacoes ? 0 : mediaAvaliacoes}" />
                    </div>
                    <div class="metric-desc">Nota dos turistas</div>
                </div>

                <div class="metric-card metric-info">
                    <h3>Total de avaliações</h3>
                    <div class="metric-numero">
                        <c:out value="${empty totalAvaliacoes ? 0 : totalAvaliacoes}" />
                    </div>
                    <div class="metric-desc">Avaliações recebidas</div>
                </div>

            </div>
        </div>

    </div>
</main>


<%@ include file="../footer.jsp" %>

</body>
</html>
