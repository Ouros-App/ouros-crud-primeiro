package primeirobd.model;

import java.sql.Date;
import java.math.BigDecimal;   // novo import, no topo

public class Lote {
    // atributos da tabela
    private int id;
    private Date dtChegada;
    private Date dtEntrega;
    private Date dtNascimento;
    private int galinhasEntregadas;
    private int galinhasRecebidas;
    private String nomeGranja;
    private BigDecimal ganho;


    private int idGranja;

    //contrutor

    public Lote() {
    }
    //metodos getters

    public int getId() {
        return id;
    }

    public Date getDtChegada() {
        return dtChegada;
    }

    public Date getDtEntrega() {
        return dtEntrega;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public int getGalinhasEntregadas() {
        return galinhasEntregadas;
    }

    public int getGalinhasRecebidas() {
        return galinhasRecebidas;
    }

    public String getNomeGranja() {
        return nomeGranja;
    }
    public BigDecimal getGanho() {
        return ganho;
    }

    public int getIdGranja() {
        return idGranja;
    }

    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setDtChegada(Date dtChegada) {
        this.dtChegada = dtChegada;
    }

    public void setDtEntrega(Date dtEntrega) {
        this.dtEntrega = dtEntrega;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public void setGalinhasEntregadas(int galinhasEntregadas) {
        this.galinhasEntregadas = galinhasEntregadas;
    }

    public void setGalinhasRecebidas(int galinhasRecebidas) {
        this.galinhasRecebidas = galinhasRecebidas;
    }

    public void setNomeGranja(String nomeGranja) {
        this.nomeGranja = nomeGranja;
    }

    public void setGanho(BigDecimal ganho) {
        this.ganho = ganho;
    }

    public void setIdGranja(int idGranja) {
        this.idGranja = idGranja;
    }

    @Override
    public String toString() {
        return "\nLOTE" +
                "\nId: " + this.id +
                "\nDt_Chegada: " + this.dtChegada +
                "\nDt_Entrega: " + this.dtEntrega +
                "\nDt_Nascimento: " + this.dtNascimento +
                "\nGalinhas_Entregadas: " + this.galinhasEntregadas +
                "\nGalinhas_Recebidas: " + this.galinhasRecebidas +
                "\nNome granja: " + this.nomeGranja +
                "\nGanho: " + this.ganho +
                "\nId_Granja: " + this.idGranja+
                "\n-------------------------------------------------------------";
    }
}
