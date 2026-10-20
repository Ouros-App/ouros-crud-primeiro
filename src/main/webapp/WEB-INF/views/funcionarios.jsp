
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Funcionários</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/Asa-icon.png">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>

<div class="Layout">

    <aside class="Sidebar">
        <div class="SidebarTopo">
            <div class="LogoSidebar">
                <img src="${pageContext.request.contextPath}/img/Ouros.png" alt="Logo do Ouros">
            </div>

            <nav class="Menu">
                <a href="${pageContext.request.contextPath}/inicio" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/inicio.svg" alt=""></span> Início
                </a>
                <a href="${pageContext.request.contextPath}/granjas" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/granja.svg" alt=""></span> Granjas
                </a>
                <a href="${pageContext.request.contextPath}/lotes" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/pintinho.svg" alt="Pintinho"></span> Lotes
                </a>
                <a href="${pageContext.request.contextPath}/funcionarios" class="MenuItem funcionarios">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/fucionarios.svg" alt=""></span> Funcionários
                </a>
                <a href="${pageContext.request.contextPath}/registros" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/registros.svg" alt=""></span> Registros
                </a>
                <a href="${pageContext.request.contextPath}/metas" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/metas.svg" alt=""></span> Metas
                </a>
            </nav>
        </div>

        <a href="${pageContext.request.contextPath}/perfil" class="Usuario">
            <img src="${pageContext.request.contextPath}/img/imagemDefault.png" alt="Foto do usuário">
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

            <!-- Busca, filtro e ordenação -->
            <form class="Filtros"
                  id="formFiltros"
                  action="${pageContext.request.contextPath}/funcionarios"
                  method="get">

                <div class="CampoBusca">
                    <svg viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         aria-hidden="true">
                        <circle cx="11" cy="11" r="7"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>

                    <input type="text"
                           id="buscaFuncionario"
                           name="busca"
                           placeholder="Buscar por nome..."
                           value="<c:out value='${busca}'/>">
                </div>

                <input type="text"
                       class="CampoSetor"
                       id="filtroSetor"
                       name="setor"
                       placeholder="Filtrar por setor..."
                       value="<c:out value='${setor}'/>">

                <div class="OrdenarPor">
                    <select id="ordenarPor"
                            name="ordenarPor"
                            aria-label="Ordenar funcionários por">

                        <option value="nome"
                            ${ordenarPor == 'nome' ? 'selected' : ''}>
                            Ordenar por: Nome
                        </option>

                        <option value="setor"
                            ${ordenarPor == 'setor' ? 'selected' : ''}>
                            Ordenar por: Setor
                        </option>
                    </select>
                </div>

                <button type="submit" class="BotaoNovo">
                    Filtrar
                </button>

                <button type="button"
                        class="BotaoNovo"
                        onclick="abrirFormulario('${pageContext.request.contextPath}/funcionarios?acao=novo')">
                    + Novo funcionário
                </button>

            </form>

            <!-- Tabela de funcionários -->
            <div class="tabela">
                <table>
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Setor</th>
                        <th>Email</th>
                        <th>Telefone</th>
                        <th>Ações</th>
                    </tr>
                    </thead>

                    <tbody id="tabela-funcionarios">

                    <c:choose>
                        <c:when test="${not empty funcionarios}">

                            <c:forEach var="f" items="${funcionarios}">

                                <tr data-id="${f.id}"
                                    data-nome="<c:out value='${f.nome}'/>"
                                    data-cpf="<c:out value='${f.cpf}'/>"
                                    data-setor="<c:out value='${f.setor}'/>"
                                    data-email="<c:out value='${f.email}'/>"
                                    data-telefone-corporativo="<c:out value='${f.telefoneCorporativo}'/>">

                                    <td><c:out value="${f.nome}"/></td>
                                    <td><c:out value="${f.setor}"/></td>
                                    <td><c:out value="${f.email}"/></td>
                                    <td><c:out value="${f.telefoneCorporativo}"/></td>

                                    <td>
                                        <div class="ColunaAcoes">

                                            <button type="button"
                                                    class="BotaoAcao editar"
                                                    title="Editar funcionário"
                                                    aria-label="Editar funcionário">
                                                <svg viewBox="0 0 24 24" fill="none"
                                                     stroke="currentColor"
                                                     stroke-width="2">
                                                    <path d="M12 20h9"></path>
                                                    <path d="M16.5 3.5a2.12 2.12 0 0 1 3 3L7 19l-4 1 1-4Z"></path>
                                                </svg>
                                            </button>

                                            <button type="button"
                                                    class="BotaoAcao excluir"
                                                    title="Excluir funcionário"
                                                    aria-label="Excluir funcionário"
                                                    data-id="${f.id}">
                                                <svg viewBox="0 0 24 24" fill="none"
                                                     stroke="currentColor"
                                                     stroke-width="2">
                                                    <polyline points="3 6 5 6 21 6"></polyline>
                                                    <path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"></path>
                                                    <path d="M10 11v6"></path>
                                                    <path d="M14 11v6"></path>
                                                    <path d="M9 6V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v2"></path>
                                                </svg>
                                            </button>

                                        </div>
                                    </td>
                                </tr>

                            </c:forEach>

                        </c:when>

                        <c:otherwise>
                            <tr>
                                <td colspan="5"
                                    style="text-align: center; padding: 24px;">
                                    Nenhum funcionário encontrado com esses filtros.
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>

                    </tbody>
                </table>
            </div>

            <!-- URLs da paginação: preservam os filtros atuais -->
            <c:url var="urlAnterior" value="/funcionarios">
                <c:param name="pagina" value="${paginacao.pagina - 1}"/>
                <c:param name="busca" value="${busca}"/>
                <c:param name="setor" value="${setor}"/>
                <c:param name="ordenarPor" value="${ordenarPor}"/>
            </c:url>

            <c:url var="urlProxima" value="/funcionarios">
                <c:param name="pagina" value="${paginacao.pagina + 1}"/>
                <c:param name="busca" value="${busca}"/>
                <c:param name="setor" value="${setor}"/>
                <c:param name="ordenarPor" value="${ordenarPor}"/>
            </c:url>

            <!-- Rodapé e controles de paginação -->
            <div class="RodapeTabela">

                <span class="Contagem" id="contagemFuncionarios">
                    Mostrando ${paginacao.inicio} a ${paginacao.fim}
                    de ${paginacao.total} funcionário(s)
                </span>

                <div class="Paginacao" id="paginacao">

                    <c:choose>
                        <c:when test="${paginacao.temAnterior}">
                            <button type="button"
                                    class="seta"
                                    aria-label="Página anterior"
                                    onclick="window.location.href='${urlAnterior}'">
                                &#9664;
                            </button>
                        </c:when>

                        <c:otherwise>
                            <button type="button"
                                    class="seta"
                                    aria-label="Página anterior"
                                    disabled>
                                &#9664;
                            </button>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach var="p"
                               begin="${paginacao.janelaInicio}"
                               end="${paginacao.janelaFim}">

                        <c:url var="urlPagina" value="/funcionarios">
                            <c:param name="pagina" value="${p}"/>
                            <c:param name="busca" value="${busca}"/>
                            <c:param name="setor" value="${setor}"/>
                            <c:param name="ordenarPor" value="${ordenarPor}"/>
                        </c:url>

                        <button type="button"
                                class="${p == paginacao.pagina ? 'ativo' : ''}"
                                aria-label="Página ${p}"
                                ${p == paginacao.pagina ? 'aria-current="page"' : ''}
                                onclick="window.location.href='${urlPagina}'">
                            <c:out value="${p}"/>
                        </button>

                    </c:forEach>

                    <c:choose>
                        <c:when test="${paginacao.temProxima}">
                            <button type="button"
                                    class="seta"
                                    aria-label="Próxima página"
                                    onclick="window.location.href='${urlProxima}'">
                                &#9654;
                            </button>
                        </c:when>

                        <c:otherwise>
                            <button type="button"
                                    class="seta"
                                    aria-label="Próxima página"
                                    disabled>
                                &#9654;
                            </button>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>

        </div>
    </div>
