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

    function showConfirmModal(message) {
        return new Promise((resolve) => {
            const modal = document.getElementById('confirmDeleteModal');
            if (!modal) {
                resolve(window.confirm(message));
                return;
            }

            const msgEl = document.getElementById('confirmDeleteMessage');
            const btnConfirm = document.getElementById('confirmDeleteBtn');
            const btnCancel = document.getElementById('confirmCancelBtn');
            const btnClose = document.getElementById('confirmCloseBtn');

            if (msgEl) msgEl.textContent = message || 'Confirmar?';

            modal.classList.add('show');
            modal.setAttribute('aria-hidden', 'false');

            // foco acessível no botão confirmar
            setTimeout(() => btnConfirm && btnConfirm.focus(), 30);
            function cleanup(result) {
                // remover listeners
                btnConfirm && btnConfirm.removeEventListener('click', onConfirm);
                btnCancel && btnCancel.removeEventListener('click', onCancel);
                btnClose && btnClose.removeEventListener('click', onCancel);
                modal && modal.removeEventListener('click', onBackdrop);
                document.removeEventListener('keydown', onKeydown);

                // esconder modal
                modal.classList.remove('show');
                modal.setAttribute('aria-hidden', 'true');

                resolve(result);
            }

            function onConfirm(e) { e && e.preventDefault(); cleanup(true); }
            function onCancel(e) { e && e.preventDefault(); cleanup(false); }
            function onBackdrop(e) { if (e.target === modal) cleanup(false); }
            function onKeydown(e) { if (e.key === 'Escape') cleanup(false); }

            btnConfirm && btnConfirm.addEventListener('click', onConfirm);
            btnCancel && btnCancel.addEventListener('click', onCancel);
            btnClose && btnClose.addEventListener('click', onCancel);
            modal && modal.addEventListener('click', onBackdrop);
            document.addEventListener('keydown', onKeydown);
        });
    }

})();
(function () {
    'use strict';

    const lightboxModal = document.getElementById('imageLightboxModal');
    const lightboxImg = document.getElementById('lightboxImg');
    const lightboxClose = document.getElementById('lightboxCloseBtn');

    if (!lightboxModal || !lightboxImg) return;

    function openLightbox(src, alt, trigger) {
        lightboxImg.src = src || '';
        lightboxImg.alt = alt || '';
        lightboxModal.classList.add('show');
        lightboxModal.setAttribute('aria-hidden', 'false');
        lightboxModal._trigger = trigger || null;
        setTimeout(() => { lightboxClose && lightboxClose.focus(); }, 30);
    }

    function closeLightbox() {
        lightboxModal.classList.remove('show');
        lightboxModal.setAttribute('aria-hidden', 'true');
        try { if (lightboxModal._trigger && typeof lightboxModal._trigger.focus === 'function') lightboxModal._trigger.focus(); } catch (e) { }
        // limpa src levemente depois para liberar memória
        setTimeout(() => { lightboxImg.src = ''; lightboxImg.alt = ''; lightboxModal._trigger = null; }, 150);
    }

    document.addEventListener('click', function (ev) {
        const thumb = ev.target.closest && ev.target.closest('.midia-img');
        if (!thumb) return;
        if (ev.target.closest('.action-toggle') || ev.target.closest('.action-menu')) return;
        ev.preventDefault();
        const src = thumb.currentSrc || thumb.src;
        openLightbox(src, thumb.alt || '', thumb);
    }, false);

    lightboxClose && lightboxClose.addEventListener('click', function (e) { e.preventDefault(); closeLightbox(); });

    lightboxModal.addEventListener('click', function (ev) {
        if (ev.target === lightboxModal) closeLightbox();
    });

    document.addEventListener('keydown', function (ev) {
        if ((ev.key === 'Escape' || ev.key === 'Esc') && lightboxModal.classList.contains('show')) {
            closeLightbox();
        }
    });

})();
