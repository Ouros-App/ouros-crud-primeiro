<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!-- Formulário de novo registro de consumo. -->
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Novo Registro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<!-- Área principal da tela. -->
<main class="EditorCartao" aria-labelledby="editorTitulo">
    <header class="EditorCabecalho">
        <h1 id="editorTitulo">Novo Registro de água</h1>
        <a class="EditorFechar" href="${pageContext.request.contextPath}/registros?tipo=${tipoAtual}" aria-label="Fechar">×</a>
    </header>
    <!-- Campos e ações da tela. -->
<form action="${pageContext.request.contextPath}/registros" method="post" target="_top">
        <input type="hidden" name="acao" value="inserir">
        <div class="EditorAbas" role="group" aria-label="Tipo">
            <button type="button" data-valor="agua" aria-pressed="true" class="EditorAba ativa">
                <svg class="EditorIcone agua" viewBox="0 0 16 20" aria-hidden="true">
                    <path fill="currentColor" d="M8 0C6 4 1 9 1 13a7 7 0 0 0 14 0C15 9 10 4 8 0Z"/>
                </svg> Água</button>
            <button type="button" data-valor="energia" aria-pressed="false" class="EditorAba">
                <svg class="EditorIcone energia" viewBox="0 0 16 20" aria-hidden="true">
                    <path fill="currentColor" d="M9 0 1 12h6l-1 8 9-13H9Z"/>
                </svg> Energia</button>
            <input type="hidden" name="tipo" value="${tipoAtual}">
        </div>
        <div class="EditorGrid">
            <div class="EditorCampo">
                <label for="lote">Lote</label>
                <select id="lote" name="lote" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="l" items="${lotes}">
                        <option value="${l.id}">#${l.id} – <c:out value="${l.nomeGranja}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="EditorCampo">
                <label for="data">Data do registro</label>
                <input id="data" name="data" type="date" required>
            </div>
            <div class="EditorCampo">
                <label for="medidorInicial">Hidrômetro inicial (água)</label>
                <input id="medidorInicial" name="medidorInicial" type="number" placeholder="1234" min="0" step="0.01" required>
            </div>
            <div class="EditorCampo">
                <label for="medidorFinal">Hidrômetro final (água)</label>
                <input id="medidorFinal" name="medidorFinal" type="number" placeholder="4321" min="0" step="0.01" required>
            </div>
        </div>
        <footer class="EditorAcoes">
            <a href="${pageContext.request.contextPath}/registros?tipo=${tipoAtual}" class="EditorCancelar">Cancelar</a>
            <button type="submit" class="EditorSalvar">Salvar</button>
        </footer>
    </form>
</main>
<!-- Comportamentos da tela. -->
<script>
    // alterna entre água e energia (só muda o tipo enviado e os rótulos)
    (() => {
        const form = document.querySelector('form');
        function atualizar() {
            const energia = form.elements.namedItem('tipo').value === 'energia';
            document.getElementById('editorTitulo').textContent = 'Novo Registro de ' + (energia ? 'energia' : 'água');
            document.querySelector('label[for=medidorInicial]').textContent = energia ? 'Medidor inicial (kWh)' : 'Hidrômetro inicial (água)';
            document.querySelector('label[for=medidorFinal]').textContent = energia ? 'Medidor final (kWh)' : 'Hidrômetro final (água)';
            form.querySelectorAll('[data-valor]').forEach(botao => {
                const ativo = (botao.dataset.valor === 'energia') === energia;
                botao.classList.toggle('ativa', ativo);
                botao.setAttribute('aria-pressed', String(ativo));
            });
        }
        form.querySelectorAll('[data-valor]').forEach(botao => botao.addEventListener('click', () => {
            form.elements.namedItem('tipo').value = botao.dataset.valor;
            atualizar();
        }));
        atualizar();
    })();
</script>
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