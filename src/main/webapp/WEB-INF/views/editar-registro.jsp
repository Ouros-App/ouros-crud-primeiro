<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Editar Registro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
    <header class="EditorCabecalho">
        <h1 id="editorTitulo">Editar Registro de ${tipoAtual == 'energia' ? 'energia' : 'água'}</h1>
        <a class="EditorFechar" href="${pageContext.request.contextPath}/registros?tipo=${tipoAtual}" aria-label="Fechar">×</a>
    </header>
    <form action="${pageContext.request.contextPath}/registros" method="post" target="_top">
        <input type="hidden" name="acao" value="editar">
        <input type="hidden" name="tipo" value="${tipoAtual}">
        <input type="hidden" name="id" value="${registroSelecionado.id}">
        <c:if test="${tipoAtual == 'energia'}">
            <p class="EditorRegistroTipoAjuda">
                Consumo atual: <strong>${registroSelecionado.consumo} kWh</strong>.
                Informe os medidores novamente para recalcular o consumo (final − inicial).
            </p>
        </c:if>
        <div class="EditorGrid">
            <div class="EditorCampo">
                <label for="lote">Lote</label>
                <select id="lote" name="lote" required>
                    <c:forEach var="l" items="${lotes}">
                        <option value="${l.id}" ${l.id == registroSelecionado.idLote ? 'selected' : ''}>#${l.id} – <c:out value="${l.nomeGranja}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="EditorCampo">
                <label for="data">Data do registro</label>
                <input id="data" name="data" type="date" value="${registroSelecionado.dtRegistro}" required>
            </div>
            <div class="EditorCampo">
                <label for="medidorInicial">${tipoAtual == 'energia' ? 'Medidor inicial (kWh)' : 'Hidrômetro inicial (água)'}</label>
                <input id="medidorInicial" name="medidorInicial" type="number" placeholder="1234" min="0" step="0.01"
                       value="${tipoAtual == 'agua' ? registroSelecionado.hidrometroInicio : ''}" required>
            </div>
            <div class="EditorCampo">
                <label for="medidorFinal">${tipoAtual == 'energia' ? 'Medidor final (kWh)' : 'Hidrômetro final (água)'}</label>
                <input id="medidorFinal" name="medidorFinal" type="number" placeholder="4321" min="0" step="0.01"
                       value="${tipoAtual == 'agua' ? registroSelecionado.hidrometroFinal : ''}" required>
            </div>
        </div>
        <footer class="EditorAcoes">
            <a href="${pageContext.request.contextPath}/registros?tipo=${tipoAtual}" class="EditorCancelar">Cancelar</a>
            <button type="submit" class="EditorSalvar">Salvar</button>
        </footer>
    </form>
</main>
<script>
    if (window.parent !== window) {
        document.body.classList.add('EditorEmModal');

        const urlLista = '${pageContext.request.contextPath}/registros?tipo=${tipoAtual}';

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