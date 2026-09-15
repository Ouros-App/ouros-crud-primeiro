package primeirobd.DAO;

import primeirobd.model.EnderecoEmpresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoEmpresaDAO {

    private final ConexaoBancoPrimeiro conexaoBancoPrimeiro = new ConexaoBancoPrimeiro();


    // metodo select :D
    public List <EnderecoEmpresa> select(String comando){
        List <EnderecoEmpresa> informacoes = new ArrayList<>();
        try(Connection conexao = conexaoBancoPrimeiro.getConnection();
            PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
            ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while(resultadoConsulta.next()){
                EnderecoEmpresa emp = new EnderecoEmpresa();
                emp.setId(resultadoConsulta.getInt("id"));
                emp.setCep(resultadoConsulta.getString("cep"));
                emp.setEstado(resultadoConsulta.getString("estado"));
                emp.setIdEmpresa(resultadoConsulta.getInt("id_empresa"));
                emp.setLogradouro(resultadoConsulta.getString("logradouro"));
                emp.setMunicipio(resultadoConsulta.getString("municipio"));
                emp.setNumero(resultadoConsulta.getInt("numero"));
                informacoes.add(emp);
            }
            return informacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n"+ e.getMessage());
        }
    }

    // metodo delete :)
    public String delete (String comando){
        try(Connection conexao = conexaoBancoPrimeiro.getConnection();
            PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)){
            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n"+ e.getMessage());
        }
    }
    // metodo insert :O
    public String insert(String comando, EnderecoEmpresa emp){
        try (Connection conexao = conexaoBancoPrimeiro.getConnection();
             PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, emp.getId());
            preparoConsultaSQL.setString(2, emp.getCep());
            preparoConsultaSQL.setInt(3, emp.getNumero());
            preparoConsultaSQL.setString(4, emp.getMunicipio());
            preparoConsultaSQL.setString(5, emp.getEstado());
            preparoConsultaSQL.setString(6, emp.getLogradouro());
            preparoConsultaSQL.setInt(7, emp.getIdEmpresa());


            preparoConsultaSQL.executeUpdate();
            conexao.close();
            return "Item inserido com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n"+ e.getMessage());
        }
    }

    //metodo update :P
    public String update (String comando){
        try(Connection conexao = conexaoBancoPrimeiro.getConnection();
            PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)){
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n"+ e.getMessage());
        }
    }
}
