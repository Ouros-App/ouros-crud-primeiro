<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recuperação de conta | Ouros</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body>

<div class="LoginPage">
    <div class="LoginShell">

        <div class="LoginMarca">
            <div class="LoginLogo"><img src="${pageContext.request.contextPath}/img/Ouros.png" alt="logo"></div>
            <div class="LoginDivisor"></div>
            <p class="LoginTagline">
                Monitoramento inteligente para granjas mais <span class="destaque">eficientes.</span>
            </p>

            <ul class="LoginRecursos">
                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M12 2.5c-3.5 4-6 7.4-6 10.7a6 6 0 0 0 12 0c0-3.3-2.5-6.7-6-10.7Z"></path>
                        </svg>
                    </span>
                    <div>
                        <div class="LoginRecursoTitulo">Registros</div>
                        <div class="LoginRecursoTexto">Acompanhe o histórico de água e energia.</div>
                    </div>
                </li>

                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M8 21h8"></path>
                            <path d="M12 17v4"></path>
                            <path d="M7 4h10v5a5 5 0 0 1-10 0V4Z"></path>
                            <path d="M17 5h2a2 2 0 0 1 0 4h-1"></path>
                            <path d="M7 5H5a2 2 0 0 0 0 4h1"></path>
                        </svg>
                    </span>
                    <div>
                        <div class="LoginRecursoTitulo">Ranking</div>
                        <div class="LoginRecursoTexto">Compare o desempenho da sua granja com outras.</div>
                    </div>
                </li>

                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M3 3v18h18"></path>
                            <path d="M7 15l4-4 3 3 5-6"></path>
                        </svg>
                    </span>
                    <div>
                        <div class="LoginRecursoTitulo">Dashboards</div>
                        <div class="LoginRecursoTexto">Visualize indicadores de consumo e desempenho.</div>
                    </div>
                </li>
            </ul>

            <img class="MascoteLogin" src="${pageContext.request.contextPath}/img/Midas-Feliz.png" alt="Midas, mascote do Ouros">
        </div>

        <div class="LoginFormulario">
            <div class="LoginCabecalho">
                <h1 class="LoginTitulo">Recuperação de Conta</h1>
                <p class="LoginSubtitulo">Redefina sua senha para continuar sendo um OUROS</p>
            </div>

            <form action="${pageContext.request.contextPath}/RecuperarSenha.jsp" method="post">
                <h2 class="LoginPergunta">Esqueceu sua senha?</h2>
                <p class="LoginInstrucao">Digite seu e-mail e enviaremos um link para redefinir sua senha.</p>

                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="email">Email</label>
                    <input class="LoginCampoInput" type="email" id="email" name="email" placeholder="seuemail@email.com" required>
                </div>

                <button type="submit" class="LoginBotao">Enviar link</button>
            </form>

            <div class="LoginRodape">
                <span>Lembrou sua senha?</span>
                <a class="LoginLinkEsqueci" href="${pageContext.request.contextPath}/login.jsp">Voltar ao login</a>
            </div>
        </div>

    </div>
</div>

</body>
</html>
