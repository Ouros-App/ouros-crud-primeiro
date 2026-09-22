package primeirobd.model;

public class Empresa {
    // atributos da tabela
    private int id;
    private String nome;
    private String telefoneCorporativo;
    private String emailCorporativo;
    private String cnpj;

    //construtor
    public Empresa() {
    }

    //metodos getters

    public int getId() {
        return id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEmailCorporativo() {
        return emailCorporativo;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefoneCorporativo() {
        return telefoneCorporativo;
    }

    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setEmailCorporativo(String emailCorporativo) {
        this.emailCorporativo = emailCorporativo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefoneCorporativo(String telefoneCorporativo) {
        this.telefoneCorporativo = telefoneCorporativo;
    }

    @Override
    public String toString() {
        return "\nEMPRESA" +
                "\nID: " + this.id +
                "\nCNPJ: " + this.cnpj +
                "\nEmail Corporativo: " + this.emailCorporativo +
                "\nNome: " + this.nome +
                "\nTelefone corporativo: " + this.telefoneCorporativo+
                "\n-------------------------------------------------------------";
    }
}
