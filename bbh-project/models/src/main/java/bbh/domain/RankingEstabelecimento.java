package bbh.domain;

public class RankingEstabelecimento {
    private final long localId;
    private final double notaMedia;
    private final int numeroAvaliacoes;
    private final String nomeEstabelecimento;
    private final int numeroDeVisitacoes;
    private final String imagemUrl;
    private final String tipo;


    public RankingEstabelecimento(long idE, double nM, int nA, String nE, int nDV, String imgUrl, String tp){
        localId = idE;
        notaMedia = nM;
        numeroAvaliacoes = nA;
        nomeEstabelecimento = nE;
        numeroDeVisitacoes = nDV;
        imagemUrl = imgUrl;
        tipo = tp;
    }

    public long getIdEstabelecimento() {
        return localId;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    public int getNumeroAvaliacoes() {
        return numeroAvaliacoes;
    }

    public String getNomeEstabelecimento() {
        return nomeEstabelecimento;
    }

    public int getNumeroDeVisitacoes() {
        return numeroDeVisitacoes;
    }
    public String getImagemUrl(){
        return imagemUrl;
    }
    public String getTipo(){
        return tipo;
    }
}
