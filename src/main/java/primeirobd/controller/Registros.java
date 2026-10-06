package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.model.Lote;
import primeirobd.model.RegistroAgua;
import primeirobd.model.RegistroEnergia;
import primeirobd.service.RegistroAguaDAO;
import primeirobd.service.RegistroEnergiaDAO;
import primeirobd.utils.Paginacao;

import java.io.IOException;
import java.util.List;

@WebServlet(name ="Registros", value ="/registros")
public class Registros extends HttpServlet {
    private RegistroAguaDAO registroAgua;
    private RegistroEnergiaDAO registroEnergia;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        registroAgua = new RegistroAguaDAO();

        // 1. Descobre qual página foi pedida .-.
        int pagina = Paginacao.lerPagina(request);

        // 2. Quantos registros serão mostrados por página ._.
        int tamanho = 5;

        // 3. Descobre quantos resultados existem O_O
        int total = registroAgua.contar();

        // 4. Cria o objeto de paginação
        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        // 5. Busca somente os resultados daquela página >_<
        List<RegistroAgua> registroAguas =
                registroAgua.select_paginado(
                        paginacao.getTamanho(),
                        paginacao.getOffset()
                );

        // 6. Envia os resultados para o JSP ¹_¹
        request.setAttribute("registroAguas", registroAguas);

        // 7. Envia a paginação para o JSP :D
        request.setAttribute("paginacao", paginacao);

        getServletContext()
                .getRequestDispatcher("/registroAguas.jsp")
                .forward(request, response);



        registroEnergia = new RegistroEnergiaDAO();

        // 1. Descobre qual página foi pedida .-.
        int pagina = Paginacao.lerPagina(request);

        // 2. Quantos registros serão mostrados por página ._.
        int tamanho = 5;

        // 3. Descobre quantos resultados existem O_O
        int total = registroEnergia.contar();

        // 4. Cria o objeto de paginação
        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        // 5. Busca somente os resultados daquela página >_<
        List<RegistroEnergia> registroEnergias =
                registroEnergia.select_paginado(
                        paginacao.getTamanho(),
                        paginacao.getOffset()
                );

        // 6. Envia os resultados para o JSP ¹_¹
        request.setAttribute("registroEnergias", registroEnergias);

        // 7. Envia a paginação para o JSP :D
        request.setAttribute("paginacao", paginacao);

        getServletContext()
                .getRequestDispatcher("/registroEnergias.jsp")
                .forward(request, response);

    }
}
