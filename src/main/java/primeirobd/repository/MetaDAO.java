package primeirobd.repository;

import primeirobd.model.Lote;
import primeirobd.model.Meta;

import java.util.List;

public interface MetaDAO {
    // Busca metas por diferentes critérios.
    List<Meta> select_all();
    List<Meta> select_titulo();
    List<Meta> select_idGranja();
    List<Meta> select_valorAlvo();

    // Remove e salva metas.
    String delete (int id);
    String insert (Meta met);

    // Atualiza os dados da meta.
    String update_id(int idNew, int idOld);
    String update_titulo(String tituloNew, String tituloOld);
    String update_idGranja(int idGranjaNew, int idGranjaOld);
    String update_valorAlvo(double valorAlvoNew, double valorAlvoOld);

    // Conta e busca metas com filtro, ordenação e paginação.
    int contar(boolean individual, String busca);
    List<Meta> select_paginado(boolean individual, String busca, int tamanho, int offset, String ordenarPor);

}
