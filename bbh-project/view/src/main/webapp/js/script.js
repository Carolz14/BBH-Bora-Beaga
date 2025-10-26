function validarCamposLogin(form) {
    var email = form.email.value;
    var senha = form.senha.value;
    var result = false;
    
    if (email === "") {
        alert("Preencha o campo email!");
        form.email.focus();
    } else if (senha === "") {
        alert("Preencha o campo senha!");
        form.senha.focus();
    } else {
        form.action = "bbh/LoginController";
        form.submit();
        result = true;
    }
    return result;
}
