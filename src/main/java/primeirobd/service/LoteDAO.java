package primeirobd.service;

import primeirobd.model.Lote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoteDAO implements primeirobd.repository.LoteDAO {
    public static final String SELECT_ALL = "SELECT * FROM lote";
    public static final String SELECT_ENTREGUES = "SELECT galinhas_entregadas FROM lote";
    public static final String SELECT_RECEBIDAS = "SELECT galinhas_recebidas FROM lote";
    public static final String SELECT_CHEGADA = "SELECT dt_chegada FROM lote";
    public static final String SELECT_GANHO = "SELECT ganho FROM lote";
    //----------------------------------------------------------------------------------------
    public static final String DELETE_BY_ID = "DELETE FROM lote WHERE id = ?";
    public static final String INSERT = "INSERT INTO lote (id, galinahs_entregadas,galinhas_recebidas,dt_nascimento,dt_chegada,dt_entrega,ganho,id_granja) VALUES (?,?,?,?,?,?,?,?)";
    //----------------------------------------------------------------------------------------
    public static final String UPDATE_ID = "UPDATE lote SET id = ? WHERE id = ?";
    public static final String UPDATE_ENTREGUES = "UPDATE lote SET galinhas_entregadas = ? WHERE galinhas_entregadas = ?";
    public static final String UPDATE_RECEBIDAS = "UPDATE lote SET galinhas_recebidas = ? WHERE galinhas_recebidas = ?";
    public static final String UPDATE_CHEGADA = "UPDATE lote SET dt_chegada = ? WHERE dt_chegada = ?";
    public static final String UPDATE_GANHO = "UPDATE lote SET ganho = ? WHERE ganho = ?";

    // metodo select :D
    public List<Lote> select_all() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
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

    public List<Lote> select_ganho() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_GANHO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setGanho(resultadoConsulta.getFloat("ganho"));
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }
    public List<Lote> select_recebida() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_RECEBIDAS);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setGalinhasRecebidas(resultadoConsulta.getInt("galinhas_recebidas"));
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Lote> select_entregue() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ENTREGUES);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setGalinhasEntregadas(resultadoConsulta.getInt("galinhas_entregadas"));
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Lote> select_chegada() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_CHEGADA);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setDtChegada(resultadoConsulta.getDate("dt_chegada"));
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // metodo delete :)
    public String delete(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(DELETE_BY_ID)) {
            preparoConsultaSQL.setInt(1, id);
            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(Lote lot) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {

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

    public String update_entregues(int entreguesNew, int entreguesOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ENTREGUES)) {
            preparoConsultaSQL.setInt(1, entreguesNew);
            preparoConsultaSQL.setInt(2, entreguesOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_recebidas(int recebidasNew, int recebidasOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_RECEBIDAS)) {
            preparoConsultaSQL.setInt(1, recebidasNew);
            preparoConsultaSQL.setInt(2, recebidasOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_chegada(Date chegadaNew, Date chegadaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_CHEGADA)) {
            preparoConsultaSQL.setDate(1, chegadaNew);
            preparoConsultaSQL.setDate(2, chegadaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_ganho(double ganhoNew, double ganhoOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_GANHO)) {
            preparoConsultaSQL.setDouble(1, ganhoNew);
            preparoConsultaSQL.setDouble(2, ganhoOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}