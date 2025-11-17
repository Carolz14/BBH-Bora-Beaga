package bbh.domain;

import java.time.LocalDate;

public class Promocao {
    private String nome;
    private Long desconto;
    private String descricao;
    private LocalDate data;
    private Long id;
    
    public Promocao(String nome, Long desconto, String descricao, LocalDate data){
        this.nome = nome;
        this.desconto = desconto;
        this.descricao = descricao;
        this.data = data;
    }
        
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public Long getDesconto(){
        return desconto;
    }
    
    public void setDesconto(Long desconto){
        this.desconto = desconto;
    }
    
    public String getDescricao(){
        return descricao;
    }
    
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    
    public LocalDate getData(){
        return data;
    }
    
    public void setData(LocalDate data){
        this.data = data;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
}
