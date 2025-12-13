function validarSuporte(form) {
    const assunto = form.assunto.value.trim();
    const mensagem = form.mensagem.value.trim();

    if (assunto.length === 0) {
        alert('Preencha o assunto do ticket.');
        form.assunto.focus();
        return false;
    }
    if (mensagem.length === 0) {
        alert('Preencha a mensagem do ticket.');
        form.mensagem.focus();
        return false;
    }
    return true;
}
