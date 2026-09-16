package primeirobd.DAO;

import primeirobd.model.Meta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetaDAO {

    // metodo select :D
    public List<Meta> select(String comando) {
        List<Meta> informacoes = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando);
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
                met.setValorAlvo(resultadoConsulta.getFloat("valor_alvo"));
                informacoes.add(met);
            }
            return informacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar mostrar informacoes do banco de dados.\n" + e.getMessage());
        }
    }

    // metodo delete :)
    public String delete(String comando) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.execute();
            return "Item apagado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar deletar informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(String comando, Meta met) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.setInt(1, met.getId());
            preparoConsultaSQL.setString(2, met.getTitulo());
            preparoConsultaSQL.setString(3, met.getDescricao());
            preparoConsultaSQL.setString(4, met.getTipoMeta());
            preparoConsultaSQL.setString(5, met.getStatus());
            preparoConsultaSQL.setFloat(6, met.getValorAlvo());
            preparoConsultaSQL.setString(7, met.getRegiao());
            preparoConsultaSQL.setString(8, met.getEstado());
            preparoConsultaSQL.setBoolean(9, met.isIndividual());
            preparoConsultaSQL.setInt(10, met.getIdGranja());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar inserir informacoes no banco de dados.\n" + e.getMessage());
        }
    }

    // metodo update :P
    public String update(String comando) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(comando)) {

            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao tentar atualizar informacoes no banco de dados.\n" + e.getMessage());
        }
    }
}