<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ouros | Eficiência e Sustentabilidade</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="landing.css?v=7">
    <link rel="icon" href="assets/Asa-icon.png">
</head>
<body>
    <header id="header">
        <a class="logo" href="#inicio" aria-label="Ouros — início"><img src="assets/Ouros.svg" alt="Ouros"></a>

        <nav id="navegacao" aria-label="Navegação principal">
            <a href="#inicio">Início</a>
            <a href="#funcionalidades">Funcionalidades</a>
            <a href="#funcionamento">Como funciona</a>
            <a href="#planos">Planos</a>
            <a href="#faq">FAQ</a>
            <a class="sobre" href="#sobre">Sobre nós</a>
        </nav>

        <div class="acoes"><a class="entrar" href="login.jsp">Entrar</a><a class="btcadastro" href="#funcionamento">Cadastro</a></div>
        <button type="button" class="menu-mobile" id="menuMobile" aria-label="Abrir menu" aria-controls="navegacao" aria-expanded="false"><span></span><span></span><span></span></button>
    </header>

    <section class="hero" id="inicio" aria-labelledby="hero-title">
        <div class="decor" aria-hidden="true"><span class="star star-1"></span><span class="star star-2"></span><span class="star star-3"></span><span class="star star-4"></span><span class="star star-5"></span><span class="star star-6"></span></div>
        <div class="hero-container">

            <div class="hero-content">
                <h1 id="hero-title"><span>Evolua sua</span><strong>GRANJA</strong><span>com eficiência e</span><span>sustentabilidade</span></h1>
                <p class="subTitle">Monitore consumo, acompanhe indicadores e evolua<br class="desktop-break"> no ranking de sustentabilidade.</p>
            </div>

            <div class="hero-visual">
                <img src="assets/linhaGold.svg" class="linha-gold" alt="" aria-hidden="true">
                <img src="assets/celular.png" class="celular" alt="Celular com a asa dourada do Ouros">
            </div>

        </div>
        <div class="hero-wave" aria-hidden="true"><img src="assets/onda.svg?v=4" alt=""></div>
    </section>

    <section class="part2" id="desafios" aria-labelledby="desafios-title">
        <div class="part2-container">
            <h2 class="part2Title" id="desafios-title"><span>DESAFIOS</span><small>invisíveis na sua rede de produtores<br> integrados</small></h2>

            <div class="cards">
                <article class="card card-1"><div class="card-content"><p>Falta de controle preciso dos gastos da produção.</p><h3>GASTOS</h3></div></article>
                <article class="card card-2"><div class="card-content"><p>Recursos desperdiçados sem acompanhamento.</p><h3>DESPERDÍCIO</h3></div></article>
                <article class="card card-3"><div class="card-content"><p>Decisões baseadas em estimativas pouco precisas.</p><h3>ESTIMATIVAS</h3></div></article>
                <article class="card card-4"><div class="card-content"><p>Processos manuais dificultam o acompanhamento.</p><h3>PAPEL</h3></div></article>
            </div>

        </div>
    </section>

    <!-- FUNCIONAMENTO -->
    <section class="funcionamento" id="funcionamento" aria-labelledby="funcionamento-titulo">
        <div class="landing-container">
            <h2 class="secao-titulo secao-titulo--dourado" id="funcionamento-titulo">FUNCIONAMENTO</h2>
            <div class="funcionamento-grid">
                <div class="funcionamento-visual" id="funcionamentoVisual" aria-live="polite">
                    <img id="funcionamentoImagem" src="assets/funcionamento-celular.svg" alt="Tela 1 do funcionamento para empresas">
                </div>
                <div class="funcionamento-conteudo">
                    <div class="perfil-opcoes" role="group" aria-label="Perfil de uso">
                        <button class="perfil-opcao selecionado" type="button" data-perfil="empresa" aria-pressed="true">Empresa</button>
                        <button class="perfil-opcao" type="button" data-perfil="integrado" aria-pressed="false">Integrado</button>
                    </div>
                    <ol class="funcionamento-passos">
                        <li class="passo-destaque" data-passo="1">
                            <button class="passo-botao" type="button" aria-label="Selecionar etapa 1" aria-pressed="true"></button>
                            <h3 data-empresa="Substitua estimativas por dados primários reais" data-integrado="Registre dados reais da sua propriedade">Substitua estimativas por dados primários reais</h3>
                            <p data-empresa="Substitua cálculos imprecisos por dados primários de consumo direto de cada propriedade rural." data-integrado="Registre o consumo da sua propriedade e compartilhe dados reais com a empresa integradora.">Substitua cálculos imprecisos por dados primários de consumo direto de cada propriedade rural.</p>
                        </li>
                        <li data-passo="2">
                            <button class="passo-botao" type="button" aria-label="Selecionar etapa 2" aria-pressed="false"></button>
                            <h3 data-empresa="Identifique e priorize os fornecedores mais eficientes" data-integrado="Acompanhe seu desempenho entre os integrados">Identifique e priorize os fornecedores mais eficientes</h3>
                            <p data-empresa="Identifique instantaneamente os produtores mais eficientes para otimizar sua tomada de decisão e compras." data-integrado="Consulte seus indicadores e veja oportunidades para melhorar seus resultados.">Identifique instantaneamente os produtores mais eficientes para otimizar sua tomada de decisão e compras.</p>
                        </li>
                        <li data-passo="3">
                            <button class="passo-botao" type="button" aria-label="Selecionar etapa 3" aria-pressed="false"></button>
                            <h3 data-empresa="Monitore suas metas ESG com precisão" data-integrado="Acompanhe metas de sustentabilidade">Monitore suas metas ESG com precisão</h3>
                            <p data-empresa="Acompanhe o progresso das suas metas ambientais com relatórios integrados de toda a sua rede de fornecedores." data-integrado="Visualize o progresso dos indicadores ambientais e de consumo da sua granja.">Acompanhe o progresso das suas metas ambientais com relatórios integrados de toda a sua rede de fornecedores.</p>
                        </li>
                    </ol>
                </div>
            </div>
        </div>
    </section>

    <!-- FUNCIONALIDADES -->

    <section class="funcionalidades" id="funcionalidades" aria-labelledby="funcionalidades-titulo">
        <div class="landing-container">
            <h2 class="secao-titulo" id="funcionalidades-titulo">FUNCIONALIDADES</h2>
            <div class="funcionalidades-faixa" data-slider="funcionalidades" tabindex="0" role="region" aria-label="Funcionalidades da plataforma; deslize horizontalmente para ver todos os cards">

                <article class="funcionalidade-card">
                    <img src="assets/gerenciamento-lote.svg" alt="" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Gerenciamento de lote</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <img src="assets/simulador-lucro.svg" alt="" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Simulador de lucro</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <img src="assets/gestao-agua-energia.svg" alt="" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Gestão de água e energia</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <img src="assets/ranking-granjas.svg" alt="" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Ranking entre as granjas</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <img src="assets/notificacoes.svg" alt="" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Notificações inteligentes</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur blandit tempus porttitor et posuere consectetur est at lobortis.</p>
                    </div>
                </article>


                <article class="funcionalidade-card">
                    <img src="assets/metas-sustentabilidade.svg" alt="" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Metas de sustentabilidade</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante venenatis dapibus posuere velit aliquet.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <img src="assets/dashboards-intuitivos.svg" alt="" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Dashboards intuitivos</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean lacinia bibendum nulla sed consectetur, donec ullamcorper.</p>
                    </div>
                </article>

            </div>
        </div>
    </section>

    <!-- RANKING E SUSTENTABILIDADE -->
    <section class="ranking" id="ranking" aria-labelledby="ranking-titulo">
        <div class="landing-container ranking-grid">
            <!-- Imagem provisória: substituir pelo pódio com os cinco troféus. -->
            <img class="ranking-podio" src="assets/ligas.png" alt="Ranking de sustentabilidade com as categorias Ferro, Cobre, Bronze, Prata e Ouro" loading="lazy">

            <div class="ranking-texto">
                <h2 id="ranking-titulo">A Liderança em<br>Sustentabilidade</h2>
                <p>Utilize rankings para gerenciar seus integrados e ter uma melhor visualização de seus gastos. Cada estado brasileiro possui seu próprio ranking.</p>
                <p>Visualize a eficiência real de cada fornecedor, sem estimar gastos irreais, identifique quem gasta mais e traz maior prejuízo ambiental, priorize decisões de compra com base em dados primários, reconheça publicamente os integrados mais eficientes.</p>
            </div>

        </div>
        <div class="landing-container">
            <aside class="ranking-calculo" aria-labelledby="calculo-titulo">
                <div class="calculo-explicacao">
                    <h3 id="calculo-titulo">Como o Ranking é Calculado?</h3>
                    <p>O produtor informa a leitura inicial e final do hidrômetro, o preço por metro cúbico de água e o preço por kWh do período. O sistema converte isso em intensidade de consumo por cabeça entregue — a mesma métrica para todos, independentemente do tamanho da propriedade.</p>
                </div>
                <div class="calculo-formulas">
                    <div class="formula">
                        <h4>Consumo de água por ave (CAA)</h4>
                        <p>Litros ÷ Quantidade de aves</p>
                    </div>
                    <div class="formula">
                        <h4>Consumo de energia por ave (CEA)</h4>
                        <p>Energia ÷ Quantidade de aves</p>
                    </div>
                </div>
                <div class="formula formula--geral">
                    <h4>Consumo geral do integrado (CGI)</h4>
                    <p>(CAA × preço da água) + (CEA × preço da energia)</p>
                </div>
            </aside>
        </div>
    </section>

    <!-- PLANOS -->
    <section class="planos" id="planos" aria-labelledby="planos-titulo">
        <div class="landing-container">
            <h2 class="secao-titulo secao-titulo--dourado" id="planos-titulo">PLANOS</h2>

            <div class="planos-grid">
                <article class="plano-card">
                    <h3>Trimestral</h3>
                    <p>Ideal para quem quer experimentar nossos serviços.</p>
                    <h4>Preço</h4>
                    <p class="plano-preco">R$ 3.500,00 fixos + R$ 5,00 por integrado, por mês.</p>
                    <!-- Seleciona o período no simulador de preço. -->
                    <button type="button" class="plano-agendar" data-plano="Trimestral" data-meses="3" data-desconto="0" aria-pressed="false">Agendar planejamento</button>
                </article>

                <article class="plano-card">
                    <h3>Semestral</h3>
                    <p>Ideal para quem quer experimentar nossos serviços.</p>
                    <h4>Preço</h4>
                    <p class="plano-preco">R$ 3.500,00 fixos + R$ 5,00 por integrado, por mês, com 5% de desconto sobre o total.</p>
                    <!-- Seleciona o período no simulador de preço. -->
                    <button type="button" class="plano-agendar" data-plano="Semestral" data-meses="6" data-desconto="5" aria-pressed="false">Agendar planejamento</button>
                </article>

                <article class="plano-card">
                    <h3>Anual</h3>
                    <p>Ideal para quem quer experimentar nossos serviços.</p>
                    <h4>Preço</h4>
                    <p class="plano-preco">R$ 3.500,00 fixos + R$ 5,00 por integrado, por mês, com 10% de desconto sobre o total.</p>
                    <!-- Seleciona o período no simulador de preço. -->
                    <button type="button" class="plano-agendar" data-plano="Anual" data-meses="12" data-desconto="10" aria-pressed="false">Agendar planejamento</button>
                </article>

            </div>

            <form class="calculadora-planos" id="calculadoraPlanos">
                <div class="calculadora-cabecalho">
                    <div>
                        <p class="calculadora-kicker">PLANEJE SEU INVESTIMENTO</p>
                        <h3>Simule seu preço</h3>
                        <p id="planoSelecionado">Plano selecionado: Trimestral</p>
                    </div>
                    <label class="integrados-campo" for="quantidadeIntegrados">
                        Quantidade de integrados
                        <input id="quantidadeIntegrados" name="integrados" type="number" min="0" step="1" value="0" inputmode="numeric" required>
                    </label>
                    <button class="calcular-preco" type="submit">Calcular estimativa</button>
                </div>
                <p class="resultado-preco" id="resultadoPreco" aria-live="polite">Informe a quantidade de integrados para calcular o investimento estimado.</p>
            </form>
        </div>
    </section>

    <!-- PERGUNTAS FREQUENTES -->
    <section class="faq" id="faq" aria-labelledby="faqTitulo">
        <div class="landing-container faq-container">
            <p class="faq-kicker">DÚVIDAS FREQUENTES</p>
            <h2 class="secao-titulo faq-titulo" id="faqTitulo">FAQ</h2>
            <div class="faq-lista">
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta1" id="faqPergunta1">O sistema funciona offline?<span aria-hidden="true">+</span></button></h3>
                    <div class="faq-resposta" id="faqResposta1" role="region" aria-labelledby="faqPergunta1" hidden><p>O uso das funcionalidades depende de conexão com a plataforma. A disponibilidade de recursos offline deverá ser confirmada para a versão implantada.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta2" id="faqPergunta2">Quem pode usar a plataforma?<span aria-hidden="true">+</span></button></h3>
                    <div class="faq-resposta" id="faqResposta2" role="region" aria-labelledby="faqPergunta2" hidden><p>A plataforma foi pensada para empresas integradoras acompanharem sua rede e para produtores integrados registrarem e consultarem os dados da própria granja.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta3" id="faqPergunta3">Quais indicadores posso acompanhar?<span aria-hidden="true">+</span></button></h3>
                    <div class="faq-resposta" id="faqResposta3" role="region" aria-labelledby="faqPergunta3" hidden><p>Consumo de água e energia, informações dos lotes, desempenho e indicadores de sustentabilidade da granja.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta4" id="faqPergunta4">Como funciona o ranking entre as granjas?<span aria-hidden="true">+</span></button></h3>
                    <div class="faq-resposta" id="faqResposta4" role="region" aria-labelledby="faqPergunta4" hidden><p>O ranking compara indicadores de consumo e sustentabilidade para ajudar a visualizar o desempenho das propriedades participantes.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta5" id="faqPergunta5">Como é calculado o preço do plano?<span aria-hidden="true">+</span></button></h3>
                    <div class="faq-resposta" id="faqResposta5" role="region" aria-labelledby="faqPergunta5" hidden><p>A estimativa usa o valor fixo de R$ 3.500,00 mais R$ 5,00 por integrado ao mês. O plano semestral tem 5% de desconto e o anual, 10% sobre o valor do período.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta6" id="faqPergunta6">Quais períodos de contratação estão disponíveis?<span aria-hidden="true">+</span></button></h3>
                    <div class="faq-resposta" id="faqResposta6" role="region" aria-labelledby="faqPergunta6" hidden><p>Há opções trimestral, semestral e anual. Use o simulador para estimar o investimento conforme o número de integrados.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta7" id="faqPergunta7">Como solicito uma demonstração?<span aria-hidden="true">+</span></button></h3>
                    <div class="faq-resposta" id="faqResposta7" role="region" aria-labelledby="faqPergunta7" hidden><p>Entre em contato com a equipe pelo bloco de ajuda abaixo para conversar sobre uma demonstração e conhecer a implantação.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta8" id="faqPergunta8">Posso trocar de plano depois?<span aria-hidden="true">+</span></button></h3>
                    <div class="faq-resposta" id="faqResposta8" role="region" aria-labelledby="faqPergunta8" hidden><p>As condições de alteração dependem do contrato. A equipe poderá orientar qual opção atende melhor ao período de uso da sua operação.</p></div>
                </article>
            </div>

            <aside class="faq-ajuda" aria-label="Ajuda e dúvidas">
                <span class="faq-ajuda-icone" aria-hidden="true"><img src="assets/balao-falas.png" alt=""></span>
                <span class="faq-ajuda-texto"><strong>Sua dúvida não está aqui?</strong><span>Nossa equipe terá o prazer de ajudar, garantindo que você tenha a melhor experiência possível.</span></span>
                <span class="faq-ajuda-mascote"><img src="assets/midas%20-%20d%C3%BAvidas_.png" alt="Mascote Midas com uma placa de dúvidas"></span>
            </aside>
        </div>
    </section>

    <!-- RODAPÉ E SOBRE -->
    <footer class="rodape" id="sobre">
        <div class="landing-container rodape-conteudo">
            <div class="rodape-marca">
                <a class="rodape-logo" href="#inicio" aria-label="Ouros — voltar ao início"><img src="assets/Ouros.svg" alt="Ouros"></a>
                <p>Desempenho que gera reconhecimento.</p>
            </div>
            <nav class="rodape-coluna" aria-label="Links do produto">
                <h2>Produto</h2>
                <a href="#funcionamento">Como funciona</a>
                <a href="#funcionalidades">Funcionalidades</a>
                <a href="#planos">Planos</a>
            </nav>
            <nav class="rodape-coluna" aria-label="Links da empresa">
                <h2>Empresa</h2>
                <a href="#sobre">Sobre nós</a>
                <a href="#faq">Perguntas frequentes</a>
                <a href="#faq">Contato</a>
            </nav>
            <div class="rodape-coluna rodape-legal">
                <h2>Legal</h2>
                <p>Política de privacidade — em elaboração</p>
                <p>Termos de uso — em elaboração</p>
            </div>
        </div>
        <div class="landing-container rodape-base"><span>© <span id="anoAtual">2026</span> Ouros. Todos os direitos reservados.</span><a href="#inicio">Voltar ao início ↑</a></div>
    </footer>

    <script src="landing.js" defer></script>
</body>

</html>
