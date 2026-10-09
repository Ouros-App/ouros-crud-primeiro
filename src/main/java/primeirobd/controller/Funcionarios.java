
package primeirobd.controller;

import primeirobd.utils.Paginacao;
import primeirobd.service.FuncionarioDAO;
import primeirobd.model.Funcionario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Funcionarios", value = "/funcionarios")
public class Funcionarios extends HttpServlet {

    private FuncionarioDAO funcionario;

    @Override
    public void init() throws ServletException {
        funcionario = new FuncionarioDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Recebe os parâmetros de busca e filtro.
        String busca = request.getParameter("busca");
        String setor = request.getParameter("setor");
        String ordenarPor = request.getParameter("ordenarPor");

        if (busca == null) {
            busca = "";
        }

        if (setor == null) {
            setor = "";
        }

        if (ordenarPor == null || ordenarPor.isBlank()) {
            ordenarPor = "nome";
        }

        // 2. Descobre qual página foi solicitada.
        int pagina = Paginacao.lerPagina(request);

        // 3. Define quantos funcionários serão exibidos.
        int tamanho = 5;

        // 4. Conta somente os funcionários filtrados.
        int total = funcionario.contar(busca, setor);

        // 5. Calcula a paginação com o total filtrado.
        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        // 6. Busca somente os funcionários da página atual,
        // aplicando a busca, o filtro e a ordenação.
        List<Funcionario> funcionarios =
                funcionario.select_paginado(
                        paginacao.getTamanho(),
                        paginacao.getOffset(),
                        busca,
                        setor,
                        ordenarPor);

        // 7. Envia os resultados para o JSP.
        request.setAttribute("funcionarios", funcionarios);

        // 8. Envia os dados da paginação.
        request.setAttribute("paginacao", paginacao);

        // 9. Mantém os filtros disponíveis no JSP.
        request.setAttribute("busca", busca);
        request.setAttribute("setor", setor);
        request.setAttribute("ordenarPor", ordenarPor);

        // 10. Encaminha para a página de funcionários.
        request.getRequestDispatcher(
                "/WEB-INF/views/funcionarios.jsp"
        ).forward(request, response);
    }
}
