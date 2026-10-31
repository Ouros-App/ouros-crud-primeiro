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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/Asa-icon.png">

    <style>

        html,
        body,
        .LoginFormulario {
            scrollbar-width: none;
            -ms-overflow-style: none;
        }

        html::-webkit-scrollbar,
        body::-webkit-scrollbar,
        .LoginFormulario::-webkit-scrollbar {
            display: none;
        }

        body {
            overflow-x: hidden;
        }

        .LoginFormulario {
            position: relative;
            overflow-y: auto;
            overflow-x: hidden;
        }

        .LoginMensagem {
            display: none;
            width: min(490px, 100%);
            box-sizing: border-box;
            margin: 0 0 18px;
            padding: 12px 15px;
            border-radius: 8px;
            font-size: 13px;
            line-height: 1.6;
            overflow-wrap: anywhere;
        }

        .LoginMensagem.erro {
            display: block;
            color: #a52828;
            background: #fff0f0;
            border: 1px solid #efcaca;
        }

        .LoginMensagem.sucesso {
            display: block;
            color: #176b3a;
            background: #edfff3;
            border: 1px solid #bde5ca;
        }

        .LoginFormulario .LoginCabecalho {
            position: relative;
            display: flex;
            align-items: center;
            width: 100%;
            box-sizing: border-box;
        }

        .LoginFormulario .LoginCabecalho > div {
            width: min(490px, 100%);
            margin: 0 auto;
        }

        .LoginVoltar {
            position: absolute;
            top: 50%;
            left: 28px;
            transform: translateY(-50%);
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            color: inherit;
            text-decoration: none;
            border-radius: 10px;
            transition: background 0.2s;
        }

        .LoginVoltar:hover,
        .LoginVoltar:focus-visible {
            background: rgba(0, 0, 0, 0.07);
        }

        .LoginVoltar svg {
            width: 24px;
            height: 24px;
            transition: transform 0.2s;
        }

        .LoginVoltar:hover svg {
            transform: translateX(-3px);
        }

        @media (max-width: 860px) {
            .LoginVoltar {
                left: 12px;
            }

            .LoginFormulario .LoginCabecalho > div {
                padding-left: 48px;
                box-sizing: border-box;
            }
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
</head>

<body>
<!-- Área principal da tela. -->

<main class="LoginPage">
    <div class="LoginShell">

        <section class="LoginMarca" aria-label="Sobre o Ouros">
            <div class="LoginLogo">
                <img src="${pageContext.request.contextPath}/img/Ouros.png" alt="Ouros">
            </div>

            <div class="LoginDivisor"></div>

            <p class="LoginTagline">
                Monitoramento inteligente para granjas mais
                <span class="destaque">eficientes.</span>
            </p>

            <ul class="LoginRecursos">
                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone" aria-hidden="true">
                        <svg viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2">
                            <path d="M12 2.5c-3.5 4-6 7.4-6 10.7a6 6 0 0 0 12 0c0-3.3-2.5-6.7-6-10.7Z"></path>
                        </svg>
                    </span>
                    <div>
                        <h2 class="LoginRecursoTitulo">Registros</h2>
                        <p class="LoginRecursoTexto">
                            Acompanhe o histórico de água e energia.
                        </p>
                    </div>
                </li>

                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone" aria-hidden="true">
                        <svg viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2">
                            <path d="M8 21h8"></path>
                            <path d="M12 17v4"></path>
                            <path d="M7 4h10v5a5 5 0 0 1-10 0V4Z"></path>
                            <path d="M17 5h2a2 2 0 0 1 0 4h-1"></path>
                            <path d="M7 5H5a2 2 0 0 0 0 4h1"></path>
                        </svg>
                    </span>
                    <div>
                        <h2 class="LoginRecursoTitulo">Ranking</h2>
                        <p class="LoginRecursoTexto">
                            Compare o desempenho da sua granja com outras.
                        </p>
                    </div>
                </li>

                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone" aria-hidden="true">
                        <svg viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2">
                            <path d="M3 3v18h18"></path>
                            <path d="M7 15l4-4 3 3 5-6"></path>
                        </svg>
                    </span>
                    <div>
                        <h2 class="LoginRecursoTitulo">Dashboards</h2>
                        <p class="LoginRecursoTexto">
                            Visualize indicadores de consumo e desempenho.
                        </p>
                    </div>
                </li>
            </ul>

           <img class="MascoteLogin"
                src="${pageContext.request.contextPath}/img/Midas-Feliz.png"
                alt="Midas, mascote do Ouros">
        </section>

        <section class="LoginFormulario" aria-labelledby="cadastro-titulo">
        <a href="${pageContext.request.contextPath}/inicio"
           class="BotaoVoltar"
           title="Voltar ao início"
           aria-label="Voltar ao início">

            <svg viewBox="0 0 24 24"
                 fill="none"
                 stroke="currentColor"
                 stroke-width="2"
                 stroke-linecap="round"
                 stroke-linejoin="round">

                <path d="M19 12H5"></path>
                <path d="M12 19l-7-7 7-7"></path>
            </svg>

        </a>

            <header class="LoginCabecalho">
                <a href="${pageContext.request.contextPath}/inicio"
                   class="LoginVoltar"
                   aria-label="Voltar para o início">
                    <svg viewBox="0 0 24 24"
                         fill="none"
                         stroke="currentColor"
                         stroke-width="2"
                         stroke-linecap="round"
                         stroke-linejoin="round"
                         aria-hidden="true">
                        <path d="M19 12H5"></path>
                        <path d="M12 19l-7-7 7-7"></path>
                    </svg>
                </a>

                <div>
                    <h1 class="LoginTitulo" id="cadastro-titulo">Bem-vindo!</h1>
                    <p class="LoginSubtitulo">Crie sua conta de administrador</p>
                </div>
            </header>

            <div id="mensagemCadastro"
                 class="LoginMensagem"
                 role="alert"
                 aria-live="polite"></div>

            <!-- Campos e ações da tela. -->
<form action="${pageContext.request.contextPath}/cadastro"
                  method="post"
                  id="formCadastro">

                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="nome">Nome</label>
                    <input class="LoginCampoInput"
                           type="text"
                           id="nome"
                           name="nome"
                           placeholder="Seu nome completo"
                           autocomplete="name"
                           maxlength="150"
                           required>
                </div>

                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="cpf">CPF</label>
                    <input class="LoginCampoInput"
                           type="text"
                           id="cpf"
                           name="cpf"
                           placeholder="000.000.000-00"
                           autocomplete="off"
                           inputmode="numeric"
                           maxlength="14"
                           required>
                </div>

                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="telefone">
                        Telefone
                    </label>
                    <input class="LoginCampoInput"
                           type="tel"
                           id="telefone"
                           name="telefone"
                           placeholder="(11) 99999-9999"
                           autocomplete="tel"
                           inputmode="tel"
                           maxlength="20"
                           required>
                </div>

                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="email">Email</label>
                    <input class="LoginCampoInput"
                           type="email"
                           id="email"
                           name="email"
                           placeholder="seuemail@email.com"
                           autocomplete="email"
                           maxlength="254"
                           required>
                </div>

                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="senha">Senha</label>
                    <input class="LoginCampoInput"
                           type="password"
                           id="senha"
                           name="senha"
                           placeholder="Mínimo de 8 caracteres"
                           autocomplete="new-password"
                           minlength="8"
                           required>
                </div>

                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="confirmarSenha">
                        Confirmar Senha
                    </label>
                    <input class="LoginCampoInput"
                           type="password"
                           id="confirmarSenha"
                           name="confirmarSenha"
                           placeholder="Repita sua senha"
                           autocomplete="new-password"
                           minlength="8"
                           required>
                </div>

                <button type="submit" class="LoginBotao" id="botaoCadastro">
                    Continuar
                </button>
            </form>

        </section>
    </div>
</main>

<!-- Comportamentos da tela. -->
<script>
    const formularioCadastro = document.getElementById("formCadastro");
    const senhaCadastro = document.getElementById("senha");
    const confirmacaoSenha = document.getElementById("confirmarSenha");
    const campoCpf = document.getElementById("cpf");
    const campoTelefone = document.getElementById("telefone");
    const mensagemCadastro = document.getElementById("mensagemCadastro");
    const botaoCadastro = document.getElementById("botaoCadastro");

    function validarConfirmacaoSenha() {
        if (confirmacaoSenha.value &&
            confirmacaoSenha.value !== senhaCadastro.value) {
            confirmacaoSenha.setCustomValidity(
                "As senhas precisam ser iguais."
            );
        } else {
            confirmacaoSenha.setCustomValidity("");
        }
    }

    function formatarCpf(valor) {
        const numeros = valor.replace(/\D/g, "").slice(0, 11);

        return numeros
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    }

    function formatarTelefone(valor) {
        const numeros = valor.replace(/\D/g, "").slice(0, 11);

        if (numeros.length <= 2) {
            return numeros.length ? "(" + numeros : "";
        }

        if (numeros.length <= 10) {
            return numeros.replace(
                /^(\d{2})(\d{0,4})(\d{0,4}).*/,
                function (_, ddd, parte1, parte2) {
                    return "(" + ddd + ") " + parte1 +
                        (parte2 ? "-" + parte2 : "");
                }
            );
        }

        return numeros.replace(
            /^(\d{2})(\d{5})(\d{0,4}).*/,
            function (_, ddd, parte1, parte2) {
                return "(" + ddd + ") " + parte1 +
                    (parte2 ? "-" + parte2 : "");
            }
        );
    }

    function mostrarMensagem(texto, tipo) {
        mensagemCadastro.textContent = texto;
        mensagemCadastro.className = "LoginMensagem " + tipo;
    }

    campoCpf.addEventListener("input", function () {
        campoCpf.value = formatarCpf(campoCpf.value);
    });

    campoTelefone.addEventListener("input", function () {
        campoTelefone.value = formatarTelefone(campoTelefone.value);
    });

    senhaCadastro.addEventListener("input", validarConfirmacaoSenha);
    confirmacaoSenha.addEventListener("input", validarConfirmacaoSenha);

    formularioCadastro.addEventListener("submit", async function (evento) {
        evento.preventDefault();

        validarConfirmacaoSenha();

        if (!formularioCadastro.checkValidity()) {
            formularioCadastro.reportValidity();
            return;
        }

        mensagemCadastro.className = "LoginMensagem";
        botaoCadastro.disabled = true;
        botaoCadastro.textContent = "Enviando...";

        try {
            const resposta = await fetch(formularioCadastro.getAttribute("action"), {
                method: "POST",
                body: new URLSearchParams(new FormData(formularioCadastro)),
                headers: {
                    "Accept": "text/plain",
                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
                }
            });

            const texto = await resposta.text();

            if (!resposta.ok) {
                mostrarMensagem(
                    texto || "Não foi possível realizar o cadastro.",
                    "erro"
                );
                return;
            }

            mostrarMensagem(
                texto || "Administrador cadastrado com sucesso!",
                "sucesso"
            );

            formularioCadastro.reset();

        } catch (erro) {
            mostrarMensagem(
                "Não foi possível conectar ao servidor. Tente novamente.",
                "erro"
            );
        } finally {
            botaoCadastro.disabled = false;
            botaoCadastro.textContent = "Continuar";
        }
    });
</script>

</body>
</html>
