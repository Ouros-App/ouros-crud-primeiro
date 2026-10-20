<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Início</title>
    <link rel="stylesheet" href="Style.css?v=5">
    <link rel="icon" type="image/png" href="img/Asa-icon.png">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>

<div class="Layout">

    <aside class="Sidebar">
        <div class="SidebarTopo">
            <div class="LogoSidebar">
                <img src="img/Ouros.png" alt="logo do ouros">
            </div>

            <nav class="Menu">
                <a href="inicio" class="MenuItem inicio">
                    <span class="Icone"><img src="img/inicio.svg" alt=""></span> Início
                </a>
                <a href="granjas" class="MenuItem">
                    <span class="Icone"><img src="img/granja.svg" alt=""></span> Granjas
                </a>
                <a href="lotes" class="MenuItem">
                        <span class="Icone"><img src="img/pintinho.svg" alt="Pintinho"></span> Lotes
                </a>
                <a href="funcionarios" class="MenuItem">
                    <span class="Icone"><img src="img/fucionarios.svg" alt=""></span> Funcionários
                </a>
                <a href="registros" class="MenuItem">
                    <span class="Icone"><img src="img/registros.svg" alt=""></span> Registros
                </a>
                <a href="metas" class="MenuItem">
                    <span class="Icone"><img src="img/metas.svg" alt=""></span> Metas
                </a>
            </nav>
        </div>

        <a href="perfil" class="Usuario">
          <img src="img/imagemDefault.png" alt="foto do usuário">
            <div class="UsuarioInfo">
                <strong>User</strong>
                <span>Admin</span>
            </div>
        </a>
    </aside>

    <div class="Conteudo ConteudoInicio">

        <div class="BannerInicio">
            <h2>Olá, UserVera</h2>
            <p class="SubtituloBanner">Aqui está o resumo deste mês</p>

            <div class="BannerStats">
                <div class="BannerStatItem">
                    <strong>128</strong>
                    <span>Granjas cadastradas</span>
                </div>
                <div class="BannerStatItem">
                    <strong>542</strong>
                    <span>Lotes em andamento</span>
                </div>
                <div class="BannerStatItem">
                    <strong>301</strong>
                    <span>Funcionários</span>
                </div>
                <div class="BannerStatItem">
                    <strong>45</strong>
                    <span>Metas em andamento</span>
                </div>
            </div>
        </div>

        <div class="CardsMetricas">
            <div class="CardMetrica">
                <div class="LabelMetrica">Frangos em produção</div>
                <div class="ValorMetrica">45.902</div>
                <span class="VariacaoMetrica positiva">&#9650; 5,4% vs mês anterior</span>
            </div>

            <div class="CardMetrica">
                <div class="LabelMetrica">Consumo de água</div>
                <div class="ValorMetrica">224.532 Hl</div>
                <span class="VariacaoMetrica negativa">&#9660; 1,2% vs mês anterior</span>
            </div>

            <div class="CardMetrica">
                <div class="LabelMetrica">Consumo de energia</div>
                <div class="ValorMetrica">1.098 GWh</div>
                <span class="VariacaoMetrica positiva">&#9650; 2,5% vs mês anterior</span>
            </div>

            <div class="CardMetrica">
                <div class="LabelMetrica">Metas concluídas</div>
                <div class="ValorMetrica">32</div>
                <span class="VariacaoMetrica negativa">&#9660; 12,1% vs mês anterior</span>
            </div>
        </div>

        <div class="SecaoTabela">
            <h2>Lotes recentes</h2>

            <div class="Painel">
                <div class="tabela">
                    <table aria-describedby="lotes-recentes-contagem">
                        <thead>
                        <tr>
                            <th>Lote</th>
                            <th>Granja</th>
                            <th>Entregues</th>
                            <th>Recebidas</th>
                            <th>Chegada</th>
                            <th>Ganho</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody id="tabela-lotes-recentes">
                        <!-- linhas preenchidas dinamicamente -->
                        </tbody>
                    </table>
                </div>

                <div class="RodapeTabela">
                    <span class="Contagem InicioContagem" id="lotes-recentes-contagem" aria-live="polite" aria-atomic="true"></span>

                    <div class="Paginacao InicioPaginacao" id="paginacao" role="group" aria-label="Paginação de lotes recentes">
                        <button type="button" class="seta" id="paginaAnterior" aria-label="Página anterior" disabled>&#9664;</button>
                        <button type="button" class="seta" id="proximaPagina" aria-label="Próxima página" disabled>&#9654;</button>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>

