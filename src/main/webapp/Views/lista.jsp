<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <title>Lista de Doações</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/lista.css"/>
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
<br>
<h1 class="title" style="margin-bottom:20px; text-align:center;">Lista de Doações</h1>
<div class="top-actions">

  <div class="top-right">
    <a id="exportProdutosFallback" class="btn-export" href="${pageContext.request.contextPath}/produto?export=produtos&format=csv" title="Exportar Produtos CSV" role="button">
      <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false" role="img" xmlns="http://www.w3.org/2000/svg">
        <path d="M19 3H8c-1.1 0-2 .9-2 2v3H5c-1.1 0-2 .9-2 2v6c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z"/>
        <path d="M11 12h2v6h3l-4 4-4-4h3z"/>
      </svg>
      Exportar Produtos CSV
    </a>
  </div>
</div>

<main class="main">
  <c:choose>
    <c:when test="${not empty produtos}">
      <table class="table">
        <colgroup>
          <col style="width:  5%"/>
          <col style="width: 15%"/>
          <col style="width: 12%"/>
          <col style="width: 12%"/>
          <col style="width: 18%"/>
          <col style="width: 10%"/>
          <col style="width:  8%"/>
          <col style="width:  8%"/>
          <col style="width:  8%"/>
          <col style="width:  8%"/>
          <col style="width:  9%"/>
        </colgroup>
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Nome Doador</th>
            <th scope="col">Telefone</th>
            <th scope="col">E-mail</th>
            <th scope="col">Marca</th>
            <th scope="col">Quantidade (kg)</th>
            <th scope="col">Animal</th>
            <th scope="col">Tipo</th>
            <th scope="col">Pacote Fechado?</th>
            <th scope="col">Data da Doação</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="p" items="${produtos}">
            <tr>
              <th scope="row">${p.id}</th>
              <td><c:out value="${p.nomeDoador}"/></td>
              <td><c:out value="${p.telefone}"/></td>
              <td><c:out value="${p.email}"/></td>
              <td><c:out value="${p.marca}"/></td>
              <td><c:out value="${p.quantidade}"/></td>
              <td><c:out value="${p.animal}"/></td>
              <td><c:out value="${p.tipo}"/></td>
              <td><c:out value="${p.pacoteFechado}"/></td>
              <td><c:out value="${p.dataDoacao}"/></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <a href="${pageContext.request.contextPath}/Views/inicio.jsp" class="back-btn" style="margin-top:16px; display:inline-block;">← Voltar</a>
    </c:when>

    <c:otherwise>
      <p class="p-lista">Nenhuma doação encontrada!</p>
      <a href="${pageContext.request.contextPath}/Views/inicio.jsp" class="back-btn">← Voltar</a>
    </c:otherwise>
  </c:choose>
</main>

</body>
</html>
