<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!-- Formulário de nova granja. -->
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Nova Granja</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<!-- Área principal da tela. -->
<main class="EditorCartao" aria-labelledby="editorTitulo">
    <header class="EditorCabecalho">
        <h1 id="editorTitulo">Nova Granja</h1>
        <a class="EditorFechar" href="${pageContext.request.contextPath}/granjas" aria-label="Fechar">×</a>
    </header>
    <!-- Campos e ações da tela. -->
<form action="${pageContext.request.contextPath}/granjas" method="post" target="_top">
        <input type="hidden" name="acao" value="inserir">
        <div class="EditorGrid">
            <div class="EditorCampo EditorCampo--full">
                <label for="nome">Nome da Granja</label>
                <input id="nome" name="nome" type="text" placeholder="Granja Ouro Branco" required>
            </div>
            <div class="EditorCampo">
                <label for="capacidade">Capacidade de aves</label>
                <input id="capacidade" name="capacidade" type="number" placeholder="5000" min="1" step="1" required>
            </div>
            <div class="EditorCampo">
                <label for="area">Área da propriedade (HA)</label>
                <input id="area" name="area" type="number" placeholder="2" min="0" step="1" required>
            </div>
            <div class="EditorCampo">
                <label for="regiao">Região</label>
                <select id="regiao" name="regiao" required>
                    <option>Sudeste</option>
                    <option>Sul</option>
                    <option>Centro-Oeste</option>
                    <option>Nordeste</option>
                    <option>Norte</option>
                </select>
            </div>
            <div class="EditorCampo">
                <label for="empresa">Empresa</label>
                <select id="empresa" name="empresa" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="e" items="${empresas}">
                        <option value="${e.id}"><c:out value="${e.nome}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="EditorCampo">
                <label for="cep">CEP</label>
                <input id="cep" name="cep" type="text" placeholder="00000-000" maxlength="9" required>
            </div>
            <div class="EditorCampo">
                <label for="numero">Número</label>
                <input id="numero" name="numero" type="number" placeholder="480" min="0" step="1" required>
            </div>
            <div class="EditorCampo">
                <label for="municipio">Município</label>
                <input id="municipio" name="municipio" type="text" placeholder="Arujá" maxlength="100" required>
            </div>
            <div class="EditorCampo">
                <label for="estado">Estado</label>
                <input id="estado" name="estado" type="text" placeholder="SP" maxlength="50" required>
            </div>
            <div class="EditorCampo EditorCampo--full">
                <label for="logradouro">Logradouro</label>
                <input id="logradouro" name="logradouro" type="text" placeholder="Estrada Rural, km 12" maxlength="150" required>
            </div>
        </div>
        <footer class="EditorAcoes">
            <a href="${pageContext.request.contextPath}/granjas" class="EditorCancelar">Cancelar</a>
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