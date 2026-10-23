<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sobre nós | Ouros</title>
    <meta name="description" content="Conheça as pessoas por trás do Ouros.">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="sobre.css?v=20261023-1">
    <link rel="icon" href="../assets/Asa-icon.png">
</head>
<body class="pagina-sobre">
    <header class="sobre-header" id="header">
        <a class="sobre-logo" href="index.jsp" aria-label="Ouros — voltar ao início">
            <img src="../assets/Ouros.svg" alt="Ouros">
        </a>
        <nav class="sobre-nav" id="navegacaoSobre" aria-label="Navegação principal">
            <a href="index.jsp#inicio">Início</a>
            <a href="index.jsp#desafios">Desafios</a>
            <a href="index.jsp#funcionamento">Passo a Passo</a>
            <a href="index.jsp#funcionalidades">Funcionalidades</a>
            <a href="index.jsp#ranking">Ranking</a>
            <a href="index.jsp#planos">Planos</a>
            <a href="index.jsp#faq">FAQ</a>
            <a class="sobre-link-atual" href="${pageContext.request.contextPath}/landing/sobre.jsp" aria-current="page">Sobre nós</a>
        </nav>
        <div class="sobre-acoes">
            <a class="sobre-entrar" href="../login.jsp">Entrar</a>
            <a class="sobre-cadastro" href="../Cadastro.jsp">Cadastro</a>
        </div>
        <button class="sobre-menu" id="menuSobre" type="button" aria-label="Abrir menu" aria-controls="navegacaoSobre" aria-expanded="false">
            <span></span><span></span><span></span>
        </button>
    </header>

    <main>
        <section class="sobre-hero" id="inicio" aria-labelledby="sobreTitulo">
            <img class="sobre-asa-fundo" src="${pageContext.request.contextPath}/assets/asa.svg?v=20261022-3" alt="" aria-hidden="true">
            <div class="sobre-estrelas" aria-hidden="true"><span class="star star-1"></span><span class="star star-2"></span><span class="star star-3"></span><span class="star star-4"></span><span class="star star-5"></span><span class="star star-6"></span><span class="star star-7"></span><span class="star star-8"></span><span class="star star-9"></span><span class="star star-10"></span></div>
            <h1 id="sobreTitulo"><span>Conheça a equipe</span><span>por trás do</span><strong>OUROS</strong></h1>
            <svg class="sobre-onda" viewBox="0 0 1440 160" preserveAspectRatio="none" aria-hidden="true">
                <path d="M0 89C100 80 128 44 240 50c104 6 114 50 226 49 109-1 133-37 242-34 100 3 127 37 232 35 112-2 147-45 256-45 101 0 156 34 244 43v62H0V89Z" fill="#faf7ec"/>
            </svg>
        </section>

        <section class="sobre-equipe" aria-labelledby="tituloEquipe">
            <div class="sobre-container">
                <h2 class="sobre-titulo-secao" id="tituloEquipe"><span>Conheça a nossa equipe</span></h2>
                <figure class="sobre-foto-equipe">
                    <div class="sobre-foto-equipe-placeholder" role="img" aria-label="Imagem provisória da equipe"></div>
                    <figcaption>Imagem provisória — foto oficial da equipe será adicionada depois</figcaption>
                </figure>

                <section class="sobre-ano" aria-labelledby="tituloPrimeiroAno">
                    <h3 class="sobre-ano-titulo" id="tituloPrimeiroAno"><span>1º Ano</span><img class="sobre-ano-asa" src="../assets/asa-icone.svg" alt="" aria-hidden="true"></h3>
                    <div class="sobre-faixa-pessoas" role="region" tabindex="0" aria-label="Equipe do primeiro ano; deslize para ver todas as pessoas">
                        <div class="sobre-pessoas">
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                        </div>
                    </div>
                </section>

                <section class="sobre-ano" aria-labelledby="tituloSegundoAno">
                    <h3 class="sobre-ano-titulo" id="tituloSegundoAno"><span>2º Ano</span><img class="sobre-ano-asa" src="../assets/asa-icone.svg" alt="" aria-hidden="true"></h3>
                    <div class="sobre-faixa-pessoas" role="region" tabindex="0" aria-label="Equipe do segundo ano; deslize para ver todas as pessoas">
                        <div class="sobre-pessoas">
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                            <figure class="sobre-pessoa" tabindex="0" aria-label="Integrante provisório; informações da equipe em atualização"><span class="sobre-pessoa-placeholder" role="img" aria-label="Foto provisória de integrante da equipe"><span aria-hidden="true">Foto<br>provisória</span></span><figcaption class="sobre-pessoa-info"><strong>Integrante provisório</strong><span>Nome e função em atualização</span></figcaption></figure>
                        </div>
                    </div>
                </section>
            </div>
        </section>
    </main>

    <footer class="rodape" id="sobre">
        <div class="landing-container rodape-conteudo">
            <div class="rodape-marca">
                <a class="rodape-logo" href="index.jsp#inicio" aria-label="Ouros — voltar ao início">
                    <img src="${pageContext.request.contextPath}/assets/Ouros.svg" alt="Ouros">
                </a>
                <p>Desempenho que gera reconhecimento</p>
                <div class="rodape-contatos">
                    <a class="rodape-contato rodape-contato-email" href="mailto:ouros.app@gmail.com">
                        <span class="rodape-contato-icone rodape-contato-icone-email" aria-hidden="true"></span>
                        <span>ouros.app@gmail.com</span>
                    </a>
                    <a class="rodape-contato rodape-contato-instagram" href="https://www.instagram.com/ouros_app/" target="_blank" rel="noopener noreferrer">
                        <span class="rodape-contato-icone rodape-contato-icone-instagram" aria-hidden="true"></span>
                        <span>ouros_app</span>
                    </a>
                </div>
            </div>
            <nav class="rodape-coluna" aria-label="Links do projeto">
                <h2>Projeto</h2>
                <div class="rodape-links">
                    <a href="index.jsp#inicio">Início</a>
                    <a href="index.jsp#desafios">Desafios</a>
                    <a href="index.jsp#funcionamento">Passo a Passo</a>
                </div>
            </nav>
            <nav class="rodape-coluna rodape-coluna-secundaria" aria-label="Mais links do projeto">
                <div class="rodape-links">
                    <a href="index.jsp#funcionalidades">Funcionalidades</a>
                    <a href="index.jsp#planos">Planos</a>
                    <a href="index.jsp#faq">FAQ</a>
                </div>
            </nav>
            <nav class="rodape-coluna rodape-coluna-equipe" aria-label="Links da equipe">
                <h2>Equipe</h2>
                <div class="rodape-links">
                    <a href="sobre.jsp#inicio">Sobre Nós</a>
                </div>
            </nav>
        </div>
        <div class="landing-container rodape-base">
            <span>© <span id="anoAtual">2026</span> Ouros. Todos os direitos reservados.</span>
        </div>
    </footer>

    <script>
        const menuSobre = document.getElementById('menuSobre');
        const navegacaoSobre = document.getElementById('navegacaoSobre');
        menuSobre.addEventListener('click', () => {
            const abrir = menuSobre.getAttribute('aria-expanded') !== 'true';
            menuSobre.setAttribute('aria-expanded', String(abrir));
            menuSobre.setAttribute('aria-label', abrir ? 'Fechar menu' : 'Abrir menu');
            navegacaoSobre.classList.toggle('sobre-nav-aberta', abrir);
        });
        navegacaoSobre.querySelectorAll('a').forEach((link) => link.addEventListener('click', () => {
            menuSobre.setAttribute('aria-expanded', 'false');
            menuSobre.setAttribute('aria-label', 'Abrir menu');
            navegacaoSobre.classList.remove('sobre-nav-aberta');
        }));
    </script>
</body>
</html>
