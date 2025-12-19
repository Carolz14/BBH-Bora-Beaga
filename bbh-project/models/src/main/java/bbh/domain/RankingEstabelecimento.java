package bbh.domain;

public class RankingEstabelecimento {
    private final long idEstabelecimento;
    private final double notaMedia;
    private final int numeroAvaliacoes;
    private final String nomeEstabelecimento;
    private final int numeroDeVisitacoes;
    private final String imagemUrl;


    public RankingEstabelecimento(long idE, double nM, int nA, String nE, int nDV, String imgUrl){
        idEstabelecimento = idE;
        notaMedia = nM;
        numeroAvaliacoes = nA;
        nomeEstabelecimento = nE;
        numeroDeVisitacoes = nDV;
        imagemUrl = imgUrl;
    }

    public long getIdEstabelecimento() {
        return idEstabelecimento;
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
}
