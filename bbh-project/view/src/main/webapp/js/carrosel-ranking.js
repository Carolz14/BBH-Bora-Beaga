
document.addEventListener("DOMContentLoaded", function() {
    
    const buttons = document.querySelectorAll('.tab-btn');
    const slides = document.querySelectorAll('.slide-content');
    const btnPrev = document.getElementById('btn-prev');
    const btnNext = document.getElementById('btn-next');
    
    // Pegar configuração inicial do JSP ou usar padrão
    const configDiv = document.getElementById('config-inicial');
    let currentType = configDiv ? configDiv.dataset.start : 'visitacoes';

    // Mostra o slide baseado no ID (visitacoes ou medias)
    function switchSlide(type) {
        currentType = type;

        slides.forEach(slide => slide.classList.remove('active'));
        buttons.forEach(btn => btn.classList.remove('active'));

        // Mostra o slide correto
        const targetSlide = document.getElementById(type);
        if (targetSlide) targetSlide.classList.add('active');

        // Ativa o botão correto
        const targetBtn = document.querySelector(`.tab-btn[data-target="${type}"]`);
        if (targetBtn) targetBtn.classList.add('active');
    }

    
    buttons.forEach(btn => {
        btn.addEventListener('click', () => {
            const type = btn.getAttribute('data-target');
            switchSlide(type);
        });
    });

    //Inicializar
    setTimeout(() => {
        switchSlide(currentType);
    }, 50);
});