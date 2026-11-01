package primeirobd.repository;

import primeirobd.model.Lote;
import primeirobd.model.Meta;

import java.util.List;

public interface MetaDAO {
    // Busca metas por diferentes critérios.
    List<Meta> selectAll();
    List<Meta> selectTitulo();
    List<Meta> selectIdGranja();
    List<Meta> selectValorAlvo();

    // Remove e salva metas.
    String delete (int id);
    String insert (Meta meta);

    // Atualiza os dados da meta.
    String updateId(int idNew, int idOld);
    String updateTitulo(String tituloNew, String tituloOld);
    String updateIdGranja(int idGranjaNew, int idGranjaOld);
    String updateValorAlvo(double valorAlvoNew, double valorAlvoOld);
    String updateMeta(Meta meta);

    // Conta e busca metas com filtro, ordenação e paginação.
    int contarBusca(boolean individual, String busca);
    int contarAllMetas();

    List<Meta> selectPaginado();
    List<Meta> selectPaginadoComBusca(boolean individual, String busca, int tamanho, int offset, String ordenarPor);

}
