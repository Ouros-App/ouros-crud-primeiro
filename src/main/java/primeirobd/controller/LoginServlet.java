
package primeirobd.controller;

import org.mindrot.jbcrypt.BCrypt;

import primeirobd.model.Administrador;
import primeirobd.service.AdministradorDAO;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final AdministradorDAO dao =
            new AdministradorDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {

        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        String contexto = request.getContextPath();

        // Verifica se os campos foram preenchidos.
        if (email == null || email.isBlank()
                || senha == null || senha.isBlank()) {

            response.sendRedirect(
                    contexto + "/login.jsp?erro=credenciais"
            );
            return;
        }

        // Busca o administrador pelo e-mail.
        Administrador administrador = dao.buscarPorEmail(email);

        // Valida o administrador e a senha.
        if (administrador == null
                || administrador.getSenha() == null
                || !BCrypt.checkpw(senha, administrador.getSenha())) {

            response.sendRedirect(
                    contexto + "/login.jsp?erro=credenciais"
            );
            return;
        }

        // Encerra a sessão anterior, se existir.
        HttpSession antiga = request.getSession(false);

        if (antiga != null) {
            antiga.invalidate();
        }

        // Cria a sessão autenticada.
        HttpSession sessao = request.getSession(true);

        sessao.setAttribute("administradorId", administrador.getId());
        sessao.setAttribute("administradorNome", administrador.getNome());

        // CORREÇÃO: utiliza o servlet /inicio.
        response.sendRedirect(contexto + "/inicio");
    }
}
