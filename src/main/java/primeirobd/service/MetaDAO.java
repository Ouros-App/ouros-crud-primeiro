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

public class MetaDAO implements primeirobd.repository.MetaDAO {
    public static final String SELECT_ALL = "SELECT * FROM meta";
    public static final String SELECT_ID_GRANJA= "SELECT id_granja FROM meta";
    public static final String SELECT_VALOR_ALVO = "SELECT valor_alvo FROM meta";
    public static final String DELETE_BY_ID = "DELETE FROM meta WHERE id = ?";
    public static final String INSERT = "INSERT INTO meta (titulo,descricao,tipo_meta,status,valor_alvo,regiao,estado,is_individual,id_granja) VALUES(?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_ID = "UPDATE meta SET id = ? WHERE id = ?";
    public static final String UPDATE_TITULO = "UPDATE meta SET titulo = ? WHERE titulo = ?";
    public static final String UPDATE_ID_GRANJA = "UPDATE meta SET id_granja = ? WHERE id_granja = ?";
    public static final String UPDATE_VALOR_ALVO = "UPDATE meta SET valor_alvo = ? WHERE valor_alvo = ?";
    public static final String SELECT_COUNT =  "SELECT COUNT(*) FROM meta";

    public static final String SELECT_COUNT_PAGINADO =
            "SELECT COUNT(*) FROM meta m " +
                    "LEFT JOIN granja g ON g.id = m.id_granja " +
                    "WHERE m.is_individual = ? AND (m.titulo LIKE ? OR g.nome LIKE ?)";

    public static final String SELECT_PAGINADO =
            "SELECT m.id, m.titulo, m.valor_alvo, m.status, g.nome AS nome_granja " +
                    "FROM meta m " +
                    "LEFT JOIN granja g ON g.id = m.id_granja " +
                    "WHERE m.is_individual = ? AND (m.titulo LIKE ? OR g.nome LIKE ?) " +
                    "ORDER BY %s, m.id " +
                    "LIMIT ? OFFSET ?";
    public static final String SELECT_BY_ID =
            "SELECT id, titulo, descricao, tipo_meta, status, valor_alvo, regiao, estado, " +
                    "is_individual, id_granja FROM meta WHERE id = ?";

    public static final String UPDATE_ALL =
            "UPDATE meta SET titulo = ?, descricao = ?, tipo_meta = ?, status = ?, valor_alvo = ?, " +
                    "regiao = ?, estado = ?, is_individual = ?, id_granja = ? WHERE id = ?";    public static final String SELECT_TITULO = "SELECT titulo FROM meta";

    public int contar(boolean individual, String busca) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = conexao.prepareStatement(SELECT_COUNT_PAGINADO)) {
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

    public int contar_all() {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try(PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_COUNT);
            ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()){
            if (resultadoConsulta.next()){
                return resultadoConsulta.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar. \n"+e.getMessage());
        }
    }

    public List<Meta> select_paginado(boolean individual, String busca, int tamanho, int offset, String ordenarPor) {
        String colunaOrdenacao;
        if ("status".equals(ordenarPor)) {
            colunaOrdenacao = "m.status";
        } else {
            colunaOrdenacao = "m.titulo";
        }
        String query = String.format(SELECT_PAGINADO, colunaOrdenacao);

        List<Meta> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = conexao.prepareStatement(query)) {
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

    public Meta select_by_id(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(SELECT_BY_ID)) {
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
    public String update_meta(Meta met) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(UPDATE_ALL)) {
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
                met.setValorAlvo(resultadoConsulta.getBigDecimal("valor_alvo"));
                informacoes.add(met);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Meta> select_paginado(){
        List<Meta> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Meta met = new Meta();
                met.setTitulo(resultadoConsulta.getString("titulo"));
                met.setNomeGranja(resultadoConsulta.getString("nome"));
                met.setValorAlvo(resultadoConsulta.getBigDecimal("valor_alvo"));
                informacoes.add(met);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro: " + e.getMessage());
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
                met.setValorAlvo(resultadoConsulta.getBigDecimal("valor_alvo"));
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
            preparoConsultaSQL.setString(1, met.getTitulo());
            preparoConsultaSQL.setString(2, met.getDescricao());
            preparoConsultaSQL.setString(3, met.getTipoMeta());
            preparoConsultaSQL.setString(4, met.getStatus());
            preparoConsultaSQL.setBigDecimal(5, met.getValorAlvo());
            preparoConsultaSQL.setString(6, met.getRegiao());
            preparoConsultaSQL.setString(7, met.getEstado());
            preparoConsultaSQL.setBoolean(8, met.isIndividual());
            preparoConsultaSQL.setInt(9, met.getIdGranja());
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