package primeirobd.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Consulta indicadores agregados de produção, água, energia e metas.
public class IndicadoresDAO {

    // Frangos de lotes que ainda não foram entregues (sem data de entrega ou entrega futura)
    public static final String SELECT_FRANGOS_EM_PRODUCAO =
            "SELECT COALESCE(SUM(galinhas_recebidas), 0) FROM lote " +
                    "WHERE dt_entrega IS NULL OR dt_entrega > CURRENT_DATE";

    // Soma de todos os registros de água (leitura final - leitura inicial)
    public static final String SELECT_CONSUMO_AGUA_TOTAL =
            "SELECT COALESCE(SUM(hidrometro_final - hidrometro_inicio), 0) FROM registro_agua";

    // Soma de todos os registros de energia
    public static final String SELECT_CONSUMO_ENERGIA_TOTAL =
            "SELECT COALESCE(SUM(consumo), 0) FROM registro_energia";

    // Metas finalizadas: ignora maiúsculas/minúsculas, espaços e variações do texto
    public static final String SELECT_METAS_CONCLUIDAS =
            "SELECT COUNT(*) FROM meta WHERE LOWER(TRIM(status::text)) IN " +
                    "('feito', 'feita', 'concluido', 'concluído', 'concluida', 'concluída', " +
                    "'finalizado', 'finalizada')";

    public long frangosEmProducao() {
        return consultar(SELECT_FRANGOS_EM_PRODUCAO, null);
    }

    public long consumoAguaTotal() {
        return consultar(SELECT_CONSUMO_AGUA_TOTAL, null);
    }

    public long consumoEnergiaTotal() {
        return consultar(SELECT_CONSUMO_ENERGIA_TOTAL, null);
    }

    public long metasConcluidas() {
        return consultar(SELECT_METAS_CONCLUIDAS, null);
    }

    // Executa a consulta agregadora e devolve zero quando não há resultado.
    private long consultar(String sql, Integer parametro) {
        Connection conexao = ConexaoBancoPrimeiro.getConnection();

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            if (parametro != null) {
                ps.setInt(1, parametro);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao buscar os indicadores. \n" + e.getMessage());
        }
    }
}