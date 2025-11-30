(function () {
    'use strict';

    function buildUrl(path) {
        const ctx = (window && window.ctx) ? window.ctx : '';
        if (!path) return ctx;
        if (ctx.endsWith('/') && path.startsWith('/')) return ctx.slice(0, -1) + path;
        return ctx + path;
    }

    function bustImage(imgEl) {
        try {
            const src = imgEl.getAttribute('src').split('?')[0];
            imgEl.setAttribute('src', src + '?v=' + Date.now());
        } catch (e) { console.warn(e); }
    }

    function showErr(msg) {
        console.error(msg);
        try { alert(msg); } catch (e) { }
    }

    document.addEventListener('DOMContentLoaded', function () {

        // close modal if click outside or Esc
        const backdropEl = document.getElementById('editModalBackdrop');
        if (backdropEl) {
            backdropEl.addEventListener('click', function (e) {
                if (e.target === backdropEl) closeEditModal();
            });
        }
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape') closeEditModal();
        });

        const formNovaAvaliacao = document.getElementById('avaliacaoComMidiaForm');
        const inputFileAvaliacao = document.getElementById('inputFileAvaliacao');
        const previewImg = document.getElementById('previewImg');
        const previewInfo = document.getElementById('previewInfo');
        const btnClearFile = document.getElementById('btn-clear-file');
        const formStatus = document.getElementById('formStatus');


        if (inputFileAvaliacao && !inputFileAvaliacao.dataset.previewAttached) {
            inputFileAvaliacao.dataset.previewAttached = '1';
            inputFileAvaliacao.addEventListener('change', function () {
                const f = this.files && this.files[0];
                if (!f) {
                    if (previewImg) { previewImg.style.display = 'none'; previewImg.src = ''; }
                    if (previewInfo) previewInfo.textContent = '';
                    if (btnClearFile) btnClearFile.style.display = 'none';
                    return;
                }
                if (!f.type || !f.type.startsWith('image/')) {
                    alert('Selecione uma imagem válida.');
                    this.value = '';
                    if (btnClearFile) btnClearFile.style.display = 'none';
                    return;
                }
                try {
                    const url = URL.createObjectURL(f);
                    if (previewImg) {
                        // revoke previous
                        if (previewImg.dataset.oldUrl) URL.revokeObjectURL(previewImg.dataset.oldUrl);
                        previewImg.dataset.oldUrl = url;
                        previewImg.src = url;
                        previewImg.style.display = 'block';
                    }
                    if (previewInfo) previewInfo.textContent = `${f.name} — ${(f.size / 1024).toFixed(1)} KB`;
                    if (btnClearFile) btnClearFile.style.display = 'inline-block';
                } catch (err) {
                    console.warn('Erro preview:', err);
                }
            });
        }

        if (btnClearFile && !btnClearFile.dataset.clearAttached) {
            btnClearFile.dataset.clearAttached = '1';
            btnClearFile.addEventListener('click', function (e) {
                e.preventDefault();
                if (inputFileAvaliacao) inputFileAvaliacao.value = '';
                if (previewImg) { previewImg.style.display = 'none'; previewImg.src = ''; }
                if (previewInfo) previewInfo.textContent = '';
                btnClearFile.style.display = 'none';
            });
        }

        if (formNovaAvaliacao) {
            // avoid duplicate binding
            if (!formNovaAvaliacao.dataset.submitAttached) {
                formNovaAvaliacao.dataset.submitAttached = '1';

                formNovaAvaliacao.addEventListener('submit', function (evt) {
                    evt.preventDefault();

                    // guard
                    if (formNovaAvaliacao.dataset.sending === '1') {
                        console.warn('Envio já em andamento — ignorando duplicate submit');
                        return;
                    }
                    formNovaAvaliacao.dataset.sending = '1';

                    const submitBtn = formNovaAvaliacao.querySelector('button[type="submit"]');
                    if (submitBtn) {
                        submitBtn.disabled = true;
                        submitBtn.dataset.originalText = submitBtn.textContent;
                        submitBtn.textContent = 'Enviando...';
                    }
                    if (formStatus) formStatus.textContent = 'Enviando...';

                    const fd = new FormData(formNovaAvaliacao);

                    fetch(formNovaAvaliacao.action, {
                        method: 'POST',
                        body: fd,
                        credentials: 'same-origin',
                        headers: { 'X-Requested-With': 'XMLHttpRequest' }
                    }).then(async resp => {
                        if (resp.ok) {
                            location.reload();
                            return;
                        }
                        const txt = await resp.text();
                        throw new Error(txt || resp.statusText);
                    }).catch(err => {
                        console.error('Erro ao enviar avaliação:', err);
                        if (formStatus) formStatus.textContent = 'Erro ao enviar a avaliação.';
                        if (submitBtn) {
                            submitBtn.disabled = false;
                            submitBtn.textContent = submitBtn.dataset.originalText || 'Enviar avaliação + imagem';
                        }
                    }).finally(() => {
                        delete formNovaAvaliacao.dataset.sending;
                    });
                });
            }
        }

        document.addEventListener('click', function (e) {
            const btn = e.target.closest('.btn-file-trigger');
            if (!btn) return;

            e.preventDefault();
            e.stopPropagation();

            if (btn.dataset.opening === '1') return;
            btn.dataset.opening = '1';

            const form = btn.closest('form');
            let input = null;
            if (form) input = form.querySelector('input[type="file"]');
            if (!input && btn.dataset.target) input = document.getElementById(btn.dataset.target);
            if (!input) {
                // fallback: look for the global nova-avaliacao input
                input = document.querySelector('#inputFileAvaliacao');
            }
            if (!input) {
                try { delete btn.dataset.opening; } catch (err) { btn.removeAttribute && btn.removeAttribute('data-opening'); }
                showErr('Campo de arquivo não encontrado.');
                return;
            }

            const onChange = function () {
                try { delete btn.dataset.opening; } catch (err) { btn.removeAttribute && btn.removeAttribute('data-opening'); }
                input.removeEventListener('change', onChange);
            };
            input.addEventListener('change', onChange, { once: true });

            const t = setTimeout(function () {
                try { delete btn.dataset.opening; } catch (err) { btn.removeAttribute && btn.removeAttribute('data-opening'); }
                input.removeEventListener('change', onChange);
            }, 1200);

            try {
                input.click();
            } catch (err) {
                try { delete btn.dataset.opening; } catch (e) { btn.removeAttribute && btn.removeAttribute('data-opening'); }
                console.error('Erro ao abrir file picker', err);
                alert('Não foi possível abrir o seletor de arquivos. Veja o console.');
            }
        }, false);
    });
})();
