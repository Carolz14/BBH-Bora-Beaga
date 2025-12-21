<%@page pageEncoding="UTF-8" %>
<head>
    <meta charset="UTF-8">
   
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/avaliacao-roteiro.css">
   
</head>

<div class="forum-container">
    <h2> Dúvidas e Comentários</h2>


        <div class="comentarios-lista">
            <c:forEach var="comentario" items="${comentarios}">
                <div class="comentario-item">
                    <div class="comentario-header">
                        <div class="comentario-autor-data">
                             <strong>${comentario.nomeUsuario}</strong>
                           
                            <span class="data-comentario">
                                &bull; ${comentario.dataFormatada}
                            </span>
                        </div>

                      
                            <c:if test="${sessionScope.usuario.id == comentario.usuarioId}">
                                <form action="${pageContext.request.contextPath}/bbh/ExcluirComentarioController"
                                    method="POST"
                                    onsubmit="return confirm('Tem certeza que deseja apagar este comentário?');"
                                    style="margin:0;">
                                    <input type="hidden" name="comentarioId" value="${comentario.id}">
                                    <input type="hidden" name="roteiroId" value="${roteiro.id}">
                                    <button type="submit" class="btn-excluir-comentario" title="Excluir Comentário">
                                        <i class="fa-solid fa-trash-can"></i>
                                    </button>
                                </form>
                            </c:if>
                    </div>

                    <p class="comentario-texto">${comentario.texto}</p>

                  
                        <c:if test="${not empty comentario.imagemUrl}">
                            <div class="comentario-anexo">
                               <a href="/imagens-bbh/comentarios/${comentario.imagemUrl}" target="_blank">
            <img src="/imagens-bbh/comentarios/${comentario.imagemUrl}" class="img-anexo">
        </a>
                            </div>
                        </c:if>
                </div>
                <hr class="divisor-comentario">
            </c:forEach>

            <c:if test="${empty comentarios}">
                <p style="text-align:center; color: #999; padding: 20px;">
                    Ninguém comentou ainda. Seja o primeiro!
                </p>
            </c:if>
        </div>

            <c:if test="${not empty sessionScope.usuario}">
                <form action="${pageContext.request.contextPath}/bbh/AdicionarComentarioController" method="POST"
                    enctype="multipart/form-data" class="novo-comentario-form">

                    <input type="hidden" name="roteiroId" value="${roteiro.id}">

                    <textarea class="caixas" name="texto" required placeholder="Escreva sua dúvida ou comentário"
                        rows="3"></textarea>

                    <div class="form-actions">
                        <div class="file-input-wrapper">
                            <label for="anexo" class="btn-anexo">
                                <i class="fa-regular fa-image"></i> Adicionar Foto
                            </label>
                            <input type="file" name="anexo" id="anexo" accept="image/*" style="display:none;"
                               >
                        </div>

                        <button type="submit" class="btn-comentar">
                            Enviar</i>
                        </button>
                    </div>
                </form>
            </c:if>
          
</div>