</div>

<!-- Modal dos formulários -->
<dialog class="FormularioModal"
        aria-label="Formulário"
        id="formularioModal">

    <iframe title="Formulário de funcionários"
            class="FormularioFrame"></iframe>

</dialog>

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
            if (event.origin !== location.origin ||
                event.source !== frame.contentWindow) {
                return;
            }

            if (event.data === 'ouros:fechar-formulario') {
                dialog.close();
            }

            if (event.data &&
                event.data.type === 'ouros:altura-formulario' &&
                Number.isFinite(event.data.height)) {

                frame.style.height =
                    Math.min(Math.max(event.data.height, 300), 1200) + 'px';
            }
        });
    })();

    // Prepara os dados para o formulário de edição.
    document.querySelectorAll('.editar').forEach(function (botao) {
        botao.addEventListener('click', function () {
            const linha = botao.closest('tr');

            const funcionario = {
                id: linha.dataset.id,
                nome: linha.dataset.nome,
                cpf: linha.dataset.cpf,
                setor: linha.dataset.setor,
                email: linha.dataset.email,
                telefone: linha.dataset.telefoneCorporativo
            };

            sessionStorage.setItem(
                'ouros-editor-funcionario',
                JSON.stringify(funcionario)
            );

            abrirFormulario(
                '${pageContext.request.contextPath}/funcionarios?acao=editar'
            );
        });
    });

    // A exclusão ainda precisa ser conectada ao servlet.
    document.querySelectorAll('.excluir').forEach(function (botao) {
        botao.addEventListener('click', function () {
            alert(
                'A exclusão ainda precisa ser conectada ao método delete do DAO e ao servlet.'
            );
        });
    });
</script>

</body>
</html>
