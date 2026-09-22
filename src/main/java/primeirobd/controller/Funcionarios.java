package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.service.*;

import java.io.IOException;

@WebServlet(name = "Funcionarios", value = "/funcionarios")
public class Funcionarios extends HttpServlet {
    private FuncionarioDAO funcionario;

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // um servlet por tabela

        request.getServletContext().getRequestDispatcher("/funcionarios.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
