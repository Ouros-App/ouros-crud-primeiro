package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.model.Granja;
import primeirobd.model.Lote;
import primeirobd.service.GranjaDAO;
import primeirobd.service.LoteDAO;
import primeirobd.utils.Paginacao;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet(name ="Lotes", value ="/lotes")
public class Lotes extends HttpServlet{
    private LoteDAO lote;
    private GranjaDAO granja;

    @Override
    public void init() throws ServletException {
        lote = new LoteDAO();
        granja = new GranjaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");

        // Abre o formulário de novo lote dentro do modal.
        if ("inserir".equals(acao)) {
            request.setAttribute("granjas", granja.select_all());
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/lote-novo.jsp")
                    .forward(request, response);
            return;
        }

        // Abre o formulário de edição do lote selecionado.
        if ("editar".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Lote selecionado = lote.select_by_id(id);
                if (selecionado == null) {
                    response.sendRedirect(request.getContextPath() + "/lotes?erro=naoencontrado");
                    return;
                }
                request.setAttribute("loteSelecionado", selecionado);
                request.setAttribute("granjas", granja.select_all());
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/editar-lote.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/lotes?erro=id");
            }
            return;
        }

        // Abre a confirmação de exclusão do lote selecionado.
        if ("excluir".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Lote selecionado = lote.select_by_id(id);
                if (selecionado == null) {
                    response.sendRedirect(request.getContextPath() + "/lotes?erro=naoencontrado");
                    return;
                }
                request.setAttribute("loteSelecionado", selecionado);
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/excluir-lote.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/lotes?erro=id");
            }
            return;
        }

        int pagina = Paginacao.lerPagina(request);
        int tamanho = 5;

        String busca = request.getParameter("buscaLote");
        busca = (busca == null) ? "" : busca.trim();

        String ordenarPor = request.getParameter("ordenarPor");
        if (ordenarPor == null || ordenarPor.isBlank()) {
            ordenarPor = "chegada";
        }

        int total;
        List<Lote> lotes;

        if (busca.isEmpty()) {
            total = lote.contar();
            Paginacao paginacao = new Paginacao(pagina, tamanho, total);
            lotes = lote.select_paginado(paginacao.getTamanho(), paginacao.getOffset(), ordenarPor);
            request.setAttribute("paginacao", paginacao);
        } else {
            String termo = "%" + busca + "%";
            total = lote.contar_filtro_pesquisa(termo);
            Paginacao paginacao = new Paginacao(pagina, tamanho, total);
            lotes = lote.select_paginado_filtro_pesquisa(termo, paginacao.getTamanho(), paginacao.getOffset(), ordenarPor);
            request.setAttribute("paginacao", paginacao);
        }

        request.setAttribute("lotes", lotes);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/lotes.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");
        String contexto = request.getContextPath();

        if ("excluir".equals(acao)) {
            int id;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                response.sendRedirect(contexto + "/lotes?erro=id");
                return;
            }
            lote.delete(id);
            response.sendRedirect(contexto + "/lotes");
            return;
        }

        if (!"inserir".equals(acao) && !"editar".equals(acao)) {
            response.sendRedirect(contexto + "/lotes");
            return;
        }

        int idLote = 0;
        int idGranja;
        int entregues;
        int recebidas;
        BigDecimal ganho;
        LocalDate nascimento;
        LocalDate chegada;
        LocalDate entrega = null;

        try {
            idGranja = Integer.parseInt(request.getParameter("granja"));
            entregues = Integer.parseInt(request.getParameter("entregues"));
            recebidas = Integer.parseInt(request.getParameter("recebidas"));
            ganho = new BigDecimal(request.getParameter("ganho")).setScale(2, RoundingMode.HALF_UP);
            nascimento = LocalDate.parse(request.getParameter("nascimento"));
            chegada = LocalDate.parse(request.getParameter("chegada"));

            String entregaParam = request.getParameter("entrega");
            if (entregaParam != null && !entregaParam.isBlank()) {
                entrega = LocalDate.parse(entregaParam);
            }

            if ("editar".equals(acao)) {
                idLote = Integer.parseInt(request.getParameter("id"));
            }
        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
            response.sendRedirect(contexto + "/lotes?erro=dados");
            return;
        }

        if (entregues <= 0
                || recebidas < 0
                || entregues > recebidas
                || ganho.signum() < 0
                || ganho.compareTo(new BigDecimal("99999999.99")) > 0
                || nascimento.isAfter(chegada)
                || (entrega != null && chegada.isAfter(entrega))) {
            response.sendRedirect(contexto + "/lotes?erro=dados");
            return;
        }

        boolean granjaExiste = false;

        for (Granja g : granja.select_all()) {
            if (g.getId() == idGranja) {
                granjaExiste = true;
                break;
            }
        }
        if (!granjaExiste) {
            response.sendRedirect(contexto + "/lotes?erro=granja");
            return;
        }

        Lote lot = new Lote();
        lot.setIdGranja(idGranja);
        lot.setGalinhasEntregadas(entregues);
        lot.setGalinhasRecebidas(recebidas);
        lot.setGanho(ganho);
        lot.setDtNascimento(Date.valueOf(nascimento));
        lot.setDtChegada(Date.valueOf(chegada));
        lot.setDtEntrega(entrega == null ? null : Date.valueOf(entrega));

        if ("inserir".equals(acao)) {
            lote.insert(lot);
        } else {
            if (lote.select_by_id(idLote) == null) {
                response.sendRedirect(contexto + "/lotes?erro=naoencontrado");
                return;
            }
            lot.setId(idLote);
            lote.update_lote(lot);
        }

        response.sendRedirect(contexto + "/lotes");
    }
}