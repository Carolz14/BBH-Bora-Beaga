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

        <h1 class="pagina-titulo">Painel do Estabelecimento</h1>

        <c:if test="${not empty sessionScope.usuario}">
            <div class="painel-grid">

                <div class="painel-card card-profile">
                    <h2>Informações Gerais</h2>

                    <p><strong>Nome</strong><span><c:out value="${sessionScope.usuario.nome}" /></span></p>
                    <p><strong>Email</strong><span><c:out value="${sessionScope.usuario.email}" /></span></p>
                    <p><strong>Endereço</strong><span><c:out value="${sessionScope.usuario.endereco}" /></span></p>
                    <p><strong>Contato</strong><span><c:out value="${sessionScope.usuario.contato}" /></span></p>
                </div>

                <div class="painel-card card-warning">
                    <h2>Visitas Mensais</h2>
                    <p class="painel-numero">
                        <c:out value="${empty totalVisitas ? 0 : totalVisitas}" />
                    </p>
                    <span>Acessos ao seu perfil neste mês</span>
                </div>

                <div class="painel-card card-success">
                    <h2>Média de Avaliações</h2>
                    <p class="painel-numero">
                        <c:out value="${empty mediaAvaliacoes ? 0 : mediaAvaliacoes}" />
                    </p>
                    <span>Percepção dos turistas</span>
                </div>

                <div class="painel-card card-info">
                    <h2>Total de Avaliações</h2>
                    <p class="painel-numero">
                        <c:out value="${empty totalAvaliacoes ? 0 : totalAvaliacoes}" />
                    </p>
                    <span>Avaliações registradas</span>
                </div>

            </div>
        </c:if>

        <c:if test="${empty sessionScope.usuario}">
            <p>Usuário não autenticado.</p>
        </c:if>

    </div>
</main>

<%@ include file="../footer.jsp" %>

</body>
</html>
