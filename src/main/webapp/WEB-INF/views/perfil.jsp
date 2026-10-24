<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meu Perfil</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/Style.css">
    <link rel="icon" type="image/png"
          href="${pageContext.request.contextPath}/img/Asa-icon.png">

    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>

<body>

<%-- Usa a foto salva ou a imagem padrão. --%>
<c:choose>
    <c:when test="${not empty administrador.fotoPerfilUrl}">
        <c:set var="fotoPerfil"
               value="${administrador.fotoPerfilUrl}" />
    </c:when>
    <c:otherwise>
        <c:set var="fotoPerfil"
               value="${pageContext.request.contextPath}/img/imagemDefault.png" />
    </c:otherwise>
</c:choose>

<div class="Layout">

    <%-- BARRA LATERAL --%>
    <aside class="Sidebar">

        <div class="SidebarTopo">

            <div class="LogoSidebar">
                <img src="${pageContext.request.contextPath}/img/Ouros.png"
                     alt="Logo do Ouros">
            </div>

            <nav class="Menu">

                <a href="inicio" class="MenuItem">
                    <span class="Icone">
                        <img src="${pageContext.request.contextPath}/img/inicio.svg" alt="">
                    </span>
                    Início
                </a>

                <a href="granjas" class="MenuItem">
                    <span class="Icone">
                        <img src="${pageContext.request.contextPath}/img/granja.svg" alt="">
                    </span>
                    Granjas
                </a>

                <a href="lotes" class="MenuItem">
                    <span class="Icone">
                        <img src="${pageContext.request.contextPath}/img/pintinho.svg" alt="">
                    </span>
                    Lotes
                </a>

                <a href="funcionarios" class="MenuItem">
                    <span class="Icone">
                        <img src="${pageContext.request.contextPath}/img/fucionarios.svg" alt="">
                    </span>
                    Funcionários
                </a>

                <a href="registros" class="MenuItem">
                    <span class="Icone">
                        <img src="${pageContext.request.contextPath}/img/registros.svg" alt="">
                    </span>
                    Registros
                </a>

                <a href="metas" class="MenuItem">
                    <span class="Icone">
                        <img src="${pageContext.request.contextPath}/img/metas.svg" alt="">
                    </span>
                    Metas
                </a>

            </nav>
        </div>

        <%-- Foto e nome do administrador na barra lateral. --%>
        <a href="perfil" class="Usuario">
            <img src="<c:out value='${fotoPerfil}'/>"
                 alt="Foto do usuário">

            <div class="UsuarioInfo">
                <strong>
                    <c:out value="${administrador.nome}" default="Usuário"/>
                </strong>
                <span>Admin</span>
            </div>
        </a>

    </aside>

    <%-- CONTEÚDO PRINCIPAL --%>
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

            <form class="FormPainel"
                  action="perfil"
                  method="post"
                  enctype="multipart/form-data"
                  style="max-width: 100%;">

                <%-- FOTO DE PERFIL --%>
                <div class="PerfilAvatarArea">

                    <img class="PerfilFoto"
                         src="<c:out value='${fotoPerfil}'/>"
                         alt="Foto do usuário"
                         id="previewFoto">

                    <div class="PerfilAvatarAcoes">

                        <div class="PerfilAvatarBotoes">

                            <label class="BotaoNovo BotaoPequeno"
                                   for="inputFoto"
                                   style="cursor: pointer;">
                                Alterar foto
                            </label>

                            <button type="button"
                                    class="BotaoSecundario BotaoPequeno"
                                    id="botaoRemoverFoto">
                                Remover foto
                            </button>

                        </div>

                        <p>PNG ou JPG, até 2 MB.</p>

                        <input type="file"
                               id="inputFoto"
                               name="foto"
                               accept="image/png, image/jpeg"
                               hidden>

                        <%-- Informa ao servlet se a foto deve ser removida. --%>
                        <input type="hidden"
                               name="removerFoto"
                               id="removerFoto"
                               value="false">

                    </div>
                </div>

                <%-- DADOS PESSOAIS --%>
                <div class="SecaoTitulo">Dados pessoais</div>

                <div class="FormGrid">

                    <div class="Campo full">
                        <label class="CampoLabel" for="nome">Nome</label>
                        <input class="CampoInput"
                               type="text"
                               id="nome"
                               name="nome"
                               value="<c:out value='${administrador.nome}'/>"
                               required>
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="telefone">Telefone</label>
                        <input class="CampoInput"
                               type="tel"
                               id="telefone"
                               name="telefone"
                               value="<c:out value='${administrador.telefone}'/>"
                               placeholder="(11) 95743-8743">
                    </div>

                    <div class="Campo full">
                        <label class="CampoLabel" for="email">E-mail</label>
                        <input class="CampoInput"
                               type="email"
                               id="email"
                               name="email"
                               value="<c:out value='${administrador.email}'/>"
                               placeholder="seuemail@email.com"
                               required>
                    </div>

                </div>

                <%-- ALTERAÇÃO DE SENHA --%>
                <div class="SecaoTitulo">Alterar senha</div>

                <div class="FormGrid">

                    <div class="Campo full">
                        <label class="CampoLabel" for="senhaAtual">Senha atual</label>
                        <input class="CampoInput"
                               type="password"
                               id="senhaAtual"
                               name="senhaAtual"
                               placeholder="Digite sua senha atual"
                               autocomplete="current-password">
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="senhaNova">Nova senha</label>
                        <input class="CampoInput"
                               type="password"
                               id="senhaNova"
                               name="senhaNova"
                               placeholder="Digite a nova senha"
                               autocomplete="new-password">
                    </div>

                    <div class="Campo">
                        <label class="CampoLabel" for="senhaConfirma">
                            Confirmar nova senha
                        </label>
                        <input class="CampoInput"
                               type="password"
                               id="senhaConfirma"
                               name="senhaConfirma"
                               placeholder="Confirme a nova senha"
                               autocomplete="new-password">
                    </div>

                </div>

                <%-- AÇÕES --%>
                <div class="FormAcoes">

                    <a class="BotaoSecundario" href="inicio.jsp">
                        Cancelar
                    </a>

                    <button type="submit" class="BotaoNovo">
                        Salvar alterações
                    </button>

                </div>

            </form>
        </div>

    </div>
