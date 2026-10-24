package primeirobd.repository;

import primeirobd.model.Empresa;
import primeirobd.model.Funcionario;
import primeirobd.model.Granja;

import java.util.List;

public interface FuncionarioDAO {
    List<Funcionario> select_all();
    List<Funcionario> select_nome();
    List<Funcionario> select_setor();
    List<Funcionario> select_email();
    List<Funcionario> select_cpf();
    List<Funcionario> select_telefone();
    //-------------------------------------------
    String delete(int id);
    String insert_funcionario_com_telefone(Funcionario fun, List<Empresa> empresas);
    //-------------------------------------------
    String update_id(int idNew, int idOld);
    String update_email(Funcionario fun, String emailNew);
    String update_nome(Funcionario fun, String nomeNew);
    String update_setor(Funcionario fun, String setorNew);
    String update_telefone(Funcionario fun, String telefoneNew);


    //-------------------------------------------
    int contar();
    List<Funcionario> select_paginado(int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_tudo(String busca, int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_nome(String busca, int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_setor(String busca, int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_email(String busca, int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_telefone(String busca, int tamanho, int offset, String ordenarPor);

    int contar_paginado();
    int contar_paginado_filtro_tudo(String busca);
    int contar_paginado_filtro_nome(String busca);
    int contar_paginado_filtro_setor(String busca);
    int contar_paginado_filtro_email(String busca);
    int contar_paginado_filtro_telefone(String busca);

}
