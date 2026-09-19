package primeirobd.tests;

import primeirobd.service.*;
import primeirobd.model.*;

public class MainSelectTeste {
    public static void main(String[] args) {
        /*
    =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
    ░█████████                                                            ░████████████                         ░██
          ░██                                             ░██                   ░██                             ░██
         ░██    ░███████  ░████████   ░██████       ░████████  ░███████         ░██     ░███████   ░███████  ░████████  ░███████
       ░███    ░██    ░██ ░██    ░██       ░██     ░██    ░██ ░██    ░██        ░██    ░██    ░██ ░██           ░██    ░██    ░██
      ░██      ░██    ░██ ░██    ░██  ░███████     ░██    ░██ ░█████████        ░██    ░█████████  ░███████     ░██    ░█████████
     ░██       ░██    ░██ ░██    ░██ ░██   ░██     ░██   ░███ ░██               ░██    ░██               ░██    ░██    ░██
    ░█████████  ░███████  ░██    ░██  ░█████░██     ░█████░██  ░███████         ░██     ░███████   ░███████      ░████  ░███████
    =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
         */
        // variavel do comando
        String comando;

        // objetos das tabelas
        Administrador adm = new Administrador();
        Empresa emp = new Empresa();
        EnderecoEmpresa enEmp = new EnderecoEmpresa();
        EnderecoGranja enGra = new EnderecoGranja();
        EnderecoProprietario enPro = new EnderecoProprietario();
        Funcionario fun = new Funcionario();
        Granja gra = new Granja();
        Lote lot = new Lote();
        Meta met = new Meta();
        ProprietarioGranja proGra = new ProprietarioGranja();
        RegistroAgua reA = new RegistroAgua();
        RegistroEnergia reE = new RegistroEnergia();
        TelefoneFuncionario teF = new TelefoneFuncionario();
        TelefoneProprietario teP = new TelefoneProprietario();

        // acessando dados
        AdministradorDAO admDAO = new AdministradorDAO();
        EmpresaDAO empDAO = new EmpresaDAO();
        EnderecoEmpresaDAO enEmpDAO = new EnderecoEmpresaDAO();
        EnderecoGranjaDAO enGraDAO = new EnderecoGranjaDAO();
        EnderecoProprietarioDAO enProDAO = new EnderecoProprietarioDAO();
        FuncionarioDAO funDAO = new FuncionarioDAO();
        GranjaDAO graDAO = new GranjaDAO();
        LoteDAO lotDAO = new LoteDAO();
        MetaDAO metDAO = new MetaDAO();
        ProprietarioGranjaDAO proGraDAO = new ProprietarioGranjaDAO();
        RegistroAguaDAO reADAO = new RegistroAguaDAO();
        RegistroEnergiaDAO reEDAO = new RegistroEnergiaDAO();
        TelefoneFuncionarioDAO teFDAO = new TelefoneFuncionarioDAO();
        TelefoneProprietarioDAO tePDAO = new TelefoneProprietarioDAO();



        //======================================================
        //                  Apenas Selects!
        //======================================================

        //definindo comando
        comando = "select * from administrador";
        // realizando comando select
        System.out.println(admDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from empresa";
        // realizando comando select
        System.out.println(empDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from endereco_empresa";
        // realizando comando select
        System.out.println(enEmpDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from endereco_granja";
        // realizando comando select
        System.out.println(enGraDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from endereco_proprietario";
        // realizando comando select
        System.out.println(enProDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from funcionario";
        // realizando comando select
        System.out.println(funDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from granja";
        // realizando comando select
        System.out.println(graDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from lote";
        // realizando comando select
        System.out.println(lotDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from meta";
        // realizando comando select
        System.out.println(metDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from proprietario_granja";
        // realizando comando select
        System.out.println(proGraDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from registro_agua";
        // realizando comando select
        System.out.println(reADAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from registro_energia";
        // realizando comando select
        System.out.println(reEDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from telefone_funcionario";
        // realizando comando select
        System.out.println(teFDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from telefone_proprietario";
        // realizando comando select
        System.out.println(tePDAO.select(comando));
        System.out.println("======================================================================");
    }
}
