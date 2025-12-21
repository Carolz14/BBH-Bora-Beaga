package bbh.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Comentario {

    private Long id;
    private Long roteiroId;
    private Long usuarioId;
    private String nomeUsuario;
    private String texto;
    private String imagemUrl;
    private LocalDateTime data;

    public Comentario() {
    }

    public Comentario(Long roteiroId, Long usuarioId, String texto) {
        this.roteiroId = roteiroId;
        this.usuarioId = usuarioId;
        this.texto = texto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoteiroId() {
        return roteiroId;
    }

    public void setRoteiroId(Long roteiroId) {
        this.roteiroId = roteiroId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

     public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

     public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

     public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

        public String getDataFormatada() {
            DateTimeFormatter formatar = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 


        return this.data.format(formatar);
    }
}
