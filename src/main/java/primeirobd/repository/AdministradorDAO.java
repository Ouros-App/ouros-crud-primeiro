package primeirobd.repository;

import primeirobd.model.Administrador;
import java.util.List;

public interface AdministradorDAO {
    // Busca administradores por diferentes critérios.
    List<Administrador> select_all();
    List<Administrador> select_nome();
    List<Administrador> select_id();
    List<Administrador> select_email();
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    // Remove, salva e atualiza dados de administradores.
    String delete(int id);
    int insert(Administrador adm);
    String update_id(int idNew, int idOld);
    String update_email(String emailNew, String emailOld);
    String update_nome(String nomeNew, String nomeOld);

    // Busca um administrador para login ou consulta detalhada.
    Administrador buscarPorEmail(String email);

    // Busca pelo identificador e atualiza a foto de perfil.
    Administrador buscarPorId(int id);
    void atualizarFotoPerfil(int id, String url);
}
