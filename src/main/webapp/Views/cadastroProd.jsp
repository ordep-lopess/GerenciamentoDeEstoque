<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Cadastrar Doação</title>
  
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
    <h1 class="title" style="margin-bottom:32px">Cadastrar Doação</h1>

    <!-- envia para o servlet /produto com action=create -->
    <form method="post" action="${pageContext.request.contextPath}/produto" class="form-container">
      <input type="hidden" name="action" value="create"/>

      <div class="title-wrapper"><h2 class="title-label">Nome do Doador</h2></div>
      <div class="input-wrapper">
        <input type="text" name="nomeDoador" placeholder="Insira o nome do doador" class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Telefone</h2></div>
      <div class="input-wrapper">
        <input type="tel" name="telefone" placeholder="(99) 99999-9999" class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">E-mail</h2></div>
      <div class="input-wrapper">
        <input type="email" name="email" placeholder="exemplo@dominio.com" class="input" required/>
      </div>
      
      <div class="title-wrapper margin-top"><h2 class="title-label">Descrição do Alimento</h2></div>
      <div class="input-wrapper">
        <input type="text" name="descricao" placeholder="Ex: Ração Seca" class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Marca</h2></div>
      <div class="input-wrapper">
        <input type="text" name="marca" placeholder="Insira a marca" class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Quantidade (kg)</h2></div>
      <div class="input-wrapper">
        <input type="number" name="quantidade" step="0.01" placeholder="Ex: 2.50" class="input" required/>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Animal</h2></div>
      <div class="input-wrapper">
        <select name="animal" class="input" required>
          <option value="">Selecione…</option>
          <option value="cao">Cão</option>
          <option value="gato">Gato</option>
          <option value="aves">Aves</option>
        </select>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Tipo de Alimento</h2></div>
      <div class="input-wrapper">
        <select name="tipo" class="input" required>
          <option value="">Selecione…</option>
          <option value="racao">Ração</option>
          <option value="petisco">Petisco</option>
          <option value="graos">Grãos</option>
        </select>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Pacote Fechado?</h2></div>
      <div class="input-wrapper">
        <select name="pacoteFechado" class="input" required>
          <option value="">Selecione…</option>
          <option value="sim">Sim</option>
          <option value="nao">Não</option>
        </select>
      </div>

      <div class="title-wrapper margin-top"><h2 class="title-label">Data da Doação</h2></div>
      <div class="input-wrapper">
        <input type="date" name="dataDoacao" class="input" required/>
      </div>
      
      <div class="title-wrapper margin-top"><h2 class="title-label">Observação</h2></div>
      <div class="input-wrapper">
          <input type="text" name="observacao" class="input" rows="3"
              placeholder="Digite alguma observação sobre a doação (opcional)"></input>
      </div>

      
      <div class="button-group margin-top">
        <button type="button" class="back-btn" onclick="history.back()">← Voltar</button>
        <button type="submit" class="send-btn">Enviar</button>
      </div>
    </form>
  </main>
</body>
</html>
