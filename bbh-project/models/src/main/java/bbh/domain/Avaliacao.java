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
    private String categoria;

    public Avaliacao(long idA, long idU, long idE, int nA, String c, Timestamp dA, String cA) {
        idAvaliacao = idA;
        idUsuario = idU;
        idEstabelecimento = idE;
        notaAvaliacao = nA;
        comentario = c;
        dataAvaliacao = dA;
        categoria = cA;
    }

    public Avaliacao(long idU, long idE, int nA, String c, String cA) {
        idUsuario = idU;
        idEstabelecimento = idE;
        notaAvaliacao = nA;
        comentario = c;
        categoria = cA;
    }

    public void setDataAvaliacao(Timestamp d) {
        dataAvaliacao = d;
    }

    public void setComentario(String c) {
        comentario = c;
    }

    public void setIdAvaliação(long idA) {
        idAvaliacao = idA;
    }

    public void setNotaAvaliacao(int n) {
        notaAvaliacao = n;
    }

    public void setCategoria(String cA) {
        categoria = cA;
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

    public String getCategoria() {
        return categoria;
    }

}
