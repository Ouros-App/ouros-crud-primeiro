<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Editar Meta</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
    <header class="EditorCabecalho">
        <h1 id="editorTitulo">Editar Meta</h1>
        <a class="EditorFechar" href="${pageContext.request.contextPath}/metas?tipo=${metaSelecionada.individual ? 'individual' : 'estadual'}" aria-label="Fechar">×</a>
    </header>
    <form action="${pageContext.request.contextPath}/metas" method="post" target="_top">
        <input type="hidden" name="acao" value="editar">
        <input type="hidden" name="id" value="${metaSelecionada.id}">
        <div class="EditorAbas" role="group" aria-label="Tipo">
            <button type="button" data-grupo="tipo" data-valor="individual" class="EditorAba">Individual</button>
            <button type="button" data-grupo="tipo" data-valor="estadual" class="EditorAba">Estadual</button>
            <input type="hidden" name="tipo" value="${metaSelecionada.individual ? 'individual' : 'estadual'}">
        </div>
        <p class="EditorMetaEscopoAjuda" data-escopo="individual">Meta individual: vale para uma granja específica.</p>
        <p class="EditorMetaEscopoAjuda" data-escopo="estadual">Meta estadual: vale para uma região e um estado, sem granja.</p>
        <div class="EditorGrid">
            <div class="EditorCampo EditorCampo--full">
                <label for="titulo">Título</label>
                <input id="titulo" name="titulo" type="text" placeholder="Diminuindo água" maxlength="100"
                       value="<c:out value='${metaSelecionada.titulo}'/>" required>
            </div>
            <div class="EditorCampo EditorCampo--full">
                <label for="descricao">Descrição</label>
                <input id="descricao" name="descricao" type="text" placeholder="Reduzir o consumo de água do lote"
                       value="<c:out value='${metaSelecionada.descricao}'/>" required>
            </div>
            <div class="EditorCampo">
                <span class="EditorLabel">Tipo de valor</span>
                <div class="EditorAbas" role="group" aria-label="Tipo de valor">
                    <button type="button" data-grupo="tipoValor" data-valor="AGUA" class="EditorAba">
                        <svg class="EditorIcone agua" viewBox="0 0 16 20" aria-hidden="true">
                            <path fill="currentColor" d="M8 0C6 4 1 9 1 13a7 7 0 0 0 14 0C15 9 10 4 8 0Z"/>
                        </svg> Água</button>
                    <button type="button" data-grupo="tipoValor" data-valor="ENERGIA" class="EditorAba">
                        <svg class="EditorIcone energia" viewBox="0 0 16 20" aria-hidden="true">
                            <path fill="currentColor" d="M9 0 1 12h6l-1 8 9-13H9Z"/>
                        </svg> Energia</button>
                    <input type="hidden" name="tipoValor" value="<c:out value='${metaSelecionada.tipoMeta}'/>">
                </div>
            </div>
            <div class="EditorCampo">
                <label for="alvo">Valor alvo</label>
                <input id="alvo" name="alvo" type="number" placeholder="1100" min="0.01" step="0.01"
                       value="${metaSelecionada.valorAlvo}" required>
            </div>
            <div class="EditorCampo">
                <label for="status">Status</label>
                <select id="status" name="status" required>
                    <option value="NAO_INICIADA" ${metaSelecionada.status == 'NAO_INICIADA' ? 'selected' : ''}>Não iniciada</option>
                    <option value="EM_ANDAMENTO" ${metaSelecionada.status == 'EM_ANDAMENTO' ? 'selected' : ''}>Em andamento</option>
                    <option value="FINALIZADA" ${metaSelecionada.status == 'FINALIZADA' ? 'selected' : ''}>Finalizada</option>
                </select>
            </div>
            <div class="EditorCampo" data-escopo="individual">
                <label for="granja">Granja</label>
                <select id="granja" name="granja" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="g" items="${granjas}">
                        <option value="${g.id}" ${g.id == metaSelecionada.idGranja ? 'selected' : ''}><c:out value="${g.nome}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="EditorCampo" data-escopo="estadual">
                <label for="regiao">Região</label>
                <select id="regiao" name="regiao" required>
                    <option ${metaSelecionada.regiao == 'Sudeste' ? 'selected' : ''}>Sudeste</option>
                    <option ${metaSelecionada.regiao == 'Sul' ? 'selected' : ''}>Sul</option>
                    <option ${metaSelecionada.regiao == 'Centro-Oeste' ? 'selected' : ''}>Centro-Oeste</option>
                    <option ${metaSelecionada.regiao == 'Nordeste' ? 'selected' : ''}>Nordeste</option>
                    <option ${metaSelecionada.regiao == 'Norte' ? 'selected' : ''}>Norte</option>
                </select>
            </div>
            <div class="EditorCampo" data-escopo="estadual">
                <label for="estado">Estado</label>
                <input id="estado" name="estado" type="text" placeholder="SP" maxlength="50"
                       value="<c:out value='${metaSelecionada.estado}'/>" required>
            </div>
        </div>
        <footer class="EditorAcoes">
            <a href="${pageContext.request.contextPath}/metas?tipo=${metaSelecionada.individual ? 'individual' : 'estadual'}" class="EditorCancelar">Cancelar</a>
            <button type="submit" class="EditorSalvar">Salvar</button>
        </footer>
    </form>
</main>
<script>
    // alterna as abas (individual/estadual e água/energia) e mostra só os campos do escopo escolhido
    (() => {
        const form = document.querySelector('form');
        function atualizar() {
            const individual = form.elements.namedItem('tipo').value === 'individual';
            const tipoValor = form.elements.namedItem('tipoValor').value;

            form.querySelectorAll('[data-grupo]').forEach(botao => {
                const ativo = form.elements.namedItem(botao.dataset.grupo).value === botao.dataset.valor;
                botao.classList.toggle('ativa', ativo);
                botao.setAttribute('aria-pressed', String(ativo));
            });

            document.querySelector('label[for=alvo]').textContent =
                'Valor alvo' + (tipoValor === 'AGUA' ? ' (litros)' : tipoValor === 'ENERGIA' ? ' (kWh)' : '');

            // campos desativados não são enviados nem validados
            form.querySelectorAll('[data-escopo]').forEach(campo => {
                const visivel = (campo.dataset.escopo === 'individual') === individual;
                campo.hidden = !visivel;
                campo.querySelectorAll('input, select').forEach(controle => { controle.disabled = !visivel; });
            });
        }
        form.querySelectorAll('[data-grupo]').forEach(botao => botao.addEventListener('click', () => {
            form.elements.namedItem(botao.dataset.grupo).value = botao.dataset.valor;
            atualizar();
        }));
        atualizar();
    })();
</script>
<script>
    if (window.parent !== window) {
        document.body.classList.add('EditorEmModal');

        const urlLista = '${pageContext.request.contextPath}/metas?tipo=${metaSelecionada.individual ? "individual" : "estadual"}';

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