package bbh.domain;

import java.sql.Timestamp;
import java.util.Objects;

public final class MidiaAvaliacao {

    private final long idMidia;
    private final long idAvaliacao;
    private final String nomeOriginal;
    private final String nomeArmazenado;
    private final String caminho;
    private final String mime;
    private final long tamanhoEmBytes;
    private final Timestamp dataMidia;

    public MidiaAvaliacao(long idM, long idA, String nO, String nA, String c, String M, long tEB, Timestamp dM) {
        idMidia = idM;
        idAvaliacao = idA;
        nomeOriginal = nO;
        nomeArmazenado = nA;
        caminho = c;
        mime = M;
        tamanhoEmBytes = tEB;
        dataMidia = dM;
    }

    public MidiaAvaliacao(long idAvaliacao, String nomeOriginal, String nomeArmazenado, String caminho, String mime, long tamanhoEmBytes) {
        this(0L, idAvaliacao, nomeOriginal, nomeArmazenado, caminho, mime, tamanhoEmBytes, null);
    }
    
    public long getIdMidia() {
        return idMidia;
    }

    public long getIdAvaliacao() {
        return idAvaliacao;
    }

    public String getNomeOriginal() {
        return nomeOriginal;
    }

    public String getNomeArmazenado() {
        return nomeArmazenado;
    }

    public String getCaminho() {
        return caminho;
    }

    public String getMime() {
        return mime;
    }

    public long getTamanhoEmBytes() {
        return tamanhoEmBytes;
    }

    public Timestamp getDataMidia() {
        return dataMidia;
    }

    public MidiaAvaliacao criarComNovoNomeOriginal(String novoNomeOriginal) {
        return new MidiaAvaliacao(this.idMidia, this.idAvaliacao, novoNomeOriginal,
                this.nomeArmazenado, this.caminho, this.mime,
                this.tamanhoEmBytes, this.dataMidia);
    }
    
   public MidiaAvaliacao criarComIdGerado(Long idGerado){
       return new MidiaAvaliacao(idGerado, this.idAvaliacao, this.nomeOriginal, this.nomeArmazenado, this.caminho, this.mime, this.tamanhoEmBytes, null);
   }

    @Override
    public String toString() {
        return "MidiaAvaliacao{id=" + idMidia + ", avaliacao=" + idAvaliacao + ", nomeOriginal=" + nomeOriginal + "}";
    }
}
