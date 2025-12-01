// Validação se os campos de login estão preenchidos
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

// Validação se os campos de cadastro estão preenchidos
function validarCamposCadastro(form) {
  var email = form.email.value;
  var nome = form.nome.value;
  var senha = form.senha.value;
  var cnjp = form.cnpj.value;
  var result = false;

  if (email === "") {
    alert("Preencha o campo email!");
    form.email.focus();
  } else if (senha === "") {
    alert("Preencha o campo senha!");
    form.senha.focus();
  } else if (nome === "") {
    alert("Preencha o campo nome!");
    form.nome.focus();
  } else if (tipo === "ESTABELECIMENTO") {
    if (cnpj === "") {
      alert("Preencha o campo cnpj!");
      form.cnpj.focus();
    }else if (contato === "") {
         alert("Preencha o campo contato!");
        form.nome.focus();
        
    }
  } else {
    form.action = "../bbh/CadastroController";
    form.submit();
    result = true;
  }
  return result;
}

// Alterna inputs de acordo com tipo de perfil
var selectTipo = document.querySelector("#tipo");
var camposEstab = document.querySelector("#camposEstab");
selectTipo.addEventListener("change", function () {
  var tipo = selectTipo.value;
  if (tipo === "ESTABELECIMENTO") {
    camposEstab.style.display = "block";
  } else {
    camposEstab.style.display = "none";
  }
});
