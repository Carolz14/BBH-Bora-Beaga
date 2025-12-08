<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@page import="bbh.domain.Usuario"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Painel Administrativo - Bora Beagá</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/painel.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pontoturistico.css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    </head>
    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="container" style="padding: 40px 20px;">

                <h1 class="pagina-titulo" style="text-align: center; margin-bottom: 30px;">Gerenciar Pontos Turísticos</h1>

                <div class="grid-pontos">
                    <c:choose>
                        <c:when test="${not empty pontos}">
                            <c:forEach var="p" items="${pontos}">
                                <div class="ponto-card">
                                    <div class="card-img-container">
                                        <c:choose>
                                            <c:when test="${not empty p.imagemUrl}">
                                                <img src="${pageContext.request.contextPath}/imagem?nome=${p.imagemUrl}" 
                                                     alt="${p.nome}" class="card-img">
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fa-solid fa-image" style="font-size: 40px; color: #ccc;"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="card-content">
                                        <h3 class="card-title">${p.nome}</h3>
                                        <p class="card-address"><i class="fa-solid fa-location-dot"></i> ${p.endereco}</p>
                                        <p class="card-desc">${p.descricao}</p>

                                        <div class="card-actions">
                                            <a href="${pageContext.request.contextPath}/bbh/CadastroPontoTuristico?acao=editar&id=${p.id}" class="btn-card btn-edit">
                                                <i class="fa-solid fa-pen"></i> Editar
                                            </a>

                                            <a href="${pageContext.request.contextPath}/bbh/CadastroPontoTuristico?acao=excluir&id=${p.id}" 
                                               class="btn-card btn-delete" 
                                               onclick="return confirm('Tem certeza que deseja excluir ${p.nome}?');">
                                                <i class="fa-solid fa-trash"></i> Excluir
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p style="text-align: center; width: 100%; color: #666;">Nenhum ponto turístico cadastrado ainda.</p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="separador"></div>

                <div class="form-section" id="formulario">
                    <div class="perfil-form">
                        <h2 class="pagina-titulo" style="text-align: center; margin-bottom: 20px;">
                            ${not empty pontoEdit ? 'Editar Ponto Turístico' : 'Cadastrar Novo Ponto'}
                        </h2>

                        <form method="POST" action="<%= request.getContextPath()%>/bbh/CadastroPontoTuristico" enctype="multipart/form-data">

                            <input type="hidden" name="id" value="${pontoEdit.id}">
                            <input type="hidden" name="acao" value="${not empty pontoEdit ? 'atualizar' : 'cadastrar'}">

                            <div class="form-group">
                                <label for="nome">Nome do Local:</label>
                                <input style="min-height: 40px;" type="text" id="nome" name="nome" 
                                       placeholder="Ex: Praça da Liberdade" 
                                       value="${pontoEdit.nome}" required>
                            </div>

                            <div class="form-group">
                                <label for="endereco">Endereço Completo:</label>
                                <input style="min-height: 40px;" type="text" id="endereco" name="endereco" 
                                       placeholder="Ex: Av. Brasil, 2000 - Savassi" 
                                       value="${pontoEdit.endereco}" required>
                            </div>

                            <div class="form-group">
                                <label for="descricao">Descrição:</label>
                                <input style="min-height: 100px;" type="text" id="descricao" name="descricao" 
                                       placeholder="Conte um pouco sobre o local..." 
                                       value="${pontoEdit.descricao}">
                            </div>

                            <div class="form-group">
                                <label>Imagem do Local:</label>
                                <c:if test="${not empty pontoEdit}">
                                    <p style="font-size: 12px; color: #666; margin-bottom: 5px;">Deixe vazio para manter a imagem atual.</p>
                                </c:if>

                                <input type="file" id="imagem-upload" name="imagem" accept="image/*" style="display: none;">
                                <label for="imagem-upload" class="upload-label">
                                    <i class="fa-solid fa-cloud-arrow-up"></i> Escolher Imagem
                                </label>
                            </div>

                            <div class="form-group" style="display: flex; gap: 10px; margin-top: 20px;">
                                <button type="submit" class="submit-btn" style="flex: 1;">
                                    ${not empty pontoEdit ? 'Salvar Alterações' : 'Cadastrar'}
                                </button>

                                <c:if test="${not empty pontoEdit}">
                                    <a href="${pageContext.request.contextPath}/bbh/CadastroPontoTuristico" class="btn-cancel">
                                        Cancelar
                                    </a>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>