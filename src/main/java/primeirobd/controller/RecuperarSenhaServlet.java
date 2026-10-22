package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.model.Administrador;
import primeirobd.service.AdministradorDAO;
import primeirobd.service.RecuperarSenhaDAO;
import primeirobd.utils.EmailService;
import primeirobd.utils.TokenUtil;
import primeirobd.utils.Validador;

import java.io.IOException;
import java.time.LocalDateTime;

/*
    Esqueci minha senha etapa 1 :(

    Validador             -> confere o formato do email (regex)
    AdministradorDAO -> busca o administrador pelo email
    TokenUtil             -> gera o token
    RecuperarSenhaDAO   -> guarda o token (mesmo molde do VerificacaoEmailDAO)
    EmailService          -> envia o email com o link
 */

@WebServlet(name = "RecuperarSenha", value = "/recuperar-senha")
public class RecuperarSenhaServlet extends HttpServlet {

    private static final String PAGINA_ESQUECI = "RecuperarSenha.jsp";
    private static final int VALIDADE_MINUTOS = 30;


    private final AdministradorDAO administradorDao = new AdministradorDAO();
    private final RecuperarSenhaDAO recuperarDao = new RecuperarSenhaDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // quem abrir a URL do servlet direto volta para o formulário
        response.sendRedirect(PAGINA_ESQUECI);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String email = request.getParameter("email");

            if (email == null || !Validador.isEmailValido(email.trim())) {
                response.sendRedirect(PAGINA_ESQUECI + "?erro=email");
                return;
            }
            email = email.trim();

            Administrador administrador = administradorDao.buscarPorEmail(email);

            // só envia se o usuário existe, mas a resposta é igual nos dois casos
            if (administrador != null) {
                try {
                    String token = TokenUtil.gerarToken();
                    recuperarDao.salvarToken(
                            administrador.getId(), token, LocalDateTime.now().plusMinutes(VALIDADE_MINUTOS));
                    EmailService.enviarEmailRecuperacao(email, token, VALIDADE_MINUTOS);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect(PAGINA_ESQUECI + "?erro=falha");
                    return;
                }
            }
        response.sendRedirect(PAGINA_ESQUECI + "?status=enviado");
    }
}
