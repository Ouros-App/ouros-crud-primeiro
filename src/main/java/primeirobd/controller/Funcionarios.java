package primeirobd.controller;

import primeirobd.utils.Paginacao;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.service.*;
import primeirobd.model.*;
import primeirobd.utils.Validador;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Funcionarios", value = "/funcionarios")
public class Funcionarios extends HttpServlet {
    private FuncionarioDAO funcionario;
    public static final int TAMANHO_PAGINA = 5;

    @Override
    public void init() throws ServletException {
        funcionario = new FuncionarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Descobre qual página foi pedida .-.
        int pagina = Paginacao.lerPagina(request);

        String buscaUsuario = request.getParameter("buscaFuncionario");
        String filtro = request.getParameter("buscarEm");

        if ("novo".equals(request.getParameter("acao"))) {
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/funcionario-novo.jsp")
                    .forward(request, response);
            return;
        }

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

        Paginacao paginacao = new Paginacao(pagina, TAMANHO_PAGINA, total);

        List<Funcionario> funcionarios = null;

        if (filtro.equals("tudo")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            } else {
                funcionarios =
                        funcionario.select_paginado_filtro_tudo(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }
        }
        if (filtro.equals("nome")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            } else {
                funcionarios =
                        funcionario.select_paginado_filtro_nome(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }
        }
        if (filtro.equals("setor")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            } else {
                funcionarios =
                        funcionario.select_paginado_filtro_setor(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }
        }
        if (filtro.equals("email")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            } else {
                funcionarios =
                        funcionario.select_paginado_filtro_email(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            }
        }
        if (filtro.equals("telefone")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset()
                        );
            } else {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // pega os valores que o usuár inseriu no formulário
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String setor = request.getParameter("setor");
        String telefone = request.getParameter("telefone");
        String empresa = request.getParameter("empresa");
        EmpresaDAO empresaDAO = new EmpresaDAO();

        if (nome == null
                || nome.isBlank()
                || setor == null
                || empresa == null
                || !Validador.isEmailValido(email)
                || !Validador.isCpfValido(cpf)
                || !Validador.isTelefoneValido(telefone)) {
            response.sendRedirect(request.getContextPath() + "/funcionarios?erro=dados");
            return;
        }

        List<Empresa> emps = empresaDAO.select_empresa_by_nome(empresa.trim());

        System.out.println("empresa=" + empresa + " | achadas=" + emps.size()
                + " | id=" + (emps.isEmpty() ? "nenhuma" : emps.getFirst().getId()));

        if (emps.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/funcionarios?erro=empresa");
            return;
        }

        Funcionario novoFuncionario = new Funcionario(
                nome.trim(), cpf.trim(), email.trim(), setor.trim(), telefone.trim());

        funcionario.insert_funcionario_com_telefone(novoFuncionario, emps);

        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }
}
