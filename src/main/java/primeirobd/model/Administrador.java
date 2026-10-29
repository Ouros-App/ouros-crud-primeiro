package primeirobd.model;

public class Administrador {

    // variaveis
    private int id;
    private String email;
    private String senha;
    private String cpf;
    private String nome;
    private String fotoPerfilUrl;
    private String telefone;

    // construtor
    public Administrador() {
    }

    //getters

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getCpf() {
        return cpf;
    }


    public String getNome() {
        return nome;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public String getTelefone() { return telefone; }


    // setter

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public void setTelefone(String telefone) { this.telefone = telefone; }


    public String toString() {
        return "\nADMINISTRADOR" +
                "\nId: " + this.id +
                "\nEmail: " + this.email +
                "\nSenha: " + this.senha +
                "\nCpf: " + this.cpf +
                "\nNome: " + this.nome +
                "\n-------------------------------------------------------------";
    }
}
