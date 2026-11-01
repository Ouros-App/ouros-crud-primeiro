package primeirobd.repository;

import primeirobd.model.Empresa;

import java.util.List;

public interface EmpresaDAO {
    // Busca todas as empresas.
    List<Empresa> selectAll();

    // Remove, salva e atualiza empresas.
    String delete(int id);
    String insert(Empresa empresa);
    String updateAll(Empresa empresa);
    // Verifica a existência de uma empresa pelo CNPJ, quando necessário.
    // boolean existsByCnpj(String cnpj);

    // Conta e busca empresas com ou sem filtro, usando paginação.
    int contar();
    int contarBusca(String busca);
    List<Empresa> selectPaginado(int tamanho, int offset);
    List<Empresa> selectPaginadoComBusca(String busca, int tamanho, int offset);
}
