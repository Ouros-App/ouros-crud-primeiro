package primeirobd.model;

import java.math.BigDecimal;

public class Meta {
    // Representa uma meta de acompanhamento, com escopo, situação e valor esperado.

    // Identificação, escopo, situação e valor esperado da meta.
    private int id;
    private String descricao;
    private String estado;
    private int idGranja;
    private boolean individual;
    private String regiao;
    private String status;
    private String tipoMeta;
    private String titulo;
    private BigDecimal valorAlvo;
    private String nomeGranja;


    // Construtor vazio usado pelo acesso a dados.
    public Meta() {
    }



    // Acessores para leitura dos dados da meta.
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEstado() {
        return estado;
    }

    public int getIdGranja() {
        return idGranja;
    }

    public boolean isIndividual() {
        return individual;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getStatus() {
        return status;
    }

    public String getTipoMeta() {
        return tipoMeta;
    }

    public String getTitulo() {
        return titulo;
    }

    public BigDecimal getValorAlvo() {
        return valorAlvo;
    }

    public String getNomeGranja() {
        return nomeGranja;
    }



    // Acessores para atualização dos dados da meta.
    public void setId(int id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdGranja(int idGranja) {
        this.idGranja = idGranja;
    }

    public void setIndividual(boolean individual) {
        this.individual = individual;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTipoMeta(String tipoMeta) {
        this.tipoMeta = tipoMeta;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setValorAlvo(BigDecimal valorAlvo) {
        this.valorAlvo = valorAlvo;
    }

    public void setNomeGranja(String nomeGranja) {
        this.nomeGranja = nomeGranja;
    }

    @Override
    // Apresentação resumida dos dados da entidade.
    public String toString() {
        return "\nMETA" +
                "\nId: " + this.id +
                "\nDescricao: " + this.descricao +
                "\nEstado: " + this.estado +
                "\nId_Granja: " + this.idGranja +
                "\nIs_Individual: " + this.individual +
                "\nRegião: " + this.regiao +
                "\nStatus: " + this.status +
                "\nTipo_Meta: " + this.tipoMeta +
                "\nTitulo: " + this.titulo +
                "\nValor_Alvo: " + this.valorAlvo +
                "\nNome_granja: " + this.nomeGranja +
                "\n-------------------------------------------------------------";
    }
}
