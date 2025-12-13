package bbh.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private Long id;
    private Long usuarioId;
    private String usuarioEmail;
    private String assunto;
    private String mensagem;
    private String status;
    private LocalDateTime criadoEm;
    private Boolean ativo;

    private List<TicketMensagem> mensagens = new ArrayList<>();

    public Ticket() {
        this.ativo = Boolean.TRUE;
        this.status = "ABERTO";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<TicketMensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<TicketMensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public String getDataAbertura() {
        if (criadoEm == null) return "";
        return criadoEm.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}