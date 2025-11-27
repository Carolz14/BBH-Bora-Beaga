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
        <div class="avaliacao" style="border-bottom:1px solid #eee; padding:8px 0;">
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

            <!-- ======= Galeria de mídias (renderiza mídias já salvas) ======= -->
            <c:set var="listaMidias" value="${midiasPorAvaliacao[av.idAvaliacao]}" />
            <c:if test="${not empty listaMidias}">
                <div class="galeria-avaliacao" style="margin-top:8px; display:flex; flex-wrap:wrap; gap:8px;">
                    <c:forEach var="m" items="${listaMidias}">
                        <div class="midia-item" data-midia-id="${m.idMidia}" style="position:relative;">
                            <img
                                src="${pageContext.request.contextPath}/midia/serve?id=${m.idMidia}"
                                alt="<c:out value='${m.nomeOriginal}'/>"
                                style="width:120px;height:90px;object-fit:cover;border-radius:6px;display:block;"
                                />
                            <!-- Deletar (form tradicional - fallback sem JS) -->
                            <form class="midia-delete-form" method="post" action="${pageContext.request.contextPath}/midia/deletar" style="margin:6px 0 0 0; text-align:center;">
                                <input type="hidden" name="id" value="${m.idMidia}" />
                                <button type="submit" class="midia-delete-btn" style="font-size:12px;">Remover</button>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            

            <!-- ======= Editar / Excluir avaliação (sem verificação de permissão) ======= -->
            <div style="margin-top:8px;">
                <button onclick="openEditModal(${av.idAvaliacao}, '${av.notaAvaliacao}', '${av.comentario}')">
                    Editar
                </button>

                <form method="post" action="${pageContext.request.contextPath}/avaliacao/deletar" style="display:inline;">
                    <input type="hidden" name="id_avaliacao" value="${av.idAvaliacao}" />
                    <input type="hidden" name="id" value="${estabelecimentoId}" />
                    <button type="submit">Excluir</button>
                </form>
            </div>
        </div>
    </c:forEach>

    <!-- substitui o bloco de nova-avaliacao / upload -->
    <div class="nova-avaliacao" style="margin-top:16px;">
        <h3>Deixe sua avaliação</h3>

        <form id="avaliacaoComMidiaForm" method="post" enctype="multipart/form-data"
              action="${pageContext.request.contextPath}/avaliacao/inserir-com-midia">
            <input type="hidden" name="id" value="${estabelecimentoId}" />

            <div class="avaliacao-grid" style="display:flex;gap:12px;align-items:flex-start;flex-wrap:wrap;">
                <!-- texto da avaliação -->
                <div style="flex:1 1 420px; min-width:280px;">
                    <label style="display:block;font-weight:600;margin-bottom:6px;">Nota</label>
                    <input type="number" name="nota" min="1" max="5" required style="width:80px;padding:6px;border-radius:4px;border:1px solid #ccc;"/>

                    <label style="display:block;font-weight:600;margin:10px 0 6px;">Comentário</label>
                    <textarea name="comentario" rows="6" required style="width:100%;padding:8px;border-radius:6px;border:1px solid #ccc;resize:vertical;"></textarea>
                </div>

                <!-- upload e preview -->
                <div style="flex:0 0 220px; min-width:200px;">
                    <label style="display:block;font-weight:600;margin-bottom:6px;">Imagem (opcional)</label>
                    <input id="inputFileAvaliacao" type="file" name="file" accept="image/*" />

                    <div id="previewWrapper" style="margin-top:10px;">
                        <img id="previewImg" src="" alt="Preview" style="width:200px;height:150px;object-fit:cover;border-radius:6px;display:none;border:1px solid #e0e0e0;" />
                        <div id="previewInfo" style="font-size:12px;color:#666;margin-top:6px;"></div>
                    </div>
                </div>
            </div>

            <div style="margin-top:12px;">
                <button type="submit" style="padding:8px 14px;border-radius:6px;border:none;background:#007bff;color:white;cursor:pointer;">Enviar avaliação + imagem</button>
                <span id="formStatus" style="margin-left:12px;color:#666;"></span>
            </div>
        </form>
    </div>

    <!-- Modal de edição (mantido) -->
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

<script src="${pageContext.request.contextPath}/js/avaliacao.js"></script>
