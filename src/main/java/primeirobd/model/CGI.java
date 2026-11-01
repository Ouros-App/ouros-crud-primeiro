package primeirobd.model;

public class CGI {
    // Agrupa os indicadores de consumo geral do integrado, incluindo água, energia e quantidade de aves.

    // Leituras e indicadores que compõem o consumo do integrado.
    private double hidrometroInicio;
    private double hidrometroFinal;
    private int galinhasEntregadas;
    private double consumoEnergetico;


    // Construtor vazio usado pelo acesso a dados.
    public CGI(){

    }


    // Acessores para leitura dos indicadores.
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



    // Acessores para atualização dos indicadores.
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
    // Apresentação dos indicadores registrados.
    public String toString() {
        return "\nCGI" +
                "\nConsumo_Energetico: " + this.consumoEnergetico +
                "\nHidrometro_Inicio: " + this.hidrometroInicio +
                "\nHidrometro_Final: " + this.hidrometroFinal +
                "\nGalinhas_Entregadas: " + this.galinhasEntregadas;
    }
}
