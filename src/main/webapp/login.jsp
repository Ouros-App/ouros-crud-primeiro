<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="Style.css?v=4">
    <link rel="icon" type="image/png" href="img/granja.svg">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>

<div class="LoginPage">
    <div class="LoginShell">

        <div class="LoginMarca">
            <link rel="icon" type="image/png" href="img/Asa-icon.png">
            <div class="LoginDivisor"></div>
            <p class="LoginTagline">
                CRUD para monitoramento de dados de forma mais <span class="destaque">eficiente.</span>
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

            <div class="MascoteLoginBox">
                <img class="MascoteLogin" src="img/Midas-Feliz.png" alt="mascote do Ouros">
            </div>
        </div>

        <div class="LoginFormulario">
            <div class="LoginCabecalho">
                <h1 class="LoginTitulo">Bem-vindo de volta !</h1>
                <p class="LoginSubtitulo">Faça login para acessar sua conta</p>
            </div>

            <form action="login.jsp" method="post">
                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="email">Email</label>
                    <input class="LoginCampoInput" type="email" id="email" name="email" placeholder="seuemail@email.com" required>
                </div>

                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="senha">Senha</label>
                    <input class="LoginCampoInput" type="password" id="senha" name="senha" placeholder="••••••••" required>
                </div>

                <div class="LoginUtilitario">
                    <a class="LoginLinkEsqueci" href="RecuperarSenha.jsp">Esqueceu sua senha?</a>
                </div>

                <button type="submit" class="LoginBotao">Continuar</button>
            </form>
        </div>

    </div>
</div>

</body>
</html>
