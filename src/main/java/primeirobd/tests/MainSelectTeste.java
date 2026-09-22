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
        Empresa emp = new Empresa();
        Funcionario fun = new Funcionario();
        Granja gra = new Granja();
        Lote lot = new Lote();
        Meta met = new Meta();
        RegistroAgua reA = new RegistroAgua();
        RegistroEnergia reE = new RegistroEnergia();

        // acessando dados
        EmpresaDAO empDAO = new EmpresaDAO();
        FuncionarioDAO funDAO = new FuncionarioDAO();
        GranjaDAO graDAO = new GranjaDAO();
        LoteDAO lotDAO = new LoteDAO();
        MetaDAO metDAO = new MetaDAO();
        RegistroAguaDAO reADAO = new RegistroAguaDAO();
        RegistroEnergiaDAO reEDAO = new RegistroEnergiaDAO();


        //======================================================
        //                  Apenas Selects!
        //======================================================


        //definindo comando
        comando = "select * from empresa";
        // realizando comando select
        System.out.println(empDAO.select(comando));
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from funcionario";
        // realizando comando select
        System.out.println(funDAO.select_all());
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from granja";
        // realizando comando select
        System.out.println(graDAO.select_all());
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from lote";
        // realizando comando select
        System.out.println(lotDAO.select_all());
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from meta";
        // realizando comando select
        System.out.println(metDAO.select_all());
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from registro_agua";
        // realizando comando select
        System.out.println(reADAO.select_all());
        System.out.println("======================================================================");
        //definindo comando
        comando = "select * from registro_energia";
        // realizando comando select
        System.out.println(reEDAO.select_all());
        System.out.println("======================================================================");
    }
}
