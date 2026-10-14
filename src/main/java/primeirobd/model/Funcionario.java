package primeirobd.model;

public class Funcionario {
    // atributos da tabela
    private int id;
    private String cpf;
    private String email;
    private int idEmpresa;
    private String nome;
    private String setor;
    private String telefoneCorporativo;

    //construtores

    public Funcionario() {
    }

    public Funcionario(String nome, String cpf, String email, String setor, String telefoneCorporativo) {
        this.email = email;
        this.cpf = cpf;
        this.nome = nome;
        this.setor = setor;
        this.telefoneCorporativo = telefoneCorporativo;
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

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public String getSetor() {
        return setor;
    }

    public String getTelefoneCorporativo() {
        return telefoneCorporativo;
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

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public void setTelefoneCorporativo(String telefoneCorporativo) {
        this.telefoneCorporativo = telefoneCorporativo;
    }

    @Override
    public String toString() {
        return "\nFUNCIONARIO" +
                "\nId: " + this.id +
                "\nCpf: " + this.cpf +
                "\nEmail: " + this.email +
                "\nId_Empresa: " + this.idEmpresa +
                "\nNome: " + this.nome +
                "\nSetor: " + this.setor +
                "\nTelefone: " + this.telefoneCorporativo +
                "\n-------------------------------------------------------------";
    }
}
