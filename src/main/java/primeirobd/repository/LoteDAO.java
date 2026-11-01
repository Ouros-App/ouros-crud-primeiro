package primeirobd.repository;

import primeirobd.model.Funcionario;
import primeirobd.model.Lote;

import java.sql.*;
import java.util.List;

public interface LoteDAO {
    // Busca lotes por situação ou resultado.
    List<Lote> selectAll();
    List<Lote> selectGalinhasEntregues();
    List<Lote> selectGalinhasRecebidas();
    List<Lote> selectDataChegada();
    List<Lote> selectGanho();

    // Remove e salva lotes.
    String delete(int id);
    String insert(Lote lote);

    // Atualiza os dados do lote.
    String updateId(int idNew, int idOld);
    String updateGalinhasEntregues(int entreguesNew, int entreguesOld);
    String updateGalinhasRecebidas(int recebidasNew, int recebidasOld);
    String updateDataChegada(Date dataNew, Date dataOld);
    String updateGanho(double ganhoNew, double ganhoOld);
    String updateLote(Lote lote);
    // Conta e busca lotes com filtro, ordenação e paginação.
    int contar();
    List<Lote> selectPaginado(int tamanho, int offset, String ordenarPor);
    List<Lote> selectPaginadoComBusca(String busca, int tamanho, int offset, String ordenarPor);
    int contarBusca(String busca);
}
