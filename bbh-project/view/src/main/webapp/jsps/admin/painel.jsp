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
 <link rel="icon" href="${pageContext.request.contextPath}/imagens/icon-page.png">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/painel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pontoturistico.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    </head>
    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="container">

                <h1 class="pagina-titulo text-center">Gerenciar Pontos Turísticos</h1>

                <div class="painel-search-bar">
                    <form action="${pageContext.request.contextPath}/bbh/CadastroPontoTuristico" method="get">
                        <input type="text" name="busca" placeholder="Pesquisar ponto turístico..." value="${param.busca}">
                        <button type="submit" class="btn-search"><i class="fa-solid fa-magnifying-glass"></i></button>

                        <c:if test="${not empty param.busca}">
                            <a href="${pageContext.request.contextPath}/bbh/CadastroPontoTuristico" class="btn-limpar-busca">
                                <i class="fa-solid fa-xmark"></i>
                            </a>
                        </c:if>
                    </form>
                </div>

                <div class="grid-pontos">
                    <c:choose>
                        <c:when test="${not empty pontos}">

                            <c:set var="limiteLoop" value="${(not empty param.busca or not empty param.verTodos) ? pontos.size() : 2}" />

                            <c:forEach var="p" items="${pontos}" end="${limiteLoop}">
                                <div class="ponto-card">
                                    <div class="card-img-container">
                                        <c:choose>
                                            <c:when test="${not empty p.imagemUrl}">
                                                <img src="${pageContext.request.contextPath}/imagem?nome=${p.imagemUrl}" alt="${p.nome}" class="card-img">
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
                            <p class="text-center w-100 grey-text">Nenhum ponto turístico encontrado.</p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <c:if test="${pontos.size() > 3 and empty param.busca and empty param.verTodos}">
                    <a href="${pageContext.request.contextPath}/bbh/CadastroPontoTuristico?verTodos=true" class="btn-ver-todos">
                        Ver todos os pontos turísticos <i class="fa-solid fa-chevron-down"></i>
                    </a>
                </c:if>
                <c:if test="${not empty param.verTodos}">
                    <a href="${pageContext.request.contextPath}/bbh/CadastroPontoTuristico" class="btn-ver-todos">
                        Mostrar menos <i class="fa-solid fa-chevron-up"></i>
                    </a>
                </c:if>

                <div class="separador"></div>

                <div class="form-section" id="formulario">
                    <div class="perfil-form">
                        <h2 class="pagina-titulo text-center">
                            ${not empty pontoEdit ? 'Editar Ponto Turístico' : 'Cadastrar Novo Ponto'}
                        </h2>

                        <form method="POST" action="<%= request.getContextPath()%>/bbh/CadastroPontoTuristico" enctype="multipart/form-data">

                            <input type="hidden" name="id" value="${pontoEdit.id}">
                            <input type="hidden" name="acao" value="${not empty pontoEdit ? 'atualizar' : 'cadastrar'}">

                            <div class="form-group">
                                <label for="nome">Nome do Local:</label>
                                <input class="input-padrao" type="text" id="nome" name="nome" 
                                       placeholder="Ex: Praça da Liberdade" 
                                       value="${pontoEdit.nome}" required>
                            </div>

                            <div class="form-group">
                                <label for="endereco">Endereço Completo:</label>
                                <input class="input-padrao" type="text" id="endereco" name="endereco" 
                                       placeholder="Ex: Av. Brasil, 2000 - Savassi" 
                                       value="${pontoEdit.endereco}" required>
                            </div>

                            <div class="form-group">
                                <label for="descricao">Descrição:</label>
                                <input class="input-area" type="text" id="descricao" name="descricao" 
                                       placeholder="Conte um pouco sobre o local..." 
                                       value="${pontoEdit.descricao}">
                            </div>

                            <div class="form-group">
                                <label for="tag">Categoria Principal:</label>
                                <select id="tag" name="tag" class="input-select">
                                    <option value="" disabled ${empty pontoEdit.tag ? 'selected' : ''}>Selecione uma categoria...</option>
                                    <option value="lazer" ${pontoEdit.tag == 'lazer' ? 'selected' : ''}>Lazer</option>
                                    <option value="parque" ${pontoEdit.tag == 'parque' ? 'selected' : ''}>Parque</option>
                                    <option value="familia" ${pontoEdit.tag == 'familia' ? 'selected' : ''}>Família</option>
                                    <option value="igrejas" ${pontoEdit.tag == 'igrejas' ? 'selected' : ''}>Igrejas</option>
                                    <option value="monumentos" ${pontoEdit.tag == 'monumentos' ? 'selected' : ''}>Monumentos</option>
                                    <option value="museus" ${pontoEdit.tag == 'museus' ? 'selected' : ''}>Museus</option>
                                    <option value="outros" ${pontoEdit.tag == 'outros' ? 'selected' : ''}>Outros</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Imagem do Local:</label>
                                <c:if test="${not empty pontoEdit}">
                                    <p class="aviso-edicao">Deixe vazio para manter a imagem atual.</p>
                                </c:if>

                                <input type="file" id="imagem-upload" name="imagem" accept="image/*" style="display: none;">
                                <label for="imagem-upload" class="upload-label">
                                    <i class="fa-solid fa-cloud-arrow-up"></i> Escolher Imagem
                                </label>
                            </div>

                            <div class="form-group actions-group">
                                <button type="submit" class="submit-btn flex-1">
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