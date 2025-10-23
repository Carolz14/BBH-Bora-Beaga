package bbh.domain;

import bbh.domain.util.PessoaTipo;


public class Pessoa {
    
    private long CNPJ;
    private Long id;
    private String nome;
    private String naturalidade;
    private String email;
    private String senha;
    private boolean habilitado;
    private PessoaTipo perfil;
    private String endereco;
    private long contato;
    
    public Pessoa(String email, String senha){
        this.email = email;
        this.senha = senha;
        this.habilitado = true;
    }
    
    public Pessoa(String nome, String email, String senha, String naturalidade){
        this.email = email;
        this.nome = nome;
        this.naturalidade = naturalidade;
        this.senha = senha;
    }
    
    public Pessoa(String nome, String email, String senha, String endereco, long contato){
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.senha = senha;
        this.contato = contato;
    }
    
    public PessoaTipo getPessoaTipo(){
        return this.perfil;
    }
    
    public void setPessoaTipo(PessoaTipo perfil){
        this.perfil = perfil;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCNPJ() {
        return CNPJ;
    }
    
    public void setCNPJ(long CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }    

    public String getNaturalidade(){
        return this.naturalidade;
    }
    
    public void setNaturalidade(String naturalidade){
        this.naturalidade = naturalidade;
    }
    
    public String getEndereco(){
        return this.endereco;
    }
    
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    
    public long getContato(){
        return this.contato;
    }
    
    public void setContato(Long contato){
        this.contato = contato;
    }
    
    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    public boolean getHabilitado() {
        return habilitado;
    }
}
