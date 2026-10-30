<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Editar Funcionário</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
<link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
<header class="EditorCabecalho">
<h1 id="editorTitulo">Editar Funcionário</h1>
<a class="EditorFechar" href="${pageContext.request.contextPath}/funcionarios" aria-label="Fechar">×</a>
</header>
<form action="${pageContext.request.contextPath}/funcionarios" method="post" autocomplete="off">
<input type="hidden" name="acao" value="editar">
<input type="hidden" name="id" value="<c:out value='${funcionarios.id}'/>">
<div class="EditorGrid">
<div class="EditorCampo EditorCampo--full">
<label for="nome">Nome completo</label>
<input id="nome" name="nome" type="text" placeholder="Rafael Santos" maxlength="100" value="<c:out value='${funcionarios.nome}'/>" required>
<small class="CampoErro" id="nome-erro"><c:out value="${erros.nome}"/></small>
</div>
<div class="EditorCampo">
<label for="cpf">CPF</label>
<input id="cpf" name="cpf" type="text" inputmode="numeric" placeholder="000.000.000-00" maxlength="14" value="<c:out value='${funcionarios.cpf}'/>" data-validar="cpf" required>
<small class="CampoErro" id="cpf-erro"><c:out value="${erros.cpf}"/></small>
</div>
<div class="EditorCampo">
<label for="setor">Setor</label>
<input id="setor" name="setor" type="text" placeholder="Manejo" maxlength="100" value="<c:out value='${funcionarios.setor}'/>" required>
<small class="CampoErro" id="setor-erro"><c:out value="${erros.setor}"/></small>
</div>
<div class="EditorCampo">
<label for="email">E-mail</label>
<input id="email" name="email" type="email" placeholder="exemplo@gmail.com" maxlength="150" value="<c:out value='${funcionarios.email}'/>" data-validar="email" required>
<small class="CampoErro" id="email-erro"><c:out value="${erros.email}"/></small>
</div>
<div class="EditorCampo">
<label for="telefone">Telefone</label>
<input id="telefone" name="telefone" type="tel" inputmode="tel" placeholder="(11) 91234-5678" maxlength="15" value="<c:out value='${funcionarios.telefone}'/>" data-validar="telefone" required>
<small class="CampoErro" id="telefone-erro"><c:out value="${erros.telefone}"/></small>
</div>
<div class="EditorCampo">
<label for="empresa">Empresa</label>
<input id="empresa" name="empresa" type="text" value="<c:out value='${funcionarios.nomeEmpresa}'/>" readonly tabindex="-1" title="A empresa não pode ser alterada por aqui.">
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
