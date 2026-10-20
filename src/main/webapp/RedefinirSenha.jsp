<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Redefinir senha | Ouros</title>

    <base href="${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="Style.css?v=8">
    <link rel="icon" type="image/png" href="img/Asa-icon.png">
</head>
<body>
<div class="LoginPage">
    <div class="LoginShell">

        <div class="LoginMarca">
            <div class="LoginLogo"><img src="img/Ouros.png" alt="Ouros"></div>
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

            <img class="MascoteSenha" src="img/midas.png" alt="Midas, mascote do Ouros">
        </div>

        <main class="LoginFormulario">
            <c:choose>
                <c:when test="${tokenValido}">
                    <div class="LoginCabecalho">
                        <h1 class="LoginTitulo">Crie uma nova senha</h1>
                        <p class="LoginSubtitulo">Escolha uma senha nova para voltar a acessar sua conta.</p>
                    </div>

                    <form class="RedefinirFormulario" action="RedefinirSenha" method="post">
                        <input type="hidden" name="token" value="<c:out value='${token}'/>">

                        <div class="LoginCampoGrupo">
                            <label class="LoginCampoLabel" for="novaSenha">Nova senha</label>
                            <input class="LoginCampoInput" type="password" id="novaSenha" name="novaSenha"
                                   autocomplete="new-password" aria-describedby="RedefinirAjuda" required>
                        </div>

                        <div class="LoginCampoGrupo">
                            <label class="LoginCampoLabel" for="confirmarSenha">Confirmar senha</label>
                            <input class="LoginCampoInput" type="password" id="confirmarSenha" name="confirmarSenha"
                                   autocomplete="new-password" aria-describedby="RedefinirAjuda" required>
                        </div>

                        <p class="RedefinirAjuda" id="RedefinirAjuda">Digite e confirme a senha que deseja usar.</p>

                        <c:if test="${erro == 'diferentes'}">
                            <p class="RedefinirFeedback" role="alert">As senhas não são iguais. Confira os campos e tente novamente.</p>
                        </c:if>
                        <c:if test="${erro == 'fraca'}">
                            <p class="RedefinirFeedback" role="alert">A senha não atende aos requisitos mínimos. Escolha outra e tente novamente.</p>
                        </c:if>

                        <button type="submit" class="LoginBotao">Salvar nova senha</button>
                    </form>

                    <div class="LoginRodape">
                        <a class="LoginLinkEsqueci" href="login.jsp">Voltar ao login</a>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="LoginCabecalho">
                        <h1 class="LoginTitulo">Link inválido</h1>
                        <p class="LoginSubtitulo">Esse link expirou ou já foi usado.</p>
                    </div>

                    <div class="LoginRodape RedefinirRodape">
                        <a class="LoginLinkEsqueci" href="RecuperarSenha.jsp">Solicitar um novo link</a>
                        <a class="LoginLinkEsqueci" href="login.jsp">Voltar ao login</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>

    </div>
</div>
</body>
</html>
