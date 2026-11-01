package primeirobd.service;

import primeirobd.model.Lote;
import primeirobd.model.Lote;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;

// Executa operações de lotes e seus registros de consumo relacionados.
public class LoteDAO implements primeirobd.repository.LoteDAO {
    public static final String comandoSelectAll = "SELECT * FROM lote;";
    public static final String comandoSelectPaginadoSemParametros = "SELECT *, granja.nome FROM lote JOIN granja ON lote.id_granja = granja.id ORDER BY lote.id";
    public static final String comandoSelectGalinhasEntregues = "SELECT galinhas_entregadas FROM lote";
    public static final String comandoSelectGalinhasRecebidas = "SELECT galinhas_recebidas FROM lote";
    public static final String comandoSelectDataChegada = "SELECT dt_chegada FROM lote";
    public static final String comandoSelectGanho = "SELECT ganho FROM lote";
    public static final String comandoSelectById =
            "SELECT id, galinhas_entregadas, galinhas_recebidas, dt_nascimento, " +
                    "dt_chegada, dt_entrega, ganho, id_granja " +
                    "FROM lote WHERE id = ?";
    //----------------------------------------------------------------------------------------
    public static final String comandoDeleteLoteERegistro =
            "WITH alvo AS (SELECT ?::int AS id), " +
                    "apaga_registro_agua AS (DELETE FROM registro_agua WHERE id_lote = (SELECT id FROM alvo)), " +
                    "apaga_registro_energia AS (DELETE FROM registro_energia WHERE id_lote = (SELECT id FROM alvo)) " +
                    "DELETE FROM lote WHERE id = (SELECT id FROM alvo)";

    public static final String comandoInsert = "INSERT INTO lote (galinhas_entregadas,galinhas_recebidas,dt_nascimento,dt_chegada,dt_entrega,ganho,id_granja) VALUES (?,?,?,?,?,?,?)";
    //----------------------------------------------------------------------------------------
    public static final String comandoUpdateAll =
            "UPDATE lote SET galinhas_entregadas = ?, galinhas_recebidas = ?, dt_nascimento = ?, " +
                    "dt_chegada = ?, dt_entrega = ?, ganho = ?, id_granja = ? WHERE id = ?";
    public static final String comandoUpdateId = "UPDATE lote SET id = ? WHERE id = ?";
    public static final String comandoUpdateGalinhasEntregues = "UPDATE lote SET galinhas_entregadas = ? WHERE galinhas_entregadas = ?";
    public static final String comandoUpdateGalinhasRecebidas = "UPDATE lote SET galinhas_recebidas = ? WHERE galinhas_recebidas = ?";
    public static final String comandoUpdateDataChegada = "UPDATE lote SET dt_chegada = ? WHERE dt_chegada = ?";
    public static final String comandoUpdateGanho = "UPDATE lote SET ganho = ? WHERE ganho = ?";
    public static final String comandoSelectCount =  "SELECT COUNT(*) FROM lote";
    public static final String comandoSelectPaginado =
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


    public static final String comandoSelectPaginadoComBusca =
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

    public static final String comandoSelectCountComBusca =
            "SELECT COUNT(*) FROM lote " +
                    "JOIN granja ON granja.id = lote.id_granja " +
                    "WHERE granja.nome LIKE ?";

