package primeirobd.service;

import primeirobd.model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements primeirobd.repository.FuncionarioDAO {
    public static final String SELECT_ALL = "SELECT * FROM funcionario";
    public static final String SELECT_NOME = "SELECT nome FROM funcionario";
    public static final String SELECT_SETOR = "SELECT setor FROM funcionario";
    public static final String SELECT_EMAIL = "SELECT email FROM funcionario";
    public static final String DELETE_BY_ID = "DELETE FROM funcionario WHERE id = ?";
    public static final String INSERT = "INSERT INTO funcionario (nome,cpf,email,setor,telefone) VALUES (?,?,?,?,?)";
    public static final String UPDATE_ID = "UPDATE funcionario SET id = ? WHERE id = ?";
    public static final String UPDATE_EMAIL = "UPDATE funcionario SET email = ? WHERE email = ?";
    public static final String UPDATE_NOME = "UPDATE funcionario SET nome = ? WHERE nome = ?";
    public static final String UPDATE_SETOR = "UPDATE funcionario SET setor = ? WHERE setor = ?";
    public static final String SELECT_COUNT =  "SELECT COUNT(*) FROM funcionario";
    public static final String SELECT_PAGINADO =
            "SELECT funcionario.nome, " +
            "funcionario.setor, " +
            "funcionario.email, " +
            "telefone_funcionario.telefone AS telefone " +
            "FROM funcionario " +
            "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
            "LIMIT ? OFFSET ?";
    public static final String SELECT_PAGINADO_FILTROS =
            "SELECT funcionario.nome, " +
            "funcionario.setor, " +
            "funcionario.email, " +
            "telefone_funcionario.telefone " +
            "FROM funcionario " +
            "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
            "WHERE funcionario.nome LIKE ? OR funcionario.setor LIKE ? OR funcionario.email LIKE ? OR telefone_funcionario.telefone LIKE ? " +
            "LIMIT ? OFFSET ?";

    public int contar() {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try(PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_COUNT);
            ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()){
            if (resultadoConsulta.next()){
                return resultadoConsulta.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar. \n"+e.getMessage());
        }
    }

    public List<Funcionario> select_paginado(int tamanho, int offset) {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO)) {
            preparoConsultaSQL.setInt(1, tamanho);
            preparoConsultaSQL.setInt(2, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

                while (resultadoConsulta.next()) {
                    Funcionario fun = new Funcionario();
                    fun.setNome(resultadoConsulta.getString("nome"));
                    fun.setSetor(resultadoConsulta.getString("setor"));
                    fun.setEmail(resultadoConsulta.getString("email"));
                    fun.setTelefone(resultadoConsulta.getString("telefone"));
                    resultado.add(fun);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_paginado_filtro_pesquisa(String busca, int tamanho, int offset) {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO_FILTROS)) {

            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setString(2, busca);
            preparoConsultaSQL.setString(3, busca);
            preparoConsultaSQL.setString(4, busca);
            preparoConsultaSQL.setInt(5, tamanho);
            preparoConsultaSQL.setInt(6, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

                while (resultadoConsulta.next()) {
                    Funcionario fun = new Funcionario();
                    fun.setNome(resultadoConsulta.getString("nome"));
                    fun.setSetor(resultadoConsulta.getString("setor"));
                    fun.setEmail(resultadoConsulta.getString("email"));
                    fun.setTelefone(resultadoConsulta.getString("telefone"));
                    resultado.add(fun);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    // metodo select :D
    public List<Funcionario> select_all() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Funcionario fun = new Funcionario();
                fun.setId(resultadoConsulta.getInt("id"));
                fun.setCpf(resultadoConsulta.getString("cpf"));
                fun.setEmail(resultadoConsulta.getString("email"));
                fun.setIdEmpresa(resultadoConsulta.getInt("id_empresa"));
                fun.setNome(resultadoConsulta.getString("nome"));
                fun.setSetor(resultadoConsulta.getString("setor"));
                resultado.add(fun);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_nome() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_NOME);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Funcionario fun = new Funcionario();
                fun.setNome(resultadoConsulta.getString("nome"));
                resultado.add(fun);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_setor() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_SETOR);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Funcionario fun = new Funcionario();
                fun.setSetor(resultadoConsulta.getString("setor"));
                resultado.add(fun);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_email() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_EMAIL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Funcionario fun = new Funcionario();
                fun.setEmail(resultadoConsulta.getString("email"));
                resultado.add(fun);
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
            preparoConsultaSQL.executeUpdate();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(Funcionario fun) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {
            preparoConsultaSQL.setString(1, fun.getNome());
            preparoConsultaSQL.setString(2, fun.getCpf());
            preparoConsultaSQL.setString(3, fun.getEmail());
            preparoConsultaSQL.setString(4, fun.getSetor());
            preparoConsultaSQL.setString(5, fun.getTelefone());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo update :P
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

    public String update_nome(String nomeNew, String nomeOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_NOME)) {
            preparoConsultaSQL.setString(1, nomeNew);
            preparoConsultaSQL.setString(2, nomeOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_setor(String setorNew, String setorOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_SETOR)) {
            preparoConsultaSQL.setString(1, setorNew);
            preparoConsultaSQL.setString(2, setorOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}