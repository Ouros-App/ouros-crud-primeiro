<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Empresas</title>
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
                <a href="lotes" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/pintinho.svg" alt="Pintinho"></span> Lotes
                </a>
                <a href="empresas" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/fucionarios.svg" alt=""></span> Funcionários
                </a>
                <a href="registros" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/registros.svg" alt=""></span> Registros
                </a>
                <a href="metas" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/metas.svg" alt=""></span> Metas
                </a>
                <a href="empresas" class="MenuItem empresa">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/empresa.svg"></span>
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
                    <h1>Empresas</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja todas as suas empresas ativas</p>
                </div>
            </div>
        </div>

        <div class="Painel">

            <div class="Filtros">
                <form action="${pageContext.request.contextPath}/empresas" method="get" class="FormBusca">
                    <div class="CampoBusca">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="11" cy="11" r="7"></circle>
                            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                        </svg>
                        <input type="text" name="buscaEmpresa" id="buscaEmpresa" value="${param.buscaEmpresa}" placeholder="Buscar...">
                    </div>

                    <select id="ordenarPor" name="ordenarPor" aria-label="Ordenar empresas por"
                            onchange="this.form.submit()">
                        <option value="nome" ${empty param.ordenarPor || param.ordenarPor == 'nome' ? 'selected' : ''}>Ordenar por: Nome</option>
                        <option value="cnpj" ${param.ordenarPor == 'cnpj' ? 'selected' : ''}>Ordenar por: CNPJ</option>
                    </select>
                </form>


                <button type="button" class="BotaoNovo" onclick="abrirForms('${pageContext.request.contextPath}/empresas?acao=novo')">
                    + Nova empresa
                </button>
            </div>

            <div class="tabela">
                <table>
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Telefone Corporativo</th>
                        <th>Email Corporativo</th>
                        <th>CNPJ</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody id="tabela-empresas">
                    <c:forEach var="empresa" items="${empresas}">
                        <tr>
                            <td><c:out value="${empresa.nome}"/></td>
                            <td><c:out value="${empresa.telefoneCorporativo}"/></td>
                            <td><c:out value="${empresa.emailCorporativo}"/></td>
                            <td><c:out value="${empresa.cnpj}"/></td>
                            <td></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="RodapeTabela">
                <span class="Contagem">
                    Mostrando ${paginacao.inicio} a ${paginacao.fim}
                    de ${paginacao.total} funcionários
                </span>

                <div class="Paginacao">
                    <c:url var="urlAnterior" value="/empresas">
                        <c:param name="pagina" value="${paginacao.pagina - 1}" />
                        <c:param name="buscaEmpresa" value="${param.buscaEmpresa}" />
                        <c:param name="buscarEm" value="${param.buscarEm}" />
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
                        <c:url var="urlPagina" value="/empresas">
                            <c:param name="pagina" value="${numero}" />
                            <c:param name="buscaEmpresa" value="${param.buscaEmpresa}" />
                            <c:param name="buscarEm" value="${param.buscarEm}" />
                        </c:url>
                        <a class="${numero == paginacao.pagina ? 'ativo' : ''}"
                           href="${urlPagina}"
                           aria-label="Página ${numero}"
                            ${numero == paginacao.pagina ? 'aria-current="page"' : ''}><c:out value="${numero}"/></a>
                    </c:forEach>

                    <c:url var="urlProxima" value="/empresas">
                        <c:param name="pagina" value="${paginacao.pagina + 1}" />
                        <c:param name="buscaEmpresa" value="${param.buscaEmpresa}" />
                        <c:param name="buscarEm" value="${param.buscarEm}" />
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

<dialog class="FormularioModal" aria-label="Formulário" id="formularioModal"><iframe title="Formulário de funcionários" class="FormularioFrame"></iframe></dialog> function abrirForms() {
<script>
    function abrirForms(url) {
        const d = document.getElementById('formularioModal');
        d.querySelector('iframe').src = url;
        d.showModal();
    }
</script>
</body>
</html>
