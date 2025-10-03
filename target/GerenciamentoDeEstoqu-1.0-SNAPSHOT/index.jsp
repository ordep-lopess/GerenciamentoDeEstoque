<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Soft Paper - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/voltar.css">
</head>
<body class="bodyp">
    <main class="main-login">
        <div class="logincard flex">
            <h2>Bem vindo de volta!</h2>
            <c:if test="${not empty erro}">
                <div class="alert alert-danger" style="color: red; font-weight: bold;">
                    ${erro}
                </div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">
                <input type="email" name="email" placeholder="Email" required>
                <input type="password" name="senha" placeholder="Senha" required>
                <br>
                <button class="form" type="submit">Entrar</button>
            </form>
            
            <h4>Não tem uma conta? 
                <a href="${pageContext.request.contextPath}/Views/cadastro.jsp" style="text-decoration: underline;">Cadastre-se.</a>
            </h4>
        </div>
    </main>
    
    <script src="${pageContext.request.contextPath}/Views/js/menu.js"></script>
</body>
</html>
