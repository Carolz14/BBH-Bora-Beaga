<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.util.UsuarioTipo"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<%@ page import="bbh.service.GestaoUsuariosService" %>
<%@ page import="bbh.domain.Usuario" %>

<%
    String idParam = request.getParameter("id");
    Usuario estab = null;
    if (idParam != null) {
        Long id = Long.parseLong(idParam);
        GestaoUsuariosService service = new GestaoUsuariosService();
        estab = service.pesquisarPorId(id);
        request.setAttribute("estabelecimento", estab);
    }
%>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bora Beagá</title>

        <link rel="stylesheet" href="../../css/style-geral.css">
        <link rel="stylesheet" href="../../css/detalhe-estabelecimento.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    </head>
    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="container">
              <a href="pagina-principal.jsp" class="back-link">Voltar</a>

            <c:if test="${not empty estabelecimento}">
                <div class="estabelecimento">

                    <div class="estabelecimento-imagem">
                        <img src="${pageContext.request.contextPath}/uploads/${estabelecimento.imagem}" 
                             alt="Imagem do estabelecimento">
                    </div>

                    <div class="estabelecimento-detalhes">
                        <div class="tags">
                            <c:forEach var="tag" items="${estabelecimento.tags}">
                                <span>${tag}</span>
                            </c:forEach>
                        </div>

                        <h1>${estabelecimento.nome}</h1>

                        <div class="rating">
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-regular fa-star"></i>
                        </div>

                        <div class="informacao">
                            <p>${estabelecimento.descricao}</p>
                            <p><strong>Contato:</strong> ${estabelecimento.contato}</p>
                            <p><strong>Endereço:</strong> ${estabelecimento.endereco}</p>
                        </div>

                        <div class="action-buttons">
                            <button class="btn btn-visitado">
                                <i class="fa-solid fa-check"></i> Já visitei
                            </button>
                            <button class="btn btn-lista-interesse">
                                <i class="fa-regular fa-bookmark"></i> Salvar na lista de interesse
                            </button>
                        </div>

                        <div class="map">
                            <img src="../imgs/mapa.jpeg" alt="Mini mapa da localização">
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${empty estabelecimento}">
                <p>Estabelecimento não encontrado.</p>
            </c:if>

                
            </div>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>