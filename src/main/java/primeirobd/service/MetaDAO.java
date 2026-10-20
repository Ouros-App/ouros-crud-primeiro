package primeirobd.service;

import primeirobd.model.Meta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetaDAO implements primeirobd.repository.MetaDAO {
    public static final String SELECT_ALL = "SELECT * FROM meta";
    public static final String SELECT_TITULO = "SELECT titulo FROM meta";
    public static final String SELECT_ID_GRANJA= "SELECT id_granja FROM meta";
    public static final String SELECT_VALOR_ALVO = "SELECT valor_alvo FROM meta";

    public static final String DELETE_BY_ID = "DELETE FROM meta WHERE id = ?";
    public static final String INSERT =
            "INSERT INTO meta (id,titulo,descricao,tipo_meta,status,valor_alvo,regiao,estado,is_individual,id_granja) VALUES(?,?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_ID = "UPDATE meta SET id = ? WHERE id = ?";
    public static final String UPDATE_TITULO = "UPDATE meta SET titulo = ? WHERE titulo = ?";
    public static final String UPDATE_ID_GRANJA = "UPDATE meta SET id_granja = ? WHERE id_granja = ?";
    public static final String UPDATE_VALOR_ALVO = "UPDATE meta SET valor_alvo = ? WHERE valor_alvo = ?";

    // metodo select :D
    public List<Meta> select_all() {
        List<Meta> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Meta met = new Meta();
                met.setId(resultadoConsulta.getInt("id"));
                met.setDescricao(resultadoConsulta.getString("descricao"));
                met.setEstado(resultadoConsulta.getString("estado"));
                met.setIdGranja(resultadoConsulta.getInt("id_granja"));
                met.setIndividual(resultadoConsulta.getBoolean("is_individual"));
                met.setRegiao(resultadoConsulta.getString("regiao"));
                met.setStatus(resultadoConsulta.getString("status"));
                met.setTipoMeta(resultadoConsulta.getString("tipo_meta"));
                met.setTitulo(resultadoConsulta.getString("titulo"));
                met.setValorAlvo(resultadoConsulta.getFloat("valor_alvo"));
                informacoes.add(met);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Meta> select_titulo() {
        List<Meta> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_TITULO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Meta met = new Meta();
                met.setTitulo(resultadoConsulta.getString("titulo"));
                informacoes.add(met);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Meta> select_idGranja() {
        List<Meta> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ID_GRANJA);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Meta met = new Meta();
                met.setIdGranja(resultadoConsulta.getInt("id_granja"));
                informacoes.add(met);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Meta> select_valorAlvo() {
        List<Meta> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_VALOR_ALVO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Meta met = new Meta();
                met.setValorAlvo(resultadoConsulta.getFloat("valor_alvo"));
                informacoes.add(met);
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
            preparoConsultaSQL.setInt(1,id);
            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(Meta met) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {

            preparoConsultaSQL.setInt(1, met.getId());
            preparoConsultaSQL.setString(2, met.getTitulo());
            preparoConsultaSQL.setString(3, met.getDescricao());
            preparoConsultaSQL.setString(4, met.getTipoMeta());
            preparoConsultaSQL.setString(5, met.getStatus());
            preparoConsultaSQL.setFloat(6, met.getValorAlvo());
            preparoConsultaSQL.setString(7, met.getRegiao());
            preparoConsultaSQL.setString(8, met.getEstado());
            preparoConsultaSQL.setBoolean(9, met.isIndividual());
            preparoConsultaSQL.setInt(10, met.getIdGranja());
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
            preparoConsultaSQL.setInt(1,idNew);
            preparoConsultaSQL.setInt(2,idOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_titulo(String tituloNew, String tituloOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_TITULO)) {
            preparoConsultaSQL.setString(1,tituloNew);
            preparoConsultaSQL.setString(2,tituloOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_idGranja(int idGranjaNew, int idGranjaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ID_GRANJA)) {
            preparoConsultaSQL.setInt(1,idGranjaNew);
            preparoConsultaSQL.setInt(2,idGranjaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_valorAlvo(double valorAlvoNew, double valorAlvoOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_VALOR_ALVO)) {
            preparoConsultaSQL.setDouble(1,valorAlvoNew);
            preparoConsultaSQL.setDouble(2,valorAlvoOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}