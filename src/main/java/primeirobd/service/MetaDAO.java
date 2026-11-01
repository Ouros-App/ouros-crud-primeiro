package primeirobd.service;

import primeirobd.model.Meta;
import primeirobd.model.Meta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;

// Administra metas individuais ou gerais vinculadas às granjas.
public class MetaDAO implements primeirobd.repository.MetaDAO {
    public static final String comandoSelectAll = "SELECT * FROM meta";
    public static final String comandoSelectIdGranja= "SELECT id_granja FROM meta";
    public static final String comandoSelectValorAlvo = "SELECT valor_alvo FROM meta";
    public static final String comandoDeleteById = "DELETE FROM meta WHERE id = ?";
    public static final String comandoInsert = "INSERT INTO meta (titulo,descricao,tipo_meta,status,valor_alvo,regiao,estado,is_individual,id_granja) VALUES(?,?,?,?,?,?,?,?,?)";
    public static final String comandoUpdateId = "UPDATE meta SET id = ? WHERE id = ?";
    public static final String comandoUpdateTitulo = "UPDATE meta SET titulo = ? WHERE titulo = ?";
    public static final String comandoUpdateIdGranja = "UPDATE meta SET id_granja = ? WHERE id_granja = ?";
    public static final String comandoUpdateValorAlvo = "UPDATE meta SET valor_alvo = ? WHERE valor_alvo = ?";
    public static final String comandoSelectCount =  "SELECT COUNT(*) FROM meta";

    public static final String comandoSelectCountPaginado =
            "SELECT COUNT(*) FROM meta m " +
                    "LEFT JOIN granja g ON g.id = m.id_granja " +
                    "WHERE m.is_individual = ? AND (m.titulo LIKE ? OR g.nome LIKE ?)";

    public static final String comandoSelectPaginado =
            "SELECT m.id, m.titulo, m.valor_alvo, m.status, g.nome AS nome_granja " +
                    "FROM meta m " +
                    "LEFT JOIN granja g ON g.id = m.id_granja " +
                    "WHERE m.is_individual = ? AND (m.titulo LIKE ? OR g.nome LIKE ?) " +
                    "ORDER BY %s, m.id " +
                    "LIMIT ? OFFSET ?";
    public static final String comandoSelectbyId =
            "SELECT id, titulo, descricao, tipo_meta, status, valor_alvo, regiao, estado, " +
                    "is_individual, id_granja FROM meta WHERE id = ?";

    public static final String comandoUpdateAll =
            "UPDATE meta SET titulo = ?, descricao = ?, tipo_meta = ?, status = ?, valor_alvo = ?, " +
                    "regiao = ?, estado = ?, is_individual = ?, id_granja = ? WHERE id = ?";    public static final String SELECT_TITULO = "SELECT titulo FROM meta";

    public int contarBusca(boolean individual, String busca) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectCountPaginado)) {
            ps.setBoolean(1, individual);
            ps.setString(2, busca);
            ps.setString(3, busca);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar. \n" + e.getMessage());
        }
    }

    public int contarAllMetas() {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(comandoSelectCount);
            ResultSet rs = ps.executeQuery()){
            if (rs.next()){
                return rs.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar. \n"+e.getMessage());
        }
    }

    // Filtra por tipo e texto antes de aplicar a ordenação e a paginação.
    public List<Meta> selectPaginadoComBusca(boolean individual, String busca, int tamanho, int offset, String ordenarPor) {
        String colunaOrdenacao;
        if ("status".equals(ordenarPor)) {
            colunaOrdenacao = "m.status";
        } else {
            colunaOrdenacao = "m.titulo";
        }
        String query = String.format(comandoSelectPaginado, colunaOrdenacao);

        List<Meta> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, individual);
            ps.setString(2, busca);
            ps.setString(3, busca);
            ps.setInt(4, tamanho);
            ps.setInt(5, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Meta met = new Meta();
                    met.setId(rs.getInt("id"));
                    met.setTitulo(rs.getString("titulo"));
                    met.setValorAlvo(rs.getBigDecimal("valor_alvo"));
                    met.setStatus(rs.getString("status"));
                    met.setNomeGranja(rs.getString("nome_granja"));
                    resultado.add(met);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public Meta selectById(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectbyId)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Meta met = new Meta();
                met.setId(rs.getInt("id"));
                met.setTitulo(rs.getString("titulo"));
                met.setDescricao(rs.getString("descricao"));
                met.setTipoMeta(rs.getString("tipo_meta"));
                met.setStatus(rs.getString("status"));
                met.setValorAlvo(rs.getBigDecimal("valor_alvo"));
                met.setRegiao(rs.getString("regiao"));
                met.setEstado(rs.getString("estado"));
                met.setIndividual(rs.getBoolean("is_individual"));
                met.setIdGranja(rs.getInt("id_granja"));
                return met;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar meta.\n" + e.getMessage(), e);
        }
    }

    // metodo update completo (todos os campos da meta são editáveis)
