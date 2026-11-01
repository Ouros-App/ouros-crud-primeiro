package primeirobd.model;

public class Administrador {
    // Representa os dados de acesso e contato de um administrador do sistema.


    // Identificação e dados usados no cadastro do administrador.
    private int id;
    private String email;
    private String senha;
    private String cpf;
    private String nome;
    private String fotoPerfilUrl;
    private String telefone;


    // Construtor vazio usado pelo acesso a dados.
    public Administrador() {
    }



    // Acessores para leitura dos atributos do modelo.
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




    // Acessores para atualização dos atributos do modelo.
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


    // Apresentação resumida dos dados principais da entidade.
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
