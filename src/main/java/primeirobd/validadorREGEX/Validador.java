package primeirobd.validadorREGEX;
import java.util.regex.Pattern;

public class Validador {
    // E-mail: padrão comum
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    // Telefone BR: aceita (11) 91234-5678, 11912345678, (11) 1234-5678, 1134567890, com/sem DDD e 9º dígito
    private static final Pattern TELEFONE_PATTERN =
            Pattern.compile("^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$");

    public static final Pattern SENHA_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!.\\-_*]).{8,}$");


    public static boolean isEmailValido(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isTelefoneValido(String telefone) {
        if (telefone == null) return false;
        return TELEFONE_PATTERN.matcher(telefone).matches();
    }

    public static boolean isSenhaValida(String senha) {
        if (senha == null) return false;
        return SENHA_PATTERN.matcher(senha).matches();
    }

    /**
     * Valida o CPF de verdade: formato + dígitos verificadores +
     * rejeita sequências repetidas (111.111.111-11 etc, que passam no formato mas são inválidas)
     */
    public static boolean isCpfValido(String cpf) {
        if (cpf == null) return false;

        // Remove tudo que não for dígito
        String digits = cpf.replaceAll("\\D", "");

        // Precisa ter exatamente 11 dígitos
        if (digits.length() != 11) return false;

        // Rejeita sequências tipo 00000000000, 11111111111, 22222222222 ...
        if (digits.matches("(\\d)\\1{10}")) return false;

        int[] nums = new int[11];
        for (int i = 0; i < 11; i++) {
            nums[i] = digits.charAt(i) - '0';
        }

        // --- Cálculo do 1º dígito verificador ---
        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += nums[i] * (10 - i);
        }
        int resto1 = soma1 % 11;
        int dv1 = (resto1 < 2) ? 0 : 11 - resto1;

        if (dv1 != nums[9]) return false;

        // --- Cálculo do 2º dígito verificador ---
        int soma2 = 0;
        for (int i = 0; i < 10; i++) {
            soma2 += nums[i] * (11 - i);
        }
        int resto2 = soma2 % 11;
        int dv2 = (resto2 < 2) ? 0 : 11 - resto2;

        return dv2 == nums[10];
    }
}
