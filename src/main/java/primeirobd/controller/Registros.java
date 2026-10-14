package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.service.RegistroAguaDAO;
import primeirobd.service.RegistroEnergiaDAO;
import primeirobd.utils.Paginacao;

import java.io.IOException;

@WebServlet(name = "Registros", value = "/registros" )
public class Registros extends HttpServlet {

    private static final int TAMANHO_PAGINA = 5;

    private final RegistroAguaDAO registroAguaDAO = new RegistroAguaDAO();
    private final RegistroEnergiaDAO registroEnergiaDAO = new RegistroEnergiaDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String tipo = request.getParameter("tipo");

        if (tipo == null || tipo.isBlank()) {
            tipo = "agua";
        }

        tipo = tipo.toLowerCase();

        if (!tipo.equals("agua") && !tipo.equals("energia")) {
            tipo = "agua";
        }

        int pagina = Paginacao.lerPagina(request);

        int total;
        Object registros;

        if (tipo.equals("energia")) {
            total = registroEnergiaDAO.contar();

            Paginacao paginacao = new Paginacao(
                    pagina,
                    TAMANHO_PAGINA,
                    total
            );

            registros = registroEnergiaDAO.select_paginado(
                    paginacao.getTamanho(),
                    paginacao.getOffset()
            );

            request.setAttribute("registroEnergias", registros);
            request.setAttribute("paginacao", paginacao);

        } else {
            total = registroAguaDAO.contar();

            Paginacao paginacao = new Paginacao(
                    pagina,
                    TAMANHO_PAGINA,
                    total
            );

            registros = registroAguaDAO.select_paginado(
                    paginacao.getTamanho(),
                    paginacao.getOffset()
            );

            request.setAttribute("registroAguas", registros);
            request.setAttribute("paginacao", paginacao);
        }

        request.setAttribute("tipoAtual", tipo);

        request.getRequestDispatcher(
                "/WEB-INF/views/registros.jsp"
        ).forward(request, response);
    }
}
