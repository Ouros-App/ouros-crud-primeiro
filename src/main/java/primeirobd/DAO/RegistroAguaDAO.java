package primeirobd.DAO;

import primeirobd.model.RegistroAgua;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroAguaDAO {

    private final ConexaoBancoPrimeiro conexaoBancoPrimeiro = new ConexaoBancoPrimeiro();

    // metodo select :D
    public List<RegistroAgua> select(String comando) {
        List<RegistroAgua> resultado = new ArrayList<>();
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                RegistroAgua rea = new RegistroAgua();
                rea.setId(resultadoConsulta.getInt("id"));
                rea.setDtRegistro(resultadoConsulta.getDate("dt_registro"));
                rea.setHidrometroInicio(resultadoConsulta.getInt("hidrometro_inicio"));
                rea.setHidrometroFinal(resultadoConsulta.getInt("hidrometro_final"));
                rea.setIdLote(resultadoConsulta.getInt("id_lote"));
                resultado.add(rea);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(String comando, RegistroAgua rea) {
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, rea.getId());
            preparoConsultaSQL.setDate(2, rea.getDtRegistro());
            preparoConsultaSQL.setInt(3, rea.getHidrometroInicio());
            preparoConsultaSQL.setInt(4, rea.getHidrometroFinal());
            preparoConsultaSQL.setInt(5, rea.getIdLote());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
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