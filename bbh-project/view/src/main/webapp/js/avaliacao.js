/* *
 1. Lógica do Modal de Edição (Abrir/Fechar)
 2. Lógica de Upload de Arquivo (Trigger, Preview, Limpar)
 3. Lógica de Submissão AJAX (Nova Avaliação e Deletar Mídia)
 */

function openEditModal(id, nota, comentario) {
    document.getElementById('modal-id-avaliacao').value = id || '';
    document.getElementById('modal-nota').value = nota || '';
    document.getElementById('modal-comentario').value = comentario || '';

    var backdrop = document.getElementById('editModalBackdrop');
    if (backdrop) {
        backdrop.classList.add('show');
        backdrop.setAttribute('aria-hidden', 'false');
    }
}

function closeEditModal() {
    var backdrop = document.getElementById('editModalBackdrop');
    if (backdrop) {
        backdrop.classList.remove('show');
        backdrop.setAttribute('aria-hidden', 'true');
    }

    // Limpa os campos do modal
    const modalId = document.getElementById('modal-id-avaliacao');
    const modalNota = document.getElementById('modal-nota');
    const modalComentario = document.getElementById('modal-comentario');

    if (modalId) modalId.value = '';
    if (modalNota) modalNota.value = '';
    if (modalComentario) modalComentario.value = '';
}
// Script Principal
document.addEventListener('DOMContentLoaded', function () {
    'use strict';
    const formNovaAvaliacao = document.getElementById('avaliacaoComMidiaForm');
    const inputFile = document.getElementById('inputFileAvaliacao');
    const previewImg = document.getElementById('previewImg');
    const previewInfo = document.getElementById('previewInfo');
    const btnClear = document.getElementById('btn-clear-file');
    const formStatus = document.getElementById('formStatus');


    // Adiciona listener para todos os botões .btn-edit
    document.querySelectorAll('.btn-edit').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var row = btn.closest('.avaliacao');
            if (!row) return;

            var id = row.getAttribute('data-id') || '';
            var nota = row.getAttribute('data-nota') || '';
            var comentarioDiv = row.querySelector('.av-comentario');
            var comentario = comentarioDiv ? comentarioDiv.textContent : '';

            openEditModal(id, nota, comentario);
        });
    });

    // Listeners para fechar o modal (click fora ou ESC)
    var backdropEl = document.getElementById('editModalBackdrop');
    if (backdropEl) {
        backdropEl.addEventListener('click', function (e) {
            // Fecha só se clicar no fundo (backdrop) e não no "filho" (modal-window)
            if (e.target === backdropEl) closeEditModal();
        });
    }
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape' || e.key === 'Esc') {
            closeEditModal();
        }
    });

    // LÓGICA DE UPLOAD/PREVIEW DE ARQUIVO

    document.addEventListener('click', function (e) {
        const btn = e.target.closest('.btn-file-trigger');
        if (!btn) return;

        e.preventDefault();
        e.stopPropagation();

        const form = btn.closest('form');
        if (!form) return;

        // Procura o input DENTRO do form do botão
        const input = form.querySelector('input[type="file"]');
        if (!input) { alert('Campo de arquivo não encontrado.'); return; }

        if (btn.dataset.opening === '1') return; // Já está abrindo
        btn.dataset.opening = '1';
        btn.disabled = true;

        let timeoutId = null;
        const cleanup = () => {
            btn.removeAttribute('data-opening');
            btn.disabled = false;
            input.removeEventListener('change', onChange);
            if (timeoutId) clearTimeout(timeoutId);
        };

        const onChange = () => cleanup();
        input.addEventListener('change', onChange, { once: true });

        timeoutId = setTimeout(cleanup, 1200); // Trava de segurança

        try {
            input.click(); // Abre o seletor de arquivos
        } catch (err) {
            cleanup();
            console.error('Erro ao abrir file picker:', err);
            alert('Não foi possível abrir o seletor de arquivos. Veja o console.');
        }
    }, false);


    // 2. Preview de Imagem (quando o input de arquivo muda)
    if (inputFile) {
        inputFile.addEventListener('change', function () {
            const f = this.files && this.files[0];

            if (!f) {
                // Limpa se o usuário cancelar
                if (previewImg) {
                    previewImg.style.display = 'none';
                    previewImg.src = '';
                }
                if (previewInfo) previewInfo.textContent = '';
                if (btnClear) btnClear.style.display = 'none';
                return;
            }

            if (!f.type.startsWith('image/')) {
                if (previewInfo) previewInfo.textContent = 'Selecione uma imagem válida.';
                if (btnClear) btnClear.style.display = 'none';
                return;
            }

            const url = URL.createObjectURL(f);
            if (previewImg) {
                previewImg.src = url;
                previewImg.onload = () => { if (previewImg.dataset.oldUrl) URL.revokeObjectURL(previewImg.dataset.oldUrl); previewImg.dataset.oldUrl = url; };
            }
            if (previewInfo) previewInfo.textContent = `${f.name} — ${(f.size / 1024).toFixed(1)} KB`;
            if (btnClear) btnClear.style.display = 'inline-block';
        });
    }

    // 3. Botão "Remover" (Limpar seleção)
    if (btnClear) {
        btnClear.addEventListener('click', function (e) {
            e.preventDefault();
            if (inputFile) inputFile.value = '';
            if (previewImg) {
                previewImg.style.display = 'none';
                previewImg.src = '';
            }
            if (previewInfo) previewInfo.textContent = '';
            btnClear.style.display = 'none';
        });
    }

    // LÓGICA DE SUBMISSÃO DE FORMULÁRIOS (AJAX)
    // 1. Enviar NOVA Avaliação (Form: #avaliacaoComMidiaForm)
    if (formNovaAvaliacao) {
        formNovaAvaliacao.addEventListener('submit', function (evt) {
            evt.preventDefault();
            const submitBtn = formNovaAvaliacao.querySelector('button[type="submit"]');
            // O HTML diz que a imagem é opcional, então não validamos se ela existe
            const fd = new FormData(formNovaAvaliacao);
            if (submitBtn) submitBtn.disabled = true;
            if (formStatus) formStatus.textContent = 'Enviando...';

            fetch(formNovaAvaliacao.action, {
                method: 'POST',
                body: fd,
                credentials: 'same-origin',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            }).then(resp => {
                if (resp.ok) {
                    location.reload();
                } else {
                    return resp.text().then(t => { throw new Error(t || resp.statusText); });
                }
            }).catch(err => {
                console.error(err);
                if (formStatus) formStatus.textContent = 'Erro ao enviar a avaliação.';
                if (submitBtn) submitBtn.disabled = false;
            });
        });
    }

    // 2. Deletar Mídia Existente (Form: .midia-delete-form)
    document.querySelectorAll('.midia-delete-form').forEach(form => {
        form.addEventListener('submit', function (evt) {
            evt.preventDefault();
            const btn = form.querySelector('button[type="submit"], button');
            if (btn) { btn.disabled = true; btn.textContent = 'Removendo...'; }

            let idValue = '';
            const hid = form.querySelector('input[name="id"]');
            if (hid) idValue = (hid.value || '').trim();

            // Fallback para pegar do data-attribute
            if (!idValue) {
                const container = form.closest('.midia-item');
                if (container) idValue = container.dataset.midiaId || container.getAttribute('data-midia-id') || '';
            }

            if (!idValue) {
                if (btn) { btn.disabled = false; btn.textContent = 'Remover'; }
                alert('Erro: id da mídia não encontrado no form. Inspecione o HTML.');
                return;
            }

            const body = new URLSearchParams();
            body.append('id', idValue);

            fetch(form.action, {
                method: 'POST',
                body: body.toString(),
                credentials: 'same-origin',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            }).then(async resp => {
                console.log('Remover midia - status', resp.status);
                const text = await resp.text();
                console.log('Remover midia - response text:', text);

                if (resp.ok) {
                    const container = form.closest('.midia-item');
                    if (container) container.remove();
                    return;
                }
                throw new Error(text || resp.statusText);
            }).catch(err => {
                console.error('Erro remover mídia (detalhe):', err);
                if (btn) { btn.disabled = false; btn.textContent = 'Remover'; }
                alert('Erro ao remover mídia: ' + (err.message || 'erro desconhecido'));
            });
        });
    });
});