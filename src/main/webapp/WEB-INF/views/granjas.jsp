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
    <style>
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
                    <h1>Granjas</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja todas suas granjas cadastradas</p>
                </div>
            </div>
        </div>

        <div class="Painel">

            <div class="Filtros">
                <form action="${pageContext.request.contextPath}/granjas" method="get" class="FormBusca" id="formBuscaGranjas">
                    <div class="CampoBusca">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
                            <circle cx="11" cy="11" r="7"></circle>
                            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                        </svg>
                        <input type="text" name="buscaGranja" id="buscaGranja"
                               value="<c:out value='${param.buscaGranja}'/>" placeholder="Buscar...">
                    </div>

                    <div class="OrdenarPor">
                        <select id="ordenarPor" name="ordenarPor" aria-label="Ordenar granjas por"
                                onchange="this.form.submit()">
                            <option value="granja" ${empty param.ordenarPor || param.ordenarPor == 'granja' ? 'selected' : ''}>Ordenar por: Granja</option>
                            <option value="capacidade" ${param.ordenarPor == 'capacidade' ? 'selected' : ''}>Ordenar por: Capacidade</option>
                            <option value="sustentabilidade" ${param.ordenarPor == 'sustentabilidade' ? 'selected' : ''}>Ordenar por: Sustentabilidade</option>
                        </select>
                    </div>
                </form>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('${pageContext.request.contextPath}/granjas?acao=inserir')">+ Nova Granja</button>
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
                    <tbody id="tabela-granjas">
                    <c:forEach var="granja" items="${granjas}">
                        <tr>
                            <td><c:out value="${granja.nome}"/></td>
                            <td><c:out value="${granja.nomeResponsavel}"/></td>
                            <td><c:out value="${granja.localizacao}"/></td>
                            <td><c:out value="${granja.capacidadeDeAves}"/></td>
                            <td><fmt:formatNumber value="${granja.cgi}" minFractionDigits="2" maxFractionDigits="2"/></td>
                            <td>
                                <div class="ColunaAcoes">
                                    <button type="button" class="BotaoAcao editar" title="Editar"
                                            onclick="abrirFormulario('${pageContext.request.contextPath}/granjas?acao=editar&id=${granja.id}')">
                                        <img src="${pageContext.request.contextPath}/img/pencil.svg" alt="Editar" width="16" height="16">
                                    </button>
                                    <button type="button" class="BotaoAcao excluir" title="Excluir"
                                            onclick="abrirFormulario('${pageContext.request.contextPath}/granjas?acao=excluir&id=${granja.id}')">
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
                <span class="Contagem" id="contagemGranjas">
                    Mostrando ${paginacao.inicio} a ${paginacao.fim} de ${paginacao.total} granjas
                </span>

                <div class="Paginacao" id="paginacao">
                    <c:url var="urlAnterior" value="/granjas">
                        <c:param name="pagina" value="${paginacao.pagina - 1}" />
                        <c:param name="buscaGranja" value="${param.buscaGranja}" />
                        <c:param name="localizacao" value="${param.localizacao}" />
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
                        <c:url var="urlPagina" value="/granjas">
                            <c:param name="pagina" value="${numero}" />
                            <c:param name="buscaGranja" value="${param.buscaGranja}" />
                            <c:param name="localizacao" value="${param.localizacao}" />
                            <c:param name="ordenarPor" value="${param.ordenarPor}" />
                        </c:url>
                        <a class="${numero == paginacao.pagina ? 'ativo' : ''}"
                           href="${urlPagina}"
                           aria-current="${numero == paginacao.pagina ? 'page' : ''}"><c:out value="${numero}"/></a>
                    </c:forEach>

                    <c:url var="urlProxima" value="/granjas">
                        <c:param name="pagina" value="${paginacao.pagina + 1}" />
                        <c:param name="buscaGranja" value="${param.buscaGranja}" />
                        <c:param name="localizacao" value="${param.localizacao}" />
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
