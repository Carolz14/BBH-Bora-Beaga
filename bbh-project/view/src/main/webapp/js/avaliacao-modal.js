(function () {
    'use strict';
    document.querySelectorAll('.btn-edit').forEach(function (btn) {
        if (btn.dataset.editAttached === '1') return;
        btn.dataset.editAttached = '1';
        btn.addEventListener('click', function () {
            const row = btn.closest('.avaliacao');
            if (!row) return;
            const id = row.getAttribute('data-id') || '';
            const nota = row.getAttribute('data-nota') || '';
            const comentarioDiv = row.querySelector('.data-comentario');
            const comentario = comentarioDiv ? comentarioDiv.textContent : '';
            openEditModal(id, nota, comentario);
        });
    });
    window.openEditModal = function (id, nota, comentario) {
        const idEl = document.getElementById('modal-id-avaliacao');
        const notaEl = document.getElementById('modal-nota');
        const comEl = document.getElementById('modal-comentario');
        if (idEl) idEl.value = id || '';
        if (notaEl) notaEl.value = (nota !== undefined && nota !== null) ? nota : '';
        if (comEl) comEl.value = comentario || '';
        const backdrop = document.getElementById('editModalBackdrop');
        if (backdrop) {
            backdrop.classList.add('show');
            backdrop.setAttribute('aria-hidden', 'false');
        }
    };

    window.closeEditModal = function () {
        const backdrop = document.getElementById('editModalBackdrop');
        if (backdrop) {
            backdrop.classList.remove('show');
            backdrop.setAttribute('aria-hidden', 'true');
        }
        const idEl = document.getElementById('modal-id-avaliacao');
        const notaEl = document.getElementById('modal-nota');
        const comEl = document.getElementById('modal-comentario');
        if (idEl) idEl.value = '';
        if (notaEl) notaEl.value = '';
        if (comEl) comEl.value = '';
    };
})();