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

    public static final String comandoSelectAll =
            "SELECT id, nome, telefone_corporativo, email_corporativo, cnpj FROM empresa ORDER BY id";

    public static final String comandoSelectNome = "SELECT id, nome, telefone_corporativo, email_corporativo, cnpj FROM empresa WHERE nome LIKE ? ORDER BY id";

    public static final String comandoDeleteById =
            "DELETE FROM empresa WHERE id = ?";

    public static final String comandoInsert =
            "INSERT INTO empresa (nome, telefone_corporativo, email_corporativo, cnpj) VALUES (?,?,?,?)";

    public static final String comandoUpdateAll =
            "UPDATE empresa SET nome = ?, telefone_corporativo = ?, email_corporativo = ?, cnpj = ? WHERE id = ?";

    public static final String comandoExistsByCnpj =
            "SELECT 1 FROM empresa WHERE cnpj = ?";

    public static final String comandoSelectCount =
            "SELECT COUNT(*) FROM empresa";

    public static final String comandoSelectCountBusca =
            "SELECT COUNT(*) FROM empresa " +
                    "WHERE nome ILIKE ? OR telefone_corporativo ILIKE ? " +
                    "OR email_corporativo ILIKE ? OR cnpj ILIKE ?";

    // ORDER BY garante que a ordem das páginas seja sempre a mesma.
    public static final String comandoSelectPaginado =
            "SELECT id, nome, telefone_corporativo, email_corporativo, cnpj " +
                    "FROM empresa " +
                    "ORDER BY id " +
                    "LIMIT ? OFFSET ?";

    public static final String comandoSelectPaginadoComBusca =
            "SELECT id, nome, telefone_corporativo, email_corporativo, cnpj " +
                    "FROM empresa " +
                    "WHERE nome ILIKE ? OR telefone_corporativo ILIKE ? " +
                    "OR email_corporativo ILIKE ? OR cnpj ILIKE ? " +
                    "ORDER BY id " +
                    "LIMIT ? OFFSET ?";

// Conta registros para que a camada de apresentação monte as páginas.
    public int contar() {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(comandoSelectCount);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar empresas.\n" + e.getMessage(), e);
        }
    }

    public int contarBusca(String busca) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectCountBusca)) {
            ps.setString(1, busca);
            ps.setString(2, busca);
            ps.setString(3, busca);
            ps.setString(4, busca);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar empresas filtradas.\n" + e.getMessage(), e);
        }
    }

// LIMIT e OFFSET recebem valores saneados para evitar paginação inválida.
    public List<Empresa> selectPaginado(int tamanho, int offset) {
        List<Empresa> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectPaginado)) {
            ps.setInt(1, Math.max(1, tamanho));
            ps.setInt(2, Math.max(0, offset));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setNome(rs.getString("nome"));
                    empresa.setTelefoneCorporativo(rs.getString("telefone_corporativo"));
                    empresa.setEmailCorporativo(rs.getString("email_corporativo"));
                    empresa.setCnpj(rs.getString("cnpj"));
                    resultado.add(empresa);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar empresas paginadas.\n" + e.getMessage(), e);
        }
    }

    public List<Empresa> selectPaginadoComBusca(String busca, int tamanho, int offset) {
        List<Empresa> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectPaginadoComBusca)) {
            ps.setString(1, busca);
            ps.setString(2, busca);
            ps.setString(3, busca);
            ps.setString(4, busca);
            ps.setInt(5, Math.max(1, tamanho));
            ps.setInt(6, Math.max(0, offset));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setNome(rs.getString("nome"));
                    empresa.setTelefoneCorporativo(rs.getString("telefone_corporativo"));
                    empresa.setEmailCorporativo(rs.getString("email_corporativo"));
                    empresa.setCnpj(rs.getString("cnpj"));
                    resultado.add(empresa);
                }
            }
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao pesquisar empresas paginadas.\n" + e.getMessage(), e);
        }
    }

    public List<Empresa> selectAll() {
        List<Empresa> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setTelefoneCorporativo(rs.getString("telefone_corporativo"));
                empresa.setEmailCorporativo(rs.getString("email_corporativo"));
                empresa.setCnpj(rs.getString("cnpj"));
                resultado.add(empresa);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar empresas.\n" + e.getMessage(), e);
        }
    }

    public boolean selectExistsEmpresa(String nomeEmpresa){
        List<Empresa> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectNome)) {
            ps.setString(1, nomeEmpresa);

            try(ResultSet rs = ps.executeQuery()){
                if(!rs.wasNull()){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar empresas.\n" + e.getMessage(), e);
        }
    }

    public List<Empresa> selectEmpresaByNome(String nomeEmpresa) {
        List<Empresa> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectNome)) {
            ps.setString(1, nomeEmpresa);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setId(rs.getInt("id"));
                    empresa.setNome(rs.getString("nome"));
                    empresa.setTelefoneCorporativo(rs.getString("telefone_corporativo"));
                    empresa.setEmailCorporativo(rs.getString("email_corporativo"));
                    empresa.setCnpj(rs.getString("cnpj"));
                    resultado.add(empresa);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar empresas.\n" + e.getMessage(), e);
        }
    }

    public List<Empresa> selectId(String nomeEmpresa) {
        List<Empresa> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectNome)) {
            ps.setString(1, nomeEmpresa);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setId(rs.getInt("id"));
                    resultado.add(empresa);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar empresas.\n" + e.getMessage(), e);
        }
    }

//    public boolean existsByCnpj(String cnpj) {
//        Connection connection = ConexaoBancoPrimeiro.getConnection();
//
//        try (PreparedStatement ps = connection.prepareStatement(comandoExistsByCnpj)) {
//            ps.setString(1, cnpj);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Erro ao verificar CNPJ existente.\n" + e.getMessage(), e);
//        }
//    }

    public String insert(Empresa empresa) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoInsert)) {
            ps.setString(1, empresa.getNome());
            ps.setString(2, empresa.getTelefoneCorporativo());
            ps.setString(3, empresa.getEmailCorporativo());
            ps.setString(4, empresa.getCnpj());
            ps.executeUpdate();
            return "Item inserido com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir empresa.\n" + e.getMessage(), e);
        }
    }

    public String updateAll(Empresa empresa) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateAll)) {
            ps.setString(1, empresa.getNome());
            ps.setString(2, empresa.getTelefoneCorporativo());
            ps.setString(3, empresa.getEmailCorporativo());
            ps.setString(4, empresa.getCnpj());
            ps.setInt(5, empresa.getId());
            ps.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar empresa.\n" + e.getMessage(), e);
        }
    }

    public String delete(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoDeleteById)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return "Item apagado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar empresa.\n" + e.getMessage(), e);
        }
    }
}
