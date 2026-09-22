package primeirobd.tests;

import primeirobd.service.*;
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


        comando = "delete from empresa where id = 1010;";
        System.out.println(empDAO.delete(comando));

        System.out.println(funDAO.delete(1010));

        System.out.println(graDAO.delete(1010));

        System.out.println(lotDAO.delete(1010));

        System.out.println(metDAO.delete(1010));

        System.out.println(reADAO.delete(1010));

        System.out.println(reEDAO.delete(1010));

    }
}
