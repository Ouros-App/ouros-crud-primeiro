package primeirobd.service;

import primeirobd.model.Administrador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Centraliza consultas e alterações dos administradores no banco.
public class AdministradorDAO implements primeirobd.repository.AdministradorDAO {
    public static final String comandoSelectAll = "SELECT id,nome,cpf,email FROM administrador";
    public static final String comandoSelectNome = "SELECT nome FROM administrador";
    public static final String comandoSelectId = "SELECT id FROM administrador";
    public static final String comandoSelectEmail = "SELECT email, foto_perfil_url FROM administrador";
    public static final String comandoDeleteById = "DELETE FROM administrador WHERE id = ?";
    public static final String comandoInsert = "INSERT INTO administrador (nome,cpf,senha,email) VALUES (?,?,?,?)";
    public static final String comandoExistsByEmail = "SELECT 1 FROM administrador WHERE email = ?";
    public static final String comandoExistsByCpf = "SELECT 1 FROM administrador WHERE cpf = ?";
    public static final String comandoUpdateId = "UPDATE administrador SET id = ? WHERE id = ?";
    public static final String comandoUpdateEmail = "UPDATE administrador SET email = ? WHERE email = ?";
    public static final String comandoUpdateNome = "UPDATE administrador SET nome = ? WHERE nome = ?";
    public static final String comandoSelectByEmail = "SELECT id,nome,cpf,email,senha FROM administrador WHERE email = ?";
    private static final String comandoUpdateSenha = "UPDATE administrador SET senha = ? WHERE id = ?";
    public static final String comandoSelectById =
            "SELECT a.id, a.nome, a.cpf, a.email, a.senha, " +
                    "a.foto_perfil_url, t.telefone " +
                    "FROM administrador a " +
                    "LEFT JOIN telefone_admin t ON t.usuario_id = a.id " +
                    "WHERE a.id = ?";
    public static final String comandoUpdatePerfil = "UPDATE administrador SET foto_perfil_url = ? WHERE id = ?";
    private static final String comandoUpdateDados = "UPDATE administrador SET nome = ? WHERE id = ?";
    private static final String comandoExistsEmailOutro = "SELECT 1 FROM administrador WHERE email = ? AND id <> ?";

    public void updateDados(int id, String nome) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoUpdateDados)) {
            ps.setString(1, nome);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados do administrador.\n" + e.getMessage());
        }
    }

    // Retorna se outro administrador já usa o e-mail.
    public boolean emailEmUsoPorOutro(String email, int id) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoExistsEmailOutro)) {
            ps.setString(1, email);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar e-mail.\n" + e.getMessage());
        }
    }

    // A consulta também carrega a foto e o telefone usados no perfil.
    public Administrador selectById(int id) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectById)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Administrador administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setCpf(rs.getString("cpf"));
                administrador.setEmail(rs.getString("email"));
                administrador.setSenha(rs.getString("senha"));
                administrador.setFotoPerfilUrl(rs.getString("foto_perfil_url"));
                administrador.setTelefone(rs.getString("telefone"));

                return administrador;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar administrador por ID.", e
            );
        }
    }

    public void updateFotoPerfil(int id, String url) {

        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoUpdatePerfil)) {

            ps.setString(1, url);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar foto de perfil.", e
            );
        }
    }

    public List<Administrador> selectAll() {
        List<Administrador> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setCpf(rs.getString("cpf"));
                administrador.setEmail(rs.getString("email"));
                resultado.add(administrador);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Administrador> selectNome() {
        List<Administrador> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectNome);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setNome(rs.getString("nome"));
                resultado.add(administrador);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Administrador> selectId() {
        List<Administrador> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectId);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                resultado.add(administrador);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Administrador> selectEmail() {
        List<Administrador> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectEmail);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setEmail(rs.getString("email"));
                administrador.setFotoPerfilUrl(rs.getString("foto_perfil_url"));
                resultado.add(administrador);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public boolean existsByEmail(String email) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoExistsByEmail)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar email existente.\n" + e.getMessage());
        }
    }

    public boolean existsByCpf(String cpf) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoExistsByCpf)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar CPF existente.\n" + e.getMessage());
        }
    }

    public String delete(int id) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoDeleteById)) {
            ps.setInt(1, id);
            ps.execute();
            return "Item apagado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public int insert(Administrador adminisrador) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoInsert, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, adminisrador.getNome());
            ps.setString(2, adminisrador.getCpf());
            ps.setString(3, adminisrador.getSenha());
            ps.setString(4, adminisrador.getEmail());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new SQLException("Não foi possível obter o id gerado.");

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateId(int idNew, int idOld) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoUpdateId)) {
            ps.setInt(1, idNew);
            ps.setInt(2, idOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateEmail(String emailNew, String emailOld) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoUpdateEmail)) {
            ps.setString(1, emailNew);
            ps.setString(2, emailOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateNome(String nomeNew, String nomeOld) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoUpdateNome)) {
            ps.setString(1, nomeNew);
            ps.setString(2, nomeOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // A senha retornada deve ser tratada como hash, nunca como texto puro.
    public Administrador selectByEmail(String email) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectByEmail)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null; // Nenhum administrador foi encontrado com esse e-mail.
                }
                Administrador administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setCpf(rs.getString("cpf"));
                administrador.setEmail(rs.getString("email"));
                administrador.setSenha(rs.getString("senha"));
                return administrador;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar administrador por email.\n" + e.getMessage());
        }
    }

    public void updateSenha(int administradorId, String senhaHash) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoUpdateSenha)) {
            ps.setString(1, senhaHash);
            ps.setInt(2, administradorId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar senha.\n" + e.getMessage());
        }
    }
}