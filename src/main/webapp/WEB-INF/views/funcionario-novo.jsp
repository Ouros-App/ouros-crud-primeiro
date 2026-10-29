<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Novo Funcionário</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
<link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
<header class="EditorCabecalho">
<h1 id="editorTitulo">Novo Funcionário</h1>
<a class="EditorFechar" href="${pageContext.request.contextPath}/funcionarios" aria-label="Fechar">×</a>
</header>
<form action="${pageContext.request.contextPath}/funcionarios" method="post" autocomplete="off">
<input type="hidden" name="acao" value="inserir">
<div class="EditorGrid">
<div class="EditorCampo EditorCampo--full">
<label for="nome">Nome completo</label>
<input id="nome" name="nome" type="text" placeholder="Rafael Santos" maxlength="100" value="<c:out value='${param.nome}'/>" required>
<small class="CampoErro" id="nome-erro"><c:out value="${erros.nome}"/></small>
</div>
<div class="EditorCampo">
<label for="cpf">CPF</label>
<input id="cpf" name="cpf" type="text" inputmode="numeric" placeholder="000.000.000-00" maxlength="14" value="<c:out value='${param.cpf}'/>" data-validar="cpf" required>
<small class="CampoErro" id="cpf-erro"><c:out value="${erros.cpf}"/></small>
</div>
<div class="EditorCampo">
<label for="setor">Setor</label>
<input id="setor" name="setor" type="text" placeholder="Manejo" maxlength="100" value="<c:out value='${param.setor}'/>" required>
<small class="CampoErro" id="setor-erro"><c:out value="${erros.setor}"/></small>
</div>
<div class="EditorCampo">
<label for="email">E-mail</label>
<input id="email" name="email" type="email" placeholder="exemplo@gmail.com" maxlength="150" value="<c:out value='${param.email}'/>" data-validar="email" required>
<small class="CampoErro" id="email-erro"><c:out value="${erros.email}"/></small>
</div>
<div class="EditorCampo">
<label for="telefone">Telefone</label>
<input id="telefone" name="telefone" type="tel" inputmode="tel" placeholder="(11) 91234-5678" maxlength="15" value="<c:out value='${param.telefone}'/>" data-validar="telefone" required>
<small class="CampoErro" id="telefone-erro"><c:out value="${erros.telefone}"/></small>
</div>
<div class="EditorCampo">
<label for="empresa">Empresa</label>
<input id="empresa" name="empresa" type="text" list="listaEmpresas" placeholder="AgroAves Brasil Ltda" maxlength="150" value="<c:out value='${param.empresa}'/>" required>
<datalist id="listaEmpresas">
<c:forEach var="emp" items="${empresas}"><option value="<c:out value='${emp.nome}'/>"></option></c:forEach>
</datalist>
<small class="CampoErro" id="empresa-erro"><c:out value="${erros.empresa}"/></small>
</div>
</div>
<footer class="EditorAcoes">
<a href="${pageContext.request.contextPath}/funcionarios" class="EditorCancelar">Cancelar</a>
<button type="submit" class="EditorSalvar">Salvar</button>
</footer>
<p class="EditorAviso" role="status" hidden></p>
</form>
</main>
<script src="${pageContext.request.contextPath}/js/validacao.js"></script>
<script src="${pageContext.request.contextPath}/js/crud.js"></script>
</body>
</html>
