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

    private final ProprietarioGranjaDAO dao = new ProprietarioGranjaDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            response.sendRedirect("login.jsp?erro=credenciais");
            return;
        }

        ProprietarioGranja usuario = dao.buscarPorEmail(email);

        // mesma mensagem para "email não existe" e "senha errada", de propósito
        if (usuario == null || !BCrypt.checkpw(senha, usuario.getSenha())) {
            response.sendRedirect("login.jsp?erro=credenciais");
            return;
        }

        if (!usuario.isEmailVerificado()) {
            response.sendRedirect("login.jsp?erro=naoverificado");
            return;
        }

        HttpSession antiga = request.getSession(false);
        if (antiga != null) {
            antiga.invalidate();
        }
        HttpSession sessao = request.getSession(true);
        sessao.setAttribute("usuarioId", usuario.getId());
        sessao.setAttribute("usuarioNome", usuario.getNome());

        response.sendRedirect("inicio.jsp");
    }
}