<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header id="header">
    <a class="logo" href="#inicio" aria-label="Ouros — início"><img src="WEB-INF/views/assets/Ouros.svg" alt="Ouros"></a>

    <nav id="navegacao" aria-label="Navegação principal">
        <a href="#inicio">Início</a>
        <a href="#desafios">Desafios</a>
        <a href="#funcionamento">Passo a passo</a>
        <a href="#funcionalidades">Funcionalidades</a>
        <a href="#ranking">Ranking</a>
        <a href="#planos">Planos</a>
        <a href="#faq">FAQ</a>
        <a class="sobre" href="${pageContext.request.contextPath}/landing/sobre.jsp">Sobre nós</a>
    </nav>

    <div class="acoes"><a class="entrar" href="acesso/login.html">Entrar</a><a class="btcadastro" href="acesso/cadastro.html">Cadastro</a></div>
    <button type="button" class="menu-mobile" id="menuMobile" aria-label="Abrir menu" aria-controls="navegacao" aria-expanded="false"><span></span><span></span><span></span></button>
</header>
