// avaliacao-dropdown.js — controla apenas o dropdown + update/delete
(function () {
    'use strict';

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

        // -------------------------------------
        // 1) Toggle do menu
        // -------------------------------------
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

        // clique fora fecha
        if (!e.target.closest('.action-menu') && !e.target.closest('.action-toggle')) {
            closeAll();
        }

        // -------------------------------------
        // 2) REMOVER mídia
        // -------------------------------------
        const rem = e.target.closest('.action-remove');
        if (rem) {
            e.preventDefault();
            const item = rem.closest('.midia-item');
            const delForm = item.querySelector('.midia-delete-form');
            const id = delForm.querySelector('input[name="id"]').value;

            if (!confirm('Remover esta mídia?')) return;

            fetch(delForm.action, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
                body: "id=" + encodeURIComponent(id)
            }).then(r => {
                if (!r.ok) throw new Error();
                item.remove();   // remove o card do DOM
                closeAll();
            }).catch(() => alert("Erro ao remover mídia"));
            return;
        }

        // -------------------------------------
        // 3) ATUALIZAR mídia
        // -------------------------------------
        const upd = e.target.closest('.action-update');
        if (upd) {
            e.preventDefault();

            const item = upd.closest('.midia-item');
            const updForm = item.querySelector('.midia-update-form');
            const fileInput = updForm.querySelector('input[type="file"]');

            // evitar duplo clique
            if (fileInput.dataset.opening === "1") return;
            fileInput.dataset.opening = "1";

            // Quando o usuário seleciona a imagem
            const onChange = function () {
                delete fileInput.dataset.opening;
                fileInput.removeEventListener('change', onChange);

                // Submit seguro para multipart/form-data
                if (updForm.requestSubmit) updForm.requestSubmit();
                else updForm.submit();
            };

            fileInput.addEventListener('change', onChange, { once: true });

            // limpa flag caso nada aconteça
            setTimeout(() => delete fileInput.dataset.opening, 1500);

            fileInput.click();   // abre o seletor de arquivos
            closeAll();
            return;
        }

    }, false);

    // fecha com ESC
    document.addEventListener('keydown', ev => {
        if (ev.key === 'Escape') closeAll();
    });

})();
