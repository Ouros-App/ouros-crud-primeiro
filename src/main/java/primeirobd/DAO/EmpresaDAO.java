package primeirobd.DAO;

import primeirobd.model.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    private final ConexaoBancoPrimeiro conexaoBancoPrimeiro = new ConexaoBancoPrimeiro();

    // metodo select :D
    public List<Empresa> select(String comando) {
        List<Empresa> resultado = new ArrayList<>();
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Empresa emp = new Empresa();
                emp.setId(resultadoConsulta.getInt("id"));
                emp.setCnpj(resultadoConsulta.getString("cnpj"));
                emp.setEmailCorporativo(resultadoConsulta.getString("email_corporativo"));
                emp.setNome(resultadoConsulta.getString("nome"));
                emp.setTelefoneCorporativo(resultadoConsulta.getString("telefone_corporativo"));
                resultado.add(emp);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // metodo delete :)
    public String delete(String comando) {
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(String comando, Empresa emp) {
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, emp.getId());
            preparoConsultaSQL.setString(2, emp.getCnpj());
            preparoConsultaSQL.setString(3, emp.getEmailCorporativo());
            preparoConsultaSQL.setString(4, emp.getNome());
            preparoConsultaSQL.setString(5, emp.getTelefoneCorporativo());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo update :P
    public String update(String comando) {
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}