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

// Reúne dados de granjas, endereços, responsáveis e indicadores.
public class GranjaDAO implements primeirobd.repository.GranjaDAO {

    public static final String comandoSelectAll =
            "SELECT id, area_propriedade, capacidade_aves, id_empresa, nome, regiao FROM granja";

    public static final String comandoSelectNome =
            "SELECT nome FROM granja";

    public static final String comandoSelectCgi =
            "SELECT registro_agua.hidrometro_inicio, " +
                    "registro_agua.hidrometro_final, lote.galinhas_entregadas, " +
                    "registro_energia.consumo " +
                    "FROM registro_agua " +
                    "JOIN lote ON registro_agua.id_lote = lote.id " +
                    "JOIN registro_energia ON registro_energia.id_lote = registro_agua.id_lote";

    public static final String comandoSelectRegiao =
            "SELECT regiao FROM granja";

    public static final String comandoSelectCapacidade =
            "SELECT capacidade_aves FROM granja";

    public static final String comandoInsertGranjaComEndereco =
            "WITH nova AS ( " +
                    "   INSERT INTO granja (nome, capacidade_aves, regiao, area_propriedade, id_empresa) " +
                    "   VALUES (?,?,?,?,?) RETURNING id " +
                    ") " +
                    "INSERT INTO endereco_granja (cep, numero, municipio, estado, logradouro, id_granja) " +
                    "SELECT ?, ?, ?, ?, ?, id FROM nova";

    public static final String comandoSelectById =
            "SELECT g.id, g.nome, g.capacidade_aves, g.regiao, g.area_propriedade, g.id_empresa, " +
                    "eg.cep, eg.numero, eg.municipio, eg.estado, eg.logradouro " +
                    "FROM granja g " +
                    "LEFT JOIN endereco_granja eg ON eg.id_granja = g.id " +
                    "WHERE g.id = ? " +
                    "ORDER BY eg.id LIMIT 1";

    public static final String comandoUpdateAll =
            "UPDATE granja SET nome = ?, capacidade_aves = ?, regiao = ?, " +
                    "area_propriedade = ?, id_empresa = ? WHERE id = ?";

    public static final String comandoUpdateEndereco =
            "UPDATE endereco_granja SET municipio = ?, estado = ? WHERE id_granja = ?";

    public static final String comandoDeleteGranjaEDependencias =
            "WITH alvo AS (SELECT ?::int AS id), " +
                    "apaga_avaliacao AS (DELETE FROM avaliacao WHERE id_dica IN " +
                    "   (SELECT id FROM dica WHERE id_granja = (SELECT id FROM alvo))), " +
                    "apaga_dica AS (DELETE FROM dica WHERE id_granja = (SELECT id FROM alvo)), " +
                    "apaga_registro_agua AS (DELETE FROM registro_agua WHERE id_lote IN " +
                    "   (SELECT id FROM lote WHERE id_granja = (SELECT id FROM alvo))), " +
                    "apaga_registro_energia AS (DELETE FROM registro_energia WHERE id_lote IN " +
                    "   (SELECT id FROM lote WHERE id_granja = (SELECT id FROM alvo))), " +
                    "apaga_lote AS (DELETE FROM lote WHERE id_granja = (SELECT id FROM alvo)), " +
                    "apaga_meta AS (DELETE FROM meta WHERE id_granja = (SELECT id FROM alvo)), " +
                    "apaga_endereco_proprietario AS (DELETE FROM endereco_proprietario WHERE id_proprietario IN " +
                    "   (SELECT id FROM proprietario_granja WHERE id_granja = (SELECT id FROM alvo))), " +
                    "apaga_telefone_proprietario AS (DELETE FROM telefone_proprietario WHERE id_proprietario IN " +
                    "   (SELECT id FROM proprietario_granja WHERE id_granja = (SELECT id FROM alvo))), " +
                    "apaga_verificacao_email AS (DELETE FROM verificacao_email WHERE usuario_id IN " +
                    "   (SELECT id FROM proprietario_granja WHERE id_granja = (SELECT id FROM alvo))), " +
                    "apaga_recuperacao_senha AS (DELETE FROM recuperacao_senha WHERE usuario_id IN " +
                    "   (SELECT id FROM proprietario_granja WHERE id_granja = (SELECT id FROM alvo))), " +
                    "apaga_proprietario AS (DELETE FROM proprietario_granja WHERE id_granja = (SELECT id FROM alvo)), " +
                    "apaga_endereco_granja AS (DELETE FROM endereco_granja WHERE id_granja = (SELECT id FROM alvo)) " +
                    "DELETE FROM granja WHERE id = (SELECT id FROM alvo)";

