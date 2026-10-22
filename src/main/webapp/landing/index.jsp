<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ouros | Eficiência e Sustentabilidade</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/landing/landing.css">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/Asa-icon.png">
</head>
<body>
    <header id="header">
        <a class="logo" href="#inicio" aria-label="Ouros — início"><img src="${pageContext.request.contextPath}/assets/Ouros.svg" alt="Ouros"></a>

        <nav id="navegacao" aria-label="Navegação principal">
            <a href="#inicio">Início</a>
            <a href="#funcionalidades">Funcionalidades</a>
            <a href="#funcionamento">Como funciona</a>
            <a href="#planos">Planos</a>
            <a href="#faq">FAQ</a>
            <a class="sobre" href="sobre.jsp">Sobre nós</a>
        </nav>

        <div class="acoes"><a class="entrar" href="../login.jsp">Entrar</a><a class="btcadastro" href="../Cadastro.jsp">Cadastro</a></div>
        <button type="button" class="menu-mobile" id="menuMobile" aria-label="Abrir menu" aria-controls="navegacao" aria-expanded="false"><span></span><span></span><span></span></button>
    </header>

    <section class="hero" id="inicio" aria-labelledby="hero-title">
        <div class="hero-atmosfera" aria-hidden="true">
            <div class="circulo hero-brilho-roxo-left"></div>
            <div class="circulo hero-brilho-roxo-right"></div>
            <div class="circulo hero-brilho-dourado"></div>
            <div class="decor">
                <span class="star star-1"></span><span class="star star-2"></span>
                <span class="star star-3"></span><span class="star star-4"></span>
                <span class="star star-5"></span><span class="star star-6"></span>
            </div>
        </div>
        <div class="hero-container">

            <div class="hero-content">
                <h1 id="hero-title"><span>Evolua sua</span><strong>GRANJA</strong><span>com eficiência e</span><span>sustentabilidade</span></h1>
                <p class="subTitle">Monitore consumo, acompanhe indicadores e evolua<br class="desktop-break"> no ranking de sustentabilidade.</p>
            </div>

            <div class="hero-visual">
                <div class="hero-composicao" role="img" aria-label="Protótipo de celular OUROS integrado à asa dourada e à linha orbital"><img src="${pageContext.request.contextPath}/assets/hero-composicao-ouros.svg" class="hero-composicao-arte" alt="" aria-hidden="true"><div class="decor composicao-stars" aria-hidden="true"><span class="star star-7"></span><span class="star star-8"></span></div></div>
            </div>

        </div>
        <div class="hero-wave" aria-hidden="true"><img src="${pageContext.request.contextPath}/assets/onda.svg" alt=""></div>
    </section>

    <section class="part2" id="desafios" aria-labelledby="desafios-title">
        <div class="part2-container">
            <h2 class="part2Title" id="desafios-title"><span>DESAFIOS</span><small>invisíveis na sua rede de produtores<br> integrados</small></h2>

            <div class="cards">
                <article class="card card-1"><img class="card-linha" src="${pageContext.request.contextPath}/assets/linhaGold.svg" alt="" aria-hidden="true"><img class="card-diagonal" src="${pageContext.request.contextPath}/assets/card-diagonal-descendo.svg" alt="" aria-hidden="true"><div class="card-content"><p>Falta de controle preciso dos gastos da produção.</p><h3>GASTOS</h3></div></article>
                <article class="card card-2"><img class="card-linha" src="${pageContext.request.contextPath}/assets/linhaGold.svg" alt="" aria-hidden="true"><img class="card-diagonal" src="${pageContext.request.contextPath}/assets/card-diagonal-subindo.svg" alt="" aria-hidden="true"><div class="card-content"><p>Recursos desperdiçados sem acompanhamento.</p><h3>DESPERDÍCIO</h3></div></article>
                <article class="card card-3"><img class="card-linha" src="${pageContext.request.contextPath}/assets/linhaGold.svg" alt="" aria-hidden="true"><img class="card-diagonal" src="${pageContext.request.contextPath}/assets/card-diagonal-descendo.svg" alt="" aria-hidden="true"><div class="card-content"><p>Decisões baseadas em estimativas pouco precisas.</p><h3>ESTIMATIVAS</h3></div></article>
                <article class="card card-4"><img class="card-linha" src="${pageContext.request.contextPath}/assets/linhaGold.svg" alt="" aria-hidden="true"><img class="card-diagonal" src="${pageContext.request.contextPath}/assets/card-diagonal-subindo.svg" alt="" aria-hidden="true"><div class="card-content"><p>Processos manuais dificultam o acompanhamento.</p><h3>PAPEL</h3></div></article>
            </div>

        </div>
    </section>

    <!-- FUNCIONAMENTO -->
    <section class="funcionamento" id="funcionamento" aria-labelledby="funcionamento-titulo">
        <div class="circulo funcionamento-brilho" aria-hidden="true"></div>
        <img class="funcionamento-detalhes" src="${pageContext.request.contextPath}/assets/funcionamento-detalhes.svg" alt="" aria-hidden="true">
        <div class="landing-container">
            <h2 class="secao-titulo secao-titulo--dourado" id="funcionamento-titulo"><span class="secao-titulo-linha" aria-hidden="true"></span>FUNCIONAMENTO</h2>
            <div class="funcionamento-grid">
                <div class="funcionamento-visual" id="funcionamentoVisual" aria-live="polite">
                    <img id="funcionamentoImagem" src="${pageContext.request.contextPath}/assets/funcionamento-notebook-figma.png" alt="Painel da plataforma exibido em um notebook">
                    <div class="funcionamento-duplo" id="funcionamentoDuplo" role="img" aria-label="Dois celulares com as telas de acompanhamento da plataforma" style="display: none;">
                        <img class="funcionamento-duplo-base" src="${pageContext.request.contextPath}/assets/funcionamento-duplo-base-figma.png" alt="">
                        <img class="funcionamento-duplo-tela funcionamento-duplo-tela-4" src="${pageContext.request.contextPath}/assets/funcionamento-tela-4-figma.png" alt="">
                        <img class="funcionamento-duplo-tela funcionamento-duplo-tela-3" src="${pageContext.request.contextPath}/assets/funcionamento-tela-3-figma.png" alt="">
                    </div>
                </div>
                <div class="funcionamento-conteudo">
                    <div class="perfil-opcoes" role="group" aria-label="Perfil de uso">
                        <button class="perfil-opcao selecionado" type="button" data-perfil="empresa" aria-pressed="true">Empresa</button>
                        <button class="perfil-opcao" type="button" data-perfil="integrado" aria-pressed="false">Integrado</button>
                    </div>
                    <ol class="funcionamento-passos">
                        <li class="passo-destaque" data-passo="1">
                            <button class="passo-botao" type="button" aria-label="Selecionar etapa 1" aria-pressed="true">1</button>
                            <h3 data-empresa="Substitua estimativas por dados primários reais" data-integrado="Registre dados reais da sua propriedade">Substitua estimativas por dados primários reais</h3>
                            <p data-empresa="Substitua cálculos imprecisos por dados primários de consumo direto de cada propriedade rural." data-integrado="Registre o consumo da sua propriedade e compartilhe dados reais com a empresa integradora.">Substitua cálculos imprecisos por dados primários de consumo direto de cada propriedade rural.</p>
                            <span class="passo-ligacao" aria-hidden="true"></span>
                        </li>
                        <li data-passo="2">
                            <button class="passo-botao" type="button" aria-label="Selecionar etapa 2" aria-pressed="false">2</button>
                            <h3 data-empresa="Identifique e priorize os fornecedores mais eficientes" data-integrado="Acompanhe seu desempenho entre os integrados">Identifique e priorize os fornecedores mais eficientes</h3>
                            <p data-empresa="Identifique instantaneamente os produtores mais eficientes para otimizar sua tomada de decisão e compras." data-integrado="Consulte seus indicadores e veja oportunidades para melhorar seus resultados.">Identifique instantaneamente os produtores mais eficientes para otimizar sua tomada de decisão e compras.</p>
                            <span class="passo-ligacao" aria-hidden="true"></span>
                        </li>
                        <li data-passo="3">
                            <button class="passo-botao" type="button" aria-label="Selecionar etapa 3" aria-pressed="false">3</button>
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
            <h2 class="secao-titulo" id="funcionalidades-titulo"><span class="secao-titulo-linha" aria-hidden="true"></span>FUNCIONALIDADES</h2>
            <div class="funcionalidades-faixa" data-slider="funcionalidades" tabindex="0" role="region" aria-label="Funcionalidades da plataforma; deslize horizontalmente para ver todos os cards">

                <article class="funcionalidade-card">
                    <img src="${pageContext.request.contextPath}/assets/funcionalidade-lote-figma.png" alt="Pessoa registrando informações da granja em um tablet" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Gerenciamento de lote</h3>
                        <p>Registre cada lote e acompanhe os dados da produção em um só lugar.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <img src="${pageContext.request.contextPath}/assets/funcionalidade-simulador-figma.png" alt="Calculadora e anotações usadas para simular custos" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Simulador de lucro</h3>
                        <p>Estime custos e receitas para entender a rentabilidade de cada lote.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <img src="${pageContext.request.contextPath}/assets/funcionalidade-agua-energia-figma.png" alt="Painéis solares e sistema de bombeamento de água em uma propriedade rural" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Gestão de água e energia</h3>
                        <p>Acompanhe o consumo de água e energia e encontre oportunidades de economia.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <img src="${pageContext.request.contextPath}/assets/funcionalidade-ranking-figma.png" alt="Degraus com estrelas douradas representando o ranking" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Ranking entre as granjas</h3>
                        <p>Compare o desempenho das granjas e reconheça quem adota práticas mais sustentáveis.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <div class="funcionalidade-imagem-sem-foto" aria-hidden="true"></div>
                    <div class="funcionalidade-texto">
                        <h3>Notificações inteligentes</h3>
                        <p>Receba alertas sobre indicadores e prazos importantes para agir no momento certo.</p>
                    </div>
                </article>


                <article class="funcionalidade-card">
                    <img src="${pageContext.request.contextPath}/assets/funcionalidade-metas-figma.png" alt="Mãos plantando uma muda como símbolo de sustentabilidade" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Metas de sustentabilidade</h3>
                        <p>Defina objetivos ambientais e acompanhe o avanço dos indicadores da sua granja.</p>
                    </div>
                </article>

                <article class="funcionalidade-card">
                    <img src="${pageContext.request.contextPath}/assets/funcionalidade-dashboard-figma.png" alt="Pessoa consultando um dashboard em um notebook" loading="lazy">
                    <div class="funcionalidade-texto">
                        <h3>Dashboards intuitivos</h3>
                        <p>Visualize os principais indicadores em painéis claros para apoiar suas decisões.</p>
                    </div>
                </article>

            </div>
        </div>
    </section>

    <!-- RANKING E SUSTENTABILIDADE -->
    <section class="ranking" id="ranking" aria-labelledby="ranking-titulo">
        <div class="landing-container ranking-grid">
            <div class="ranking-apresentacao">
                <span class="ranking-titulo-linha" aria-hidden="true"></span>
                <h2 class="ranking-titulo" id="ranking-titulo">A Liderança em<br>Sustentabilidade</h2>
                <img class="ranking-podio" src="${pageContext.request.contextPath}/assets/ligas.png" alt="Pódio de sustentabilidade com as categorias Ferro, Cobre, Bronze, Prata e Ouro" loading="lazy">
            </div>

            <ul class="ranking-beneficios" aria-label="Benefícios do ranking de sustentabilidade">
                <li class="ranking-beneficio">
                    <span class="ranking-beneficio-icone"><img src="${pageContext.request.contextPath}/assets/ranking-icone-olho.svg" alt=""></span>
                    <p>Visualize a eficiência real de cada fornecedor, sem estimar gastos indiretos.</p>
                </li>
                <li class="ranking-beneficio">
                    <span class="ranking-beneficio-icone"><img src="${pageContext.request.contextPath}/assets/ranking-icone-lupa.svg" alt=""></span>
                    <p>Identifique quem gasta mais e traz maior prejuízo ambiental.</p>
                </li>
                <li class="ranking-beneficio">
                    <span class="ranking-beneficio-icone"><img src="${pageContext.request.contextPath}/assets/ranking-icone-dados.svg" alt=""></span>
                    <p>Priorize decisões de compra com base em dados primários.</p>
                </li>
                <li class="ranking-beneficio">
                    <span class="ranking-beneficio-icone"><img src="${pageContext.request.contextPath}/assets/ranking-icone-trofeu.svg" alt=""></span>
                    <p>Reconheça publicamente os integrados mais eficientes.</p>
                </li>
            </ul>

        </div>
        <div class="landing-container">
            <aside class="ranking-calculo" aria-labelledby="calculo-titulo">
                <div class="calculo-cabecalho">
                    <h3 id="calculo-titulo">Como o Ranking é Calculado?</h3>
                    <span class="calculo-titulo-divisor" aria-hidden="true"></span>
                </div>
                <div class="calculo-explicacao">
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
                    <h4>Consumo Geral do Integrado (CGI)</h4>
                    <p aria-label="CAA vezes 0.7, mais CEA vezes 0.3, igual a CGI"><span>(</span><span class="formula-destaque">CAA</span> × <span class="formula-destaque">0.7</span><span>)</span> + <span>(</span><span class="formula-destaque">CEA</span> × <span class="formula-destaque">0.3</span><span>)</span> = <span class="formula-destaque">CGI</span></p>
                </div>
            </aside>
        </div>

    </section>

    <!-- PLANOS -->
    <section class="planos" id="planos" aria-labelledby="planos-titulo">
        <div class="circulo planos-brilho" aria-hidden="true"></div>
        <div class="landing-container">
            <h2 class="secao-titulo secao-titulo--dourado" id="planos-titulo"><span class="secao-titulo-linha" aria-hidden="true"></span>PLANOS</h2>

            <div class="planos-grid">
                <article class="plano-card">
                    <h3>Trimestral</h3>
                    <p>Ideal para quem quer experimentar nossos serviços.</p>
                    <h4>Preço</h4>
                    <p class="plano-preco">R$ 3500,00 fixo + 5 reais por integrado, por mês.</p>
                    <!-- Abre um rascunho de e-mail para agendar este plano. -->
                    <a class="plano-agendar" data-plano="Trimestral" data-meses="3" data-desconto="0" href="mailto:sofia.20260263@institutojef.org.br?subject=Agendamento%20de%20planejamento%20-%20Trimestral">Agendar planejamento</a>
                </article>

                <article class="plano-card">
                    <h3>Semestral</h3>
                    <p>Ideal para quem quer experimentar nossos serviços.</p>
                    <h4>Preço</h4>
                    <p class="plano-preco">R$ 3500,00 fixo + 5 reais por integrado, por mês. Com 5% de desconto.</p>
                    <!-- Abre um rascunho de e-mail para agendar este plano. -->
                    <a class="plano-agendar" data-plano="Semestral" data-meses="6" data-desconto="5" href="mailto:sofia.20260263@institutojef.org.br?subject=Agendamento%20de%20planejamento%20-%20Semestral">Agendar planejamento</a>
                </article>

                <article class="plano-card">
                    <h3>Anual</h3>
                    <p>Ideal para quem quer experimentar nossos serviços.</p>
                    <h4>Preço</h4>
                    <p class="plano-preco">R$ 3500,00 fixo + 5 reais por integrado, por mês. Com 10% de desconto.</p>
                    <!-- Abre um rascunho de e-mail para agendar este plano. -->
                    <a class="plano-agendar" data-plano="Anual" data-meses="12" data-desconto="10" href="mailto:sofia.20260263@institutojef.org.br?subject=Agendamento%20de%20planejamento%20-%20Anual">Agendar planejamento</a>
                </article>

            </div>

            <form class="calculadora-planos" id="calculadoraPlanos">
                <div class="calculadora-cabecalho">
                    <div>
                        <p class="calculadora-kicker">PLANEJE SEU INVESTIMENTO</p>
                        <h3>Simule seu preço</h3>
                        <fieldset class="opcoes-plano">
                            <legend>Escolha um plano</legend>
                            <label class="opcao-plano"><input type="radio" name="plano" value="Trimestral" data-meses="3" data-desconto="0" checked><span>Trimestral</span></label>
                            <label class="opcao-plano"><input type="radio" name="plano" value="Semestral" data-meses="6" data-desconto="5"><span>Semestral</span></label>
                            <label class="opcao-plano"><input type="radio" name="plano" value="Anual" data-meses="12" data-desconto="10"><span>Anual</span></label>
                        </fieldset>
                        <p id="planoSelecionado">Plano selecionado: Trimestral</p>
                    </div>
                    <label class="integrados-campo" for="quantidadeIntegrados">
                        Quantidade de integrados
                        <input id="quantidadeIntegrados" name="integrados" type="number" min="0" step="1" value="0" inputmode="numeric" required>
                    </label>
                    <button class="calcular-preco" type="submit">Simular preço</button>
                </div>
                <p class="resultado-preco" id="resultadoPreco" aria-live="polite">Informe a quantidade de integrados para calcular o investimento estimado.</p>
            </form>
        </div>
    </section>

    <!-- PERGUNTAS FREQUENTES -->
    <section class="faq" id="faq" aria-labelledby="faqTitulo">
        <div class="circulo faq-brilho faq-brilho-1" aria-hidden="true"></div>
        <div class="circulo faq-brilho faq-brilho-2" aria-hidden="true"></div>
        <div class="circulo faq-brilho faq-brilho-3" aria-hidden="true"></div>
        <div class="landing-container faq-container">
            <h2 class="secao-titulo faq-titulo" id="faqTitulo"><span class="secao-titulo-linha" aria-hidden="true"></span>FAQ</h2>
            <div class="faq-lista">
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta1" id="faqPergunta1">O sistema funciona offline?<span class="faq-seta" aria-hidden="true"></span></button></h3>
                    <div class="faq-resposta" id="faqResposta1" role="region" aria-labelledby="faqPergunta1" hidden><p>O uso das funcionalidades depende de conexão com a plataforma. A disponibilidade de recursos offline deverá ser confirmada para a versão implantada.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta2" id="faqPergunta2">Quem pode usar a plataforma?<span class="faq-seta" aria-hidden="true"></span></button></h3>
                    <div class="faq-resposta" id="faqResposta2" role="region" aria-labelledby="faqPergunta2" hidden><p>A plataforma foi pensada para empresas integradoras acompanharem sua rede e para produtores integrados registrarem e consultarem os dados da própria granja.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta3" id="faqPergunta3">Quais indicadores posso acompanhar?<span class="faq-seta" aria-hidden="true"></span></button></h3>
                    <div class="faq-resposta" id="faqResposta3" role="region" aria-labelledby="faqPergunta3" hidden><p>Consumo de água e energia, informações dos lotes, desempenho e indicadores de sustentabilidade da granja.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta4" id="faqPergunta4">Como funciona o ranking entre as granjas?<span class="faq-seta" aria-hidden="true"></span></button></h3>
                    <div class="faq-resposta" id="faqResposta4" role="region" aria-labelledby="faqPergunta4" hidden><p>O ranking compara indicadores de consumo e sustentabilidade para ajudar a visualizar o desempenho das propriedades participantes.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta5" id="faqPergunta5">Como é calculado o preço do plano?<span class="faq-seta" aria-hidden="true"></span></button></h3>
                    <div class="faq-resposta" id="faqResposta5" role="region" aria-labelledby="faqPergunta5" hidden><p>A estimativa usa o valor fixo de R$ 3.500,00 mais R$ 5,00 por integrado ao mês. O plano semestral tem 5% de desconto e o anual, 10% sobre o valor do período.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta6" id="faqPergunta6">Quais períodos de contratação estão disponíveis?<span class="faq-seta" aria-hidden="true"></span></button></h3>
                    <div class="faq-resposta" id="faqResposta6" role="region" aria-labelledby="faqPergunta6" hidden><p>Há opções trimestral, semestral e anual. Use o simulador para estimar o investimento conforme o número de integrados.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta7" id="faqPergunta7">Como solicito uma demonstração?<span class="faq-seta" aria-hidden="true"></span></button></h3>
                    <div class="faq-resposta" id="faqResposta7" role="region" aria-labelledby="faqPergunta7" hidden><p>Entre em contato com a equipe pelo bloco de ajuda abaixo para conversar sobre uma demonstração e conhecer a implantação.</p></div>
                </article>
                <article class="faq-item">
                    <h3><button class="faq-pergunta" type="button" aria-expanded="false" aria-controls="faqResposta8" id="faqPergunta8">Posso trocar de plano depois?<span class="faq-seta" aria-hidden="true"></span></button></h3>
                    <div class="faq-resposta" id="faqResposta8" role="region" aria-labelledby="faqPergunta8" hidden><p>As condições de alteração dependem do contrato. A equipe poderá orientar qual opção atende melhor ao período de uso da sua operação.</p></div>
                </article>
            </div>

            <aside class="faq-ajuda" aria-label="Ajuda e dúvidas">
                <div class="circulo faq-ajuda-brilho" aria-hidden="true"></div>
                <span class="faq-ajuda-icone" aria-hidden="true"><img src="${pageContext.request.contextPath}/assets/balao-falas.png" alt=""></span>
                <span class="faq-ajuda-texto"><strong>Sua dúvida não está aqui?</strong><span>Nossa equipe terá o prazer de ajudá-lo, garantindo que você<br>tenha a melhor experiência possível</span></span>
            </aside>
        </div>
    </section>

    <!-- RODAPÉ E SOBRE -->
    <footer class="rodape" id="sobre">
        <div class="landing-container rodape-conteudo">
            <div class="rodape-marca">
                <a class="rodape-logo" href="#inicio" aria-label="Ouros — voltar ao início"><img src="${pageContext.request.contextPath}/assets/Ouros.svg" alt="Ouros"></a>
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
                    <a href="#inicio">Início</a>
                    <a href="#desafios">Desafios</a>
                    <a href="#funcionamento">Passo a Passo</a>
                </div>
            </nav>
            <nav class="rodape-coluna rodape-coluna-secundaria" aria-label="Mais links do projeto">
                <div class="rodape-links">
                    <a href="#funcionalidades">Funcionalidades</a>
                    <a href="#planos">Planos</a>
                    <a href="#faq">FAQ</a>
                </div>
            </nav>
            <nav class="rodape-coluna rodape-coluna-equipe" aria-label="Links da equipe">
                <h2>Equipe</h2>
                <div class="rodape-links">
                    <a href="sobre.jsp">Sobre Nós</a>
                </div>
            </nav>
        </div>
        <div class="landing-container rodape-base"><span>© <span id="anoAtual">2026</span> Ouros. Todos os direitos reservados.</span></div>
    </footer>

    <script src="${pageContext.request.contextPath}/landing/landing.js" data-context-path="${pageContext.request.contextPath}" defer></script>
</body>

</html>