// Metas gerais não recebem uma granja vinculada.
    public String updateMeta(Meta met) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateAll)) {
            ps.setString(1, met.getTitulo());
            ps.setString(2, met.getDescricao());
            ps.setString(3, met.getTipoMeta());
            ps.setString(4, met.getStatus());
            ps.setBigDecimal(5, met.getValorAlvo());
            ps.setString(6, met.getRegiao());
            ps.setString(7, met.getEstado());
            ps.setBoolean(8, met.isIndividual());
            if (met.isIndividual()) {
                ps.setInt(9, met.getIdGranja());
            } else {
                ps.setNull(9, Types.INTEGER);
            }
            ps.setInt(10, met.getId());
            ps.executeUpdate();
            return "Meta atualizada com sucesso";
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar meta.\n" + e.getMessage(), e);
        }
    }

    // Lista os registros para consultas administrativas.
    public List<Meta> selectAll() {
        List<Meta> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Meta met = new Meta();
                met.setId(rs.getInt("id"));
                met.setDescricao(rs.getString("descricao"));
                met.setEstado(rs.getString("estado"));
                met.setIdGranja(rs.getInt("id_granja"));
                met.setIndividual(rs.getBoolean("is_individual"));
                met.setRegiao(rs.getString("regiao"));
                met.setStatus(rs.getString("status"));
                met.setTipoMeta(rs.getString("tipo_meta"));
                met.setTitulo(rs.getString("titulo"));
                met.setValorAlvo(rs.getBigDecimal("valor_alvo"));
                resultado.add(met);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Meta> selectPaginado(){
        List<Meta> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Meta met = new Meta();
                met.setTitulo(rs.getString("titulo"));
                met.setNomeGranja(rs.getString("nome"));
                met.setValorAlvo(rs.getBigDecimal("valor_alvo"));
                resultado.add(met);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Erro: " + e.getMessage());
        }
    }

    public List<Meta> selectTitulo() {
        List<Meta> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_TITULO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Meta met = new Meta();
                met.setTitulo(rs.getString("titulo"));
                resultado.add(met);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Meta> selectIdGranja() {
        List<Meta> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectIdGranja);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Meta met = new Meta();
                met.setIdGranja(rs.getInt("id_granja"));
                resultado.add(met);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Meta> selectValorAlvo() {
        List<Meta> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectValorAlvo);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Meta met = new Meta();
                met.setValorAlvo(rs.getBigDecimal("valor_alvo"));
                resultado.add(met);
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
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

    // Insere um novo registro.
    public String insert(Meta met) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoInsert)) {
            ps.setString(1, met.getTitulo());
            ps.setString(2, met.getDescricao());
            ps.setString(3, met.getTipoMeta());
            ps.setString(4, met.getStatus());
            ps.setBigDecimal(5, met.getValorAlvo());
            ps.setString(6, met.getRegiao());
            ps.setString(7, met.getEstado());
            ps.setBoolean(8, met.isIndividual());
            ps.setInt(9, met.getIdGranja());
            ps.executeUpdate();
            return "Item inserido com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // Atualiza o registro identificado.
    public String updateId(int idNew, int idOld) {
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

    public String updateTitulo(String tituloNew, String tituloOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateTitulo)) {
            ps.setString(1,tituloNew);
            ps.setString(2,tituloOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateIdGranja(int idGranjaNew, int idGranjaOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateIdGranja)) {
            ps.setInt(1,idGranjaNew);
            ps.setInt(2,idGranjaOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateValorAlvo(double valorAlvoNew, double valorAlvoOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateValorAlvo)) {
            ps.setDouble(1,valorAlvoNew);
            ps.setDouble(2,valorAlvoOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}