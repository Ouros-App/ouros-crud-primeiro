package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.model.Meta;
import primeirobd.service.MetaDAO;
import primeirobd.utils.Paginacao;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Metas", value = "/metas")
public class Metas extends HttpServlet {
    private MetaDAO meta;

    @Override
    public void init() throws ServletException {
        meta = new MetaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pagina = Paginacao.lerPagina(request);
        int tamanho = 5;

        // alternador: ?tipo=individual (padrão) ou ?tipo=estadual
        boolean individual = !"estadual".equals(request.getParameter("tipo"));

        String busca = request.getParameter("buscaMeta");
        busca = (busca == null) ? "" : busca.trim();
        String termo = "%" + busca + "%";

        int total = meta.contar(individual, termo);
        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        List<Meta> metas = meta.select_paginado(
                individual, termo, paginacao.getTamanho(), paginacao.getOffset());

        request.setAttribute("metas", metas);
        request.setAttribute("paginacao", paginacao);
        request.setAttribute("tipo", individual ? "individual" : "estadual");

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/metas.jsp")
                .forward(request, response);
    }
}