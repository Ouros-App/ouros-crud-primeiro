<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registros</title>
    <link rel="stylesheet" href="Style.css?v=9">
    <link rel="icon" type="image/png" href="img/Asa-icon.png">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>

<div class="Layout">

    <aside class="Sidebar">
        <a href="perfil" class="Usuario">
          <img src="img/imagemDefault.png" alt="foto do usuário">
            <div class="UsuarioInfo">
                <strong>User</strong>
                <span>Admin</span>
            </div>
        </a>

        <div class="SidebarCentro">
            <nav class="Menu">
                <a href="inicio" class="MenuItem">
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
                <a href="registros" class="MenuItem registros">
                    <span class="Icone"><img src="img/registros.svg" alt=""></span> Registros
                </a>
                <a href="metas" class="MenuItem">
                    <span class="Icone"><img src="img/metas.svg" alt=""></span> Metas
                </a>
            </nav>
        </div>

        <div class="LogoSidebar">
            <img src="img/Ouros.png" alt="logo do ouros">
        </div>
    </aside>

    <div class="Conteudo">

        <div class="hero">
            <div>
                <div class="Titulo">
                    <h1>Registros</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja todos os registros das granjas</p>
                </div>
            </div>
        </div>

        <div class="Painel">

            <div class="Alternador">
                <button type="button" class="AlternadorItem ativo" data-tipo="Agua" aria-pressed="true">💧 Água</button>
                <button type="button" class="AlternadorItem" data-tipo="Energia" aria-pressed="false">⚡ Energia</button>
            </div>

            <div class="Filtros">
                <div class="CampoBusca">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="11" cy="11" r="7"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                    <input type="search" id="buscaRegistro" placeholder="Buscar registros..." aria-label="Buscar registros">
                </div>

                <div class="OrdenarPor">
                    <select id="filtroLote" aria-label="Filtrar registros por lote">
                        <option value="">Todos os lotes</option>
                    </select>
                </div>

                <div class="OrdenarPor">
                    <select id="ordenarPor" aria-label="Ordenar registros">
                        <option value="data">Ordenar por: Data</option>
                        <option value="consumo">Ordenar por: Consumo</option>
                    </select>
                </div>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('registro-novo.jsp')">
                    + Novo registro
                </button>
            </div>

            <div class="tabela">
                <table>
                    <caption class="VisuallyHidden">Registros de consumo das granjas</caption>
                    <thead>
                    <tr>
                        <th scope="col">Lote</th>
                        <th scope="col">Data</th>
                        <th scope="col">Medição inicial</th>
                        <th scope="col">Medição final</th>
                        <th scope="col" id="tituloConsumo">Consumo (litros)</th>
                    </tr>
                    </thead>
                    <tbody id="tabela-registros">
                        <c:forEach var="registro" items="${registroAguas}">
                            <tr data-tipo="Agua">
                                <td>Lote #<c:out value="${registro.idLote}"/></td>
                                <td class="DadoData"><c:out value="${registro.dtRegistro}"/></td>
                                <td class="DadoNumerico"><c:out value="${registro.hidrometroInicio}"/></td>
                                <td class="DadoNumerico"><c:out value="${registro.hidrometroFinal}"/></td>
                                <td class="DadoNumerico"><c:out value="${registro.hidrometroFinal - registro.hidrometroInicio}"/> L</td>
                            </tr>
                        </c:forEach>
                        <c:forEach var="registro" items="${registroEnergias}">
                            <tr data-tipo="Energia">
                                <td>Lote #<c:out value="${registro.idLote}"/></td>
                                <td class="DadoData"><c:out value="${registro.dtRegistro}"/></td>
                                <td class="DadoIndisponivel" aria-label="Não se aplica">—</td>
                                <td class="DadoIndisponivel" aria-label="Não se aplica">—</td>
                                <td class="DadoNumerico"><c:out value="${registro.consumo}"/> kWh</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="RodapeTabela">
                <span class="Contagem" id="contagemRegistros" aria-live="polite"></span>

                <nav class="Paginacao" id="paginacao" aria-label="Páginas de registros">
                    <button type="button" class="seta" id="botaoAnterior" aria-label="Página anterior">&#9664;</button>
                    <button type="button" class="seta" id="botaoProximo" aria-label="Próxima página">&#9654;</button>
                </nav>
            </div>

        </div>
    </div>

</div>

