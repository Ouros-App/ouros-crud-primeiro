package primeirobd.model;

public class ProprietarioGranja {
    // Representa o proprietário responsável por uma granja e seus dados de acesso.

    // Identificação, contato, vínculo com a granja e credenciais do proprietário.
    private int id;
    private String cpf;
    private String email;
    private int idGranja;
    private String nome;
    private String senha;



    // Construtor vazio usado pelo acesso a dados.
    public ProprietarioGranja() {
    }



    // Acessores para leitura dos dados do proprietário.
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





    // Acessores para atualização dos dados do proprietário.
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
    // Apresentação resumida dos dados da entidade.
    public String toString() {
        return "\nPROPRIETARIO_GRANJA" +
                "\nId: " + this.id +
                "\nCpf: " + this.cpf +
                "\nEmail: " + this.email +
                "\nId_Granja: " + this.idGranja +
                "\nNome: " + this.nome +
                "\nSenha: " + this.senha +
                "\n-------------------------------------------------------------";
    }
}
