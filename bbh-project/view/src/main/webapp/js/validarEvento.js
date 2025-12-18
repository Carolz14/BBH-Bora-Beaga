function validarCamposEvento(form) {
    const nome = form.nomeEvento.value.trim();
    const descricao = form.descricaoEvento.value.trim();
    const dataStr = form.dataEvento.value;
    const horarioStr = form.horarioEvento.value;

    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);

    if (nome === "") {
        alert("Preencha o nome do evento!");
        form.nomeEvento.focus();
        return false;
    }

    if (descricao === "") {
        alert("Preencha a descrição!");
        form.descricaoEvento.focus();
        return false;
    }

    if (dataStr === "") {
        alert("Escolha a data do evento!");
        form.dataEvento.focus();
        return false;
    }

    const dataSelecionada = new Date(dataStr);
    dataSelecionada.setHours(0, 0, 0, 0);

    if (dataSelecionada < hoje) {
        alert("A data do evento não pode ser anterior a hoje!");
        form.dataEvento.focus();
        return false;
    }

    if (horarioStr === "") {
        alert("Escolha o horário do evento!");
        form.horarioEvento.focus();
        return false;
    }

    form.submit();
    return true;
}
