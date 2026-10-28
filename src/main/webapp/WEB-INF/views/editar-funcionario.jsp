<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <form action="${pageContext.request.contextPath}/funcionarios" method="post" target="_top" data-editor="funcionario">
        <input type="hidden" name="acao" value="editar">
        <input type="hidden" name="id" value="${funcionarios.id}">
        <div class="EditorGrid">
            <div class="EditorCampo EditorCampo--full">
                <label for="nome">Nome completo</label>
                <input id="nome" name="nome" type="text" placeholder="Rafael Santos" value="${funcionarios.nome}"  required>
            </div>
            <div class="EditorCampo">
                <label for="cpf">CPF</label>
                <input id="cpf" name="cpf" type="text" placeholder="000.000.000-00" value="${funcionarios.cpf}" >
            </div>
            <div class="EditorCampo">
                <label for="setor">Setor</label>
                <input id="setor" name="setor" type="text" placeholder="Manejo" value="${funcionarios.setor}" >
            </div>
            <div class="EditorCampo">
                <label for="email">E-mail</label>
                <input id="email" name="email" type="email" placeholder="exemplo@gmail.com" value="${funcionarios.email}" required>
            </div>
            <div class="EditorCampo">
                <label for="telefone">Telefone</label>
                <input id="telefone" name="telefone" type="tel" placeholder="11 12345-6789" value="${funcionarios.telefone}" >
            </div>
            <div class="EditorCampo">
                <label for="empresa">Empresa</label>
                <input id="empresa" name="empresa" type="text" placeholder="11 12345-6789" value="${funcionarios.nomeEmpresa}" >
            </div>
        </div>
        <footer class="EditorAcoes">
            <a href="${pageContext.request.contextPath}/funcionarios" class="EditorCancelar">Cancelar</a>
            <button type="submit" class="EditorSalvar">Salvar</button>
        </footer>
        <p class="EditorAviso" role="status" hidden>
        </p>
    </form>
</main>
<script>
    if (window.parent !== window) {
        document.body.classList.add('EditorEmModal');

        const urlLista = '${pageContext.request.contextPath}/funcionarios';

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
