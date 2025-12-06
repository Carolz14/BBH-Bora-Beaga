package bbh.domain;

public class RankingEstabelecimento {
    private final long idEstabelecimento;
    private final double notaMedia;
    private final int numeroAvaliacoes;
    private final String nomeEstabelecimento;
    private final int numeroDeVisitacoes;

    public RankingEstabelecimento(long idE, double nM, int nA, String nE, int nDV){
        idEstabelecimento = idE;
        notaMedia = nM;
        numeroAvaliacoes = nA;
        nomeEstabelecimento = nE;
        numeroDeVisitacoes = nDV;
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
}
