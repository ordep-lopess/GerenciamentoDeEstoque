<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Login" %>
<%
    // pega o objeto Login da sessão
    Login usuario = (Login) session.getAttribute("usuario");
    String nome = (usuario != null) ? usuario.getNome() : "Visitante";
%>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="CSS/reset.css">
        <link rel="stylesheet" href="CSS/index.css">
        <link rel="stylesheet" href="CSS/header.css"/>
        <title>Inicio</title>
    </head>
    <body>
           
    <!-- cabeçalho de usuário -->
    <header class="user-header">
      <span class="greeting">Olá, <%= nome %></span>
      <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Sair</a>
    </header>
  
        <h1 class="title">Arcane Limited Company</h1>
        <header class="bar-wrapper">
            <a href="lista.jsp" class="bar-item">
                <p class="bar-text">Lista</p>
            </a>
            <a href="cadastroProd.jsp" class="bar-item">
                <p class="bar-text">Cadastrar</p>
            </a>
            <a href="consulta.jsp" class="bar-item">
                <p class="bar-text">Consulta</p>
            </a>
            <a href="alterar.jsp" class="bar-item">
                <p class="bar-text">Alterar</p>
            </a>
            <a href="excluirProd.jsp" class="bar-item">
                <p class="bar-text">Excluir</p>
            </a>
        </header>
    </body>
</html>