package primeirobd.controller;
import primeirobd.utils.Paginacao;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
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



        String buscaUsuario = request.getParameter("buscaFuncionario");
        String filtro = request.getParameter("buscarEm");

        if (filtro == null || filtro.isBlank()) {
            filtro = "tudo";
        }

        if (buscaUsuario == null) {
            buscaUsuario = "";
        }

        buscaUsuario = buscaUsuario.trim();


        int total;

        if (buscaUsuario.isEmpty()) {
            total = funcionario.contar_paginado();
        } else {
            String busca = "%" + buscaUsuario + "%";

            switch (filtro) {
                case "nome":
                    total = funcionario.contar_paginado_filtro_nome(busca);
                    break;

                case "setor":
                    total = funcionario.contar_paginado_filtro_setor(busca);
                    break;

                case "email":
                    total = funcionario.contar_paginado_filtro_email(busca);
                    break;

                case "telefone":
                    total = funcionario.contar_paginado_filtro_telefone(busca);
                    break;

                default:
                    total = funcionario.contar_paginado_filtro_tudo(busca);
                    break;
            }
        }

        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        List<Funcionario> funcionarios = null;

        if(filtro.equals("tudo")){
            if(buscaUsuario.isEmpty()){
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
            if(buscaUsuario.isEmpty()){
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
            if(buscaUsuario.isEmpty()){
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
            if(buscaUsuario.isEmpty()){
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
            if(buscaUsuario.isEmpty()){
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
