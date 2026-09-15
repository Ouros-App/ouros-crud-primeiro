package primeirobd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import primeirobd.DAO.*;
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
        String selectAdmin = "SELECT * FROM administrador;";
        List<Administrador> administradores = administrador.select(selectAdmin);
        request.setAttribute("administradores", administradores);

        String selectEmpresa = "SELECT * FROM empresa;";
        List<Empresa> empresas = empresa.select(selectEmpresa);
        request.setAttribute("empresas", empresas);

        String selectEnderecoEmpresa = "SELECT * FROM endereco_empresa;";
        List<EnderecoEmpresa> enderecoEmpresas = enderecoEmpresa.select(selectEnderecoEmpresa);
        request.setAttribute("enderecoEmpresas", enderecoEmpresas);

        String selectEnderecoGranja = "SELECT * FROM endereco_granja;";
        List<EnderecoGranja> enderecoGranjas = enderecoGranja.select(selectEnderecoGranja);
        request.setAttribute("enderecoGranjas", enderecoGranjas);

        String selectEnderecoProprietario = "SELECT * FROM endereco_proprietario;";
        List<EnderecoProprietario> enderecoProprietarios = enderecoProprietario.select(selectEnderecoProprietario);
        request.setAttribute("enderecoProprietarios", enderecoProprietarios);

        String selectFuncionario = "SELECT * FROM funcionario;";
        List<Funcionario> funcionarios = funcionario.select(selectFuncionario);
        request.setAttribute("funcionario", funcionarios);

        String selectGranja = "SELECT * FROM granja;";
        List<Granja> granjas = granja.select(selectGranja);
        request.setAttribute("granja", granjas);

        String selectLote = "SELECT * FROM lote;";
        List<Lote> lotes = lote.select(selectLote);
        request.setAttribute("lote", lotes);

        String selectMeta = "SELECT * FROM meta;";
        List<Meta> metas = meta.select(selectMeta);
        request.setAttribute("Meta", metas);

        String selectProprietarioGranja = "SELECT * FROM proprietario_granja;";
        List<ProprietarioGranja> proprietarioGranjas = proprietarioGranja.select(selectProprietarioGranja);
        request.setAttribute("ProprietarioGranja", proprietarioGranjas);

        String selectRegistroAgua = "SELECT * FROM registro_agua;";
        List<RegistroAgua> registroAguas = registroAgua.select(selectRegistroAgua);
        request.setAttribute("RegistroAgua", registroAguas);

        String selectRegistroEnergia = "SELECT * FROM registro_energia;";
        List<RegistroEnergia> registroEnergias = registroEnergia.select(selectRegistroEnergia);
        request.setAttribute("RegistroAgua", registroEnergias);

        String selectTelefoneFuncionario = "SELECT * FROM telefone_funcionario;";
        List<TelefoneFuncionario> telefoneFuncionarios = telefoneFuncionario.select(selectTelefoneFuncionario);
        request.setAttribute("TelefoneFuncionario", telefoneFuncionarios);

        String selectTelefoneProprietario = "SELECT * FROM telefone_proprietario;";
        List<TelefoneProprietario> telefoneProprietarios= telefoneProprietario.select(selectTelefoneProprietario);
        request.setAttribute("TelefoneProprietario", telefoneProprietario);

        request.getServletContext().getRequestDispatcher("/TabelaInterface.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
