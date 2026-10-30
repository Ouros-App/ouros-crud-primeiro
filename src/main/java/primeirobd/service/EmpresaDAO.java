package primeirobd.service;

import primeirobd.model.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Executa as operações de empresas e suas consultas paginadas.
public class EmpresaDAO implements primeirobd.repository.EmpresaDAO {

    public static final String SELECT_ALL =
            "SELECT id, nome, telefone_corporativo, email_corporativo, cnpj FROM empresa ORDER BY id";

    public static final String SELECT_NOME = "SELECT id, nome, telefone_corporativo, email_corporativo, cnpj FROM empresa WHERE nome LIKE ? ORDER BY id";

    public static final String DELETE_BY_ID =
            "DELETE FROM empresa WHERE id = ?";

    public static final String INSERT =
            "INSERT INTO empresa (nome, telefone_corporativo, email_corporativo, cnpj) VALUES (?,?,?,?)";

    public static final String UPDATE =
            "UPDATE empresa SET nome = ?, telefone_corporativo = ?, email_corporativo = ?, cnpj = ? WHERE id = ?";

    public static final String EXISTS_BY_CNPJ =
            "SELECT 1 FROM empresa WHERE cnpj = ?";

    public static final String SELECT_COUNT =
            "SELECT COUNT(*) FROM empresa";

    public static final String SELECT_COUNT_FILTRO =
            "SELECT COUNT(*) FROM empresa " +
                    "WHERE nome ILIKE ? OR telefone_corporativo ILIKE ? " +
                    "OR email_corporativo ILIKE ? OR cnpj ILIKE ?";

    // ORDER BY garante que a ordem das páginas seja sempre a mesma.
    public static final String SELECT_PAGINADO =
            "SELECT id, nome, telefone_corporativo, email_corporativo, cnpj " +
                    "FROM empresa " +
                    "ORDER BY id " +
                    "LIMIT ? OFFSET ?";

    public static final String SELECT_PAGINADO_FILTRO_PESQUISA =
            "SELECT id, nome, telefone_corporativo, email_corporativo, cnpj " +
                    "FROM empresa " +
                    "WHERE nome ILIKE ? OR telefone_corporativo ILIKE ? " +
                    "OR email_corporativo ILIKE ? OR cnpj ILIKE ? " +
                    "ORDER BY id " +
                    "LIMIT ? OFFSET ?";

// Conta registros para que a camada de apresentação monte as páginas.
    public int contar() {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_COUNT);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            if (resultadoConsulta.next()) {
                return resultadoConsulta.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar empresas.\n" + e.getMessage(), e);
        }
    }

    public int contar_filtro_pesquisa(String busca) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_COUNT_FILTRO)) {
            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setString(2, busca);
            preparoConsultaSQL.setString(3, busca);
            preparoConsultaSQL.setString(4, busca);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
                if (resultadoConsulta.next()) {
                    return resultadoConsulta.getInt(1);
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar empresas filtradas.\n" + e.getMessage(), e);
        }
    }

