package primeirobd.repository;

import primeirobd.model.Meta;

import java.util.List;

public interface MetaDAO {
    List<Meta> select_all();
    List<Meta> select_titulo();
    List<Meta> select_idGranja();
    List<Meta> select_valorAlvo();

    String delete (int id);
    String insert (Meta met);

    String update_id(int idNew, int idOld);
    String update_titulo(String tituloNew, String tituloOld);
    String update_idGranja(int idGranjaNew, int idGranjaOld);
    String update_valorAlvo(double valorAlvoNew, double valorAlvoOld);


}
