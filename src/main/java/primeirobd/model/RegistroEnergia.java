package primeirobd.model;

import java.sql.Date;
import java.math.BigDecimal;

public class RegistroEnergia {
    // Representa uma medição de energia associada a um lote e a uma granja.

    // Identificação, consumo, data e vínculo do registro de energia.
    private int id;
    private BigDecimal consumo;
    private Date dtRegistro;
    private String nomeGranja;
    private String status;
    private int idLote;



    // Construtor vazio usado pelo acesso a dados.
    public RegistroEnergia() {
    }



    // Acessores para leitura dos dados da medição.
    public int getId() {
        return id;
    }

    public BigDecimal getConsumo() {
        return consumo;
    }

    public Date getDtRegistro() {
        return dtRegistro;
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

    public void setConsumo(BigDecimal consumo) {
        this.consumo = consumo;
    }

    public void setDtRegistro(Date dtRegistro) {
        this.dtRegistro = dtRegistro;
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
        return "\nREGISTRO_ENERGIA" +
                "\nId: " + this.id +
                "\nConsumo: " + this.consumo +
                "\nDt_Registro: " + this.dtRegistro +
                "\nNome_granja: " + this.nomeGranja +
                "\nStatus: " + this.status +
                "\nId_Lote: " + this.idLote+
                "\n-------------------------------------------------------------";
    }
}
