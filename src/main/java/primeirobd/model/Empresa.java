package primeirobd.model;

public class Empresa {
    // Representa uma empresa integrada ao sistema e seus dados corporativos.

    // Identificação e dados corporativos da empresa.
    private int id;
    private String nome;
    private String telefoneCorporativo;
    private String emailCorporativo;
    private String cnpj;


    // Construtor vazio usado pelo acesso a dados.
    public Empresa() {
    }

    public Empresa(String nome, String telefoneCorporativo, String emailCorporativo, String cnpj) {
        this.nome = nome;
        this.telefoneCorporativo = telefoneCorporativo;
        this.emailCorporativo = emailCorporativo;
        this.cnpj = cnpj;
    }


    // Acessores para leitura dos dados corporativos.
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefoneCorporativo() {
        return telefoneCorporativo;
    }

    public String getEmailCorporativo() {
        return emailCorporativo;
    }

    public String getCnpj() {
        return cnpj;
    }


    // Acessores para atualização dos dados corporativos.
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefoneCorporativo(String telefoneCorporativo) {
        this.telefoneCorporativo = telefoneCorporativo;
    }

    public void setEmailCorporativo(String emailCorporativo) {
        this.emailCorporativo = emailCorporativo;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    // Apresentação resumida dos dados da empresa.
    public String toString() {
        return "\nEMPRESA" +
                "\nID: " + this.id +
                "\nNome: " + this.nome +
                "\nTelefone_corporativo: " + this.telefoneCorporativo +
                "\nEmail_corporativo: " + this.emailCorporativo +
                "\nCNPJ: " + this.cnpj;
    }
}
