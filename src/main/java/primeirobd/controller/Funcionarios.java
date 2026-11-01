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

import primeirobd.utils.ModalUtil;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Funcionarios", value = "/funcionarios")
public class Funcionarios extends HttpServlet {
    private FuncionarioDAO funcionarioDao;
    public static final int TAMANHO_PAGINA = 5;

    @Override
    public void init() throws ServletException {
        funcionarioDao = new FuncionarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lê a página solicitada para montar a lista paginada.
        int pagina = Paginacao.lerPagina(request);

        String buscaUsuario = request.getParameter("buscaFuncionario");
        String filtro = request.getParameter("buscarEm");
        String acao = request.getParameter("acao");
        String ordenarPor = request.getParameter("ordenarPor");

        if ("inserir".equals(acao)) {
            // lista de empresas para o campo de empresa sugerir os nomes
            request.setAttribute("empresas", new EmpresaDAO().selectAll());
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/funcionario-novo.jsp")
                    .forward(request, response);
            return;
        }

        if ("editar".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Funcionario funcionarios = funcionarioDao.selectById(id);
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
                Funcionario funcionarios = funcionarioDao.selectById(id);
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
            total = funcionarioDao.contarPaginado();
        } else {
            String busca = "%" + buscaUsuario + "%";
            total = funcionarioDao.contarPaginadoComBusca(busca);

        }

        Paginacao paginacao = new Paginacao(pagina, TAMANHO_PAGINA, total);

        List<Funcionario> funcionarios = null;

        if (filtro.equals("tudo")) {
            if (buscaUsuario.isEmpty()) {
                funcionarios =
                        funcionarioDao.selectPaginado(
                                paginacao.getTamanho(),
                                paginacao.getOffset(),
                                ordenarPor
                        );
            } else {
                funcionarios =
                        funcionarioDao.selectPaginadoComBusca(
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

        String contexto = request.getContextPath();
        // Lê a ação enviada pelo formulário (inserir, editar ou excluir).
        String acao = request.getParameter("acao");

        if ("excluir".equals(acao)) {
            try {
                funcionarioDao.delete(Integer.parseInt(request.getParameter("id")));
            } catch (NumberFormatException e) {
                response.sendRedirect(contexto + "/funcionarios?erro=id");
                return;
            }
            response.sendRedirect(contexto + "/funcionarios?ok=excluido");
            return;
        }

        boolean inserir = "inserir".equals(acao);
        boolean editar = "editar".equals(acao);

        if (!inserir && !editar) {
            response.sendRedirect(contexto + "/funcionarios");
            return;
        }

        // pega os valores que o usuário inseriu no formulário
        String nome = valor(request, "nome");
        String cpf = valor(request, "cpf");
        String email = valor(request, "email");
        String setor = valor(request, "setor");
        String telefone = valor(request, "telefone");
        String nomeEmpresa = valor(request, "empresa");

        // valida cada campo e guarda a mensagem de erro de quem falhou
        Map<String, String> erros = new LinkedHashMap<>();

        if (nome.isEmpty()) {
            erros.put("nome", "Informe o nome.");
        }
        if (cpf.isEmpty()) {
            erros.put("cpf", "Informe o CPF.");
        } else if (!Validador.isCpfValido(cpf)) {
            erros.put("cpf", "CPF inválido. Confira os 11 números (formato 000.000.000-00).");
        }
        if (setor.isEmpty()) {
            erros.put("setor", "Informe o setor.");
        }
        if (email.isEmpty()) {
            erros.put("email", "Informe o e-mail.");
        } else if (!Validador.isEmailValido(email)) {
            erros.put("email", "E-mail inválido. Use o formato nome@empresa.com.");
        }
        if (telefone.isEmpty()) {
            erros.put("telefone", "Informe o telefone.");
        } else if (!Validador.isTelefoneValido(telefone)) {
            erros.put("telefone", "Telefone inválido. Use DDD + número, por exemplo (11) 91234-5678.");
        }

        EmpresaDAO empresaDAO = new EmpresaDAO();
        List<Empresa> empresas = null;

        if (inserir) {
            if (nomeEmpresa.isEmpty()) {
                erros.put("empresa", "Informe a empresa.");
            } else {
                empresas = empresaDAO.selectEmpresaByNome(nomeEmpresa);
                if (empresas.isEmpty()) {
                    erros.put("empresa", "Empresa não encontrada. Escolha uma da lista.");
                }
            }
        }

        int id = 0;
        Funcionario funcionarioModel = null;

        if (editar) {
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                response.sendRedirect(contexto + "/funcionarios?erro=id");
                return;
            }
            funcionarioModel = funcionarioDao.selectById(id);
            if (funcionarioModel == null) {
                response.sendRedirect(contexto + "/funcionarios?erro=naoencontrado");
                return;
            }
        }

        // com erro: devolve o formulário (dentro do modal) com as mensagens e o que a pessoa digitou
        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);

            if (inserir) {
                request.setAttribute("empresas", empresaDAO.selectAll());
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/funcionario-novo.jsp")
                        .forward(request, response);
            } else {
                Funcionario digitado = new Funcionario(id, nome, cpf, email, setor, telefone);
                digitado.setNomeEmpresa(funcionarioModel.getNomeEmpresa());
                request.setAttribute("funcionarios", digitado);
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/editar-funcionario.jsp")
                        .forward(request, response);
            }
            return;
        }

        if (inserir) {
            funcionarioDao.insertFuncionarioComTelefone(
                    new Funcionario(nome, cpf, email, setor, telefone),
                    empresas);
            ModalUtil.concluir(request, response, "/funcionarios?ok=inserido");
            return;
        }

        Funcionario editado = new Funcionario(id, nome, cpf, email, setor, telefone);

        boolean mudouFuncionario =
                !Objects.equals(funcionarioModel.getNome(), editado.getNome())
                        || !Objects.equals(funcionarioModel.getCpf(), editado.getCpf())
                        || !Objects.equals(funcionarioModel.getEmail(), editado.getEmail())
                        || !Objects.equals(funcionarioModel.getSetor(), editado.getSetor());

        boolean mudouTelefone = !Objects.equals(funcionarioModel.getTelefone(), editado.getTelefone());

        if (mudouFuncionario || mudouTelefone) {
            funcionarioDao.updateFuncionarioComTelefone(editado, funcionarioModel.getIdEmpresa());
        }

        ModalUtil.concluir(request, response, "/funcionarios?ok=editado");
    }

    // lê o parâmetro já sem espaços nas pontas (vazio se não veio)
    private String valor(HttpServletRequest request, String nome) {
        String v = request.getParameter(nome);
        return v == null ? "" : v.trim();
    }
}
