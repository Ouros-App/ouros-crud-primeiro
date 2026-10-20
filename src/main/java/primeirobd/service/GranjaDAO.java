package primeirobd.service;

import primeirobd.model.CGI;
import primeirobd.model.Granja;
import primeirobd.model.RegistroAgua;
import primeirobd.utils.CalculoCGI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GranjaDAO implements primeirobd.repository.GranjaDAO {
    public static final String SELECT_ALL = "SELECT id,area_propriedade, capacidade_aves,id_empresa,nome,regiao FROM granja";
    public static final String SELECT_NOME = "SELECT nome FROM granja";
    public static final String SELECT_ALL_JOIN_PAGINADO = "SELECT *, CONCAT(endereco_proprietario.municipio, ' ', '-', ' ', endereco_proprietario.estado) as localizacao, proprietario_granja.nome as responsavel FROM granja JOIN proprietario_granja ON granja.id = proprietario_granja.id_granja JOIN endereco_proprietario ON proprietario_granja.id = endereco_proprietario.id_proprietario;";
    public static final String SELECT_CGI = "SELECT registro_agua.hidrometro_inicio, registro_agua.hidrometro_final, lote.galinhas_entregadas, registro_energia.consumo FROM registro_agua JOIN lote ON registro_agua.id_lote = lote.id JOIN registro_energia ON registro_energia.id_lote = registro_agua.id_lote";
    public static final String SELECT_REGIAO = "SELECT regiao FROM granja";
    public static final String SELECT_CAPACIDADE = "SELECT capacidade_aves FROM granja";
    public static final String DELETE_BY_ID = "DELETE FROM granja where id = ?";
    public static final String INSERT = "INSERT INTO granja (nome,capacidade_aves,regiao,area_propriedade,id_empresa) values (?,?,?,?,?)";
    public static final String UPDATE_ID = "UPDATE granja SET id = ? WHERE id = ?";
    public static final String UPDATE_NOME = "UPDATE granja SET nome = ? WHERE nome = ?";
    public static final String UPDATE_CAPACIDADE = "UPDATE granja SET capacidade_aves = ? WHERE capacidade_aves = ?";
    public static final String UPDATE_REGIAO = "UPDATE granja SET regiao = ? where capacidade_aves = ?";
    public static final String UPDATE_AREA = "UPDATE granja SET area_propriedade = ? WHERE area_propriedade = ?";
    public static final String UPDATE_IDEMPRESA = "UPDATE granja SET id_empresa = ? WHERE id_empresa = ?";
    public static final String SELECT_COUNT = "SELECT COUNT(*) FROM granja";
    // a mesma coisa do outro select, mas esse tem offset ¬_¬
    public static final String SELECT_PAGINADO_JOIN =
            "SELECT granja.id, " +
                    "granja.area_propriedade, " +
                    "granja.capacidade_aves, " +
                    "granja.id_empresa, " +
                    "granja.nome, " +
                    "granja.regiao, " +
                    "CONCAT(endereco_proprietario.municipio, ' - ', endereco_proprietario.estado) AS localizacao, " +
                    "proprietario_granja.nome AS responsavel " +
                    "FROM granja " +
                    "JOIN proprietario_granja " +
                    "ON granja.id = proprietario_granja.id_granja " +
                    "JOIN endereco_proprietario " +
                    "ON proprietario_granja.id = endereco_proprietario.id_proprietario " +
                    "LIMIT ? OFFSET ?";


    // metodo de continhas (ﾉ´ヮ´)ﾉ
    public int contar(){
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try(PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_COUNT);
        ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()){
            if (resultadoConsulta.next()) {
                return resultadoConsulta.getInt(1);
            }
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao contar. \n"+e.getMessage());
        }
    }

    // metodo do select soq paginado uau

    public List<Granja> select_paginado(int tamanho, int offset) {

        List<Granja> resultado = new ArrayList<>();

        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_PAGINADO_JOIN)) {

            preparoConsultaSQL.setInt(1, tamanho);
            preparoConsultaSQL.setInt(2, offset);

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
                while (resultadoConsulta.next()) {
                    Granja gra = new Granja();
                    gra.setId(resultadoConsulta.getInt("id"));
                    gra.setAreaPropriedade(resultadoConsulta.getInt("area_propriedade"));
                    gra.setCapacidadeDeAves(resultadoConsulta.getInt("capacidade_aves"));
                    gra.setIdEmpresa(resultadoConsulta.getInt("id_empresa"));
                    gra.setNome(resultadoConsulta.getString("nome"));
                    gra.setRegiao(resultadoConsulta.getString("regiao"));
                    gra.setLocalizacao(resultadoConsulta.getString("localizacao"));
                    gra.setNomeResponsavel(resultadoConsulta.getString("responsavel"));
                    resultado.add(gra);
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao mostrar informações paginadas.\n" + e.getMessage());
        }
    }

    // metodo select :D
    public List<Granja> select_all() {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Granja gra = new Granja();

                gra.setId(resultadoConsulta.getInt("id"));
                gra.setAreaPropriedade(resultadoConsulta.getInt("area_propriedade"));
                gra.setCapacidadeDeAves(resultadoConsulta.getInt("capacidade_aves"));
                gra.setIdEmpresa(resultadoConsulta.getInt("id_empresa"));
                gra.setNome(resultadoConsulta.getString("nome"));
                gra.setRegiao(resultadoConsulta.getString("regiao"));

                resultado.add(gra);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("\n\nErro ao tentar: \n" + e.getMessage());
        }
    }

    public List<Granja> select_all_join_paginado(){
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        int contador = 0;
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_ALL_JOIN_PAGINADO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
            while (resultadoConsulta.next()) {
                Granja gra = new Granja();
                int id = resultadoConsulta.getInt("id");
                gra.setId(resultadoConsulta.getInt("id"));
                gra.setAreaPropriedade(resultadoConsulta.getInt("area_propriedade"));
                gra.setCapacidadeDeAves(resultadoConsulta.getInt("capacidade_aves"));
                gra.setIdEmpresa(resultadoConsulta.getInt("id_empresa"));
                gra.setNome(resultadoConsulta.getString("nome"));
                gra.setRegiao(resultadoConsulta.getString("regiao"));
                gra.setLocalizacao(resultadoConsulta.getString("localizacao"));
                gra.setNomeResponsavel(resultadoConsulta.getString("responsavel"));
                gra.setCgi(CalculoCGI.calcularCGI(gra, select_cgi(), contador));
                contador++;
                resultado.add(gra);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("\n\nErro ao tentar: \n" + e.getMessage());
        }
    }

    public List<CGI> select_cgi() {
        List<CGI> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_CGI);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
            while (resultadoConsulta.next()) {
                CGI cgi = new CGI();
                cgi.setHidrometroInicio(resultadoConsulta.getDouble("hidrometro_inicio"));
                cgi.setHidrometroFinal(resultadoConsulta.getDouble("hidrometro_final"));
                cgi.setGalinhasEntregadas(resultadoConsulta.getInt("galinhas_entregadas"));
                cgi.setConsumoEnergetico(resultadoConsulta.getDouble("consumo"));
                resultado.add(cgi);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }

    public List<Granja> select_nome() {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_NOME);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Granja gra = new Granja();
                gra.setNome(resultadoConsulta.getString("nome"));
                resultado.add(gra);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }

    public List<Granja> select_regiao() {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_REGIAO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Granja gra = new Granja();

                gra.setRegiao(resultadoConsulta.getString("regiao"));

                resultado.add(gra);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }

    public List<Granja> select_capacidade() {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_CAPACIDADE);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                Granja gra = new Granja();

                gra.setCapacidadeDeAves(resultadoConsulta.getInt("capacidade_aves"));

                resultado.add(gra);
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
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
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }

    // metodo insert :O
    public String insert(Granja gra){
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(INSERT)) {
            preparoConsultaSQL.setString(1, gra.getNome());
            preparoConsultaSQL.setInt(2, gra.getCapacidadeDeAves());
            preparoConsultaSQL.setString(3, gra.getRegiao());
            preparoConsultaSQL.setInt(4, gra.getAreaPropriedade());
            preparoConsultaSQL.setInt(5, gra.getIdEmpresa());
            preparoConsultaSQL.executeUpdate();
            return "Item inserido com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }

    // metodo update :P
    public String update_id(int idNew,int idOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ID)) {
            preparoConsultaSQL.setInt(1, idNew);
            preparoConsultaSQL.setInt(2, idOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
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
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }

    public String update_capacidade(String capacidadeNew, String capacidadeOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_CAPACIDADE)) {
            preparoConsultaSQL.setString(1, capacidadeNew);
            preparoConsultaSQL.setString(2, capacidadeOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }

    public String update_regiao(String regiaoNew, String regiaoOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_REGIAO)) {
            preparoConsultaSQL.setString(1, regiaoNew);
            preparoConsultaSQL.setString(2, regiaoOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }

    public String update_area(int areaNew, int areaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_AREA)) {
            preparoConsultaSQL.setInt(1, areaNew);
            preparoConsultaSQL.setInt(2, areaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }

    public String update_idEmpresa(int idEmpresaNew, int idEmpresaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_IDEMPRESA)) {
            preparoConsultaSQL.setInt(1, idEmpresaNew);
            preparoConsultaSQL.setInt(2, idEmpresaOld);
            preparoConsultaSQL.execute();
            return "Item atualizado com sucesso no banco de dados";
        } catch (SQLException e) {
            throw new RuntimeException("\nErro ao tentar: \n" + e.getMessage());
        }
    }
}

