package primeirobd.model;

public class Administrador {
    // atributos da tabela
    private int id;
    private String email;
    private String senha;

    //construtor
    public Administrador() {

    }

    //metodos getters

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }


    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "\nADMINISTRADOR" +
                "\nID: " + this.id +
                "\nEmail: " + this.email +
                "\nSenha: " + this.senha +
                "\n-------------------------------------------------------------";
    }
}
