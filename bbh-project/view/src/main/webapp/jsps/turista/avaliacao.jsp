<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head>
    <title>Avaliações</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/avaliacoes.css" />
</head>


<section class="avaliacoes">
    <h1 style="margin-bottom:15px; margin-left:5px">Avaliações</h1>

    <div style="margin-left:4px">
        <strong>Média do estabelecimento:</strong> <c:out value="${media}" />
        &nbsp;·&nbsp;
        <strong>Número de avaliações:</strong> <c:out value="${empty avaliacoes ? 0 : avaliacoes.size()}" />
    </div>

    <c:if test="${empty avaliacoes}">
        <p>Seja o primeiro a avaliar este estabelecimento.</p>
    </c:if>

    <c:forEach var="av" items="${avaliacoes}">
        <div class="avaliacao"


             data-id="${av.idAvaliacao}"
             data-nota="${av.notaAvaliacao}">

            <div class="avaliacao-top">
                <div>
                    <strong>Usuário:</strong> <c:out value="${av.idUsuario}" />
                    &nbsp;·&nbsp;
                    <small><c:out value="${av.dataAvaliacao}" /></small>
                </div>
                <div>
                    <div class="rating">
                        <c:forEach var="i" begin="1" end="${av.notaAvaliacao}">
                            <img src="<c:url value='/imagens/estrela-png.png' />"
                                 alt="" aria-hidden="true" class="nota-star" />
                        </c:forEach>
                    </div>
                </div>
            </div>

            <p class="comentario">
                <c:out value="${av.comentario}" />
            </p>
            <div class="data-comentario" style="display:none;"><c:out value="${av.comentario}" /></div>

            <c:set var="listaMidias" value="${midiasPorAvaliacao[av.idAvaliacao]}" />

            <c:if test="${not empty listaMidias}">
                <div class="galeria-avaliacao">
                    <c:forEach var="m" items="${listaMidias}">
                        <div class="midia-item" data-midia-id="${m.idMidia}">
                            <img class="midia-img"
                                 src="${pageContext.request.contextPath}/midia/serve?id=${m.idMidia}"
                                 alt="<c:out value='${m.nomeOriginal}'/>" />

                            <c:if test="${ehAdmin or (idUsuario == av.idUsuario)}">
                                <div class="midia-actions">
                                    <button type="button" class="action-toggle" aria-expanded="false" title="Opções">⋯</button>

                                    <div class="action-menu" role="menu">
                                        <button type="button" class="action-update" data-midia-id="${m.idMidia}" role="menuitem">Atualizar mídia</button>
                                        <button type="button" class="action-remove" data-midia-id="${m.idMidia}" role="menuitem">Remover mídia</button>
                                    </div>
                                </div>

                                <!-- forms escondidos usados pelo JS -->
                                <form class="midia-update-form hidden-form" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/midia/atualizar">
                                    <input type="hidden" name="id" value="${m.idMidia}" />
                                    <input class="file-input-hidden" type="file" name="file" accept="image/*" />
                                </form>

                                <form class="midia-delete-form hidden-form" method="post" action="${pageContext.request.contextPath}/midia/deletar">
                                    <input type="hidden" name="id" value="${m.idMidia}" />
                                </form>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty listaMidias}">
                <c:if test="${ehAdmin or (idUsuario == av.idUsuario )}">
                    <div class="midia-insert-wrapper">
                        <!-- Form para inserir mídia: file hidden + botão simples -->
                        <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/midia/upload" class="midia-insert-form">
                            <input type="hidden" name="id" value="${av.idAvaliacao}" />
                            <input class="file-input-hidden" type="file" name="file" accept="image/*" onchange="this.form.submit()" style="display:none" />
                            <button type="button" class="btn-file-trigger btn-simple" title="Inserir imagem">Inserir mídia</button>
                        </form>

                        <!-- Caso queira mostrar o input visível como fallback, descomente abaixo -->
                        <%-- <input type="file" name="file" accept="image/*" required /> --%>
                    </div>
                </c:if>
            </c:if>

            <c:if test="${ehAdmin or (idUsuario == av.idUsuario)}">
                <div class="avaliacao-actions">
                    <button type="button" class="btn-edit btn-simple">Editar avaliação</button>
                    <form method="post" action="${pageContext.request.contextPath}/avaliacao/deletar" class="inline-form">
                        <input type="hidden" name="id_avaliacao" value="${av.idAvaliacao}" />
                        <input type="hidden" name="id" value="${estabelecimentoId}" />
                        <button class="btn-simple-remove" type="submit">Excluir avaliação</button>
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>

    <!-- formulário para nova avaliação -->
    <div class="nova-avaliacao">
        <h3>Deixe sua avaliação</h3>

        <form id="avaliacaoComMidiaForm" method="post" enctype="multipart/form-data"
              action="${pageContext.request.contextPath}/avaliacao/inserir-com-midia">
            <input type="hidden" name="id" value="${estabelecimentoId}" />

            <div class="avaliacao-grid">
                <div class="avaliacao-main">
                    <label class="label-block">Nota :</label>
                    <input type="number" name="nota" min="1" max="5" required class="nota-input" />

                    <label class="label-block">Comentário :</label>
                    <textarea name="comentario" rows="6" required class="comentario-input"></textarea>
                </div>

                <div class="avaliacao-side">
                    <label class="label-block">Imagem :</label>

                    <!-- controles de arquivo -->
                    <div class="file-controls">
                        <button type="button" class="btn-file-trigger btn-simple">Inserir mídia</button>
                        <button type="button" id="btn-clear-file" class="btn-clear-file btn-simple-remove">Remover</button>
                    </div>

                    <!-- input real (escondido): não possui onchange que submeta o form -->
                    <input id="inputFileAvaliacao" type="file" name="file" accept="image/*" class="file-input-hidden"  style="display:none"/>

                    <div id="previewWrapper">
                        <img id="previewImg" src="" alt="Preview" />
                        <div id="previewInfo"></div>
                    </div>
                </div>
            </div>

            <div class="form-actions">
                <button id="btnSubmitAvaliacao" class="btn-edit btn-simple" type="submit">Enviar avaliação</button>
                <span id="formStatus"></span>
            </div>
        </form>
    </div>

    <div id="editModalBackdrop" class="modal-backdrop" aria-hidden="true">
        <div class="modal-window" role="dialog" aria-modal="true" aria-labelledby="modalTitle">
            <div class="modal-header">
                <h3 id="modalTitle">Editar Avaliação</h3>
                <button class="modal-close" aria-label="Fechar" onclick="closeEditModal()">✕</button>
            </div>
            <form id="editForm" method="post" action="${pageContext.request.contextPath}/avaliacao/atualizar">
                <input type="hidden" name="id_avaliacao" id="modal-id-avaliacao" value="" />
                <input type="hidden" name="id" value="${estabelecimentoId}" />
                <div>
                    <label>Nota :
                        <input type="number" class="nota-input" name="nota" id="modal-nota" min="1" max="5" required />
                    </label>
                </div>
                <div>
                    <label>Comentário:
                        <textarea name="comentario" id="modal-comentario" class="comentario-input" rows="5" required></textarea>
                    </label>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn-edit btn-simple" onclick="closeEditModal()">Cancelar</button>
                    <button type="submit" class="btn-edit btn-simple">Salvar</button>
                </div>
            </form>
        </div>
    </div>
    <div id="confirmDeleteModal" class="modal-backdrop" aria-hidden="true" aria-labelledby="confirmDeleteTitle" role="dialog">
        <div class="modal-window" role="document" style="max-width:420px;">
            <div class="modal-header">
                <h3 id="confirmDeleteTitle">Confirmar exclusão</h3>
                <button class="modal-close" id="confirmCloseBtn" aria-label="Fechar">✕</button>
            </div>

            <div class="modal-body" style="padding:8px 4px 0 4px;">
                <p id="confirmDeleteMessage" style="color:#333; margin:8px 4px;">Tem certeza que deseja excluir este item? Esta ação não pode ser desfeita.</p>
            </div>

            <div class="modal-footer" style="gap:8px; display:flex; justify-content:flex-end; padding-top:8px;">
                <button type="button" id="confirmCancelBtn" class="btn-simple" aria-label="Cancelar">Cancelar</button>
                <button type="button" id="confirmDeleteBtn" class="btn-simple-remove" aria-label="Confirmar exclusão">Excluir</button>
            </div>
        </div>
    </div>
    <div id="imageLightboxModal" class="modal-backdrop" aria-hidden="true" aria-labelledby="imageLightboxTitle" role="dialog">
        <button id="lightboxCloseBtn" class="lightbox-close" aria-label="Fechar">✕</button>
        <img id="lightboxImg" src="" alt="" class="lightbox-image" />
    </div>

</section>

<script src="${pageContext.request.contextPath}/js/avaliacao-envio.js"></script>
<script src="${pageContext.request.contextPath}/js/avaliacao-modal.js"></script>
<script src="${pageContext.request.contextPath}/js/avaliacao-dropdown.js"></script>
<script src="${pageContext.request.contextPath}/js/confirmar-exclusao.js"></script>
