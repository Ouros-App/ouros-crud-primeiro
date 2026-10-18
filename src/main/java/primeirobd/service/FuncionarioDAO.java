package primeirobd.service;

import primeirobd.model.Empresa;
import primeirobd.model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements primeirobd.repository.FuncionarioDAO {
    public static final String SELECT_ALL = "SELECT * FROM funcionario";
    public static final String SELECT_NOME = "SELECT nome FROM funcionario";
    public static final String SELECT_SETOR = "SELECT setor FROM funcionario";
    public static final String SELECT_EMAIL = "SELECT email FROM funcionario";
    public static final String DELETE_BY_ID = "DELETE FROM funcionario WHERE id = ?";
    public static final String INSERT_FUNCIONARIO_COM_TELEFONE =
            "WITH novo AS ( " +
                    "   INSERT INTO funcionario (nome, cpf, email, setor, id_empresa) " +
                    "   VALUES (?,?,?,?,?) RETURNING id " +
                    ") " +
                    "INSERT INTO telefone_funcionario (telefone, id_funcionario) " +
                    "SELECT ?, id FROM novo";
    public static final String UPDATE_ID = "UPDATE funcionario SET id = ? WHERE id = ?";
    public static final String UPDATE_EMAIL = "UPDATE funcionario SET email = ? WHERE email = ?";
    public static final String UPDATE_NOME = "UPDATE funcionario SET nome = ? WHERE nome = ?";
    public static final String UPDATE_SETOR = "UPDATE funcionario SET setor = ? WHERE setor = ?";
    public static final String UPDATE_ALL = "UPDATE funcionario SET nome = ?, cpf = ?, email = ?, setor = ?, id_empresa = ? WHERE id = ?";
    public static final String UPDATE_TELEFONE = "UPDATE telefone_funcionario SET telefone = ? WHERE id_funcionario = ?";
    public static final String SELECT_COUNT =  "SELECT COUNT(*) FROM funcionario";
    public static final String SELECT_COUNT_PAGINADO =
            "SELECT COUNT(*) " +
                    "FROM funcionario " +
                    "JOIN telefone_funcionario " +
                    "ON funcionario.id = telefone_funcionario.id_funcionario";

    public static final String SELECT_COUNT_FILTRO_TUDO =
            "SELECT COUNT(*) " +
                    "FROM funcionario " +
                    "JOIN telefone_funcionario " +
                    "ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE funcionario.nome LIKE ? " +
                    "OR funcionario.setor LIKE ? " +
                    "OR funcionario.email LIKE ? " +
                    "OR telefone_funcionario.telefone LIKE ?";

    public static final String SELECT_COUNT_FILTRO_NOME =
            "SELECT COUNT(*) FROM funcionario " +
                    "JOIN telefone_funcionario " +
                    "ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE funcionario.nome LIKE ?";

    public static final String SELECT_COUNT_FILTRO_SETOR =
            "SELECT COUNT(*) FROM funcionario " +
                    "JOIN telefone_funcionario " +
                    "ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE funcionario.setor LIKE ?";

    public static final String SELECT_COUNT_FILTRO_EMAIL =
            "SELECT COUNT(*) FROM funcionario " +
                    "JOIN telefone_funcionario " +
                    "ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE funcionario.email LIKE ?";

    public static final String SELECT_COUNT_FILTRO_TELEFONE =
            "SELECT COUNT(*) FROM funcionario " +
                    "JOIN telefone_funcionario " +
                    "ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE telefone_funcionario.telefone LIKE ?";

    public static final String SELECT_PAGINADO =
            "SELECT funcionario.id, " +
             "funcionario.nome, " +
            "funcionario.setor, " +
            "funcionario.email, " +
            "telefone_funcionario.telefone AS telefone " +
            "FROM funcionario " +
            "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
            "ORDER BY funcionario.id " +
            "LIMIT ? OFFSET ?";
    public static final String SELECT_PAGINADO_FILTRO_TUDO =
            "SELECT funcionario.id, " +
                    "funcionario.nome, " +
                    "funcionario.setor, " +
                    "funcionario.email, " +
                    "telefone_funcionario.telefone " +
                    "FROM funcionario " +
                    "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE funcionario.nome LIKE ? OR funcionario.setor LIKE ? OR funcionario.email LIKE ? OR telefone_funcionario.telefone LIKE ? " +
                    "ORDER BY funcionario.id " +
                    "LIMIT ? OFFSET ?";
    public static final String SELECT_BY_ID =
            "SELECT f.id, f.nome, f.cpf, f.email, f.setor, f.id_empresa, " +
                    "e.nome AS empresa, t.telefone " +
                    "FROM funcionario f " +
                    "JOIN empresa e ON e.id = f.id_empresa " +
                    "LEFT JOIN telefone_funcionario t ON t.id_funcionario = f.id " +
                    "WHERE f.id = ?";
    public static final String SELECT_PAGINADO_FILTRO_NOME =
            "SELECT funcionario.id, " +
            "funcionario.nome, " +
            "funcionario.setor, " +
            "funcionario.email, " +
            "telefone_funcionario.telefone " +
            "FROM funcionario " +
            "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
            "ORDER BY funcionario.id " +
            "WHERE funcionario.nome LIKE ? " +
            "LIMIT ? OFFSET ?";
    public static final String SELECT_PAGINADO_FILTRO_SETOR =
            "SELECT funcionario.id, " +
                    "funcionario.nome, " +
                    "funcionario.setor, " +
                    "funcionario.email, " +
                    "telefone_funcionario.telefone " +
                    "FROM funcionario " +
                    "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE funcionario.setor LIKE ? " +
                    "ORDER BY funcionario.id " +
                    "LIMIT ? OFFSET ?";

    public static final String SELECT_PAGINADO_FILTRO_EMAIL =
            "SELECT funcionario.id, " +
                    "funcionario.nome, " +
                    "funcionario.setor, " +
                    "funcionario.email, " +
                    "telefone_funcionario.telefone " +
                    "FROM funcionario " +
                    "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE funcionario.email LIKE ? " +
                    "ORDER BY funcionario.id " +
                    "LIMIT ? OFFSET ?";

    public static final String SELECT_PAGINADO_FILTRO_TELEFONE =
            "SELECT funcionario.id, " +
                    "funcionario.nome, " +
                    "funcionario.setor, " +
                    "funcionario.email, " +
                    "telefone_funcionario.telefone " +
                    "FROM funcionario " +
                    "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE telefone_funcionario.telefone LIKE ? " +
                    "ORDER BY funcionario.id " +
                    "LIMIT ? OFFSET ?";

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

    public List<Funcionario> select_paginado(int tamanho, int offset) {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO)) {
            preparoConsultaSQL.setInt(1, tamanho);
            preparoConsultaSQL.setInt(2, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
                while (resultadoConsulta.next()) {
                    Funcionario fun = new Funcionario();
                    fun.setId(resultadoConsulta.getInt("id"));
                    fun.setNome(resultadoConsulta.getString("nome"));
                    fun.setSetor(resultadoConsulta.getString("setor"));
                    fun.setEmail(resultadoConsulta.getString("email"));
                    fun.setTelefone(resultadoConsulta.getString("telefone"));
                    resultado.add(fun);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_paginado_filtro_tudo(String busca, int tamanho, int offset) {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO_FILTRO_TUDO)) {

            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setString(2, busca);
            preparoConsultaSQL.setString(3, busca);
            preparoConsultaSQL.setString(4, busca);
            preparoConsultaSQL.setInt(5, tamanho);
            preparoConsultaSQL.setInt(6, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

                while (resultadoConsulta.next()) {
                    Funcionario fun = new Funcionario();
                    fun.setId(resultadoConsulta.getInt("id"));

                    fun.setNome(resultadoConsulta.getString("nome"));
                    fun.setSetor(resultadoConsulta.getString("setor"));
                    fun.setEmail(resultadoConsulta.getString("email"));
                    fun.setTelefone(resultadoConsulta.getString("telefone"));
                    resultado.add(fun);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_paginado_filtro_nome(String busca, int tamanho, int offset) {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO_FILTRO_NOME)) {

            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setInt(2, tamanho);
            preparoConsultaSQL.setInt(3, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

                while (resultadoConsulta.next()) {
                    Funcionario fun = new Funcionario();
                    fun.setId(resultadoConsulta.getInt("id"));
                    fun.setNome(resultadoConsulta.getString("nome"));
                    fun.setSetor(resultadoConsulta.getString("setor"));
                    fun.setEmail(resultadoConsulta.getString("email"));
                    fun.setTelefone(resultadoConsulta.getString("telefone"));
                    resultado.add(fun);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_paginado_filtro_setor(String busca, int tamanho, int offset) {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO_FILTRO_SETOR)) {

            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setInt(2, tamanho);
            preparoConsultaSQL.setInt(3, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

                while (resultadoConsulta.next()) {
                    Funcionario fun = new Funcionario();
                    fun.setId(resultadoConsulta.getInt("id"));
                    fun.setNome(resultadoConsulta.getString("nome"));
                    fun.setSetor(resultadoConsulta.getString("setor"));
                    fun.setEmail(resultadoConsulta.getString("email"));
                    fun.setTelefone(resultadoConsulta.getString("telefone"));
                    resultado.add(fun);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_paginado_filtro_email(String busca, int tamanho, int offset) {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO_FILTRO_EMAIL)) {

            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setInt(2, tamanho);
            preparoConsultaSQL.setInt(3, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

                while (resultadoConsulta.next()) {
                    Funcionario fun = new Funcionario();
                    fun.setId(resultadoConsulta.getInt("id"));
                    fun.setNome(resultadoConsulta.getString("nome"));
                    fun.setSetor(resultadoConsulta.getString("setor"));
                    fun.setEmail(resultadoConsulta.getString("email"));
                    fun.setTelefone(resultadoConsulta.getString("telefone"));
                    resultado.add(fun);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_paginado_filtro_telefone(String busca, int tamanho, int offset) {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO_FILTRO_TELEFONE)) {

            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setInt(2, tamanho);
            preparoConsultaSQL.setInt(3, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

                while (resultadoConsulta.next()) {
                    Funcionario fun = new Funcionario();
                    fun.setId(resultadoConsulta.getInt("id"));
                    fun.setNome(resultadoConsulta.getString("nome"));
                    fun.setSetor(resultadoConsulta.getString("setor"));
                    fun.setEmail(resultadoConsulta.getString("email"));
                    fun.setTelefone(resultadoConsulta.getString("telefone"));
                    resultado.add(fun);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public Funcionario select_by_id(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Funcionario fun = new Funcionario();
                fun.setId(rs.getInt("id"));
                fun.setNome(rs.getString("nome"));
                fun.setCpf(rs.getString("cpf"));
                fun.setEmail(rs.getString("email"));
                fun.setSetor(rs.getString("setor"));
                fun.setIdEmpresa(rs.getInt("id_empresa"));
                fun.setNomeEmpresa(rs.getString("empresa"));
                fun.setTelefone(rs.getString("telefone"));
                return fun;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário.\n" + e.getMessage(), e);
        }
    }

    // metodo select :D
    public List<Funcionario> select_all() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Funcionario fun = new Funcionario();
                fun.setId(resultadoConsulta.getInt("id"));
                fun.setCpf(resultadoConsulta.getString("cpf"));
                fun.setEmail(resultadoConsulta.getString("email"));
                fun.setIdEmpresa(resultadoConsulta.getInt("id_empresa"));
                fun.setNome(resultadoConsulta.getString("nome"));
                fun.setSetor(resultadoConsulta.getString("setor"));
                resultado.add(fun);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_nome() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_NOME);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Funcionario fun = new Funcionario();
                fun.setNome(resultadoConsulta.getString("nome"));
                resultado.add(fun);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_setor() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_SETOR);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Funcionario fun = new Funcionario();
                fun.setSetor(resultadoConsulta.getString("setor"));
                resultado.add(fun);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> select_email() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_EMAIL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Funcionario fun = new Funcionario();
                fun.setEmail(resultadoConsulta.getString("email"));
                resultado.add(fun);
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
            preparoConsultaSQL.executeUpdate();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert_funcionario_com_telefone(Funcionario fun, List<Empresa> empresas) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = conexao.prepareStatement(INSERT_FUNCIONARIO_COM_TELEFONE)) {

            fun.setIdEmpresa(empresas.getFirst().getId());

            ps.setString(1, fun.getNome());
            ps.setString(2, fun.getCpf());
            ps.setString(3, fun.getEmail());
            ps.setString(4, fun.getSetor());
            ps.setInt(5, fun.getIdEmpresa());
            ps.setString(6, fun.getTelefone());
            ps.executeUpdate();

            return "Funcionário e telefone inseridos com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir funcionário.\n" + e.getMessage());
        }
    }

    // metodo update :P
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

    public String update_email(String emailNew, String emailOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_EMAIL)) {
            preparoConsultaSQL.setString(1, emailNew);
            preparoConsultaSQL.setString(2, emailOld);
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

    public String update_setor(String setorNew, String setorOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_SETOR)) {
            preparoConsultaSQL.setString(1, setorNew);
            preparoConsultaSQL.setString(2, setorOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String update_funcionario_com_telefone(Funcionario fun, int idEmpresa) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try {
            conexao.setAutoCommit(false);
            try (PreparedStatement ps1 = conexao.prepareStatement(UPDATE_ALL);
                 PreparedStatement ps2 = conexao.prepareStatement(UPDATE_TELEFONE)) {

                ps1.setString(1, fun.getNome());
                ps1.setString(2, fun.getCpf());
                ps1.setString(3, fun.getEmail());
                ps1.setString(4, fun.getSetor());
                ps1.setInt(5, idEmpresa);
                ps1.setInt(6, fun.getId());
                ps1.executeUpdate();

                ps2.setString(1, fun.getTelefone());
                ps2.setInt(2, fun.getId());
                ps2.executeUpdate();

                conexao.commit();
                return "Funcionário atualizado com sucesso";
            } catch (SQLException e) {
                conexao.rollback();
                throw e;
            } finally {
                conexao.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário.\n" + e.getMessage(), e);
        }
    }


    @Override
    public int contar_paginado() {
        return executarContagem(SELECT_COUNT_PAGINADO, null);
    }

    @Override
    public int contar_paginado_filtro_tudo(String busca) {
        return executarContagem(SELECT_COUNT_FILTRO_TUDO, busca);
    }

    @Override
    public int contar_paginado_filtro_nome(String busca) {
        return executarContagem(SELECT_COUNT_FILTRO_NOME, busca);
    }

    @Override
    public int contar_paginado_filtro_setor(String busca) {
        return executarContagem(SELECT_COUNT_FILTRO_SETOR, busca);
    }

    @Override
    public int contar_paginado_filtro_email(String busca) {
        return executarContagem(SELECT_COUNT_FILTRO_EMAIL, busca);
    }

    @Override
    public int contar_paginado_filtro_telefone(String busca) {
        return executarContagem(SELECT_COUNT_FILTRO_TELEFONE, busca);
    }

    private int executarContagem(String sql, String busca) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            if (busca != null) {
                if (sql.equals(SELECT_COUNT_FILTRO_TUDO)) {
                    ps.setString(1, busca);
                    ps.setString(2, busca);
                    ps.setString(3, busca);
                    ps.setString(4, busca);
                } else {
                    ps.setString(1, busca);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao contar funcionários: " + e.getMessage(), e
            );
        }
    }
}
