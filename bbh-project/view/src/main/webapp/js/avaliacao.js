
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
        // abrir modal ao clicar em "Editar" — encontramos a .avaliacao mais próxima e lemos data-attributes
        document.querySelectorAll('.btn-edit').forEach(function(btn) {
            btn.addEventListener('click', function () {
                var row = btn.closest('.avaliacao');
                if (!row) return;
                var id = row.getAttribute('data-id') || '';
                var nota = row.getAttribute('data-nota') || '';
                var comentario = row.getAttribute('data-comentario') || '';
                // comentário já foi escapado pelo servidor via c:out; aqui o browser já decodificou entidades
                openEditModal(id, nota, comentario);
            });
        });

        var backdropEl = document.getElementById('editModalBackdrop');
        backdropEl.addEventListener('click', function(e) {
            if (e.target === backdropEl) closeEditModal();
        });

        // ESC para fechar
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape') closeEditModal();
        });
    });