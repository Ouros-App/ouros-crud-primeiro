
package primeirobd.controller;

import org.mindrot.jbcrypt.BCrypt;

import primeirobd.model.ProprietarioGranja;
import primeirobd.service.ProprietarioGranjaDAO;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final ProprietarioGranjaDAO dao =
            new ProprietarioGranjaDAO();

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

        // Busca o usuário pelo e-mail.
        ProprietarioGranja usuario = dao.buscarPorEmail(email);

        // Valida o usuário e a senha.
        if (usuario == null
                || usuario.getSenha() == null
                || !BCrypt.checkpw(senha, usuario.getSenha())) {

            response.sendRedirect(
                    contexto + "/login.jsp?erro=credenciais"
            );
            return;
        }

        // Exige que o e-mail esteja verificado.
        if (!usuario.isEmailVerificado()) {

            response.sendRedirect(
                    contexto + "/login.jsp?erro=naoverificado"
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

        sessao.setAttribute("usuarioId", usuario.getId());
        sessao.setAttribute("usuarioNome", usuario.getNome());

        // CORREÇÃO: utiliza o servlet /inicio.
        response.sendRedirect(contexto + "/inicio");
    }
}
