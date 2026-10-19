<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lotes</title>
    <link rel="stylesheet" href="Style.css?v=7">
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
                <a href="inicio" class="MenuItem">
                    <span class="Icone"><img src="img/inicio.svg" alt=""></span> Início
                </a>
                <a href="granjas" class="MenuItem">
                    <span class="Icone"><img src="img/granja.svg" alt=""></span> Granjas
                </a>
                <a href="lotes" class="MenuItem lotes">
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

    <div class="Conteudo">

        <div class="hero">
            <div>
                <div class="Titulo">
                    <h1>Lotes</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja todos os seus lotes cadastrados</p>
                </div>
            </div>
        </div>

        <div class="Painel">

            <div class="Filtros">
                <div class="CampoBusca">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="11" cy="11" r="7"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                    <input type="search" id="buscaLote" placeholder="Buscar nesta página..." aria-label="Buscar lotes nesta página">
                </div>

                <div class="OrdenarPor">
                    <select id="filtroGranja" aria-label="Filtrar lotes por granja nesta página">
                        <option value="">Todas as granjas</option>
                    </select>
                </div>

                <div class="OrdenarPor">
                    <select id="ordenarPor" aria-label="Ordenar lotes nesta página">
                        <option value="chegada">Ordenar por: Chegada</option>
                        <option value="entregues">Ordenar por: Entregues</option>
                        <option value="ganho">Ordenar por: Ganho</option>
                    </select>
                </div>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('lote-novo.jsp')">
                    + Novo lote
                </button>
            </div>

            <div class="tabela">
                <table>
                    <caption class="VisuallyHidden">Lista de lotes cadastrados</caption>
                    <thead>
                    <tr>
                        <th scope="col">Lote</th>
                        <th scope="col">Granja</th>
                        <th scope="col">Entregues</th>
                        <th scope="col">Recebidas</th>
                        <th scope="col">Chegada</th>
                        <th scope="col">Ganho (kg)</th>
                    </tr>
                    </thead>
                    <tbody id="tabela-lotes">
                        <c:forEach var="lote" items="${lotes}">
                            <tr>
                                <td><c:out value="${lote.id}"/></td>
                                <td><c:out value="${lote.nomeGranja}"/></td>
                                <td class="DadoNumerico"><c:out value="${lote.galinhasEntregadas}"/></td>
                                <td class="DadoNumerico"><c:out value="${lote.galinhasRecebidas}"/></td>
                                <td class="DadoData"><c:out value="${lote.dtChegada}"/></td>
                                <td class="DadoNumerico"><c:out value="${lote.ganho}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="RodapeTabela">
                <span class="Contagem" id="contagemLotes" aria-live="polite">
                    <c:choose>
                        <c:when test="${paginacao.total == 0}">Nenhum lote cadastrado.</c:when>
                        <c:otherwise>Mostrando ${paginacao.inicio} a ${paginacao.fim} de ${paginacao.total} lotes</c:otherwise>
                    </c:choose>
                </span>

                <nav class="Paginacao" aria-label="Páginas de lotes">
                    <c:if test="${paginacao.temAnterior}">
                        <a class="seta" href="lotes?pagina=${paginacao.pagina - 1}" aria-label="Página anterior">&#9664;</a>
                    </c:if>
                    <c:forEach var="pagina" begin="${paginacao.janelaInicio}" end="${paginacao.janelaFim}">
                        <c:choose>
                            <c:when test="${pagina == paginacao.pagina}">
                                <span class="ativo" aria-current="page" aria-label="Página ${pagina}">${pagina}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="lotes?pagina=${pagina}" aria-label="Página ${pagina}">${pagina}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${paginacao.temProxima}">
                        <a class="seta" href="lotes?pagina=${paginacao.pagina + 1}" aria-label="Próxima página">&#9654;</a>
                    </c:if>
                </nav>
            </div>

        </div>
    </div>

</div>

<script>
(function () {
    var corpoTabela = document.getElementById("tabela-lotes");
    var busca = document.getElementById("buscaLote");
    var filtroGranja = document.getElementById("filtroGranja");
    var ordenarPor = document.getElementById("ordenarPor");
    var contagem = document.getElementById("contagemLotes");
    var resumoOriginal = contagem.textContent.trim();
    var linhasOriginais = Array.from(corpoTabela.querySelectorAll("tr"));
    var comparador = new Intl.Collator("pt-BR", { sensitivity: "base", numeric: true });

    function preencherGranjas() {
        var granjas = Array.from(new Set(linhasOriginais
            .map(function (linha) { return linha.cells[1] ? linha.cells[1].textContent.trim() : ""; })
            .filter(Boolean)))
            .sort(function (a, b) { return comparador.compare(a, b); });

        granjas.forEach(function (granja) {
            var opcao = document.createElement("option");
            opcao.value = granja;
            opcao.textContent = granja;
            filtroGranja.appendChild(opcao);
        });
    }

    function obterLinhasFiltradas() {
        var termo = busca.value.trim().toLocaleLowerCase("pt-BR");
        var granjaSelecionada = filtroGranja.value;
        var colunaOrdenacao = { chegada: 4, entregues: 2, ganho: 5 }[ordenarPor.value] || 4;

        return linhasOriginais.filter(function (linha) {
            var texto = linha.textContent.toLocaleLowerCase("pt-BR");
            var granja = linha.cells[1] ? linha.cells[1].textContent.trim() : "";
            return texto.includes(termo) && (!granjaSelecionada || granja === granjaSelecionada);
        }).sort(function (linhaA, linhaB) {
            var valorA = linhaA.cells[colunaOrdenacao].textContent.trim();
            var valorB = linhaB.cells[colunaOrdenacao].textContent.trim();
            if (colunaOrdenacao === 2 || colunaOrdenacao === 5) {
                return (parseFloat(valorA.replace(",", ".")) || 0) - (parseFloat(valorB.replace(",", ".")) || 0);
            }
            return comparador.compare(valorA, valorB);
        });
    }

    function renderizarTabela() {
        var linhasFiltradas = obterLinhasFiltradas();
        var fragmento = document.createDocumentFragment();
        var filtrosAtivos = busca.value.trim() || filtroGranja.value;

        if (linhasFiltradas.length === 0) {
            var linhaVazia = document.createElement("tr");
            linhaVazia.className = "TabelaVazia";
            var celulaVazia = document.createElement("td");
            celulaVazia.colSpan = 6;
            celulaVazia.textContent = linhasOriginais.length === 0
                ? "Nenhum lote cadastrado."
                : "Nenhum lote encontrado nesta página.";
            linhaVazia.appendChild(celulaVazia);
            fragmento.appendChild(linhaVazia);
            contagem.textContent = celulaVazia.textContent;
        } else {
            linhasFiltradas.forEach(function (linha) { fragmento.appendChild(linha); });
            contagem.textContent = filtrosAtivos
                ? linhasFiltradas.length + (linhasFiltradas.length === 1 ? " lote encontrado nesta página" : " lotes encontrados nesta página")
                : resumoOriginal;
        }

        corpoTabela.replaceChildren(fragmento);
    }

    busca.addEventListener("input", renderizarTabela);
    filtroGranja.addEventListener("change", renderizarTabela);
    ordenarPor.addEventListener("change", renderizarTabela);

    preencherGranjas();
    renderizarTabela();
})();
</script>

<dialog class="FormularioModal" aria-label="Formulário" id="formularioModal"><iframe title="Formulário de lotes" class="FormularioFrame"></iframe></dialog>
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
