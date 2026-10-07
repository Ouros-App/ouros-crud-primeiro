package primeirobd.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.model.Granja;
import primeirobd.service.GranjaDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name ="Granjas", value ="/granjas")
public class Granjas extends HttpServlet {
    private GranjaDAO granja;

    @Override
    public void init(){
        granja = new GranjaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // cria a lista de granjas
        List<Granja> granjas = granja.select_all_join_paginado();
        // atribui a lista a requisição e cria um JSON para filtro de tabelas
        request.setAttribute("granjas", new Gson().toJson(granjas));
        getServletContext().getRequestDispatcher("/WEB-INF/views/granjas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
