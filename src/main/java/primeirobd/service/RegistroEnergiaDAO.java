package primeirobd.service;

import primeirobd.model.Lote;
import primeirobd.model.RegistroEnergia;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroEnergiaDAO implements primeirobd.repository.RegistroEnergiaDAO {
    public static final String SELECT_ALL = "SELECT * FROM registro_energia";
    public static final String SELECT_ID_LOTE = "SELECT id_lote FROM registro_energia";
    public static final String SELECT_DATA = "SELECT dt_registro FROM registro_energia";
    public static final String SELECT_CONSUMO = "SELECT consumo FROM registro_energia";
    public static final String DELETE_BY_ID = "DELETE FROM registro_energia WHERE id = ?";
    public static final String INSERT = "INSERT INTO registro_energia (dt_registro,consumo,id_lote) VALUES (?,?,?)";
    public static final String UPDATE_ID = "UPDATE registro_energia SET id = ? WHERE id = ?";
    public static final String UPDATE_ID_LOTE = "UPDATE registro_energia SET id_lote = ? WHERE id_lote = ?";
    public static final String UPDATE_DATA = "UPDATE registro_energia SET dt_registro = ? WHERE dt_registro = ?";
    public static final String UPDATE_CONSUMO = "UPDATE registro_energia SET consumo = ? WHERE consumo = ?";
    public static final String SELECT_COUNT =
            "SELECT COUNT(*) FROM registro_energia re " +
                    "JOIN lote l ON l.id = re.id_lote " +
                    "JOIN granja g ON g.id = l.id_granja " +
                    "WHERE g.nome ILIKE ?";

    public static final String SELECT_PAGINADO =
            "SELECT re.id, re.dt_registro, re.consumo, re.id_lote, " +
                    "       g.nome AS nome_granja, " +
                    "       CASE " +
                    "           WHEN ref.media IS NULL THEN NULL " +
                    "           WHEN re.consumo < ref.media THEN 'Abaixo' " +
                    "           WHEN re.consumo <= ref.media + 1 THEN 'Normal' " +
                    "           ELSE 'Acima' " +
                    "       END AS status " +
                    "FROM registro_energia re " +
                    "JOIN lote l ON l.id = re.id_lote " +
                    "JOIN granja g ON g.id = l.id_granja " +
                    "LEFT JOIN LATERAL ( " +
                    "   SELECT AVG(ult.media_lote) AS media " +
                    "   FROM ( " +
                    "       SELECT AVG(r2.consumo) AS media_lote " +
                    "       FROM lote l2 " +
                    "       LEFT JOIN registro_energia r2 ON r2.id_lote = l2.id " +
                    "       WHERE l2.id_granja = l.id_granja AND l2.dt_chegada < l.dt_chegada " +
                    "       GROUP BY l2.id, l2.dt_chegada " +
                    "       ORDER BY l2.dt_chegada DESC, l2.id DESC " +
                    "       LIMIT 3 " +
                    "   ) ult " +
                    ") ref ON TRUE " +
                    "WHERE g.nome ILIKE ? " +
                    "ORDER BY %s, re.id DESC " +
                    "LIMIT ? OFFSET ?";

    public static final String SELECT_BY_ID =
            "SELECT id, dt_registro, consumo, id_lote FROM registro_energia WHERE id = ?";

    public static final String UPDATE_ALL =
            "UPDATE registro_energia SET dt_registro = ?, consumo = ?, id_lote = ? WHERE id = ?";

    public int contar(String busca) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = conexao.prepareStatement(SELECT_COUNT)) {
            ps.setString(1, busca);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar. \n" + e.getMessage());
        }
    }

    public List<RegistroEnergia> select_paginado(String busca, int tamanho, int offset, String ordenarPor) {
        // Nunca inserir diretamente um parâmetro do usuário no ORDER BY.
        String colunaOrdenacao;
        if ("lote".equals(ordenarPor)) {
            colunaOrdenacao = "re.id_lote DESC";
        } else {
            colunaOrdenacao = "re.dt_registro DESC";
        }
        String query = String.format(SELECT_PAGINADO, colunaOrdenacao);

        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(query)) {
            ps.setString(1, busca);
            ps.setInt(2, tamanho);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegistroEnergia ree = new RegistroEnergia();
                    ree.setId(rs.getInt("id"));
                    ree.setDtRegistro(rs.getDate("dt_registro"));
                    ree.setConsumo(rs.getBigDecimal("consumo"));
                    ree.setIdLote(rs.getInt("id_lote"));
                    ree.setNomeGranja(rs.getString("nome_granja"));
                    ree.setStatus(rs.getString("status"));
                    resultado.add(ree);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    // Metodo select por id (usado nos formulários de editar e excluir).
    public RegistroEnergia select_by_id(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                RegistroEnergia ree = new RegistroEnergia();
                ree.setId(rs.getInt("id"));
                ree.setDtRegistro(rs.getDate("dt_registro"));
                ree.setConsumo(rs.getBigDecimal("consumo"));
                ree.setIdLote(rs.getInt("id_lote"));
                return ree;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar registro de energia.\n" + e.getMessage(), e);
        }
    }

    // metodo update completo (todos os campos do registro são editáveis)
    public String update_registro(RegistroEnergia ree) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(UPDATE_ALL)) {
            ps.setDate(1, ree.getDtRegistro());
            ps.setBigDecimal(2, ree.getConsumo());
            ps.setInt(3, ree.getIdLote());
            ps.setInt(4, ree.getId());
            ps.executeUpdate();
            return "Registro atualizado com sucesso";
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar registro de energia.\n" + e.getMessage(), e);
        }
    }

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
                ree.setConsumo(resultadoConsulta.getBigDecimal("consumo"));
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
                ree.setConsumo(resultadoConsulta.getBigDecimal("consumo"));
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
            preparoConsultaSQL.setDate(1, ree.getDtRegistro());
            preparoConsultaSQL.setBigDecimal(2, ree.getConsumo());
            preparoConsultaSQL.setInt(3, ree.getIdLote());
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