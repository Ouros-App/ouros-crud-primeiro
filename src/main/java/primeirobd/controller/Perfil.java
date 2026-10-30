package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.mindrot.jbcrypt.BCrypt;
import primeirobd.model.Administrador;
import primeirobd.service.AdministradorDAO;
import primeirobd.service.TelefoneAdminDAO;
import primeirobd.utils.CloudinaryUtil;
import primeirobd.utils.Validador;

import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "Perfil", value = "/perfil")

/*
   Permite receber a foto enviada pelo formulário e limita o tamanho do arquivo
   e da requisição completa.
 */
@MultipartConfig(
        maxFileSize = 2 * 1024 * 1024,
        maxRequestSize = 3 * 1024 * 1024
)
public class Perfil extends HttpServlet {

    private static final long LIMITE_FOTO = 2 * 1024 * 1024;

    // Acesso aos dados do administrador.
    private final AdministradorDAO dao = new AdministradorDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        // Usa a sessão existente; sem ela, o usuário precisa fazer login.

        HttpSession sessao = request.getSession(false);

        if (sessao == null ||
                sessao.getAttribute("administradorId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        // Recupera da sessão o administrador que está acessando o perfil.
        int id = (Integer) sessao.getAttribute("administradorId");

        // Busca os dados atualizados do administrador.
        Administrador adm = dao.buscarPorId(id);

        // Sem cadastro correspondente, informa que o recurso não existe.
        if (adm == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Administrador não encontrado."
            );
            return;
        }

        // Envia os dados para a página de perfil.
        request.setAttribute("administrador", adm);

        request.getRequestDispatcher(
                "/WEB-INF/views/perfil.jsp"
        ).forward(request, response);
    }


    // Remove a foto ou salva os dados pessoais, a senha e uma nova foto.
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Recupera a sessão existente para autorizar a alteração.
        HttpSession sessao = request.getSession(false);

        // Sem uma sessão válida, não é permitido alterar o perfil.
        if (sessao == null ||
                sessao.getAttribute("administradorId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        // Identifica o administrador que está fazendo a alteração.
        int id = (Integer) sessao.getAttribute("administradorId");

        try {

            // Remove a foto somente quando o formulário envia a confirmação explícita.

            String remover = request.getParameter("removerFoto");

            if ("true".equals(remover)) {

                // Remove do cadastro a referência para a foto.
                dao.atualizarFotoPerfil(id, null);

                // Volta ao perfil informando que a foto foi removida.
                response.sendRedirect(
                        request.getContextPath() + "/perfil?foto=removida"
                );
                return;
            }

            // Lê os dados enviados pelo formulário.
            String nome = request.getParameter("nome");
            String telefone = request.getParameter("telefone");
            String senhaAtual = request.getParameter("senhaAtual");
            String senhaNova = request.getParameter("senhaNova");
            String senhaConfirma = request.getParameter("senhaConfirma");

            nome = nome == null ? "" : nome.trim();
            telefone = telefone == null ? "" : telefone.trim();

            // Busca os dados atuais, inclusive para conferir a senha.
            Administrador atual = dao.buscarPorId(id);

            if (atual == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Administrador não encontrado."
                );
                return;
            }

            // Valida tudo antes de alterar qualquer informação.
            StringBuilder erros = new StringBuilder();

            if (nome.isEmpty()) {
                erros.append("- Informe o nome.\n");
            }

            if (!telefone.isEmpty() && !Validador.isTelefoneValido(telefone)) {
                erros.append("- Telefone inválido.\n");
            }

            // Só troca a senha quando a nova senha ou a confirmação foi preenchida.
            boolean trocarSenha = (senhaNova != null && !senhaNova.isEmpty())
                    || (senhaConfirma != null && !senhaConfirma.isEmpty());

            if (trocarSenha) {
                if (senhaAtual == null || atual.getSenha() == null
                        || !BCrypt.checkpw(senhaAtual, atual.getSenha())) {
                    erros.append("- Senha atual incorreta.\n");
                }
                if (!Validador.isSenhaValida(senhaNova)) {
                    erros.append("- A nova senha não atende aos requisitos mínimos.\n");
                } else if (atual.getSenha() != null
                        && BCrypt.checkpw(senhaNova, atual.getSenha())) {
                    erros.append("- A nova senha deve ser diferente da atual.\n");
                }
                if (senhaNova == null || !senhaNova.equals(senhaConfirma)) {
                    erros.append("- A confirmação não confere com a nova senha.\n");
                }
            }

            // A foto é opcional, mas precisa ter tamanho e formato permitidos.
            Part parte = request.getPart("foto");
            boolean temFoto = parte != null && parte.getSize() > 0;

            if (temFoto) {
                if (parte.getSize() > LIMITE_FOTO) {
                    erros.append("- A imagem deve ter no máximo 2 MB.\n");
                }

                String tipo = parte.getContentType();
                if (tipo == null ||
                        !(tipo.equalsIgnoreCase("image/png")
                                || tipo.equalsIgnoreCase("image/jpeg"))) {
                    erros.append("- Envie uma imagem PNG ou JPG.\n");
                }
            }

            // Em caso de erro, retorna ao formulário com os dados já preenchidos.
            if (erros.length() > 0) {
                atual.setNome(nome);
                atual.setTelefone(telefone);

                request.setAttribute("erro", erros.toString());
                request.setAttribute("administrador", atual);
                request.getRequestDispatcher("/WEB-INF/views/perfil.jsp")
                        .forward(request, response);
                return;
            }

            // Envia a foto antes de gravar os demais dados, evitando um cadastro incompleto.
            String urlFoto = null;

            if (temFoto) {
                byte[] bytes;

                // O fluxo é fechado automaticamente ao terminar a leitura.
                try (InputStream entrada = parte.getInputStream()) {
                    bytes = entrada.readAllBytes();
                }

                // Envia a imagem e recebe o endereço que será salvo no cadastro.
                urlFoto = CloudinaryUtil.enviarFoto(bytes);
            }

            // Grava os dados validados no banco.
            dao.atualizarDados(id, nome);

            if (!telefone.isEmpty()) {
                new TelefoneAdminDAO().salvar(id, telefone);
            }

            if (trocarSenha) {
                dao.atualizarSenha(id, BCrypt.hashpw(senhaNova, BCrypt.gensalt()));
            }

            if (urlFoto != null) {
                dao.atualizarFotoPerfil(id, urlFoto);
            }

            // Conclui a alteração e informa o sucesso ao usuário.
            response.sendRedirect(
                    request.getContextPath() + "/perfil?salvo=1"
            );

        } catch (IllegalStateException e) {

            // Trata problemas no envio da foto ou nos limites da requisição.

            throw new ServletException(
                    "Verifique o tamanho da imagem e as configurações do Cloudinary.", e
            );

        } catch (Exception e) {

            // Trata outras falhas no envio da imagem ou na atualização do banco.
            throw new ServletException(
                    "Não foi possível salvar o perfil.", e
            );
        }
    }
}