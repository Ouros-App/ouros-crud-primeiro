package primeirobd.repository;

import primeirobd.model.Funcionario;
import primeirobd.model.Lote;

import java.sql.*;
import java.util.List;

public interface LoteDAO {
    // Busca lotes por situação ou resultado.
    List<Lote> select_all();
    List<Lote> select_entregue();
    List<Lote> select_recebida();
    List<Lote> select_chegada();
    List<Lote> select_ganho();

    // Remove e salva lotes.
    String delete(int id);
    String insert(Lote lot);

    // Atualiza os dados do lote.
    String update_id(int idNew, int idOld);
    String update_entregues(int entreguesNew, int entreguesOld);
    String update_recebidas(int recebidasNew, int recebidasOld);
    String update_chegada(Date dataNew, Date dataOld);
    String update_ganho(double ganhoNew, double ganhoOld);

    // Conta e busca lotes com filtro, ordenação e paginação.
    int contar();
    List<Lote> select_paginado(int tamanho, int offset, String ordenarPor);
    List<Lote> select_paginado_filtro_pesquisa(String busca, int tamanho, int offset, String ordenarPor);
    int contar_filtro_pesquisa(String busca);
}
