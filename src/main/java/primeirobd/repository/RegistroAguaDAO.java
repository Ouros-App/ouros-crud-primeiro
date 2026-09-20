package primeirobd.repository;

import primeirobd.model.RegistroAgua;

import java.sql.*;
import java.util.List;

public interface RegistroAguaDAO {
    List<RegistroAgua> select_all();
    List<RegistroAgua> select_lote();
    List<RegistroAgua> select_data();
    List<RegistroAgua> select_hidroInic();
    List<RegistroAgua> select_hidroFim();
    //---------------------------------------------
    String delete(int id);
    String insert(RegistroAgua reA);
    //---------------------------------------------
    String update_id(int idNew, int idOld);
    String update_idLote(String lotNew, String lotOld);
    String update_data(Date dataNew, Date dataOld);
    String update_hidroInic(double hidroNew, double hidroOld);
    String update_hidroFim(double hidroNew, double hidroOld);

}
