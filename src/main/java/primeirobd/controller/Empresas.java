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

@WebServlet(name = "Empresas", value = "/empresas")
public class Empresas extends HttpServlet {
    private EmpresaDAO empresa;
    public static final int TAMANHO_PAGINA = 5;

    @Override
    public void init() throws ServletException {
        empresa = new EmpresaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Descobre qual página foi pedida .-.
        int pagina = Paginacao.lerPagina(request);

        String buscaUsuario = request.getParameter("buscaEmpresa");

        // abre o formulário de nova empresa (carregado dentro do modal)
        if ("novo".equals(request.getParameter("acao"))) {
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/empresa-nova.jsp")
                    .forward(request, response);
            return;
        }

        if (buscaUsuario == null) {
            buscaUsuario = "";
        }

        buscaUsuario = buscaUsuario.trim();

        // Descobre quantos resultados existem O_O
        int total;

        if (buscaUsuario.isEmpty()) {
            total = empresa.contar();
        } else {
            total = empresa.contar_filtro_pesquisa("%" + buscaUsuario + "%");
        }

        Paginacao paginacao = new Paginacao(pagina, TAMANHO_PAGINA, total);

        // Busca somente as empresas daquela página >_<
        List<Empresa> empresas;

        if (buscaUsuario.isEmpty()) {
            empresas =
                    empresa.select_paginado(
                            paginacao.getTamanho(),
                            paginacao.getOffset()
                    );
        } else {
            empresas =
                    empresa.select_paginado_filtro_pesquisa(
                            "%" + buscaUsuario + "%",
                            paginacao.getTamanho(),
                            paginacao.getOffset()
                    );
        }

        // Envia as empresas para o JSP ¹_¹
        request.setAttribute("empresas", empresas);

        // Envia a paginação para o JSP :D
        request.setAttribute("paginacao", paginacao);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/empresas.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // pega os valores que o usuário inseriu no formulário
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefoneCorporativo");
        String email = request.getParameter("emailCorporativo");
        String cnpj = request.getParameter("cnpj");

        if (nome == null
                || nome.isBlank()
                || !Validador.isTelefoneValido(telefone)
                || !Validador.isEmailValido(email)){
            response.sendRedirect(request.getContextPath() + "/empresas?erro=dados");
            return;
        }


        Empresa novaEmpresa = new Empresa(
                nome.trim(),
                telefone.trim(),
                email.trim(),
                cnpj.trim()
        );

        empresa.insert(novaEmpresa);

        response.sendRedirect(request.getContextPath() + "/empresas");
    }
}