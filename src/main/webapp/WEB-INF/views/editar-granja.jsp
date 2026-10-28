<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Editar Granja</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
    <header class="EditorCabecalho">
        <h1 id="editorTitulo">Editar Granja</h1>
        <a class="EditorFechar" href="${pageContext.request.contextPath}/granjas" aria-label="Fechar">×</a>
    </header>
    <form action="${pageContext.request.contextPath}/granjas" method="post" target="_top">
        <input type="hidden" name="acao" value="editar">
        <input type="hidden" name="id" value="${granjaSelecionada.id}">
        <div class="EditorGrid">
            <div class="EditorCampo EditorCampo--full">
                <label for="nome">Nome da Granja</label>
                <input id="nome" name="nome" type="text" placeholder="Granja Ouro Branco"
                       value="<c:out value='${granjaSelecionada.nome}'/>" required>
            </div>
            <div class="EditorCampo">
                <label for="capacidade">Capacidade de aves</label>
                <input id="capacidade" name="capacidade" type="number" placeholder="5000" min="1" step="1"
                       value="${granjaSelecionada.capacidadeDeAves}" required>
            </div>
            <div class="EditorCampo">
                <label for="area">Área da propriedade (HA)</label>
                <input id="area" name="area" type="number" placeholder="2" min="0" step="1"
                       value="${granjaSelecionada.areaPropriedade}" required>
            </div>
            <div class="EditorCampo">
                <label for="regiao">Região</label>
                <select id="regiao" name="regiao" required>
                    <option ${granjaSelecionada.regiao == 'Sudeste' ? 'selected' : ''}>Sudeste</option>
                    <option ${granjaSelecionada.regiao == 'Sul' ? 'selected' : ''}>Sul</option>
                    <option ${granjaSelecionada.regiao == 'Centro-Oeste' ? 'selected' : ''}>Centro-Oeste</option>
                    <option ${granjaSelecionada.regiao == 'Nordeste' ? 'selected' : ''}>Nordeste</option>
                    <option ${granjaSelecionada.regiao == 'Norte' ? 'selected' : ''}>Norte</option>
                </select>
            </div>
            <div class="EditorCampo">
                <label for="empresa">Empresa</label>
                <select id="empresa" name="empresa" required>
                    <c:forEach var="e" items="${empresas}">
                        <option value="${e.id}" ${e.id == granjaSelecionada.idEmpresa ? 'selected' : ''}><c:out value="${e.nome}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="EditorCampo">
                <label for="municipio">Município</label>
                <input id="municipio" name="municipio" type="text" placeholder="Arujá" maxlength="100"
                       value="<c:out value='${granjaSelecionada.municipio}'/>" required>
            </div>
            <div class="EditorCampo">
                <label for="estado">Estado</label>
                <input id="estado" name="estado" type="text" placeholder="SP" maxlength="50"
                       value="<c:out value='${granjaSelecionada.estado}'/>" required>
            </div>
        </div>
        <footer class="EditorAcoes">
            <a href="${pageContext.request.contextPath}/granjas" class="EditorCancelar">Cancelar</a>
            <button type="submit" class="EditorSalvar">Salvar</button>
        </footer>
    </form>
</main>
<script>
    if (window.parent !== window) {
        document.body.classList.add('EditorEmModal');

        const urlLista = '${pageContext.request.contextPath}/granjas';

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