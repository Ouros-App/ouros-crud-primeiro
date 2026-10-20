
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

    @Override
    public void init() throws ServletException {
        granja = new GranjaDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        // 1. Descobre a página solicitada
        int pagina = Paginacao.lerPagina(request);

        // 2. Quantidade de granjas por página
        int tamanho = 5;

        // 3. Lê os filtros da requisição
        String buscaGranja = request.getParameter("buscaGranja");
        String localizacao = request.getParameter("localizacao");
        String ordenarPor = request.getParameter("ordenarPor");

        // 4. Evita valores nulos
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

        // 5. Conta somente as granjas que correspondem aos filtros
        int total = granja.contar(buscaGranja, localizacao);

        // 6. Calcula os dados da paginação
        Paginacao paginacao = new Paginacao(
                pagina,
                tamanho,
                total
        );

        // 7. Busca somente as granjas da página atual
        List<Granja> granjas = granja.select_paginado(
                paginacao.getTamanho(),
                paginacao.getOffset(),
                buscaGranja,
                localizacao,
                ordenarPor
        );

        // 8. Envia os dados para o JSP
        request.setAttribute("granjas", granjas);
        request.setAttribute("paginacao", paginacao);

        // Mantém os filtros disponíveis no JSP
        request.setAttribute("buscaGranja", buscaGranja);
        request.setAttribute("localizacao", localizacao);
        request.setAttribute("ordenarPor", ordenarPor);

        // 9. Abre a página de granjas
        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/granjas.jsp")
                .forward(request, response);
    }
}
