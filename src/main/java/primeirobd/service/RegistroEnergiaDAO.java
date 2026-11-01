package primeirobd.service;

import primeirobd.model.Lote;
import primeirobd.model.RegistroEnergia;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Persiste consumos de energia e expõe sua classificação comparativa.
public class RegistroEnergiaDAO implements primeirobd.repository.RegistroEnergiaDAO {
    public static final String comandoSelectAll = "SELECT * FROM registro_energia";
    public static final String comandoSelectIdLote = "SELECT id_lote FROM registro_energia";
    public static final String comandoSelectData = "SELECT dt_registro FROM registro_energia";
    public static final String comandoSelectConsumo = "SELECT consumo FROM registro_energia";
    public static final String comandoDeleteById = "DELETE FROM registro_energia WHERE id = ?";
    public static final String comandoInsert = "INSERT INTO registro_energia (dt_registro,consumo,id_lote) VALUES (?,?,?)";
    public static final String comandoUpdateId = "UPDATE registro_energia SET id = ? WHERE id = ?";
    public static final String comandoUpdateidLote = "UPDATE registro_energia SET id_lote = ? WHERE id_lote = ?";
    public static final String comandoUpdateData = "UPDATE registro_energia SET dt_registro = ? WHERE dt_registro = ?";
    public static final String comandoupdateConsumo = "UPDATE registro_energia SET consumo = ? WHERE consumo = ?";
    public static final String comandoSelectCount =
            "SELECT COUNT(*) FROM registro_energia re " +
                    "JOIN lote l ON l.id = re.id_lote " +
                    "JOIN granja g ON g.id = l.id_granja " +
                    "WHERE g.nome ILIKE ?";

    public static final String comandoSelectPaginado =
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

    public static final String comandoSelectById =
            "SELECT id, dt_registro, consumo, id_lote FROM registro_energia WHERE id = ?";

    public static final String comandoUpdateAll =
            "UPDATE registro_energia SET dt_registro = ?, consumo = ?, id_lote = ? WHERE id = ?";

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

// O parâmetro de ordenação nunca é incorporado diretamente ao SQL.
    public List<RegistroEnergia> selectPaginadoComBusca(String busca, int tamanho, int offset, String ordenarPor) {
        // Nunca inserir diretamente um parâmetro do usuário no ORDER BY.
        String colunaOrdenacao;
        if ("lote".equals(ordenarPor)) {
            colunaOrdenacao = "re.id_lote DESC";
        } else {
            colunaOrdenacao = "re.dt_registro DESC";
        }
        String query = String.format(comandoSelectPaginado, colunaOrdenacao);

        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
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

    // Busca um registro para edição ou exclusão.
    public RegistroEnergia selectById(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectById)) {
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
// Atualiza data, consumo e lote do registro.
    public String updateRegistro(RegistroEnergia ree) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateAll)) {
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

    // Lista os registros para consultas administrativas.
    public List<RegistroEnergia> selectAll() {
        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroEnergia ree = new RegistroEnergia();
                ree.setId(rs.getInt("id"));
                ree.setDtRegistro(rs.getDate("dt_registro"));
                ree.setConsumo(rs.getBigDecimal("consumo"));
                ree.setIdLote(rs.getInt("id_lote"));
                resultado.add(ree);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroEnergia> selectLote() {
        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectIdLote);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroEnergia ree = new RegistroEnergia();
                ree.setIdLote(rs.getInt("id_lote"));
                resultado.add(ree);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroEnergia> selectData() {
        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectData);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroEnergia ree = new RegistroEnergia();
                ree.setDtRegistro(rs.getDate("dt_registro"));
                resultado.add(ree);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<RegistroEnergia> selectConsumo() {
        List<RegistroEnergia> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectConsumo);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroEnergia ree = new RegistroEnergia();
                ree.setConsumo(rs.getBigDecimal("consumo"));
                resultado.add(ree);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }


    // Insere um novo registro.
    public String insert(RegistroEnergia ree) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoInsert)) {
            ps.setDate(1, ree.getDtRegistro());
            ps.setBigDecimal(2, ree.getConsumo());
            ps.setInt(3, ree.getIdLote());
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
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateidLote)) {
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

    public String updateConsumo(double consNew,double consOld ){
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoupdateConsumo)) {
            ps.setDouble(1,consNew);
            ps.setDouble(2,consOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}
