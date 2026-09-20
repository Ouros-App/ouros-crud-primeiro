package primeirobd.service;

import primeirobd.model.RegistroEnergia;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroEnergiaDAO implements primeirobd.repository.RegistroEnergiaDAO {
    public static final String SELECT_ALL =
            "SELECT * FROM registro_energia";
    public static final String SELECT_ID_LOTE =
            "SELECT id_lote FROM registro_energia";
    public static final String SELECT_DATA =
            "SELECT dt_registro FROM registro_energia";
    public static final String SELECT_CONSUMO =
            "SELECT consumo FROM registro_energia";
    public static final String DELETE_BY_ID =
            "DELETE FROM registro_energia WHERE id = ?";
    public static final String INSERT =
            "INSERT INTO registro_energia (id,dt_registro,consumo,id_lote) VALUES (?,?,?,?)";
    public static final String UPDATE_ID =
            "UPDATE registro_energia SET id = ? WHERE id = ?";
    public static final String UPDATE_ID_LOTE =
            "UPDATE registro_energia SET id_lote = ? WHERE id_lote = ?";
    public static final String UPDATE_DATA =
            "UPDATE registro_energia SET dt_registro = ? WHERE dt_registro = ?";
    public static final String UPDATE_CONSUMO =
            "UPDATE registro_energia SET consumo = ? WHERE consumo = ?";
    // metodo select :D
    public List<RegistroEnergia> select_all() {
        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                RegistroEnergia ree = new RegistroEnergia();
                ree.setId(resultadoConsulta.getInt("id"));
                ree.setDtRegistro(resultadoConsulta.getDate("dt_registro"));
                ree.setConsumo(resultadoConsulta.getInt("consumo"));
                ree.setIdLote(resultadoConsulta.getInt("id_lote"));
                resultado.add(ree);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroEnergia> select_lote() {
        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ID_LOTE);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                RegistroEnergia ree = new RegistroEnergia();
                ree.setIdLote(resultadoConsulta.getInt("id_lote"));
                resultado.add(ree);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroEnergia> select_data() {
        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_DATA);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                RegistroEnergia ree = new RegistroEnergia();
                ree.setDtRegistro(resultadoConsulta.getDate("dt_registro"));
                resultado.add(ree);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroEnergia> select_consumo() {
        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_CONSUMO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                RegistroEnergia ree = new RegistroEnergia();
                ree.setConsumo(resultadoConsulta.getInt("consumo"));
                resultado.add(ree);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }


    // metodo insert :O
    public String insert(RegistroEnergia ree) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {

            preparoConsultaSQL.setInt(1, ree.getId());
            preparoConsultaSQL.setDate(2, ree.getDtRegistro());
            preparoConsultaSQL.setInt(3, ree.getConsumo());
            preparoConsultaSQL.setInt(4, ree.getIdLote());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
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

    // metodo update :P
    public String update_id(int idNew,int idOld ){
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ID)) {
            preparoConsultaSQL.setInt(1,idNew);
            preparoConsultaSQL.setInt(2,idOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
    public String update_idLote(String lotNew,String lotOld ){
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ID_LOTE)) {
            preparoConsultaSQL.setString(1,lotNew);
            preparoConsultaSQL.setString(2,lotOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }


    public String update_data(Date dataNew, Date dataOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_DATA)){
            preparoConsultaSQL.setDate(1,dataNew);
            preparoConsultaSQL.setDate(2,dataOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_consumo(double consNew,double consOld ){
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_CONSUMO)) {
            preparoConsultaSQL.setDouble(1,consNew);
            preparoConsultaSQL.setDouble(2,consOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }



}