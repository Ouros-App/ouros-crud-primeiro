<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Granjas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/Asa-icon.png">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>

<div class="Layout">

    <aside class="Sidebar">
        <div class="SidebarTopo">
            <div class="LogoSidebar">
                <img src="${pageContext.request.contextPath}/img/Ouros.png" alt="logo do ouros">
            </div>

            <nav class="Menu">
                <a href="inicio" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/inicio.svg" alt=""></span> Início
                </a>
                <a href="granjas" class="MenuItem granjas">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/granja.svg" alt=""></span> Granjas
                </a>
                <a href="lotes" class="MenuItem">
                        <span class="Icone"><img src="${pageContext.request.contextPath}/img/pintinho.svg" alt="Pintinho"></span> Lotes
                </a>
                <a href="funcionarios" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/fucionarios.svg" alt=""></span> Funcionários
                </a>
                <a href="registros" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/registros.svg" alt=""></span> Registros
                </a>
                <a href="metas" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/metas.svg" alt=""></span> Metas
                </a>
            </nav>
        </div>

        <a href="perfil" class="Usuario">
           <img src="${pageContext.request.contextPath}/img/imagemDefault.png" alt="foto do usuário">
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
                    <input type="text" id="buscaGranja" placeholder="Buscar...">
                </div>

                <input type="text" class="CampoSetor" id="filtroLocalizacao" placeholder="">

                <div class="OrdenarPor">
                    <select id="ordenarPor">
                        <option value="granja">Ordenar por: Granja</option>
                        <option value="capacidade">Ordenar por: Capacidade</option>
                        <option value="sustentabilidade">Ordenar por: Sustentabilidade</option>
                    </select>
                </div>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('granja-nova.jsp')">
                    + Nova granja
                </button>
            </div>

            <div class="tabela">
                <table>
                    <thead>
                    <tr>
                        <th>Granja</th>
                        <th>Responsável</th>
                        <th>Localização</th>
                        <th>Capacidade</th>
                        <th>Sustentabilidade</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody id="tabela-granjas"></tbody>
                </table>
            </div>

            <div class="RodapeTabela">
                <span class="Contagem" id="contagemGranjas">mostrando 1 a 5 de 25 granjas</span>

                <div class="Paginacao" id="paginacao">
                    <button type="button" class="seta" id="botaoAnterior">&#9664;</button>
                    <button type="button" class="ativo" data-pagina="1">1</button>
                    <button type="button" data-pagina="2">2</button>
                    <button type="button" data-pagina="3">3</button>
                    <button type="button" data-pagina="4">4</button>
                    <button type="button" data-pagina="5">5</button>
                    <button type="button" class="seta" id="botaoProximo">&#9654;</button>
                </div>
            </div>

        </div>
    </div>

</div>