// LIMIT e OFFSET recebem valores saneados para evitar paginação inválida.
    public List<Empresa> select_paginado(int tamanho, int offset) {
        List<Empresa> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO)) {
            preparoConsultaSQL.setInt(1, Math.max(1, tamanho));
            preparoConsultaSQL.setInt(2, Math.max(0, offset));

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
                while (resultadoConsulta.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setNome(resultadoConsulta.getString("nome"));
                    empresa.setTelefoneCorporativo(resultadoConsulta.getString("telefone_corporativo"));
                    empresa.setEmailCorporativo(resultadoConsulta.getString("email_corporativo"));
                    empresa.setCnpj(resultadoConsulta.getString("cnpj"));
                    resultado.add(empresa);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar empresas paginadas.\n" + e.getMessage(), e);
        }
    }

    public List<Empresa> select_paginado_filtro_pesquisa(String busca, int tamanho, int offset) {
        List<Empresa> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO_FILTRO_PESQUISA)) {
            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setString(2, busca);
            preparoConsultaSQL.setString(3, busca);
            preparoConsultaSQL.setString(4, busca);
            preparoConsultaSQL.setInt(5, Math.max(1, tamanho));
            preparoConsultaSQL.setInt(6, Math.max(0, offset));

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
                while (resultadoConsulta.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setNome(resultadoConsulta.getString("nome"));
                    empresa.setTelefoneCorporativo(resultadoConsulta.getString("telefone_corporativo"));
                    empresa.setEmailCorporativo(resultadoConsulta.getString("email_corporativo"));
                    empresa.setCnpj(resultadoConsulta.getString("cnpj"));
                    resultado.add(empresa);
                }
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao pesquisar empresas paginadas.\n" + e.getMessage(), e);
        }
    }

    public List<Empresa> select_all() {
        List<Empresa> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(resultadoConsulta.getInt("id"));
                empresa.setNome(resultadoConsulta.getString("nome"));
                empresa.setTelefoneCorporativo(resultadoConsulta.getString("telefone_corporativo"));
                empresa.setEmailCorporativo(resultadoConsulta.getString("email_corporativo"));
                empresa.setCnpj(resultadoConsulta.getString("cnpj"));
                resultado.add(empresa);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar empresas.\n" + e.getMessage(), e);
        }
    }

    public boolean select_exists_empresa(String nomeEmpresa){
        List<Empresa> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_NOME)) {
            preparoConsultaSQL.setString(1, nomeEmpresa);

            try(ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()){
                if(!resultadoConsulta.wasNull()){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar empresas.\n" + e.getMessage(), e);
        }
    }

    public List<Empresa> select_empresa_by_nome(String nomeEmpresa) {
        List<Empresa> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_NOME)) {
            preparoConsultaSQL.setString(1, nomeEmpresa);

            try(ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()){
                while (resultadoConsulta.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setId(resultadoConsulta.getInt("id"));
                    empresa.setNome(resultadoConsulta.getString("nome"));
                    empresa.setTelefoneCorporativo(resultadoConsulta.getString("telefone_corporativo"));
                    empresa.setEmailCorporativo(resultadoConsulta.getString("email_corporativo"));
                    empresa.setCnpj(resultadoConsulta.getString("cnpj"));
                    resultado.add(empresa);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar empresas.\n" + e.getMessage(), e);
        }
    }

    public List<Empresa> select_id(String nomeEmpresa) {
        List<Empresa> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_NOME)) {
            preparoConsultaSQL.setString(1, nomeEmpresa);

            try(ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()){
                while (resultadoConsulta.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setId(resultadoConsulta.getInt("id"));
                    resultado.add(empresa);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar empresas.\n" + e.getMessage(), e);
        }
    }

//    public boolean existsByCnpj(String cnpj) {
//        Connection conexao = ConexaoBancoPrimeiro.getConnection();
//
//        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(EXISTS_BY_CNPJ)) {
//            preparoConsultaSQL.setString(1, cnpj);
//
//            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
//                return resultadoConsulta.next();
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Erro ao verificar CNPJ existente.\n" + e.getMessage(), e);
//        }
//    }

    public String insert(Empresa emp) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {
            preparoConsultaSQL.setString(1, emp.getNome());
            preparoConsultaSQL.setString(2, emp.getTelefoneCorporativo());
            preparoConsultaSQL.setString(3, emp.getEmailCorporativo());
            preparoConsultaSQL.setString(4, emp.getCnpj());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir empresa.\n" + e.getMessage(), e);
        }
    }

    public String update(Empresa emp) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE)) {
            preparoConsultaSQL.setString(1, emp.getNome());
            preparoConsultaSQL.setString(2, emp.getTelefoneCorporativo());
            preparoConsultaSQL.setString(3, emp.getEmailCorporativo());
            preparoConsultaSQL.setString(4, emp.getCnpj());
            preparoConsultaSQL.setInt(5, emp.getId());
            preparoConsultaSQL.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar empresa.\n" + e.getMessage(), e);
        }
    }

    public String delete(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(DELETE_BY_ID)) {
            preparoConsultaSQL.setInt(1, id);
            preparoConsultaSQL.executeUpdate();
            return "Item apagado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar empresa.\n" + e.getMessage(), e);
        }
    }
}
