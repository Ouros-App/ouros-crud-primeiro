package primeirobd.repository;

import primeirobd.model.RegistroEnergia;

import java.sql.*;
import java.util.List;

public interface RegistroEnergiaDAO {
    List<RegistroEnergia> select_all();
    List<RegistroEnergia> select_lote();
    List<RegistroEnergia> select_data();
    List<RegistroEnergia> select_consumo();
    //---------------------------------------------
    String delete(int id);
    String insert(RegistroEnergia ree);
    //---------------------------------------------
    String update_id(int idNew, int idOld);
    String update_idLote(String lotNew, String lotOld);
    String update_data(Date dataNew, Date dataOld);
    String update_consumo(double consNew, double consOld);


}
