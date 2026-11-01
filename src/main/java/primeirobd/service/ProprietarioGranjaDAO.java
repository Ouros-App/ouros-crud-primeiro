package primeirobd.service;

import primeirobd.model.ProprietarioGranja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Executa operações dos proprietários associados às granjas.
public class ProprietarioGranjaDAO implements primeirobd.repository.ProprietarioGranjaDAO {
    public static final String comandoSelectAll = "SELECT id,nome,cpf,email,id_granja FROM proprietario_granja";
    public static final String comandoSelectNome = "SELECT nome FROM proprietario_granja";
    public static final String comandoSelectId = "SELECT id FROM proprietario_granja";
    public static final String comandoSelectEmail = "SELECT email FROM proprietario_granja";
    public static final String comandoSelectIdGranja = "SELECT id_granja FROM proprietario_granja";
    public static final String comandoDeleteById = "DELETE FROM proprietario_granja WHERE id = ?";
    public static final String comandoInsert = "INSERT INTO proprietario_granja (nome,cpf,senha,email,id_granja) VALUES (?,?,?,?,?)";
    public static final String comandoExistsByEmail = "SELECT 1 FROM proprietario_granja WHERE email = ?";
    public static final String comandoExistsByCpf = "SELECT 1 FROM proprietario_granja WHERE cpf = ?";
    public static final String comandoUpdateId = "UPDATE proprietario_granja SET id = ? WHERE id = ?";
    public static final String comandoUpdateEmail = "UPDATE proprietario_granja SET email = ? WHERE email = ?";
    public static final String comandoUpdateNome = "UPDATE proprietario_granja SET nome = ? WHERE nome = ?";
     public static final String comandoUpdateIdGranja = "UPDATE proprietario_granja SET id_granja = ? WHERE id_granja = ?";
    public static final String comandoSelectByEmail = "SELECT id,nome,cpf,email,senha,id_granja FROM proprietario_granja WHERE email = ?";
    private static final String comandoUpdateSenha = "UPDATE proprietario_granja SET senha = ? WHERE id = ?";

    public List<ProprietarioGranja> selectAll() {
        List<ProprietarioGranja> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProprietarioGranja proprietarioGranja = new ProprietarioGranja();
                proprietarioGranja.setId(rs.getInt("id"));
                proprietarioGranja.setCpf(rs.getString("cpf"));
                proprietarioGranja.setEmail(rs.getString("email"));
                proprietarioGranja.setIdGranja(rs.getInt("id_granja"));
                proprietarioGranja.setNome(rs.getString("nome"));
                resultado.add(proprietarioGranja);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<ProprietarioGranja> selectNome() {
        List<ProprietarioGranja> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectNome);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProprietarioGranja proprietarioGranja = new ProprietarioGranja();
                proprietarioGranja.setNome(rs.getString("nome"));
                resultado.add(proprietarioGranja);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<ProprietarioGranja> selectId() {
        List<ProprietarioGranja> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectId);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProprietarioGranja proprietarioGranja = new ProprietarioGranja();
                proprietarioGranja.setId(rs.getInt("id"));
                resultado.add(proprietarioGranja);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<ProprietarioGranja> selectIdGranja() {
        List<ProprietarioGranja> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectIdGranja);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProprietarioGranja proprietarioGranja = new ProprietarioGranja();
                proprietarioGranja.setIdGranja(rs.getInt("id_granja"));
                resultado.add(proprietarioGranja);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<ProprietarioGranja> selectEmail() {
        List<ProprietarioGranja> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoSelectEmail);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProprietarioGranja proprietarioGranja = new ProprietarioGranja();
                proprietarioGranja.setEmail(rs.getString("email"));
                resultado.add(proprietarioGranja);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public boolean existsByEmail(String email) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = connection.prepareStatement(comandoExistsByEmail)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar email existente.\n" + e.getMessage());
        }
    }

    public boolean existsByCpf(String cpf) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = connection.prepareStatement(comandoExistsByCpf)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
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

    public int insert(ProprietarioGranja proprietarioGranja) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     comandoInsert, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, proprietarioGranja.getNome());
            ps.setString(2, proprietarioGranja.getCpf());
            ps.setString(3, proprietarioGranja.getSenha());
            ps.setString(4, proprietarioGranja.getEmail());
            ps.setInt(5, proprietarioGranja.getIdGranja());
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

    public String updateIdGranja(int idGranjaNew, int idGranjaOld) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoUpdateIdGranja)) {
            ps.setInt(1, idGranjaNew);
            ps.setInt(2, idGranjaOld);
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

    public String updateSenha(String senhaNew, String senhaOld) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = connection.prepareStatement(comandoUpdateSenha)) {
            ps.setString(1, senhaNew);
            ps.setString(2, senhaOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

// A senha lida do banco é um hash usado apenas na autenticação.
    public ProprietarioGranja selectByEmail(String email) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = connection.prepareStatement(comandoSelectByEmail)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null; // Nenhum usuário foi encontrado com esse e-mail.
                }
                ProprietarioGranja proprietarioGranja = new ProprietarioGranja();
                proprietarioGranja.setId(rs.getInt("id"));
                proprietarioGranja.setNome(rs.getString("nome"));
                proprietarioGranja.setCpf(rs.getString("cpf"));
                proprietarioGranja.setEmail(rs.getString("email"));
                proprietarioGranja.setSenha(rs.getString("senha")); // O valor lido é o hash, não a senha em texto puro.
                proprietarioGranja.setIdGranja(rs.getInt("id_granja"));
                return proprietarioGranja;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email.\n" + e.getMessage());
        }
    }
// Persiste somente o hash já preparado pela camada de autenticação.
    public void atualizarSenha(int usuarioId, String senhaHash) {
        try (Connection connection = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = connection.prepareStatement(comandoUpdateSenha)) {

            stmt.setString(1, senhaHash);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar senha.\n" + e.getMessage());
        }
    }
}