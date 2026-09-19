package primeirobd.service;

import primeirobd.model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    // metodo select :D
    public List<Funcionario> select(String comando) {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
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

    // metodo delete :)
    public String delete(String comando) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(String comando, Funcionario fun) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, fun.getId());
            preparoConsultaSQL.setString(2, fun.getNome());
            preparoConsultaSQL.setString(3, fun.getCpf());
            preparoConsultaSQL.setString(4, fun.getEmail());
            preparoConsultaSQL.setString(5, fun.getSetor());
            preparoConsultaSQL.setInt(6, fun.getIdEmpresa());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo update :P
    public String update(String comando) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}