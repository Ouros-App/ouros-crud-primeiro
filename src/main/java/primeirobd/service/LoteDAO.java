package primeirobd.service;

import primeirobd.model.Lote;
import primeirobd.model.Lote;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;

// Executa operações de lotes e seus registros de consumo relacionados.
public class LoteDAO implements primeirobd.repository.LoteDAO {
    public static final String SELECT_ALL = "SELECT * FROM lote;";
    public static final String SELECT_ALL_JOIN_PAGINADO = "SELECT *, granja.nome FROM lote JOIN granja ON lote.id_granja = granja.id ORDER BY lote.id";
    public static final String SELECT_ENTREGUES = "SELECT galinhas_entregadas FROM lote";
    public static final String SELECT_RECEBIDAS = "SELECT galinhas_recebidas FROM lote";
    public static final String SELECT_CHEGADA = "SELECT dt_chegada FROM lote";
    public static final String SELECT_GANHO = "SELECT ganho FROM lote";
    public static final String SELECT_BY_ID =
            "SELECT id, galinhas_entregadas, galinhas_recebidas, dt_nascimento, " +
                    "dt_chegada, dt_entrega, ganho, id_granja " +
                    "FROM lote WHERE id = ?";
    //----------------------------------------------------------------------------------------
    public static final String DELETE_LOTE_E_REGISTROS =
            "WITH alvo AS (SELECT ?::int AS id), " +
                    "apaga_registro_agua AS (DELETE FROM registro_agua WHERE id_lote = (SELECT id FROM alvo)), " +
                    "apaga_registro_energia AS (DELETE FROM registro_energia WHERE id_lote = (SELECT id FROM alvo)) " +
                    "DELETE FROM lote WHERE id = (SELECT id FROM alvo)";

    public static final String INSERT = "INSERT INTO lote (galinhas_entregadas,galinhas_recebidas,dt_nascimento,dt_chegada,dt_entrega,ganho,id_granja) VALUES (?,?,?,?,?,?,?)";
    //----------------------------------------------------------------------------------------
    public static final String UPDATE_ALL =
            "UPDATE lote SET galinhas_entregadas = ?, galinhas_recebidas = ?, dt_nascimento = ?, " +
                    "dt_chegada = ?, dt_entrega = ?, ganho = ?, id_granja = ? WHERE id = ?";
    public static final String UPDATE_ID = "UPDATE lote SET id = ? WHERE id = ?";
    public static final String UPDATE_ENTREGUES = "UPDATE lote SET galinhas_entregadas = ? WHERE galinhas_entregadas = ?";
    public static final String UPDATE_RECEBIDAS = "UPDATE lote SET galinhas_recebidas = ? WHERE galinhas_recebidas = ?";
    public static final String UPDATE_CHEGADA = "UPDATE lote SET dt_chegada = ? WHERE dt_chegada = ?";
    public static final String UPDATE_GANHO = "UPDATE lote SET ganho = ? WHERE ganho = ?";
    public static final String SELECT_COUNT =  "SELECT COUNT(*) FROM lote";
    public static final String SELECT_PAGINADO =
            "SELECT lote.id, " +
                    "granja.nome, " +
                    "lote.galinhas_entregadas, " +
                    "lote.galinhas_recebidas, " +
                    "lote.dt_chegada, " +
                    "lote.ganho " +
                    "FROM lote " +
                    "JOIN granja ON granja.id = lote.id_granja " +
                    "ORDER BY %s, lote.id " +
                    "LIMIT ? OFFSET ?";


    public static final String SELECT_PAGINADO_FILTROS =
            "SELECT lote.id, " +
                    "granja.nome, " +
                    "lote.galinhas_entregadas, " +
                    "lote.galinhas_recebidas, " +
                    "lote.dt_chegada, " +
                    "lote.ganho " +
                    "FROM lote " +
                    "JOIN granja ON granja.id = lote.id_granja " +
                    "WHERE granja.nome LIKE ? " +
                    "ORDER BY %s, lote.id " +
                    "LIMIT ? OFFSET ?";

    public static final String SELECT_COUNT_FILTRO =
            "SELECT COUNT(*) FROM lote " +
                    "JOIN granja ON granja.id = lote.id_granja " +
                    "WHERE granja.nome LIKE ?";

