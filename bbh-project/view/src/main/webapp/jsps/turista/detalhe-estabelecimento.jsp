<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${estabelecimento.nome}" /> - Bora Beagá</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detalhe-estabelecimento.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <style>
        .avaliacoes { margin-top: 2rem; }
        .avaliacao { border-bottom: 1px solid #eee; padding: 12px 0; }
        .nota-badge { background:white; padding:4px 8px; border-radius:6px; font-weight:600; }
        .nova-avaliacao { margin-top:1.5rem; padding:12px; border:1px solid #eee; border-radius:6px; }
        .btn-link { background:none; border:none; color:#007bff; cursor:pointer; padding:0; }
        /* Modal básico */
        .modal-backdrop {
            display: none;
            position: fixed;
            inset: 0;
            background: rgba(0,0,0,0.5);
            align-items: center;
            justify-content: center;
            z-index: 1000;
        }
        .modal-backdrop.show { display: flex; }
        .modal-window {
            background: white;
            width: 100%;
            max-width: 720px;
            border-radius: 8px;
            padding: 18px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.25);
        }
        .modal-header { display:flex; justify-content:space-between; align-items:center; margin-bottom:8px; }
        .modal-footer { display:flex; gap:8px; justify-content:flex-end; margin-top:12px; }
        .modal-close { background:none; border:none; font-size:18px; cursor:pointer; }
        label { display:block; margin-bottom:6px; font-weight:500; }
        input[type="number"], textarea { width:100%; padding:8px; border:1px solid #ccc; border-radius:4px; }
    </style>
</head>

<body>

<%@ include file="../header.jsp" %>

<main>
    <div class="container">

        <a href="${pageContext.request.contextPath}/jsps/turista/pagina-principal.jsp" class="back-link">Voltar</a>

        <c:if test="${not empty estabelecimento}">
            <div class="estabelecimento">
                <div class="estabelecimento-imagem">
                    <img src="${pageContext.request.contextPath}/imagens/restaurante.jpeg" alt="Imagem do estabelecimento" />
                </div>

                <div class="estabelecimento-detalhes">
                    <h1><c:out value="${estabelecimento.nome}" /></h1>

                    <div class="rating">
                        <span class="nota-badge"><c:out value="${media}" /></span>
                        <small>(média baseada em <c:out value="${avaliacoes.size()}" /> avaliações)</small>
                    </div>

                    <div class="informacao">
                        <p><strong>Contato:</strong> <c:out value="${estabelecimento.contato}" /></p>
                        <p><strong>Endereço:</strong> <c:out value="${estabelecimento.endereco}" /></p>
                    </div>
                </div>
            </div>

            <jsp:include page="/avaliacao/listar" flush="true">
                <jsp:param name="id" value="${estabelecimento.id}" />
            </jsp:include>
            
        </c:if>

        <c:if test="${empty estabelecimento}">
            <p>Estabelecimento não encontrado.</p>
        </c:if>

    </div>
</main>

<!-- Modal de edição -->
<div id="editModalBackdrop" class="modal-backdrop" aria-hidden="true">
    <div class="modal-window" role="dialog" aria-modal="true" aria-labelledby="modalTitle">
        <div class="modal-header">
            <h3 id="modalTitle">Editar Avaliação</h3>
            <button class="modal-close" aria-label="Fechar" onclick="closeEditModal()">✕</button>
        </div>

        <form id="editForm" method="post" action="${pageContext.request.contextPath}/avaliacao/atualizar">
            <input type="hidden" name="id_avaliacao" id="modal-id-avaliacao" value="" />
            <input type="hidden" name="id" value="${estabelecimento.id}" />

            <div>
                <label>Nota (1 a 5)
                    <input type="number" name="nota" id="modal-nota" min="1" max="5" required />
                </label>
            </div>

            <div style="margin-top:8px;">
                <label>Comentário
                    <textarea name="comentario" id="modal-comentario" rows="5" required></textarea>
                </label>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeEditModal()">Cancelar</button>
                <button type="submit" class="btn btn-primary">Salvar</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="../footer.jsp" %>
<script src="${pageContext.request.contextPath}/js/avaliacao.js"></script>
</body>
</html>
