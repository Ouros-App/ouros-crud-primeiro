<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!-- Formulário de novo lote. -->
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Novo Lote</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<!-- Área principal da tela. -->
<main class="EditorCartao" aria-labelledby="editorTitulo">
    <header class="EditorCabecalho">
        <h1 id="editorTitulo">Novo Lote</h1>
        <a class="EditorFechar" href="${pageContext.request.contextPath}/lotes" aria-label="Fechar">×</a>
    </header>
    <!-- Campos e ações da tela. -->
<form action="${pageContext.request.contextPath}/lotes" method="post" target="_top">
        <input type="hidden" name="acao" value="inserir">
        <div class="EditorGrid">
            <div class="EditorCampo EditorCampo--full">
                <label for="granja">Granja</label>
                <select id="granja" name="granja" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="g" items="${granjas}">
                        <option value="${g.id}"><c:out value="${g.nome}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="EditorCampo">
                <label for="entregues">Galinhas entregues</label>
                <input id="entregues" name="entregues" type="number" placeholder="5000" min="1" step="1" required>
            </div>
            <div class="EditorCampo">
                <label for="recebidas">Galinhas recebidas</label>
                <input id="recebidas" name="recebidas" type="number" placeholder="4980" min="0" step="1" required>
            </div>
            <div class="EditorCampo">
                <label for="nascimento">Data de nascimento</label>
                <input id="nascimento" name="nascimento" type="date" required>
            </div>
            <div class="EditorCampo">
                <label for="chegada">Data de chegada</label>
                <input id="chegada" name="chegada" type="date" required>
            </div>
            <div class="EditorCampo">
                <label for="entrega">Data de entrega</label>
                <input id="entrega" name="entrega" type="date">
            </div>
            <div class="EditorCampo">
                <label for="ganho">Ganho (kg)</label>
                <input id="ganho" name="ganho" type="number" placeholder="0,00" min="0" step="0.01" required>
            </div>
        </div>
        <footer class="EditorAcoes">
            <a href="${pageContext.request.contextPath}/lotes" class="EditorCancelar">Cancelar</a>
            <button type="submit" class="EditorSalvar">Salvar</button>
        </footer>
    </form>
</main>
<!-- Comportamentos da tela. -->
<script>
    if (window.parent !== window) {
        document.body.classList.add('EditorEmModal');
        function fecharFormulario(event) {
            event.preventDefault();
            window.parent.postMessage('ouros:fechar-formulario', location.origin);
        }
        document.querySelectorAll('.EditorFechar, .EditorCancelar').forEach(function (link) {
            link.addEventListener('click', fecharFormulario);
        });
        document.addEventListener('keydown', function (event) {
            if (event.key === 'Escape') { event.stopImmediatePropagation(); fecharFormulario(event); }
        }, true);
        new ResizeObserver(function () {
            window.parent.postMessage({type:'ouros:altura-formulario',height:Math.ceil(document.querySelector('.EditorCartao').getBoundingClientRect().height)}, location.origin);
        }).observe(document.querySelector('.EditorCartao'));
    }
</script>
</body>
</html>