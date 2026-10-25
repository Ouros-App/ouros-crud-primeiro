package primeirobd.testes;

import primeirobd.DAO.*;
import primeirobd.model.*;

public class MainDeleteTestes {
    public static void main(String[] args) {
        /*
    =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
    ░█████████                                                             ░████████████                        ░██
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

        comando = "delete from administrador where id = 1010;";
        System.out.println(admDAO.delete(comando));

        comando = "delete from empresa where id = 1010;";
        System.out.println(empDAO.delete(comando));

        comando = "delete from endereco_empresa where id = 1010;";
        System.out.println(enEmpDAO.delete(comando));

        comando = "delete from endereco_granja where id = 1010;";
        System.out.println(enGraDAO.delete(comando));

        comando = "delete from endereco_proprietario where id = 1010;";
        System.out.println(enProDAO.delete(comando));

        comando = "delete from funcionario where id = 1010;";
        System.out.println(funDAO.delete(comando));

        comando = "delete from granja where id = 1010;";
        System.out.println(graDAO.delete(comando));

        comando = "delete from lote where id = 1010;";
        System.out.println(lotDAO.delete(comando));

        comando = "delete from meta where id = 1010;";
        System.out.println(metDAO.delete(comando));

        comando = "delete from proprietario_granja where id = 1010;";
        System.out.println(proGraDAO.delete(comando));

        comando = "delete from registro_agua where id = 1010;";
        System.out.println(reADAO.delete(comando));

        comando = "delete from registro_energia where id = 1010;";
        System.out.println(reEDAO.delete(comando));

        comando = "delete from telefone_funcionario where id = 1010;";
        System.out.println(teFDAO.delete(comando));

        comando = "delete from telefone_proprietario where id = 1010;";
        System.out.println(tePDAO.delete(comando));
    }
}
