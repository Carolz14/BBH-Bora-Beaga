(function () {
    'use strict';
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

            setTimeout(() => btnConfirm && btnConfirm.focus(), 30);
            function cleanup(result) {
                btnConfirm && btnConfirm.removeEventListener('click', onConfirm);
                btnCancel && btnCancel.removeEventListener('click', onCancel);
                btnClose && btnClose.removeEventListener('click', onCancel);
                modal && modal.removeEventListener('click', onBackdrop);
                document.removeEventListener('keydown', onKeydown);

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

    function closeAll() {
        document.querySelectorAll('.action-menu').forEach(m => {
            m.style.display = 'none';
            m.classList.remove('open');
        });
        document.querySelectorAll('.action-toggle').forEach(b =>
            b.setAttribute('aria-expanded', 'false')
        );
    }

    document.addEventListener('click', function (e) {
        const toggle = e.target.closest('.action-toggle');
        if (toggle) {
            e.preventDefault();
            const container = toggle.parentNode;
            const menu = container.querySelector('.action-menu');
            const isOpen = menu.classList.contains('open');

            closeAll();
            if (!isOpen) {
                menu.style.display = 'block';
                menu.classList.add('open');
                toggle.setAttribute('aria-expanded', 'true');
            }
            return;
        }

        if (!e.target.closest('.action-menu') && !e.target.closest('.action-toggle')) {
            closeAll();
        }


        const rem = e.target.closest('.action-remove');
        if (rem) {
            e.preventDefault();
            const item = rem.closest('.midia-item');
            const delForm = item.querySelector('.midia-delete-form');
            const id = delForm.querySelector('input[name="id"]').value;
            showConfirmModal('Remover esta mídia?').then(confirmed => {
                if (!confirmed) return;

                fetch(delForm.action, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
                    body: "id=" + encodeURIComponent(id)
                }).then(r => {
                    if (!r.ok) throw new Error();
                    item.remove();
                    closeAll();
                }).catch(() => alert("Erro ao remover mídia"));
            });

            return;
        }

        const upd = e.target.closest('.action-update');
        if (upd) {
            e.preventDefault();

            const item = upd.closest('.midia-item');
            const updForm = item.querySelector('.midia-update-form');
            const fileInput = updForm.querySelector('input[type="file"]');
            if (fileInput.dataset.opening === "1") return;
            fileInput.dataset.opening = "1";

            const onChange = function () {
                delete fileInput.dataset.opening;
                fileInput.removeEventListener('change', onChange);

                if (updForm.requestSubmit) updForm.requestSubmit();
                else updForm.submit();
            };

            fileInput.addEventListener('change', onChange, { once: true });

            setTimeout(() => delete fileInput.dataset.opening, 1500);

            fileInput.click();
            closeAll();
            return;
        }

    }, false);

    document.addEventListener('keydown', ev => {
        if (ev.key === 'Escape') closeAll();
    });

})();