<script>
(function () {
    // Dados de exemplo; a paginação acompanha a quantidade efetivamente disponível.
    var lotesRecentes = [
        { lote: "#14", granja: "Granja sedentária", entregues: "5000", recebidas: "4.980", chegada: "05/09/2026", ganho: "6,4 kg", status: "recebido" },
        { lote: "#13", granja: "Granja Ouro Branco", entregues: "4000", recebidas: "---", chegada: "---", ganho: "---", status: "em-processo" },
        { lote: "#12", granja: "Granja Sonho de Valça", entregues: "3000", recebidas: "2789", chegada: "05/09/2026", ganho: "1,3 kg", status: "recebido" },
        { lote: "#11", granja: "Sonho diamante negro", entregues: "1200", recebidas: "---", chegada: "---", ganho: "---", status: "em-processo" },
        { lote: "#10", granja: "Granja preguiça", entregues: "10.000", recebidas: "9.000", chegada: "05/09/2026", ganho: "21,4 kg", status: "recebido" }
    ];
    var statusInfo = {
        "recebido": { texto: "Recebido", classe: "positivo" },
        "em-processo": { texto: "Em processo", classe: "neutro" }
    };
    var itensPorPagina = 5;
    var paginaAtual = 1;
    var corpoTabela = document.getElementById("tabela-lotes-recentes");
    var contagem = document.getElementById("lotes-recentes-contagem");
    var paginacao = document.getElementById("paginacao");
    var botaoAnterior = document.getElementById("paginaAnterior");
    var botaoProximo = document.getElementById("proximaPagina");

    function criarCelula(valor) {
        var celula = document.createElement("td");
        celula.textContent = valor;
        return celula;
    }

    function atualizarPaginacao(totalPaginas) {
        paginacao.querySelectorAll("button[data-pagina]").forEach(function (botao) {
            botao.remove();
        });

        for (var pagina = 1; pagina <= totalPaginas; pagina += 1) {
            var botao = document.createElement("button");
            botao.type = "button";
            botao.textContent = pagina;
            botao.dataset.pagina = pagina;
            botao.setAttribute("aria-label", "Página " + pagina);
            if (pagina === paginaAtual) {
                botao.className = "ativo";
                botao.setAttribute("aria-current", "page");
            }
            botao.addEventListener("click", function (evento) {
                paginaAtual = Number(evento.currentTarget.dataset.pagina);
                renderizarLotesRecentes();
            });
            paginacao.insertBefore(botao, botaoProximo);
        }

        botaoAnterior.disabled = paginaAtual === 1 || totalPaginas === 0;
        botaoProximo.disabled = paginaAtual === totalPaginas || totalPaginas === 0;
    }

    function renderizarLotesRecentes() {
        var totalPaginas = Math.ceil(lotesRecentes.length / itensPorPagina);
        paginaAtual = Math.min(paginaAtual, Math.max(1, totalPaginas));
        var inicio = (paginaAtual - 1) * itensPorPagina;
        var fim = Math.min(inicio + itensPorPagina, lotesRecentes.length);
        var fragmento = document.createDocumentFragment();

        if (lotesRecentes.length === 0) {
            var linhaVazia = document.createElement("tr");
            linhaVazia.className = "TabelaVazia";
            var celulaVazia = document.createElement("td");
            celulaVazia.colSpan = 7;
            celulaVazia.textContent = "Nenhum lote recente disponível.";
            linhaVazia.appendChild(celulaVazia);
            fragmento.appendChild(linhaVazia);
            contagem.textContent = "Nenhum lote recente disponível.";
        } else {
            lotesRecentes.slice(inicio, fim).forEach(function (lote) {
                var linha = document.createElement("tr");
                var informacoes = statusInfo[lote.status] || { texto: "Status indisponível", classe: "neutro" };
                linha.appendChild(criarCelula(lote.lote));
                linha.appendChild(criarCelula(lote.granja));
                linha.appendChild(criarCelula(lote.entregues));
                linha.appendChild(criarCelula(lote.recebidas));
                linha.appendChild(criarCelula(lote.chegada));
                linha.appendChild(criarCelula(lote.ganho));

                var celulaStatus = document.createElement("td");
                var status = document.createElement("span");
                status.className = "Status " + informacoes.classe;
                status.textContent = informacoes.texto;
                celulaStatus.appendChild(status);
                linha.appendChild(celulaStatus);
                fragmento.appendChild(linha);
            });
            contagem.textContent = "Mostrando " + (inicio + 1) + " a " + fim + " de " + lotesRecentes.length + " lotes";
        }

        corpoTabela.replaceChildren(fragmento);
        atualizarPaginacao(totalPaginas);
    }

    botaoAnterior.addEventListener("click", function () {
        if (paginaAtual > 1) {
            paginaAtual -= 1;
            renderizarLotesRecentes();
        }
    });
    botaoProximo.addEventListener("click", function () {
        var totalPaginas = Math.ceil(lotesRecentes.length / itensPorPagina);
        if (paginaAtual < totalPaginas) {
            paginaAtual += 1;
            renderizarLotesRecentes();
        }
    });

    renderizarLotesRecentes();
})();
</script>

</body>
</html>
