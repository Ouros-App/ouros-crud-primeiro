package primeirobd.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.service.*;
import primeirobd.model.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Funcionarios", value = "/funcionarios")
public class Funcionarios extends HttpServlet{
    private FuncionarioDAO funcionario;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        funcionario = new FuncionarioDAO();
        List<Funcionario> funcionarios = funcionario.select_all();
        request.setAttribute("funcionarios", funcionarios);
        getServletContext().getRequestDispatcher("/funcionariosjsp").forward(request, response);
    }
}
