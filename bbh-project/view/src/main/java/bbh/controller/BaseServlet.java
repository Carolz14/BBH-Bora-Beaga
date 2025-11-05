package bbh.controller;

import bbh.common.NaoEncontradoException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


import bbh.domain.Usuario;
import bbh.common.NaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public abstract class BaseServlet extends HttpServlet {
    protected Long getIdUsuario(HttpServletRequest request) throws NaoEncontradoException {
        HttpSession session = request.getSession(false); 
        
        if (session != null) {
            Object usuarioObj = session.getAttribute("usuario");
            if (usuarioObj instanceof Usuario) {
                Usuario usuario = (Usuario) usuarioObj;
                if (usuario.getId() != null) {
                    return usuario.getId();
                }
            }
        }
        throw new NaoEncontradoException("Usuário não autenticado ou ID de usuário não disponível na sessão.");
    }
}
