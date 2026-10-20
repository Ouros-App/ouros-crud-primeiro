package primeirobd.model;

public class ProprietarioGranja {
    // atributos da tabela
    private int id;
    private String cpf;
    private String email;
    private int idGranja;
    private String nome;
    private String senha;

    //construtor

    public ProprietarioGranja() {
    }

    //metodos getters

    public int getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public int getIdGranja() {
        return idGranja;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }


    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdGranja(int idGranja) {
        this.idGranja = idGranja;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "\nPROPRIETARIO_GRANJA" +
                "\nId: " + this.id +
                "\nCpf: " + this.cpf +
                "\nEmail: " + this.email +
                "\nId_Granja: " + this.idGranja +
                "\nNome: " + this.nome +
                "\nSenha: " + this.senha+
                "\n-------------------------------------------------------------";
    }
}
