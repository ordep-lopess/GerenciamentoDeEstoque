<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Produto" %>
<%@ page import="dao.ProdutoDAO" %>
<%@ page import="models.Login" %>
<%
    // Recupera o usuário da sessão
    Login usuario = (Login) session.getAttribute("usuario");
    String nome = (usuario != null) ? usuario.getNome() : "Visitante";

    // Parâmetro 'id' pode vir via GET ou POST
    String ident = request.getParameter("id");
    boolean showForm = (ident == null || ident.isEmpty());
    String mensagem = null;
    Produto resultado = null;

    if (!showForm) {
        try {
            int id = Integer.parseInt(ident);
            resultado = new ProdutoDAO().getProdutoById(id);
            if (resultado == null) {
                mensagem = "Doação não encontrada para o ID " + id;
            }
        } catch (NumberFormatException e) {
            mensagem = "ID inválido!";
        } catch (Exception e) {
            mensagem = "Erro ao consultar: " + e.getMessage();
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Consultar Doação</title>
  <link rel="stylesheet" href="CSS/reset.css"/>
  <link rel="stylesheet" href="CSS/index.css"/>
  <link rel="stylesheet" href="CSS/consulta.css"/>
  <link rel="stylesheet" href="CSS/voltar.css"/>
  <link rel="stylesheet" href="CSS/header.css"/>
</head>
<body>
  <!-- cabeçalho de usuário -->
  <header class="user-header">
    <span class="greeting">Olá, <%= nome %></span>
    <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Sair</a>
  </header>

  <main class="main">
    <% if (showForm) { %>
      <h1 class="title" style="margin-bottom:32px">Consultar Doação por ID</h1>
      <form method="post" action="consulta.jsp" class="form-container">
        <div class="input-wrapper">
          <input
            type="number"
            name="id"
            placeholder="Insira o ID da doação"
            min="1"
            class="input"
            required
          />
        </div>
        <div class="button-group margin-top">
          <button type="button" class="back-btn" onclick="history.back()">← Voltar</button>
          <button type="submit" class="send-btn">Consultar</button>
        </div>
      </form>

    <% } else { %>
      <h1 class="title" style="margin-bottom:32px">Resultado da Consulta</h1>

      <% if (resultado != null) { %>
        <div class="table">
          <p class="cell"><strong>ID</strong></p>
          <p class="cell"><%= resultado.getId() %></p>

          <p class="cell"><strong>Doador</strong></p>
          <p class="cell"><%= resultado.getNomeDoador() %></p>

          <p class="cell"><strong>Telefone</strong></p>
          <p class="cell"><%= resultado.getTelefone() %></p>

          <p class="cell"><strong>Email</strong></p>
          <p class="cell"><%= resultado.getEmail() %></p>

          <p class="cell"><strong>Descrição</strong></p>
          <p class="cell"><%= resultado.getDescricao() %></p>

          <p class="cell"><strong>Marca</strong></p>
          <p class="cell"><%= resultado.getMarca() %></p>

          <p class="cell"><strong>Quantidade (kg)</strong></p>
          <p class="cell"><%= resultado.getQuantidade() %></p>

          <p class="cell"><strong>Animal</strong></p>
          <p class="cell"><%= resultado.getAnimal() %></p>

          <p class="cell"><strong>Tipo</strong></p>
          <p class="cell"><%= resultado.getTipo() %></p>

          <p class="cell"><strong>Pacote Fechado</strong></p>
          <p class="cell"><%= resultado.getPacoteFechado() %></p>

          <p class="cell"><strong>Data da Doação</strong></p>
          <p class="cell"><%= resultado.getDataDoacao() %></p>
        </div>
      <% } else { %>
        <p class="erro"><%= mensagem %></p>
      <% } %>

      <div class="button-group margin-top">
        <a href="inicio.jsp" class="back-btn">← Início</a>
        <a href="consulta.jsp" class="send-btn">Nova Consulta</a>
      </div>
    <% } %>
  </main>
</body>
</html>
```