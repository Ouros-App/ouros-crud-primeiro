<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Redefinir senha</title>
    <base href="${pageContext.request.contextPath}/">
</head>
<body>

<c:choose>


    <c:when test="${tokenValido}">
        <h1>Crie uma nova senha</h1>

        <form action="RedefinirSenha" method="post">
            <input type="hidden" name="token" value="<c:out value='${token}'/>">

            <label for="novaSenha">Nova senha</label><br>
            <input type="password" id="novaSenha" name="novaSenha" required><br><br>

            <label for="confirmarSenha">Confirmar senha</label><br>
            <input type="password" id="confirmarSenha" name="confirmarSenha" required><br><br>

            <button type="submit">Salvar nova senha</button>
        </form>

        <c:if test="${erro == 'diferentes'}">
            <p>As senhas não são iguais.</p>
        </c:if>
        <c:if test="${erro == 'fraca'}">
            <p>A senha não atende aos requisitos mínimos.</p>
        </c:if>
    </c:when>


    <c:otherwise>
        <h1>Link inválido</h1>
        <p>Esse link expirou ou já foi usado.</p>
        <a href="../../RecuperarSenha.jsp">Pedir novo link</a>
    </c:otherwise>

</c:choose>

<p><a href="login.jsp">Voltar ao login</a></p>

</body>
</html>
