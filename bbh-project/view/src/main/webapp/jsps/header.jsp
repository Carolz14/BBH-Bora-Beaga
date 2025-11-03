<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bbh.domain.Usuario"%>
<%@page import="bbh.controller.LoginController"%>
<%@page import="bbh.autorizacao.ControleAutorizacao"%>

<%@taglib uri="jakarta.tags.core" prefix="c" %>

<%
    LoginController.validarSessao(request, response);
    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>

<!DOCTYPE html>
<html>
    <header>
        <h1 id="logo">Bora Beagá</h1>
        <nav>
            <ul>
               <c:if test="${ControleAutorizacao.checkPermissao('inicio', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/turista/pagina-principal.jsp">Início</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('roteiros', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/turista/lista-roteiros.html">Roteiros</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('eventos', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/turista/eventos.html">Eventos</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('interesse', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/turista/lista-interesses.html">Lista de Interesse</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('perfil', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/admin/perfil.jsp">Perfil</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('painel', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/estabelecimento/painel.html">Painel</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('usuario', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/admin/usuarios.html">Usuários</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('locais', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/admin/painel.html">Painel</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('promocoes', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/estabelecimento/promocoes.html">Promoções</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('gerenciarEventos', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/estabelecimento/eventos.html">Eventos</a></li>
            </c:if>

            <c:if test="${ControleAutorizacao.checkPermissao('perfilEstab', sessionScope.usuario.usuarioTipo)}">
                <li><a href="../jsps/estabelecimento/perfil.html">Perfil</a></li>
            </c:if>
            </ul>
        </nav>
    </header>