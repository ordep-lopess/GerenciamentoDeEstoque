<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Login" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Início</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/voltar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/alterar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/header.css"/>
</head>
<body>
<%
    Login usuario = (Login) session.getAttribute("usuario");
    String nome = (usuario != null && usuario.getNome() != null) ? usuario.getNome() : "Visitante";
%>

<header class="user-header">
  <span class="greeting">Olá, <%= nome %></span>
  <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Sair</a>
</header>

<h1 class="title">empatinha ong</h1>

<header class="bar-wrapper">
    <a href="${pageContext.request.contextPath}/produto?action=list" class="bar-item">
        <p class="bar-text">Lista</p>
    </a>

    <a href="${pageContext.request.contextPath}/produto?action=new" class="bar-item">
        <p class="bar-text">Cadastrar</p>
    </a>

    <a href="${pageContext.request.contextPath}/movimentar?action=list" class="bar-item">
        <p class="bar-text">Movimentar</p>
    </a>

    <a href="${pageContext.request.contextPath}/produto?action=consulta" class="bar-item">
        <p class="bar-text">Consulta</p>
    </a>

    <a href="${pageContext.request.contextPath}/produto?action=edit" class="bar-item">
        <p class="bar-text">Alterar</p>
    </a>

    <a href="${pageContext.request.contextPath}/Views/excluirProd.jsp" class="bar-item">
      <p class="bar-text">Excluir</p>
    </a>
</header>

</body>
</html>
