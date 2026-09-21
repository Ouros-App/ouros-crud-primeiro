package primeirobd.repository;

import primeirobd.model.Lote;

import java.sql.*;
import java.util.List;

public interface LoteDAO {
    List<Lote> select_all();
    List<Lote> select_entregue();
    List<Lote> select_recebida();
    List<Lote> select_chegada();
    List<Lote> select_ganho();
    String delete(int id);
    String insert(Lote lot);
    String update_id(int idNew, int idOld);
    String update_entregues(int entreguesNew, int entreguesOld);
    String update_recebidas(int recebidasNew, int recebidasOld);
    String update_chegada(Date dataNew, Date dataOld);
    String update_ganho(double ganhoNew, double ganhoOld);


}
