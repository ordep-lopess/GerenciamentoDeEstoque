<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Produto" %>
<%@ page import="dao.ProdutoDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Login" %>
<%
    // pega o objeto Login da sessão
    Login usuario = (Login) session.getAttribute("usuario");
    String nome = (usuario != null) ? usuario.getNome() : "Visitante";
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Lista de Doações</title>
  <link rel="stylesheet" href="CSS/reset.css"/>
  <link rel="stylesheet" href="CSS/index.css"/>
  <link rel="stylesheet" href="CSS/lista.css"/>
  <link rel="stylesheet" href="CSS/voltar.css"/>
  <link rel="stylesheet" href="CSS/header.css"/>
</head>
<body>
        <!-- cabeçalho de usuário -->
    <header class="user-header">
      <span class="greeting">Olá, <%= nome %></span>
      <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Sair</a>
    </header>
<%
  ProdutoDAO dao = new ProdutoDAO();
  // Chama getAllProdutos(), que é o nome do método no seu DAO
  List<Produto> produtos = dao.getAllProdutos();
%>

<h1 class="title" style="margin-bottom:32px">Lista de Doações</h1>
<main class="main">
  <% if (produtos != null && !produtos.isEmpty()) { %>
    <div class="table-head">
      <p class="bar-text table-text">ID</p>
      <p class="bar-text table-text">Doador</p>
      <p class="bar-text table-text">Contato</p>
      <p class="bar-text table-text">Descrição</p>
      <p class="bar-text table-text">Marca</p>
      <p class="bar-text table-text">Quantidade (kg)</p>
      <p class="bar-text table-text">Animal</p>
    </div>

    <table class="table-body">
      <colgroup>
        <col style="width: 10%"/>
        <col style="width: 15%"/>
        <col style="width: 15%"/>
        <col style="width: 20%"/>
        <col style="width: 10%"/>
        <col style="width: 15%"/>
        <col style="width: 15%"/>
      </colgroup>
      <tbody>
        <% for (Produto p : produtos) { %>
          <tr>
            <td class="bar-text table-cell"><%= p.getId() %></td>
            <td class="bar-text table-cell"><%= p.getNome() %></td>
            <td class="bar-text table-cell"><%= p.getContato() %></td>
            <td class="bar-text table-cell"><%= p.getDescricao() %></td>
            <td class="bar-text table-cell"><%= p.getMarca() %></td>
            <td class="bar-text table-cell"><%= p.getQuantidade() %></td>
            <td class="bar-text table-cell"><%= p.getAnimal() %></td>
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
