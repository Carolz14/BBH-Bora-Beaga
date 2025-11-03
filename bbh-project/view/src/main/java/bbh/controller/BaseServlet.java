package bbh.controller;

import bbh.common.NaoEncontradoException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class BaseServlet extends HttpServlet {

    protected Long getIdUsuario(HttpServletRequest request) throws NaoEncontradoException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object obj = session.getAttribute("usuarioId");
            if (obj instanceof Long) return (Long) obj;
            if (obj instanceof Integer) return ((Integer) obj).longValue();
            if (obj instanceof String) {
                try { return Long.parseLong((String) obj); } catch (NumberFormatException ignore) {}
            }
        }
        throw new NaoEncontradoException("O id do usuário não foi encontrado");
    }

}
