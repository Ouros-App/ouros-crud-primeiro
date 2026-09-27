package primeirobd.repository;

import primeirobd.model.ProprietarioGranja;

import java.util.List;

public interface ProprietarioGranjaDAO {
    List<ProprietarioGranja> select_all();
    List<ProprietarioGranja> select_nome();
    List<ProprietarioGranja> select_id();
    List<ProprietarioGranja> select_idGranja();
    List<ProprietarioGranja> select_email();
    //-------------------------------------------
    String delete(int id);
    int insert(ProprietarioGranja prg);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    void marcarEmailComoVerificado(int usuarioId);
    //-------------------------------------------
    String update_id(int idNew, int idOld);
    String update_email(String emailNew, String emailOld);
    String update_nome(String nomeNew, String nomeOld);
    String update_idGranja(int idGranjaNew, int idGranjaOld);
    String update_senha(String senhaNew, String senhaOld);
}