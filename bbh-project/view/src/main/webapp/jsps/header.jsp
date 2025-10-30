<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <header>
        <h1 id="logo">Bora Beagá</h1>
        <nav>
            <ul> 
                <% if (ControleAutorizacao.checkPermissao("inicio", usuario.getUsuarioTipo())) {%>
                <li><a href="">Início</a></li>
                    <% if (ControleAutorizacao.checkPermissao("roteiros", usuario.getUsuarioTipo())) {%>
                <li><a href="">Roteiros</a></li>
                    <% if (ControleAutorizacao.checkPermissao("eventos", usuario.getUsuarioTipo())) {%>
                <li><a href="">Eventos</a></li>
                    <% if (ControleAutorizacao.checkPermissao("interesse", usuario.getUsuarioTipo())) {%>
                <li><a href="">Lista de interesse</a></li>
                    <% if (ControleAutorizacao.checkPermissao("perfil", usuario.getUsuarioTipo())) {%>
                <li><a href="">Perfil</a></li>
                    <% if (ControleAutorizacao.checkPermissao("painel", usuario.getUsuarioTipo())) {%>
                <li><a href="">Painel</a></li>
                    <% if (ControleAutorizacao.checkPermissao("usuario", usuario.getUsuarioTipo())) {%>
                <li><a href="">Usuários</a></li>
                    <% if (ControleAutorizacao.checkPermissao("promocoes", usuario.getUsuarioTipo())) {%>
                <li><a href="">Promoções</a></li>
                    <% if (ControleAutorizacao.checkPermissao("eventos", usuario.getUsuarioTipo())) {%>
                <li><a href="">Eventos</a></li>
            </ul>
        </nav>
    </header>
