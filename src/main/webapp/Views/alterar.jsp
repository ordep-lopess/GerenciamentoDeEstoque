<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Produto" %>
<%@ page import="dao.ProdutoDAO" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Alterar Doação</title>
  <link rel="stylesheet" href="CSS/reset.css"/>
  <link rel="stylesheet" href="CSS/index.css"/>
  <link rel="stylesheet" href="CSS/voltar.css"/>
  <link rel="stylesheet" href="CSS/alterar.css"/>
</head>
<body>
<%
    boolean isPost   = "POST".equalsIgnoreCase(request.getMethod());
    String  idParam  = request.getParameter("id");
    String  mensagem = null;
    Produto produto  = null;

    if (isPost) {
        // --- Atualização via POST ---
        try {
            int    id         = Integer.parseInt(idParam);
            String nome       = request.getParameter("nome").trim();
            String contato    = request.getParameter("contato").trim();
            String descricao  = request.getParameter("descricao").trim();
            String marca      = request.getParameter("marca").trim();
            double quantidade = Double.parseDouble(request.getParameter("quantidade"));
            String animal     = request.getParameter("animal");

            Produto toUpdate = new Produto();
            toUpdate.setId(id);
            toUpdate.setNome(nome);
            toUpdate.setContato(contato);
            toUpdate.setDescricao(descricao);
            toUpdate.setMarca(marca);
            toUpdate.setQuantidade(quantidade);
            toUpdate.setAnimal(animal);

            boolean ok = new ProdutoDAO().updateProduto(toUpdate);
            mensagem = ok
              ? "Doação alterada com sucesso!"
              : "Falha ao alterar a doação.";
        }
        catch (NumberFormatException e) {
            mensagem = "Erro: valor de quantidade inválido.";
        }
        catch (Exception e) {
            mensagem = "Erro ao atualizar: " + e.getMessage();
        }
    }
    else {
        // --- Carrega formulário via GET ---
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                produto = new ProdutoDAO().getProdutoById(id);
                if (produto == null) {
                    mensagem = "Doação de ID " + id + " não encontrada.";
                }
            }
            catch (NumberFormatException e) {
                mensagem = "ID inválido.";
            }
        }
    }
%>

<main class="main">

  <%-- Se veio mensagem após POST ou GET sem encontrar --%>
  <% if (mensagem != null && (isPost || produto == null)) { %>
    <div class="mensagem">
      <p><%= mensagem %></p>
      <div class="button-group">
        <a href="alterar.jsp" class="back-btn">← Nova busca</a>
        <a href="inicio.jsp" class="back-btn">← Início</a>
      </div>
    </div>

  <% } 
     else if (!isPost && produto == null) { // GET sem parâmetro ou ID em branco %>
    <h1 class="title" style="margin-bottom:32px">Buscar Doação por ID</h1>
    <form method="get" action="alterar.jsp" class="form-container">
      <div class="input-wrapper">
        <input type="number" name="id" placeholder="Insira o ID da doação"
               min="1" class="input" required/>
      </div>
      <div class="button-group">
        <button type="button" class="back-btn" onclick="history.back()">← Voltar</button>
        <button type="submit" class="send-btn">Buscar</button>
      </div>
    </form>

  <% } 
     else { // GET com produto carregado ou POST sem erro --%>
    <h1 class="title" style="margin-bottom:32px">Alterar Doação</h1>
    <form method="post" action="alterar.jsp" class="form-container">
      <input type="hidden" name="id" value="<%= produto.getId() %>"/>

      <div class="title-wrapper"><h2 class="title-label">Nome do Doador</h2></div>
      <div class="input-wrapper">
        <input type="text" name="nome"
               value="<%= produto.getNome() %>"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Contato</h2></div>
      <div class="input-wrapper">
        <input type="text" name="contato"
               value="<%= produto.getContato() %>"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Descrição</h2></div>
      <div class="input-wrapper">
        <input type="text" name="descricao"
               value="<%= produto.getDescricao() %>"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Marca</h2></div>
      <div class="input-wrapper">
        <input type="text" name="marca"
               value="<%= produto.getMarca() %>"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Quantidade (kg)</h2></div>
      <div class="input-wrapper">
        <input type="number" name="quantidade"
               value="<%= produto.getQuantidade() %>"
               min="0" step="0.01"
               class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Animal</h2></div>
      <div class="input-wrapper">
        <select name="animal" class="input" required>
          <option value="">Selecione…</option>
          <option value="cao"   <%= "cao".equals(produto.getAnimal())   ? "selected" : "" %>>Cão</option>
          <option value="gato"  <%= "gato".equals(produto.getAnimal())  ? "selected" : "" %>>Gato</option>
          <option value="aves"  <%= "aves".equals(produto.getAnimal())  ? "selected" : "" %>>Aves</option>
        </select>
      </div>

      <div class="button-group">
        <button type="button" class="back-btn" onclick="history.back()">← Voltar</button>
        <button type="submit" class="send-btn">Salvar</button>
        <a href="inicio.jsp" class="back-btn">← Início</a>
      </div>
    </form>
  <% } %>

</main>
</body>
</html>
