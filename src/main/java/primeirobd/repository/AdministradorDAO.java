package primeirobd.repository;

import primeirobd.model.Administrador;

import java.util.List;

public interface AdministradorDAO {
    List<Administrador> select_all();
    List<Administrador> select_email();
    String delete(int id);
    String insert(Administrador adm);
    String update_id(int idNew, int idOld);
    String update_email(String emailNew, String emailOld);
    String update_senha(String senhalNew, String senhaOld);


}
