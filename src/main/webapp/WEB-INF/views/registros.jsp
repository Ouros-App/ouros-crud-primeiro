<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registros</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/Asa-icon.png">
    <style>
        .AlternadorItem {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border: 0;
            background: transparent;
            color: inherit;
            text-decoration: none;
            cursor: pointer;
        }

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
            text-decoration: none;
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

        .TabelaVazia {
            padding: 28px;
            text-align: center;
            color: #718096;
        }
    </style>
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
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/inicio.svg" alt=""></span>
                    Início
                </a>
                <a href="${pageContext.request.contextPath}/granjas" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/granja.svg" alt=""></span>
                    Granjas
                </a>
                <a href="${pageContext.request.contextPath}/lotes" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/pintinho.svg" alt="Pintinho"></span>
                    Lotes
                </a>
                <a href="${pageContext.request.contextPath}/funcionarios" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/fucionarios.svg" alt=""></span>
                    Funcionários
                </a>
                <a href="${pageContext.request.contextPath}/registros" class="MenuItem registros">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/registros.svg" alt=""></span>
                    Registros
                </a>
                <a href="${pageContext.request.contextPath}/metas" class="MenuItem">
                    <span class="Icone"><img src="${pageContext.request.contextPath}/img/metas.svg" alt=""></span>
                    Metas
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
                    <h1>Registros</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja todos os registros das granjas</p>
                </div>
            </div>
        </div>

        <div class="Painel">

            <div class="Alternador">
                <c:url var="urlAgua" value="/registros">
                    <c:param name="tipo" value="agua"/>
                    <c:param name="pagina" value="1"/>
                </c:url>

                <c:url var="urlEnergia" value="/registros">
                    <c:param name="tipo" value="energia"/>
                    <c:param name="pagina" value="1"/>
                </c:url>

                <a href="${urlAgua}"
                   class="AlternadorItem ${tipoAtual == 'agua' ? 'ativo' : ''}">
                    💧 Água
                </a>

                <a href="${urlEnergia}"
                   class="AlternadorItem ${tipoAtual == 'energia' ? 'ativo' : ''}">
                    ⚡ Energia
                </a>
            </div>

            <div class="Filtros">
                <div class="CampoBusca">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
                        <circle cx="11" cy="11" r="7"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                    <input type="text" id="buscaRegistro" placeholder="Buscar..." autocomplete="off">
                </div>

                <div class="OrdenarPor">
                    <select id="ordenarPor" disabled>
                        <option>Ordenar por: Data</option>
                    </select>
                </div>

                <button type="button" class="BotaoNovo" onclick="abrirFormulario('registro-novo.jsp')">
                    + Novo registro
                </button>
            </div>

            <div class="tabela">
                <table>
                    <thead>
                    <tr>
                        <th>Granja</th>
                        <th>Lote</th>
                        <th>Data</th>
                        <c:choose>
                            <c:when test="${tipoAtual == 'agua'}">
                                <th>Hidrômetro inicial</th>
                                <th>Hidrômetro final</th>
                            </c:when>
                            <c:otherwise>
                                <th>Consumo</th>
                                <th>Unidade</th>
                            </c:otherwise>
                        </c:choose>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                    </thead>

                    <tbody id="tabela-registros">
                    <c:choose>
                        <c:when test="${tipoAtual == 'agua'}">
                            <c:choose>
                                <c:when test="empty registroAguas">
                                    <tr>
                                        <td colspan="7" class="TabelaVazia">Nenhum registro de água encontrado.</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="registro" items="${registroAguas}">
                                        <tr>
                                            <td>-</td>
                                            <td><c:out value="${registro.idLote}"/></td>
                                            <td><c:out value="${registro.dtRegistro}"/></td>
                                            <td><c:out value="${registro.hidrometroInicio}"/></td>
                                            <td><c:out value="${registro.hidrometroFinal}"/></td>
                                            <td>
                                                <span class="Status neutro">-</span>
                                            </td>
                                            <td></td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </c:when>

                        <c:otherwise>
                            <c:choose>
                                <c:when test="${empty registroEnergias}">
                                    <tr>
                                        <td colspan="7" class="TabelaVazia">Nenhum registro de energia encontrado.</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="registro" items="${registroEnergias}">
                                        <tr>
                                            <td>-</td>
                                            <td><c:out value="${registro.idLote}"/></td>
                                            <td><c:out value="${registro.dtRegistro}"/></td>
                                            <td><c:out value="${registro.consumo}"/></td>
                                            <td>-</td>
                                            <td>
                                                <span class="Status neutro">-</span>
                                            </td>
                                            <td></td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>

            <div class="RodapeTabela">
                <span class="Contagem" id="contagemRegistros">
                    Mostrando ${paginacao.inicio} a ${paginacao.fim}
                    de ${paginacao.total}
                    registros de ${tipoAtual == 'agua' ? 'água' : 'energia'}
                </span>

                <div class="Paginacao" id="paginacao">
                    <c:url var="urlAnterior" value="/registros">
                        <c:param name="tipo" value="${tipoAtual}"/>
                        <c:param name="pagina" value="${paginacao.pagina - 1}"/>
                    </c:url>

                    <c:choose>
                        <c:when test="${paginacao.temAnterior}">
                            <a class="seta" href="${urlAnterior}" aria-label="Página anterior">&#9664;</a>
                        </c:when>
                        <c:otherwise>
                            <span class="seta desabilitada" aria-label="Não há página anterior">&#9664;</span>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="${paginacao.janelaInicio}"
                               end="${paginacao.janelaFim}"
                               var="numero">
                        <c:url var="urlPagina" value="/registros">
                            <c:param name="tipo" value="${tipoAtual}"/>
                            <c:param name="pagina" value="${numero}"/>
                        </c:url>

                        <a class="${numero == paginacao.pagina ? 'ativo' : ''}"
                           href="${urlPagina}"
                           aria-current="${numero == paginacao.pagina ? 'page' : ''}">
                            <c:out value="${numero}"/>
                        </a>
                    </c:forEach>

                    <c:url var="urlProxima" value="/registros">
                        <c:param name="tipo" value="${tipoAtual}"/>
                        <c:param name="pagina" value="${paginacao.pagina + 1}"/>
                    </c:url>

                    <c:choose>
                        <c:when test="${paginacao.temProxima}">
                            <a class="seta" href="${urlProxima}" aria-label="Próxima página">&#9654;</a>
                        </c:when>
                        <c:otherwise>
                            <span class="seta desabilitada" aria-label="Não há próxima página">&#9654;</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </div>
    </div>
</div>

<dialog class="FormularioModal" aria-label="Formulário" id="formularioModal">
    <iframe title="Formulário de registros" class="FormularioFrame"></iframe>
</dialog>

<script>
    (function () {
        const campoBusca = document.getElementById('buscaRegistro');
        const tabela = document.getElementById('tabela-registros');

        campoBusca.addEventListener('input', function () {
            const termo = this.value.trim().toLowerCase();

            tabela.querySelectorAll('tr').forEach(function (linha) {
                linha.style.display = linha.textContent.toLowerCase().includes(termo) ? '' : 'none';
            });
        });

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

            if (event.data === 'ouros:fechar-formulario') {
                dialog.close();
            }

            if (event.data && event.data.type === 'ouros:altura-formulario'
                && Number.isFinite(event.data.height)) {
                frame.style.height = Math.min(Math.max(event.data.height, 300), 1200) + 'px';
            }
        });
    })();
</script>

</body>
</html>
