package primeirobd.tests;

import primeirobd.service.*;
import primeirobd.model.*;

public class MainTestesTodos {
    public static void main(String[] args) {
        /*
    =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
        ███████╗ ██████╗ ███╗   ██╗ █████╗     ██████╗ ███████╗    ████████╗███████╗███████╗████████╗███████╗███████╗
        ╚══███╔╝██╔═══██╗████╗  ██║██╔══██╗    ██╔══██╗██╔════╝    ╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██╔════╝██╔════╝
          ███╔╝ ██║   ██║██╔██╗ ██║███████║    ██║  ██║█████╗         ██║   █████╗  ███████╗   ██║   █████╗  ███████╗
         ███╔╝  ██║   ██║██║╚██╗██║██╔══██║    ██║  ██║██╔══╝         ██║   ██╔══╝  ╚════██║   ██║   ██╔══╝  ╚════██║
        ███████╗╚██████╔╝██║ ╚████║██║  ██║    ██████╔╝███████╗       ██║   ███████╗███████║   ██║   ███████╗███████║
        ╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═╝    ╚═════╝ ╚══════╝       ╚═╝   ╚══════╝╚══════╝   ╚═╝   ╚══════╝╚══════╝
    =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
         */
        // variavel do comando
        String comando;

        // objetos das tabelas
        Funcionario fun = new Funcionario();
        Granja gra = new Granja();
        Lote lot = new Lote();
        Meta met = new Meta();
        RegistroAgua reA = new RegistroAgua();
        RegistroEnergia reE = new RegistroEnergia();

        // acessando dados
        FuncionarioDAO funDAO = new FuncionarioDAO();
        GranjaDAO graDAO = new GranjaDAO();
        LoteDAO lotDAO = new LoteDAO();
        MetaDAO metDAO = new MetaDAO();
        RegistroAguaDAO reADAO = new RegistroAguaDAO();
        RegistroEnergiaDAO reEDAO = new RegistroEnergiaDAO();

        /*
        INSERTS
         */
        //informações
        fun.setId(0);
        fun.setNome("teste");
        fun.setCpf("1234");
        fun.setEmail("teste@teste.com");
        fun.setSetor("teste");
        fun.setIdEmpresa(1);

        //realizando comando insert
        System.out.println(funDAO.insert(fun));

        //informações
        gra.setId(0);
        gra.setNome("teste");
        gra.setCapacidadeDeAves(1234);
        gra.setRegiao("teste");
        gra.setAreaPropriedade(1234);
        gra.setIdEmpresa(1);
        // realizando comando insert
        System.out.println(graDAO.insert(gra));

        //informações
        lot.setId(0);
        lot.setGalinhasEntregadas(1234);
        lot.setGalinhasRecebidas(1234);
        lot.setDtNascimento(java.sql.Date.valueOf("0101-01-01"));
        lot.setDtChegada(java.sql.Date.valueOf("0101-01-01"));
        lot.setDtEntrega(java.sql.Date.valueOf("0101-01-01"));
        lot.setGanho(0);
        lot.setIdGranja(1);
        // realizando comando insert
        System.out.println(lotDAO.insert(lot));

        // informações
        met.setId(0);
        met.setTitulo("teste");
        met.setDescricao("teste");
        met.setTipoMeta("teste");
        met.setStatus("NAO_INICIADA");
        met.setValorAlvo(1234);
        met.setRegiao("teste");
        met.setEstado("teste");
        met.setIndividual(true);
        met.setIdGranja(1);
        // realizando comando insert
        System.out.println(metDAO.insert(met));

        // informações
        reA.setId(0);
        reA.setDtRegistro(java.sql.Date.valueOf("0101-01-01"));
        reA.setHidrometroInicio(1234);
        reA.setHidrometroFinal(1234);
        reA.setIdLote(1);
        // realizando comando insert
        System.out.println(reADAO.insert(reA));

        // informações
        reE.setId(0);
        reE.setDtRegistro(java.sql.Date.valueOf("0101-01-01"));
        reE.setConsumo(1234);
        reE.setIdLote(1);
        // realizando comando insert
        System.out.println(reEDAO.insert(reE));

        // informações


        /*
        UPDATE
         */

        System.out.println(funDAO.update_id(1010,0));

        System.out.println(graDAO.update_id(1010,0));

        System.out.println(lotDAO.update_id(1010,0));

        System.out.println(metDAO.update_id(1010,0));

        System.out.println(reADAO.update_id(1010,0));

        System.out.println(reEDAO.update_id(1010,0));

        /*
        DELETE
         */

        System.out.println(funDAO.delete(1010));

        System.out.println(graDAO.delete(1010));

        System.out.println(lotDAO.delete(1010));

        System.out.println(metDAO.delete(1010));

        System.out.println(reADAO.delete(1010));

        System.out.println(reEDAO.delete(1010));

        /*
        SELECT
         */
        // realizando comando select
        System.out.println(funDAO.select_all());
        System.out.println("======================================================================");
        // realizando comando select
        System.out.println(graDAO.select_all());
        System.out.println("======================================================================");
        //realizando comando select
        System.out.println(lotDAO.select_all());
        System.out.println("======================================================================");
        //realizando comando select
        System.out.println(metDAO.select_all());
        System.out.println("======================================================================");
        // realizando comando select
        System.out.println(reADAO.select_all());
        System.out.println("======================================================================");
        // realizando comando select
        System.out.println(reEDAO.select_all());
        System.out.println("======================================================================");


    }
}
