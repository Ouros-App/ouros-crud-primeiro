package primeirobd.repository;

import primeirobd.model.Administrador;
import java.util.List;

public interface AdministradorDAO {
    // Busca administradores por diferentes critérios.
    List<Administrador> selectAll();
    List<Administrador> selectNome();
    List<Administrador> selectId();
    List<Administrador> selectEmail();
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    // Remove, salva e atualiza dados de administradores.
    String delete(int id);
    int insert(Administrador adm);
    String updateId(int idNew, int idOld);
    String updateEmail(String emailNew, String emailOld);
    String updateNome(String nomeNew, String nomeOld);
    void updateDados(int id, String nome);

    // Busca um administrador para login ou consulta detalhada.
    Administrador selectByEmail(String email);

    // Busca pelo identificador e atualiza a foto de perfil.
    Administrador selectById(int id);
    void updateFotoPerfil(int id, String url);
    void updateSenha(int administradorId, String senhaHash);
}
