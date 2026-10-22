<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro | Ouros</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="Style.css">
    <style>
        .LoginFormulario > .LoginGoogle {
            width: min(490px, 100%);
            max-width: 490px;
            height: 56px;
            flex: 0 0 56px;
            box-sizing: border-box;
        }

        @media (min-width: 861px) and (max-height: 820px) {
            .LoginFormulario {
                justify-content: flex-start;
                padding-top: 20px;
                padding-bottom: 20px;
            }

            .LoginFormulario > * {
                flex-shrink: 0;
            }

            .LoginFormulario .LoginCabecalho {
                margin-bottom: 22px;
            }

            .LoginFormulario > form .LoginCampoGrupo {
                margin-bottom: 16px;
            }

            .LoginFormulario > form .LoginCampoGrupo:last-of-type {
                margin-bottom: 14px;
            }

            .LoginFormulario .LoginSeparador {
                margin: 14px auto 20px;
            }

            .LoginFormulario .LoginRodape {
                margin-top: 12px;
            }
        }
    </style>
    <link rel="icon" type="image/png" href="img/Asa-icon.png">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>

<main class="LoginPage">
    <div class="LoginShell">
        <section class="LoginMarca" aria-label="Sobre o Ouros">
            <div class="LoginLogo"><img src="img/Ouros.png" alt="Ouros"></div>
            <div class="LoginDivisor"></div>
            <p class="LoginTagline">
                Monitoramento inteligente para granjas mais <span class="destaque">eficientes.</span>
            </p>

            <ul class="LoginRecursos">
                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone" aria-hidden="true">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M12 2.5c-3.5 4-6 7.4-6 10.7a6 6 0 0 0 12 0c0-3.3-2.5-6.7-6-10.7Z"></path>
                        </svg>
                    </span>
                    <div>
                        <h2 class="LoginRecursoTitulo">Registros</h2>
                        <p class="LoginRecursoTexto">Acompanhe o histórico de água e energia.</p>
                    </div>
                </li>
                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone" aria-hidden="true">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M8 21h8"></path><path d="M12 17v4"></path>
                            <path d="M7 4h10v5a5 5 0 0 1-10 0V4Z"></path>
                            <path d="M17 5h2a2 2 0 0 1 0 4h-1"></path><path d="M7 5H5a2 2 0 0 0 0 4h1"></path>
                        </svg>
                    </span>
                    <div>
                        <h2 class="LoginRecursoTitulo">Ranking</h2>
                        <p class="LoginRecursoTexto">Compare o desempenho da sua granja com outras.</p>
                    </div>
                </li>
                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone" aria-hidden="true">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M3 3v18h18"></path><path d="M7 15l4-4 3 3 5-6"></path>
                        </svg>
                    </span>
                    <div>
                        <h2 class="LoginRecursoTitulo">Dashboards</h2>
                        <p class="LoginRecursoTexto">Visualize indicadores de consumo e desempenho.</p>
                    </div>
                </li>
            </ul>

            <img class="MascoteLogin" src="img/Midas-Feliz.png" alt="Midas, mascote do Ouros">
        </section>

        <section class="LoginFormulario" aria-labelledby="cadastro-titulo">
            <header class="LoginCabecalho">
                <h1 class="LoginTitulo" id="cadastro-titulo">Bem-vindo!</h1>
                <p class="LoginSubtitulo">Crie sua conta para ser um OUROS</p>
            </header>

            <form action="Cadastro.jsp" method="post" id="formCadastro">
                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="nome">Nome</label>
                    <input class="LoginCampoInput" type="text" id="nome" name="nome" placeholder="Seu nome completo" autocomplete="name" required>
                </div>
                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="email">Email</label>
                    <input class="LoginCampoInput" type="email" id="email" name="email" placeholder="seuemail@email.com" autocomplete="email" required>
                </div>
                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="senha">Senha</label>
                    <input class="LoginCampoInput" type="password" id="senha" name="senha" placeholder="Mínimo de 8 caracteres" autocomplete="new-password" minlength="8" required>
                </div>
                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="confirmarSenha">Confirmar Senha</label>
                    <input class="LoginCampoInput" type="password" id="confirmarSenha" name="confirmarSenha" placeholder="Repita sua senha" autocomplete="new-password" minlength="8" required>
                </div>

                <button type="submit" class="LoginBotao">Continuar</button>
            </form>

            <div class="LoginSeparador" aria-hidden="true">
                <span class="LoginSeparadorLinha"></span>
                <span>ou</span>
                <span class="LoginSeparadorLinha"></span>
            </div>

            <button type="button" class="LoginGoogle" aria-label="Entrar com Google">
                <span class="LoginGoogleIcone" aria-hidden="true"></span>
                Entrar com Google
            </button>

            <div class="LoginRodape">
                <span>Já tem uma conta?</span>
                <a class="LoginLinkEsqueci" href="login.jsp">Entrar</a>
            </div>
        </section>
    </div>
</main>

<script>
    const formularioCadastro = document.getElementById("formCadastro");
    const senhaCadastro = document.getElementById("senha");
    const confirmacaoSenha = document.getElementById("confirmarSenha");

    function validarConfirmacaoSenha() {
        if (confirmacaoSenha.value && confirmacaoSenha.value !== senhaCadastro.value) {
            confirmacaoSenha.setCustomValidity("As senhas precisam ser iguais.");
        } else {
            confirmacaoSenha.setCustomValidity("");
        }
    }

    senhaCadastro.addEventListener("input", validarConfirmacaoSenha);
    confirmacaoSenha.addEventListener("input", validarConfirmacaoSenha);
    formularioCadastro.addEventListener("submit", function (evento) {
        validarConfirmacaoSenha();

        if (!formularioCadastro.checkValidity()) {
            evento.preventDefault();
            formularioCadastro.reportValidity();
        }
    });
</script>

</body>
</html>
