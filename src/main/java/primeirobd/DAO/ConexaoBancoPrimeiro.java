package primeirobd.DAO;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoPrimeiro {

    // carregando o .env
    Dotenv dotenv = Dotenv.load();

    // variveis do .env
    private String url;
    private String user;
    private String password;

    //getters
    public String getUrl() {
        return this.url;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    // setters
    public void setUrl() {
        this.url = dotenv.get("URL");
    }

    public void setUser() {
        this.user = dotenv.get("USER");
    }

    public void setPassword() {
        this.password = dotenv.get("PASSWORD");
    }

    //construtor
    public ConexaoBancoPrimeiro() {
    }

    // classe da conexao
    public Connection getConnection() {
        try{
            Class.forName("org.postgresql.Driver");
            // setar nossas variaveis igualmente esta no .env
            setUrl();
            setUser();
            setPassword();

            //fazer a conexao
            return DriverManager.getConnection(getUrl(),getUser(),getPassword());
        }catch (SQLException e){

            // se der problema
            throw new RuntimeException(e.getMessage());
        } catch(ClassNotFoundException cnfe){
            throw new RuntimeException(cnfe.getMessage());
        }
    }
}
