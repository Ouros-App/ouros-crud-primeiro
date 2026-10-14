<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro</title>
</head>
<body>
<h1>Cadastro de Proprietário</h1>

<form action="cadastro" method="post">

    <label for="nome">Nome:</label><br>
    <input type="text" id="nome" name="nome"><br><br>

    <label for="cpf">CPF:</label><br>
    <input type="text" id="cpf" name="cpf" placeholder="000.000.000-00"><br><br>

    <label for="email">Email:</label><br>
    <input type="text" id="email" name="email"><br><br>

    <label for="telefone">Telefone:</label><br>
    <input type="text" id="telefone" name="telefone" placeholder="(11) 91234-5678"><br><br>

    <label for="senha">Senha:</label><br>
    <input type="password" id="senha" name="senha"><br><br>

    <label for="idGranja">ID da Granja:</label><br>
    <input type="text" id="idGranja" name="idGranja"><br><br>

    <input type="submit" value="Cadastrar">

</form>

</body>
</html>