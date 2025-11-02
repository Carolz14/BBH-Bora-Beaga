package bbh.autorizacao;

import bbh.domain.util.UsuarioTipo;
import java.util.HashMap;
import java.util.Map;

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
        permissao.addUsuarioGrupo(UsuarioTipo.ESTABELECIMENTO);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("interesse");
        permissao.addUsuarioGrupo(UsuarioTipo.TURISTA);
        permissao.addUsuarioGrupo(UsuarioTipo.GUIA);
        permissoes.put(permissao.getRecurso(), permissao);

        permissao = new Permissao("perfil");
        permissao.addUsuarioGrupo(UsuarioTipo.TURISTA);
        permissao.addUsuarioGrupo(UsuarioTipo.GUIA);
        permissao.addUsuarioGrupo(UsuarioTipo.ADMINISTRADOR);
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
        
        permissao = new Permissao("turistico");
        permissao.addUsuarioGrupo(UsuarioTipo.ADMINISTRADOR);
        permissoes.put(permissao.getRecurso(), permissao);

    }

    public static boolean checkPermissao(String permissao, UsuarioTipo usuario) {
        return permissoes.get(permissao).check(usuario);
    }


}
