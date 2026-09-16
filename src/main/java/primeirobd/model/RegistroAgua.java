package primeirobd.model;

import java.sql.Date;

public class RegistroAgua {
    // atributos da tabela
    private int id;
    private Date dtRegistro;
    private int hidrometroInicio;
    private int hidrometroFinal;
    private int idLote;

    //construtor
    public RegistroAgua() {
    }

    //metodos getters

    public int getId() {
        return id;
    }

    public Date getDtRegistro() {
        return dtRegistro;
    }

    public int getHidrometroInicio() {
        return hidrometroInicio;
    }

    public int getHidrometroFinal() {
        return hidrometroFinal;
    }

    public int getIdLote() {
        return idLote;
    }


    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setDtRegistro(Date dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    public void setHidrometroInicio(int hidrometroInicio) {
        this.hidrometroInicio = hidrometroInicio;
    }

    public void setHidrometroFinal(int hidrometroFinal) {
        this.hidrometroFinal = hidrometroFinal;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    @Override
    public String toString() {
        return "\nREGISTRO_AGUA" +
                "\nId: " + this.id +
                "\nDt_Registro: " + this.dtRegistro +
                "\nHidrometro_Iniio: " + this.hidrometroInicio +
                "\nHidrometro_Final: " + this.hidrometroFinal +
                "\nId_Lote: " + this.idLote+
                "\n-------------------------------------------------------------";
    }
}
