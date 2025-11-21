<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Login" %>
<%@ page import="models.Produto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Alterar Doação</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/voltar.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/alterar.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/header.css"/>
</head>
<body>
<%
    Login usuario = (Login) session.getAttribute("usuario");
    String nomeUsuario = (usuario != null) ? usuario.getNome() : "Visitante";
    Produto produto = (Produto) request.getAttribute("produto");
%>

  <!-- cabeçalho de usuário -->
  <header class="user-header">
    <span class="greeting">Olá, <%= nomeUsuario %></span>
    <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Sair</a>
  </header>

  <main class="main">
    <%-- mensagem de operação, se existir --%>
    <c:if test="${not empty mensagem}">
      <div class="mensagem"><p>${mensagem}</p></div>
    </c:if>

    <%-- lógica: se produto não foi carregado mostra busca por id (igual ao backup) --%>
    <c:choose>
      <c:when test="${empty produto}">
        <h1 class="title" style="margin-bottom:32px">Buscar Doação por ID</h1>
        <form method="get" action="${pageContext.request.contextPath}/produto" class="form-container">
          <input type="hidden" name="action" value="edit"/>
          <div class="input-wrapper">
            <input type="number" name="id" placeholder="Insira o ID da doação" min="1" class="input" required/>
          </div>
          <div class="button-group">
            <button type="button" class="back-btn" onclick="history.back()">← Voltar</button>
            <button type="submit" class="send-btn">Buscar</button>
          </div>
        </form>
      </c:when>

      <c:otherwise>
        <h1 class="title" style="margin-bottom:32px">Alterar Doação</h1>

        <!-- envia para o servlet /produto com action=update -->
        <form method="post" action="${pageContext.request.contextPath}/produto" class="form-container">
          <input type="hidden" name="action" value="update"/>
          <input type="hidden" name="id" value="${produto.id}"/>

          <div class="title-wrapper"><h2 class="title-label">Nome do Doador</h2></div>
          <div class="input-wrapper">
            <input type="text" name="nomeDoador" class="input" required
                   value="${produto != null ? produto.nomeDoador : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Telefone</h2></div>
          <div class="input-wrapper">
            <input type="tel" name="telefone" class="input" required
                   value="${produto != null ? produto.telefone : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Email</h2></div>
          <div class="input-wrapper">
            <input type="email" name="email" class="input" required
                   value="${produto != null ? produto.email : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Descrição</h2></div>
          <div class="input-wrapper">
            <input type="text" name="descricao" class="input" required
                   value="${produto != null ? produto.descricao : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Marca</h2></div>
          <div class="input-wrapper">
            <input type="text" name="marca" class="input" required
                   value="${produto != null ? produto.marca : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Animal</h2></div>
          <div class="input-wrapper">
            <select name="animal" class="input" required>
              <option value="">Selecione…</option>
              <option value="cao" ${produto != null && produto.animal == 'cao' ? 'selected' : ''}>Cão</option>
              <option value="gato" ${produto != null && produto.animal == 'gato' ? 'selected' : ''}>Gato</option>
              <option value="aves" ${produto != null && produto.animal == 'aves' ? 'selected' : ''}>Aves</option>
            </select>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Tipo</h2></div>
          <div class="input-wrapper">
            <select name="tipo" class="input" required>
              <option value="">Selecione…</option>
              <option value="racao" ${produto != null && produto.tipo == 'racao' ? 'selected' : ''}>Ração</option>
              <option value="petisco" ${produto != null && produto.tipo == 'petisco' ? 'selected' : ''}>Petisco</option>
              <option value="graos" ${produto != null && produto.tipo == 'graos' ? 'selected' : ''}>Grãos</option>
            </select>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Pacote Fechado</h2></div>
          <div class="input-wrapper">
            <select name="pacoteFechado" class="input" required>
              <option value="">Selecione…</option>
              <option value="sim" ${produto != null && produto.pacoteFechado == 'sim' ? 'selected' : ''}>Sim</option>
              <option value="nao" ${produto != null && produto.pacoteFechado == 'nao' ? 'selected' : ''}>Não</option>
            </select>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Data da Doação</h2></div>
          <div class="input-wrapper">
            <input type="date" name="dataDoacao" class="input" required
                   value="${produto != null && produto.dataDoacao != null ? produto.dataDoacao : ''}"/>
          </div>

          <div class="button-group margin-top">
            <button type="button" class="back-btn" onclick="history.back()">← Voltar</button>
            <button type="submit" class="send-btn">Salvar</button>
            <a href="${pageContext.request.contextPath}/produto?action=list" class="back-btn">← Início</a>
          </div>
        </form>
      </c:otherwise>
    </c:choose>

  </main>
</body>
</html>
