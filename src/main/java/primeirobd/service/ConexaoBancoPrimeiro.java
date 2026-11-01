package primeirobd.service;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Abre a conexão PostgreSQL a partir de configurações externas.
public class ConexaoBancoPrimeiro {

    // Carrega as configurações externas do ambiente.
    public static final  Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    // conexão única, compartilhada entre todos os DAOs
    private static Connection connection;

    // construtor privado: ninguém mais precisa (nem deve) instanciar essa classe
    private ConexaoBancoPrimeiro() {
    }

    // Obtém a conexão usada pelos DAOs.
    // Reaproveita a conexão enquanto ela estiver aberta.
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.postgresql.Driver");

                String url = dotenv.get("URL");
                String user = dotenv.get("USER");
                String password = dotenv.get("PASSWORD");

                connection = DriverManager.getConnection(url, user, password);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao conectar: " + e.getMessage(), e);
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe.getMessage());
        }
    }
}