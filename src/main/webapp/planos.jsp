<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <a class="plano-agendar" data-plano="Trimestral" data-meses="3" data-desconto="0" href="mailto:ouros.app@gmail.com?subject=Agendamento%20de%20planejamento%20-%20Trimestral">Agendar planejamento</a>
            </article>

            <article class="plano-card">
                <h3>Semestral</h3>
                <p>Ideal para quem busca acompanhar nossos serviços por mais tempo.</p>
                <h4>Preço</h4>
                <p class="plano-preco">R$ 3500,00 fixo + 5 reais por integrado, por mês. Com <span class="plano-desconto-destaque">5%</span> de desconto.</p>
                <!-- Abre um rascunho de e-mail para agendar este plano. -->
                <a class="plano-agendar" data-plano="Semestral" data-meses="6" data-desconto="5" href="mailto:ouros.app@gmail.com?subject=Agendamento%20de%20planejamento%20-%20Semestral">Agendar planejamento</a>
            </article>

            <article class="plano-card">
                <h3>Anual</h3>
                <p>Ideal para quem quer aproveitar nossos serviços de forma contínua.</p>
                <h4>Preço</h4>
                <p class="plano-preco">R$ 3500,00 fixo + 5 reais por integrado, por mês. Com <span class="plano-desconto-destaque">10%</span> de desconto.</p>
                <!-- Abre um rascunho de e-mail para agendar este plano. -->
                <a class="plano-agendar" data-plano="Anual" data-meses="12" data-desconto="10" href="mailto:ouros.app@gmail.com?subject=Agendamento%20de%20planejamento%20-%20Anual">Agendar planejamento</a>
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
