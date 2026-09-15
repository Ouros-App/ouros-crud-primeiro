package primeirobd.model;

public class EnderecoGranja {
    // atributos da tabela
    private int id;
    private String cep;
    private String estado;
    private int idGranja;
    private String logradouro;
    private String municipio;
    private int numero;

    //construtor

    public EnderecoGranja() {
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

    public int getIdGranja() {
        return idGranja;
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

    public void setIdGranja(int idGranja) {
        this.idGranja = idGranja;
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
        return "\nENDERECO_GRANJA" +
                "\nId: " + this.id +
                "\ncep: " + this.cep +
                "\nEstado: " + this.estado +
                "\nId_Granja: " + this.idGranja +
                "\nLogradouro: " + this.logradouro +
                "\nMunicipio: " + this.municipio +
                "\nNumero: " + this.numero+
                "\n-------------------------------------------------------------";
    }
}
