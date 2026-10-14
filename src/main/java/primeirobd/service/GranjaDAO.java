package primeirobd.service;

import primeirobd.model.CGI;
import primeirobd.model.Granja;
import primeirobd.utils.CalculoCGI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GranjaDAO implements primeirobd.repository.GranjaDAO {

    public static final String SELECT_ALL =
            "SELECT id, area_propriedade, capacidade_aves, id_empresa, nome, regiao FROM granja";

    public static final String SELECT_NOME =
            "SELECT nome FROM granja";

    public static final String SELECT_ALL_JOIN_PAGINADO =
            "SELECT *, " +
                    "CONCAT(endereco_proprietario.municipio, ' - ', endereco_proprietario.estado) AS localizacao, " +
                    "proprietario_granja.nome AS responsavel " +
                    "FROM granja " +
                    "JOIN proprietario_granja ON granja.id = proprietario_granja.id_granja " +
                    "JOIN endereco_proprietario ON proprietario_granja.id = endereco_proprietario.id_proprietario";

    public static final String SELECT_CGI =
            "SELECT registro_agua.hidrometro_inicio, " +
                    "registro_agua.hidrometro_final, lote.galinhas_entregadas, " +
                    "registro_energia.consumo " +
                    "FROM registro_agua " +
                    "JOIN lote ON registro_agua.id_lote = lote.id " +
                    "JOIN registro_energia ON registro_energia.id_lote = registro_agua.id_lote";

    public static final String SELECT_REGIAO =
            "SELECT regiao FROM granja";

    public static final String SELECT_CAPACIDADE =
            "SELECT capacidade_aves FROM granja";

    public static final String DELETE_BY_ID =
            "DELETE FROM granja WHERE id = ?";

    public static final String INSERT =
            "INSERT INTO granja (nome, capacidade_aves, regiao, area_propriedade, id_empresa) VALUES (?,?,?,?,?)";

    public static final String UPDATE_ID =
            "UPDATE granja SET id = ? WHERE id = ?";

    public static final String UPDATE_NOME =
            "UPDATE granja SET nome = ? WHERE nome = ?";

    public static final String UPDATE_CAPACIDADE =
            "UPDATE granja SET capacidade_aves = ? WHERE capacidade_aves = ?";

    // Corrigido: o valor antigo precisa ser comparado com a coluna regiao.
    public static final String UPDATE_REGIAO =
            "UPDATE granja SET regiao = ? WHERE regiao = ?";

    public static final String UPDATE_AREA =
            "UPDATE granja SET area_propriedade = ? WHERE area_propriedade = ?";

    public static final String UPDATE_IDEMPRESA =
            "UPDATE granja SET id_empresa = ? WHERE id_empresa = ?";

    public static final String SELECT_COUNT =
            "SELECT COUNT(*) FROM granja";

    public static final String SELECT_PAGINADO =
            "SELECT * FROM granja LIMIT ? OFFSET ?";

    public static final String SELECT_COUNT_FILTRADO =
            "SELECT COUNT(*) " +
                    "FROM granja g " +
                    "LEFT JOIN LATERAL ( " +
                    "   SELECT pg.nome AS responsavel, ep.municipio, ep.estado " +
                    "   FROM proprietario_granja pg " +
                    "   LEFT JOIN endereco_proprietario ep ON ep.id_proprietario = pg.id " +
                    "   WHERE pg.id_granja = g.id " +
                    "   ORDER BY pg.id " +
                    "   LIMIT 1 " +
                    ") dados ON TRUE " +
                    "WHERE (? = '' OR LOWER(COALESCE(g.nome, '')) LIKE LOWER(?)) " +
                    "AND (? = '' OR LOWER(CONCAT_WS(' ', COALESCE(dados.municipio, ''), " +
                    "COALESCE(dados.estado, ''))) LIKE LOWER(?))";

    public static final String SELECT_PAGINADO_JOIN =
            "WITH consumo_por_lote AS ( " +
                    "   SELECT l.id AS id_lote, l.id_granja, l.galinhas_entregadas, " +
                    "          AVG(ra.hidrometro_final - ra.hidrometro_inicio) AS consumo_agua, " +
                    "          AVG(re.consumo) AS consumo_energia " +
                    "   FROM lote l " +
                    "   LEFT JOIN registro_agua ra ON ra.id_lote = l.id " +
                    "   LEFT JOIN registro_energia re ON re.id_lote = l.id " +
                    "   WHERE l.galinhas_entregadas > 0 " +
                    "   GROUP BY l.id, l.id_granja, l.galinhas_entregadas " +
                    "), cgi_por_granja AS ( " +
                    "   SELECT id_granja, " +
                    "          AVG(((COALESCE(consumo_agua, 0) / NULLIF(galinhas_entregadas, 0)) * 0.7 " +
                    "          + (COALESCE(consumo_energia, 0) / NULLIF(galinhas_entregadas, 0)) * 0.3) * 1000) AS cgi " +
                    "   FROM consumo_por_lote " +
                    "   GROUP BY id_granja " +
                    ") " +
                    "SELECT g.id, g.area_propriedade, g.capacidade_aves, g.id_empresa, " +
                    "       g.nome, g.regiao, " +
                    "       CONCAT_WS(' - ', NULLIF(dados.municipio, ''), NULLIF(dados.estado, '')) AS localizacao, " +
                    "       dados.responsavel AS responsavel, COALESCE(cgi.cgi, 0) AS cgi " +
                    "FROM granja g " +
                    "LEFT JOIN LATERAL ( " +
                    "   SELECT pg.nome AS responsavel, ep.municipio, ep.estado " +
                    "   FROM proprietario_granja pg " +
                    "   LEFT JOIN endereco_proprietario ep ON ep.id_proprietario = pg.id " +
                    "   WHERE pg.id_granja = g.id " +
                    "   ORDER BY pg.id " +
                    "   LIMIT 1 " +
                    ") dados ON TRUE " +
                    "LEFT JOIN cgi_por_granja cgi ON cgi.id_granja = g.id " +
                    "WHERE (? = '' OR LOWER(COALESCE(g.nome, '')) LIKE LOWER(?)) " +
                    "AND (? = '' OR LOWER(CONCAT_WS(' ', COALESCE(dados.municipio, ''), " +
                    "COALESCE(dados.estado, ''))) LIKE LOWER(?)) " +
                    "ORDER BY %s ASC, g.id ASC LIMIT ? OFFSET ?";


    // Conta todas as granjas, mantendo o metodo antigo.
    public int contar() {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(SELECT_COUNT);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

            if (resultadoConsulta.next()) {
                return resultadoConsulta.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Ocorreu um erro ao contar granjas.\n" + e.getMessage(), e
            );
        }
    }


    // Conta somente as granjas que correspondem à busca e ao filtro.
    public int contar(String busca, String localizacao) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        busca = busca == null ? "" : busca.trim();
        localizacao = localizacao == null ? "" : localizacao.trim();

        try (PreparedStatement preparoConsultaSQL =
                     conexao.prepareStatement(SELECT_COUNT_FILTRADO)) {

            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setString(2, "%" + busca + "%");
            preparoConsultaSQL.setString(3, localizacao);
            preparoConsultaSQL.setString(4, "%" + localizacao + "%");

            try (ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {
                if (resultadoConsulta.next()) {
                    return resultadoConsulta.getInt(1);
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Ocorreu um erro ao contar granjas filtradas.\n" + e.getMessage(), e
            );
        }
    }


    // Metodo antigo, mantido para não quebrar chamadas existentes.
    public List<Granja> select_paginado(int tamanho, int offset) {
        return select_paginado(tamanho, offset, "", "", "granja");
    }


    // Paginação integrada à busca, à localização e à ordenação.
    public List<Granja> select_paginado(
            int tamanho,
            int offset,
            String busca,
            String localizacao,
            String ordenarPor) {

        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        busca = busca == null ? "" : busca.trim();
        localizacao = localizacao == null ? "" : localizacao.trim();

        // Nunca inserir diretamente um parâmetro do usuário no ORDER BY.
        String colunaOrdenacao;
        if ("capacidade".equalsIgnoreCase(ordenarPor)) {
            colunaOrdenacao = "g.capacidade_aves";
        } else if ("sustentabilidade".equalsIgnoreCase(ordenarPor)) {
            colunaOrdenacao = "COALESCE(cgi.cgi, 0)";
        } else {
            colunaOrdenacao = "g.nome";
        }

        String sql = String.format(SELECT_PAGINADO_JOIN, colunaOrdenacao);

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(sql)) {

            preparoConsultaSQL.setString(1, busca);
            preparoConsultaSQL.setString(2, "%" + busca + "%");
            preparoConsultaSQL.setString(3, localizacao);
            preparoConsultaSQL.setString(4, "%" + localizacao + "%");
            preparoConsultaSQL.setInt(5, Math.max(1, tamanho));
            preparoConsultaSQL.setInt(6, Math.max(0, offset));

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
                    gra.setCgi(resultadoConsulta.getDouble("cgi"));

                    resultado.add(gra);
                }
            }

            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Ocorreu um erro ao mostrar granjas paginadas.\n" + e.getMessage(), e
            );
        }
    }


    // Select de todas as granjas sem os dados relacionados.
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
            throw new RuntimeException(
                    "Erro ao tentar mostrar informações do banco de dados.\n" + e.getMessage(), e
            );
        }
    }


    public List<Granja> select_all_join_paginado() {
        List<Granja> resultado = new ArrayList<>();
        Connection conexao = ConexaoBancoPrimeiro.getConnection();
        List<CGI> informacoesCGI = select_cgi();
        int contador = 0;

        try (PreparedStatement preparoConsultaSQL =
                     conexao.prepareStatement(SELECT_ALL_JOIN_PAGINADO);
             ResultSet resultadoConsulta = preparoConsultaSQL.executeQuery()) {

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

                // Preserva o cálculo antigo sem acessar posição inexistente.
                if (contador < informacoesCGI.size()
                        && informacoesCGI.get(contador).getGalinhasEntregadas() > 0) {
                    gra.setCgi(CalculoCGI.calcularCGI(gra, informacoesCGI, contador));
                }

                contador++;
                resultado.add(gra);
            }

            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao tentar mostrar granjas com relacionamentos.\n" + e.getMessage(), e
            );
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
            throw new RuntimeException(
                    "Erro ao buscar dados para o CGI.\n" + e.getMessage(), e
            );
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
            throw new RuntimeException("Erro ao consultar nomes de granjas.\n" + e.getMessage(), e);
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
            throw new RuntimeException("Erro ao consultar regiões de granjas.\n" + e.getMessage(), e);
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
            throw new RuntimeException("Erro ao consultar capacidade das granjas.\n" + e.getMessage(), e);
        }
    }


    // Metodo delete.
    public String delete(int id) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(DELETE_BY_ID)) {
            preparoConsultaSQL.setInt(1, id);
            preparoConsultaSQL.executeUpdate();
            return "Item apagado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir granja.\n" + e.getMessage(), e);
        }
    }


    // Metodo insert.
    public String insert(Granja gra) {
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
            throw new RuntimeException("Erro ao inserir granja.\n" + e.getMessage(), e);
        }
    }


    // Metodos update.
    public String update_id(int idNew, int idOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_ID)) {
            preparoConsultaSQL.setInt(1, idNew);
            preparoConsultaSQL.setInt(2, idOld);
            preparoConsultaSQL.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ID da granja.\n" + e.getMessage(), e);
        }
    }


    public String update_nome(String nomeNew, String nomeOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_NOME)) {
            preparoConsultaSQL.setString(1, nomeNew);
            preparoConsultaSQL.setString(2, nomeOld);
            preparoConsultaSQL.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar nome da granja.\n" + e.getMessage(), e);
        }
    }


    public String update_capacidade(String capacidadeNew, String capacidadeOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_CAPACIDADE)) {
            preparoConsultaSQL.setString(1, capacidadeNew);
            preparoConsultaSQL.setString(2, capacidadeOld);
            preparoConsultaSQL.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar capacidade da granja.\n" + e.getMessage(), e);
        }
    }


    public String update_regiao(String regiaoNew, String regiaoOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_REGIAO)) {
            preparoConsultaSQL.setString(1, regiaoNew);
            preparoConsultaSQL.setString(2, regiaoOld);
            preparoConsultaSQL.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar região da granja.\n" + e.getMessage(), e);
        }
    }


    public String update_area(int areaNew, int areaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_AREA)) {
            preparoConsultaSQL.setInt(1, areaNew);
            preparoConsultaSQL.setInt(2, areaOld);
            preparoConsultaSQL.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar área da granja.\n" + e.getMessage(), e);
        }
    }


    public String update_idEmpresa(int idEmpresaNew, int idEmpresaOld) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement preparoConsultaSQL = conexao.prepareStatement(UPDATE_IDEMPRESA)) {
            preparoConsultaSQL.setInt(1, idEmpresaNew);
            preparoConsultaSQL.setInt(2, idEmpresaOld);
            preparoConsultaSQL.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar empresa da granja.\n" + e.getMessage(), e);
        }
    }
}
