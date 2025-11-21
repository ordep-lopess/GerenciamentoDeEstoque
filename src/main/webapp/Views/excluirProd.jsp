<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Login" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Excluir Produto</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/excluir.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/voltar.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/header.css"/>
</head>
<body>
  <%
    Login usuario = (Login) session.getAttribute("usuario");
    String nome = (usuario != null && usuario.getNome() != null) ? usuario.getNome() : "Visitante";
    String result = request.getParameter("result"); // esperado: deleted, deletefail, invalidid, etc.
    String mensagem = null;
    boolean showForm = true;

    if ("deleted".equals(result)) {
      mensagem = "Produto excluído com sucesso!";
      showForm = false;
    } else if ("deletefail".equals(result)) {
      mensagem = "Falha ao excluir o produto.";
      showForm = false;
    } else if ("invalidid".equals(result)) {
      mensagem = "ID inválido.";
      showForm = false;
    } else if ("notfound".equals(result)) {
      mensagem = "Produto não encontrado.";
      showForm = false;
    }
  %>

  <!-- cabeçalho de usuário -->
  <header class="user-header">
    <span class="greeting">Olá, <%= nome %></span>
    <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Sair</a>
  </header>

  <main class="main">
    <% if (showForm) { %>
      <h1 class="title" style="margin-bottom:32px">Excluir Produto por ID</h1>
      <!-- envia para o servlet /produto via POST; o servlet realiza a exclusão -->
      <form method="post" action="${pageContext.request.contextPath}/produto" class="form-container">
        <input type="hidden" name="action" value="delete"/>
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
        <p><%= mensagem != null ? mensagem : "Operação concluída." %></p>
        <div class="button-group margin-top">
          <a href="${pageContext.request.contextPath}/excluirProd.jsp" class="back-btn">← Nova exclusão</a>
          <a href="${pageContext.request.contextPath}/inicio.jsp" class="back-btn">← Início</a>
        </div>
      </div>
    <% } %>
  </main>
</body>
</html>
