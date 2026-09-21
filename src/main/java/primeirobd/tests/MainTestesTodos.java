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

        /*
        INSERTS
         */
        //informações
        adm.setId(0);
        adm.setEmail("testeJDBC_7@teste_7.com");
        adm.setSenha("1234567");

        // realizando comando insert
        System.out.println(admDAO.insert(adm));

        //informações
        emp.setId(0);
        emp.setNome("teste");
        emp.setTelefoneCorporativo("0800 80008");
        emp.setEmailCorporativo("teste@gmail.com");
        emp.setCnpj("123456");
        //definindo comando
        comando = "insert into empresa (id, nome, telefone_corporativo, email_corporativo, cnpj) values (?, ?, ?, ?, ?)";
        // realizando comando insert
        System.out.println(empDAO.insert(comando, emp));

        //informações
        enEmp.setId(0);
        enEmp.setCep("teste");
        enEmp.setNumero(1234);
        enEmp.setMunicipio("teste");
        enEmp.setEstado("teste");
        enEmp.setLogradouro("teste");
        enEmp.setIdEmpresa(1);
        //definindo comando
        comando = "insert into endereco_empresa (id, cep, numero, municipio, estado, logradouro, id_empresa) values (?, ?, ?, ?, ?, ?, ?)";
        // realizando comando insert
        System.out.println(enEmpDAO.insert(comando, enEmp));

        //informações
        enGra.setId(0);
        enGra.setCep("teste");
        enGra.setNumero(1234);
        enGra.setMunicipio("teste");
        enGra.setEstado("teste");
        enGra.setLogradouro("teste");
        enGra.setIdGranja(1);
        //definindo comando
        comando = "insert into endereco_granja (id, cep, numero, municipio, estado, logradouro, id_granja) values (?, ?, ?, ?, ?, ?, ?)";
        // realizando comando insert
        System.out.println(enGraDAO.insert(comando, enGra));

        //informações
        enPro.setId(0);
        enPro.setCep("teste");
        enPro.setNumero(1234);
        enPro.setMunicipio("teste");
        enPro.setEstado("teste");
        enPro.setLogradouro("teste");
        enPro.setIdProprietario(1);
        //definindo comando
        comando = "insert into endereco_proprietario (id, cep, numero, municipio, estado, logradouro, id_proprietario) values (?, ?, ?, ?, ?, ?, ?)";
        // realizando comando insert
        System.out.println(enProDAO.insert(comando, enPro));

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
        proGra.setId(0);
        proGra.setNome("teste");
        proGra.setCpf("teste");
        proGra.setSenha("teste");
        proGra.setEmail("teste@teste.com");
        proGra.setIdGranja(1);
        //definindo comando
        comando = "insert into proprietario_granja (id, nome, cpf, senha, email, id_granja) values (?, ?, ?, ?, ?, ?)";
        // realizando comando insert
        System.out.println(proGraDAO.insert(comando, proGra));

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
        teF.setId(0);
        teF.setTelefone("40028922");
        teF.setIdFuncionario(1);
        //definindo comando
        comando = "insert into telefone_funcionario (id, telefone, id_funcionario) values (?, ?, ?)";
        // realizando comando insert
        System.out.println(teFDAO.insert(comando, teF));

        // informações
        teP.setId(0);
        teP.setTelefone("40028922");
        teP.setIdProprietario(1);
        //definindo comando
        comando = "insert into telefone_proprietario (id, telefone, id_proprietario) values (?, ?, ?)";
        // realizando comando insert
        System.out.println(tePDAO.insert(comando, teP));


        /*
        UPDATE
         */
        System.out.println(admDAO.update_id(1010,0));

        comando = "update empresa set id = 1010 where id = 0";
        System.out.println(empDAO.update(comando));

        comando = "update endereco_empresa set id = 1010 where id = 0";
        System.out.println(enEmpDAO.update(comando));

        comando = "update endereco_granja set id = 1010 where id = 0";
        System.out.println(enGraDAO.update(comando));

        comando = "update endereco_proprietario set id = 1010 where id = 0";
        System.out.println(enProDAO.update(comando));


        System.out.println(funDAO.update_id(1010,0));

        System.out.println(graDAO.update_id(1010,0));

        System.out.println(lotDAO.update_id(1010,0));

        System.out.println(metDAO.update_id(1010,0));

        comando = "update proprietario_granja set id = 1010 where id = 0";
        System.out.println(proGraDAO.update(comando));

        System.out.println(reADAO.update_id(1010,0));

        System.out.println(reEDAO.update_id(1010,0));

        comando = "update telefone_funcionario set id = 1010 where id = 0";
        System.out.println(teFDAO.update(comando));

        comando = "update telefone_proprietario set id = 1010 where id = 0";
        System.out.println(tePDAO.update(comando));


        /*
        DELETE
         */
        System.out.println(admDAO.delete(1010));

        comando = "delete from empresa where id = 1010;";
        System.out.println(empDAO.delete(comando));

        comando = "delete from endereco_empresa where id = 1010;";
        System.out.println(enEmpDAO.delete(comando));

        comando = "delete from endereco_granja where id = 1010;";
        System.out.println(enGraDAO.delete(comando));

        comando = "delete from endereco_proprietario where id = 1010;";
        System.out.println(enProDAO.delete(comando));

        System.out.println(funDAO.delete(1010));

        System.out.println(graDAO.delete(1010));

        System.out.println(lotDAO.delete(1010));

        System.out.println(metDAO.delete(1010));

        comando = "delete from proprietario_granja where id = 1010;";
        System.out.println(proGraDAO.delete(comando));

        System.out.println(reADAO.delete(1010));

        System.out.println(reEDAO.delete(1010));

        comando = "delete from telefone_funcionario where id = 1010;";
        System.out.println(teFDAO.delete(comando));

        comando = "delete from telefone_proprietario where id = 1010;";
        System.out.println(tePDAO.delete(comando));

        /*
        SELECT
         */

        System.out.println(admDAO.select_all());
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
        comando = "select * from proprietario_granja";
        // realizando comando select
        System.out.println(proGraDAO.select(comando));
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
