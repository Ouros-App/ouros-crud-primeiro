package primeirobd.model;

public class Granja {
    // atributos da tabela
    private int id;
    private int areaPropriedade;
    private int capacidadeDeAves;
    private int idEmpresa;
    private String nome;
    private String nomeResponsavel;
    private String localizacao;
    private String regiao;
    private double cgi;
    private String cep;
    private int numero;
    private String logradouro;
    private String municipio;
    private String estado;

    //construtor

    public Granja() {
    }

    //metodos getters

    public int getId() {
        return id;
    }

    public int getAreaPropriedade() {
        return areaPropriedade;
    }

    public int getCapacidadeDeAves() {
        return capacidadeDeAves;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public double getCgi() {
        return cgi;
    }

    public String getCep() { return cep; }

    public int getNumero() { return numero; }

    public String getLogradouro() { return logradouro; }

    public String getMunicipio() { return municipio; }

    public String getEstado() { return estado; }


    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setAreaPropriedade(int areaPropriedade) {
        this.areaPropriedade = areaPropriedade;
    }

    public void setCapacidadeDeAves(int capacidadeDeAves) {
        this.capacidadeDeAves = capacidadeDeAves;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public void setCgi(double cgi) {
        this.cgi = cgi;
    }

    public void setCep(String cep) { this.cep = cep; }

    public void setNumero(int numero) { this.numero = numero; }

    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "\nGRANJA" +
                "\nId: " + this.id +
                "\nArea_Propriedade: " + this.areaPropriedade +
                "\nCapacidade_De_Aves: " + this.capacidadeDeAves +
                "\nId_Empresa: " + this.idEmpresa +
                "\nNome: " + this.nome +
                "\nResponsável: " + this.nomeResponsavel +
                "\nLocalização: " + this.localizacao +
                "\nRegiao: " + this.regiao +
                "\nCGI: " + this.cgi +
                "\n-------------------------------------------------------------";
    }
}
