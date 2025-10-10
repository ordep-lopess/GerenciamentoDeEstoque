<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <title>Cadastro de Usuário</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/voltar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/cadastro.css">
</head>
<body class="bodyp">
  
  <main class="main-cadastro">
    <h1 class="title-login">empatinha ong</h1>
    <div class="cadastrocard flex">
      <h2>Crie sua conta!</h2>
      <form id="cadastroForm" action="${pageContext.request.contextPath}/cadastro" method="post" class="cadastro-form">
      
        <label>Nome completo:</label>
        <input type="text" name="nome" class="input-normal" required>
        
        <label>Email:</label>
        <input type="email" name="email" class="input-normal" required>
        
        <label>Senha:</label>
        <input type="password" name="senha" class="input-normal" required
            pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$"
            title="A senha deve ter pelo menos 8 caracteres, incluir 1 letra maiúscula, 1 letra minúscula e 1 caractere especial."
            onfocus="dica.showPasswordCriteria()"
            onblur="dica.hidePasswordCriteria()"
            oninput="this.setCustomValidity('')">

        <div id="passwordCriteria" style="color: white; display: none;">
          A senha deve ter pelo menos 8 caracteres,<br>
          incluir 1 letra maiúscula, 1 letra minúscula<br>
          e 1 caractere especial.
        </div>
        
        <label>Repita sua senha:</label>
        <input type="password" name="senhaRepeat" class="input-normal" required oninput="checkPasswordMatch()">
        
        <div class="forminput" style="display: flex; gap: 1rem;">
          <div class="formcoluna" style="flex: 1;">
            <label>Rua:</label><br>
            <input type="text" name="rua" class="input-medio" required>
          </div>
          <div class="formcoluna" style="flex: 2;">
            <label>Número:</label><br>
            <input type="number" name="num" class="input-pequeno" required>
          </div>
        </div>
        
        <label>CEP:</label>
        <input type="number" name="cep" class="input-normal" required>
        <br>
        <button type="submit" class="form">Cadastrar</button>
      </form>
      <h4>Já tem uma conta? <a href="../index.jsp" style="text-decoration: underline;">Faça o login.</a></h4>
    </div>
  </main>
  
  
  <script src="${pageContext.request.contextPath}/Views/js/menu.js"></script>
  <script src="js/outros.js"></script>
</body>
</html>
