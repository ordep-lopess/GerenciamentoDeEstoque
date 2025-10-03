<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Produto, modelDAO.ProdutoDAO" %>
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
</head>
<body>
  <%
    String ident     = request.getParameter("id");
    boolean showForm = (ident == null);
    String mensagem  = null;

    if (!showForm) {
      try {
        int id = Integer.parseInt(ident);
        Produto prod = new Produto();
        prod.setId(id);
        boolean ok = new ProdutoDAO().exProdId(prod);
        mensagem = ok
          ? "Produto excluído com sucesso!"
          : "Não foi possível excluir o produto!";
      } catch (NumberFormatException e) {
        mensagem = "Erro: ID inválido!";
      } catch (Exception e) {
        mensagem = "Erro ao excluir o produto: " + e.getMessage();
      }
    }
  %>

  <main class="main">
    <% if (showForm) { %>
      <div class="title-wrapper">
        <h1 class="title" style="margin-bottom: 32px">Excluir Produto Por ID</h1>
      </div>
      <form method="get" action="excluirProd.jsp" class="form-container">
        <div class="input-wrapper">
          <input
            type="number"
            id="id"
            name="id"
            placeholder="Insira o número do ID"
            min="0"
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
          <a href="excluirProd.jsp" class="back-btn">Nova Exclusão</a>
          <a href="inicio.jsp" class="back-btn">Inicio</a>
        </div>
      </div>
    <% } %>
  </main>
</body>
</html>
```