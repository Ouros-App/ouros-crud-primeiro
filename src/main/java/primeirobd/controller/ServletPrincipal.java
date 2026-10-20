package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.service.*;
import primeirobd.model.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Administradores", value = "/servlet-principal")
public class ServletPrincipal extends HttpServlet {
    private AdministradorDAO administrador;
    private EmpresaDAO empresa;
    private EnderecoEmpresaDAO enderecoEmpresa;
    private EnderecoGranjaDAO enderecoGranja;
    private EnderecoProprietarioDAO enderecoProprietario;
    private FuncionarioDAO funcionario;
    private GranjaDAO granja;
    private LoteDAO lote;
    private MetaDAO meta;
    private ProprietarioGranjaDAO proprietarioGranja;
    private RegistroAguaDAO registroAgua;
    private RegistroEnergiaDAO registroEnergia;
    private TelefoneFuncionarioDAO telefoneFuncionario;
    private TelefoneProprietarioDAO telefoneProprietario;

    @Override
    public void init(){
        administrador = new AdministradorDAO();
        empresa = new EmpresaDAO();
        enderecoEmpresa = new EnderecoEmpresaDAO();
        enderecoGranja = new EnderecoGranjaDAO();
        enderecoProprietario = new EnderecoProprietarioDAO();
        funcionario = new FuncionarioDAO();
        granja = new GranjaDAO();
        lote = new LoteDAO();
        meta = new MetaDAO();
        proprietarioGranja = new ProprietarioGranjaDAO();
        registroAgua = new RegistroAguaDAO();
        registroEnergia = new RegistroEnergiaDAO();
        telefoneFuncionario = new TelefoneFuncionarioDAO();
        telefoneProprietario = new TelefoneProprietarioDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // um servlet por tabela
        List<Funcionario> funcionarios = funcionario.select_all();
        request.setAttribute("funcionarios", funcionarios);

        List<Granja> granjas = granja.select_all();
        request.setAttribute("granjas", granjas);

        List<Lote> lotes = lote.select_all();
        request.setAttribute("lotes", lotes);

        List<Meta> metas = meta.select_all();
        request.setAttribute("metas", metas);

        List<RegistroAgua> registroAguas = registroAgua.select_all();
        request.setAttribute("registroAguas", registroAguas);

        List<RegistroEnergia> registroEnergias = registroEnergia.select_all();
        request.setAttribute("registroEnergias", registroEnergias);

        request.getServletContext().getRequestDispatcher("/TabelaInterface.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
