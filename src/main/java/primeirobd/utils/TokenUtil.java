package primeirobd.utils;
import java.security.SecureRandom;
import java.util.Base64;


public class TokenUtil {
    public static String gerarToken(){
        // SecueRandom, é um random mais seguro e imprevisivel
        SecureRandom random = new SecureRandom();
        // array de 24 bytes 256 valores possiveis
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        // deixa tudo em um formato legivel
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
