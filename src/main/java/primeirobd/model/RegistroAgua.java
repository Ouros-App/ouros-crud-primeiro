package primeirobd.model;

import java.sql.Date;
import java.math.BigDecimal;

public class RegistroAgua {
    // atributos da tabela
    private int id;
    private Date dtRegistro;
    private BigDecimal hidrometroInicio;
    private BigDecimal hidrometroFinal;
    private String nomeGranja;
    private String status;
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

    public BigDecimal getHidrometroInicio() {
        return hidrometroInicio;
    }

    public BigDecimal getHidrometroFinal() {
        return hidrometroFinal;
    }

    public int getIdLote() {
        return idLote;
    }

    public String getNomeGranja() {
        return nomeGranja;
    }

    public String getStatus() {
        return status;
    }


    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setDtRegistro(Date dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    public void setHidrometroInicio(BigDecimal hidrometroInicio) {
        this.hidrometroInicio = hidrometroInicio;
    }

    public void setHidrometroFinal(BigDecimal hidrometroFinal) {
        this.hidrometroFinal = hidrometroFinal;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public void setNomeGranja(String nomeGranja) {
        this.nomeGranja = nomeGranja;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nREGISTRO_AGUA" +
                "\nId: " + this.id +
                "\nDt_Registro: " + this.dtRegistro +
                "\nHidrometro_Iniio: " + this.hidrometroInicio +
                "\nHidrometro_Final: " + this.hidrometroFinal +
                "\nNome_granja: " + this.nomeGranja +
                "\nStatus: " + this.status +
                "\nId_Lote: " + this.idLote+
                "\n-------------------------------------------------------------";
    }
}
