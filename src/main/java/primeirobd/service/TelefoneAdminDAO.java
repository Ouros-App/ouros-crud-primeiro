package primeirobd.service;

import java.sql.*;

public class TelefoneAdminDAO {

    private static final String INSERT =
            "INSERT INTO telefone_admin (telefone, usuario_id) VALUES (?, ?)";
    private static final String UPDATE =
            "UPDATE telefone_admin SET telefone = ? WHERE usuario_id = ?";

    public void insert(int usuarioId, String telefone) {
        try (Connection c = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = c.prepareStatement(INSERT)) {
            stmt.setString(1, telefone);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar telefone do administrador.\n" + e.getMessage());
        }
    }

    // atualiza se já existe; se não existe, insere
    public void salvar(int usuarioId, String telefone) {
        try (Connection c = ConexaoBancoPrimeiro.getConnection();
             PreparedStatement stmt = c.prepareStatement(UPDATE)) {
            stmt.setString(1, telefone);
            stmt.setInt(2, usuarioId);
            if (stmt.executeUpdate() == 0) {
                insert(usuarioId, telefone);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar telefone do administrador.\n" + e.getMessage());
        }
    }
}