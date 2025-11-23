<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${estabelecimento.nome} - Bora Beagá</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detalhe-estabelecimento.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
        <style> 
        .avaliacoes {
            margin-top: 2rem;
        }
        .avaliacao {
            border-bottom: 1px solid #eee;
            padding: 12px 0;
        }
        .nota-badge {
            background:#f0c14b;
            padding:4px 8px;
            border-radius:6px;
            font-weight:600;
        }
        .nova-avaliacao {
            margin-top:1.5rem;
            padding:12px;
            border:1px solid #eee;
            border-radius:6px;
        }
        .btn-link {
            background:none;
            border:none;
            color:#007bff;
            cursor:pointer;
            padding:0;
        } 
        </style>
    </head>

    <body>

        <%@ include file="../header.jsp" %>

        <main>
            <div class="container">

                <a href="${pageContext.request.contextPath}/jsps/turista/pagina-principal.jsp" class="back-link">
                    Voltar
                </a>

                <c:if test="${not empty estabelecimento}">
                    <div class="estabelecimento">

                        <div class="estabelecimento-imagem">
                            <img src="${pageContext.request.contextPath}/imagens/restaurante.jpeg"
                                 alt="Imagem do estabelecimento">
                        </div>

                        <div class="estabelecimento-detalhes">
                            <h1>${estabelecimento.nome}</h1>

                            <!-- Aqui depois vamos integrar a média real -->
                            <div class="rating">
                                <i class="fa-solid fa-star"></i>
                                <i class="fa-solid fa-star"></i>
                                <i class="fa-solid fa-star"></i>
                                <i class="fa-solid fa-star"></i>
                                <i class="fa-regular fa-star"></i>
                            </div>

                            <div class="informacao">
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
                                <img src="${pageContext.request.contextPath}/imagens/mapa.jpeg"
                                     alt="Mini mapa da localização">
                            </div>
                        </div>
                    </div>

                    <section class="avaliacoes">

                        <h2>Avaliações</h2>

                        <div>
                            <strong>Média:</strong> ${media}
                            &nbsp;·&nbsp;
                            <strong>Total:</strong> ${avaliacoes.size()}
                        </div>

                        <c:if test="${empty avaliacoes}">
                            <p>Seja o primeiro a avaliar este estabelecimento.</p>
                        </c:if>

                        <c:forEach var="av" items="${avaliacoes}">
                            <div class="avaliacao">

                                <div style="display:flex;justify-content:space-between;align-items:center;">
                                    <div>
                                        <strong>Usuário:</strong> ${av.idUsuario}
                                        &nbsp;·&nbsp;
                                        <small>${av.dataAvaliacao}</small>
                                    </div>

                                    <span class="nota-badge">${av.notaAvaliacao}</span>

                                    <form method="post" action="${pageContext.request.contextPath}/avaliacao/deletar"
                                          onsubmit="return confirm('Excluir avaliação?');"
                                          style="margin:0;">
                                        <input type="hidden" name="id_avaliacao" value="${av.idAvaliacao}">
                                        <input type="hidden" name="id" value="${estabelecimento.id}">
                                        <button type="submit" class="btn-link">Excluir</button>
                                    </form>
                                </div>

                                <p style="white-space:pre-wrap;">
                                    <c:out value="${av.comentario}" />
                                </p>
                            </div>
                        </c:forEach>

                        <!-- FORM NOVA AVALIAÇÃO -->
                        <div class="nova-avaliacao">
                            <h3>Deixe sua avaliação</h3>

                            <form method="post" action="${pageContext.request.contextPath}/avaliacao/inserir">
                                <input type="hidden" name="id" value="${estabelecimento.id}" />

                                <label>
                                    Nota:
                                    <input name="nota" type="number" min="1" max="5" required />
                                </label>
                                <br><br>

                                <label>
                                    Comentário:
                                    <textarea name="comentario" rows="4" required></textarea>
                                </label>
                                <br><br>

                                <button type="submit" class="btn btn-success">Enviar avaliação</button>
                            </form>
                        </div>
                    </section>
                </c:if>

                <c:if test="${empty estabelecimento}">
                    <p>Estabelecimento não encontrado.</p>
                </c:if>

            </div>
        </main>

        <%@ include file="../footer.jsp" %>

    </body>
</html>
