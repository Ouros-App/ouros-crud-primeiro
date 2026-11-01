package primeirobd.repository;

import primeirobd.model.ProprietarioGranja;

import java.util.List;

public interface ProprietarioGranjaDAO {
    // Busca proprietários por diferentes critérios.
    List<ProprietarioGranja> selectAll();
    List<ProprietarioGranja> selectNome();
    List<ProprietarioGranja> selectId();
    List<ProprietarioGranja> selectIdGranja();
    List<ProprietarioGranja> selectEmail();
    // Remove, salva e verifica proprietários.
    String delete(int id);
    int insert(ProprietarioGranja proprietarioGranja);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    ProprietarioGranja selectByEmail(String email);
    // Atualiza os dados do proprietário e seu vínculo com a granja.
    String updateId(int idNew, int idOld);
    String updateEmail(String emailNew, String emailOld);
    String updateNome(String nomeNew, String nomeOld);
    String updateIdGranja(int idGranjaNew, int idGranjaOld);
    String updateSenha(String senhaNew, String senhaOld);
}
