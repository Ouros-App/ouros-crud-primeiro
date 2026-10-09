package primeirobd.utils;

import primeirobd.model.CGI;
import primeirobd.model.Granja;
import primeirobd.service.ConexaoBancoPrimeiro;
import primeirobd.service.GranjaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class CalculoCGI {
    public static double calcularCGI(Granja granja, List<CGI> informacoes, int contador){
        // variaveis
        double hidrometroInicio = informacoes.get(contador).getHidrometroInicio();
        double hidrometroFinal = informacoes.get(contador).getHidrometroFinal();
        int galinhasEntregadas = informacoes.get(contador).getGalinhasEntregadas();
        double consumoEnergetico = informacoes.get(contador).getConsumoEnergetico();
        double consumoTotalAgua = 0;
        double consumoTotalEnergia = 0;
        double cgi = 0;
        // calculo do consumo com agua
        consumoTotalAgua = (hidrometroFinal - hidrometroInicio) / galinhasEntregadas;
        // calculo do consumo com energia
        consumoTotalEnergia = consumoEnergetico / galinhasEntregadas;
        // calculo do CGI (Consumo Geral do Integrado)
        cgi = ((consumoTotalAgua * 0.7) + (consumoTotalEnergia * 0.3)) * 1000;
        return cgi;
    }
}
