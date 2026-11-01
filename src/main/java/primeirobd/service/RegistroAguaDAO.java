package primeirobd.service;

import primeirobd.model.RegistroAgua;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Persiste leituras de água e calcula o status comparativo na listagem.
public class RegistroAguaDAO implements primeirobd.repository.RegistroAguaDAO {
    public static final String comandoSelectAll = "SELECT * FROM registro_agua";
    public static final String comandoSelectIdLote = "SELECT id_lote FROM registro_agua";
    public static final String comandoSelectDataRegistro = "SELECT dt_registro FROM registro_agua";
    public static final String comandoSelectHidrometroInicio = "SELECT hidrometro_inicio FROM registro_agua";
    public static final String comandoSelectHidrometroFim = "SELECT hidrometro_fim FROM registro_agua";
    public static final String comandoDeleteById = "DELETE FROM registro_agua WHERE id = ?";
    public static final String comandoInsert = "INSERT INTO registro_agua (dt_registro,hidrometro_inicio,hidrometro_final,id_lote) VALUES (?,?,?,?)";
    public static final String comandoUpdateId = "UPDATE registro_agua SET id = ? WHERE id = ?";
    public static final String comandoUpdateIdLote = "UPDATE registro_agua SET id_lote = ? WHERE id_lote = ?";
    public static final String comandoUpdateData = "UPDATE registro_agua SET dt_registro = ? WHERE dt_registro = ?";
    public static final String comandoUpdateHidrometroInicio = "UPDATE registro_agua SET hidrometro_inicio = ? WHERE hidrometro_inicio = ?";
    public static final String comandoUpdateHidrometroFim = "UPDATE registro_agua SET hidrometro_final = ? WHERE hidrometro_final = ?";
    public static final String comandoSelectCount =
            "SELECT COUNT(*) FROM registro_agua ra " +
                    "JOIN lote l ON l.id = ra.id_lote " +
                    "JOIN granja g ON g.id = l.id_granja " +
                    "WHERE g.nome ILIKE ?";

    public static final String comandoSelectPaginado =
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

    public static final String comandoSelectById =
            "SELECT id, dt_registro, hidrometro_inicio, hidrometro_final, id_lote " +
                    "FROM registro_agua WHERE id = ?";

    public static final String comandoUpdateAll =
            "UPDATE registro_agua SET dt_registro = ?, hidrometro_inicio = ?, " +
                    "hidrometro_final = ?, id_lote = ? WHERE id = ?";

    public int contarBusca(String busca) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectCount)) {
            ps.setString(1, busca);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar. \n" + e.getMessage());
        }
    }

// A ordenação é escolhida por uma lista fixa antes de montar o SQL.
    public List<RegistroAgua> selectPaginadoComBusca(String busca, int tamanho, int offset, String ordenarPor) {
        String colunaOrdenacao;
        if ("lote".equals(ordenarPor)) {
            colunaOrdenacao = "ra.id_lote DESC";
        } else {
            colunaOrdenacao = "ra.dt_registro DESC";
        }
        String query = String.format(comandoSelectPaginado, colunaOrdenacao);

        List<RegistroAgua> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, busca);
            ps.setInt(2, tamanho);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegistroAgua registroAgua = new RegistroAgua();
                    registroAgua.setId(rs.getInt("id"));
                    registroAgua.setDtRegistro(rs.getDate("dt_registro"));
                    registroAgua.setHidrometroInicio(rs.getBigDecimal("hidrometro_inicio"));
                    registroAgua.setHidrometroFinal(rs.getBigDecimal("hidrometro_final"));
                    registroAgua.setIdLote(rs.getInt("id_lote"));
                    registroAgua.setNomeGranja(rs.getString("nome_granja"));
                    registroAgua.setStatus(rs.getString("status"));
                    resultado.add(registroAgua);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public RegistroAgua selectById(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectById)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                RegistroAgua registroAgua = new RegistroAgua();
                registroAgua.setId(rs.getInt("id"));
                registroAgua.setDtRegistro(rs.getDate("dt_registro"));
                registroAgua.setHidrometroInicio(rs.getBigDecimal("hidrometro_inicio"));
                registroAgua.setHidrometroFinal(rs.getBigDecimal("hidrometro_final"));
                registroAgua.setIdLote(rs.getInt("id_lote"));
                return registroAgua;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar registro de água.\n" + e.getMessage(), e);
        }
    }

