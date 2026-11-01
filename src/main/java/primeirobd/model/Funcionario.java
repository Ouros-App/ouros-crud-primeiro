package primeirobd.model;

public class Funcionario {
    // Representa um funcionário e o vínculo dele com uma empresa.

    // Identificação, contato e vínculo empresarial do funcionário.
    private int id;
    private String cpf;
    private String email;
    private int idEmpresa;
    private String nome;
    private String setor;
    private String telefone;
    private String nomeEmpresa;



    // Construtor vazio usado pelo acesso a dados.
    public Funcionario() {
    }

    // Construtores para criação com os dados disponíveis do funcionário.
    public Funcionario(String nome, String cpf, String email, String setor, String telefone) {
        this.email = email;
        this.cpf = cpf;
        this.nome = nome;
        this.setor = setor;
        this.telefone = telefone;
    }

    public Funcionario(int id, String nome, String cpf, String email, String setor, String telefone) {
        this.email = email;
        this.cpf = cpf;
        this.nome = nome;
        this.setor = setor;
        this.telefone = telefone;
        this.id = id;
    }



    // Acessores para leitura dos dados do funcionário.
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

    public String getTelefone() {
        return telefone;
    }

    public String getNomeEmpresa() { return nomeEmpresa; }



    // Acessores para atualização dos dados do funcionário.
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

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    @Override
    // Apresentação resumida dos dados da entidade.
    public String toString() {
        return "\nFUNCIONARIO" +
                "\nId: " + this.id +
                "\nCpf: " + this.cpf +
                "\nEmail: " + this.email +
                "\nId_Empresa: " + this.idEmpresa +
                "\nNome: " + this.nome +
                "\nSetor: " + this.setor +
                "\nTelefone: " + this.telefone +
                "\n-------------------------------------------------------------";
    }
}
