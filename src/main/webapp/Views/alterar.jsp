<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <c:if test="${not empty mensagem}">
      <div class="mensagem"><p><c:out value="${mensagem}" /></p></div>
    </c:if>

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

        <form method="post" action="${pageContext.request.contextPath}/produto" class="form-container">
          <input type="hidden" name="action" value="update"/>
          <input type="hidden" name="id" value="${produto.id}"/>

          <div class="title-wrapper"><h2 class="title-label">Nome do Doador</h2></div>
          <div class="input-wrapper">
            <input type="text" name="nomeDoador" class="input" required
                   value="${produto.nomeDoador != null ? produto.nomeDoador : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Telefone</h2></div>
          <div class="input-wrapper">
            <input type="tel" name="telefone" class="input" required
                   value="${produto.telefone != null ? produto.telefone : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Email</h2></div>
          <div class="input-wrapper">
            <input type="email" name="email" class="input" required
                   value="${produto.email != null ? produto.email : ''}"/>
          </div>
          
          <div class="title-wrapper margin-top"><h2 class="title-label">Descrição</h2></div>
          <div class="input-wrapper">
            <input type="text" name="descricao" class="input" required
                   value="${produto.descricao != null ? produto.descricao : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Marca</h2></div>
          <div class="input-wrapper">
            <input type="text" name="marca" class="input" required
                   value="${produto.marca != null ? produto.marca : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Animal</h2></div>
          <div class="input-wrapper">
            <select name="animal" class="input" required>
              <option value="">Selecione…</option>
              <option value="cao" <c:if test="${produto.animal == 'cao'}">selected</c:if>>Cão</option>
              <option value="gato" <c:if test="${produto.animal == 'gato'}">selected</c:if>>Gato</option>
              <option value="aves" <c:if test="${produto.animal == 'aves'}">selected</c:if>>Aves</option>
            </select>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Tipo</h2></div>
          <div class="input-wrapper">
            <select name="tipo" class="input" required>
              <option value="">Selecione…</option>
              <option value="racao" <c:if test="${produto.tipo == 'racao'}">selected</c:if>>Ração</option>
              <option value="petisco" <c:if test="${produto.tipo == 'petisco'}">selected</c:if>>Petisco</option>
              <option value="graos" <c:if test="${produto.tipo == 'graos'}">selected</c:if>>Grãos</option>
            </select>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Pacote Fechado</h2></div>
          <div class="input-wrapper">
            <select name="pacoteFechado" class="input" required>
              <option value="">Selecione…</option>
              <option value="sim" <c:if test="${produto.pacoteFechado == 'sim'}">selected</c:if>>Sim</option>
              <option value="nao" <c:if test="${produto.pacoteFechado == 'nao'}">selected</c:if>>Não</option>
            </select>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Data da Doação</h2></div>
          <div class="input-wrapper">
            <input type="date" name="dataDoacao" class="input" required
                   value="${produto.dataDoacao != null ? produto.dataDoacao : ''}"/>
          </div>

          <div class="title-wrapper margin-top"><h2 class="title-label">Observação</h2></div>
          <div class="input-wrapper">
            <textarea name="observacao" class="input" rows="3"><c:out value="${observacaoProduto != null ? observacaoProduto.observacao : ''}" /></textarea>
          </div>

          <div class="button-group margin-top">
            <button type="button" class="back-btn" onclick="history.back()">← Voltar</button>
            <button type="submit" class="send-btn">Salvar</button>
            <a href="${pageContext.request.contextPath}/Views/inicio.jsp" class="back-btn">← Início</a>
          </div>
        </form>
      </c:otherwise>
    </c:choose>

  </main>
</body>
</html>
