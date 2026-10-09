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

@WebServlet(name = "Funcionarios", value = "/funcionarios")
public class Funcionarios extends HttpServlet{
    private FuncionarioDAO funcionario;

    @Override
    public void init() throws ServletException {
        funcionario = new FuncionarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Descobre qual página foi pedida .-.
        int pagina = Paginacao.lerPagina(request);

        // 2. Quantos registros serão mostrados por página ._.
        int tamanho = 5;

        // 3. Descobre quantos funcionários existem O_O
        int total = funcionario.contar();

        // 4. Cria o objeto de paginação
        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        String buscaUsuario = request.getParameter("buscaFuncionario");

        List<Funcionario> funcionarios = null;

        // 5. Busca somente os dados daquela página >_<
        String filtro = request.getParameter("buscarEm");

        if (filtro == null || filtro.isBlank()) {
            filtro = "tudo"; // valor padrão na primeira visita
        }

        if(filtro.equals("tudo")){
            if(buscaUsuario == null){
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }else {
                funcionarios =
                        funcionario.select_paginado_filtro_tudo(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }
        }
        if(filtro.equals("nome")){
            if(buscaUsuario == null){
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }else {
                funcionarios =
                        funcionario.select_paginado_filtro_nome(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }
        }
        if(filtro.equals("setor")){
            if(buscaUsuario == null){
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }else {
                funcionarios =
                        funcionario.select_paginado_filtro_setor(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }
        }
        if(filtro.equals("email")){
            if(buscaUsuario == null){
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }else {
                funcionarios =
                        funcionario.select_paginado_filtro_email(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }
        }
        if(filtro.equals("telefone")){
            if(buscaUsuario == null){
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }else {
                funcionarios =
                        funcionario.select_paginado_filtro_telefone(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }
        }

        // 6. Envia os funcionários para o JSP ¹_¹
        request.setAttribute("funcionarios", funcionarios);

        // 7. Envia a paginação para o JSP :D
        request.setAttribute("paginacao", paginacao);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/funcionarios.jsp")
                .forward(request, response);
    }
}
