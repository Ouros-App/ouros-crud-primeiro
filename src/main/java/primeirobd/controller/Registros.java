package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.model.RegistroAgua;
import primeirobd.model.RegistroEnergia;
import primeirobd.service.RegistroAguaDAO;
import primeirobd.service.RegistroEnergiaDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name ="Registros", value ="/registros")
public class Registros extends HttpServlet {
    private RegistroAguaDAO registroAgua;
    private RegistroEnergiaDAO registroEnergia;

    @Override
    public void init() {
        registroAgua = new RegistroAguaDAO();
        registroEnergia = new RegistroEnergiaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RegistroAgua> registrosDeAgua = registroAgua.select_all();
        List<RegistroEnergia> registrosDeEnergia = registroEnergia.select_all();

        request.setAttribute("registrosAgua", registrosDeAgua);
        request.setAttribute("registrosEnergia", registrosDeEnergia);

        getServletContext().getRequestDispatcher("/WEB-INF/views/registros.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
