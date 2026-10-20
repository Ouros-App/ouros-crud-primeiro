package primeirobd.service;

import primeirobd.model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO implements primeirobd.repository.AdministradorDAO {
    public static final String SELECT_ALL = "SELECT id,email FROM administrador";
    public static final String SELECT_EMAIL = "SELECT email FROM administrador";
    public static final String INSERT = "INSERT INTO administrador (id, email, senha) values (?, ?, ?)";
    public static final String DELETE_BY_ID = "DELETE FROM administrador WHERE id = ?";
    public static final String UPDATE_ID = "UPDATE administrador SET id = ? WHERE id = ?";
    public static final String UPDATE_EMAIL = "UPDATE administrador SET email = ? WHERE email = ?";
    public static final String UPDATE_SENHA = "UPDATE administrador SET senha = ? WHERE senha = ?";

    // metodos selects :D
    public List<Administrador> select_all() {
        List<Administrador> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection(); // conexão compartilhada, não fecha aqui
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
            while (resultadoConsulta.next()) {
                Administrador adm = new Administrador();
                adm.setId(resultadoConsulta.getInt("id"));
                adm.setEmail(resultadoConsulta.getString("email"));
                resultado.add(adm);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // email
    public List<Administrador> select_email() {
        List<Administrador> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection(); // conexão compartilhada, não fecha aqui
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_EMAIL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
            while (resultadoConsulta.next()) {
                Administrador adm = new Administrador();
                adm.setEmail(resultadoConsulta.getString("email"));
                resultado.add(adm);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // metodo delete :)
    public String delete(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(DELETE_BY_ID)) {
            preparoConsultaSQL.setInt(1,id);
            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
    // metodo insert :O
    public String insert(Administrador adm) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {
            preparoConsultaSQL.setInt(1, adm.getId());
            preparoConsultaSQL.setString(2, adm.getEmail());
            preparoConsultaSQL.setString(3, adm.getSenha());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodos updates :P

    //id
    public String update_id(int idNew, int idOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ID)) {
            preparoConsultaSQL.setInt(1, idNew);
            preparoConsultaSQL.setInt(2, idOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
    //email
    public String update_email(String emailNew, String emailOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_EMAIL)) {
            preparoConsultaSQL.setString(1, emailNew);
            preparoConsultaSQL.setString(2, emailOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
    //Senha
    public String update_senha(String senhaNew, String senhaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_SENHA)) {
            preparoConsultaSQL.setString(1, senhaNew);
            preparoConsultaSQL.setString(2, senhaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}