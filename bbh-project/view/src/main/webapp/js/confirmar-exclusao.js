function showConfirmModal(message) {
  return new Promise((resolve) => {
    const modal = document.getElementById('confirmDeleteModal');
    if (!modal) { resolve(window.confirm(message)); return; }

    const msgEl = document.getElementById('confirmDeleteMessage');
    const btnConfirm = document.getElementById('confirmDeleteBtn');
    const btnCancel  = document.getElementById('confirmCancelBtn');
    const btnClose   = document.getElementById('confirmCloseBtn');

    if (msgEl) msgEl.textContent = message || 'Confirmar?';

    modal.classList.add('show');
    modal.setAttribute('aria-hidden', 'false');
    setTimeout(() => btnConfirm && btnConfirm.focus(), 30);

    function cleanup(result) {
      btnConfirm && btnConfirm.removeEventListener('click', onConfirm);
      btnCancel && btnCancel.removeEventListener('click', onCancel);
      btnClose  && btnClose.removeEventListener('click', onCancel);
      modal && modal.removeEventListener('click', onBackdrop);
      document.removeEventListener('keydown', onKeydown);

      modal.classList.remove('show');
      modal.setAttribute('aria-hidden', 'true');

      resolve(result);
    }

    function onConfirm(e) { e && e.preventDefault(); cleanup(true); }
    function onCancel(e)  { e && e.preventDefault(); cleanup(false); }
    function onBackdrop(e) { if (e.target === modal) cleanup(false); }
    function onKeydown(e) { if (e.key === 'Escape') cleanup(false); }

    btnConfirm && btnConfirm.addEventListener('click', onConfirm);
    btnCancel  && btnCancel.addEventListener('click', onCancel);
    btnClose   && btnClose.addEventListener('click', onCancel);
    modal && modal.addEventListener('click', onBackdrop);
    document.addEventListener('keydown', onKeydown);
  });
}

document.addEventListener('click', function (ev) {
  const btn = ev.target.closest('.btn-simple-remove');
  if (!btn) return;

  const form = btn.closest('form');
  if (!form) return;

  if (!form.querySelector('input[name="id_avaliacao"]')) return;

  ev.preventDefault();
  ev.stopPropagation();

  showConfirmModal('Tem certeza que deseja excluir esta avaliação?').then(confirmed => {
    if (!confirmed) return;
    if (typeof form.requestSubmit === 'function') form.requestSubmit();
    else form.submit();
  });
}, true);
