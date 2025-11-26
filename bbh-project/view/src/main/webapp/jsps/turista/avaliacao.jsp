<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c" %>

<section class="avaliacoes">
    <h2>Avaliações</h2>

    <div>
        <strong>Média:</strong> <c:out value="${media}" />
        &nbsp;·&nbsp;
        <strong>Total:</strong> <c:out value="${avaliacoes.size()}" />
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

            <p style="white-space:pre-wrap; margin-top:8px;"><c:out value="${av.comentario}" /></p>

            <!-- Galeria de mídias -->
            <c:set var="listaMidias" value="${midiasPorAvaliacao[av.idAvaliacao]}" />
            <c:if test="${not empty listaMidias}">
                <div class="galeria-avaliacao" style="margin-top:8px;">
                    <c:forEach var="m" items="${listaMidias}">
                        <div style="display:inline-block;margin-right:8px;">
                            <img src="${pageContext.request.contextPath}/midia/serve?id=${m.idMidia}"
                                 alt="${m.nomeOriginal}"
                                 style="width:120px;height:90px;object-fit:cover;border-radius:6px;" />
                        </div>
                    </c:forEach>
                </div>
            </c:if>

        </div>
    </c:forEach>

    <!-- formulário para nova avaliação (pode mover se preferir) -->
    <div class="nova-avaliacao" style="margin-top:16px;">
        <h3>Deixe sua avaliação</h3>
        <form method="post" action="${pageContext.request.contextPath}/avaliacao/inserir">
            <input type="hidden" name="id" value="${estabelecimentoId}" />
            <label>Nota: <input type="number" name="nota" min="1" max="5" required/></label>
            <br/>
            <label>Comentário:<br/>
                <textarea name="comentario" rows="4" required></textarea>
            </label>
            <br/>
            <button type="submit">Enviar avaliação</button>
        </form>
    </div>
</section>
