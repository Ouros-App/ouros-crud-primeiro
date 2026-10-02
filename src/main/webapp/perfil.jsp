<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meu Perfil</title>
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
                <a href="inicio.jsp" class="MenuItem">
                    <span class="Icone"><img src="img/inicio.svg" alt=""></span> Início
                </a>
                <a href="granjas.jsp" class="MenuItem">
                    <span class="Icone"><img src="img/granja.svg" alt=""></span> Granjas
                </a>
                <a href="lotes.jsp" class="MenuItem">
                        <span class="Icone"><img src="img/pintinho.svg" alt="Pintinho"></span> Lotess
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

        <div class="hero">
            <div>
                <div class="Titulo">
                    <h1>Meu Perfil</h1>
                </div>
                <div class="subtitulo">
                    <p>Veja e edite suas informações</p>
                </div>
            </div>
        </div>

        <div class="Painel">
            <form class="FormPainel" action="perfil" method="post" enctype="multipart/form-data" style="max-width: 100%;">

                <div class="PerfilAvatarArea">
                    <img class="PerfilFoto" src="img/imagemDefault.png" alt="foto do usuário" id="previewFoto">

                    <div class="PerfilAvatarAcoes">
                        <div class="PerfilAvatarBotoes">
                            <label class="BotaoNovo BotaoPequeno" for="inputFoto" style="cursor: pointer;">
                                Alterar foto
                            </label>
                            <button type="button" class="BotaoSecundario BotaoPequeno" id="botaoRemoverFoto">
                                Remover foto
                            </button>
                        </div>
                        <p>PNG ou JPG, até 2MB.</p>
                        <input type="file" id="inputFoto" name="foto" accept="image/png, image/jpeg" hidden>
                    </div>
                </div>

                <div class="SecaoTitulo">Dados pessoais</div>

                <div class="FormGrid">
                    <div class="Campo full">
                        <label class="CampoLabel" for="nome">Nome</label>
                        <input class="CampoInput" type="text" id="nome" name="nome" value="User" required>
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="cargo">Cargo</label>
                        <input class="CampoInput" type="text" id="cargo" name="cargo" value="Admin" required>
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="telefone">Telefone</label>
                        <input class="CampoInput" type="tel" id="telefone" name="telefone" placeholder="(11) 95743-8743">
                    </div>

                    <div class="Campo full">
                        <label class="CampoLabel" for="email">E-mail</label>
                        <input class="CampoInput" type="email" id="email" name="email" placeholder="seuemail@email.com" required>
                    </div>
                </div>

                <div class="SecaoTitulo">Alterar senha</div>

                <div class="FormGrid">
                    <div class="Campo full">
                        <label class="CampoLabel" for="senhaAtual">Senha atual</label>
                        <input class="CampoInput" type="password" id="senhaAtual" name="senhaAtual" placeholder="••••••••">
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="senhaNova">Nova senha</label>
                        <input class="CampoInput" type="password" id="senhaNova" name="senhaNova" placeholder="••••••••">
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="senhaConfirma">Confirmar nova senha</label>
                        <input class="CampoInput" type="password" id="senhaConfirma" name="senhaConfirma" placeholder="••••••••">
                    </div>
                </div>

                <div class="FormAcoes">
                    <a class="BotaoSecundario" href="inicio.jsp">Cancelar</a>
                    <button type="submit" class="BotaoNovo">Salvar alterações</button>
                </div>

            </form>
        </div>
    </div>
</div>

<script>
    var inputFoto = document.getElementById("inputFoto");
    var previewFoto = document.getElementById("previewFoto");
    var fotoOriginal = previewFoto.src;

    // Mostra a imagem escolhida na hora, antes mesmo de salvar
    inputFoto.addEventListener("change", function (e) {
        var arquivo = e.target.files[0];
        if (!arquivo) return;

        var leitor = new FileReader();
        leitor.onload = function (evento) {
            previewFoto.src = evento.target.result;
        };
        leitor.readAsDataURL(arquivo);
    });

    // Volta pra foto padrão (a remoção definitiva acontece só quando salvar o formulário)
    document.getElementById("botaoRemoverFoto").addEventListener("click", function () {
        inputFoto.value = "";
        previewFoto.src = fotoOriginal;
    });
</script>

</body>
</html>