// Atualiza a leitura completa mantendo seus valores numéricos no banco.
    public String updateRegistro(RegistroAgua registroAgua) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateAll)) {
            ps.setDate(1, registroAgua.getDtRegistro());
            ps.setBigDecimal(2, registroAgua.getHidrometroInicio());
            ps.setBigDecimal(3, registroAgua.getHidrometroFinal());
            ps.setInt(4, registroAgua.getIdLote());
            ps.setInt(5, registroAgua.getId());
            ps.executeUpdate();
            return "Registro atualizado com sucesso";
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar registro de água.\n" + e.getMessage(), e);
        }
    }

    // Lista os registros para consultas administrativas.
    public List<RegistroAgua> selectAll() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroAgua registroAgua = new RegistroAgua();
                registroAgua.setId(rs.getInt("id"));
                registroAgua.setDtRegistro(rs.getDate("dt_registro"));
                registroAgua.setHidrometroInicio(rs.getBigDecimal("hidrometro_inicio"));
                registroAgua.setHidrometroFinal(rs.getBigDecimal("hidrometro_final"));
                registroAgua.setIdLote(rs.getInt("id_lote"));
                resultado.add(registroAgua);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroAgua> selectLote() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectIdLote);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroAgua registroAgua = new RegistroAgua();
                registroAgua.setIdLote(rs.getInt("id_lote"));
                resultado.add(registroAgua);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroAgua> selectData() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectDataRegistro);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroAgua registroAgua = new RegistroAgua();
                registroAgua.setDtRegistro(rs.getDate("dt_registro"));
                resultado.add(registroAgua);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroAgua> selectHidrometroInicio() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectHidrometroInicio);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroAgua registroAgua = new RegistroAgua();
                registroAgua.setHidrometroInicio(rs.getBigDecimal("hidrometro_inicio"));
                resultado.add(registroAgua);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroAgua> selectHidrometroFim() {
        List<RegistroAgua> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectHidrometroFim);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroAgua registroAgua = new RegistroAgua();
                registroAgua.setHidrometroFinal(rs.getBigDecimal("hidrometro_final"));
                resultado.add(registroAgua);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // Insere um novo registro.
    public String insert(RegistroAgua registroAgua) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoInsert)) {
            ps.setDate(1, registroAgua.getDtRegistro());
            ps.setBigDecimal(2, registroAgua.getHidrometroInicio());
            ps.setBigDecimal(3, registroAgua.getHidrometroFinal());
            ps.setInt(4, registroAgua.getIdLote());
            ps.executeUpdate();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // Exclui o registro identificado.
    public String delete(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoDeleteById)) {
            ps.setInt(1,id);
            ps.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // Atualiza o registro identificado.
    public String updateId(int idNew,int idOld ){
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateId)) {
            ps.setInt(1,idNew);
            ps.setInt(2,idOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
    public String updateIdLote(String lotNew,String lotOld ){
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateIdLote)) {
            ps.setString(1,lotNew);
            ps.setString(2,lotOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }


    public String updateData(Date dataNew, Date dataOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateData)){
            ps.setDate(1,dataNew);
            ps.setDate(2,dataOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateHidrometroInicio(double hidrometroNew,double hidrometroOld ){
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateHidrometroInicio)) {
            ps.setDouble(1,hidrometroNew);
            ps.setDouble(2,hidrometroOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateHidrometroFim(double hidrometroNew,double hidrometroOld ){
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateHidrometroFim)) {
            ps.setDouble(1,hidrometroNew);
            ps.setDouble(2,hidrometroOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}