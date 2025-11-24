function validarCamposPromocao(form) {
    const nome = form.nomePromocao.value.trim();
    const descricao = form.descricaoPromocao.value.trim();
    const dataStr = form.dataPromocao.value;
    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);

    if (nome === "") {
        alert("Preencha o nome da promoção!");
        form.nomePromocao.focus();
        return false;
    }
    if (descricao === "") {
        alert("Preencha a descrição!");
        form.descricaoPromocao.focus();
        return false;
    }
    if (dataStr === "") {
        alert("Escolha uma data de duração!");
        form.dataPromocao.focus();
        return false;
    }

    const dataSelecionada = new Date(dataStr);
    dataSelecionada.setHours(0, 0, 0, 0);

    if (dataSelecionada < hoje) {
        alert("Não é possível selecionar uma data anterior a hoje!");
        form.dataPromocao.focus();
        return false;
    }
    form.submit();
    return true; 
}
