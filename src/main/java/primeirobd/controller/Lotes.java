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

        // 1. Descobre qual página foi pedida .-.
        int pagina = Paginacao.lerPagina(request);

        // 2. Quantos registros serão mostrados por página ._.
        int tamanho = 5;

        // 3. Descobre quantos resultados existem O_O
        int total = lote.contar();

        // 4. Cria o objeto de paginação
        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        String buscaUsuario = request.getParameter("buscaLote");
        // 5. Busca somente os funcionários daquela página >_<

        List<Lote> lotes;

        // 5. Busca somente os dados daquela página >_<
        if(buscaUsuario == null){
            lotes =
                    lote.select_paginado(
                            paginacao.getTamanho(),
                            paginacao.getOffset()
                    );
        }else {
            lotes =
                    lote.select_paginado_filtro_pesquisa(
                            "%" + buscaUsuario + "%",
                            paginacao.getTamanho(),
                            paginacao.getOffset()
                    );
        }

        // 6. Envia os resultados para o JSP ¹_¹
        request.setAttribute("lotes", lotes);

        // 7. Envia a paginação para o JSP :D
        request.setAttribute("paginacao", paginacao);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/lotes.jsp")
                .forward(request, response);
    }
}
