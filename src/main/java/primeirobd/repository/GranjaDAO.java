package primeirobd.repository;

import primeirobd.model.Granja;

import java.util.List;

public interface GranjaDAO {
    List<Granja> select_all();
    List<Granja> select_nome();
    List<Granja> select_regiao();
    List<Granja> select_capacidade();
    String delete(int id);
    String insert(Granja gra);
    String update_id(int idNew, int idOld);
    String update_nome(String nomeNew, String nomeOld);
    String update_capacidade(String capacidadeNew, String capacidadeOld);
    String update_regiao(String regiaoNew, String regiaoOld);
    String update_area(int areaNew, int areaOld);
    String update_idEmpresa(int idEmpresaNew, int idEmpresaOld);
}
