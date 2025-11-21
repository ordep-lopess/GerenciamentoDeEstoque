<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.Login" %>
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

<%
    Login usuario = (Login) session.getAttribute("usuario");
    String nome = (usuario != null && usuario.getNome() != null) ? usuario.getNome() : "Visitante";
%>

<header class="user-header">
  <span class="greeting">Olá, <%= nome %></span>
  <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Sair</a>
</header>

  <h1 class="title" style="margin-bottom:32px">Lista de Doações</h1>

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
              <th scope="col">Produto</th>
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
                <td>${p.nomeDoador}</td>
                <td>${p.telefone}</td>
                <td>${p.email}</td>
                <td>${p.descricao}</td>
                <td>${p.marca}</td>
                <td>${p.quantidade}</td>
                <td>${p.animal}</td>
                <td>${p.tipo}</td>
                <td>${p.pacoteFechado}</td>
                <td>${p.dataDoacao}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>

        <a href="${pageContext.request.contextPath}/Views/inicio.jsp" class="back-btn">← Voltar</a>
      </c:when>

      <c:otherwise>
        <p class="p-lista">Nenhuma doação encontrada!</p>
        <a href="${pageContext.request.contextPath}/Views/inicio.jsp" class="back-btn">← Voltar</a>
      </c:otherwise>
    </c:choose>
  </main>

</body>
</html>