    public static final String comandoDeleteById =
            "DELETE FROM granja WHERE id = ?";

    public static final String comandoInsert =
            "INSERT INTO granja (nome, capacidade_aves, regiao, area_propriedade, id_empresa) VALUES (?,?,?,?,?)";

    public static final String comandoUpdateId =
            "UPDATE granja SET id = ? WHERE id = ?";

    public static final String comandoUpdateNome =
            "UPDATE granja SET nome = ? WHERE nome = ?";

    public static final String comandoUpdaeCapacidade =
            "UPDATE granja SET capacidade_aves = ? WHERE capacidade_aves = ?";

    // Corrigido: o valor antigo precisa ser comparado com a coluna regiao.
    public static final String comandoUpdateRegiao =
            "UPDATE granja SET regiao = ? WHERE regiao = ?";

    public static final String comandoUpdateArea =
            "UPDATE granja SET area_propriedade = ? WHERE area_propriedade = ?";

    public static final String comandoUpdateIdEmpresa =
            "UPDATE granja SET id_empresa = ? WHERE id_empresa = ?";

    public static final String comandoSelectCount =
            "SELECT COUNT(*) FROM granja";

    public static final String comandoSelectCountPaginadoComBusca =
            "SELECT COUNT(*) " +
                    "FROM granja g " +
                    "LEFT JOIN LATERAL ( " +
                    "   SELECT eg.municipio, eg.estado " +
                    "   FROM endereco_granja eg " +
                    "   WHERE eg.id_granja = g.id " +
                    "   ORDER BY eg.id " +
                    "   LIMIT 1 " +
                    ") ender ON TRUE " +
                    "WHERE (? = '' OR LOWER(COALESCE(g.nome, '')) LIKE LOWER(?)) " +
                    "AND (? = '' OR LOWER(CONCAT_WS(' ', COALESCE(ender.municipio, ''), " +
                    "COALESCE(ender.estado, ''))) LIKE LOWER(?))";

    public static final String comandoSelectPaginadoComBusca =
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
                    "       CONCAT_WS(' - ', NULLIF(ender.municipio, ''), NULLIF(ender.estado, '')) AS localizacao, " +
                    "       dados.responsavel AS responsavel, COALESCE(cgi.cgi, 0) AS cgi " +
                    "FROM granja g " +
                    "LEFT JOIN LATERAL ( " +
                    "   SELECT pg.nome AS responsavel " +
                    "   FROM proprietario_granja pg " +
                    "   WHERE pg.id_granja = g.id " +
                    "   ORDER BY pg.id " +
                    "   LIMIT 1 " +
                    ") dados ON TRUE " +
                    "LEFT JOIN LATERAL ( " +
                    "   SELECT eg.municipio, eg.estado " +
                    "   FROM endereco_granja eg " +
                    "   WHERE eg.id_granja = g.id " +
                    "   ORDER BY eg.id " +
                    "   LIMIT 1 " +
                    ") ender ON TRUE " +
                    "LEFT JOIN cgi_por_granja cgi ON cgi.id_granja = g.id " +
                    "WHERE (? = '' OR LOWER(COALESCE(g.nome, '')) LIKE LOWER(?)) " +
                    "AND (? = '' OR LOWER(CONCAT_WS(' ', COALESCE(ender.municipio, ''), " +
                    "COALESCE(ender.estado, ''))) LIKE LOWER(?)) " +
                    "ORDER BY %s ASC, g.id ASC LIMIT ? OFFSET ?";


    // Conta todas as granjas, mantendo o metodo antigo.
    public int contar() {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectCount);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Ocorreu um erro ao contar granjas.\n" + e.getMessage(), e
            );
        }
    }


    // Conta somente as granjas que correspondem à busca e ao filtro.
    public int contarBuscaELocalizacao(String busca, String localizacao) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        busca = busca == null ? "" : busca.trim();
        localizacao = localizacao == null ? "" : localizacao.trim();

        try (PreparedStatement ps =
                     connection.prepareStatement(comandoSelectCountPaginadoComBusca)) {

            ps.setString(1, busca);
            ps.setString(2, "%" + busca + "%");
            ps.setString(3, localizacao);
            ps.setString(4, "%" + localizacao + "%");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Ocorreu um erro ao contar granjas filtradas.\n" + e.getMessage(), e
            );
        }
    }


    // Paginação integrada à busca, à localização e à ordenação.
