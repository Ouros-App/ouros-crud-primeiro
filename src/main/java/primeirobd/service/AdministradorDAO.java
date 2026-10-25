package primeirobd.service;

import primeirobd.model.Administrador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO implements primeirobd.repository.AdministradorDAO {
    public static final String SELECT_ALL = "SELECT id,nome,cpf,email,email_verificado FROM administrador";
    public static final String SELECT_NOME = "SELECT nome FROM administrador";
    public static final String SELECT_ID = "SELECT id FROM administrador";
    public static final String SELECT_EMAIL = "SELECT email, foto_perfil_url FROM administrador";
    public static final String DELETE_BY_ID = "DELETE FROM administrador WHERE id = ?";
    public static final String INSERT = "INSERT INTO administrador (nome,cpf,senha,email) VALUES (?,?,?,?)";
    public static final String EXISTS_BY_EMAIL = "SELECT 1 FROM administrador WHERE email = ?";
    public static final String EXISTS_BY_CPF = "SELECT 1 FROM administrador WHERE cpf = ?";
    public static final String UPDATE_ID = "UPDATE administrador SET id = ? WHERE id = ?";
    public static final String UPDATE_EMAIL = "UPDATE administrador SET email = ? WHERE email = ?";
    public static final String UPDATE_NOME = "UPDATE administrador SET nome = ? WHERE nome = ?";
    public static final String UPDATE_EMAIL_VERIFICADO = "UPDATE administrador SET email_verificado = true WHERE id = ?";
    public static final String SELECT_BY_EMAIL = "SELECT id,nome,cpf,email,senha,email_verificado FROM administrador WHERE email = ?";
    private static final String UPDATE_SENHA = "UPDATE administrador SET senha = ? WHERE id = ?";
    public static final String SELECT_BY_ID =
            "SELECT a.id, a.nome, a.cpf, a.email, a.senha, a.email_verificado, " +
                    "a.foto_perfil_url, t.telefone " +
                    "FROM administrador a " +
                    "LEFT JOIN telefone_admin t ON t.usuario_id = a.id " +
                    "WHERE a.id = ?";
    public static final String UPDATE_PERFIL = "UPDATE administrador SET foto_perfil_url = ? WHERE id = ?";

    public Administrador buscarPorId(int id) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SELECT_BY_ID)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Administrador adm = new Administrador();
                adm.setId(rs.getInt("id"));
                adm.setNome(rs.getString("nome"));
                adm.setCpf(rs.getString("cpf"));
                adm.setEmail(rs.getString("email"));
                adm.setSenha(rs.getString("senha"));
                adm.setEmailVerificado(rs.getBoolean("email_verificado"));
                adm.setFotoPerfilUrl(rs.getString("foto_perfil_url"));
                adm.setTelefone(rs.getString("telefone"));

                return adm;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar administrador por ID.", e
            );
        }
    }

    public void atualizarFotoPerfil(int id, String url) {

        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(UPDATE_PERFIL)) {

            stmt.setString(1, url);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar foto de perfil.", e
            );
        }
    }

    public List<Administrador> select_all() {
        List<Administrador> informacoes = new ArrayList<>();
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Administrador adm = new Administrador();
                adm.setId(rs.getInt("id"));
                adm.setNome(rs.getString("nome"));
                adm.setCpf(rs.getString("cpf"));
                adm.setEmail(rs.getString("email"));
                adm.setEmailVerificado(rs.getBoolean("email_verificado"));
                informacoes.add(adm);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Administrador> select_nome() {
        List<Administrador> informacoes = new ArrayList<>();
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SELECT_NOME);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Administrador adm = new Administrador();
                adm.setNome(rs.getString("nome"));
                informacoes.add(adm);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Administrador> select_id() {
        List<Administrador> informacoes = new ArrayList<>();
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SELECT_ID);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Administrador adm = new Administrador();
                adm.setId(rs.getInt("id"));
                informacoes.add(adm);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Administrador> select_email() {
        List<Administrador> informacoes = new ArrayList<>();
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SELECT_EMAIL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Administrador adm = new Administrador();
                adm.setEmail(rs.getString("email"));
                adm.setFotoPerfilUrl(rs.getString("foto_perfil_url"));
                informacoes.add(adm);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public boolean existsByEmail(String email) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(EXISTS_BY_EMAIL)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar email existente.\n" + e.getMessage());
        }
    }

    public boolean existsByCpf(String cpf) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(EXISTS_BY_CPF)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar CPF existente.\n" + e.getMessage());
        }
    }

    public String delete(int id) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(DELETE_BY_ID)) {
            stmt.setInt(1, id);
            stmt.execute();
            return "Item apagado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public int insert(Administrador adm) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, adm.getNome());
            stmt.setString(2, adm.getCpf());
            stmt.setString(3, adm.getSenha());
            stmt.setString(4, adm.getEmail());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new SQLException("Não foi possível obter o id gerado.");

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_id(int idNew, int idOld) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(UPDATE_ID)) {
            stmt.setInt(1, idNew);
            stmt.setInt(2, idOld);
            stmt.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_email(String emailNew, String emailOld) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(UPDATE_EMAIL)) {
            stmt.setString(1, emailNew);
            stmt.setString(2, emailOld);
            stmt.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_nome(String nomeNew, String nomeOld) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(UPDATE_NOME)) {
            stmt.setString(1, nomeNew);
            stmt.setString(2, nomeOld);
            stmt.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public void marcarEmailComoVerificado(int administradorId) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(UPDATE_EMAIL_VERIFICADO)) {
            stmt.setInt(1, administradorId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao marcar email como verificado.\n" + e.getMessage());
        }
    }

    public Administrador buscarPorEmail(String email) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SELECT_BY_EMAIL)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null; // nenhum administrador com esse email
                }
                Administrador adm = new Administrador();
                adm.setId(rs.getInt("id"));
                adm.setNome(rs.getString("nome"));
                adm.setCpf(rs.getString("cpf"));
                adm.setEmail(rs.getString("email"));
                adm.setSenha(rs.getString("senha")); // aqui vem o HASH, não a senha pura
                adm.setEmailVerificado(rs.getBoolean("email_verificado"));
                return adm;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar administrador por email.\n" + e.getMessage());
        }
    }

    public void atualizarSenha(int administradorId, String senhaHash) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(UPDATE_SENHA)) {
            stmt.setString(1, senhaHash);
            stmt.setInt(2, administradorId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar senha.\n" + e.getMessage());
        }
    }
}