package primeirobd.model;

public class TelefoneProprietario {
    // atributos da tabela
    private int id;
    private int idProprietario;
    private String telefone;


    //construtor
    public TelefoneProprietario() {
    }

    //metodos getters

    public int getId() {
        return id;
    }

    public int getIdProprietario() {
        return idProprietario;
    }

    public String getTelefone() {
        return telefone;
    }


    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setIdProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "\nTELEFONE_PROPRIETARIO" +
                "\nid: " + this.id +
                "\nidProprietario: " + this.idProprietario +
                "\ntelefone: " + this.telefone+
                "\n-------------------------------------------------------------";
    }
}
