package primeirobd.DAO;

import primeirobd.model.ProprietarioGranja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioGranjaDAO {

    private final ConexaoBancoPrimeiro conexaoBancoPrimeiro = new ConexaoBancoPrimeiro();

    // metodo select :D
    public List<ProprietarioGranja> select(String comando) {
        List<ProprietarioGranja> informacoes = new ArrayList<>();
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                ProprietarioGranja prg = new ProprietarioGranja();
                prg.setId(resultadoConsulta.getInt("id"));
                prg.setCpf(resultadoConsulta.getString("cpf"));
                prg.setEmail(resultadoConsulta.getString("email"));
                prg.setIdGranja(resultadoConsulta.getInt("id_granja"));
                prg.setNome(resultadoConsulta.getString("nome"));
                prg.setSenha(resultadoConsulta.getString("senha"));
                informacoes.add(prg);
            }
            return informacoes;

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
    public String insert(String comando, ProprietarioGranja prg) {
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, prg.getId());
            preparoConsultaSQL.setString(2, prg.getNome());
            preparoConsultaSQL.setString(3, prg.getCpf());
            preparoConsultaSQL.setString(4, prg.getSenha());
            preparoConsultaSQL.setString(5, prg.getEmail());
            preparoConsultaSQL.setInt(6, prg.getIdGranja());
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