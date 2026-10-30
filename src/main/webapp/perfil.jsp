<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meu Perfil | Ouros</title>
    <link rel="stylesheet" href="Style.css?v=5">
    <link rel="icon" type="image/png" href="img/Asa-icon.png">
    <base href="${pageContext.request.contextPath}/">
</head>
<body>

<div class="Layout">

    <aside class="Sidebar">
        <a href="perfil.jsp" class="Usuario" aria-current="page">
            <img src="img/imagemDefault.png" alt="Foto do usuário">
            <div class="UsuarioInfo">
                <strong>User</strong>
                <span>Admin</span>
            </div>
        </a>

        <div class="SidebarCentro">
            <nav class="Menu" aria-label="Navegação principal">
                <a href="inicio.jsp" class="MenuItem">
                    <span class="Icone"><img src="img/inicio.svg" alt=""></span> Início
                </a>
                <a href="granjas.jsp" class="MenuItem">
                    <span class="Icone"><img src="img/granja.svg" alt=""></span> Granjas
                </a>
                <a href="lotes.jsp" class="MenuItem">
                    <span class="Icone"><img src="img/pintinho.svg" alt=""></span> Lotes
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

        <div class="LogoSidebar">
            <img src="img/Ouros.png" alt="Ouros">
        </div>
    </aside>

    <main class="Conteudo">

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

        <form class="FormPainel PerfilFormulario" action="perfil" method="post" enctype="multipart/form-data">

            <div class="PerfilAvatarArea">
                <img class="PerfilFoto" src="img/imagemDefault.png" alt="Prévia da foto do perfil" id="previewFoto">

                <div class="PerfilAvatarAcoes">
                    <div class="PerfilAvatarBotoes">
                        <input class="PerfilInputArquivo" type="file" id="inputFoto" name="foto"
                               accept="image/png, image/jpeg" aria-describedby="perfilFotoAjuda perfilFotoFeedback">
                        <label class="BotaoNovo BotaoPequeno PerfilFotoAcao" for="inputFoto">
                            Alterar foto
                        </label>
                        <button type="button" class="BotaoSecundario BotaoPequeno" id="botaoRemoverFoto"
                                aria-controls="previewFoto">
                            Remover foto
                        </button>
                    </div>
                    <p id="perfilFotoAjuda">PNG ou JPG, até 2 MB.</p>
                    <p class="PerfilFotoFeedback" id="perfilFotoFeedback" role="status" aria-live="polite"></p>
                </div>
            </div>

            <section class="PerfilSecao" aria-labelledby="dadosPessoaisTitulo">
                <h2 class="SecaoTitulo" id="dadosPessoaisTitulo">Dados pessoais</h2>

                <div class="FormGrid">
                    <div class="Campo full">
                        <label class="CampoLabel" for="nome">Nome</label>
                        <input class="CampoInput" type="text" id="nome" name="nome" value="User"
                               autocomplete="name" required>
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="cargo">Cargo</label>
                        <input class="CampoInput" type="text" id="cargo" name="cargo" value="Admin"
                               autocomplete="organization-title" required>
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="telefone">Telefone</label>
                        <input class="CampoInput" type="tel" id="telefone" name="telefone"
                               placeholder="(11) 95743-8743" autocomplete="tel">
                    </div>

                    <div class="Campo full">
                        <label class="CampoLabel" for="email">E-mail</label>
                        <input class="CampoInput" type="email" id="email" name="email"
                               placeholder="seuemail@email.com" autocomplete="email" required>
                    </div>
                </div>
            </section>

            <section class="PerfilSecao" aria-labelledby="senhaTitulo">
                <h2 class="SecaoTitulo" id="senhaTitulo">Alterar senha</h2>

                <div class="FormGrid">
                    <div class="Campo full">
                        <label class="CampoLabel" for="senhaAtual">Senha atual</label>
                        <input class="CampoInput" type="password" id="senhaAtual" name="senhaAtual"
                               placeholder="••••••••" autocomplete="current-password">
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="senhaNova">Nova senha</label>
                        <input class="CampoInput" type="password" id="senhaNova" name="senhaNova"
                               placeholder="••••••••" autocomplete="new-password">
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="senhaConfirma">Confirmar nova senha</label>
                        <input class="CampoInput" type="password" id="senhaConfirma" name="senhaConfirma"
                               placeholder="••••••••" autocomplete="new-password">
                    </div>
                </div>
            </section>

            <div class="FormAcoes">
                <a class="BotaoSecundario" href="inicio.jsp">Cancelar</a>
                <button type="submit" class="BotaoNovo">Salvar alterações</button>
            </div>

        </form>
    </main>
</div>

<script>
    (function () {
        var inputFoto = document.getElementById("inputFoto");
        var previewFoto = document.getElementById("previewFoto");
        var feedbackFoto = document.getElementById("perfilFotoFeedback");
        var fotoOriginal = previewFoto.src;
        var tiposPermitidos = ["image/png", "image/jpeg"];
        var tamanhoMaximo = 2 * 1024 * 1024;

        inputFoto.addEventListener("change", function () {
            var arquivo = inputFoto.files && inputFoto.files[0];
            if (!arquivo) {
                feedbackFoto.textContent = "";
                return;
            }

            if (arquivo.size > tamanhoMaximo) {
                inputFoto.value = "";
                previewFoto.src = fotoOriginal;
                feedbackFoto.textContent = "A imagem deve ter no máximo 2 MB.";
                return;
            }

            if (tiposPermitidos.indexOf(arquivo.type) === -1) {
                inputFoto.value = "";
                previewFoto.src = fotoOriginal;
                feedbackFoto.textContent = "Escolha uma imagem no formato PNG ou JPG.";
                return;
            }

            feedbackFoto.textContent = "";
            var leitor = new FileReader();
            leitor.onload = function (evento) {
                previewFoto.src = evento.target.result;
            };
            leitor.onerror = function () {
                inputFoto.value = "";
                previewFoto.src = fotoOriginal;
                feedbackFoto.textContent = "Não foi possível abrir essa imagem. Tente outro arquivo.";
            };
            leitor.readAsDataURL(arquivo);
        });

        document.getElementById("botaoRemoverFoto").addEventListener("click", function () {
            inputFoto.value = "";
            previewFoto.src = fotoOriginal;
            feedbackFoto.textContent = "A prévia voltou à imagem padrão.";
        });
    })();
</script>

</body>
</html>
