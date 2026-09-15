package primeirobd.DAO;

import primeirobd.model.EnderecoGranja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoGranjaDAO {

    private final ConexaoBancoPrimeiro conexaoBancoPrimeiro = new ConexaoBancoPrimeiro();

    // metodo select :D
    public List<EnderecoGranja> select(String comando) {
        List<EnderecoGranja> resultado = new ArrayList<>();
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                EnderecoGranja endG = new EnderecoGranja();
                endG.setId(resultadoConsulta.getInt("id"));
                endG.setCep(resultadoConsulta.getString("cep"));
                endG.setEstado(resultadoConsulta.getString("estado"));
                endG.setIdGranja(resultadoConsulta.getInt("id_granja"));
                endG.setLogradouro(resultadoConsulta.getString("logradouro"));
                endG.setMunicipio(resultadoConsulta.getString("municipio"));
                endG.setNumero(resultadoConsulta.getInt("numero"));
                resultado.add(endG);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // metodo delete :)
    public String delete(String comando) {
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(String comando, EnderecoGranja endG) {
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, endG.getId());
            preparoConsultaSQL.setString(2, endG.getCep());
            preparoConsultaSQL.setInt(3, endG.getNumero());
            preparoConsultaSQL.setString(4, endG.getMunicipio());
            preparoConsultaSQL.setString(5, endG.getEstado());
            preparoConsultaSQL.setString(6, endG.getLogradouro());
            preparoConsultaSQL.setInt(7, endG.getIdGranja());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo update :P
    public String update(String comando) {
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}