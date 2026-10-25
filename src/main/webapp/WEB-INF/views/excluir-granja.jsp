<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Confirmar exclusão</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="ExcluirPagina">
<main class="ExcluirCartao" aria-labelledby="excluirTitulo" aria-describedby="excluirAviso">
    <div class="ExcluirIlustracao" aria-hidden="true">
        <svg viewBox="0 0 24 24" width="16" height="16"><path fill="currentColor" d="M8 3h8l1 2h4v2H3V5h4l1-2Zm-3 6h14v11a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V9Z"/></svg>
    </div>
    <h1 id="excluirTitulo">Tem certeza que deseja excluir a granja <c:out value="${granjaSelecionada.nome}"/>?</h1>
    <p id="excluirAviso">Essa ação não pode ser desfeita! Lotes, registros, metas, proprietários e endereços desta granja também serão excluídos.</p>
    <form action="${pageContext.request.contextPath}/granjas" method="post" target="_top" id="excluirForm">
        <input type="hidden" name="acao" value="excluir">
        <input type="hidden" name="id" value="${granjaSelecionada.id}">
        <div class="ExcluirAcoes">
            <button type="button" class="ExcluirCancelar" id="cancelar" onclick="fecharFormulario()">Cancelar</button>
            <button type="submit" class="ExcluirConfirmar">
                <svg viewBox="0 0 24 24" aria-hidden="true"><path fill="currentColor" d="M8 3h8l1 2h4v2H3V5h4l1-2Zm-3 6h14v11a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V9Z"/></svg>
                Excluir
            </button>
        </div>
    </form>
</main>
<script>
    function fecharFormulario() {
        window.parent.document.getElementById('formularioModal').close();
    }
</script>
</body>
</html>