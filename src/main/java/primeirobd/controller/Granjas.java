package primeirobd.controller;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        granja = new GranjaDAO();
        List<Granja> granjas = granja.select_all_join_endereco_proprietario_proprietario_granja();
        request.setAttribute("granjas", granjas);
        getServletContext().getRequestDispatcher("/granjas.jsp").forward(request, response);
    }
}
