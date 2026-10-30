<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro | Ouros</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/Asa-icon.png">
    <script src="acesso.js" defer></script>
</head>
<body>
<main class="LoginPage LoginPage--cadastro">
    <div class="LoginShell">
        <section class="LoginMarca" aria-label="Sobre o Ouros">
            <div class="LoginLogo"><img src="${pageContext.request.contextPath}/img/Ouros.png" alt="Ouros"></div>
            <div class="LoginDivisor"></div>
            <p class="LoginTagline">Monitoramento inteligente para granjas mais <span class="destaque">eficientes.</span></p>
            <ul class="LoginRecursos">
                <li class="LoginRecursoItem"><span class="LoginRecursoIcone" aria-hidden="true"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2.5c-3.5 4-6 7.4-6 10.7a6 6 0 0 0 12 0c0-3.3-2.5-6.7-6-10.7Z"></path></svg></span><div><h2 class="LoginRecursoTitulo">Registros</h2><p class="LoginRecursoTexto">Acompanhe o histórico de água e energia.</p></div></li>
                <li class="LoginRecursoItem"><span class="LoginRecursoIcone" aria-hidden="true"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M8 21h8"></path><path d="M12 17v4"></path><path d="M7 4h10v5a5 5 0 0 1-10 0V4Z"></path><path d="M17 5h2a2 2 0 0 1 0 4h-1"></path><path d="M7 5H5a2 2 0 0 0 0 4h1"></path></svg></span><div><h2 class="LoginRecursoTitulo">Ranking</h2><p class="LoginRecursoTexto">Compare o desempenho da sua granja com outras.</p></div></li>
                <li class="LoginRecursoItem"><span class="LoginRecursoIcone" aria-hidden="true"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 3v18h18"></path><path d="M7 15l4-4 3 3 5-6"></path></svg></span><div><h2 class="LoginRecursoTitulo">Dashboards</h2><p class="LoginRecursoTexto">Visualize indicadores de consumo e desempenho.</p></div></li>
            </ul>
        </section>
        <section class="LoginFormulario" aria-labelledby="formulario-titulo">
            <header class="LoginCabecalho">
                <h1 class="LoginTitulo" id="formulario-titulo">Bem-vindo!</h1>
                <p class="LoginSubtitulo">Crie sua conta para ser um OUROS</p>
            </header>
            <form class="LoginFormEtapa" data-auth-form="cadastro-etapa1">
                <div class="LoginCampoGrupo"><label class="LoginCampoLabel" for="nome">Nome</label><input class="LoginCampoInput" type="text" id="nome" name="nome" placeholder="Seu nome completo" autocomplete="name" required></div>
                <div class="LoginCampoGrupo"><label class="LoginCampoLabel" for="email">Email</label><input class="LoginCampoInput" type="email" id="email" name="email" placeholder="seuemail@email.com" autocomplete="email" required></div>
                <div class="LoginCampoLinha">
                    <div class="LoginCampoGrupo"><label class="LoginCampoLabel" for="cnpj">CNPJ</label><input class="LoginCampoInput" type="text" id="cnpj" name="cnpj" placeholder="12.345.678/0001-90" inputmode="numeric" autocomplete="organization" maxlength="18" required></div>
                    <div class="LoginCampoGrupo"><label class="LoginCampoLabel" for="telefone">Telefone</label><input class="LoginCampoInput" type="tel" id="telefone" name="telefone" placeholder="(11) 99999-9999" inputmode="tel" autocomplete="tel" maxlength="15" required></div>
                </div>
                <div class="LoginCampoGrupo"><label class="LoginCampoLabel" for="senha">Senha</label><input class="LoginCampoInput" type="password" id="senha" name="senha" placeholder="Mínimo de 8 caracteres" autocomplete="new-password" minlength="8" required></div>
                <div class="LoginCampoGrupo"><label class="LoginCampoLabel" for="confirmarSenha">Confirmar Senha</label><input class="LoginCampoInput" type="password" id="confirmarSenha" name="confirmarSenha" placeholder="Repita sua senha" autocomplete="new-password" minlength="8" required></div>
                <p class="LoginAviso" role="status" aria-live="polite"></p>
                <button class="LoginBotao" type="submit">Continuar</button>
            </form>

            <div class="LoginRodape"><span>Já tem uma conta?</span><a class="LoginLinkEsqueci" href="WEB-INF/views/landing_page/acesso/login.html">Entrar</a></div>
        </section>
    </div>
</main>
</body>
</html>
