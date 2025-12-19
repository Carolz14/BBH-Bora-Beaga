/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbh.domain;

import java.sql.Timestamp;

public class Avaliacao {
    private final long idUsuario;
    private long idAvaliacao;
    private final long idEstabelecimento;
    private int notaAvaliacao;
    private String comentario;
    private Timestamp dataAvaliacao;

    public Avaliacao(long idA, long idU, long idE, int nA, String c, Timestamp dA) {
        idAvaliacao = idA;
        idUsuario = idU;
        idEstabelecimento = idE;
        notaAvaliacao = nA;
        comentario = c;
        dataAvaliacao = dA;
    }

    public Avaliacao(long idU, long idE, int nA, String c) {
        idUsuario = idU;
        idEstabelecimento = idE;
        notaAvaliacao = nA;
        comentario = c;
    }

    public void setDataAvaliacao(Timestamp d) {
        dataAvaliacao = d;
    }

    public void setComentario(String c) {
        comentario = c;
    }

    public void setIdAvaliacao(long idA) {
        idAvaliacao = idA;
    }

    public void setNotaAvaliacao(int n) {
        notaAvaliacao = n;
    }

    public String getComentario() {
        return comentario;
    }

    public Timestamp getDataAvaliacao() {
        return dataAvaliacao;
    }

    public long getIdAvaliacao() {
        return idAvaliacao;
    }

    public int getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public long getIdEstabelecimento() {
        return idEstabelecimento;
    }
}
