package primeirobd.controller;

import primeirobd.utils.Paginacao;

import java.util.Objects;
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
        String acao = request.getParameter("acao");
        String ordenarPor = request.getParameter("ordenarPor");

        if ("inserir".equals(acao)) {
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/funcionario-novo.jsp")
                    .forward(request, response);
            return;
        }

        if ("editar".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Funcionario funcionarios = funcionario.select_by_id(id);
                if (funcionarios == null) {
                    response.sendRedirect(request.getContextPath() + "/funcionarios?erro=naoencontrado");
                    return;
                }
                request.setAttribute("funcionarios", funcionarios);
                getServletContext().getRequestDispatcher("/WEB-INF/views/editar-funcionario.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/funcionarios?erro=id");
            }
            return;
        }
        if ("excluir".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Funcionario funcionarios = funcionario.select_by_id(id);
                if (funcionarios == null) {
                    response.sendRedirect(request.getContextPath() + "/funcionarios?erro=naoencontrado");
                    return;
                }
                request.setAttribute("funcionarios", funcionarios);
                getServletContext().getRequestDispatcher("/WEB-INF/views/excluir-funcionario.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/funcionarios?erro=id");
            }
            return;
        }

        if (filtro == null || filtro.isBlank()) {
            filtro = "tudo";
        }

        if (buscaUsuario == null || buscaUsuario.isBlank()) {
            buscaUsuario = "";
        }

        if(ordenarPor == null || ordenarPor.isBlank()){
            ordenarPor = "nome";
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
                                paginacao.getOffset(),
                                ordenarPor
                        );
            } else {
                funcionarios =
                        funcionario.select_paginado_filtro_tudo(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            }
        }
        if (filtro.equals("nome")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            } else {
                funcionarios =
                        funcionario.select_paginado_filtro_nome(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            }
        }
        if (filtro.equals("setor")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            } else {
                funcionarios =
                        funcionario.select_paginado_filtro_setor(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            }
        }
        if (filtro.equals("email")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            } else {
                funcionarios =
                        funcionario.select_paginado_filtro_email(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            }
        }
        if (filtro.equals("telefone")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionario.select_paginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            } else {
                funcionarios =
                        funcionario.select_paginado_filtro_telefone(
                                "%" + buscaUsuario + "%",
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            }
        }

        request.setAttribute("funcionarios", funcionarios);

        request.setAttribute("paginacao", paginacao);

        request.setAttribute("ordenarPor", ordenarPor);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/funcionarios.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try{
            // pega os valores que o usuário inseriu no formulário
            String acao = request.getParameter("acao");
            String novoNome = request.getParameter("nome");
            String novoCpf = request.getParameter("cpf");
            String novoEmail = request.getParameter("email");
            String novoSetor = request.getParameter("setor");
            String novoTelefone = request.getParameter("telefone");
            String novaEmpresa = request.getParameter("empresa");

        if ("editar".equals(acao)) {
            if (    novoNome.isBlank()
                    || novoCpf.isBlank()
                    || novoEmail.isBlank()
                    || novoSetor.isBlank()
                    || novoTelefone.isBlank()
                    || !Validador.isEmailValido(novoEmail)
                    || !Validador.isCpfValido(novoCpf)
                    || !Validador.isTelefoneValido(novoTelefone)) {
                response.sendRedirect(request.getContextPath() + "/funcionarios?erro=dados");
                return;
            }
            int id = Integer.parseInt(request.getParameter("id"));
            Funcionario atual = funcionario.select_by_id(id);

            if (atual == null) {
                response.sendRedirect(request.getContextPath() + "/funcionarios?erro=naoencontrado");
                return;
            }

            Funcionario editado = new Funcionario(id, novoNome.trim(), novoCpf.trim(),
                    novoEmail.trim(), novoSetor.trim(), novoTelefone.trim());

            boolean mudouFuncionario =
                    !atual.getNome().equals(editado.getNome())
                            || !atual.getCpf().equals(editado.getCpf())
                            || !atual.getEmail().equals(editado.getEmail())
                            || !atual.getSetor().equals(editado.getSetor());

            boolean mudouTelefone = !Objects.equals(atual.getTelefone(), editado.getTelefone());

            if (mudouFuncionario || mudouTelefone) {
                funcionario.update_funcionario_com_telefone(editado, atual.getIdEmpresa());
            }
        }
        else if("inserir".equals(acao)){
            if (    novoNome.isBlank()
                    || novoCpf.isBlank()
                    || novoEmail.isBlank()
                    || novoSetor.isBlank()
                    || novoTelefone.isBlank()
                    || !Validador.isEmailValido(novoEmail)
                    || !Validador.isCpfValido(novoCpf)
                    || !Validador.isTelefoneValido(novoTelefone)) {
                response.sendRedirect(request.getContextPath() + "/funcionarios?erro=dados");
                return;
            }
            EmpresaDAO empresaDAO = new EmpresaDAO();

            List<Empresa> empresas = empresaDAO.select_empresa_by_nome(novaEmpresa);

            if (empresas.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/funcionarios?erro=empresa");
                return;
            }

            funcionario.insert_funcionario_com_telefone(
                    new Funcionario(novoNome.trim(), novoCpf.trim(), novoEmail.trim(), novoSetor.trim(), novoTelefone.trim()),
                    empresas);
        }
        else if("excluir".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            funcionario.delete(id);
        }

        response.sendRedirect(request.getContextPath() + "/funcionarios");

        } catch (NullPointerException npe){
            throw new RuntimeException(npe.getMessage(), npe);
        }
    }
}
