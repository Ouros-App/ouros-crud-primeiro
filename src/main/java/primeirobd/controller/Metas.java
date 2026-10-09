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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name ="Metas", value ="/metas")
public class Metas extends HttpServlet{
    private MetaDAO meta;

    @Override
    public void init() {
        meta = new MetaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meta> metas = new ArrayList<>();
        request.setAttribute("metas", new Gson().toJson(metas));
        getServletContext().getRequestDispatcher("/WEB-INF/views/metas.jsp").forward(request, response);
    }
}
