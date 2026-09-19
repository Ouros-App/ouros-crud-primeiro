package primeirobd.tests;

import primeirobd.service.*;
import primeirobd.model.*;

public class MainUpdateTestes {
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



        comando = "update administrador set id = 1010 where id = 0";
        System.out.println(admDAO.update(comando));

        comando = "update empresa set id = 1010 where id = 0";
        System.out.println(empDAO.update(comando));

        comando = "update endereco_empresa set id = 1010 where id = 0";
        System.out.println(enEmpDAO.update(comando));

        comando = "update endereco_granja set id = 1010 where id = 0";
        System.out.println(enGraDAO.update(comando));

        comando = "update endereco_proprietario set id = 1010 where id = 0";
        System.out.println(enProDAO.update(comando));

        comando = "update funcionario set id = 1010 where id = 0";
        System.out.println(funDAO.update(comando));

        comando = "update granja set id = 1010 where id = 0";
        System.out.println(graDAO.update(comando));

        comando = "update lote set id = 1010 where id = 0";
        System.out.println(lotDAO.update(comando));

        comando = "update meta set id = 1010 where id = 0";
        System.out.println(metDAO.update(comando));

        comando = "update proprietario_granja set id = 1010 where id = 0";
        System.out.println(proGraDAO.update(comando));

        comando = "update registro_agua set id = 1010 where id = 0";
        System.out.println(reADAO.update(comando));

        comando = "update registro_energia set id = 1010 where id = 0";
        System.out.println(reEDAO.update(comando));

        comando = "update telefone_funcionario set id = 1010 where id = 0";
        System.out.println(teFDAO.update(comando));

        comando = "update telefone_proprietario set id = 1010 where id = 0";
        System.out.println(tePDAO.update(comando));
    }
}
