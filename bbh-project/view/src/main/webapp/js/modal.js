const btnCriar = document.querySelector(".btn-criar");
const modal = document.querySelector(".modal-roteiro");
const btnFechar = document.querySelector(".fechar");



btnFechar.addEventListener("click", fecharModal);



function abrirModal() {
    setTimeout(() => {
        modal.classList.add('ativo');
    }, 10);
}

function fecharModal() {
    modal.classList.remove('ativo');
}




modal.addEventListener('click', e => {
    // Fecha o modal apenas se o clique for no fundo escuro
    if (e.target === modal) {
        fecharModal();
    }
});