    public int contar_filtro_pesquisa(String busca) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_COUNT_FILTRO)) {
            preparoConsultaSQL.setString(1, busca);

            try (ResultSet resultado = preparoConsultaSQL.executeQuery()) {
                return resultado.next() ? resultado.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar. \n" + e.getMessage());
        }
    }

    public int contar() {
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

// A ordenação aceita somente colunas previamente definidas.
    public List<Lote> select_paginado(int tamanho, int offset, String ordenarPor) {
        // Nunca inserir diretamente um parâmetro do usuário no ORDER BY.
        String colunaOrdenacao;
        if ("entregues".equals(ordenarPor)) {
            colunaOrdenacao = "lote.galinhas_entregadas";
        } else if ("ganho".equals(ordenarPor)) {
            colunaOrdenacao = "lote.ganho";
        } else {
            colunaOrdenacao = "lote.dt_chegada";
        }
        String query = String.format(SELECT_PAGINADO, colunaOrdenacao);

        List<Lote> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(query)) {
            preparoConsultaSQL.setInt(1, tamanho);
            preparoConsultaSQL.setInt(2, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
                while (resultadoConsulta.next()) {
                    Lote lot = new Lote();
                    lot.setId(resultadoConsulta.getInt("id"));
                    lot.setNomeGranja(resultadoConsulta.getString("nome"));
                    lot.setGalinhasEntregadas(resultadoConsulta.getInt("galinhas_entregadas"));
                    lot.setGalinhasRecebidas(resultadoConsulta.getInt("galinhas_recebidas"));
                    lot.setDtChegada(resultadoConsulta.getDate("dt_chegada"));
                    lot.setGanho(resultadoConsulta.getBigDecimal("ganho"));
                    resultado.add(lot);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public List<Lote> select_paginado_filtro_pesquisa(String busca, int tamanho, int offset, String ordenarPor) {
        // Nunca inserir diretamente um parâmetro do usuário no ORDER BY.
        String colunaOrdenacao;
        if ("entregues".equals(ordenarPor)) {
            colunaOrdenacao = "lote.galinhas_entregadas";
        } else if ("ganho".equals(ordenarPor)) {
            colunaOrdenacao = "lote.ganho";
        } else {
            colunaOrdenacao = "lote.dt_chegada";
        }
        String query = String.format(SELECT_PAGINADO_FILTROS, colunaOrdenacao);

        List<Lote> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(query)) {
            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setInt(2, tamanho);
            preparoConsultaSQL.setInt(3, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
                while (resultadoConsulta.next()) {
                    Lote lot = new Lote();
                    lot.setId(resultadoConsulta.getInt("id"));
                    lot.setNomeGranja(resultadoConsulta.getString("nome"));
                    lot.setGalinhasEntregadas(resultadoConsulta.getInt("galinhas_entregadas"));
                    lot.setGalinhasRecebidas(resultadoConsulta.getInt("galinhas_recebidas"));
                    lot.setDtChegada(resultadoConsulta.getDate("dt_chegada"));
                    lot.setGanho(resultadoConsulta.getBigDecimal("ganho"));
                    resultado.add(lot);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    // Busca um registro para edição ou exclusão.
    public Lote select_by_id(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Lote lot = new Lote();
                lot.setId(rs.getInt("id"));
                lot.setGalinhasEntregadas(rs.getInt("galinhas_entregadas"));
                lot.setGalinhasRecebidas(rs.getInt("galinhas_recebidas"));
                lot.setDtNascimento(rs.getDate("dt_nascimento"));
                lot.setDtChegada(rs.getDate("dt_chegada"));
                lot.setDtEntrega(rs.getDate("dt_entrega"));
                lot.setGanho(rs.getBigDecimal("ganho"));
                lot.setIdGranja(rs.getInt("id_granja"));
                return lot;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar lote.\n" + e.getMessage(), e);
        }
    }

    // Insere um novo registro.

    // metodo update completo (todos os campos do lote são editáveis)
    public String update_lote(Lote lot) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(UPDATE_ALL)) {
            ps.setInt(1, lot.getGalinhasEntregadas());
            ps.setInt(2, lot.getGalinhasRecebidas());
            ps.setDate(3, lot.getDtNascimento());
            ps.setDate(4, lot.getDtChegada());
            ps.setDate(5, lot.getDtEntrega());
            ps.setBigDecimal(6, lot.getGanho());
            ps.setInt(7, lot.getIdGranja());
            ps.setInt(8, lot.getId());
            ps.executeUpdate();
            return "Lote atualizado com sucesso";
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar lote.\n" + e.getMessage(), e);
        }
    }

    // Lista os registros para consultas administrativas.
    public List<Lote> select_all() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setId(resultadoConsulta.getInt("id"));
                lot.setDtChegada(resultadoConsulta.getDate("dt_chegada"));
                lot.setDtEntrega(resultadoConsulta.getDate("dt_entrega"));
                lot.setDtNascimento(resultadoConsulta.getDate("dt_nascimento"));
                lot.setGalinhasEntregadas(resultadoConsulta.getInt("galinhas_entregadas"));
                lot.setGalinhasRecebidas(resultadoConsulta.getInt("galinhas_recebidas"));
                lot.setGanho(resultadoConsulta.getBigDecimal("ganho"));
                lot.setIdGranja(resultadoConsulta.getInt("id_granja"));
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Lote> select_all_join_paginado(){
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL_JOIN_PAGINADO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setId(resultadoConsulta.getInt("id"));
                lot.setNomeGranja(resultadoConsulta.getString("nome"));
                lot.setGalinhasEntregadas(resultadoConsulta.getInt("galinhas_entregadas"));
                lot.setGalinhasRecebidas(resultadoConsulta.getInt("galinhas_recebidas"));
                lot.setDtChegada(resultadoConsulta.getDate("dt_chegada"));
                lot.setGanho(resultadoConsulta.getBigDecimal("ganho"));
                // O status não é retornado por esta consulta.
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Lote> select_ganho() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_GANHO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setGanho(resultadoConsulta.getBigDecimal("ganho"));
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }
    public List<Lote> select_recebida() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_RECEBIDAS);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setGalinhasRecebidas(resultadoConsulta.getInt("galinhas_recebidas"));
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Lote> select_entregue() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ENTREGUES);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setGalinhasEntregadas(resultadoConsulta.getInt("galinhas_entregadas"));
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Lote> select_chegada() {
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_CHEGADA);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Lote lot = new Lote();
                lot.setDtChegada(resultadoConsulta.getDate("dt_chegada"));
                informacoes.add(lot);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // Exclui o registro identificado.
// Remove primeiro os registros de água e energia ligados ao lote.
    public String delete(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(DELETE_LOTE_E_REGISTROS)) {
            preparoConsultaSQL.setInt(1, id);
            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // Insere um novo registro.
    public String insert(Lote lot) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {
            preparoConsultaSQL.setInt(1, lot.getGalinhasEntregadas());
            preparoConsultaSQL.setInt(2, lot.getGalinhasRecebidas());
            preparoConsultaSQL.setDate(3, lot.getDtNascimento());
            preparoConsultaSQL.setDate(4, lot.getDtChegada());
            preparoConsultaSQL.setDate(5, lot.getDtEntrega());
            preparoConsultaSQL.setBigDecimal(6, lot.getGanho());
            preparoConsultaSQL.setInt(7, lot.getIdGranja());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // Atualiza o registro identificado.
    public String update_id(int idNew, int idOld) {
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

    public String update_entregues(int entreguesNew, int entreguesOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ENTREGUES)) {
            preparoConsultaSQL.setInt(1, entreguesNew);
            preparoConsultaSQL.setInt(2, entreguesOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_recebidas(int recebidasNew, int recebidasOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_RECEBIDAS)) {
            preparoConsultaSQL.setInt(1, recebidasNew);
            preparoConsultaSQL.setInt(2, recebidasOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_chegada(Date chegadaNew, Date chegadaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_CHEGADA)) {
            preparoConsultaSQL.setDate(1, chegadaNew);
            preparoConsultaSQL.setDate(2, chegadaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_ganho(double ganhoNew, double ganhoOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_GANHO)) {
            preparoConsultaSQL.setDouble(1, ganhoNew);
            preparoConsultaSQL.setDouble(2, ganhoOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}