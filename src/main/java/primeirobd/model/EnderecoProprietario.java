package primeirobd.model;

public class EnderecoProprietario {
    // atributos da tabela
    private int id;
    private String cep;
    private String estado;
    private int idProprietario;
    private String logradouro;
    private String municipio;
    private int numero;

    //construtor

    public EnderecoProprietario() {
    }

    //metodos getters

    public int getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public String getEstado() {
        return estado;
    }

    public int getIdProprietario() {
        return idProprietario;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public int getNumero() {
        return numero;
    }

    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "\nENDERECO_PROPRIETARIO" +
                "\nId: " + this.id +
                "\nCep: " + this.cep +
                "\nEstado: " + this.estado +
                "\nId_Proprietario: " + this.idProprietario +
                "\nLogradouro: " + this.logradouro +
                "\nMunicipio: " + this.municipio +
                "\nNumero: " + this.numero+
                "\n-------------------------------------------------------------";
    }
}
