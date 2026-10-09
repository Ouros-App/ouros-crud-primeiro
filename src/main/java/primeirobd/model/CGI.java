package primeirobd.model;

public class CGI {
    // atributos do cgi (Consumo Geral do Integrado)
    private double hidrometroInicio;
    private double hidrometroFinal;
    private int galinhasEntregadas;
    private double consumoEnergetico;

    //construtor
    public CGI(){

    }

    //metodos getters
    public double getConsumoEnergetico() {
        return consumoEnergetico;
    }

    public int getGalinhasEntregadas() {
        return galinhasEntregadas;
    }

    public double getHidrometroFinal() {
        return hidrometroFinal;
    }

    public double getHidrometroInicio() {
        return hidrometroInicio;
    }


    //metodos setters
    public void setConsumoEnergetico(double consumoEnergetico) {
        this.consumoEnergetico = consumoEnergetico;
    }

    public void setGalinhasEntregadas(int galinhasEntregadas) {
        this.galinhasEntregadas = galinhasEntregadas;
    }

    public void setHidrometroFinal(double hidrometroFinal) {
        this.hidrometroFinal = hidrometroFinal;
    }

    public void setHidrometroInicio(double hidrometroInicio) {
        this.hidrometroInicio = hidrometroInicio;
    }

    @Override
    public String toString() {
        return "\nCGI" +
                "\nConsumo_Energetico: " + this.consumoEnergetico +
                "\nHidrometro_Inicio: " + this.hidrometroInicio +
                "\nHidrometro_Final: " + this.hidrometroFinal +
                "\nGalinhas_Entregadas: " + this.galinhasEntregadas;
    }
}
