package primeirobd.service;

import primeirobd.model.ProprietarioGranja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioGranjaDAO implements primeirobd.repository.ProprietarioGranjaDAO {
    public static final String SELECT_ALL = "SELECT id,nome,cpf,email,id_granja FROM proprietario_granja";
    public static final String SELECT_NOME = "SELECT nome FROM proprietario_granja";
    public static final String SELECT_ID = "SELECT id FROM proprietario_granja";
    public static final String SELECT_EMAIL = "SELECT email FROM proprietario_granja";
    public static final String SELECT_ID_GRANJA = "SELECT id_granja FROM proprietario_granja";
    public static final String DELETE_BY_ID = "DELETE FROM proprietario_granja WHERE id = ?";
    public static final String INSERT = "INSERT INTO proprietario_granja (nome,cpf,senha,email,id_granja) VALUES (?,?,?,?,?)";
    public static final String EXISTS_BY_EMAIL = "SELECT 1 FROM proprietario_granja WHERE email = ?";
    public static final String EXISTS_BY_CPF = "SELECT 1 FROM proprietario_granja WHERE cpf = ?";
    public static final String UPDATE_ID = "UPDATE proprietario_granja SET id = ? WHERE id = ?";
    public static final String UPDATE_EMAIL = "UPDATE proprietario_granja SET email = ? WHERE email = ?";
    public static final String UPDATE_NOME = "UPDATE proprietario_granja SET nome = ? WHERE nome = ?";
    public static final String UPDATE_SENHA = "UPDATE proprietario_granja SET senha = ? WHERE senha = ?";
    public static final String UPDATE_ID_GRANJA = "UPDATE proprietario_granja SET id_granja = ? WHERE id_granja = ?";
    public static final String UPDATE_EMAIL_VERIFICADO = "UPDATE proprietario_granja SET email_verificado = true WHERE id = ?";


    public List<ProprietarioGranja> select_all() {
        List<ProprietarioGranja> informacoes = new ArrayList<>();
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                ProprietarioGranja prg = new ProprietarioGranja();
                prg.setId(resultadoConsulta.getInt("id"));
                prg.setCpf(resultadoConsulta.getString("cpf"));
                prg.setEmail(resultadoConsulta.getString("email"));
                prg.setIdGranja(resultadoConsulta.getInt("id_granja"));
                prg.setNome(resultadoConsulta.getString("nome"));
                informacoes.add(prg);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<ProprietarioGranja> select_nome() {
        List<ProprietarioGranja> informacoes = new ArrayList<>();
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_NOME);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                ProprietarioGranja prg = new ProprietarioGranja();
                prg.setNome(resultadoConsulta.getString("nome"));
                informacoes.add(prg);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<ProprietarioGranja> select_id() {
        List<ProprietarioGranja> informacoes = new ArrayList<>();
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ID);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                ProprietarioGranja prg = new ProprietarioGranja();
                prg.setId(resultadoConsulta.getInt("id"));
                informacoes.add(prg);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<ProprietarioGranja> select_idGranja() {
        List<ProprietarioGranja> informacoes = new ArrayList<>();
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ID_GRANJA);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                ProprietarioGranja prg = new ProprietarioGranja();
                prg.setIdGranja(resultadoConsulta.getInt("id_granja"));
                informacoes.add(prg);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<ProprietarioGranja> select_email() {
        List<ProprietarioGranja> informacoes = new ArrayList<>();
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_EMAIL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                ProprietarioGranja prg = new ProprietarioGranja();
                prg.setEmail(resultadoConsulta.getString("email"));
                informacoes.add(prg);
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
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(DELETE_BY_ID)) {
            preparoConsultaSQL.setInt(1, id);
            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public int insert(ProprietarioGranja prg) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(
                     INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparoConsultaSQL.setString(1, prg.getNome());
            preparoConsultaSQL.setString(2, prg.getCpf());
            preparoConsultaSQL.setString(3, prg.getSenha());
            preparoConsultaSQL.setString(4, prg.getEmail());
            preparoConsultaSQL.setInt(5, prg.getIdGranja());
            preparoConsultaSQL.executeUpdate();

            try (ResultSet rs = preparoConsultaSQL.getGeneratedKeys()) {
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
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ID)) {
            preparoConsultaSQL.setInt(1, idNew);
            preparoConsultaSQL.setInt(2, idOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_idGranja(int idGranjaNew, int idGranjaOld) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ID_GRANJA)) {
            preparoConsultaSQL.setInt(1, idGranjaNew);
            preparoConsultaSQL.setInt(2, idGranjaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_email(String emailNew, String emailOld) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_EMAIL)) {
            preparoConsultaSQL.setString(1, emailNew);
            preparoConsultaSQL.setString(2, emailOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_nome(String nomeNew, String nomeOld) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_NOME)) {
            preparoConsultaSQL.setString(1, nomeNew);
            preparoConsultaSQL.setString(2, nomeOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_senha(String senhaNew, String senhaOld) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_SENHA)) {
            preparoConsultaSQL.setString(1, senhaNew);
            preparoConsultaSQL.setString(2, senhaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public void marcarEmailComoVerificado(int usuarioId) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(UPDATE_EMAIL_VERIFICADO)) {

            stmt.setInt(1, usuarioId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao marcar email como verificado.\n" + e.getMessage());
        }
    }
}