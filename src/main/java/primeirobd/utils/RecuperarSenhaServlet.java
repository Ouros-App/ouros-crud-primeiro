package primeirobd.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.model.ProprietarioGranja;
import primeirobd.service.ProprietarioGranjaDAO;

import java.io.IOException;

@WebServlet(name = "recuperarSenha", value = "/recuperarSenha")
public class RecuperarSenhaServlet extends HttpServlet {

    private final ProprietarioGranjaDAO dao = new ProprietarioGranjaDAO();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        String email = request.getParameter("email");

        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            response.sendRedirect("recuperarSenha.jsp?erro=credenciais");
            return;
        }

        ProprietarioGranja usuario = dao.buscarPorEmail(email);




        response.setContentType("text/plain;charset=UTF-8");

        StringBuilder erros = new StringBuilder();




    }
}
