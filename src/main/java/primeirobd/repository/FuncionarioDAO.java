package primeirobd.repository;

import primeirobd.model.Funcionario;

import java.util.List;

public interface FuncionarioDAO {
    List<Funcionario> select_all();
    List<Funcionario> select_nome();
    List<Funcionario> select_setor();
    List<Funcionario> select_email();
    //-------------------------------------------
    String delete(int id);
    String insert(Funcionario fun);
    //-------------------------------------------
    String update_id(int idNew, int idOld);
    String update_email(String emailNew, String emailOld);
    String update_nome(String nomeNew, String nomeOld);
    String update_setor(String setorNew, String setorOld);

}