    public int contarBusca(String busca) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectCountComBusca)) {
            ps.setString(1, busca);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar. \n" + e.getMessage());
        }
    }

    public int contar() {
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

// A ordenação aceita somente colunas previamente definidas.
    public List<Lote> selectPaginado(int tamanho, int offset, String ordenarPor) {
        // Nunca inserir diretamente um parâmetro do usuário no ORDER BY.
        String colunaOrdenacao;
        if ("entregues".equals(ordenarPor)) {
            colunaOrdenacao = "lote.galinhas_entregadas";
        } else if ("ganho".equals(ordenarPor)) {
            colunaOrdenacao = "lote.ganho";
        } else {
            colunaOrdenacao = "lote.dt_chegada";
        }
        String query = String.format(comandoSelectPaginado, colunaOrdenacao);

        List<Lote> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, tamanho);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lote lot = new Lote();
                    lot.setId(rs.getInt("id"));
                    lot.setNomeGranja(rs.getString("nome"));
                    lot.setGalinhasEntregadas(rs.getInt("galinhas_entregadas"));
                    lot.setGalinhasRecebidas(rs.getInt("galinhas_recebidas"));
                    lot.setDtChegada(rs.getDate("dt_chegada"));
                    lot.setGanho(rs.getBigDecimal("ganho"));
                    resultado.add(lot);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public List<Lote> selectPaginadoComBusca(String busca, int tamanho, int offset, String ordenarPor) {
        // Nunca inserir diretamente um parâmetro do usuário no ORDER BY.
        String colunaOrdenacao;
        if ("entregues".equals(ordenarPor)) {
            colunaOrdenacao = "lote.galinhas_entregadas";
        } else if ("ganho".equals(ordenarPor)) {
            colunaOrdenacao = "lote.ganho";
        } else {
            colunaOrdenacao = "lote.dt_chegada";
        }
        String query = String.format(comandoSelectPaginadoComBusca, colunaOrdenacao);

        List<Lote> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, busca);
            ps.setInt(2, tamanho);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lote lot = new Lote();
                    lot.setId(rs.getInt("id"));
                    lot.setNomeGranja(rs.getString("nome"));
                    lot.setGalinhasEntregadas(rs.getInt("galinhas_entregadas"));
                    lot.setGalinhasRecebidas(rs.getInt("galinhas_recebidas"));
                    lot.setDtChegada(rs.getDate("dt_chegada"));
                    lot.setGanho(rs.getBigDecimal("ganho"));
                    resultado.add(lot);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    // Busca um registro para edição ou exclusão.
    public Lote selectById(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectById)) {
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
    public String updateLote(Lote lot) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateAll)) {
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
    public List<Lote> selectAll() {
        List<Lote> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lote lot = new Lote();
                lot.setId(rs.getInt("id"));
                lot.setDtChegada(rs.getDate("dt_chegada"));
                lot.setDtEntrega(rs.getDate("dt_entrega"));
                lot.setDtNascimento(rs.getDate("dt_nascimento"));
                lot.setGalinhasEntregadas(rs.getInt("galinhas_entregadas"));
                lot.setGalinhasRecebidas(rs.getInt("galinhas_recebidas"));
                lot.setGanho(rs.getBigDecimal("ganho"));
                lot.setIdGranja(rs.getInt("id_granja"));
                resultado.add(lot);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Lote> selectPaginadoSemParametros(){
        List<Lote> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comandoSelectPaginadoSemParametros);
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

    public List<Lote> selectGanho() {
        List<Lote> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectGanho);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lote lot = new Lote();
                lot.setGanho(rs.getBigDecimal("ganho"));
                resultado.add(lot);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }
    public List<Lote> selectGalinhasRecebidas() {
        List<Lote> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectGalinhasRecebidas);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lote lot = new Lote();
                lot.setGalinhasRecebidas(rs.getInt("galinhas_recebidas"));
                resultado.add(lot);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Lote> selectGalinhasEntregues() {
        List<Lote> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectGalinhasEntregues);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lote lot = new Lote();
                lot.setGalinhasEntregadas(rs.getInt("galinhas_entregadas"));
                resultado.add(lot);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Lote> selectDataChegada() {
        List<Lote> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectDataChegada);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lote lot = new Lote();
                lot.setDtChegada(rs.getDate("dt_chegada"));
                resultado.add(lot);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // Exclui o registro identificado.
// Remove primeiro os registros de água e energia ligados ao lote.
    public String delete(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoDeleteLoteERegistro)) {
            ps.setInt(1, id);
            ps.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // Insere um novo registro.
    public String insert(Lote lot) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoInsert)) {
            ps.setInt(1, lot.getGalinhasEntregadas());
            ps.setInt(2, lot.getGalinhasRecebidas());
            ps.setDate(3, lot.getDtNascimento());
            ps.setDate(4, lot.getDtChegada());
            ps.setDate(5, lot.getDtEntrega());
            ps.setBigDecimal(6, lot.getGanho());
            ps.setInt(7, lot.getIdGranja());
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
            ps.setInt(1, idNew);
            ps.setInt(2, idOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateGalinhasEntregues(int entreguesNew, int entreguesOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateGalinhasEntregues)) {
            ps.setInt(1, entreguesNew);
            ps.setInt(2, entreguesOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateGalinhasRecebidas(int recebidasNew, int recebidasOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateGalinhasRecebidas)) {
            ps.setInt(1, recebidasNew);
            ps.setInt(2, recebidasOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateDataChegada(Date chegadaNew, Date chegadaOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateDataChegada)) {
            ps.setDate(1, chegadaNew);
            ps.setDate(2, chegadaOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateGanho(double ganhoNew, double ganhoOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateGanho)) {
            ps.setDouble(1, ganhoNew);
            ps.setDouble(2, ganhoOld);
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}