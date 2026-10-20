package primeirobd.model;

public class TelefoneFuncionario {
    // atributos da tabela
    private int id;
    private int idFuncionario;
    private String telefone;

    //construtor

    public TelefoneFuncionario() {
    }

    //metodos getters

    public String getTelefone() {
        return telefone;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public int getId() {
        return id;
    }


    //metodos setters

    public void setId(int id) {
        this.id = id;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "\nTELEFONE_FUNCIONARIO" +
                "\nid: " + this.id +
                "\nidFuncionario: " + this.idFuncionario +
                "\ntelefone: " + this.telefone+
                "\n-------------------------------------------------------------";
    }
}
