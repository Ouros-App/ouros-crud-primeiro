package primeirobd.repository;

import primeirobd.model.Empresa;

import java.util.List;

public interface EmpresaDAO {
    List<Empresa> select_all();
    //-------------------------------------------
    String delete(int id);
    String insert(Empresa emp);
    String update(Empresa emp);
    //-------------------------------------------
//    boolean existsByCnpj(String cnpj);
    //-------------------------------------------
    int contar();
    int contar_filtro_pesquisa(String busca);
    List<Empresa> select_paginado(int tamanho, int offset);
    List<Empresa> select_paginado_filtro_pesquisa(String busca, int tamanho, int offset);
}
