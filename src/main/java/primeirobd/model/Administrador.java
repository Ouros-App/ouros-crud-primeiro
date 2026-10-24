package primeirobd.model;

public class Administrador {

    // variaveis
    private int id;
    private String email;
    private String senha;
    private String cpf;
    private boolean emailVerificado;
    private String nome;
    private String fotoPerfilUrl;

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

    public boolean isEmailVerificado() {
        return emailVerificado;
    }

    public String getNome() {
        return nome;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }


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

    public void setEmailVerificado(boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }


    public String toString() {
        return "\nADMINISTRADOR" +
                "\nId: " + this.id +
                "\nEmail: " + this.email +
                "\nSenha: " + this.senha +
                "\nCpf: " + this.cpf +
                "\nEmail_Verificado: " + this.emailVerificado +
                "\nNome: " + this.nome +
                "\n-------------------------------------------------------------";
    }
}
