<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lotes</title>
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
                <a href="granjas" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/granja.svg" alt=""></span> Granjas
                </a>
                <a href="lotes" class="MenuItem lotes">
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
                    <h1>Lotes</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja todos os seus lotes cadastrados</p>
                </div>
            </div>
        </div>

        <div class="Painel">

            <div class="Filtros">
                <form action="lotes" method="get" class="FormBusca">
                    <div class="CampoBusca">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="11" cy="11" r="7"></circle>
                            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                        </svg>
                        <input type="text" name="buscaLote" id="buscaLote" value="${param.buscaLote}" placeholder="Buscar...">
                    </div>

                    <div class="OrdenarPor">
                        <select id="ordenarPor" name="ordenarPor" aria-label="Ordenar lotes por"
                                onchange="this.form.submit()">
                            <option value="chegada" ${empty param.ordenarPor || param.ordenarPor == 'chegada' ? 'selected' : ''}>Ordenar por: Chegada</option>
                            <option value="entregues" ${param.ordenarPor == 'entregues' ? 'selected' : ''}>Ordenar por: Entregues</option>
                            <option value="ganho" ${param.ordenarPor == 'ganho' ? 'selected' : ''}>Ordenar por: Ganho</option>
                        </select>
                    </div>
                </form>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('${pageContext.request.contextPath}/lotes?acao=inserir')">
                    + Novo lote
                </button>
            </div>

            <div class="tabela">
                <table>
                    <thead>
                    <tr>
                        <th>Lote</th>
                        <th>Granja</th>
                        <th>Entregues</th>
                        <th>Recebidas</th>
                        <th>Chegada</th>
                        <th>Ganho</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody id="tabela-lotes">
                        <c:forEach var="lote" items="${lotes}">
                            <tr>
                                <td><c:out value="${lote.id}"/></td>
                                <td><c:out value="${lote.nomeGranja}"/></td>
                                <td><c:out value="${lote.galinhasEntregadas}"/></td>
                                <td><c:out value="${lote.galinhasRecebidas}"/></td>
                                <td><c:out value="${lote.dtChegada}"/></td>
                                <td><c:out value="${lote.ganho}"/></td>
                                <td>
                                    <div class="ColunaAcoes">
                                        <button type="button" class="BotaoAcao editar" title="Editar"
                                                onclick="abrirFormulario('${pageContext.request.contextPath}/lotes?acao=editar&id=${lote.id}')">
                                            <img src="${pageContext.request.contextPath}/img/pencil.svg" alt="Editar" width="16" height="16">
                                        </button>
                                        <button type="button" class="BotaoAcao excluir" title="Excluir"
                                                onclick="abrirFormulario('${pageContext.request.contextPath}/lotes?acao=excluir&id=${lote.id}')">
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
                    de ${paginacao.total} lotes
                </span>

                <div class="Paginacao">
                    <c:url var="urlAnterior" value="/lotes">
                        <c:param name="pagina" value="${paginacao.pagina - 1}" />
                        <c:param name="buscaLote" value="${param.buscaLote}" />
                        <c:param name="ordenarPor" value="${param.ordenarPor}" />
                    </c:url>
                    <c:choose>
                        <c:when test="${paginacao.temAnterior}">
                            <a class="seta" href="${urlAnterior}" aria-label="Página anterior">&#9664;</a>
                        </c:when>
                        <c:otherwise>
                            <span class="seta desabilitada" aria-disabled="true">&#9664;</span>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="${paginacao.janelaInicio}" end="${paginacao.janelaFim}" var="numero">
                        <c:url var="urlPagina" value="/lotes">
                            <c:param name="pagina" value="${numero}" />
                            <c:param name="buscaLote" value="${param.buscaLote}" />
                            <c:param name="ordenarPor" value="${param.ordenarPor}" />
                        </c:url>
                        <a class="${numero == paginacao.pagina ? 'ativo' : ''}"
                           href="${urlPagina}"
                           aria-label="Página ${numero}"
                            ${numero == paginacao.pagina ? 'aria-current="page"' : ''}><c:out value="${numero}"/></a>
                    </c:forEach>

                    <c:url var="urlProxima" value="/lotes">
                        <c:param name="pagina" value="${paginacao.pagina + 1}" />
                        <c:param name="buscaLote" value="${param.buscaLote}" />
                        <c:param name="ordenarPor" value="${param.ordenarPor}" />
                    </c:url>
                    <c:choose>
                        <c:when test="${paginacao.temProxima}">
                            <a class="seta" href="${urlProxima}" aria-label="Próxima página">&#9654;</a>
                        </c:when>
                        <c:otherwise>
                            <span class="seta desabilitada" aria-disabled="true">&#9654;</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </div>
    </div>

</div>

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
