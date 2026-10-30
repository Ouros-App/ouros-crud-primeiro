<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Metas</title>
    <link rel="stylesheet" href="Style.css?v=8">
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
                <a href="registros" class="MenuItem">
                    <span class="Icone"><img src="img/registros.svg" alt=""></span> Registros
                </a>
                <a href="metas" class="MenuItem metas">
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
                    <h1>Metas</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja todas suas metas para as granjas</p>
                </div>
            </div>
        </div>

        <div class="Painel">

            <div class="Alternador">
                <button type="button" class="AlternadorItem ativo" data-tipo="Individual" aria-pressed="true">Individual</button>
                <button type="button" class="AlternadorItem" data-tipo="Estadual" aria-pressed="false">Estadual</button>
            </div>

            <div class="Filtros">
                <div class="CampoBusca">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="11" cy="11" r="7"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                    <input type="search" id="buscaMeta" placeholder="Buscar metas..." aria-label="Buscar metas">
                </div>

                <div class="OrdenarPor">
                    <select id="filtroAlvo" aria-label="Filtrar metas por granja ou estado">
                        <option value="">Todas as granjas e estados</option>
                    </select>
                </div>

                <div class="OrdenarPor">
                    <select id="ordenarPor" aria-label="Ordenar metas">
                        <option value="titulo">Ordenar por: Título</option>
                        <option value="status">Ordenar por: Status</option>
                    </select>
                </div>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('meta-nova.jsp')">
                    + Nova meta
                </button>
            </div>

            <div class="tabela">
                <table>
                    <caption class="VisuallyHidden">Lista de metas cadastradas</caption>
                    <thead>
                    <tr>
                        <th scope="col">Título</th>
                        <th scope="col">Granja ou estado</th>
                        <th scope="col">Indicador</th>
                        <th scope="col">Valor alvo</th>
                        <th scope="col">Status</th>
                    </tr>
                    </thead>
                    <tbody id="tabela-metas">
                        <c:forEach var="meta" items="${metas}">
                            <tr data-individual="${meta.individual}">
                                <td><c:out value="${meta.titulo}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${meta.individual}">Granja #<c:out value="${meta.idGranja}"/></c:when>
                                        <c:otherwise>Estado: <c:out value="${meta.estado}"/></c:otherwise>
                                    </c:choose>
                                </td>
                                <td><c:out value="${meta.tipoMeta}"/></td>
                                <td class="DadoNumerico"><c:out value="${meta.valorAlvo}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${meta.status eq 'Feito'}"><span class="Status positivo"><c:out value="${meta.status}"/></span></c:when>
                                        <c:when test="${meta.status eq 'Falha'}"><span class="Status negativo"><c:out value="${meta.status}"/></span></c:when>
                                        <c:otherwise><span class="Status neutro"><c:out value="${meta.status}"/></span></c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="RodapeTabela">
                <span class="Contagem" id="contagemMetas" aria-live="polite"></span>

                <nav class="Paginacao" id="paginacao" aria-label="Páginas de metas">
                    <button type="button" class="seta" id="botaoAnterior" aria-label="Página anterior">&#9664;</button>
                    <button type="button" class="seta" id="botaoProximo" aria-label="Próxima página">&#9654;</button>
                </nav>
            </div>

        </div>
    </div>

</div>

<script>
(function () {
    var corpoTabela = document.getElementById("tabela-metas");
    var busca = document.getElementById("buscaMeta");
    var filtroAlvo = document.getElementById("filtroAlvo");
    var ordenarPor = document.getElementById("ordenarPor");
    var contagem = document.getElementById("contagemMetas");
    var paginacao = document.getElementById("paginacao");
    var botaoAnterior = document.getElementById("botaoAnterior");
    var botaoProximo = document.getElementById("botaoProximo");
    var botoesTipo = Array.from(document.querySelectorAll(".AlternadorItem"));
    var linhasOriginais = Array.from(corpoTabela.querySelectorAll("tr"));
    var comparador = new Intl.Collator("pt-BR", { sensitivity: "base", numeric: true });
    var tipoAtual = "Individual";
    var paginaAtual = 1;
    var itensPorPagina = 5;

    function preencherAlvos() {
        var alvos = Array.from(new Set(linhasOriginais
            .map(function (linha) { return linha.cells[1] ? linha.cells[1].textContent.trim() : ""; })
            .filter(Boolean)))
            .sort(function (a, b) { return comparador.compare(a, b); });

        alvos.forEach(function (alvo) {
            var opcao = document.createElement("option");
            opcao.value = alvo;
            opcao.textContent = alvo;
            filtroAlvo.appendChild(opcao);
        });
    }

    function obterMetasFiltradas() {
        var termo = busca.value.trim().toLocaleLowerCase("pt-BR");
        var alvoSelecionado = filtroAlvo.value;
        var colunaOrdenacao = ordenarPor.value === "status" ? 4 : 0;

        return linhasOriginais.filter(function (linha) {
            var tipoIndividual = linha.dataset.individual === "true";
            var correspondeAoTipo = tipoAtual === "Individual" ? tipoIndividual : !tipoIndividual;
            var texto = linha.textContent.toLocaleLowerCase("pt-BR");
            var alvo = linha.cells[1] ? linha.cells[1].textContent.trim() : "";
            return correspondeAoTipo && texto.includes(termo) && (!alvoSelecionado || alvo === alvoSelecionado);
        }).sort(function (linhaA, linhaB) {
            return comparador.compare(
                linhaA.cells[colunaOrdenacao].textContent.trim(),
                linhaB.cells[colunaOrdenacao].textContent.trim()
            );
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
        var metas = obterMetasFiltradas();
        var totalPaginas = Math.ceil(metas.length / itensPorPagina);
        paginaAtual = Math.min(paginaAtual, Math.max(1, totalPaginas));
        var inicio = (paginaAtual - 1) * itensPorPagina;
        var fim = Math.min(inicio + itensPorPagina, metas.length);
        var fragmento = document.createDocumentFragment();

        if (metas.length === 0) {
            var linhaVazia = document.createElement("tr");
            linhaVazia.className = "TabelaVazia";
            var celulaVazia = document.createElement("td");
            celulaVazia.colSpan = 5;
            celulaVazia.textContent = linhasOriginais.length === 0
                ? "Nenhuma meta cadastrada."
                : "Nenhuma meta encontrada para estes filtros.";
            linhaVazia.appendChild(celulaVazia);
            fragmento.appendChild(linhaVazia);
            contagem.textContent = celulaVazia.textContent;
        } else {
            metas.slice(inicio, fim).forEach(function (linha) { fragmento.appendChild(linha); });
            contagem.textContent = "Mostrando " + (inicio + 1) + " a " + fim + " de " + metas.length + " metas";
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
    filtroAlvo.addEventListener("change", function () { paginaAtual = 1; renderizarTabela(); });
    ordenarPor.addEventListener("change", function () { paginaAtual = 1; renderizarTabela(); });
    botaoAnterior.addEventListener("click", function () {
        if (paginaAtual > 1) { paginaAtual -= 1; renderizarTabela(); }
    });
    botaoProximo.addEventListener("click", function () {
        var totalPaginas = Math.max(1, Math.ceil(obterMetasFiltradas().length / itensPorPagina));
        if (paginaAtual < totalPaginas) { paginaAtual += 1; renderizarTabela(); }
    });

    preencherAlvos();
    renderizarTabela();
})();
</script>

<dialog class="FormularioModal" aria-label="Formulário" id="formularioModal"><iframe title="Formulário de metas" class="FormularioFrame"></iframe></dialog>
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
