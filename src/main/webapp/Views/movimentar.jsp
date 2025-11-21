<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Login" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <title>Movimentações</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/lista.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/voltar.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/movimentacao.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/header.css"/>
</head>
<body>
<%
  Login usuario = (Login) session.getAttribute("usuario");
  String nome = (usuario != null && usuario.getNome() != null) ? usuario.getNome() : "Visitante";
%>

<header class="user-header">
  <span class="greeting">Olá, <%= nome %></span>
  <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Sair</a>
</header>

<h1 class="title" style="margin-bottom:24px">Movimentações</h1>

<main class="main">

  <c:choose>
    <c:when test="${not empty movimentacoes}">
      <table class="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Produto</th>
            <th>Tipo</th>
            <th>Quantidade (kg)</th>
            <th>Data</th>
            <th>Responsável</th>
            <th>Observação</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="m" items="${movimentacoes}">
            <tr>
              <td><c:out value="${m.id != null ? m.id : m['id']}"/></td>

              <!-- ALTERAÇÃO: coluna PRODUTO mostrando somente a DESCRIÇÃO -->
              <td>
                <c:choose>
                  <c:when test="${not empty m.produtoDescricao}">
                    <c:out value="${m.produtoDescricao}"/>
                  </c:when>
                  <c:when test="${not empty m.produto and not empty m.produto.descricao}">
                    <c:out value="${m.produto.descricao}"/>
                  </c:when>
                  <c:otherwise>-</c:otherwise>
                </c:choose>
              </td>

              <td><c:out value="${m.tipo != null ? m.tipo : m['tipo']}"/></td>
              <td><c:out value="${m.quantidade != null ? m.quantidade : m['quantidade']}"/></td>
              <td>
                <c:choose>
                  <c:when test="${not empty m.dataMovimentacao}"><c:out value="${m.dataMovimentacao}"/></c:when>
                  <c:when test="${not empty m['dataMovimentacao']}"><c:out value="${m['dataMovimentacao']}"/></c:when>
                  <c:otherwise>-</c:otherwise>
                </c:choose>
              </td>
              <td><c:out value="${m.responsavel != null ? m.responsavel : m['responsavel']}"/></td>
              <td><c:out value="${m.observacao != null ? m.observacao : m['observacao']}"/></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:when>

    <c:otherwise>
      <p class="empty-message">Nenhuma movimentação registrada.</p>
    </c:otherwise>
  </c:choose>

  <div class="btn-row">
    <a href="${pageContext.request.contextPath}/Views/inicio.jsp" class="back-btn">← Voltar</a>
    <div class="spacer"></div>
    <a href="#" id="openModal" class="back-btn small-right color-yellow">+ Nova Movimentação</a>
  </div>
</main>

<!-- Modal / formulário -->
<div id="modalBackdrop" class="modal-backdrop" role="dialog" aria-hidden="true" style="display:none;">
  <div class="modal" role="document">
    <div class="modal-header">
      <h2 class="title">Nova Movimentação</h2>
      <button class="modal-close" id="closeModal" aria-label="Fechar">&times;</button>
    </div>

    <form id="movForm" method="post" action="${pageContext.request.contextPath}/movimentar">
      <input type="hidden" name="action" value="create"/>

      <div class="form-row">
        <label for="tipo">Tipo de movimentação</label>
        <select id="tipo" name="tipo" class="input" required>
          <option value="">Selecione…</option>
          <option value="entrada">Entrada</option>
          <option value="saida">Saída</option>
        </select>
      </div>

      <div class="form-row">
        <label for="produtoId">Produto</label>
        <select id="produtoId" name="produtoId" class="input" required>
          <option value="">Selecione o produto...</option>
          <c:forEach var="p" items="${produtos}">
            <option value="${p.id}"><c:out value="${p.descricao}"/> — <c:out value="${p.marca}"/> (<c:out value="${p.quantidade}"/> kg)</option>
          </c:forEach>
        </select>
      </div>

      <div class="form-row">
        <label for="quantidade">Quantidade (kg)</label>
        <input id="quantidade" name="quantidade" type="number" step="0.01" class="input" placeholder="Ex: 2.50" required/>
      </div>

      <div class="form-row">
        <label for="responsavel">Responsável</label>
        <input id="responsavel" name="responsavel" type="text" class="input" placeholder="Nome do responsável (opcional)"/>
      </div>

      <div class="form-row">
        <label for="observacao">Observação</label>
        <input id="observacao" name="observacao" type="text" class="input" placeholder="Observação (opcional)"/>
      </div>

      <div class="modal-actions">
        <button type="button" id="cancelBtn" class="back-btn small-right color-red">Cancelar</button>
        <button type="submit" class="back-btn small-right color-yellow">Salvar</button>
      </div>
    </form>
  </div>
</div>

<script src="${pageContext.request.contextPath}/Views/js/form.js"></script>

</body>
</html>
