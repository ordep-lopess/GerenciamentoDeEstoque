<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Produto" %>
<%@ page import="dao.ProdutoDAO" %>
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
  <title>Excluir Produto</title>
  <link rel="stylesheet" href="CSS/reset.css"/>
  <link rel="stylesheet" href="CSS/index.css"/>
  <link rel="stylesheet" href="CSS/excluir.css"/>
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
  String ident     = request.getParameter("id");
  boolean showForm = (ident == null || ident.isEmpty());
  String mensagem  = null;

  if (!showForm) {
    try {
      int id = Integer.parseInt(ident);
      // chama o método correto
      boolean ok = new ProdutoDAO().deleteProduto(id);
      mensagem = ok
        ? "Produto excluído com sucesso!"
        : "Não foi possível excluir o produto com ID " + id + ".";
    } catch (NumberFormatException e) {
      mensagem = "Erro: ID inválido!";
    } catch (Exception e) {
      mensagem = "Erro ao excluir o produto: " + e.getMessage();
    }
  }
%>

<main class="main">
  <% if (showForm) { %>
    <h1 class="title" style="margin-bottom:32px">Excluir Produto por ID</h1>
    <form method="get" action="excluirProd.jsp" class="form-container">
      <div class="input-wrapper">
        <input
          type="number"
          name="id"
          placeholder="Insira o número do ID"
          min="1"
          class="input"
          required
        />
      </div>
      <div class="button-group margin-top">
        <button type="submit" class="remove-btn">Excluir</button>
        <button type="button" class="back-btn" onclick="history.back()">Cancelar</button>
      </div>
    </form>
  <% } else { %>
    <div class="mensagem">
      <p><%= mensagem %></p>
      <div class="button-group margin-top">
        <a href="excluirProd.jsp" class="back-btn">← Nova exclusão</a>
        <a href="inicio.jsp" class="back-btn">← Início</a>
      </div>
    </div>
  <% } %>
</main>
</body>
</html>
