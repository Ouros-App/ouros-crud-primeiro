package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.service.FuncionarioDAO;
import primeirobd.service.GranjaDAO;
import primeirobd.service.IndicadoresDAO;
import primeirobd.service.LoteDAO;
import primeirobd.service.MetaDAO;
import primeirobd.utils.Paginacao;

import java.io.IOException;

@WebServlet(name = "Inicio", value = "/inicio")
public class Inicio extends HttpServlet {
    private GranjaDAO granjaDao;
    private LoteDAO loteDao;
    private FuncionarioDAO funcionarioDao;
    private MetaDAO metaDao;
    private IndicadoresDAO indicadoresDao;

    @Override
    public void init() throws ServletException {
        granjaDao = new GranjaDAO();
        loteDao = new LoteDAO();
        funcionarioDao = new FuncionarioDAO();
        metaDao = new MetaDAO();
        indicadoresDao = new IndicadoresDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int totalLotes = loteDao.contar();

        request.setAttribute("totalGranjas", granjaDao.contar());
        request.setAttribute("totalLotes", totalLotes);
        request.setAttribute("totalFuncionarios", funcionarioDao.contar());
        request.setAttribute("totalMetas", metaDao.contar());

        request.setAttribute("frangosEmProducao", indicadoresDao.frangosEmProducao());
        request.setAttribute("consumoAgua", indicadoresDao.consumoAguaTotal());
        request.setAttribute("consumoEnergia", indicadoresDao.consumoEnergiaTotal());
        request.setAttribute("metasConcluidas", indicadoresDao.metasConcluidas());

        Paginacao paginacao = new Paginacao(Paginacao.lerPagina(request), 5, totalLotes);
        request.setAttribute("paginacao", paginacao);
        request.setAttribute("lotes", loteDao.select_paginado(paginacao.getTamanho(), paginacao.getOffset()));

        getServletContext().getRequestDispatcher("/WEB-INF/views/inicio.jsp").forward(request, response);
    }
}