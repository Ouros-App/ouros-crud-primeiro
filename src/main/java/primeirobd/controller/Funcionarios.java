package primeirobd.controller;

import com.google.gson.Gson;
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
    public void init() {
        funcionario = new FuncionarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if("novo".equals(acao)){
            List<Funcionario> funcionarios = funcionario.select_all_paginado();
            request.setAttribute("funcionarios", new Gson().toJson(funcionarios));
            getServletContext().getRequestDispatcher("/WEB-INF/views/funcionario-novo.jsp").forward(request, response);
            return;
        }
        List<Funcionario> funcionarios = funcionario.select_all_paginado();
        request.setAttribute("funcionarios", new Gson().toJson(funcionarios));
        getServletContext().getRequestDispatcher("/WEB-INF/views/funcionarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pega as informações do formulário do JSP
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String setor = request.getParameter("setor");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        // cria o novo funcionario
        Funcionario novoFuncionario = new Funcionario(nome, cpf, setor, email, telefone);
        // adiciona no banco de dados
        funcionario.insert(novoFuncionario);
        // redireciona a resposta para o /funcionarios
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }
}
