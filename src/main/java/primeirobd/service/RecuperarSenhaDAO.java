package primeirobd.service;

import primeirobd.model.RecuperarSenha;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RecuperarSenhaDAO implements primeirobd.repository.RecuperarSenhaDAO {

    private static final String INVALIDAR_ANTIGOS =
            "UPDATE recuperacao_senha SET usado = TRUE WHERE usuario_id = ? AND usado = FALSE";

    private static final String INSERIR =
            "INSERT INTO recuperacao_senha (usuario_id, token, expira_em, usado) VALUES (?, ?, ?, FALSE)";

    private static final String BUSCAR =
            "SELECT id, usuario_id, token, expira_em, usado FROM recuperacao_senha WHERE token = ?";

    private static final String MARCAR_USADO =
            "UPDATE recuperacao_senha SET usado = TRUE WHERE token = ?";


    public void salvarToken(int usuarioId, String token, LocalDateTime expiraEm) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection()) {

            // só o pedido mais recente vale: invalida os anteriores ainda abertos
            try (PreparedStatement ps = conexao.prepareStatement(INVALIDAR_ANTIGOS)) {
                ps.setInt(1, usuarioId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conexao.prepareStatement(INSERIR)) {
                ps.setInt(1, usuarioId);
                ps.setString(2, token);
                ps.setTimestamp(3, Timestamp.valueOf(expiraEm));
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar token de recuperação de senha", e);
        }
    }


    public RecuperarSenha buscarPorToken(String token) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = conexao.prepareStatement(BUSCAR)) {

            ps.setString(1, token);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                RecuperarSenha rs2 = new RecuperarSenha();
                rs2.setId(rs.getInt("id"));
                rs2.setUsuarioId(rs.getInt("usuario_id"));
                rs2.setToken(rs.getString("token"));
                rs2.setExpiraEm(rs.getTimestamp("expira_em").toLocalDateTime());
                rs2.setUsado(rs.getBoolean("usado"));
                return rs2;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar token de recuperação de senha", e);
        }
    }


    public void marcarComoUsado(String token) {
        try (Connection conexao = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement ps = conexao.prepareStatement(MARCAR_USADO)) {

            ps.setString(1, token);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao marcar token como usado", e);
        }
    }
}