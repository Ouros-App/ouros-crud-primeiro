package primeirobd.service;

import primeirobd.model.Granja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GranjaDAO implements primeirobd.repository.GranjaDAO {
    public static final String SELECT_ALL = "SELECT id,area_propriedade, capacidade_aves,id_empresa,nome,regiao FROM granja";
    public static final String SELECT_NOME = "SELECT nome FROM granja";
    public static final String SELECT_REGIAO = "SELECT regiao FROM granja";
    public static final String SELECT_CAPACIDADE = "SELECT capacidade_aves FROM granja";
    public static final String DELETE_BY_ID = "DELETE FROM granja where id = ?";
    public static final String INSERT = "INSERT INTO granja (id,nome,capacidade_aves,regiao,area_propriedade,id_empresa) values (?,?,?,?,?,?)";
    public static final String UPDATE_ID = "UPDATE granja SET id = ? WHERE id = ?";
    public static final String UPDATE_NOME = "UPDATE granja SET nome = ? WHERE nome = ?";
    public static final String UPDATE_CAPACIDADE = "UPDATE granja SET capacidade_aves = ? WHERE capacidade_aves = ?";
    public static final String UPDATE_REGIAO = "UPDATE granja SET regiao = ? where capacidade_aves = ?";
    public static final String UPDATE_AREA = "UPDATE granja SET area_propriedade = ? WHERE area_propriedade = ?";
    public static final String UPDATE_IDEMPRESA = "UPDATE granja SET id_empresa = ? WHERE id_empresa = ?";



    // metodo select :D
    public List<Granja> select_all() {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
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
    public List<Granja> select_nome() {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_NOME);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Granja gra = new Granja();
                gra.setNome(resultadoConsulta.getString("nome"));
                resultado.add(gra);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Granja> select_regiao() {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_REGIAO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Granja gra = new Granja();

                gra.setRegiao(resultadoConsulta.getString("regiao"));

                resultado.add(gra);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Granja> select_capacidade() {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_CAPACIDADE);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Granja gra = new Granja();

                gra.setCapacidadeDeAves(resultadoConsulta.getInt("capacidade_aves"));

                resultado.add(gra);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
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

    // metodo insert :O
    public String insert(Granja gra){
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {
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
    public String update_id(int idNew,int idOld) {
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

    public String update_nome(String nomeNew, String nomeOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_NOME)) {
            preparoConsultaSQL.setString(1, nomeNew);
            preparoConsultaSQL.setString(2, nomeOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_capacidade(String capacidadeNew, String capacidadeOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_CAPACIDADE)) {
            preparoConsultaSQL.setString(1, capacidadeNew);
            preparoConsultaSQL.setString(2, capacidadeOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_regiao(String regiaoNew, String regiaoOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_REGIAO)) {
            preparoConsultaSQL.setString(1, regiaoNew);
            preparoConsultaSQL.setString(2, regiaoOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_area(int areaNew, int areaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_AREA)) {
            preparoConsultaSQL.setInt(1, areaNew);
            preparoConsultaSQL.setInt(2, areaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_idEmpresa(int idEmpresaNew, int idEmpresaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_IDEMPRESA)) {
            preparoConsultaSQL.setInt(1, idEmpresaNew);
            preparoConsultaSQL.setInt(2, idEmpresaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}

