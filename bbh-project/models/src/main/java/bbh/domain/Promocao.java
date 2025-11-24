package bbh.domain;

import java.time.LocalDate;

public class Promocao {

    private String nome;
    private String descricao;
    private LocalDate data;
    private Long id;
    private Long idEstabelecimento;

    public Promocao(String nome, String descricao, LocalDate data, Long idEstabelecimento) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.idEstabelecimento = idEstabelecimento;
    }

    public Promocao() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEstabelecimento() {
        return idEstabelecimento;
    }

    public void setIdEstabelecimento(Long idEstabelecimento) {
        this.idEstabelecimento = idEstabelecimento;
    }
}
