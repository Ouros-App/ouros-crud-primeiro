<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="funcionamento" id="funcionamento" data-perfil="empresa" aria-labelledby="funcionamento-titulo">
    <img class="funcionamento-detalhes" src="WEB-INF/views/assets/funcionamento-detalhes-passo-a-passo.svg" alt="" aria-hidden="true">
    <div class="landing-container">
        <div class="funcionamento-cabecalho">
            <h2 class="secao-titulo secao-titulo--dourado" id="funcionamento-titulo"><span class="secao-titulo-linha" aria-hidden="true"></span><span class="funcionamento-titulo-texto">PASSO A PASSO</span></h2>
            <div class="perfil-opcoes" role="group" aria-label="Perfil de uso">
                <button class="perfil-opcao selecionado" type="button" data-perfil="empresa" aria-pressed="true">Empresa</button>
                <button class="perfil-opcao" type="button" data-perfil="integrado" aria-pressed="false">Integrado</button>
            </div>
        </div>
        <div class="funcionamento-grid">
            <div class="funcionamento-visual" id="funcionamentoVisual" data-assets-base="assets/" aria-live="polite">
                <div class="circulo funcionamento-brilho" aria-hidden="true"></div>
                <div class="funcionamento-recorte" id="funcionamentoRecorte">
                    <img id="funcionamentoImagem" src="WEB-INF/views/assets/imagem-1-empresa-passo-a-passo.svg" alt="Tablet com painel da empresa e indicadores de produção">
                </div>
            </div>
            <div class="funcionamento-conteudo">
                <ol class="funcionamento-passos">
                    <li class="passo-destaque" data-passo="1">
                        <button class="passo-botao" type="button" aria-label="Selecionar etapa 1" aria-pressed="true">1</button>
                        <h3 data-empresa="Substitua estimativas por dados primários reais" data-integrado="Registre seu consumo de forma simples e offline">Substitua estimativas por dados primários reais</h3>
                        <p data-empresa="Substitua cálculos imprecisos por dados primários de consumo direto de cada propriedade rural." data-integrado="Lance seus dados de água e energia ao final de cada lote, mesmo sem sinal de internet na granja.">Substitua cálculos imprecisos por dados primários de consumo direto de cada propriedade rural.</p>
                        <span class="passo-ligacao" aria-hidden="true"></span>
                    </li>
                    <li data-passo="2">
                        <button class="passo-botao" type="button" aria-label="Selecionar etapa 2" aria-pressed="false">2</button>
                        <h3 data-empresa="Identifique e priorize os fornecedores mais eficientes" data-integrado="Acompanhe sua evolução em dashboards claros">Identifique e priorize os fornecedores mais eficientes</h3>
                        <p data-empresa="Identifique instantaneamente os produtores mais eficientes para otimizar sua tomada de decisão e compras." data-integrado="Acompanhe a evolução da sua produção com gráficos claros e fáceis de entender.">Identifique instantaneamente os produtores mais eficientes para otimizar sua tomada de decisão e compras.</p>
                        <span class="passo-ligacao" aria-hidden="true"></span>
                    </li>
                    <li data-passo="3">
                        <button class="passo-botao" type="button" aria-label="Selecionar etapa 3" aria-pressed="false">3</button>
                        <h3 data-empresa="Monitore suas metas ESG com precisão" data-integrado="Suba no ranking e conquiste o nível Ouro">Monitore suas metas ESG com precisão</h3>
                        <p data-empresa="Acompanhe o progresso das suas metas ambientais com relatórios integrados de toda a sua rede de fornecedores." data-integrado="Melhore sua eficiência, suba de nível (do Bronze ao Ouro) e ganhe destaque perante a empresa.">Acompanhe o progresso das suas metas ambientais com relatórios integrados de toda a sua rede de fornecedores.</p>
                    </li>
                </ol>
            </div>
        </div>
    </div>
</section>