<script>
    var granjas = ${empty granjas ? '[]' : granjas};

    function esc(t) {
        return String(t == null ? "" : t).replace(/[&<>"']/g, function (c) {
            return {"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"}[c];
        });
    }

    function renderizarGranjas(lista) {
        var corpoTabela = document.getElementById("tabela-granjas");
        corpoTabela.innerHTML = "";

        lista.forEach(function (g) {
            var linha = document.createElement("tr");
            linha.innerHTML =
                "<td>" + esc(g.nome) + "</td>" +
                "<td>" + esc(g.nomeResponsavel) + "</td>" +
                "<td>" + esc(g.localizacao) + "</td>" +
                "<td>" + esc(g.capacidadeDeAves) + "</td>" +
                "<td>" + esc(g.cgi) + "</td>" +
                "<td>" +
                "<div class='ColunaAcoes'>" +
                "<button type='button' class='BotaoAcao editar' title='Editar'>" +
                "<svg viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2'>" +
                "<path d='M12 20h9'></path>" +
                "<path d='M16.5 3.5a2.12 2.12 0 0 1 3 3L7 19l-4 1 1-4Z'></path>" +
                "</svg></button>" +
                "<button type='button' class='BotaoAcao excluir' title='Excluir'>" +
                "<svg viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2'>" +
                "<polyline points='3 6 5 6 21 6'></polyline>" +
                "<path d='M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6'></path>" +
                "<path d='M10 11v6'></path><path d='M14 11v6'></path>" +
                "<path d='M9 6V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v2'></path>" +
                "</svg></button>" +
                "</div></td>";

            linha.querySelector('.editar').addEventListener('click', function () {
                sessionStorage.setItem('ouros-editor-granja', JSON.stringify(g));
                abrirFormulario('granja-nova.jsp?acao=editar');
            });
            corpoTabela.appendChild(linha);
        });
    }

    document.getElementById("buscaGranja").addEventListener("input", function (e) {
        var termo = e.target.value.toLowerCase();
        var filtradas = granjas.filter(function (g) {
            return (g.nome || "").toLowerCase().includes(termo);
        });
        renderizarGranjas(filtradas);
    });

    // ===== paginação (visual) =====
    var botoesPagina = document.querySelectorAll("#paginacao button[data-pagina]");
    var botaoAnterior = document.getElementById("botaoAnterior");
    var botaoProximo = document.getElementById("botaoProximo");
    var totalPaginas = botoesPagina.length;

    function marcarPaginaAtiva(numeroPagina) {
        botoesPagina.forEach(function (botao) {
            var pagina = parseInt(botao.getAttribute("data-pagina"), 10);
            botao.classList.toggle("ativo", pagina === numeroPagina);
        });
        botaoAnterior.disabled = numeroPagina === 1;
        botaoProximo.disabled = numeroPagina === totalPaginas;
    }
    botoesPagina.forEach(function (botao) {
        botao.addEventListener("click", function () {
            marcarPaginaAtiva(parseInt(botao.getAttribute("data-pagina"), 10));
        });
    });
    botaoAnterior.addEventListener("click", function () {
        var p = parseInt(document.querySelector("#paginacao button.ativo").getAttribute("data-pagina"), 10);
        if (p > 1) marcarPaginaAtiva(p - 1);
    });
    botaoProximo.addEventListener("click", function () {
        var p = parseInt(document.querySelector("#paginacao button.ativo").getAttribute("data-pagina"), 10);
        if (p < totalPaginas) marcarPaginaAtiva(p + 1);
    });
    marcarPaginaAtiva(1);

    renderizarGranjas(granjas);
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
<script>
    // Relaciona o value do <select> com o campo do objeto granja
    var camposOrdenacao = {
        granja: "nome",
        capacidade: "capacidadeDeAves",
        sustentabilidade: "cgi"
    };

    function aplicarFiltroEOrdenacao() {
        var termo = document.getElementById("buscaGranja").value.toLowerCase();
        var criterio = document.getElementById("ordenarPor").value;
        var campo = camposOrdenacao[criterio];

        // 1. filtra (cópia nova do array, o original não é alterado)
        var lista = granjas.filter(function (g) {
            return (g.nome || "").toLowerCase().includes(termo);
        });

        // 2. ordena
        if (campo) {
            lista.sort(function (a, b) {
                var valorA = a[campo];
                var valorB = b[campo];

                var numeroA = Number(String(valorA).replace(",", "."));
                var numeroB = Number(String(valorB).replace(",", "."));

                if (valorA !== "" && valorB !== "" && valorA != null && valorB != null
                    && !isNaN(numeroA) && !isNaN(numeroB)) {
                    return numeroA - numeroB;
                }
                return String(valorA == null ? "" : valorA)
                    .localeCompare(String(valorB == null ? "" : valorB), "pt-BR", { sensitivity: "base", numeric: true });
            });
        }

        // 3. desenha
        renderizarGranjas(lista);
    }

    document.getElementById("buscaGranja").addEventListener("input", aplicarFiltroEOrdenacao);
    document.getElementById("ordenarPor").addEventListener("change", aplicarFiltroEOrdenacao);

    aplicarFiltroEOrdenacao();
</script>
</body>
</html>
