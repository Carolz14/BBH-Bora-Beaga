
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
            var comentarioDiv = row.querySelector('.data-comentario');
            var comentario = comentarioDiv ? comentarioDiv.textContent : '';
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

            const btn = form.querySelector('button[type="submit"], button');
            if (btn) { btn.disabled = true; btn.textContent = 'Removendo...'; }

            let idValue = '';
            const hid = form.querySelector('input[name="id"]');
            if (hid) idValue = (hid.value || '').trim();

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

    const form = document.getElementById('avaliacaoComMidiaForm');
    const status = document.getElementById('formStatus');
    form.addEventListener('submit', function (evt) {
    });
});
