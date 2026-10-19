<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cadastro de Proprietário</title>
    <link rel="stylesheet" href="Style.css?v=6">
    <link rel="icon" href="img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
    <header class="EditorCabecalho">
        <h1 id="editorTitulo">Cadastro de Proprietário</h1>
    </header>
    <p class="CadastroIntroducao">Preencha seus dados para criar o acesso e vinculá-lo à sua granja.</p>
    <form action="cadastro" method="post" data-cadastro="proprietario">
        <div class="EditorGrid">
            <div class="EditorCampo EditorCampo--full">
                <label for="nome">Nome completo</label>
                <input type="text" id="nome" name="nome" autocomplete="name" maxlength="120" placeholder="Seu nome completo" required>
            </div>
            <div class="EditorCampo">
                <label for="cpf">CPF</label>
                <input type="text" id="cpf" name="cpf" inputmode="numeric" autocomplete="off" maxlength="14" pattern="[0-9]{3}\.[0-9]{3}\.[0-9]{3}-[0-9]{2}" title="Digite um CPF válido." placeholder="000.000.000-00" aria-describedby="cpfAjuda" required>
                <small class="EditorCampoAjuda" id="cpfAjuda">Digite os 11 números; a formatação é aplicada automaticamente.</small>
            </div>
            <div class="EditorCampo">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" autocomplete="email" maxlength="254" pattern="[\w.+-]+@[\w-]+(\.[\w-]+)*\.[A-Za-z]{2,}" placeholder="voce@exemplo.com" required>
            </div>
            <div class="EditorCampo">
                <label for="telefone">Telefone</label>
                <input type="tel" id="telefone" name="telefone" inputmode="tel" autocomplete="tel" maxlength="15" pattern="\([0-9]{2}\) 9?[0-9]{4}-[0-9]{4}" placeholder="(11) 91234-5678" aria-describedby="telefoneAjuda" required>
                <small class="EditorCampoAjuda" id="telefoneAjuda">Informe o DDD e um telefone com 10 ou 11 números.</small>
            </div>
            <div class="EditorCampo">
                <label for="idGranja">ID da Granja</label>
                <input type="number" id="idGranja" name="idGranja" min="1" max="2147483647" step="1" inputmode="numeric" placeholder="1" aria-describedby="granjaAjuda" required>
                <small class="EditorCampoAjuda" id="granjaAjuda">Use o identificador da granja ao qual seu acesso será vinculado.</small>
            </div>
            <div class="EditorCampo EditorCampo--full">
                <label for="senha">Senha</label>
                <div class="CadastroSenhaControle">
                    <input type="password" id="senha" name="senha" autocomplete="new-password" minlength="8" aria-describedby="senhaAjuda" required>
                    <button class="CadastroSenhaToggle" type="button" aria-label="Mostrar senha" aria-controls="senha" aria-pressed="false">Mostrar</button>
                </div>
                <small class="EditorCampoAjuda" id="senhaAjuda">Mínimo de 8 caracteres, com maiúscula, minúscula, número e símbolo.</small>
            </div>
        </div>
        <footer class="EditorAcoes CadastroAcoes">
            <a href="login.jsp" class="EditorCancelar">Voltar ao login</a>
            <button type="submit" class="EditorSalvar">Criar cadastro</button>
        </footer>
    </form>
</main>
<script>
(() => {
    const form = document.querySelector('[data-cadastro="proprietario"]');
    if (!form) return;

    const cpf = form.elements.namedItem('cpf');
    const telefone = form.elements.namedItem('telefone');
    const senha = form.elements.namedItem('senha');
    const alternarSenha = form.querySelector('.CadastroSenhaToggle');

    function formatarCpf(valor) {
        const numeros = valor.replace(/\D/g, '').slice(0, 11);
        return numeros
            .replace(/^(\d{3})(\d)/, '$1.$2')
            .replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3')
            .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    }

    function cpfValido(valor) {
        const numeros = valor.replace(/\D/g, '');
        if (numeros.length !== 11 || /^(\d)\1{10}$/.test(numeros)) return false;
        for (let digito = 9; digito <= 10; digito += 1) {
            let soma = 0;
            for (let indice = 0; indice < digito; indice += 1) {
                soma += Number(numeros[indice]) * (digito + 1 - indice);
            }
            let verificador = (soma * 10) % 11;
            if (verificador === 10) verificador = 0;
            if (verificador !== Number(numeros[digito])) return false;
        }
        return true;
    }

    function formatarTelefone(valor) {
        let numeros = valor.replace(/\D/g, '');
        if (/^\s*\+55/.test(valor)) numeros = numeros.slice(2);
        numeros = numeros.slice(0, 11);
        if (!numeros) return '';
        if (numeros.length <= 2) return '(' + numeros;
        const ddd = numeros.slice(0, 2);
        const telefoneLocal = numeros.slice(2);
        if (telefoneLocal.length <= 4) return '(' + ddd + ') ' + telefoneLocal;
        const tamanhoPrefixo = numeros.length > 10 ? 5 : 4;
        return '(' + ddd + ') ' + telefoneLocal.slice(0, tamanhoPrefixo) + '-' + telefoneLocal.slice(tamanhoPrefixo);
    }

    cpf.addEventListener('input', () => {
        cpf.value = formatarCpf(cpf.value);
        cpf.setCustomValidity('');
    });
    telefone.addEventListener('input', () => {
        telefone.value = formatarTelefone(telefone.value);
        telefone.setCustomValidity('');
    });
    senha.addEventListener('input', () => senha.setCustomValidity(''));

    alternarSenha.addEventListener('click', () => {
        const visivel = senha.type === 'password';
        senha.type = visivel ? 'text' : 'password';
        alternarSenha.textContent = visivel ? 'Ocultar' : 'Mostrar';
        alternarSenha.setAttribute('aria-label', visivel ? 'Ocultar senha' : 'Mostrar senha');
        alternarSenha.setAttribute('aria-pressed', String(visivel));
        senha.focus();
    });

    form.addEventListener('submit', evento => {
        if (!cpfValido(cpf.value)) {
            evento.preventDefault();
            cpf.setCustomValidity('Informe um CPF válido com 11 números.');
            cpf.reportValidity();
            return;
        }
        if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!._*-]).{8,}$/.test(senha.value)) {
            evento.preventDefault();
            senha.setCustomValidity('Use ao menos 8 caracteres, incluindo maiúscula, minúscula, número e símbolo.');
            senha.reportValidity();
        }
    });
})();
</script>
</body>
</html>
