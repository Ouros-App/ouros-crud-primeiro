package primeirobd.service;

import primeirobd.model.Empresa;
import primeirobd.model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Reúne operações de funcionários, telefones, filtros e paginação.
public class FuncionarioDAO implements primeirobd.repository.FuncionarioDAO {
    public static final String comandoSelectAll = "SELECT * FROM funcionario";
    public static final String comandoSelectNome = "SELECT nome FROM funcionario";
    public static final String comandoSelectSetor = "SELECT setor FROM funcionario";
    public static final String comandoSelectEmail = "SELECT email FROM funcionario";
    public static final String comandoSelectTelefone = "SELECT telefone FROM telefone_funcionario";
    public static final String comandoSelectCpf = "SELECT email FROM funcionario";
    public static final String comandoDeleteFuncionarioETelefone =
                    "WITH apaga_telefone AS (\n" +
                    "    DELETE FROM telefone_funcionario\n" +
                    "    WHERE id_funcionario = ?\n" +
                    ")\n" +
                    "DELETE FROM funcionario\n" +
                    "WHERE id = ?;";
    public static final String comandoInsertFuncionarioComTelefone =
            "WITH novo AS ( " +
                    "   INSERT INTO funcionario (nome, cpf, email, setor, id_empresa) " +
                    "   VALUES (?,?,?,?,?) RETURNING id " +
                    ") " +
                    "INSERT INTO telefone_funcionario (telefone, id_funcionario) " +
                    "SELECT ?, id FROM novo";
    public static final String comandoUpdateId = "UPDATE funcionario SET id = ? WHERE id = ?";
    public static final String comandoUpdateEmail = "UPDATE funcionario SET email = ? WHERE id = ?";
    public static final String comandoUpdateNome = "UPDATE funcionario SET nome = ? WHERE id = ?";
    public static final String comandoUpdateCpf = "UPDATE funcionario SET cpf = ? WHERE id = ?";
    public static final String comandoUpdateSetor = "UPDATE funcionario SET setor = ? WHERE id = ?";
    public static final String comandoUpdateAll = "UPDATE funcionario SET nome = ?, cpf = ?, email = ?, setor = ?, id_empresa = ? WHERE id = ?";
    public static final String comandoUpdateTelefone = "UPDATE telefone_funcionario SET telefone = ? WHERE id_funcionario = ?";
    public static final String comandoSelectCount =  "SELECT COUNT(*) FROM funcionario";
    public static final String comandoSelectCountPaginado =
            "SELECT COUNT(*) " +
                    "FROM funcionario " +
                    "JOIN telefone_funcionario " +
                    "ON funcionario.id = telefone_funcionario.id_funcionario";

    public static final String comandoSelectCountComBusca =
            "SELECT COUNT(*) " +
                    "FROM funcionario " +
                    "JOIN telefone_funcionario " +
                    "ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE funcionario.nome LIKE ? " +
                    "OR funcionario.setor LIKE ? " +
                    "OR funcionario.email LIKE ? " +
                    "OR telefone_funcionario.telefone LIKE ?";

    public static final String comandoSelectPaginado =
            "SELECT funcionario.id, " +
             "funcionario.nome, " +
            "funcionario.setor, " +
            "funcionario.email, " +
            "telefone_funcionario.telefone AS telefone " +
            "FROM funcionario " +
            "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
            "ORDER BY %s, funcionario.id " +
            "LIMIT ? OFFSET ?";
    public static final String comandoSelectPaginadoComBusca =
            "SELECT funcionario.id, " +
                    "funcionario.nome, " +
                    "funcionario.setor, " +
                    "funcionario.email, " +
                    "telefone_funcionario.telefone " +
                    "FROM funcionario " +
                    "JOIN telefone_funcionario ON funcionario.id = telefone_funcionario.id_funcionario " +
                    "WHERE funcionario.nome LIKE ? OR funcionario.setor LIKE ? OR funcionario.email LIKE ? OR telefone_funcionario.telefone LIKE ? " +
                    "ORDER BY %s, funcionario.id " +
                    "LIMIT ? OFFSET ?";
    public static final String comandoSelectById =
            "SELECT f.id, f.nome, f.cpf, f.email, f.setor, f.id_empresa, " +
                    "e.nome AS empresa, t.telefone " +
                    "FROM funcionario f " +
                    "JOIN empresa e ON e.id = f.id_empresa " +
                    "LEFT JOIN telefone_funcionario t ON t.id_funcionario = f.id " +
                    "WHERE f.id = ?";

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

// A lista fixa de colunas evita SQL dinâmico vindo diretamente do usuário.
    private String colunaOrdem(String ordenarPor) {
        return "setor".equals(ordenarPor) ? "funcionario.setor" : "funcionario.nome"; // whitelist
    }

    public List<Funcionario> selectPaginado(int tamanho, int offset, String ordenarPor) {
        String query = String.format(comandoSelectPaginado, colunaOrdem(ordenarPor));
        List<Funcionario> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, tamanho);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(rs.getInt("id"));
                    funcionario.setNome(rs.getString("nome"));
                    funcionario.setSetor(rs.getString("setor"));
                    funcionario.setEmail(rs.getString("email"));
                    funcionario.setTelefone(rs.getString("telefone"));
                    resultado.add(funcionario);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public List<Funcionario> selectPaginadoComBusca(String busca, int tamanho, int offset, String ordenarPor) {
        String query = String.format(comandoSelectPaginadoComBusca, colunaOrdem(ordenarPor));
        List<Funcionario> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, busca);
            ps.setString(2, busca);
            ps.setString(3, busca);
            ps.setString(4, busca);
            ps.setInt(5, tamanho);
            ps.setInt(6, offset);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Funcionario funcionario  = new Funcionario();
                    funcionario .setId(rs.getInt("id"));

