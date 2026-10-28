<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Metas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/Asa-icon.png">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <style>
        /* Paginação padronizada com o restante do sistema */
        .RodapeTabela .Paginacao {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }

        .RodapeTabela .Paginacao a,
        .RodapeTabela .Paginacao .seta {
            box-sizing: border-box;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 34px;
            height: 36px;
            padding: 0 8px;
            border: none;
            border-radius: 9px;
            background: transparent;
            color: #17172f;
            font-family: inherit;
            font-size: 16px;
            font-weight: 500;
            line-height: 1;
            text-decoration: none !important;
            cursor: pointer;
        }

        .RodapeTabela .Paginacao a:hover:not(.ativo) {
            background: #f0f1f5;
        }

        .RodapeTabela .Paginacao a.ativo {
            background: #111025;
            color: #ffffff;
            font-weight: 600;
        }

        .RodapeTabela .Paginacao .seta {
            min-width: 28px;
            width: 28px;
            padding: 0;
            color: #9aa7bd;
            font-size: 13px;
        }

        .RodapeTabela .Paginacao .seta.desabilitada {
            cursor: default;
            pointer-events: none;
        }
    </style>
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
                <a href="granjas" class="MenuItem">
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
                <a href="metas" class="MenuItem metas">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/metas.svg" alt=""></span> Metas
                </a>

            </nav>
        </div>

        <a href="perfil" class="Usuario">
            <c:choose>
                <c:when test="${not empty administrador.fotoPerfilUrl}">
                    <img src="<c:out value='${administrador.fotoPerfilUrl}'/>" alt="foto do usuário">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/img/imagemDefault.png" alt="foto do usuário">
                </c:otherwise>
            </c:choose>
            <div class="UsuarioInfo">
                <strong><c:out value="${administrador.nome}" default="Usuário"/></strong>
                <span>Admin</span>
            </div>
        </a>
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
                <a href="${pageContext.request.contextPath}/metas?tipo=individual"
                   class="AlternadorItem ${tipo == 'individual' ? 'ativo' : ''}">Individual</a>
                <a href="${pageContext.request.contextPath}/metas?tipo=estadual"
                   class="AlternadorItem ${tipo == 'estadual' ? 'ativo' : ''}">Estadual</a>
            </div>

            <div class="Filtros">
                <form action="${pageContext.request.contextPath}/metas" method="get" class="FormBusca">
                    <input type="hidden" name="tipo" value="${tipo}">
                    <div class="CampoBusca">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="11" cy="11" r="7"></circle>
                            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                        </svg>
                        <input type="text" name="buscaMeta" id="buscaMeta" value="<c:out value='${param.buscaMeta}'/>" placeholder="Buscar...">
                    </div>

                    <div class="OrdenarPor">
                        <select id="ordenarPor" name="ordenarPor" aria-label="Ordenar metas por"
                                onchange="this.form.submit()">
                            <option value="titulo" ${empty param.ordenarPor || param.ordenarPor == 'titulo' ? 'selected' : ''}>Ordenar por: Título</option>
                            <option value="status" ${param.ordenarPor == 'status' ? 'selected' : ''}>Ordenar por: Status</option>
                        </select>
                    </div>
                </form>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('${pageContext.request.contextPath}/metas?acao=inserir&tipo=${tipo}')">
                    + Nova meta
                </button>
            </div>

            <div class="tabela">
                <table>
                    <thead>
                    <tr>
                        <th>Título</th>
                        <th>Granja</th>
                        <th>Alvo</th>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody id="tabela-metas">
                    <c:forEach var="m" items="${metas}">
                        <c:choose>
                            <c:when test="${m.status == 'FINALIZADA'}">
                                <c:set var="classeStatus" value="positivo"/>
                                <c:set var="textoStatus" value="Finalizada"/>
                            </c:when>
                            <c:when test="${m.status == 'EM_ANDAMENTO'}">
                                <c:set var="classeStatus" value="neutro"/>
                                <c:set var="textoStatus" value="Em andamento"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="classeStatus" value="negativo"/>
                                <c:set var="textoStatus" value="Não iniciada"/>
                            </c:otherwise>
                        </c:choose>
                        <tr>
                            <td><c:out value="${m.titulo}"/></td>
                            <td><c:out value="${m.nomeGranja}"/></td>
                            <td><c:out value="${m.valorAlvo}"/></td>
                            <td><span class="Status ${classeStatus}"><c:out value="${textoStatus}"/></span></td>
                            <td>
                                <div class="ColunaAcoes">
                                    <button type="button" class="BotaoAcao editar" title="Editar"
                                            onclick="abrirFormulario('${pageContext.request.contextPath}/metas?acao=editar&tipo=${tipo}&id=${m.id}')">
                                        <img src="${pageContext.request.contextPath}/img/pencil.svg" alt="Editar" width="16" height="16">
                                    </button>
                                    <button type="button" class="BotaoAcao excluir" title="Excluir"
                                            onclick="abrirFormulario('${pageContext.request.contextPath}/metas?acao=excluir&tipo=${tipo}&id=${m.id}')">
                                        <img src="${pageContext.request.contextPath}/img/bin.svg" alt="Excluir" width="16" height="16">
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="RodapeTabela">
                <span class="Contagem">
                    Mostrando ${paginacao.inicio} a ${paginacao.fim}
                    de ${paginacao.total} metas
                </span>

                <div class="Paginacao">
                    <c:url var="urlAnterior" value="/metas">
                        <c:param name="pagina" value="${paginacao.pagina - 1}" />
                        <c:param name="tipo" value="${tipo}" />
                        <c:param name="buscaMeta" value="${param.buscaMeta}" />
                        <c:param name="ordenarPor" value="${param.ordenarPor}" />
                    </c:url>
                    <c:choose>
                        <c:when test="${paginacao.temAnterior}">
                            <a class="seta" href="${urlAnterior}" aria-label="Página anterior">&#9664;</a>
                        </c:when>
                        <c:otherwise>
                            <span class="seta desabilitada" aria-label="Não há página anterior" aria-disabled="true">&#9664;</span>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="${paginacao.janelaInicio}" end="${paginacao.janelaFim}" var="numero">
                        <c:url var="urlPagina" value="/metas">
                            <c:param name="pagina" value="${numero}" />
                            <c:param name="tipo" value="${tipo}" />
                            <c:param name="buscaMeta" value="${param.buscaMeta}" />
                            <c:param name="ordenarPor" value="${param.ordenarPor}" />
                        </c:url>
                        <a class="${numero == paginacao.pagina ? 'ativo' : ''}"
                           href="${urlPagina}"
                           aria-label="Página ${numero}"
                            ${numero == paginacao.pagina ? 'aria-current="page"' : ''}><c:out value="${numero}"/></a>
                    </c:forEach>

                    <c:url var="urlProxima" value="/metas">
                        <c:param name="pagina" value="${paginacao.pagina + 1}" />
                        <c:param name="tipo" value="${tipo}" />
                        <c:param name="buscaMeta" value="${param.buscaMeta}" />
                        <c:param name="ordenarPor" value="${param.ordenarPor}" />
                    </c:url>
                    <c:choose>
                        <c:when test="${paginacao.temProxima}">
                            <a class="seta" href="${urlProxima}" aria-label="Próxima página">&#9654;</a>
                        </c:when>
                        <c:otherwise>
                            <span class="seta desabilitada" aria-label="Não há próxima página" aria-disabled="true">&#9654;</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </div>
    </div>

</div>

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
