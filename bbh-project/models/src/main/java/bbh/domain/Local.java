package bbh.domain;

public class Local {
    private Long id;
    private String nome;
    private String endereco;
    private String categoria;
    private String descricao;
 
    private boolean ativo = true;

    public Local() {}

    public Local(Long id, String nome, String endereco, String categoria, String descricao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.descricao = descricao;
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

    public String getCategoria() { 
        return categoria; 
    }
    public void setCategoria(String categoria) { 
        this.categoria = categoria; 
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
}
