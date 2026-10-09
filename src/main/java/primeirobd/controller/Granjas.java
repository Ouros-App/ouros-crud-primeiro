package primeirobd.controller;
import primeirobd.utils.Paginacao;

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

@WebServlet(name = "Granjas", value = "/granjas")
public class Granjas extends HttpServlet{
    private GranjaDAO granja;

    @Override
    public void init() throws ServletException {
        granja = new GranjaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Descobre qual página foi pedida .-.
        int pagina = Paginacao.lerPagina(request);

        // 2. Quantos registros serão mostrados por página ._.
        int tamanho = 5;

        // 3. Descobre quantos dados existem O_O
        int total = granja.contar();

        // 4. Cria o objeto de paginação
        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        String buscaUsuario = request.getParameter("buscaGranja");

        List<Granja> granjas;

        // 5. Busca somente os dados daquela página >_<
        if(buscaUsuario == null){
            granjas =
                    granja.select_paginado(
                            paginacao.getTamanho(),
                            paginacao.getOffset()
                    );
        }else {
            granjas =
                    granja.select_paginado_filtro_pesquisa(
                            "%" + buscaUsuario + "%",
                            paginacao.getTamanho(),
                            paginacao.getOffset()
                    );
        }

        // 6. Envia os dados para o JSP ¹_¹
        request.setAttribute("granjas", granjas);

        // 7. Envia a paginação para o JSP :D
        request.setAttribute("paginacao", paginacao);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/granjas.jsp")
                .forward(request, response);
    }
}
