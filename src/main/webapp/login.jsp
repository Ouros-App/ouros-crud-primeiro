<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Entrar | Ouros</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/Style.css">

    <link rel="icon"
          type="image/png"
          href="${pageContext.request.contextPath}/img/Asa-icon.png">

    <style>
        .LoginMensagem {
            display: none;
            box-sizing: border-box;
            width: 100%;
            margin-bottom: 20px;
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

        .LoginFormulario form {
            width: 100%;
        }

        .LoginFormulario .LoginCampoInput {
            box-sizing: border-box;
        }

        .LoginFormulario .LoginBotao:disabled {
            opacity: 0.7;
            cursor: wait;
        }
    </style>
</head>

<body>

<div class="LoginPage">
    <div class="LoginShell">

        <!-- Lado visual: identidade original do Ouros -->
        <div class="LoginMarca">

            <div class="LoginLogo">
                <img src="${pageContext.request.contextPath}/img/Ouros.png"
                     alt="Ouros">
            </div>

            <div class="LoginDivisor"></div>

            <p class="LoginTagline">
                Monitoramento inteligente para granjas mais
                <span class="destaque">eficientes.</span>
            </p>

            <ul class="LoginRecursos">

                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone" aria-hidden="true">
                        <svg viewBox="0 0 24 24"
                             fill="none"
                             stroke="currentColor"
                             stroke-width="2">
                            <path d="M12 2.5c-3.5 4-6 7.4-6 10.7a6 6 0 0 0 12 0c0-3.3-2.5-6.7-6-10.7Z"></path>
                        </svg>
                    </span>

                    <div>
                        <div class="LoginRecursoTitulo">Registros</div>
                        <div class="LoginRecursoTexto">
                            Acompanhe o histórico de água e energia.
                        </div>
                    </div>
                </li>

                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone" aria-hidden="true">
                        <svg viewBox="0 0 24 24"
                             fill="none"
                             stroke="currentColor"
                             stroke-width="2">
                            <path d="M8 21h8"></path>
                            <path d="M12 17v4"></path>
                            <path d="M7 4h10v5a5 5 0 0 1-10 0V4Z"></path>
                            <path d="M17 5h2a2 2 0 0 1 0 4h-1"></path>
                            <path d="M7 5H5a2 2 0 0 0 0 4h1"></path>
                        </svg>
                    </span>

                    <div>
                        <div class="LoginRecursoTitulo">Ranking</div>
                        <div class="LoginRecursoTexto">
                            Compare o desempenho da sua granja com outras.
                        </div>
                    </div>
                </li>

                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone" aria-hidden="true">
                        <svg viewBox="0 0 24 24"
                             fill="none"
                             stroke="currentColor"
                             stroke-width="2">
                            <path d="M3 3v18h18"></path>
                            <path d="M7 15l4-4 3 3 5-6"></path>
                        </svg>
                    </span>

                    <div>
                        <div class="LoginRecursoTitulo">Dashboards</div>
                        <div class="LoginRecursoTexto">
                            Visualize indicadores de consumo e desempenho.
                        </div>
                    </div>
                </li>

            </ul>

            <div class="MascoteLoginBox">
                <img class="MascoteLogin"
                     src="${pageContext.request.contextPath}/img/Midas-Feliz.png"
                     alt="Midas, mascote do Ouros">
            </div>

        </div>

        <!-- Formulário de login -->
        <div class="LoginFormulario">

            <div class="LoginCabecalho">
                <h1 class="LoginTitulo">Bem-vindo de volta!</h1>
                <p class="LoginSubtitulo">
                    Faça login para acessar sua conta
                </p>
            </div>

            <%
                String erro = request.getParameter("erro");
                String mensagem = null;
                String tipoMensagem = "erro";

                if ("credenciais".equals(erro)) {
                    mensagem = "E-mail ou senha incorretos. Verifique seus dados e tente novamente.";
                } else if ("naoverificado".equals(erro)) {
                    mensagem = "Seu e-mail ainda não foi verificado. Confira sua caixa de entrada e confirme sua conta.";
                } else if ("naoverificado".equals(request.getParameter("sucesso"))) {
                    mensagem = "Conta verificada com sucesso! Você já pode entrar.";
                    tipoMensagem = "sucesso";
                } else if ("verificado".equals(request.getParameter("sucesso"))) {
                    mensagem = "Conta verificada com sucesso! Você já pode entrar.";
                    tipoMensagem = "sucesso";
                }
            %>

            <% if (mensagem != null) { %>
                <div class="LoginMensagem <%= tipoMensagem %>"
                     role="alert"
                     aria-live="polite">
                    <%= mensagem %>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/login"
                  method="post"
                  id="formLogin">

                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="email">
                        Email
                    </label>

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
                    <label class="LoginCampoLabel" for="senha">
                        Senha
                    </label>

                    <input class="LoginCampoInput"
                           type="password"
                           id="senha"
                           name="senha"
                           placeholder="••••••••"
                           autocomplete="current-password"
                           required>
                </div>

                <div class="LoginUtilitario">
                    <a class="LoginLinkEsqueci"
                       href="${pageContext.request.contextPath}/RecuperarSenha.jsp">
                        Esqueceu sua senha?
                    </a>
                </div>

                <button type="submit"
                        class="LoginBotao"
                        id="botaoLogin">
                    Continuar
                </button>

            </form>

            <div class="LoginRodape">
                <span>Ainda não tem uma conta?</span>

                <a class="LoginLinkEsqueci"
                   href="${pageContext.request.contextPath}/cadastro">
                    Cadastre-se
                </a>
            </div>

        </div>

    </div>
</div>

<script>
    const formLogin = document.getElementById("formLogin");
    const botaoLogin = document.getElementById("botaoLogin");

    formLogin.addEventListener("submit", function () {
        if (!formLogin.checkValidity()) {
            return;
        }

        botaoLogin.disabled = true;
        botaoLogin.textContent = "Entrando...";
    });
</script>

</body>
</html>