// A consulta combina filtros, ordenação segura, paginação e cálculo de CGI.
    public List<Granja> selectPaginado(
            int tamanho,
            int offset,
            String busca,
            String localizacao,
            String ordenarPor) {

        List<Granja> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

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

        String sql = String.format(comandoSelectPaginadoComBusca, colunaOrdenacao);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, busca);
            ps.setString(2, "%" + busca + "%");
            ps.setString(3, localizacao);
            ps.setString(4, "%" + localizacao + "%");
            ps.setInt(5, Math.max(1, tamanho));
            ps.setInt(6, Math.max(0, offset));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Granja gra = new Granja();

                    gra.setId(rs.getInt("id"));
                    gra.setAreaPropriedade(rs.getInt("area_propriedade"));
                    gra.setCapacidadeDeAves(rs.getInt("capacidade_aves"));
                    gra.setIdEmpresa(rs.getInt("id_empresa"));
                    gra.setNome(rs.getString("nome"));
                    gra.setRegiao(rs.getString("regiao"));
                    gra.setLocalizacao(rs.getString("localizacao"));
                    gra.setNomeResponsavel(rs.getString("responsavel"));
                    gra.setCgi(rs.getDouble("cgi"));

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


    // Metodo exigido pela interface: busca por nome com paginação.

    public List<Granja> selectPaginadoComBusca(
            String busca,
            int tamanho,
            int offset) {

        return selectPaginado(
                tamanho,
                offset,
                busca,
                "",
                "granja"
        );
    }


    // Select de todas as granjas sem os dados relacionados.
    public List<Granja> selectAll() {
        List<Granja> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Granja gra = new Granja();
                gra.setId(rs.getInt("id"));
                gra.setAreaPropriedade(rs.getInt("area_propriedade"));
                gra.setCapacidadeDeAves(rs.getInt("capacidade_aves"));
                gra.setIdEmpresa(rs.getInt("id_empresa"));
                gra.setNome(rs.getString("nome"));
                gra.setRegiao(rs.getString("regiao"));
                resultado.add(gra);
            }

            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao tentar mostrar informações do banco de dados.\n" + e.getMessage(), e
            );
        }
    }

    public List<CGI> selectCgi() {
        List<CGI> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectCgi);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CGI cgi = new CGI();
                cgi.setHidrometroInicio(rs.getDouble("hidrometro_inicio"));
                cgi.setHidrometroFinal(rs.getDouble("hidrometro_final"));
                cgi.setGalinhasEntregadas(rs.getInt("galinhas_entregadas"));
                cgi.setConsumoEnergetico(rs.getDouble("consumo"));
                resultado.add(cgi);
            }

            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar dados para o CGI.\n" + e.getMessage(), e
            );
        }
    }


    public List<Granja> selectNome() {
        List<Granja> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectNome);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Granja gra = new Granja();
                gra.setNome(rs.getString("nome"));
                resultado.add(gra);
            }

            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar nomes de granjas.\n" + e.getMessage(), e);
        }
    }


    public List<Granja> selectRegiao() {
        List<Granja> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectRegiao);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Granja gra = new Granja();
                gra.setRegiao(rs.getString("regiao"));
                resultado.add(gra);
            }

            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar regiões de granjas.\n" + e.getMessage(), e);
        }
    }


    public List<Granja> selectCapacidade() {
        List<Granja> resultado = new ArrayList<>();
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectCapacidade);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Granja gra = new Granja();
                gra.setCapacidadeDeAves(rs.getInt("capacidade_aves"));
                resultado.add(gra);
            }

            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar capacidade das granjas.\n" + e.getMessage(), e);
        }
    }


    // Busca uma granja pelo identificador.
    public Granja selectById(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoSelectById)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Granja gra = new Granja();
                gra.setId(rs.getInt("id"));
                gra.setNome(rs.getString("nome"));
                gra.setCapacidadeDeAves(rs.getInt("capacidade_aves"));
                gra.setRegiao(rs.getString("regiao"));
                gra.setAreaPropriedade(rs.getInt("area_propriedade"));
                gra.setIdEmpresa(rs.getInt("id_empresa"));
                gra.setCep(rs.getString("cep"));
                gra.setNumero(rs.getInt("numero"));
                gra.setMunicipio(rs.getString("municipio"));
                gra.setEstado(rs.getString("estado"));
                gra.setLogradouro(rs.getString("logradouro"));
                return gra;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar granja.\n" + e.getMessage(), e);
        }
    }

    // Insere a granja e seu endereço relacionado.
    public String insert(Granja gra) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoInsertGranjaComEndereco)) {
            ps.setString(1, gra.getNome());
            ps.setInt(2, gra.getCapacidadeDeAves());
            ps.setString(3, gra.getRegiao());
            ps.setInt(4, gra.getAreaPropriedade());
            ps.setInt(5, gra.getIdEmpresa());
            ps.setString(6, gra.getCep());
            ps.setInt(7, gra.getNumero());
            ps.setString(8, gra.getMunicipio());
            ps.setString(9, gra.getEstado());
            ps.setString(10, gra.getLogradouro());
            ps.executeUpdate();
            return "Granja e endereço inseridos com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir granja.\n" + e.getMessage(), e);
        }
    }

    // Atualiza granja e endereço na mesma transação.
    public String updateGranjaComEndereco(Granja gra) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement ps1 = connection.prepareStatement(comandoUpdateAll);
                 PreparedStatement ps2 = connection.prepareStatement(comandoUpdateEndereco)) {

                ps1.setString(1, gra.getNome());
                ps1.setInt(2, gra.getCapacidadeDeAves());
                ps1.setString(3, gra.getRegiao());
                ps1.setInt(4, gra.getAreaPropriedade());
                ps1.setInt(5, gra.getIdEmpresa());
                ps1.setInt(6, gra.getId());
                ps1.executeUpdate();

                ps2.setString(1, gra.getMunicipio());
                ps2.setString(2, gra.getEstado());
                ps2.setInt(3, gra.getId());
                ps2.executeUpdate();

                connection.commit();
                return "Granja atualizada com sucesso";
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar granja.\n" + e.getMessage(), e);
        }
    }

    // Exclui a granja e suas dependências relacionadas.
    public String delete(int id) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoDeleteGranjaEDependencias)) {
            ps.setInt(1, id);
            ps.execute();
            return "Item apagado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir granja.\n" + e.getMessage(), e);
        }
    }


    // Métodos de atualização compatíveis com a interface legada.
    public String updateId(int idNew, int idOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateId)) {
            ps.setInt(1, idNew);
            ps.setInt(2, idOld);
            ps.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ID da granja.\n" + e.getMessage(), e);
        }
    }


    public String updateNome(String nomeNew, String nomeOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateNome)) {
            ps.setString(1, nomeNew);
            ps.setString(2, nomeOld);
            ps.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar nome da granja.\n" + e.getMessage(), e);
        }
    }


    public String updateCapacidade(String capacidadeNew, String capacidadeOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdaeCapacidade)) {
            ps.setString(1, capacidadeNew);
            ps.setString(2, capacidadeOld);
            ps.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar capacidade da granja.\n" + e.getMessage(), e);
        }
    }


    public String updateRegiao(String regiaoNew, String regiaoOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateRegiao)) {
            ps.setString(1, regiaoNew);
            ps.setString(2, regiaoOld);
            ps.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar região da granja.\n" + e.getMessage(), e);
        }
    }


    public String updateArea(int areaNew, int areaOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateArea)) {
            ps.setInt(1, areaNew);
            ps.setInt(2, areaOld);
            ps.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar área da granja.\n" + e.getMessage(), e);
        }
    }


    public String updateIdEmpresa(int idEmpresaNew, int idEmpresaOld) {
        Connection connection = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(comandoUpdateIdEmpresa)) {
            ps.setInt(1, idEmpresaNew);
            ps.setInt(2, idEmpresaOld);
            ps.executeUpdate();
            return "Item atualizado com sucesso no banco de dados";

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar empresa da granja.\n" + e.getMessage(), e);
        }
    }
}
