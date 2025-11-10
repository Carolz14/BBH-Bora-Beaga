package bbh.domain;

public class Tag {
    private final String nome;
    private final String slug;
    private final Long id;
    private int contador;
    public Tag(String no, String sl, Long i){
        nome = no;
        slug = sl;
        id = i;
    }
    public String getNome() {
        return nome;
    }

    public String getSlug() {
        return slug;
    }

    public Long getId() {
        return id;
    }

    public int getContador() {
        return contador;
    }
    public void setContador(int num){
        contador = contador + num;
    }

}
