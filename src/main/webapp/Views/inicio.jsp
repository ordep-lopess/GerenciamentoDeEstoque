<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Início</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/voltar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/alterar.css"/>
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

<h1 class="title">empatinha ong</h1>

<header class="bar-wrapper">
    <a href="${pageContext.request.contextPath}/produto?action=list" class="bar-item">
        <p class="bar-text">Lista</p>
    </a>

    <a href="${pageContext.request.contextPath}/produto?action=new" class="bar-item">
        <p class="bar-text">Cadastrar</p>
    </a>

    <a href="${pageContext.request.contextPath}/movimentar?action=list" class="bar-item">
        <p class="bar-text">Movimentar</p>
    </a>

    <a href="${pageContext.request.contextPath}/produto?action=consulta" class="bar-item">
        <p class="bar-text">Consulta</p>
    </a>

    <a href="${pageContext.request.contextPath}/produto?action=edit" class="bar-item">
        <p class="bar-text">Alterar</p>
    </a>

    <a href="${pageContext.request.contextPath}/Views/excluirProd.jsp" class="bar-item">
      <p class="bar-text">Excluir</p>
    </a>
</header>
</body>
</html>
