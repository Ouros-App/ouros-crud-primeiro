<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    Object statusAttribute = request.getAttribute("jakarta.servlet.error.status_code");
    Object exceptionAttribute = request.getAttribute("jakarta.servlet.error.exception_type");

    if (statusAttribute == null) {
        statusAttribute = request.getAttribute("javax.servlet.error.status_code");
    }
    if (exceptionAttribute == null) {
        exceptionAttribute = request.getAttribute("javax.servlet.error.exception_type");
    }
    int errorCode = 404;

    if (statusAttribute instanceof Integer) {
        errorCode = ((Integer) statusAttribute).intValue();
    } else if (exceptionAttribute != null) {
        errorCode = 500;
    }

    boolean notFound = errorCode == 404;
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#ffffff">
    <title>Erro <%= errorCode %> | OUROS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/erro.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/Midas-Zonzo.png">
</head>
<body>
<main class="error-page" aria-labelledby="error-title">
    <span class="corner corner--top" aria-hidden="true"></span>
    <span class="corner corner--bottom" aria-hidden="true"></span>

    <section class="error-content">
        <p class="eyebrow">Erro CRUD</p>

        <% if (notFound) { %>
        <div class="error-scene error-scene--404" role="img" aria-label="Erro 404 com Midas Zonzo">
            <span class="digit">4</span>
            <div class="mascot-wrap" aria-hidden="true">
                <svg class="orbit" viewBox="0 0 360 220" focusable="false">
                    <ellipse cx="180" cy="98" rx="139" ry="38" transform="rotate(-13 180 98)" />
                    <ellipse cx="180" cy="98" rx="139" ry="38" transform="rotate(13 180 98)" />
                    <path d="M49 71l8 2 5 7-8 5-8-5 1-8zm51-31 8 2 4 8-8 5-7-6 3-9zm48 12 7 3 3 8-8 4-7-6 5-9zm99-13 8 2 4 8-8 5-7-6 3-9zm53 57 8 2 4 8-8 5-7-6 3-9z" />
                </svg>
                <img src="${pageContext.request.contextPath}/img/Midas-Zonzo.png" alt="">
            </div>
            <span class="digit">4</span>
        </div>
        <% } else { %>
        <div class="error-scene error-scene--generic" role="img" aria-label="Erro <%= errorCode %> com Midas Zonzo">
            <span class="generic-code"><%= errorCode %></span>
            <div class="mascot-wrap" aria-hidden="true">
                <img src="${pageContext.request.contextPath}/img/Midas-Zonzo.png" alt="">
            </div>
        </div>
        <% } %>

        <h1 id="error-title">Ocorreu um erro!</h1>
        <p class="message">Tente novamente mais tarde.</p>

        <div class="actions">
            <button class="button button--primary" type="button" onclick="window.location.reload()">
                Tentar novamente
            </button>
            <a class="button button--secondary" href="${pageContext.request.contextPath}/inicio.jsp">
                Voltar ao início
            </a>
        </div>
    </section>
</main>
</body>
</html>
