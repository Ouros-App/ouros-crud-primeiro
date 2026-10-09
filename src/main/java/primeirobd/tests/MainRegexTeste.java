package primeirobd.tests;

import primeirobd.validadorREGEX.Validador;



import java.util.Scanner;

public class MainRegexTeste {
    static void main() {
        String palavra = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("digita email, cpf, telefone, senha");

        String[] palavras = new String[4];

        for (int i = 0; i < palavras.length; i++) {
            palavras[i] = sc.nextLine();
        }
        System.out.println("Email valido?");
        System.out.println(Validador.isEmailValido(palavras[0]));
        System.out.println("Cpf valido?");
        System.out.println(Validador.isCpfValido(palavras[1]));
        System.out.println("Telefone valido?");
        System.out.println(Validador.isTelefoneValido(palavras[2]));
        System.out.println("Senha valida?");
        System.out.println(Validador.isSenhaValida(palavras[3]));
    }

}
