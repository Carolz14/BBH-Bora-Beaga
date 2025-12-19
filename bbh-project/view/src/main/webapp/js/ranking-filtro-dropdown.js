
(function () {
    // elementos
    const dropdown = document.querySelector('.filter-dropdown');
    if (!dropdown) return;

    const toggle = dropdown.querySelector('.filter-toggle');
    const panel = dropdown.querySelector('.filter-panel');
    const diasInput = dropdown.querySelector('input[name="dias"]');
    const form = dropdown.querySelector('form');

    // função para abrir/fechar o painel
    function setOpen(open, focusToggle = false) {
        if (open) {
            panel.classList.add('open');
            panel.removeAttribute('hidden');
            dropdown.classList.add('open');
            toggle.setAttribute('aria-expanded', 'true');
            dropdown.dataset.open = 'true';
            if (focusToggle) panel.querySelector('input, button, select')?.focus();
        } else {
            panel.classList.remove('open');
            // uso timeout pra respeitar a animação de max-height -> depois esconde realmente
            setTimeout(() => {
                if (!panel.classList.contains('open')) panel.setAttribute('hidden', '');
            }, 300);
            dropdown.classList.remove('open');
            toggle.setAttribute('aria-expanded', 'false');
            dropdown.dataset.open = 'false';
        }
    }

    // toggle ao clicar no botão
    toggle.addEventListener('click', function (e) {
        e.preventDefault();
        const isOpen = dropdown.dataset.open === 'true';
        setOpen(!isOpen, !isOpen);
    });

    // fecha ao clicar fora
    document.addEventListener('click', function (e) {
        if (!dropdown.contains(e.target) && dropdown.dataset.open === 'true') {
            setOpen(false);
        }
    });

    // fecha com ESC
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape' && dropdown.dataset.open === 'true') {
            setOpen(false);
            toggle.focus();
        }
    });

    // fecha o painel quando o formulário for submetido (para mostrar os resultados)
    if (form) {
        form.addEventListener('submit', function () {
            setOpen(false);
        });
    }


})();