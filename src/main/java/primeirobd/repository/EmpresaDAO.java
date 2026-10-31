package primeirobd.repository;

import primeirobd.model.Empresa;

import java.util.List;

public interface EmpresaDAO {
    // Busca todas as empresas.
    List<Empresa> select_all();

    // Remove, salva e atualiza empresas.
    String delete(int id);
    String insert(Empresa emp);
    String update(Empresa emp);
    // Verifica a existência de uma empresa pelo CNPJ, quando necessário.
    // boolean existsByCnpj(String cnpj);

    // Conta e busca empresas com ou sem filtro, usando paginação.
    int contar();
    int contar_filtro_pesquisa(String busca);
    List<Empresa> select_paginado(int tamanho, int offset);
    List<Empresa> select_paginado_filtro_pesquisa(String busca, int tamanho, int offset);
}
