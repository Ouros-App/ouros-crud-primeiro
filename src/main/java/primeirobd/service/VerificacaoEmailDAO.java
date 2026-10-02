package primeirobd.service;

import primeirobd.model.VerificacaoEmail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VerificacaoEmailDAO {

    public static final String INSERT =
            "INSERT INTO verificacao_email (usuario_id, token, expira_em, usado) VALUES (?, ?, ?, false)";

    public static final String SELECT_BY_TOKEN =
            "SELECT id, usuario_id, token, expira_em, usado FROM verificacao_email WHERE token = ?";

    public static final String MARCAR_COMO_USADO =
            "UPDATE verificacao_email SET usado = true WHERE token = ?";

    public void salvarToken(int usuarioId, String token, LocalDateTime expiraEm) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(INSERT)) {

            stmt.setInt(1, usuarioId);
            stmt.setString(2, token);
            stmt.setTimestamp(3, Timestamp.valueOf(expiraEm));
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar token de verificação.\n" + e.getMessage());
        }
    }

    public VerificacaoEmail buscarPorToken(String token) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SELECT_BY_TOKEN)) {

            stmt.setString(1, token);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null; // nenhum token encontrado com esse valor
                }

                VerificacaoEmail vt = new VerificacaoEmail();
                vt.setId(rs.getInt("id"));
                vt.setUsuarioId(rs.getInt("usuario_id"));
                vt.setToken(rs.getString("token"));
                vt.setExpiraEm(rs.getTimestamp("expira_em").toLocalDateTime());
                vt.setUsado(rs.getBoolean("usado"));
                return vt;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar token de verificação.\n" + e.getMessage());
        }
    }

    public void marcarComoUsado(String token) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(MARCAR_COMO_USADO)) {

            stmt.setString(1, token);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao marcar token como usado.\n" + e.getMessage());
        }
    }
}