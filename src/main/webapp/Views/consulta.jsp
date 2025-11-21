<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.Login" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Consultar Doação</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/consulta.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/voltar.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/header.css"/>
</head>
<body>
  <%
    Login usuario = (Login) session.getAttribute("usuario");
    String nome = (usuario != null) ? usuario.getNome() : "Visitante";
  %>

  <header class="user-header">
    <span class="greeting">Olá, <%= nome %></span>
    <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Sair</a>
  </header>

  <main class="main">
    <c:choose>
      <c:when test="${empty resultado and empty mensagem}">
        <h1 class="title" style="margin-bottom:32px">Consultar Doação por ID</h1>
        <form method="post" action="${pageContext.request.contextPath}/produto" class="form-container">
          <input type="hidden" name="action" value="consulta"/>
          <div class="input-wrapper">
            <input type="number" name="id" placeholder="Insira o ID da doação" min="1" class="input" required/>
          </div>
          <div class="button-group margin-top">
            <button type="button" class="back-btn" onclick="history.back()">← Voltar</button>
            <button type="submit" class="send-btn">Consultar</button>
          </div>
        </form>
      </c:when>

      <c:otherwise>
        <h1 class="title" style="margin-bottom:32px">Resultado da Consulta</h1>

        <c:if test="${not empty resultado}">
          <div class="table">
            <p class="cell"><strong>ID</strong></p>
            <p class="cell">${resultado.id}</p>

            <p class="cell"><strong>Doador</strong></p>
            <p class="cell">${resultado.nomeDoador}</p>

            <p class="cell"><strong>Telefone</strong></p>
            <p class="cell">${resultado.telefone}</p>

            <p class="cell"><strong>Email</strong></p>
            <p class="cell">${resultado.email}</p>

            <p class="cell"><strong>Descrição</strong></p>
            <p class="cell">${resultado.descricao}</p>

            <p class="cell"><strong>Marca</strong></p>
            <p class="cell">${resultado.marca}</p>

            <p class="cell"><strong>Quantidade (kg)</strong></p>
            <p class="cell">${resultado.quantidade}</p>

            <p class="cell"><strong>Animal</strong></p>
            <p class="cell">${resultado.animal}</p>

            <p class="cell"><strong>Tipo</strong></p>
            <p class="cell">${resultado.tipo}</p>

            <p class="cell"><strong>Pacote Fechado</strong></p>
            <p class="cell">${resultado.pacoteFechado}</p>

            <p class="cell"><strong>Data da Doação</strong></p>
            <p class="cell">${resultado.dataDoacao}</p>
          </div>
        </c:if>

        <c:if test="${not empty mensagem}">
          <p class="erro">${mensagem}</p>
        </c:if>

        <div class="button-group margin-top">
          <a href="${pageContext.request.contextPath}/Views/inicio.jsp" class="back-btn">← Início</a>
          <a href="${pageContext.request.contextPath}/produto?action=consulta" class="send-btn">Nova Consulta</a>
        </div>
      </c:otherwise>
    </c:choose>
  </main>
</body>
</html>
