package primeirobd.tests;

import primeirobd.utils.Validador;



import java.util.Scanner;

public class MainRegexTeste {
    static void main() {
        String palavra = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("digita email, cpf, telefone");

        String[] palavras = new String[3];

        for (int i = 0; i < palavras.length; i++) {
            palavras[i] = sc.nextLine();
        }
        System.out.println("Email valido?");
        System.out.println(Validador.isEmailValido(palavras[0]));
        System.out.println("Cpf valido?");
        System.out.println(Validador.isCpfValido(palavras[1]));
        System.out.println("Telefone valido?");
        System.out.println(Validador.isTelefoneValido(palavras[2]));
    }

}
