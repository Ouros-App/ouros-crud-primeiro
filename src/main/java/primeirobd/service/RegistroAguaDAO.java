package primeirobd.service;

import primeirobd.model.RegistroAgua;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroAguaDAO implements primeirobd.repository.RegistroAguaDAO {
    public static final String SELECT_ALL = "SELECT * FROM registro_agua";
    public static final String SELECT_ID_LOTE = "SELECT id_lote FROM registro_agua";
    public static final String SELECT_DATA = "SELECT dt_registro FROM registro_agua";
    public static final String SELECT_HIDRO_INIC = "SELECT hidrometro_inicio FROM registro_agua";
    public static final String SELECT_HIDRO_FIM = "SELECT hidrometro_fim FROM registro_agua";
    public static final String DELETE_BY_ID = "DELETE FROM registro_agua WHERE id = ?";
    public static final String INSERT = "INSERT INTO registro_agua (dt_registro,hidrometro_inicio,hidrometro_final,id_lote) VALUES (?,?,?,?)";
    public static final String UPDATE_ID = "UPDATE registro_agua SET id = ? WHERE id = ?";
    public static final String UPDATE_ID_LOTE = "UPDATE registro_agua SET id_lote = ? WHERE id_lote = ?";
    public static final String UPDATE_DATA = "UPDATE registro_agua SET dt_registro = ? WHERE dt_registro = ?";
    public static final String UPDATE_HIDRO_INIC = "UPDATE registro_agua SET hidrometro_inicio = ? WHERE hidrometro_inicio = ?";
    public static final String UPDATE_HIDRO_FIM = "UPDATE registro_agua SET hidrometro_final = ? WHERE hidrometro_final = ?";
    public static final String SELECT_COUNT =
            "SELECT COUNT(*) FROM registro_agua ra " +
                    "JOIN lote l ON l.id = ra.id_lote " +
                    "JOIN granja g ON g.id = l.id_granja " +
                    "WHERE g.nome ILIKE ?";

    public static final String SELECT_PAGINADO =
            "SELECT ra.id, ra.dt_registro, ra.hidrometro_inicio, ra.hidrometro_final, ra.id_lote, " +
                    "       g.nome AS nome_granja, " +
                    "       CASE " +
                    "           WHEN ref.media IS NULL THEN NULL " +
                    "           WHEN (ra.hidrometro_final - ra.hidrometro_inicio) < ref.media THEN 'Abaixo' " +
                    "           WHEN (ra.hidrometro_final - ra.hidrometro_inicio) <= ref.media + 1 THEN 'Normal' " +
                    "           ELSE 'Acima' " +
                    "       END AS status " +
                    "FROM registro_agua ra " +
                    "JOIN lote l ON l.id = ra.id_lote " +
                    "JOIN granja g ON g.id = l.id_granja " +
                    "LEFT JOIN LATERAL ( " +
                    "   SELECT AVG(ult.media_lote) AS media " +
                    "   FROM ( " +
                    "       SELECT AVG(r2.hidrometro_final - r2.hidrometro_inicio) AS media_lote " +
                    "       FROM lote l2 " +
                    "       LEFT JOIN registro_agua r2 ON r2.id_lote = l2.id " +
                    "       WHERE l2.id_granja = l.id_granja AND l2.dt_chegada < l.dt_chegada " +
                    "       GROUP BY l2.id, l2.dt_chegada " +
                    "       ORDER BY l2.dt_chegada DESC, l2.id DESC " +
                    "       LIMIT 3 " +
                    "   ) ult " +
                    ") ref ON TRUE " +
                    "WHERE g.nome ILIKE ? " +
                    "ORDER BY %s, ra.id DESC " +
                    "LIMIT ? OFFSET ?";

    public static final String SELECT_BY_ID =
            "SELECT id, dt_registro, hidrometro_inicio, hidrometro_final, id_lote " +
                    "FROM registro_agua WHERE id = ?";

    public static final String UPDATE_ALL =
            "UPDATE registro_agua SET dt_registro = ?, hidrometro_inicio = ?, " +
                    "hidrometro_final = ?, id_lote = ? WHERE id = ?";

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

    public List<RegistroAgua> select_paginado(String busca, int tamanho, int offset, String ordenarPor) {
        String colunaOrdenacao;
        if ("lote".equals(ordenarPor)) {
            colunaOrdenacao = "ra.id_lote DESC";
        } else {
            colunaOrdenacao = "ra.dt_registro DESC";
        }
        String query = String.format(SELECT_PAGINADO, colunaOrdenacao);

        List<RegistroAgua> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(query)) {
            ps.setString(1, busca);
            ps.setInt(2, tamanho);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegistroAgua rea = new RegistroAgua();
                    rea.setId(rs.getInt("id"));
                    rea.setDtRegistro(rs.getDate("dt_registro"));
                    rea.setHidrometroInicio(rs.getBigDecimal("hidrometro_inicio"));
                    rea.setHidrometroFinal(rs.getBigDecimal("hidrometro_final"));
                    rea.setIdLote(rs.getInt("id_lote"));
                    rea.setNomeGranja(rs.getString("nome_granja"));
                    rea.setStatus(rs.getString("status"));
                    resultado.add(rea);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public RegistroAgua select_by_id(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                RegistroAgua rea = new RegistroAgua();
                rea.setId(rs.getInt("id"));
                rea.setDtRegistro(rs.getDate("dt_registro"));
                rea.setHidrometroInicio(rs.getBigDecimal("hidrometro_inicio"));
                rea.setHidrometroFinal(rs.getBigDecimal("hidrometro_final"));
                rea.setIdLote(rs.getInt("id_lote"));
                return rea;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar registro de água.\n" + e.getMessage(), e);
        }
    }

    public String update_registro(RegistroAgua rea) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(UPDATE_ALL)) {
            ps.setDate(1, rea.getDtRegistro());
            ps.setBigDecimal(2, rea.getHidrometroInicio());
            ps.setBigDecimal(3, rea.getHidrometroFinal());
            ps.setInt(4, rea.getIdLote());
            ps.setInt(5, rea.getId());
            ps.executeUpdate();
            return "Registro atualizado com sucesso";
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar registro de água.\n" + e.getMessage(), e);
        }
    }

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
                rea.setHidrometroInicio(resultadoConsulta.getBigDecimal("hidrometro_inicio"));
                rea.setHidrometroFinal(resultadoConsulta.getBigDecimal("hidrometro_final"));
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
                rea.setHidrometroInicio(resultadoConsulta.getBigDecimal("hidrometro_inicio"));
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
                rea.setHidrometroFinal(resultadoConsulta.getBigDecimal("hidrometro_final"));
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
            preparoConsultaSQL.setDate(1, rea.getDtRegistro());
            preparoConsultaSQL.setBigDecimal(2, rea.getHidrometroInicio());
            preparoConsultaSQL.setBigDecimal(3, rea.getHidrometroFinal());
            preparoConsultaSQL.setInt(4, rea.getIdLote());
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