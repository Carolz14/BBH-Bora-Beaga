<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<section class="avaliacoes">
    <h2>Avaliações</h2>

    <div>
        <strong>Média:</strong> <c:out value="${media}" />
        &nbsp;·&nbsp;
        <strong>Total:</strong> <c:out value="${empty avaliacoes ? 0 : avaliacoes.size()}" />
    </div>

    <c:if test="${empty avaliacoes}">
        <p>Seja o primeiro a avaliar este estabelecimento.</p>
    </c:if>

    <c:forEach var="av" items="${avaliacoes}">
        <div class="avaliacao"
             data-id="${av.idAvaliacao}"
             data-nota="${av.notaAvaliacao}"
             style="border-bottom:1px solid #eee; padding:8px 0;">

            <div style="display:flex;justify-content:space-between;align-items:center;">
                <div>
                    <strong>Usuário:</strong> <c:out value="${av.idUsuario}" />
                    &nbsp;·&nbsp;
                    <small><c:out value="${av.dataAvaliacao}" /></small>
                </div>
                <div>
                    <span class="nota-badge"><c:out value="${av.notaAvaliacao}" /></span>
                </div>
            </div>

            <p style="white-space:pre-wrap; margin-top:8px;">
                <c:out value="${av.comentario}" />
            </p>

            <div class="data-comentario" style="display:none;"><c:out value="${av.comentario}" /></div>

            <c:set var="listaMidias" value="${midiasPorAvaliacao[av.idAvaliacao]}" />

            <c:if test="${not empty listaMidias}">
                <div class="galeria-avaliacao" style="margin-top:8px; display:flex; flex-wrap:wrap; gap:8px;">
                    <c:forEach var="m" items="${listaMidias}">
                        <div class="midia-item" data-midia-id="${m.idMidia}" style="position:relative; padding:6px;">
                            <img class="midia-img"
                                 src="${pageContext.request.contextPath}/midia/serve?id=${m.idMidia}"
                                 alt="<c:out value='${m.nomeOriginal}'/>"
                                 style="width:120px;height:90px;object-fit:cover;border-radius:6px;display:block;" />

                            <c:if test="${ehAdmin or (idUsuario == av.idUsuario)}">
                                <div class="midia-actions" style="position:absolute; right:6px; top:6px;">
                                        <button type="button" class="action-toggle" aria-expanded="false" title="Opções"
                                                style="background:#fff;border:1px solid #ddd;border-radius:6px;padding:6px;cursor:pointer;">⋯</button>

                                        <div class="action-menu" role="menu" style="display:none; position:absolute; right:0; top:34px; min-width:140px; background:#fff; border:1px solid #e6e7ef; border-radius:6px; box-shadow:0 6px 18px rgba(0,0,0,0.06); z-index:100;">
                                            <button type="button" class="action-update" data-midia-id="${m.idMidia}" role="menuitem" style="display:block; padding:8px 12px; width:100%; text-align:left; background:none; border:0; cursor:pointer;">Atualizar mídia</button>
                                            <button type="button" class="action-remove" data-midia-id="${m.idMidia}" role="menuitem" style="display:block; padding:8px 12px; width:100%; text-align:left; background:none; border:0; cursor:pointer; color:#c00;">Remover mídia</button>
                                        </div>
                                    </div>

                                    <!-- forms escondidos usados pelo JS (sem onchange) -->
                                    <form class="midia-update-form" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/midia/atualizar" style="display:none;">
                                        <input type="hidden" name="id" value="${m.idMidia}" />
                                        <input class="file-input-hidden" type="file" name="file" accept="image/*" style="display:none;" />
                                    </form>

                                    <form class="midia-delete-form" method="post" action="${pageContext.request.contextPath}/midia/deletar" style="display:none;">
                                        <input type="hidden" name="id" value="${m.idMidia}" />
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>   
            </c:if>

            <c:if test="${empty listaMidias}">
                <c:if test="${ehAdmin or (idUsuario == av.idUsuario )}">
                   <div style="margin-top:8px;">
                    <!-- Form para inserir mídia: file hidden + botão simples -->
                    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/midia/upload" class="midia-insert-form" style="display:inline-block;">
                        <input type="hidden" name="id" value="${av.idAvaliacao}" />
                        <input class="file-input-hidden" type="file" name="file" accept="image/*" style="display:none;" onchange="this.form.submit()" />
                        <button type="button" class="btn-file-trigger btn-simple" title="Inserir imagem">Inserir mídia</button>
                    </form>

                    <!-- Caso queira mostrar o input visível como fallback, descomente abaixo e remova o style display:none acima -->
                    <%-- <input type="file" name="file" accept="image/*" required /> --%>
                </div> 
                </c:if>  
            </c:if>
            <c:if test="${ehAdmin or (idUsuario == av.idUsuario)}">
                 <div style="margin-top:8px;">
                <button type="button" class="btn-edit">Editar</button>
                <form method="post" action="${pageContext.request.contextPath}/avaliacao/deletar" style="display:inline;">
                    <input type="hidden" name="id_avaliacao" value="${av.idAvaliacao}" />
                    <input type="hidden" name="id" value="${estabelecimentoId}" />
                    <button type="submit">Excluir</button>
                </form>
            </div>
            </c:if>
        </div>
    </c:forEach>

    <!-- formulário para nova avaliação (mantido) -->
    <!-- substitui o bloco de nova-avaliacao / upload -->
    <div class="nova-avaliacao" style="margin-top:16px;">
        <h3>Deixe sua avaliação</h3>

        <form id="avaliacaoComMidiaForm" method="post" enctype="multipart/form-data"
              action="${pageContext.request.contextPath}/avaliacao/inserir-com-midia">
            <input type="hidden" name="id" value="${estabelecimentoId}" />

            <div class="avaliacao-grid" style="display:flex;gap:12px;align-items:flex-start;flex-wrap:wrap;">
                <div style="flex:1 1 420px; min-width:280px;">
                    <label style="display:block;font-weight:600;margin-bottom:6px;">Nota</label>
                    <input type="number" name="nota" min="1" max="5" required
                           style="width:80px;padding:6px;border-radius:4px;border:1px solid #ccc;"/>

                    <label style="display:block;font-weight:600;margin:10px 0 6px;">Comentário</label>
                    <textarea name="comentario" rows="6" required
                              style="width:100%;padding:8px;border-radius:6px;border:1px solid #ccc;resize:vertical;"></textarea>
                </div>

                <div style="flex:0 0 220px; min-width:200px;">
                    <label style="display:block;font-weight:600;margin-bottom:6px;">Imagem (opcional)</label>

                    <!-- botão estilizado que abre o file picker -->
                    <div style="display:flex;align-items:center;gap:8px;">
                        <button type="button" class="btn-file-trigger" style="
                                padding:8px 12px;
                                border-radius:6px;
                                background:linear-gradient(180deg,#fff,#eef2ff);
                                border:1px solid #cdd6ff;
                                box-shadow:0 2px 0 rgba(0,0,0,0.03);
                                cursor:pointer;
                                font-weight:600;
                                ">
                            📷 Inserir mídia
                        </button>

                        <!-- botão limpar (aparece quando tiver arquivo selecionado) -->
                        <button type="button" id="btn-clear-file" style="
                                display:none;
                                padding:6px 10px;border-radius:6px;border:1px solid #eee;background:#fff;cursor:pointer;
                                ">Remover</button> <!--  to falando desse botão gemini-->
                    </div>

                    <!-- input real (escondido): não possui onchange que submeta o form -->
                    <input id="inputFileAvaliacao" type="file" name="file" accept="image/*" style="display:none;" />

                    <div id="previewWrapper" style="margin-top:10px;">
                        <img id="previewImg" src="" alt="Preview"
                             style="width:200px;height:150px;object-fit:cover;border-radius:6px;display:none;border:1px solid #e0e0e0;" />
                        <div id="previewInfo" style="font-size:12px;color:#666;margin-top:6px;"></div>
                    </div>
                </div>
            </div>

            <div style="margin-top:12px;">
                <button id="btnSubmitAvaliacao" type="submit" style="
                        padding:8px 14px;border-radius:6px;border:none;background:#007bff;color:white;cursor:pointer;
                        box-shadow: 0 2px 8px rgba(2, 55, 120, 0.15);
                        ">Enviar avaliação + imagem</button>
                <span id="formStatus" style="margin-left:12px;color:#666;"></span>
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

</section>

<!-- CSS minimal para botões mais bonitos -->
<style>
    .btn-simple {
        background: #0b5ed7;
        color: #fff;
        border: none;
        padding: 6px 10px;
        border-radius: 6px;
        cursor: pointer;
        font-size: 13px;
    }
    .btn-simple[disabled] {
        opacity: 0.6;
        cursor: default;
    }
    .btn-simple:active {
        transform: translateY(1px);
    }
</style>
<script src="${pageContext.request.contextPath}/js/avaliacao-envio.js"></script>
<script src="${pageContext.request.contextPath}/js/avaliacao-modal.js"></script>
<script src="${pageContext.request.contextPath}/js/avaliacao-dropdown.js"></script>
