package primeirobd.controller;
import primeirobd.model.Administrador;
import primeirobd.service.AdministradorDAO;
import primeirobd.service.TelefoneAdminDAO;
import primeirobd.utils.CloudinaryUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
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

        /*
           Verifica se existe uma sessão e se ela contem
           o ID do administrador autenticado.

           Se não estiver logado, redireciona para o login.
         */
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


    // usar o do post para remover ou atualizar a foto
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // recupera a sessão existente
        HttpSession sessao = request.getSession(false);

        // sem sessao não pode alterar a foto
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

                // ja que remoeu atualiza o banco, deixando a URL da foto como null.
                dao.atualizarFotoPerfil(id, null);

                // redireciona e informa que a foto foi removida
                response.sendRedirect(
                        request.getContextPath() + "/perfil?foto=removida"
                );
                return;
            }

            // telefone do admin
            String telefone = request.getParameter("telefone");
            if (telefone != null && !telefone.isBlank()) {
                if (!Validador.isTelefoneValido(telefone.trim())) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Telefone inválido.");
                    return;
                }
                new TelefoneAdminDAO().salvar(id, telefone.trim());
            }

            // recupera o arquivo enviado pelo campo foto
            Part parte = request.getPart("foto");

            if (parte == null || parte.getSize() == 0) {
                response.sendRedirect(
                        request.getContextPath() + "/perfil"
                );
                return;
            }

            /*
               verifica novamente o limite de 2 MB, mas que isso nao pode >:(
               a anotação MultipartConfig tambem define esse limite.
             */
            if (parte.getSize() > 2 * 1024 * 1024) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "A imagem deve ter no máximo 2 MB."
                );
                return;
            }

            // recupera o tipo, como .png
            String tipo = parte.getContentType();

            // aceita só png e jpeg
            if (tipo == null ||
                    !(tipo.equalsIgnoreCase("image/png")
                            || tipo.equalsIgnoreCase("image/jpeg"))) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Envie uma imagem PNG ou JPG."
                );
                return;
            }

            /*
               abre um fluxo de leitura para acessar os bytes
               do arquivo enviado.

               try-with-resources fecha o fluxo automaticamente.
             */
            byte[] bytes;

            try (InputStream entrada = parte.getInputStream()) {

                /*
                   lê o conteúdo inteiro do arquivo e guarda
                   seus bytes em um array.
                 */
                bytes = entrada.readAllBytes();
            }

            /*
               envia os bytes da imagem para o Cloudinary.
               o metodo retorna a URL da imagem armazenada.
             */
            String url = CloudinaryUtil.enviarFoto(bytes);

            /*
               salva a URL retornada pelo Cloudinary no banco,
               associando-a ao administrador identificado pelo ID.
             */
            dao.atualizarFotoPerfil(id, url);

            /*
               redireciona para o perfil e informa que a foto
               foi salva. EBAAA >:D
             */
            response.sendRedirect(
                    request.getContextPath() + "/perfil?foto=salva"
            );

        } catch (IllegalStateException e) {

            /*
               trata uma possível exceção relacionada ao estado
               da requisição multipart, incluindo limites de tamanho.
             */
            throw new ServletException(
                    "Verifique as configurações do Cloudinary.", e
            );

        } catch (Exception e) {

            /*
               captura outras exceções ocorridas durante o processo,
               como: falhas no envio ou na atualização do banco.
             */
            throw new ServletException(
                    "Não foi possível salvar a foto de perfil.", e
            );
        }
    }
}
