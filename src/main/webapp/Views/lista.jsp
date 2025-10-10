<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Produto, dao.ProdutoDAO, models.Login, java.util.List" %>
<%
    // Recupera usuário da sessão
    Login usuario = (Login) session.getAttribute("usuario");
    String nomeUsuario = (usuario != null) ? usuario.getNome() : "Visitante";

    // Busca lista de produtos
    ProdutoDAO dao = new ProdutoDAO();
    List<Produto> produtos = dao.getAllProdutos();
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <title>Lista de Doações</title>
  <link rel="stylesheet" href="CSS/reset.css"/>
  <link rel="stylesheet" href="CSS/index.css"/>
  <link rel="stylesheet" href="CSS/lista.css"/>
  <link rel="stylesheet" href="CSS/voltar.css"/>
  <link rel="stylesheet" href="CSS/header.css"/>
</head>
<body>
  <header class="user-header">
    <span class="greeting">Olá, <%= nomeUsuario %></span>
    <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Sair</a>
  </header>

  <h1 class="title" style="margin-bottom:32px">Lista de Doações</h1>

  <main class="main">
    <% if (produtos != null && !produtos.isEmpty()) { %>
      <table class="table">
        <colgroup>
          <col style="width:  5%"/>  <!-- ID -->
          <col style="width: 15%"/>  <!-- Nome -->
          <col style="width: 12%"/>  <!-- Telefone -->
          <col style="width: 12%"/>  <!-- E-mail -->
          <col style="width: 18%"/>  <!-- Descrição -->
          <col style="width: 10%"/>  <!-- Marca -->
          <col style="width:  8%"/>  <!-- Quantidade(kg) -->
          <col style="width:  8%"/>  <!-- Animal -->
          <col style="width:  8%"/>  <!-- Tipo -->
          <col style="width:  8%"/>  <!-- Pacote Fechado -->
          <col style="width:  9%"/>  <!-- Data da Doação -->
        </colgroup>
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Nome</th>
            <th scope="col">Telefone</th>
            <th scope="col">E-mail</th>
            <th scope="col">Descrição</th>
            <th scope="col">Marca</th>
            <th scope="col">Quantidade (kg)</th>
            <th scope="col">Animal</th>
            <th scope="col">Tipo</th>
            <th scope="col">Pacote Fechado?</th>
            <th scope="col">Data da Doação</th>
          </tr>
        </thead>
        <tbody>
          <% for (Produto p : produtos) { %>
            <tr>
              <th scope="row"><%= p.getId() %></th>
              <td><%= p.getNomeDoador() %></td>
              <td><%= p.getTelefone() %></td>
              <td><%= p.getEmail() %></td>
              <td><%= p.getDescricao() %></td>
              <td><%= p.getMarca() %></td>
              <td><%= p.getQuantidade() %></td>
              <td><%= p.getAnimal() %></td>
              <td><%= p.getTipo() %></td>
              <td><%= p.getPacoteFechado() %></td>
              <td><%= p.getDataDoacao() %></td>
            </tr>
          <% } %>
        </tbody>
      </table>

      <a href="inicio.jsp" class="back-btn">← Voltar</a>
    <% } else { %>
      <p>Nenhuma doação encontrada!</p>
      <a href="inicio.jsp" class="back-btn">← Voltar</a>
    <% } %>
  </main>
</body>
</html>
