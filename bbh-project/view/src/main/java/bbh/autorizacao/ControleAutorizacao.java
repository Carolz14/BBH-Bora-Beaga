package bbh.autorizacao;

import java.util.HashMap;
import java.util.Map;

import bbh.domain.util.UsuarioTipo;

public class ControleAutorizacao {

    private final static Map<String, Permissao> permissoes;

    static {
        permissoes = new HashMap();
        ControleAutorizacao.inicializarPermissoes();
    }

    private static void inicializarPermissoes() {
        Permissao permissao = new Permissao("inicio");
        permissao.addUsuarioGrupo(UsuarioTipo.TURISTA);
        permissao.addUsuarioGrupo(UsuarioTipo.GUIA);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("roteiros");
        permissao.addUsuarioGrupo(UsuarioTipo.TURISTA);
        permissao.addUsuarioGrupo(UsuarioTipo.GUIA);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("eventos");
        permissao.addUsuarioGrupo(UsuarioTipo.TURISTA);
        permissao.addUsuarioGrupo(UsuarioTipo.GUIA);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("interesse");
        permissao.addUsuarioGrupo(UsuarioTipo.TURISTA);
        permissao.addUsuarioGrupo(UsuarioTipo.GUIA);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("perfil");
        permissao.addUsuarioGrupo(UsuarioTipo.TURISTA);
        permissao.addUsuarioGrupo(UsuarioTipo.GUIA);
        permissao.addUsuarioGrupo(UsuarioTipo.ADMINISTRADOR);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("perfilEstab");

        permissao.addUsuarioGrupo(UsuarioTipo.ESTABELECIMENTO);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("painel");
        permissao.addUsuarioGrupo(UsuarioTipo.ESTABELECIMENTO);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("usuario");
        permissao.addUsuarioGrupo(UsuarioTipo.ADMINISTRADOR);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("promocoes");
        permissao.addUsuarioGrupo(UsuarioTipo.ESTABELECIMENTO);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("locais");
        permissao.addUsuarioGrupo(UsuarioTipo.ADMINISTRADOR);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("gerenciarEventos");
        permissao.addUsuarioGrupo(UsuarioTipo.ESTABELECIMENTO);
        permissoes.put(permissao.getRecurso(), permissao);
    }

    public static boolean checkPermissao(String permissao, UsuarioTipo usuario) {
        return permissoes.get(permissao).check(usuario);
    }

}
