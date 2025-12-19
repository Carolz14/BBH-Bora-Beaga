
document.addEventListener("DOMContentLoaded", function() {
    
    // 1. Pegar referências
    const buttons = document.querySelectorAll('.tab-btn');
    const slides = document.querySelectorAll('.slide-content');
    const btnPrev = document.getElementById('btn-prev');
    const btnNext = document.getElementById('btn-next');
    
    // Pegar configuração inicial do JSP ou usar padrão
    const configDiv = document.getElementById('config-inicial');
    let currentType = configDiv ? configDiv.dataset.start : 'visitacoes';

    // 2. Função Principal: Mostra o slide baseado no ID (visitacoes ou medias)
    function switchSlide(type) {
        // Atualiza variável de estado
        currentType = type;

        // Esconde todos e remove classe ativa dos botões
        slides.forEach(slide => slide.classList.remove('active'));
        buttons.forEach(btn => btn.classList.remove('active'));

        // Mostra o slide correto
        const targetSlide = document.getElementById(type);
        if (targetSlide) targetSlide.classList.add('active');

        // Ativa o botão correto
        const targetBtn = document.querySelector(`.tab-btn[data-target="${type}"]`);
        if (targetBtn) targetBtn.classList.add('active');
    }

    // 3. Função para alternar (usada pelas setas)
    function toggleSlide() {
        if (currentType === 'visitacoes') {
            switchSlide('medias');
        } else {
            switchSlide('visitacoes');
        }
    }

    // 4. Event Listeners (Cliques)
    
    // Botões das abas
    buttons.forEach(btn => {
        btn.addEventListener('click', () => {
            const type = btn.getAttribute('data-target');
            switchSlide(type);
        });
    });

    // Setas (ambas fazem a mesma coisa: alternam entre os dois)
    if (btnPrev) btnPrev.addEventListener('click', toggleSlide);
    if (btnNext) btnNext.addEventListener('click', toggleSlide);

    // 5. Inicializar
    // Pequeno delay para garantir que o navegador renderizou o layout antes de animar
    setTimeout(() => {
        switchSlide(currentType);
    }, 50);
});