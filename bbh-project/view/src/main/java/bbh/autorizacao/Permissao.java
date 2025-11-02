package bbh.autorizacao;

import bbh.domain.util.UsuarioTipo;
import java.util.HashSet;
import java.util.Set;

class Permissao {

    private final String permissao;
    private final Set<UsuarioTipo> usuario;

    public Permissao(String permissao) {
        this.permissao = permissao;
        this.usuario = new HashSet();
    }

    public void addUsuarioGrupo(UsuarioTipo usuario) {
        this.usuario.add(usuario);
    }

    public String getRecurso() {
        return permissao;
    }

    public boolean check(UsuarioTipo usuario) {
        return this.usuario.contains(usuario);
    }
}