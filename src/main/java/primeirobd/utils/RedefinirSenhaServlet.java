package primeirobd.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import primeirobd.model.RecuperarSenha;
import primeirobd.service.ProprietarioGranjaDAO;
import primeirobd.service.RecuperarSenhaDAO;
import primeirobd.validadorREGEX.Validador;

import java.io.IOException;
import java.time.LocalDateTime;

/*
    esqueci minha senha etapa 2 :)
    o usuário chega aqui pelo link do email (/RedefinirSenha?token=...).

    RecuperarSenhaDAO     -> busca e marca o token como usado
    ProprietarioGranjaDAO -> atualiza a senha do usuário
    Validador             -> regra de senha (a mesma do cadastro)
 */
@WebServlet(name = "RedefinirSenha", value = "/RedefinirSenha")
public class RedefinirSenhaServlet extends HttpServlet {

    private static final String PAGINA_REDEFINIR = "/RedefinirSenha.jsp";

    private final RecuperarSenhaDAO recuperarDao = new RecuperarSenhaDAO();
    private final ProprietarioGranjaDAO usuarioDao = new ProprietarioGranjaDAO();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        // a página mostra o formulário ou "link inválido", conforme este atributo
        request.setAttribute("tokenValido", tokenEhValido(token));
        request.setAttribute("token", token);
        request.getRequestDispatcher(PAGINA_REDEFINIR).forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        String novaSenha = request.getParameter("novaSenha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        //confere o token de novo antes de trocar a senha pq nos nao confiamos no GET >:(
        RecuperarSenha registro = (token == null || token.isBlank())
                ? null
                : recuperarDao.buscarPorToken(token);

        if (!tokenEhValido(registro)) {
            request.setAttribute("tokenValido", false);
            request.getRequestDispatcher(PAGINA_REDEFINIR).forward(request, response);
            return;
        }

        // a partir daqui o token é válido: se der erro, volta para o formulário
        request.setAttribute("tokenValido", true);
        request.setAttribute("token", token);

        if (novaSenha == null || !novaSenha.equals(confirmarSenha)) {
            request.setAttribute("erro", "diferentes");
            request.getRequestDispatcher(PAGINA_REDEFINIR).forward(request, response);
            return;
        }

        if (!Validador.isSenhaValida(novaSenha)) {
            request.setAttribute("erro", "fraca");
            request.getRequestDispatcher(PAGINA_REDEFINIR).forward(request, response);
            return;
        }

        // senha criptografada, igual ao cadastro
        String senhaHash = BCrypt.hashpw(novaSenha, BCrypt.gensalt());
        usuarioDao.atualizarSenha(registro.getUsuarioId(), senhaHash);

        // o link só funciona uma vez
        recuperarDao.marcarComoUsado(token);

        response.sendRedirect("login.jsp?status=senhaalterada");
    }

    private boolean tokenEhValido(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }
        return tokenEhValido(recuperarDao.buscarPorToken(token));
    }

    // existe, não foi usado e não expirou
    private boolean tokenEhValido(RecuperarSenha registro) {
        return registro != null
                && !registro.isUsado()
                && registro.getExpiraEm().isAfter(LocalDateTime.now());
    }
}