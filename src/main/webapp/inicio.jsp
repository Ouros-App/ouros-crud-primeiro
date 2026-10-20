<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Início</title>
    <link rel="stylesheet" href="Style.css?v=4">
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
                <a href="inicio.jsp" class="MenuItem inicio">
                    <span class="Icone"><img src="img/inicio.svg" alt=""></span> Início
                </a>
                <a href="granjas.jsp" class="MenuItem">
                    <span class="Icone"><img src="img/granja.svg" alt=""></span> Granjas
                </a>
                <a href="lotes.jsp" class="MenuItem">
                        <span class="Icone"><img src="img/pintinho.svg" alt="Pintinho"></span> Lotes
                </a>
                <a href="funcionarios.jsp" class="MenuItem">
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

        <a href="perfil.jsp" class="Usuario">
          <img src="img/imagemDefault.png" alt="foto do usuário">
            <div class="UsuarioInfo">
                <strong>User</strong>
                <span>Admin</span>
            </div>
        </a>
    </aside>

    <div class="Conteudo">

        <div class="BannerInicio">
            <h2>Olá, UserVera</h2>
            <p class="SubtituloBanner">Aqui está o resumo deste mês</p>

            <div class="BannerStats">
                <div class="BannerStatItem">
                    <strong>128</strong>
                    <span>Granjas cadastradas</span>
                </div>
                <div class="BannerStatItem">
                    <strong>542</strong>
                    <span>Lotes em andamento</span>
                </div>
                <div class="BannerStatItem">
                    <strong>301</strong>
                    <span>Funcionários</span>
                </div>
                <div class="BannerStatItem">
                    <strong>45</strong>
                    <span>Metas em andamento</span>
                </div>
            </div>
        </div>

        <div class="CardsMetricas">
            <div class="CardMetrica">
                <div class="LabelMetrica">Frangos em produção</div>
                <div class="ValorMetrica">45.902</div>
                <span class="VariacaoMetrica positiva">&#9650; 5,4% vs mês anterior</span>
            </div>

            <div class="CardMetrica">
                <div class="LabelMetrica">Consumo de água</div>
                <div class="ValorMetrica">224.532 Hl</div>
                <span class="VariacaoMetrica negativa">&#9660; 1,2% vs mês anterior</span>
            </div>

            <div class="CardMetrica">
                <div class="LabelMetrica">Consumo de energia</div>
                <div class="ValorMetrica">1.098 GWh</div>
                <span class="VariacaoMetrica positiva">&#9650; 2,5% vs mês anterior</span>
            </div>

            <div class="CardMetrica">
                <div class="LabelMetrica">Metas concluídas</div>
                <div class="ValorMetrica">32</div>
                <span class="VariacaoMetrica negativa">&#9660; 12,1% vs mês anterior</span>
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
                        <tbody id="tabela-lotes-recentes">
                        <!-- linhas preenchidas dinamicamente -->
                        </tbody>
                    </table>
                </div>

                <div class="RodapeTabela">
                    <span class="Contagem">mostrando 1 a 5 de 25 lotes</span>

                    <div class="Paginacao" id="paginacao">
                        <button type="button" class="seta" id="paginaAnterior">&#9664;</button>
                        <button type="button" class="ativo" data-pagina="1">1</button>
                        <button type="button" data-pagina="2">2</button>
                        <button type="button" data-pagina="3">3</button>
                        <button type="button" data-pagina="4">4</button>
                        <button type="button" data-pagina="5">5</button>
                        <button type="button" class="seta" id="proximaPagina">&#9654;</button>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>

<script>
    // Exemplo de dados - substitua pela chamada real ao backend
    var lotesRecentes = [
        { lote: "#14", granja: "Granja sedentária", entregues: "5000", recebidas: "4.980", chegada: "05/09/2026", ganho: "6,4 kg", status: "recebido" },
        { lote: "#13", granja: "Granja Ouro Branco", entregues: "4000", recebidas: "---", chegada: "---", ganho: "---", status: "em-processo" },
        { lote: "#12", granja: "Granja Sonho de Valça", entregues: "3000", recebidas: "2789", chegada: "05/09/2026", ganho: "1,3 kg", status: "recebido" },
        { lote: "#11", granja: "Sonho diamante negro", entregues: "1200", recebidas: "---", chegada: "---", ganho: "---", status: "em-processo" },
        { lote: "#10", granja: "Granja preguiça", entregues: "10.000", recebidas: "9.000", chegada: "05/09/2026", ganho: "21,4 kg", status: "recebido" }
    ];

    var statusInfo = {
        "recebido": { texto: "Recebido", classe: "positivo" },
        "em-processo": { texto: "Em processo", classe: "neutro" }
    };

    function renderizarLotesRecentes(lista) {
        var corpoTabela = document.getElementById("tabela-lotes-recentes");
        corpoTabela.innerHTML = "";

        lista.forEach(function (l) {
            var s = statusInfo[l.status];
            var linha = document.createElement("tr");
            linha.innerHTML =
                "<td>" + l.lote + "</td>" +
                "<td>" + l.granja + "</td>" +
                "<td>" + l.entregues + "</td>" +
                "<td>" + l.recebidas + "</td>" +
                "<td>" + l.chegada + "</td>" +
                "<td>" + l.ganho + "</td>" +
                "<td><span class='Status " + s.classe + "'>" + s.texto + "</span></td>";
            corpoTabela.appendChild(linha);
        });
    }

    document.querySelectorAll("#paginacao button[data-pagina]").forEach(function (botao) {
        botao.addEventListener("click", function () {
            document.querySelectorAll("#paginacao button[data-pagina]").forEach(function (b) {
                b.classList.remove("ativo");
            });
            botao.classList.add("ativo");
            // aqui entra a chamada para buscar a página correspondente no backend
        });
    });

    renderizarLotesRecentes(lotesRecentes);
</script>

</body>
</html>
