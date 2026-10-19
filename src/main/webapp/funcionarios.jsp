<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Funcionários</title>
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
                <a href="inicio.jsp" class="MenuItem">
                    <span class="Icone"><img src="img/inicio.svg" alt=""></span> Início
                </a>
                <a href="granjas.jsp" class="MenuItem">
                    <span class="Icone"><img src="img/granja.svg" alt=""></span> Granjas
                </a>
                <a href="lotes.jsp" class="MenuItem">
                        <span class="Icone"><img src="img/pintinho.svg" alt="Pintinho"></span> Lotes
                </a>
                <a href="funcionarios" class="MenuItem funcionarios">
                    <span class="Icone"><img src="img/fucionarios.svg" alt=""></span> Funcionários
                </a>
                <a href="registros.jsp" class="MenuItem">
                    <span class="Icone"><img src="img/registros.svg" alt=""></span> Registros
                </a>
                <a href="metas.jsp" class="MenuItem">
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
                    <h1>Funcionários</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja todos os seus funcionários ativos</p>
                </div>
            </div>
        </div>

        <div class="Painel">

            <div class="Filtros">
                <div class="CampoBusca">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
                        <circle cx="11" cy="11" r="7"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                    <input type="search" id="buscaFuncionario" placeholder="Buscar funcionário..." aria-label="Buscar funcionários">
                </div>

                <div class="OrdenarPor">
                    <select id="filtroSetor" aria-label="Filtrar funcionários por setor">
                        <option value="">Todos os setores</option>
                    </select>
                </div>

                <div class="OrdenarPor">
                    <select id="ordenarPor" aria-label="Ordenar funcionários">
                        <option value="nome">Ordenar por: Nome</option>
                        <option value="setor">Ordenar por: Setor</option>
                    </select>
                </div>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('funcionario-novo.jsp')">
                    + Novo funcionário
                </button>
            </div>

            <div class="tabela">
                <table>
                    <caption class="VisuallyHidden">Lista de funcionários cadastrados</caption>
                    <thead>
                    <tr>
                        <th scope="col">Nome</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">CPF</th>
                        <th scope="col">Setor</th>
                    </tr>
                    </thead>
                    <tbody id="tabela-funcionarios">
                    <c:forEach var="funcionarios" items="${funcionarios}">
                        <tr>
                            <td><c:out value="${funcionarios.nome}"/></td>
                            <td><c:out value="${funcionarios.email}"/></td>
                            <td><c:out value="${funcionarios.cpf}"/></td>
                            <td><c:out value="${funcionarios.setor}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="RodapeTabela">
                <span class="Contagem" id="contagemFuncionarios" aria-live="polite"></span>

                <nav class="Paginacao" id="paginacao" aria-label="Páginas de funcionários">
                    <button type="button" class="seta" id="botaoAnterior" aria-label="Página anterior">&#9664;</button>
                    <button type="button" class="seta" id="botaoProximo" aria-label="Próxima página">&#9654;</button>
                </nav>
            </div>

        </div>
    </div>

</div>

<script>
(function () {
    var corpoTabela = document.getElementById("tabela-funcionarios");
    var busca = document.getElementById("buscaFuncionario");
    var filtroSetor = document.getElementById("filtroSetor");
    var ordenarPor = document.getElementById("ordenarPor");
    var contagem = document.getElementById("contagemFuncionarios");
    var paginacao = document.getElementById("paginacao");
    var botaoAnterior = document.getElementById("botaoAnterior");
    var botaoProximo = document.getElementById("botaoProximo");
    var linhasOriginais = Array.from(corpoTabela.querySelectorAll("tr"));
    var itensPorPagina = 5;
    var paginaAtual = 1;

    function preencherSetores() {
        var setores = Array.from(new Set(linhasOriginais
            .map(function (linha) { return linha.cells[3] ? linha.cells[3].textContent.trim() : ""; })
            .filter(Boolean)))
            .sort(function (a, b) { return a.localeCompare(b, "pt-BR", { sensitivity: "base" }); });

        setores.forEach(function (setor) {
            var opcao = document.createElement("option");
            opcao.value = setor;
            opcao.textContent = setor;
            filtroSetor.appendChild(opcao);
        });
    }

    function obterLinhasFiltradas() {
        var termo = busca.value.trim().toLocaleLowerCase("pt-BR");
        var setorSelecionado = filtroSetor.value;
        var colunaOrdenacao = ordenarPor.value === "setor" ? 3 : 0;

        return linhasOriginais.filter(function (linha) {
            var texto = linha.textContent.toLocaleLowerCase("pt-BR");
            var setor = linha.cells[3] ? linha.cells[3].textContent.trim() : "";
            return texto.includes(termo) && (!setorSelecionado || setor === setorSelecionado);
        }).sort(function (linhaA, linhaB) {
            var textoA = linhaA.cells[colunaOrdenacao].textContent.trim();
            var textoB = linhaB.cells[colunaOrdenacao].textContent.trim();
            return textoA.localeCompare(textoB, "pt-BR", { sensitivity: "base" });
        });
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
                renderizarTabela();
            });
            paginacao.insertBefore(botao, botaoProximo);
        }

        botaoAnterior.disabled = paginaAtual === 1;
        botaoProximo.disabled = paginaAtual === totalPaginas;
    }

    function renderizarTabela() {
        var linhas = obterLinhasFiltradas();
        var totalPaginas = Math.max(1, Math.ceil(linhas.length / itensPorPagina));
        paginaAtual = Math.min(paginaAtual, totalPaginas);

        var inicio = (paginaAtual - 1) * itensPorPagina;
        var fim = Math.min(inicio + itensPorPagina, linhas.length);
        var fragmento = document.createDocumentFragment();

        if (linhas.length === 0) {
            var linhaVazia = document.createElement("tr");
            linhaVazia.className = "TabelaVazia";
            var celulaVazia = document.createElement("td");
            celulaVazia.colSpan = 4;
            celulaVazia.textContent = "Nenhum funcionário encontrado.";
            linhaVazia.appendChild(celulaVazia);
            fragmento.appendChild(linhaVazia);
            contagem.textContent = "Nenhum funcionário encontrado";
        } else {
            linhas.slice(inicio, fim).forEach(function (linha) {
                fragmento.appendChild(linha);
            });
            contagem.textContent = "Mostrando " + (inicio + 1) + " a " + fim + " de " + linhas.length + " funcionários";
        }

        corpoTabela.replaceChildren(fragmento);
        atualizarPaginacao(totalPaginas);
    }

    busca.addEventListener("input", function () {
        paginaAtual = 1;
        renderizarTabela();
    });
    filtroSetor.addEventListener("change", function () {
        paginaAtual = 1;
        renderizarTabela();
    });
    ordenarPor.addEventListener("change", function () {
        paginaAtual = 1;
        renderizarTabela();
    });
    botaoAnterior.addEventListener("click", function () {
        if (paginaAtual > 1) {
            paginaAtual -= 1;
            renderizarTabela();
        }
    });
    botaoProximo.addEventListener("click", function () {
        var totalPaginas = Math.max(1, Math.ceil(obterLinhasFiltradas().length / itensPorPagina));
        if (paginaAtual < totalPaginas) {
            paginaAtual += 1;
            renderizarTabela();
        }
    });

    preencherSetores();
    renderizarTabela();
})();
</script>

<dialog class="FormularioModal" aria-label="Formulário" id="formularioModal"><iframe title="Formulário de funcionarios" class="FormularioFrame"></iframe></dialog>
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
