package primeirobd.service;

import primeirobd.model.RegistroAgua;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroAguaDAO implements primeirobd.repository.RegistroAguaDAO {
    public static final String SELECT_ALL =
            "SELECT * FROM registro_agua";
    public static final String SELECT_ID_LOTE =
            "SELECT id_lote FROM registro_agua";
    public static final String SELECT_DATA =
            "SELECT dt_registro FROM registro_agua";
    public static final String SELECT_HIDRO_INIC =
            "SELECT hidrometro_inicio FROM registro_agua";
    public static final String SELECT_HIDRO_FIM =
            "SELECT hidrometro_fim FROM registro_agua";
    public static final String DELETE_BY_ID =
            "DELETE FROM registro_agua WHERE id = ?";
    public static final String INSERT =
            "INSERT INTO registro_agua (id,dt_registro,hidrometro_inicio,hidrometro_final,id_lote) VALUES (?,?,?,?,?)";
    public static final String UPDATE_ID =
            "UPDATE registro_agua SET id = ? WHERE id = ?";
    public static final String UPDATE_ID_LOTE =
            "UPDATE registro_agua SET id_lote = ? WHERE id_lote = ?";
    public static final String UPDATE_DATA =
            "UPDATE registro_agua SET dt_registro = ? WHERE dt_registro = ?";
    public static final String UPDATE_HIDRO_INIC =
            "UPDATE registro_agua SET hidrometro_inicio = ? WHERE hidrometro_inicio = ?";
    public static final String UPDATE_HIDRO_FIM =
            "UPDATE registro_agua SET hidrometro_final = ? WHERE hidrometro_final = ?";



    // metodo select :D
    public List<RegistroAgua> select_all() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
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

    public List<RegistroAgua> select_lote() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ID_LOTE);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                RegistroAgua rea = new RegistroAgua();
                rea.setIdLote(resultadoConsulta.getInt("id_lote"));
                resultado.add(rea);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroAgua> select_data() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_DATA);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                RegistroAgua rea = new RegistroAgua();
                rea.setDtRegistro(resultadoConsulta.getDate("dt_registro"));
                resultado.add(rea);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroAgua> select_hidroInic() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_HIDRO_INIC);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                RegistroAgua rea = new RegistroAgua();
                rea.setHidrometroInicio(resultadoConsulta.getInt("hidrometro_inicio"));
                resultado.add(rea);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroAgua> select_hidroFim() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_HIDRO_FIM);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                RegistroAgua rea = new RegistroAgua();
                rea.setHidrometroFinal(resultadoConsulta.getInt("hidrometro_final"));
                resultado.add(rea);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(RegistroAgua rea) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {

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

    public String update_hidroInic(double hidroNew,double hidroOld ){
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_HIDRO_INIC)) {
            preparoConsultaSQL.setDouble(1,hidroNew);
            preparoConsultaSQL.setDouble(2,hidroOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_hidroFim(double hidroNew,double hidroOld ){
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_HIDRO_FIM)) {
            preparoConsultaSQL.setDouble(1,hidroNew);
            preparoConsultaSQL.setDouble(2,hidroOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}