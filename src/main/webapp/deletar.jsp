<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Confirmar exclusão</title>
    <link rel="stylesheet" href="Style.css?v=6">
    <link rel="icon" href="img/Asa-icon.png">
</head>
<body class="ExcluirPagina">
    <main class="ExcluirCartao" aria-labelledby="excluirTitulo" aria-describedby="excluirAviso">
        <div class="ExcluirIlustracao" aria-hidden="true">
            <svg viewBox="0 0 24 24"><path fill="currentColor" d="M8 3h8l1 2h4v2H3V5h4l1-2Zm-3 6h14v11a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V9Z"/></svg>
        </div>
        <h1 id="excluirTitulo">Tem certeza que deseja excluir este item?</h1>
        <p id="excluirAviso">Essa ação não pode ser desfeita!</p>
        <form method="post" id="excluirForm">
            <input type="hidden" name="acao" value="excluir">
            <input type="hidden" name="id" value="">
            <input type="hidden" name="tipo" value="">
            <div class="ExcluirAcoes">
                <button type="button" class="ExcluirCancelar" id="cancelar">Cancelar</button>
                <button type="submit" class="ExcluirConfirmar">
                    <svg viewBox="0 0 24 24" aria-hidden="true"><path fill="currentColor" d="M8 3h8l1 2h4v2H3V5h4l1-2Zm-3 6h14v11a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V9Z"/></svg>
                    Excluir
                </button>
            </div>
            <p id="excluirResultado" class="ExcluirResultado" role="status" tabindex="-1" hidden></p>
        </form>
    </main>
    <script>
    (() => {
        const routes = {granja:'granjas', lote:'lotes', funcionario:'funcionarios', registro:'registros', meta:'metas'};
        const labels = {granja:'a granja', lote:'o lote', funcionario:'o funcionário', registro:'o registro', meta:'a meta'};
        const entity = new URLSearchParams(window.location.search).get('entidade');
        const route = Object.prototype.hasOwnProperty.call(routes, entity) ? routes[entity] : null;
        const form = document.getElementById('excluirForm');
        const titulo = document.getElementById('excluirTitulo');
        const notice = document.getElementById('excluirResultado');
        const submitButton = form.querySelector('.ExcluirConfirmar');
        let data = {};
        try {
            const selecionado = JSON.parse(sessionStorage.getItem('ouros-excluir') || '{}');
            if (selecionado && typeof selecionado === 'object' && !Array.isArray(selecionado)) data = selecionado;
        } catch (error) { /* Sem seleção válida. */ }
        if (data.entidade !== entity) data = {};
        const id = typeof data.id === 'string' || typeof data.id === 'number' ? String(data.id).trim() : '';
        const tipo = typeof data.tipo === 'string' ? data.tipo : '';
        const nome = typeof data.nome === 'string' ? data.nome.trim() : '';
        const selecaoValida = Boolean(route && id);

        if (route) {
            form.action = route;
            form.querySelector('input[name="id"]').value = id;
            form.querySelector('input[name="tipo"]').value = tipo;
            titulo.textContent = 'Tem certeza que deseja excluir ' + labels[entity] + (nome ? ' ' + nome : '') + '?';
        }
        if (!selecaoValida) {
            submitButton.disabled = true;
            notice.hidden = false;
            notice.textContent = 'Não foi possível identificar o item. Feche esta janela e tente novamente pela lista.';
        }

        function close() {
            if (window.parent !== window) window.parent.postMessage('ouros:fechar-formulario', location.origin);
            else location.href = (route || 'inicio') + '.jsp';
        }
        document.getElementById('cancelar').addEventListener('click', close);
        document.addEventListener('keydown', event => { if (event.key === 'Escape') { event.preventDefault(); close(); } });
        form.addEventListener('submit', event => {
            // A identificação real e a autorização da exclusão devem ser validadas pelo backend.
            if (!selecaoValida) {
                event.preventDefault();
                notice.hidden = false;
                notice.textContent = 'Não foi possível identificar o item. Feche esta janela e tente novamente pela lista.';
                notice.focus();
                return;
            }
            submitButton.disabled = true;
        });
        if (window.parent !== window) {
            document.body.classList.add('ExcluirEmModal');
            const card = document.querySelector('.ExcluirCartao');
            if ('ResizeObserver' in window) {
                new ResizeObserver(() => window.parent.postMessage({type:'ouros:altura-formulario',height:Math.ceil(card.getBoundingClientRect().height)}, location.origin)).observe(card);
            }
        }
    })();
    </script>
</body>
</html>
