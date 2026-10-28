<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Editar Lote</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
    <header class="EditorCabecalho">
        <h1 id="editorTitulo">Editar Lote</h1>
        <a class="EditorFechar" href="${pageContext.request.contextPath}/lotes" aria-label="Fechar">×</a>
    </header>
    <form action="${pageContext.request.contextPath}/lotes" method="post" target="_top">
        <input type="hidden" name="acao" value="editar">
        <input type="hidden" name="id" value="${loteSelecionado.id}">
        <div class="EditorGrid">
            <div class="EditorCampo EditorCampo--full">
                <label for="granja">Granja</label>
                <select id="granja" name="granja" required>
                    <c:forEach var="g" items="${granjas}">
                        <option value="${g.id}" ${g.id == loteSelecionado.idGranja ? 'selected' : ''}><c:out value="${g.nome}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="EditorCampo">
                <label for="entregues">Galinhas entregues</label>
                <input id="entregues" name="entregues" type="number" placeholder="5000" min="1" step="1"
                       value="${loteSelecionado.galinhasEntregadas}" required>
            </div>
            <div class="EditorCampo">
                <label for="recebidas">Galinhas recebidas</label>
                <input id="recebidas" name="recebidas" type="number" placeholder="4980" min="0" step="1"
                       value="${loteSelecionado.galinhasRecebidas}" required>
            </div>
            <div class="EditorCampo">
                <label for="nascimento">Data de nascimento</label>
                <input id="nascimento" name="nascimento" type="date"
                       value="${loteSelecionado.dtNascimento}" required>
            </div>
            <div class="EditorCampo">
                <label for="chegada">Data de chegada</label>
                <input id="chegada" name="chegada" type="date"
                       value="${loteSelecionado.dtChegada}" required>
            </div>
            <div class="EditorCampo">
                <label for="entrega">Data de entrega</label>
                <input id="entrega" name="entrega" type="date"
                       value="${loteSelecionado.dtEntrega}">
            </div>
            <div class="EditorCampo">
                <label for="ganho">Ganho (kg)</label>
                <input id="ganho" name="ganho" type="number" placeholder="0,00" min="0" step="0.01"
                       value="${loteSelecionado.ganho}" required>
            </div>
        </div>
        <footer class="EditorAcoes">
            <a href="${pageContext.request.contextPath}/lotes" class="EditorCancelar">Cancelar</a>
            <button type="submit" class="EditorSalvar">Salvar</button>
        </footer>
    </form>
</main>
<script>
    if (window.parent !== window) {
        document.body.classList.add('EditorEmModal');

        const urlLista = '${pageContext.request.contextPath}/lotes';

        function fecharFormulario(event) {
            event.preventDefault();
            try {
                // mesma origem: fecha o <dialog> do pai diretamente
                window.parent.document.getElementById('formularioModal').close();
            } catch (e) {
                // origem diferente: volta para a listagem na janela inteira
                window.top.location.href = urlLista;
            }
        }

        document.querySelectorAll('.EditorFechar, .EditorCancelar').forEach(function (link) {
            link.addEventListener('click', fecharFormulario);
        });
        document.addEventListener('keydown', function (event) {
            if (event.key === 'Escape') { event.stopImmediatePropagation(); fecharFormulario(event); }
        }, true);

        new ResizeObserver(function () {
            window.parent.postMessage(
                {type:'ouros:altura-formulario',
                    height:Math.ceil(document.querySelector('.EditorCartao').getBoundingClientRect().height)},
                '*');
        }).observe(document.querySelector('.EditorCartao'));
    }
</script>
</body>
</html>