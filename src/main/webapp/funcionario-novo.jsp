<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Novo Funcionário</title>
<link rel="stylesheet" href="Style.css?v=10">
<link rel="icon" href="img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
<header class="EditorCabecalho">
<h1 id="editorTitulo">Novo Funcionário</h1>
<a class="EditorFechar" href="funcionarios.jsp" aria-label="Fechar">×</a>
</header>
<form action="funcionarios" method="post" data-editor="funcionario">
<input type="hidden" name="acao" value="criar">
<input type="hidden" name="id" value="">
<div class="EditorGrid">
<div class="EditorCampo EditorCampo--full">
<label for="nome">Nome completo</label>
<input id="nome" name="nome" type="text" placeholder="Rafael Santos" autocomplete="name" maxlength="120" required>
</div>
<div class="EditorCampo">
<label for="cpf">CPF</label>
<input id="cpf" name="cpf" type="text" placeholder="000.000.000-00" inputmode="numeric" autocomplete="off" maxlength="14" aria-describedby="cpfAjuda">
<small class="EditorCampoAjuda" id="cpfAjuda">Digite os 11 números; a formatação será aplicada automaticamente.</small>
</div>
<div class="EditorCampo">
<label for="setor">Setor</label>
<input id="setor" name="setor" type="text" placeholder="Manejo" autocomplete="organization-title" maxlength="80">
</div>
<div class="EditorCampo">
<label for="email">E-mail</label>
<input id="email" name="email" type="email" placeholder="exemplo@gmail.com" autocomplete="email" maxlength="254" required>
</div>
<div class="EditorCampo">
<label for="telefone">Telefone</label>
<input id="telefone" name="telefone" type="tel" placeholder="+55 11 12345-6789" autocomplete="tel" inputmode="tel" maxlength="20">
</div>
</div>
<footer class="EditorAcoes">
<a href="funcionarios.jsp" class="EditorCancelar">Cancelar</a>
<button type="submit" class="EditorSalvar">Salvar</button>
</footer>
<p class="EditorAviso" role="status" hidden>
</p>
</form>
</main>
<script>
(function () {
    var form = document.querySelector('[data-editor="funcionario"]');
    if (!form) return;

    var cpf = form.elements.namedItem("cpf");
    var aviso = form.querySelector(".EditorAviso");
    var editando = new URLSearchParams(location.search).get("acao") === "editar";

    function formatarCpf(valor) {
        var numeros = valor.replace(/\D/g, "").slice(0, 11);
        return numeros
            .replace(/^(\d{3})(\d)/, "$1.$2")
            .replace(/^(\d{3})\.(\d{3})(\d)/, "$1.$2.$3")
            .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    }

    function cpfValido(valor) {
        var numeros = valor.replace(/\D/g, "");
        if (numeros.length !== 11 || /^(\d)\1{10}$/.test(numeros)) return false;

        for (var digito = 9; digito <= 10; digito += 1) {
            var soma = 0;
            for (var indice = 0; indice < digito; indice += 1) {
                soma += Number(numeros[indice]) * (digito + 1 - indice);
            }
            var verificador = (soma * 10) % 11;
            if (verificador === 10) verificador = 0;
            if (verificador !== Number(numeros[digito])) return false;
        }
        return true;
    }

    if (editando) {
        document.title = "Editar Funcionário";
        document.getElementById("editorTitulo").textContent = document.title;
        form.elements.acao.value = "editar";
        try {
            var dados = JSON.parse(sessionStorage.getItem("ouros-editor-funcionario") || "{}");
            ["nome", "cpf", "setor", "email", "telefone"].forEach(function (campo) {
                var entrada = form.elements.namedItem(campo);
                if (entrada && dados[campo] != null) entrada.value = dados[campo];
            });
        } catch (erro) {
            // O formulário continua disponível para edição manual.
        }
    }

    cpf.value = formatarCpf(cpf.value);
    cpf.addEventListener("input", function () {
        cpf.value = formatarCpf(cpf.value);
        cpf.setCustomValidity("");
        cpf.removeAttribute("aria-invalid");
        aviso.hidden = true;
    });

    form.addEventListener("submit", function (evento) {
        var numeros = cpf.value.replace(/\D/g, "");
        if (numeros && !cpfValido(numeros)) {
            evento.preventDefault();
            cpf.setCustomValidity("Informe um CPF válido com 11 números.");
            cpf.setAttribute("aria-invalid", "true");
            cpf.reportValidity();
            return;
        }

        if (editando && !form.elements.id.value) {
            evento.preventDefault();
            aviso.hidden = false;
            aviso.textContent = "Não foi possível salvar a edição sem o identificador do funcionário.";
            return;
        }

        cpf.value = numeros;
    });
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
