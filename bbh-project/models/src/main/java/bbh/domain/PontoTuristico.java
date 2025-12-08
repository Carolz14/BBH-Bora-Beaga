package bbh.domain;

public class PontoTuristico {

    private Long id;
    private String nome;
    private String endereco;
    private String descricao;
    private String imagemUrl;
    private boolean ativo = true;

    public PontoTuristico() {
    }

    public PontoTuristico(String nome, String endereco, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
