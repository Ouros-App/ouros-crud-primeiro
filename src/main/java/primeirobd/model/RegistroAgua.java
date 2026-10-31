package primeirobd.model;

import java.sql.Date;
import java.math.BigDecimal;

public class RegistroAgua {
    // Representa uma medição de água associada a um lote e a uma granja.

    // Identificação, data, leituras do hidrômetro e vínculo do registro.
    private int id;
    private Date dtRegistro;
    private BigDecimal hidrometroInicio;
    private BigDecimal hidrometroFinal;
    private String nomeGranja;
    private String status;
    private int idLote;


    // Construtor vazio usado pelo acesso a dados.
    public RegistroAgua() {
    }



    // Acessores para leitura dos dados da medição.
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




    // Acessores para atualização dos dados da medição.
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
    // Apresentação resumida dos dados da entidade.
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
