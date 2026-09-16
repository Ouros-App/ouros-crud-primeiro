package primeirobd.DAO;

import primeirobd.model.Lote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO {

    // metodo select :D
    public List<Lote> select(String comando) {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setId(resultadoConsulta.getInt("id"));
                lot.setDtChegada(resultadoConsulta.getDate("dt_chegada"));
                lot.setDtEntrega(resultadoConsulta.getDate("dt_entrega"));
                lot.setDtNascimento(resultadoConsulta.getDate("dt_nascimento"));
                lot.setGalinhasEntregadas(resultadoConsulta.getInt("galinhas_entregadas"));
                lot.setGalinhasRecebidas(resultadoConsulta.getInt("galinhas_recebidas"));
                lot.setGanho(resultadoConsulta.getFloat("ganho"));
                lot.setIdGranja(resultadoConsulta.getInt("id_granja"));
                informacoes.add(lot);
            }
            return informacoes;
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
    public String insert(String comando, Lote lot) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, lot.getId());
            preparoConsultaSQL.setInt(2, lot.getGalinhasEntregadas());
            preparoConsultaSQL.setInt(3, lot.getGalinhasRecebidas());
            preparoConsultaSQL.setDate(4, lot.getDtNascimento());
            preparoConsultaSQL.setDate(5, lot.getDtChegada());
            preparoConsultaSQL.setDate(6, lot.getDtEntrega());
            preparoConsultaSQL.setFloat(7, lot.getGanho());
            preparoConsultaSQL.setInt(8, lot.getIdGranja());
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