<script>
(function () {
    var corpoTabela = document.getElementById("tabela-registros");
    var busca = document.getElementById("buscaRegistro");
    var filtroLote = document.getElementById("filtroLote");
    var ordenarPor = document.getElementById("ordenarPor");
    var tituloConsumo = document.getElementById("tituloConsumo");
    var contagem = document.getElementById("contagemRegistros");
    var paginacao = document.getElementById("paginacao");
    var botaoAnterior = document.getElementById("botaoAnterior");
    var botaoProximo = document.getElementById("botaoProximo");
    var botoesTipo = Array.from(document.querySelectorAll(".AlternadorItem"));
    var linhasOriginais = Array.from(corpoTabela.querySelectorAll("tr"));
    var comparador = new Intl.Collator("pt-BR", { sensitivity: "base", numeric: true });
    var tipoAtual = "Agua";
    var paginaAtual = 1;
    var itensPorPagina = 5;

    function preencherLotes() {
        var lotes = Array.from(new Set(linhasOriginais
            .map(function (linha) { return linha.cells[0] ? linha.cells[0].textContent.trim() : ""; })
            .filter(Boolean)))
            .sort(function (a, b) { return comparador.compare(a, b); });

        lotes.forEach(function (lote) {
            var opcao = document.createElement("option");
            opcao.value = lote;
            opcao.textContent = lote;
            filtroLote.appendChild(opcao);
        });
    }

    function obterRegistrosFiltrados() {
        var termo = busca.value.trim().toLocaleLowerCase("pt-BR");
        var loteSelecionado = filtroLote.value;
        var colunaOrdenacao = ordenarPor.value === "consumo" ? 4 : 1;

        return linhasOriginais.filter(function (linha) {
            var texto = linha.textContent.toLocaleLowerCase("pt-BR");
            var lote = linha.cells[0] ? linha.cells[0].textContent.trim() : "";
            return linha.dataset.tipo === tipoAtual && texto.includes(termo) && (!loteSelecionado || lote === loteSelecionado);
        }).sort(function (linhaA, linhaB) {
            var valorA = linhaA.cells[colunaOrdenacao].textContent.trim();
            var valorB = linhaB.cells[colunaOrdenacao].textContent.trim();
            if (colunaOrdenacao === 4) {
                return (parseFloat(valorA.replace(",", ".")) || 0) - (parseFloat(valorB.replace(",", ".")) || 0);
            }
            return comparador.compare(valorA, valorB);
        });
    }

    function atualizarPaginacao(totalPaginas) {
        paginacao.querySelectorAll("button[data-pagina]").forEach(function (botao) { botao.remove(); });
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
                renderizarTabela();
            });
            paginacao.insertBefore(botao, botaoProximo);
        }
        botaoAnterior.disabled = totalPaginas === 0 || paginaAtual === 1;
        botaoProximo.disabled = totalPaginas === 0 || paginaAtual === totalPaginas;
    }

    function renderizarTabela() {
        var registros = obterRegistrosFiltrados();
        var registrosDoTipo = linhasOriginais.filter(function (linha) { return linha.dataset.tipo === tipoAtual; });
        var totalPaginas = Math.ceil(registros.length / itensPorPagina);
        paginaAtual = Math.min(paginaAtual, Math.max(1, totalPaginas));
        var inicio = (paginaAtual - 1) * itensPorPagina;
        var fim = Math.min(inicio + itensPorPagina, registros.length);
        var fragmento = document.createDocumentFragment();
        var unidade = tipoAtual === "Agua" ? "litros" : "kWh";
        tituloConsumo.textContent = "Consumo (" + unidade + ")";

        if (registros.length === 0) {
            var linhaVazia = document.createElement("tr");
            linhaVazia.className = "TabelaVazia";
            var celulaVazia = document.createElement("td");
            celulaVazia.colSpan = 5;
            celulaVazia.textContent = registrosDoTipo.length === 0
                ? "Nenhum registro de " + (tipoAtual === "Agua" ? "água" : "energia") + " carregado."
                : "Nenhum registro encontrado para estes filtros.";
            linhaVazia.appendChild(celulaVazia);
            fragmento.appendChild(linhaVazia);
            contagem.textContent = celulaVazia.textContent;
        } else {
            registros.slice(inicio, fim).forEach(function (linha) { fragmento.appendChild(linha); });
            contagem.textContent = "Mostrando " + (inicio + 1) + " a " + fim + " de " + registros.length + " registros de " + (tipoAtual === "Agua" ? "água" : "energia") + " carregados";
        }

        corpoTabela.replaceChildren(fragmento);
        atualizarPaginacao(totalPaginas);
    }

    botoesTipo.forEach(function (botao) {
        botao.addEventListener("click", function () {
            tipoAtual = botao.dataset.tipo;
            botoesTipo.forEach(function (outroBotao) {
                var ativo = outroBotao === botao;
                outroBotao.classList.toggle("ativo", ativo);
                outroBotao.setAttribute("aria-pressed", String(ativo));
            });
            paginaAtual = 1;
            renderizarTabela();
        });
    });
    busca.addEventListener("input", function () { paginaAtual = 1; renderizarTabela(); });
    filtroLote.addEventListener("change", function () { paginaAtual = 1; renderizarTabela(); });
    ordenarPor.addEventListener("change", function () { paginaAtual = 1; renderizarTabela(); });
    botaoAnterior.addEventListener("click", function () {
        if (paginaAtual > 1) { paginaAtual -= 1; renderizarTabela(); }
    });
    botaoProximo.addEventListener("click", function () {
        var totalPaginas = Math.ceil(obterRegistrosFiltrados().length / itensPorPagina);
        if (paginaAtual < totalPaginas) { paginaAtual += 1; renderizarTabela(); }
    });

    preencherLotes();
    renderizarTabela();
})();
</script>

<dialog class="FormularioModal" aria-label="Formulário" id="formularioModal"><iframe title="Formulário de registros" class="FormularioFrame"></iframe></dialog>
<script>
(function () {
    const dialog = document.getElementById('formularioModal');
    const frame = dialog.querySelector('iframe');
    let previousOverflow = '';
    window.abrirFormulario = function (url) {
        frame.src = url;
        previousOverflow = document.body.style.overflow;
        document.body.style.overflow = 'hidden';
        dialog.showModal();
    };
    dialog.addEventListener('close', function () {
        document.body.style.overflow = previousOverflow;
        frame.removeAttribute('src');
    });
    window.addEventListener('message', function (event) {
        if (event.origin !== location.origin || event.source !== frame.contentWindow) return;
        if (event.data === 'ouros:fechar-formulario') dialog.close();
        if (event.data && event.data.type === 'ouros:altura-formulario' && Number.isFinite(event.data.height)) {
            frame.style.height = Math.min(Math.max(event.data.height, 300), 1200) + 'px';
        }
    });
})();
</script>
</body>
</html>
