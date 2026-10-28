package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.model.Lote;
import primeirobd.model.RegistroAgua;
import primeirobd.model.RegistroEnergia;
import primeirobd.service.LoteDAO;
import primeirobd.service.RegistroAguaDAO;
import primeirobd.service.RegistroEnergiaDAO;
import primeirobd.utils.Paginacao;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet(name = "Registros", value = "/registros" )
public class Registros extends HttpServlet {

    private static final int TAMANHO_PAGINA = 5;

    // maior valor que cabe em numeric(10,2)
    private static final BigDecimal LIMITE = new BigDecimal("99999999.99");

    private final RegistroAguaDAO registroAguaDAO = new RegistroAguaDAO();
    private final RegistroEnergiaDAO registroEnergiaDAO = new RegistroEnergiaDAO();
    private final LoteDAO loteDAO = new LoteDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String tipo = request.getParameter("tipo");

        if (tipo == null || tipo.isBlank()) {
            tipo = "agua";
        }

        tipo = tipo.toLowerCase();

        if (!tipo.equals("agua") && !tipo.equals("energia")) {
            tipo = "agua";
        }

        request.setAttribute("tipoAtual", tipo);

        // abre o formulário de novo registro (carregado dentro do modal)
        if ("inserir".equals(acao)) {
            request.setAttribute("lotes", loteDAO.select_all_join_paginado());
            request.getRequestDispatcher("/WEB-INF/views/registro-novo.jsp")
                    .forward(request, response);
            return;
        }

        // abre o formulário de edição ou a confirmação de exclusão
        if ("editar".equals(acao) || "excluir".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Object selecionado = tipo.equals("energia")
                        ? registroEnergiaDAO.select_by_id(id)
                        : registroAguaDAO.select_by_id(id);

                if (selecionado == null) {
                    response.sendRedirect(request.getContextPath() + "/registros?tipo=" + tipo + "&erro=naoencontrado");
                    return;
                }
                request.setAttribute("registroSelecionado", selecionado);

                String jsp;
                if ("editar".equals(acao)) {
                    request.setAttribute("lotes", loteDAO.select_all_join_paginado());
                    jsp = "/WEB-INF/views/editar-registro.jsp";
                } else {
                    jsp = "/WEB-INF/views/excluir-registro.jsp";
                }
                request.getRequestDispatcher(jsp).forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/registros?tipo=" + tipo + "&erro=id");
            }
            return;
        }

        int pagina = Paginacao.lerPagina(request);

        String busca = request.getParameter("buscaRegistro");
        busca = (busca == null) ? "" : busca.trim();
        String termo = "%" + busca + "%";

        String ordenarPor = request.getParameter("ordenarPor");
        if (ordenarPor == null || ordenarPor.isBlank()) {
            ordenarPor = "data";
        }

        if (tipo.equals("energia")) {
            int total = registroEnergiaDAO.contar(termo);

            Paginacao paginacao = new Paginacao(pagina, TAMANHO_PAGINA, total);

            request.setAttribute("registroEnergias", registroEnergiaDAO.select_paginado(
                    termo, paginacao.getTamanho(), paginacao.getOffset(), ordenarPor));
            request.setAttribute("paginacao", paginacao);

        } else {
            int total = registroAguaDAO.contar(termo);

            Paginacao paginacao = new Paginacao(pagina, TAMANHO_PAGINA, total);

            request.setAttribute("registroAguas", registroAguaDAO.select_paginado(
                    termo, paginacao.getTamanho(), paginacao.getOffset(), ordenarPor));
            request.setAttribute("paginacao", paginacao);
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/registros.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");
        String contexto = request.getContextPath();
        String tipo = "energia".equals(request.getParameter("tipo")) ? "energia" : "agua";

        if ("excluir".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                if (tipo.equals("energia")) {
                    registroEnergiaDAO.delete(id);
                } else {
                    registroAguaDAO.delete(id);
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(contexto + "/registros?tipo=" + tipo + "&erro=id");
                return;
            }
            response.sendRedirect(contexto + "/registros?tipo=" + tipo);
            return;
        }

        if (!"inserir".equals(acao) && !"editar".equals(acao)) {
            response.sendRedirect(contexto + "/registros?tipo=" + tipo);
            return;
        }

        int idRegistro = 0;
        int idLote;
        LocalDate data;
        BigDecimal inicial;
        BigDecimal fim;

        try {
            idLote = Integer.parseInt(request.getParameter("lote"));
            data = LocalDate.parse(request.getParameter("data"));
            inicial = new BigDecimal(request.getParameter("medidorInicial")).setScale(2, RoundingMode.HALF_UP);
            fim = new BigDecimal(request.getParameter("medidorFinal")).setScale(2, RoundingMode.HALF_UP);

            if ("editar".equals(acao)) {
                idRegistro = Integer.parseInt(request.getParameter("id"));
            }
        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
            response.sendRedirect(contexto + "/registros?tipo=" + tipo + "&erro=dados");
            return;
        }

        if (inicial.signum() < 0
                || fim.compareTo(inicial) < 0
                || fim.compareTo(LIMITE) > 0) {
            response.sendRedirect(contexto + "/registros?tipo=" + tipo + "&erro=medidor");
            return;
        }

        Lote lot = loteDAO.select_by_id(idLote);
        if (lot == null) {
            response.sendRedirect(contexto + "/registros?tipo=" + tipo + "&erro=lote");
            return;
        }

        if (data.isBefore(lot.getDtChegada().toLocalDate())
                || (lot.getDtEntrega() != null && data.isAfter(lot.getDtEntrega().toLocalDate()))) {
            response.sendRedirect(contexto + "/registros?tipo=" + tipo + "&erro=data");
            return;
        }

        if (tipo.equals("agua")) {
            RegistroAgua rea = new RegistroAgua();
            rea.setIdLote(idLote);
            rea.setDtRegistro(Date.valueOf(data));
            rea.setHidrometroInicio(inicial);
            rea.setHidrometroFinal(fim);

            if ("inserir".equals(acao)) {
                registroAguaDAO.insert(rea);
            } else {
                if (registroAguaDAO.select_by_id(idRegistro) == null) {
                    response.sendRedirect(contexto + "/registros?tipo=" + tipo + "&erro=naoencontrado");
                    return;
                }
                rea.setId(idRegistro);
                registroAguaDAO.update_registro(rea);
            }
        } else {
            RegistroEnergia ree = new RegistroEnergia();
            ree.setIdLote(idLote);
            ree.setDtRegistro(Date.valueOf(data));
            ree.setConsumo(fim.subtract(inicial));

            if ("inserir".equals(acao)) {
                registroEnergiaDAO.insert(ree);
            } else {
                if (registroEnergiaDAO.select_by_id(idRegistro) == null) {
                    response.sendRedirect(contexto + "/registros?tipo=" + tipo + "&erro=naoencontrado");
                    return;
                }
                ree.setId(idRegistro);
                registroEnergiaDAO.update_registro(ree);
            }
        }

        response.sendRedirect(contexto + "/registros?tipo=" + tipo);
    }
}
