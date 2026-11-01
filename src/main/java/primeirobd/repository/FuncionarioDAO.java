package primeirobd.repository;

import primeirobd.model.Empresa;
import primeirobd.model.Funcionario;
import primeirobd.model.Granja;

import java.util.List;

public interface FuncionarioDAO {
    // Busca funcionários por diferentes critérios.
    List<Funcionario> selectAll();
    List<Funcionario> selectNome();
    List<Funcionario> selectSetor();
    List<Funcionario> selectEmail();
    List<Funcionario> selectCpf();
    List<Funcionario> selectTelefone();

    // Remove e salva funcionários, incluindo seus telefones e empresas.
    String delete(int id);
    String insertFuncionarioComTelefone(Funcionario fun, List<Empresa> empresas);
    // Atualiza os dados principais do funcionário.
    String updateId(int idNew, int idOld);
    String updateEmail(Funcionario funcionario, String emailNew);
    String updateNome(Funcionario funcionario, String nomeNew);
    String updateSetor(Funcionario funcionario, String setorNew);
    String updateTelefone(Funcionario funcionario, String telefoneNew);
    // Conta e busca funcionários com filtros, ordenação e paginação.
    int contar();
    List<Funcionario> selectPaginado(int tamanho, int offset, String ordenarPor);
    List<Funcionario> selectPaginadoComBusca(String busca, int tamanho, int offset, String ordenarPor);

    // Conta os resultados de cada filtro usado na paginação.
    int contarPaginado();
    int contarPaginadoComBusca(String busca);
}
