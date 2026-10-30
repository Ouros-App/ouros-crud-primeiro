package primeirobd.repository;

import primeirobd.model.Empresa;
import primeirobd.model.Funcionario;
import primeirobd.model.Granja;

import java.util.List;

public interface FuncionarioDAO {
    // Busca funcionários por diferentes critérios.
    List<Funcionario> select_all();
    List<Funcionario> select_nome();
    List<Funcionario> select_setor();
    List<Funcionario> select_email();
    List<Funcionario> select_cpf();
    List<Funcionario> select_telefone();

    // Remove e salva funcionários, incluindo seus telefones e empresas.
    String delete(int id);
    String insert_funcionario_com_telefone(Funcionario fun, List<Empresa> empresas);
    // Atualiza os dados principais do funcionário.
    String update_id(int idNew, int idOld);
    String update_email(Funcionario fun, String emailNew);
    String update_nome(Funcionario fun, String nomeNew);
    String update_setor(Funcionario fun, String setorNew);
    String update_telefone(Funcionario fun, String telefoneNew);
    // Conta e busca funcionários com filtros, ordenação e paginação.
    int contar();
    List<Funcionario> select_paginado(int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_tudo(String busca, int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_nome(String busca, int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_setor(String busca, int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_email(String busca, int tamanho, int offset, String ordenarPor);
    List<Funcionario> select_paginado_filtro_telefone(String busca, int tamanho, int offset, String ordenarPor);

    // Conta os resultados de cada filtro usado na paginação.
    int contar_paginado();
    int contar_paginado_filtro_tudo(String busca);
    int contar_paginado_filtro_nome(String busca);
    int contar_paginado_filtro_setor(String busca);
    int contar_paginado_filtro_email(String busca);
    int contar_paginado_filtro_telefone(String busca);

}
