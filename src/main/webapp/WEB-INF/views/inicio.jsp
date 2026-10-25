<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="pt_BR"/>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Início</title>
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
                <a href="inicio" class="MenuItem inicio">
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

        <div class="BannerInicio">
            <h2>Olá, <c:out value="${administrador.nome}" default="Usuário"/></h2>
            <p class="SubtituloBanner">Aqui está o resumo deste mês</p>

            <div class="BannerStats">
                <div class="BannerStatItem">
                    <strong>${totalGranjas}</strong>
                    <span>Granjas cadastradas</span>
                </div>
                <div class="BannerStatItem">
                    <strong>${totalLotes}</strong>
                    <span>Lotes</span>
                </div>
                <div class="BannerStatItem">
                    <strong>${totalFuncionarios}</strong>
                    <span>Funcionários</span>
                </div>
                <div class="BannerStatItem">
                    <strong>${totalMetas}</strong>
                    <span>Metas</span>
                </div>
            </div>
        </div>

        <div class="CardsMetricas">
            <div class="CardMetrica">
                <div class="LabelMetrica">Frangos em produção</div>
                <div class="ValorMetrica"><fmt:formatNumber value="${frangosEmProducao}" pattern="#,##0"/></div>
            </div>

            <div class="CardMetrica">
                <div class="LabelMetrica">Consumo de água</div>
                <div class="ValorMetrica"><fmt:formatNumber value="${consumoAgua}" pattern="#,##0"/> m³</div>
            </div>

            <div class="CardMetrica">
                <div class="LabelMetrica">Consumo de energia</div>
                <div class="ValorMetrica"><fmt:formatNumber value="${consumoEnergia}" pattern="#,##0"/> kWh</div>
            </div>

            <div class="CardMetrica">
                <div class="LabelMetrica">Metas concluídas</div>
                <div class="ValorMetrica"><fmt:formatNumber value="${metasConcluidas}" pattern="#,##0"/></div>
            </div>
        </div>

        <div class="SecaoTabela">
            <h2>Lotes recentes</h2>

            <div class="Painel">
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
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="l" items="${lotes}">
                            <tr>
                                <td>#${l.id}</td>
                                <td><c:out value="${l.nomeGranja}"/></td>
                                <td>${l.galinhasEntregadas}</td>
                                <td>${l.galinhasRecebidas}</td>
                                <td><c:out value="${empty l.dtChegada ? '---' : l.dtChegada}"/></td>
                                <td>${l.ganho} kg</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty l.dtChegada}">
                                            <span class="Status neutro">Em processo</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="Status positivo">Recebido</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty lotes}">
                            <tr><td colspan="7">Nenhum lote cadastrado.</td></tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>

                <div class="RodapeTabela">
                    <span class="Contagem">
                        Mostrando ${paginacao.inicio} a ${paginacao.fim} de ${paginacao.total} lotes
                    </span>

                    <div class="Paginacao">
                        <c:url var="urlAnterior" value="/inicio">
                            <c:param name="pagina" value="${paginacao.pagina - 1}" />
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
                            <c:url var="urlPagina" value="/inicio">
                                <c:param name="pagina" value="${numero}" />
                            </c:url>
                            <a class="${numero == paginacao.pagina ? 'ativo' : ''}"
                               href="${urlPagina}"
                               aria-label="Página ${numero}"
                               ${numero == paginacao.pagina ? 'aria-current="page"' : ''}><c:out value="${numero}"/></a>
                        </c:forEach>

                        <c:url var="urlProxima" value="/inicio">
                            <c:param name="pagina" value="${paginacao.pagina + 1}" />
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

</div>

</body>
</html>
