<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
   <header class="user-header">
    <span class="greeting">
      Olá,
      <c:choose>
        <c:when test="${not empty sessionScope.usuario and not empty sessionScope.usuario.nome}">
          <c:out value="${sessionScope.usuario.nome}" />
        </c:when>
        <c:otherwise>Visitante</c:otherwise>
      </c:choose>
    </span>
    <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Sair</a>
  </header>

<main class="main">
  <c:url var="produtoUrl" value="/produto"/>
  <c:url var="excluirPage" value="/Views/excluirProd.jsp"/>
  <c:url var="inicioPage" value="/Views/inicio.jsp"/>

  <c:choose>
    <c:when test="${param.result == 'deleted'}">
      <div class="mensagem">
        <p>Produto excluído com sucesso!</p>
        <div class="button-group margin-top">
          <a href="${excluirPage}" class="back-btn">← Nova exclusão</a>
          <a href="${inicioPage}" class="back-btn">← Início</a>
        </div>
      </div>
    </c:when>

    <c:when test="${param.result == 'deletefail'}">
      <div class="mensagem">
        <p>Falha ao excluir o produto. Tente novamente mais tarde.</p>
        <div class="button-group margin-top">
          <a href="${excluirPage}" class="back-btn">← Nova exclusão</a>
          <a href="${inicioPage}" class="back-btn">← Início</a>
        </div>
      </div>
    </c:when>

    <c:when test="${param.result == 'hasmovements'}">
      <div class="mensagem">
        <p><strong>Não é possível excluir este produto:</strong> existem movimentações relacionadas.</p>
        <p>Remova ou revise as movimentações antes de tentar novamente.</p>
        <div class="button-group margin-top">
          <a href="${pageContext.request.contextPath}/movimentar?action=list" class="back-btn">Ver movimentações</a>
          <a href="${excluirPage}" class="back-btn">← Nova exclusão</a>
          <a href="${inicioPage}" class="back-btn">← Início</a>
        </div>
      </div>
    </c:when>

    <c:when test="${param.result == 'invalidid'}">
      <div class="mensagem">
        <p>ID inválido. Informe um ID numérico válido.</p>
        <div class="button-group margin-top">
          <a href="${excluirPage}" class="back-btn">← Nova exclusão</a>
          <a href="${inicioPage}" class="back-btn">← Início</a>
        </div>
      </div>
    </c:when>

    <c:when test="${param.result == 'notfound'}">
      <div class="mensagem">
        <p>Produto não encontrado.</p>
        <div class="button-group margin-top">
          <a href="${excluirPage}" class="back-btn">← Nova exclusão</a>
          <a href="${inicioPage}" class="back-btn">← Início</a>
        </div>
      </div>
    </c:when>

    <c:otherwise>
      <h1 class="title" style="margin-bottom:24px">Excluir Produto por ID</h1>

      <form method="post" action="${produtoUrl}" class="form-container" novalidate>
        <input type="hidden" name="action" value="delete"/>

        <div class="input-wrapper">
          <input id="id" name="id" type="number" placeholder="Insira o número do ID" min="1" class="input" required/>
        </div>

        <div class="button-group margin-top">
          <button type="submit" class="remove-btn">Excluir</button>
          <button type="button" class="back-btn" onclick="history.back()">Cancelar</button>
        </div>
      </form>
    </c:otherwise>
  </c:choose>
</main>
</body>
</html>
