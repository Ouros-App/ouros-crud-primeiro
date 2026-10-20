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

@WebServlet(name ="Lotes", value ="/lotes")
public class Lotes extends HttpServlet{
    private LoteDAO lote;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        lote = new LoteDAO();
        List<Lote> lotes = lote.select_all_join_granja();
        request.setAttribute("lotes", lotes);
        getServletContext().getRequestDispatcher("/lotes.jsp").forward(request, response);
    }
}
