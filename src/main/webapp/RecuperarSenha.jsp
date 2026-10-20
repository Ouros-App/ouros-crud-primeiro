<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Esqueci minha senha</title>

    <base href="${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="Style.css?v=5">
    <link rel="icon" type="image/png" href="img/Asa-icon.png">
</head>
<body>

<div class="LoginPage">
    <div class="LoginShell">

        <div class="LoginMarca">
            <div class="LoginLogo"><img src="img/Ouros.png" alt="logo"></div>
            <div class="LoginDivisor"></div>
            <p class="LoginTagline">
                CRUD para monitoramento de dados de forma mais <span class="destaque">eficiente.</span>
            </p>

            <ul class="LoginRecursos">
                <li class="LoginRecursoItem">
                    <span class="LoginRecursoIcone">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
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
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
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
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
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

            <img class="MascoteSenha" src="img/midas.png" alt="mascote do Ouros">
        </div>

        <div class="LoginFormulario">
            <div class="LoginCabecalho">
                <h1 class="LoginTitulo">Esqueceu sua senha?</h1>
                <p class="LoginSubtitulo">Digite seu e-mail e enviaremos um link para redefinir sua senha</p>
            </div>


            <form action="RecuperarSenha" method="post">
                <div class="LoginCampoGrupo">
                    <label class="LoginCampoLabel" for="email">Email</label>
                    <input class="LoginCampoInput" type="email" id="email" name="email"
                           placeholder="seuemail@email.com" autocomplete="email" required>
                </div>

                <button type="submit" class="LoginBotao">Enviar link</button>

                <%-- mensagens vindas do servlet (?status=... e ?erro=...) --%>
                <c:if test="${param.status == 'enviado'}">
                    <p class="LoginSubtitulo" role="status">
                        Se esse e-mail estiver cadastrado, enviamos um link para redefinir sua senha.
                    </p>
                </c:if>

                <c:if test="${param.erro == 'email'}">
                    <p class="LoginErro" role="alert">Digite um e-mail válido.</p>
                </c:if>

                <c:if test="${param.erro == 'falha'}">
                    <p class="LoginErro" role="alert">
                        Não foi possível enviar agora. Tente novamente em instantes.
                    </p>
                </c:if>
            </form>

            <div class="LoginRodape">
                <a class="LoginLinkEsqueci" href="login.jsp">Lembrou sua senha? Voltar ao login</a>
            </div>
        </div>

    </div>
</div>

</body>
</html>
