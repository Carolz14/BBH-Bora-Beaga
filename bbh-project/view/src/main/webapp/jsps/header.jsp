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
                    <li><a href="${pageContext.request.contextPath}/bbh/feed">Início</a></li>
                    </c:if>

                <c:if test="${ControleAutorizacao.checkPermissao('roteiros', sessionScope.usuario.usuarioTipo)}">
                    <li><a href="../turista/lista-roteiros.jsp">Roteiros</a></li>
                    </c:if>

                <c:if test="${ControleAutorizacao.checkPermissao('eventos', sessionScope.usuario.usuarioTipo)}">
                    <li><a href="${pageContext.request.contextPath}/evento">Eventos</a></li>
                </c:if>
  
                <c:if test="${ControleAutorizacao.checkPermissao('interesse', sessionScope.usuario.usuarioTipo)}">
                    <li><a href="../turista/lista-interesses.jsp">Lista de Interesse</a></li>
                    </c:if>

                <c:if test="${ControleAutorizacao.checkPermissao('locais', sessionScope.usuario.usuarioTipo)}">
                    <li><a href="../admin/painel.jsp">Painel</a></li>
                    </c:if>

                <c:if test="${ControleAutorizacao.checkPermissao('usuario', sessionScope.usuario.usuarioTipo)}">
                    <li><a href="${pageContext.request.contextPath}/bbh/usuarios">Usuários</a></li>
                    </c:if>
                    <c:if test="${ControleAutorizacao.checkPermissao('perfil', sessionScope.usuario.usuarioTipo)}">
                    <li><a href="../admin/perfil.jsp">Perfil</a></li>
                    </c:if>

                <c:if test="${ControleAutorizacao.checkPermissao('painel', sessionScope.usuario.usuarioTipo)}">
                    <li><a href="../estabelecimento/painel.jsp">Painel</a></li>
                    </c:if>
                    <c:if test="${ControleAutorizacao.checkPermissao('promocoes', sessionScope.usuario.usuarioTipo)}">
                    <li><a href="<%= request.getContextPath()%>/bbh/promocoes">Promoções</a></li>
                    </c:if>

                 <c:if test="${ControleAutorizacao.checkPermissao('gerenciarEventos', sessionScope.usuario.usuarioTipo)}">
                        <li><a href="${pageContext.request.contextPath}/evento">Eventos</a></li>
                  </c:if> 

                <c:if test="${ControleAutorizacao.checkPermissao('perfilEstab', sessionScope.usuario.usuarioTipo)}">
                    <li><a href="../estabelecimento/perfil.jsp">Perfil</a></li>
                    </c:if>
            </ul>
        </nav>
            </header>
