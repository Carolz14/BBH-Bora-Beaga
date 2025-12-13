function abrirConversa(id, assunto) {
    document.getElementById("modalTicketId").value = id;
    document.getElementById("modalAssunto").innerText = assunto;

    fetch(`/bbh/suporte?acao=mensagens&id=${id}`)
        .then(r => r.json())
        .then(lista => {
            const chat = document.getElementById("chatMensagens");
            chat.innerHTML = "";

            lista.forEach(m => {
                const d = document.createElement("div");
                d.className = "msg " + m.autorTipo;
                d.innerHTML = `<p>${m.mensagem}</p><span>${m.dataFormatada}</span>`;
                chat.appendChild(d);
            });
        });

    abrirModal();
}
