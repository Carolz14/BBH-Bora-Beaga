<%@page import="bbh.domain.Usuario"%>


<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

    if (usuario == null) {
        response.sendRedirect("../login.html");
        return;
    }
%>