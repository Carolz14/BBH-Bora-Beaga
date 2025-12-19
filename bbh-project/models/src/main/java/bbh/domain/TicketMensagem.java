package bbh.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketMensagem {

    private Long id;
    private Long ticketId;
    private Long autorId;
    private String autorTipo; 
    private String mensagem;
    private LocalDateTime criadoEm;

    public TicketMensagem() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getAutorId() {
        return autorId;
    }
    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public String getAutorTipo() {
        return autorTipo;
    }
    public void setAutorTipo(String autorTipo) {
        this.autorTipo = autorTipo;
    }

    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getDataFormatada() {
        if (criadoEm == null) return "";
        return criadoEm.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
