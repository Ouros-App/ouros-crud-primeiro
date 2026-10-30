<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Granjas</title>
    <link rel="stylesheet" href="Style.css?v=6">
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
                <a href="granjas" class="MenuItem granjas">
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

        <div class="LogoSidebar">
            <img src="img/Ouros.png" alt="logo do ouros">
        </div>
    </aside>

    <div class="Conteudo">



        <div class="hero">
            <div>
                <div class="Titulo">
                    <h1>Granjas</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja todas suas granjas cadastradas</p>
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
                    <input type="search" id="buscaGranja" placeholder="Buscar nesta página..." aria-label="Buscar granjas nesta página">
                </div>

                <div class="OrdenarPor">
                    <select id="filtroRegiao" aria-label="Filtrar granjas por região nesta página">
                        <option value="">Todas as regiões</option>
                    </select>
                </div>

                <div class="OrdenarPor">
                    <select id="ordenarPor" aria-label="Ordenar granjas nesta página">
                        <option value="nome">Ordenar por: Granja</option>
                        <option value="capacidade">Ordenar por: Capacidade</option>
                        <option value="regiao">Ordenar por: Região</option>
                    </select>
                </div>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('granja-nova.jsp')">
                    + Nova granja
                </button>
            </div>

            <div class="tabela">
                <table>
                    <caption class="VisuallyHidden">Lista de granjas cadastradas</caption>
                    <thead>
                    <tr>
                        <th scope="col">Granja</th>
                        <th scope="col">Responsável</th>
                        <th scope="col">Localização</th>
                        <th scope="col">Capacidade (aves)</th>
                        <th scope="col">Região</th>
                    </tr>
                    </thead>
                    <tbody id="tabela-granjas">
                        <c:forEach var="granja" items="${granjas}">
                            <tr>
                                <td><c:out value="${granja.nome}"/></td>
                                <td><c:out value="${granja.nomeResponsavel}"/></td>
                                <td><c:out value="${granja.localizacao}"/></td>
                                <td><c:out value="${granja.capacidadeDeAves}"/></td>
                                <td><c:out value="${granja.regiao}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="RodapeTabela">
                <span class="Contagem" id="contagemGranjas" aria-live="polite">
                    <c:choose>
                        <c:when test="${paginacao.total == 0}">Nenhuma granja cadastrada.</c:when>
                        <c:otherwise>Mostrando ${paginacao.inicio} a ${paginacao.fim} de ${paginacao.total} granjas</c:otherwise>
                    </c:choose>
                </span>

                <nav class="Paginacao" aria-label="Páginas de granjas">
                    <c:if test="${paginacao.temAnterior}">
                        <a class="seta" href="granjas?pagina=${paginacao.pagina - 1}" aria-label="Página anterior">&#9664;</a>
                    </c:if>
                    <c:forEach var="pagina" begin="${paginacao.janelaInicio}" end="${paginacao.janelaFim}">
                        <c:choose>
                            <c:when test="${pagina == paginacao.pagina}">
                                <span class="ativo" aria-current="page" aria-label="Página ${pagina}">${pagina}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="granjas?pagina=${pagina}" aria-label="Página ${pagina}">${pagina}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${paginacao.temProxima}">
                        <a class="seta" href="granjas?pagina=${paginacao.pagina + 1}" aria-label="Próxima página">&#9654;</a>
                    </c:if>
                </nav>
            </div>

        </div>
    </div>

</div>

<script>
(function () {
    var corpoTabela = document.getElementById("tabela-granjas");
    var busca = document.getElementById("buscaGranja");
    var filtroRegiao = document.getElementById("filtroRegiao");
    var ordenarPor = document.getElementById("ordenarPor");
    var contagem = document.getElementById("contagemGranjas");
    var resumoOriginal = contagem.textContent.trim();
    var linhasOriginais = Array.from(corpoTabela.querySelectorAll("tr"));
    var comparador = new Intl.Collator("pt-BR", { sensitivity: "base", numeric: true });

    function preencherRegioes() {
        var regioes = Array.from(new Set(linhasOriginais
            .map(function (linha) { return linha.cells[4] ? linha.cells[4].textContent.trim() : ""; })
            .filter(Boolean)))
            .sort(function (a, b) { return comparador.compare(a, b); });

        regioes.forEach(function (regiao) {
            var opcao = document.createElement("option");
            opcao.value = regiao;
            opcao.textContent = regiao;
            filtroRegiao.appendChild(opcao);
        });
    }

    function obterLinhasFiltradas() {
        var termo = busca.value.trim().toLocaleLowerCase("pt-BR");
        var regiaoSelecionada = filtroRegiao.value;
        var colunaOrdenacao = { nome: 0, capacidade: 3, regiao: 4 }[ordenarPor.value] || 0;

        return linhasOriginais.filter(function (linha) {
            var texto = linha.textContent.toLocaleLowerCase("pt-BR");
            var regiao = linha.cells[4] ? linha.cells[4].textContent.trim() : "";
            return texto.includes(termo) && (!regiaoSelecionada || regiao === regiaoSelecionada);
        }).sort(function (linhaA, linhaB) {
            var valorA = linhaA.cells[colunaOrdenacao].textContent.trim();
            var valorB = linhaB.cells[colunaOrdenacao].textContent.trim();
            if (colunaOrdenacao === 3) {
                return (Number(valorA) || 0) - (Number(valorB) || 0);
            }
            return comparador.compare(valorA, valorB);
        });
    }

    function renderizarTabela() {
        var linhasFiltradas = obterLinhasFiltradas();
        var fragmento = document.createDocumentFragment();
        var filtrosAtivos = busca.value.trim() || filtroRegiao.value;

        if (linhasFiltradas.length === 0) {
            var linhaVazia = document.createElement("tr");
            linhaVazia.className = "TabelaVazia";
            var celulaVazia = document.createElement("td");
            celulaVazia.colSpan = 5;
            celulaVazia.textContent = linhasOriginais.length === 0
                ? "Nenhuma granja cadastrada."
                : "Nenhuma granja encontrada nesta página.";
            linhaVazia.appendChild(celulaVazia);
            fragmento.appendChild(linhaVazia);
            contagem.textContent = celulaVazia.textContent;
        } else {
            linhasFiltradas.forEach(function (linha) { fragmento.appendChild(linha); });
            contagem.textContent = filtrosAtivos
                ? linhasFiltradas.length + (linhasFiltradas.length === 1 ? " granja encontrada nesta página" : " granjas encontradas nesta página")
                : resumoOriginal;
        }

        corpoTabela.replaceChildren(fragmento);
    }

    busca.addEventListener("input", renderizarTabela);
    filtroRegiao.addEventListener("change", renderizarTabela);
    ordenarPor.addEventListener("change", renderizarTabela);

    preencherRegioes();
    renderizarTabela();
})();
</script>
<dialog class="FormularioModal" aria-label="Formulário" id="formularioModal"><iframe title="Formulário de granjas" class="FormularioFrame"></iframe></dialog>
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
