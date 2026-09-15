package primeirobd.model;

public class EnderecoEmpresa {
    // atributos da tabela
    private int id;
    private String cep;
    private String estado;
    private int idEmpresa;
    private String logradouro;
    private String municipio;
    private int numero;

    //construtor
    public EnderecoEmpresa() {

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

    public int getIdEmpresa() {
        return idEmpresa;
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

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
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
        return "\nENDEREÇO_EMPRESA" +
                "\nID: " + this.id +
                "\nCEP: " + this.cep +
                "\nEstado: " + this.estado +
                "\nID_Empresa: " + this.idEmpresa +
                "\nLogradouro: " + this.logradouro +
                "\nMunicipio: " + this.municipio +
                "\nNúmero: " + this.numero+
                "\n-------------------------------------------------------------";
    }
}
