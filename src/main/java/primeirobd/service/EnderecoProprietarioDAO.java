package primeirobd.service;

import primeirobd.model.EnderecoProprietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoProprietarioDAO {

    // metodo select :D
    public List<EnderecoProprietario> select(String comando) {
        List<EnderecoProprietario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                EnderecoProprietario endP = new EnderecoProprietario();
                endP.setId(resultadoConsulta.getInt("id"));
                endP.setCep(resultadoConsulta.getString("cep"));
                endP.setEstado(resultadoConsulta.getString("estado"));
                endP.setIdProprietario(resultadoConsulta.getInt("id_proprietario"));
                endP.setLogradouro(resultadoConsulta.getString("logradouro"));
                endP.setMunicipio(resultadoConsulta.getString("municipio"));
                endP.setNumero(resultadoConsulta.getInt("numero"));
                resultado.add(endP);
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
    public String insert(String comando, EnderecoProprietario endP) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, endP.getId());
            preparoConsultaSQL.setString(2, endP.getCep());
            preparoConsultaSQL.setInt(3, endP.getNumero());
            preparoConsultaSQL.setString(4, endP.getMunicipio());
            preparoConsultaSQL.setString(5, endP.getEstado());
            preparoConsultaSQL.setString(6, endP.getLogradouro());
            preparoConsultaSQL.setInt(7, endP.getIdProprietario());

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