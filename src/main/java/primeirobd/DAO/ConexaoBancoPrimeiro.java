package primeirobd.DAO;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoPrimeiro {

    // carregando o .env
    private static final Dotenv dotenv = Dotenv.load();

    // conexão única, compartilhada entre todos os DAOs
    private static Connection connection;

    // construtor privado: ninguém mais precisa (nem deve) instanciar essa classe
    private ConexaoBancoPrimeiro() {
    }

    // classe da conexao
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
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe.getMessage());
        }
    }
}