                    funcionario .setNome(rs.getString("nome"));
                    funcionario .setSetor(rs.getString("setor"));
                    funcionario .setEmail(rs.getString("email"));
                    funcionario .setTelefone(rs.getString("telefone"));
                    resultado.add(funcionario );
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    public Funcionario selectById(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectById)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Funcionario funcionario  = new Funcionario();
                funcionario .setId(rs.getInt("id"));
                funcionario .setNome(rs.getString("nome"));
                funcionario .setCpf(rs.getString("cpf"));
                funcionario .setEmail(rs.getString("email"));
                funcionario .setSetor(rs.getString("setor"));
                funcionario .setIdEmpresa(rs.getInt("id_empresa"));
                funcionario .setNomeEmpresa(rs.getString("empresa"));
                funcionario .setTelefone(rs.getString("telefone"));
                return funcionario ;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionario cionário.\n" + e.getMessage(), e);
        }
    }

    // Lista os registros para consultas administrativas.
    public List<Funcionario> selectAll() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario  = new Funcionario();
                funcionario .setId(rs.getInt("id"));
                funcionario .setCpf(rs.getString("cpf"));
                funcionario .setEmail(rs.getString("email"));
                funcionario .setIdEmpresa(rs.getInt("id_empresa"));
                funcionario .setNome(rs.getString("nome"));
                funcionario .setSetor(rs.getString("setor"));
                resultado.add(funcionario );
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> selectNome() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectNome);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario  = new Funcionario();
                funcionario .setNome(rs.getString("nome"));
                resultado.add(funcionario );
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> selectCpf() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectCpf);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario  = new Funcionario();
                funcionario .setSetor(rs.getString("cpf  "));
                resultado.add(funcionario );
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> selectTelefone() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectTelefone);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario  = new Funcionario();
                funcionario .setSetor(rs.getString("telefone"));
                resultado.add(funcionario );
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> selectSetor() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectSetor);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario  = new Funcionario();
                funcionario .setSetor(rs.getString("setor"));
                resultado.add(funcionario );
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    public List<Funcionario> selectEmail() {
        List<Funcionario> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectEmail);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario  = new Funcionario();
                funcionario .setEmail(rs.getString("email"));
                resultado.add(funcionario );
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // Exclui o registro identificado.
    public String delete(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoDeleteFuncionarioETelefone)){
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException sqle){
            throw new RuntimeException("Erro ao atualizar funcionario cionário.\n" + sqle.getMessage(), sqle);
        }
    }

    // Insere um novo registro.
    public String insertFuncionarioComTelefone(Funcionario funcionario , List<Empresa> empresas) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoInsertFuncionarioComTelefone)) {

            funcionario .setIdEmpresa(empresas.get(0).getId());

            ps.setString(1, funcionario .getNome());
            ps.setString(2, funcionario .getCpf());
            ps.setString(3, funcionario .getEmail());
            ps.setString(4, funcionario .getSetor());
            ps.setInt(5, funcionario .getIdEmpresa());
            ps.setString(6, funcionario .getTelefone());
            ps.executeUpdate();

            return "Funcionário e telefone inseridos com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir funcionario cionário.\n" + e.getMessage());
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

    public String updateEmail(Funcionario funcionario , String emailNew) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateEmail)) {
            ps.setString(1, emailNew);
            ps.setInt(2, funcionario .getId());
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateNome(Funcionario funcionario , String nomeNew) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateNome)) {
            ps.setString(1, nomeNew);
            ps.setInt(2, funcionario .getId());
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateSetor(Funcionario funcionario , String setorNew) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateSetor)) {
            ps.setString(1, setorNew);
            ps.setInt(2, funcionario .getId());
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateTelefone(Funcionario funcionario , String telefoneNew) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateTelefone)) {
            ps.setString(1, telefoneNew);
            ps.setInt(2, funcionario .getId());
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    public String updateCpf(Funcionario funcionario , String cpfNew) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateCpf)) {
            ps.setString(1, cpfNew);
            ps.setInt(2, funcionario .getId());
            ps.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

// Atualiza funcionário e telefone na mesma transação.
    public String updateFuncionarioComTelefone(Funcionario funcionario , int idEmpresa) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement ps1 = connection.prepareStatement(comandoUpdateAll);
                 PreparedStatement ps2 = connection.prepareStatement(comandoUpdateTelefone)) {

                ps1.setString(1, funcionario .getNome());
                ps1.setString(2, funcionario .getCpf());
                ps1.setString(3, funcionario.getEmail());
                ps1.setString(4, funcionario.getSetor());
                ps1.setInt(5, idEmpresa);
                ps1.setInt(6, funcionario.getId());
                ps1.executeUpdate();

                ps2.setString(1, funcionario.getTelefone());
                ps2.setInt(2, funcionario.getId());
                ps2.executeUpdate();

                connection.commit();
                return "Funcionário atualizado com sucesso";
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário.\n" + e.getMessage(), e);
        }
    }


    @Override
    public int contarPaginado() {
        return executarContagem(comandoSelectCountPaginado, null);
    }

    @Override
    public int contarPaginadoComBusca(String busca) {
        return executarContagem(comandoSelectCountComBusca, busca);
    }

    private int executarContagem(String sql, String busca) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            if (busca != null) {
                if (sql.equals(comandoSelectCountComBusca)) {
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
