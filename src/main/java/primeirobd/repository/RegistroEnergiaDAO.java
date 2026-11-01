package primeirobd.repository;

import primeirobd.model.Lote;
import primeirobd.model.RegistroEnergia;

import java.sql.*;
import java.util.List;

public interface RegistroEnergiaDAO {
    // Busca registros de energia por diferentes critérios.
    List<RegistroEnergia> selectAll();
    List<RegistroEnergia> selectLote();
    List<RegistroEnergia> selectData();
    List<RegistroEnergia> selectConsumo();
    RegistroEnergia selectById(int id);
    // Remove e salva registros de energia.
    String delete(int id);
    String insert(RegistroEnergia registroEnergia);
    // Atualiza os dados do registro de energia.
    String updateId(int idNew, int idOld);
    String updateIdLote(String loteNew, String loteOld);
    String updateData(Date dataNew, Date dataOld);
    String updateConsumo(double consumoNew, double consumoOld);
    String updateRegistro(RegistroEnergia registroEnergia);

    // Conta e busca registros filtrados com ordenação e paginação.
    int contarBusca(String busca);
    List<RegistroEnergia> selectPaginadoComBusca(String busca, int tamanho, int offset, String ordenarPor);
}
