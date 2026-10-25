package primeirobd.controller;

import primeirobd.model.Empresa;
import primeirobd.model.Granja;
import primeirobd.service.EmpresaDAO;
import primeirobd.service.GranjaDAO;
import primeirobd.utils.Paginacao;
import primeirobd.utils.Validador;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Granjas", value = "/granjas")
public class Granjas extends HttpServlet {
    private GranjaDAO granja;
    private EmpresaDAO empresa;
    public static final int TAMANHO_PAGINA = 5;

    @Override
    public void init() throws ServletException {
        granja = new GranjaDAO();
        empresa = new EmpresaDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String acao = request.getParameter("acao");

        // abre o formulário de nova granja (carregado dentro do modal)
        if ("inserir".equals(acao)) {
            request.setAttribute("empresas", empresa.select_all());
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/granja-nova.jsp")
                    .forward(request, response);
            return;
        }

        // abre o formulário de edição
        if ("editar".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Granja selecionada = granja.select_by_id(id);
                if (selecionada == null) {
                    response.sendRedirect(request.getContextPath() + "/granjas?erro=naoencontrado");
                    return;
                }
                request.setAttribute("granjaSelecionada", selecionada);
                request.setAttribute("empresas", empresa.select_all());
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/editar-granja.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/granjas?erro=id");
            }
            return;
        }

        // abre a confirmação de exclusão
        if ("excluir".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Granja selecionada = granja.select_by_id(id);
                if (selecionada == null) {
                    response.sendRedirect(request.getContextPath() + "/granjas?erro=naoencontrado");
                    return;
                }
                request.setAttribute("granjaSelecionada", selecionada);
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/excluir-granja.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/granjas?erro=id");
            }
            return;
        }

        int pagina = Paginacao.lerPagina(request);

        String buscaGranja = request.getParameter("buscaGranja");
        String localizacao = request.getParameter("localizacao");
        String ordenarPor = request.getParameter("ordenarPor");

        if (buscaGranja == null) {
            buscaGranja = "";
        }

        if (localizacao == null) {
            localizacao = "";
        }

        if (ordenarPor == null || ordenarPor.isBlank()) {
            ordenarPor = "granja";
        }

        buscaGranja = buscaGranja.trim();
        localizacao = localizacao.trim();

        int total = granja.contar(buscaGranja, localizacao);

        Paginacao paginacao = new Paginacao(
                pagina,
                TAMANHO_PAGINA,
                total
        );

        List<Granja> granjas = granja.select_paginado(
                paginacao.getTamanho(),
                paginacao.getOffset(),
                buscaGranja,
                localizacao,
                ordenarPor
        );

        request.setAttribute("granjas", granjas);
        request.setAttribute("paginacao", paginacao);

        request.setAttribute("buscaGranja", buscaGranja);
        request.setAttribute("localizacao", localizacao);
        request.setAttribute("ordenarPor", ordenarPor);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/granjas.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");

        if ("excluir".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id <= 0) {
                response.sendRedirect(request.getContextPath() + "/granjas?erro=id");
                return;
            }
            granja.delete(id);
            response.sendRedirect(request.getContextPath() + "/granjas");
            return;
        }

        if (!"inserir".equals(acao) && !"editar".equals(acao)) {
            response.sendRedirect(request.getContextPath() + "/granjas");
            return;
        }

        // pega os valores que o usuário inseriu no formulário
        String nome = request.getParameter("nome");
        String regiao = request.getParameter("regiao");
        String municipio = request.getParameter("municipio");
        String estado = request.getParameter("estado");
        int capacidade = Integer.parseInt(request.getParameter("capacidade"));
        int area = Integer.parseInt(request.getParameter("area"));
        int idEmpresa = Integer.parseInt(request.getParameter("empresa"));

        // campos que só existem na criação
        String cep = request.getParameter("cep");
        String logradouro = request.getParameter("logradouro");
        int numero = Integer.parseInt(request.getParameter("numero"));

        if (nome == null
                || nome.isBlank()
                || capacidade <= 0
                || area < 0
                || !Validador.isRegiaoValida(regiao)
                || municipio == null
                || municipio.isBlank()
                || estado == null
                || estado.isBlank()
                || ("inserir".equals(acao) && (!Validador.isCepValido(cep) || numero < 0 || logradouro == null || logradouro.isBlank()))) {
            response.sendRedirect(request.getContextPath() + "/granjas?erro=dados");
            return;
        }

        if (!empresaExiste(idEmpresa)) {
            response.sendRedirect(request.getContextPath() + "/granjas?erro=empresa");
            return;
        }

        Granja gra = new Granja();
        gra.setNome(nome.trim());
        gra.setCapacidadeDeAves(capacidade);
        gra.setAreaPropriedade(area);
        gra.setRegiao(regiao.trim());
        gra.setIdEmpresa(idEmpresa);
        gra.setMunicipio(municipio.trim());
        gra.setEstado(estado.trim());

        if ("inserir".equals(acao)) {
            gra.setCep(cep.trim().replaceAll("\\D", ""));
            gra.setNumero(numero);
            gra.setLogradouro(logradouro.trim());
            granja.insert(gra);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id <= 0) {
                response.sendRedirect(request.getContextPath() + "/granjas?erro=id");
                return;
            }
            if (granja.select_by_id(id) == null) {
                response.sendRedirect(request.getContextPath() + "/granjas?erro=naoencontrado");
                return;
            }
            gra.setId(id);
            granja.update_granja_com_endereco(gra);
        }

        response.sendRedirect(request.getContextPath() + "/granjas");
    }
    private boolean empresaExiste(int idEmpresa) {
        for (Empresa e : empresa.select_all()) {
            if (e.getId() == idEmpresa) {
                return true;
            }
        }
        return false;
    }
}