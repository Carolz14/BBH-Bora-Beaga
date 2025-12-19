
(function () {
    const dropdown = document.querySelector('.filter-dropdown');
    if (!dropdown) return;

    const toggle = dropdown.querySelector('.filter-toggle');
    const panel = dropdown.querySelector('.filter-panel');
    const diasInput = dropdown.querySelector('input[name="dias"]');
    const form = dropdown.querySelector('form');

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
            // uso timeout pra respeitar a animação de max-height > depois esconde realmente
            setTimeout(() => {
                if (!panel.classList.contains('open')) panel.setAttribute('hidden', '');
            }, 300);
            dropdown.classList.remove('open');
            toggle.setAttribute('aria-expanded', 'false');
            dropdown.dataset.open = 'false';
        }
    }

    toggle.addEventListener('click', function (e) {
        e.preventDefault();
        const isOpen = dropdown.dataset.open === 'true';
        setOpen(!isOpen, !isOpen);
    });

    document.addEventListener('click', function (e) {
        if (!dropdown.contains(e.target) && dropdown.dataset.open === 'true') {
            setOpen(false);
        }
    });

    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape' && dropdown.dataset.open === 'true') {
            setOpen(false);
            toggle.focus();
        }
    });

    if (form) {
        form.addEventListener('submit', function () {
            setOpen(false);
        });
    }


})();