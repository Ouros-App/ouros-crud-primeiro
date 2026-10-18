
package primeirobd.controller;

import primeirobd.utils.Paginacao;
import primeirobd.service.GranjaDAO;
import primeirobd.model.Granja;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Granjas", value = "/granjas")
public class Granjas extends HttpServlet {
    private GranjaDAO granja;
    public static final int TAMANHO_PAGINA = 5;

    @Override
    public void init() throws ServletException {
        granja = new GranjaDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        int pagina = Paginacao.lerPagina(request);

        String buscaGranja = request.getParameter("buscaGranja");
        String localizacao = request.getParameter("localizacao");
        String ordenarPor = request.getParameter("ordenarPor");

        if (buscaGranja == null) {
            buscaGranja = "";
        }

        if (localizacao == null) {
            localizacao = "";
        }

        if (ordenarPor == null || ordenarPor.isBlank()) {
            ordenarPor = "granja";
        }

        buscaGranja = buscaGranja.trim();
        localizacao = localizacao.trim();

        int total = granja.contar(buscaGranja, localizacao);

        Paginacao paginacao = new Paginacao(
                pagina,
                TAMANHO_PAGINA,
                total
        );

        List<Granja> granjas = granja.select_paginado(
                paginacao.getTamanho(),
                paginacao.getOffset(),
                buscaGranja,
                localizacao,
                ordenarPor
        );

        request.setAttribute("granjas", granjas);
        request.setAttribute("paginacao", paginacao);

        request.setAttribute("buscaGranja", buscaGranja);
        request.setAttribute("localizacao", localizacao);
        request.setAttribute("ordenarPor", ordenarPor);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/granjas.jsp")
                .forward(request, response);
    }
}
