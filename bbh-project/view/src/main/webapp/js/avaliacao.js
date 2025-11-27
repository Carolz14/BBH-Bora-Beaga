
function openEditModal(id, nota, comentario) {
    document.getElementById('modal-id-avaliacao').value = id || '';
    document.getElementById('modal-nota').value = nota || '';
    document.getElementById('modal-comentario').value = comentario || '';
    var backdrop = document.getElementById('editModalBackdrop');
    backdrop.classList.add('show');
    backdrop.setAttribute('aria-hidden', 'false');
}

function closeEditModal() {
    var backdrop = document.getElementById('editModalBackdrop');
    backdrop.classList.remove('show');
    backdrop.setAttribute('aria-hidden', 'true');
    // limpar
    document.getElementById('modal-id-avaliacao').value = '';
    document.getElementById('modal-nota').value = '';
    document.getElementById('modal-comentario').value = '';
}
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.btn-edit').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var row = btn.closest('.avaliacao');
            if (!row) return;
            var id = row.getAttribute('data-id') || '';
            var nota = row.getAttribute('data-nota') || '';
            var comentario = row.getAttribute('data-comentario') || '';
            openEditModal(id, nota, comentario);
        });
    });

    var backdropEl = document.getElementById('editModalBackdrop');
    backdropEl.addEventListener('click', function (e) {
        if (e.target === backdropEl) closeEditModal();
    });

    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') closeEditModal();
    });
});
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.midia-upload-form').forEach(form => {
        form.addEventListener('submit', function (evt) {
            evt.preventDefault();
            const status = form.querySelector('.midia-upload-status');
            const submitBtn = form.querySelector('.midia-upload-submit');
            const fileInput = form.querySelector('input[type=file]');

            if (!fileInput || fileInput.files.length === 0) {
                status.textContent = 'Selecione um arquivo.';
                return;
            }

            const fd = new FormData(form);
            submitBtn.disabled = true;
            status.textContent = 'Enviando...';

            fetch(form.action, {
                method: 'POST',
                body: fd,
                credentials: 'same-origin'
            }).then(resp => {
                if (resp.ok) {
                    location.reload();
                } else {
                    return resp.text().then(t => { throw new Error(t || resp.statusText); });
                }
            }).catch(err => {
                console.error(err);
                status.textContent = 'Erro ao enviar a imagem.';
                submitBtn.disabled = false;
            });
        });
    });

    document.querySelectorAll('.midia-delete-form').forEach(form => {
        form.addEventListener('submit', function (evt) {
            evt.preventDefault();
            const fd = new FormData(form);
            const btn = form.querySelector('button');
            btn.disabled = true;
            btn.textContent = 'Removendo...';

            fetch(form.action, {
                method: 'POST',
                body: fd,
                credentials: 'same-origin'
            }).then(resp => {
                if (resp.ok) {
                    const container = form.closest('.midia-item');
                    if (container) container.remove();
                } else {
                    return resp.text().then(t => { throw new Error(t || resp.statusText); });
                }
            }).catch(err => {
                console.error(err);
                btn.disabled = false;
                btn.textContent = 'Remover';
                alert('Erro ao remover mídia.');
            });
        });
    });
});
document.addEventListener('DOMContentLoaded', function () {
    const inputFile = document.getElementById('inputFileAvaliacao');
    const previewImg = document.getElementById('previewImg');
    const previewInfo = document.getElementById('previewInfo');

    inputFile.addEventListener('change', function () {
        const f = this.files && this.files[0];
        if (!f) {
            previewImg.style.display = 'none';
            previewImg.src = '';
            previewInfo.textContent = '';
            return;
        }
        if (!f.type.startsWith('image/')) {
            previewInfo.textContent = 'Selecione uma imagem válida.';
            return;
        }
        const url = URL.createObjectURL(f);
        previewImg.src = url;
        previewImg.style.display = 'block';
        previewInfo.textContent = `${f.name} — ${(f.size / 1024).toFixed(1)} KB`;
    });

    // Opcional: intercepta o submit e usa fetch para feedback AJAX
    const form = document.getElementById('avaliacaoComMidiaForm');
    const status = document.getElementById('formStatus');
    form.addEventListener('submit', function (evt) {
        // descomente para usar AJAX; por padrão deixe o comportamento tradicional se preferir fallback
        //evt.preventDefault();
        //status.textContent = 'Enviando...';
        //const fd = new FormData(form);
        //fetch(form.action, { method:'POST', body: fd, credentials:'same-origin' })
        //  .then(r => { if(r.redirected) window.location = r.url; else return r.text(); })
        //  .catch(e => { console.error(e); status.textContent = 'Erro ao enviar.'; });
    });
});