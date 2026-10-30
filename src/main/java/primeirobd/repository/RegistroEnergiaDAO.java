package primeirobd.repository;

import primeirobd.model.Lote;
import primeirobd.model.RegistroEnergia;

import java.sql.*;
import java.util.List;

public interface RegistroEnergiaDAO {
    // Busca registros de energia por diferentes critérios.
    List<RegistroEnergia> select_all();
    List<RegistroEnergia> select_lote();
    List<RegistroEnergia> select_data();
    List<RegistroEnergia> select_consumo();
    // Remove e salva registros de energia.
    String delete(int id);
    String insert(RegistroEnergia ree);
    // Atualiza os dados do registro de energia.
    String update_id(int idNew, int idOld);
    String update_idLote(String lotNew, String lotOld);
    String update_data(Date dataNew, Date dataOld);
    String update_consumo(double consNew, double consOld);

    // Conta e busca registros filtrados com ordenação e paginação.
    int contar(String busca);
    List<RegistroEnergia> select_paginado(String busca, int tamanho, int offset, String ordenarPor);
}
