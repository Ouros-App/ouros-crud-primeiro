package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.model.Granja;
import primeirobd.model.Meta;
import primeirobd.service.GranjaDAO;
import primeirobd.service.MetaDAO;
import primeirobd.utils.Paginacao;
import primeirobd.utils.Validador;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

@WebServlet(name = "Metas", value = "/metas")
public class Metas extends HttpServlet {
    private MetaDAO meta;
    private GranjaDAO granja;

    // Valores permitidos para o status da meta.
    private static final Set<String> STATUS = Set.of("NAO_INICIADA", "EM_ANDAMENTO", "FINALIZADA");

    // Tipos de meta aceitos pelo cadastro.
    private static final Set<String> TIPOS_META = Set.of(
            "AGUA", "ENERGIA", "CUSTO", "DESEMPENHO", "SANIDADE", "MORTALIDADE",
            "SUSTENTABILIDADE", "INFRAESTRUTURA", "ESTADUAL", "REGIONAL", "GLOBAL");

    // Maior valor permitido pelo campo numérico do banco.
    private static final BigDecimal LIMITE = new BigDecimal("99999999.99");

    @Override
    public void init() throws ServletException {
        meta = new MetaDAO();
        granja = new GranjaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        // Define qual aba de metas será exibida.
        boolean individual = !"estadual".equals(request.getParameter("tipo"));
        request.setAttribute("tipo", individual ? "individual" : "estadual");

        // Abre o formulário de nova meta dentro do modal.
        if ("inserir".equals(acao)) {
            request.setAttribute("granjas", granja.select_all());
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/meta-nova.jsp")
                    .forward(request, response);
            return;
        }

        // Abre o formulário de edição da meta selecionada.
        if ("editar".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Meta selecionada = meta.select_by_id(id);
                if (selecionada == null) {
                    response.sendRedirect(request.getContextPath() + "/metas?erro=naoencontrado");
                    return;
                }
                request.setAttribute("metaSelecionada", selecionada);
                request.setAttribute("granjas", granja.select_all());
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/editar-meta.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/metas?erro=id");
            }
            return;
        }

        // Abre a confirmação de exclusão da meta selecionada.
        if ("excluir".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Meta selecionada = meta.select_by_id(id);
                if (selecionada == null) {
                    response.sendRedirect(request.getContextPath() + "/metas?erro=naoencontrado");
                    return;
                }
                request.setAttribute("metaSelecionada", selecionada);
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/excluir-meta.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/metas?erro=id");
            }
            return;
        }

        int pagina = Paginacao.lerPagina(request);
        int tamanho = 5;

        String busca = request.getParameter("buscaMeta");
        busca = (busca == null) ? "" : busca.trim();
        String termo = "%" + busca + "%";

        String ordenarPor = request.getParameter("ordenarPor");
        if (ordenarPor == null || ordenarPor.isBlank()) {
            ordenarPor = "titulo";
        }

        int total = meta.contar(individual, termo);
        Paginacao paginacao = new Paginacao(pagina, tamanho, total);

        List<Meta> metas = meta.select_paginado(
                individual, termo, paginacao.getTamanho(), paginacao.getOffset(), ordenarPor);

        request.setAttribute("metas", metas);
        request.setAttribute("paginacao", paginacao);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/metas.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");
        String contexto = request.getContextPath();
        boolean individual = !"estadual".equals(request.getParameter("tipo"));
        // Mantém a aba selecionada depois de salvar ou excluir.
        String tipoVolta = individual ? "individual" : "estadual";

        if ("excluir".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                meta.delete(id);
            } catch (NumberFormatException e) {
                response.sendRedirect(contexto + "/metas?tipo=" + tipoVolta + "&erro=id");
                return;
            }
            response.sendRedirect(contexto + "/metas?tipo=" + tipoVolta);
            return;
        }

        if (!"inserir".equals(acao) && !"editar".equals(acao)) {
            response.sendRedirect(contexto + "/metas?tipo=" + tipoVolta);
            return;
        }

        // Lê os dados enviados pelo formulário.
        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");
        String tipoMeta = request.getParameter("tipoValor");
        String status = request.getParameter("status");
        String regiao = request.getParameter("regiao");
        String estado = request.getParameter("estado");

        int idMeta = 0;
        int idGranja = 0;
        BigDecimal alvo;

        try {
            alvo = new BigDecimal(request.getParameter("alvo")).setScale(2, RoundingMode.HALF_UP);

            // Metas individuais precisam indicar uma granja.
            if (individual) {
                idGranja = Integer.parseInt(request.getParameter("granja"));
            }
            if ("editar".equals(acao)) {
                idMeta = Integer.parseInt(request.getParameter("id"));
            }
        } catch (NumberFormatException | NullPointerException e) {
            response.sendRedirect(contexto + "/metas?tipo=" + tipoVolta + "&erro=dados");
            return;
        }

        if (titulo == null || titulo.isBlank() || titulo.trim().length() > 100
                || descricao == null || descricao.isBlank()
                || tipoMeta == null || !TIPOS_META.contains(tipoMeta)
                || status == null || !STATUS.contains(status)
                || alvo.signum() <= 0
                || alvo.compareTo(LIMITE) > 0
                || (!individual && (!Validador.isRegiaoValida(regiao)
                || estado == null || estado.isBlank() || estado.trim().length() > 50))) {
            response.sendRedirect(contexto + "/metas?tipo=" + tipoVolta + "&erro=dados");
            return;
        }

        // Confirma que a granja informada existe antes de salvar.
        if (individual) {
            boolean granjaExiste = false;

            for (Granja g : granja.select_all()) {
                if (g.getId() == idGranja) {
                    granjaExiste = true;
                    break;
                }
            }
            if (!granjaExiste) {
                response.sendRedirect(contexto + "/metas?tipo=" + tipoVolta + "&erro=granja");
                return;
            }
        }

        Meta met = new Meta();
        met.setTitulo(titulo.trim());
        met.setDescricao(descricao.trim());
        met.setTipoMeta(tipoMeta);
        met.setStatus(status);
        met.setValorAlvo(alvo);
        met.setIndividual(individual);

        // Mantém os campos de granja, região e estado de acordo com o tipo da meta.
        if (individual) {
            met.setIdGranja(idGranja);
            met.setRegiao(null);
            met.setEstado(null);
        } else {
            met.setIdGranja(0);
            met.setRegiao(regiao.trim());
            met.setEstado(estado.trim());
        }

        if ("inserir".equals(acao)) {
            meta.insert(met);
        } else {
            if (meta.select_by_id(idMeta) == null) {
                response.sendRedirect(contexto + "/metas?tipo=" + tipoVolta + "&erro=naoencontrado");
                return;
            }
            met.setId(idMeta);
            meta.update_meta(met);
        }

        response.sendRedirect(contexto + "/metas?tipo=" + tipoVolta);
    }
}