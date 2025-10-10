<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Produto, dao.ProdutoDAO, models.Login, java.time.LocalDate" %>
<%
    // recupera o usuário da sessão
    Login usuario = (Login) session.getAttribute("usuario");
    String nomeUsuario = (usuario != null) ? usuario.getNome() : "Visitante";

    boolean isPost = "POST".equalsIgnoreCase(request.getMethod());
    String mensagem = "";

    if (isPost) {
        try {
            Produto p = new Produto();
            p.setNomeDoador(   request.getParameter("nomeDoador").trim());
            p.setTelefone(     request.getParameter("telefone").trim());
            p.setEmail(        request.getParameter("email").trim());
            p.setDescricao(    request.getParameter("descricao").trim());
            p.setMarca(        request.getParameter("marca").trim());
            p.setQuantidade(   Double.parseDouble(request.getParameter("quantidade")));
            p.setAnimal(       request.getParameter("animal"));
            p.setTipo(         request.getParameter("tipo"));
            p.setPacoteFechado(request.getParameter("pacoteFechado"));
            p.setDataDoacao(   LocalDate.parse(request.getParameter("dataDoacao")));

            boolean ok = new ProdutoDAO().insProduto(p);
            mensagem = ok
              ? "Doação cadastrada com sucesso!"
              : "Falha ao cadastrar doação.";
        }
        catch (NumberFormatException e) {
            mensagem = "Erro: quantidade inválida.";
        }
        catch (Exception e) {
            mensagem = "Erro ao cadastrar: " + e.getMessage();
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Cadastrar Doação</title>
  <link rel="stylesheet" href="CSS/reset.css"/>
  <link rel="stylesheet" href="CSS/index.css"/>
  <link rel="stylesheet" href="CSS/voltar.css"/>
  <link rel="stylesheet" href="CSS/alterar.css"/>
  <link rel="stylesheet" href="CSS/header.css"/>
</head>
<body>
  <!-- cabeçalho de usuário -->
  <header class="user-header">
    <span class="greeting">Olá, <%= nomeUsuario %></span>
    <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Sair</a>
  </header>

  <main class="main">
    <% if (!isPost) { %>
      <h1 class="title" style="margin-bottom:32px">Cadastrar Doação</h1>
      <form method="post" action="" class="form-container">

        <div class="title-wrapper"><h2 class="title-label">Nome do Doador</h2></div>
        <div class="input-wrapper">
          <input
            type="text"
            name="nomeDoador"
            placeholder="Insira o nome do doador"
            class="input"
            required
          />
        </div>

        <div class="title-wrapper margin-top"><h2 class="title-label">Telefone</h2></div>
        <div class="input-wrapper">
          <input
            type="tel"
            name="telefone"
            placeholder="(99) 99999-9999"
            class="input"
            required
          />
        </div>

        <div class="title-wrapper margin-top"><h2 class="title-label">E-mail</h2></div>
        <div class="input-wrapper">
          <input
            type="email"
            name="email"
            placeholder="exemplo@dominio.com"
            class="input"
            required
          />
        </div>

        <div class="title-wrapper margin-top"><h2 class="title-label">Descrição do Alimento</h2></div>
        <div class="input-wrapper">
          <input
            type="text"
            name="descricao"
            placeholder="Ex: Ração Seca 10kg"
            class="input"
            required
          />
        </div>

        <div class="title-wrapper margin-top"><h2 class="title-label">Marca</h2></div>
        <div class="input-wrapper">
          <input
            type="text"
            name="marca"
            placeholder="Insira a marca"
            class="input"
            required
          />
        </div>

        <div class="title-wrapper margin-top"><h2 class="title-label">Quantidade (kg)</h2></div>
        <div class="input-wrapper">
          <input
            type="number"
            name="quantidade"
            step="0.01"
            placeholder="Ex: 2.50"
            class="input"
            required
          />
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

        <div class="title-wrapper margin-top"><h2 class="title-label">Tipo de Alimento</h2></div>
        <div class="input-wrapper">
          <select name="tipo" class="input" required>
            <option value="">Selecione…</option>
            <option value="racao">Ração</option>
            <option value="petisco">Petisco</option>
            <option value="graos">Grãos</option>
          </select>
        </div>

        <div class="title-wrapper margin-top"><h2 class="title-label">Pacote Fechado?</h2></div>
        <div class="input-wrapper">
          <select name="pacoteFechado" class="input" required>
            <option value="">Selecione…</option>
            <option value="sim">Sim</option>
            <option value="nao">Não</option>
          </select>
        </div>

        <div class="title-wrapper margin-top"><h2 class="title-label">Data da Doação</h2></div>
        <div class="input-wrapper">
          <input
            type="date"
            name="dataDoacao"
            class="input"
            required
          />
        </div>

        <div class="button-group margin-top">
          <button
            type="button"
            class="back-btn"
            onclick="history.back()"
          >
            ← Voltar
          </button>
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
```