package primeirobd.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.service.*;
import primeirobd.model.*;
import primeirobd.utils.Paginacao;

import java.io.IOException;
import java.util.List;

@WebServlet(name ="Lotes", value ="/lotes")
public class Lotes extends HttpServlet{
    private LoteDAO lote;

    @Override
    public void init() throws ServletException {
        lote = new LoteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int pagina = Paginacao.lerPagina(request);
        int tamanho = 5;

        String busca = request.getParameter("buscaLote");
        busca = (busca == null) ? "" : busca.trim();

        int total;
        List<Lote> lotes;

        if (busca.isEmpty()) {
            total = lote.contar();
            Paginacao paginacao = new Paginacao(pagina, tamanho, total);
            lotes = lote.select_paginado(paginacao.getTamanho(), paginacao.getOffset());
            request.setAttribute("paginacao", paginacao);
        } else {
            String termo = "%" + busca + "%";
            total = lote.contar_filtro_pesquisa(termo);
            Paginacao paginacao = new Paginacao(pagina, tamanho, total);
            lotes = lote.select_paginado_filtro_pesquisa(termo, paginacao.getTamanho(), paginacao.getOffset());
            request.setAttribute("paginacao", paginacao);
        }

        request.setAttribute("lotes", lotes);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/lotes.jsp")
                .forward(request, response);
    }
}
