const btn = document.querySelector(".btn");
const modal = document.querySelector(".modal-roteiro");
const btnFechar = document.querySelector(".fechar");
const btnAtualizar = document.querySelector(".btn-atualizar");
const titulo = document.querySelector("#modal-titulo");
const inputId = document.querySelector("#roteiroId");
const inputNome = document.querySelector("#roteiroNome");
const inputDescricao = document.querySelector("#roteiroDescricao");
const inputParadas = document.querySelector("#paradasTexto");
const form = document.querySelector("#formRoteiro")


btnFechar.addEventListener("click", fecharModal);

function abrirModalCriar() {
  inputId.value = "";
  inputNome.value = "";
  inputDescricao.value = "";
  inputParadas.value = "";

  titulo.innerText = "Criar Roteiro";
  btn.innerText = "Criar";
  form.action = contextPath + "/bbh/CriarRoteiroController";

  setTimeout(() => {
    modal.classList.add("ativo");
  }, 10);
}

function abrirModalEditar(id, nome, descricao, paradas) {
  inputId.value = id;
  inputNome.value = nome;
  inputDescricao.value = descricao;
  inputParadas.value = paradas;

  titulo.innerText = "Editar Roteiro";
  btn.innerText = "Editar";
  form.action = contextPath + "/bbh/AtualizarRoteiroController";

  setTimeout(() => {
    modal.classList.add("ativo");
  }, 10);
}



function fecharModal() {
  modal.classList.remove("ativo");
}

modal.addEventListener("click", (e) => {
  if (e.target === modal) {
    fecharModal();
  }
});
