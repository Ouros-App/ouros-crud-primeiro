package primeirobd.model;

import java.sql.Date;

public class RegistroEnergia {
    // atributos da tabela
    private int id;
    private int consumo;
    private Date dtRegistro;
    private int idLote;

    //construtor

    public RegistroEnergia() {
    }

    //metodos getters

    public int getId() {
        return id;
    }

    public int getConsumo() {
        return consumo;
    }

    public Date getDtRegistro() {
        return dtRegistro;
    }

    public int getIdLote() {
        return idLote;
    }


    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public void setDtRegistro(Date dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    @Override
    public String toString() {
        return "\nREGISTRO_ENERGIA" +
                "\nId: " + this.id +
                "\nConsumo: " + this.consumo +
                "\nDt_Registro: " + this.dtRegistro +
                "\nId_Lote: " + this.idLote+
                "\n-------------------------------------------------------------";
    }
}