</div>

<script>
    const inputFoto = document.getElementById("inputFoto");
    const previewFoto = document.getElementById("previewFoto");
    const fotoSidebar = document.querySelector(".Sidebar .Usuario img");
    const botaoRemoverFoto = document.getElementById("botaoRemoverFoto");
    const campoRemoverFoto = document.getElementById("removerFoto");
    const formulario = document.querySelector(".FormPainel");

    const fotoPadrao =
        "${pageContext.request.contextPath}/img/imagemDefault.png";

    // Guarda a foto que estava salva ao abrir a página.
    const fotoOriginal = previewFoto.src;

    // Exibe a foto selecionada nas duas áreas.
    function atualizarFotos(src) {
        previewFoto.src = src;
        fotoSidebar.src = src;
    }

    // Mostra a prévia da imagem selecionada.
    inputFoto.addEventListener("change", function () {
        const arquivo = inputFoto.files[0];

        if (!arquivo) {
            return;
        }

        // Verifica o limite de 2 MB.
        if (arquivo.size > 2 * 1024 * 1024) {
            alert("A imagem deve ter no máximo 2 MB.");
            inputFoto.value = "";
            return;
        }

        // Aceita somente PNG e JPEG.
        if (!["image/png", "image/jpeg"].includes(arquivo.type)) {
            alert("Selecione uma imagem PNG ou JPG.");
            inputFoto.value = "";
            return;
        }

        // Uma nova imagem cancela a intenção de remover a foto.
        campoRemoverFoto.value = "false";

        const leitor = new FileReader();

        leitor.onload = function (evento) {
            atualizarFotos(evento.target.result);
        };

        leitor.readAsDataURL(arquivo);
    });

    // Confirma e envia imediatamente a solicitação de remoção.
    botaoRemoverFoto.addEventListener("click", function () {

        if (!confirm("Deseja remover sua foto de perfil?")) {
            return;
        }

        inputFoto.value = "";
        campoRemoverFoto.value = "true";

        atualizarFotos(fotoPadrao);

        formulario.submit();
    });
</script>

</body>
</html>