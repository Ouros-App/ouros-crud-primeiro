package primeirobd.DAO;

import primeirobd.model.Granja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GranjaDAO {

    // metodo select :D
    public List<Granja> select(String comando) {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Granja gra = new Granja();

                gra.setId(resultadoConsulta.getInt("id"));
                gra.setAreaPropriedade(resultadoConsulta.getInt("area_propriedade"));
                gra.setCapacidadeDeAves(resultadoConsulta.getInt("capacidade_aves"));
                gra.setIdEmpresa(resultadoConsulta.getInt("id_empresa"));
                gra.setNome(resultadoConsulta.getString("nome"));
                gra.setRegiao(resultadoConsulta.getString("regiao"));

                resultado.add(gra);
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
    public String insert(String comando, Granja gra) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, gra.getId());
            preparoConsultaSQL.setString(2, gra.getNome());
            preparoConsultaSQL.setInt(3, gra.getCapacidadeDeAves());
            preparoConsultaSQL.setString(4, gra.getRegiao());
            preparoConsultaSQL.setInt(5, gra.getAreaPropriedade());
            preparoConsultaSQL.setInt(6, gra.getIdEmpresa());
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

