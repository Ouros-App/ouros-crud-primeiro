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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        lote = new LoteDAO();

        // 1. Descobre qual página foi pedida .-.
        int pagina = Paginacao.lerPagina(request);

        // 2. Quantos registros serão mostrados por página ._.
        int tamanho = 5;

        // 3. Descobre quantos resultados existem O_O
        int total = lote.contar();

        // 4. Cria o objeto de paginação
        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        // 5. Busca somente os resultados daquela página >_<
        List<Lote> lotes =
                lote.select_paginado(
                        paginacao.getTamanho(),
                        paginacao.getOffset()
                );

        // 6. Envia os resultados para o JSP ¹_¹
        request.setAttribute("lotes", lotes);

        // 7. Envia a paginação para o JSP :D
        request.setAttribute("paginacao", paginacao);

        getServletContext()
                .getRequestDispatcher("/lotes.jsp")
                .forward(request, response);
    }
}
