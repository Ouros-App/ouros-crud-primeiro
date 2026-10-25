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
  Permite receber requisições contendo arquivos enviados por formulários.

  maxFileSize: tamanho máximo de um arquivo = 2 MB.
  maxRequestSize: tamanho máximo da requisição inteira = 3 MB.
 */
@MultipartConfig(
        maxFileSize = 2 * 1024 * 1024,
        maxRequestSize = 3 * 1024 * 1024
)
public class Perfil extends HttpServlet {

    private static final long LIMITE_FOTO = 2 * 1024 * 1024;

    // dao do adm
    private final AdministradorDAO dao = new AdministradorDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        /*
           Procura uma sessão existente.
           O false impede que uma nova sessão seja criada
           caso o usuário ainda não tenha uma.
         */
        HttpSession sessao = request.getSession(false);

        if (sessao == null ||
                sessao.getAttribute("administradorId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        // Recupera o ID do administrador armazenado na sessão.
        int id = (Integer) sessao.getAttribute("administradorId");

        // Busca os dados do administrador no banco de dados.
        Administrador adm = dao.buscarPorId(id);

        // se não existir, então mostra 404
        if (adm == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Administrador não encontrado."
            );
            return;
        }

        // manda pro jsp
        request.setAttribute("administrador", adm);

        request.getRequestDispatcher(
                "/WEB-INF/views/perfil.jsp"
        ).forward(request, response);
    }


    // o post remove a foto ou salva dados pessoais, senha e foto
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // recupera a sessão existente
        HttpSession sessao = request.getSession(false);

        // sem sessao não pode alterar o perfil
        if (sessao == null ||
                sessao.getAttribute("administradorId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        // identifica qual administrador está fazendo a alteração.
        int id = (Integer) sessao.getAttribute("administradorId");

        try {

            /*
               verifica se o formulário solicitou a remoção da foto.
               o parâmetro removerFoto precisa ter o valor "true".
             */
            String remover = request.getParameter("removerFoto");

            if ("true".equals(remover)) {

                // atualiza o banco, deixando a URL da foto como null.
                dao.atualizarFotoPerfil(id, null);

                // redireciona e informa que a foto foi removida
                response.sendRedirect(
                        request.getContextPath() + "/perfil?foto=removida"
                );
                return;
            }

            // dados enviados pelo formulário
            String nome = request.getParameter("nome");
            String telefone = request.getParameter("telefone");
            String senhaAtual = request.getParameter("senhaAtual");
            String senhaNova = request.getParameter("senhaNova");
            String senhaConfirma = request.getParameter("senhaConfirma");

            nome = nome == null ? "" : nome.trim();
            telefone = telefone == null ? "" : telefone.trim();

            // dados atuais do banco (usados para conferir a senha atual)
            Administrador atual = dao.buscarPorId(id);

            if (atual == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Administrador não encontrado."
                );
                return;
            }

            // 1) VALIDAÇÕES (nada é gravado antes de passar por todas)
            StringBuilder erros = new StringBuilder();

            if (nome.isEmpty()) {
                erros.append("- Informe o nome.\n");
            }

            if (!telefone.isEmpty() && !Validador.isTelefoneValido(telefone)) {
                erros.append("- Telefone inválido.\n");
            }

            // só é troca de senha se a pessoa preencheu a nova senha ou a confirmação.
            // a senha atual sozinha pode vir do preenchimento automático do navegador.
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

            // foto (opcional): valida tamanho e tipo
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

            // se houve erro, volta para a página mantendo o que foi digitado
            if (erros.length() > 0) {
                atual.setNome(nome);
                atual.setTelefone(telefone);

                request.setAttribute("erro", erros.toString());
                request.setAttribute("administrador", atual);
                request.getRequestDispatcher("/WEB-INF/views/perfil.jsp")
                        .forward(request, response);
                return;
            }

            // 2) UPLOAD DA FOTO (antes de gravar, para não salvar pela metade)
            String urlFoto = null;

            if (temFoto) {
                byte[] bytes;

                // try-with-resources fecha o fluxo automaticamente
                try (InputStream entrada = parte.getInputStream()) {
                    bytes = entrada.readAllBytes();
                }

                // envia para o Cloudinary, que devolve a URL da imagem
                urlFoto = CloudinaryUtil.enviarFoto(bytes);
            }

            // 3) GRAVAÇÃO NO BANCO
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

            // sucesso
            response.sendRedirect(
                    request.getContextPath() + "/perfil?salvo=1"
            );

        } catch (IllegalStateException e) {

            /*
               exceção relacionada ao estado da requisição multipart,
               incluindo limites de tamanho, ou à configuração do Cloudinary.
             */
            throw new ServletException(
                    "Verifique o tamanho da imagem e as configurações do Cloudinary.", e
            );

        } catch (Exception e) {

            // outras falhas: envio da imagem ou atualização do banco
            throw new ServletException(
                    "Não foi possível salvar o perfil.", e
            );
        }
    }
}