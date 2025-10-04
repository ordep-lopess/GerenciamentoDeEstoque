<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Produto, dao.ProdutoDAO" %>
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
  <title>Cadastrar Doação</title>
  <link rel="stylesheet" href="CSS/reset.css"/>
  <link rel="stylesheet" href="CSS/index.css"/>
  <link rel="stylesheet" href="CSS/cadastro.css"/>
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
    boolean isPost = "POST".equalsIgnoreCase(request.getMethod());
    String mensagem = "";

    if (isPost) {
        try {
            Produto p = new Produto();
            p.setNome(request.getParameter("nome").trim());
            p.setContato(request.getParameter("contato").trim());
            p.setDescricao(request.getParameter("descricao").trim());
            p.setMarca(request.getParameter("marca").trim());
            p.setQuantidade(Double.parseDouble(request.getParameter("quantidade")));
            p.setAnimal(request.getParameter("animal"));

            boolean ok = new ProdutoDAO().insProduto(p);
            mensagem = ok
              ? "Doação cadastrada com sucesso!"
              : "Falha ao cadastrar doação.";

        } catch (NumberFormatException e) {
            mensagem = "Erro: quantidade inválida.";
        } catch (Exception e) {
            mensagem = "Erro ao cadastrar: " + e.getMessage();
        }
    }
%>

<main class="main">
  <% if (!isPost) { %>
    <h1 class="title" style="margin-bottom:32px">Cadastrar Doação</h1>
    <form method="post" action="" class="form-container">

      <div class="title-wrapper"><h2 class="title-label">Nome do Doador</h2></div>
      <div class="input-wrapper">
        <input type="text" name="nome"
               placeholder="Insira o nome do doador"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Contato</h2></div>
      <div class="input-wrapper">
        <input type="text" name="contato"
               placeholder="Telefone ou e-mail"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Descrição</h2></div>
      <div class="input-wrapper">
        <input type="text" name="descricao"
               placeholder="Nome do alimento doado"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Marca</h2></div>
      <div class="input-wrapper">
        <input type="text" name="marca"
               placeholder="Insira a marca"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Quantidade (kg)</h2></div>
      <div class="input-wrapper">
        <input type="number" name="quantidade" step="0.01"
               placeholder="Ex: 2.50"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Animal</h2></div>
      <div class="input-wrapper">
        <select name="animal" class="input" required>
          <option value="">Selecione…</option>
          <option value="cao">Cão</option>
          <option value="gato">Gato</option>
          <option value="aves">Aves</option>
        </select>
      </div>

      <div class="button-group margin-top">
        <button type="button" class="back-btn" onclick="history.back()">← Voltar</button>
        <button type="submit" class="send-btn">Enviar</button>
      </div>
    </form>

  <% } else { %>
    <div class="mensagem">
      <p><%= mensagem %></p>
      <div class="button-group">
        <a href="inicio.jsp" class="back-btn">← Início</a>
        <a href="cadastroProd.jsp" class="send-btn">Nova Doação</a>
      </div>
    </div>
  <% } %>
</main>
</body>
</html>
