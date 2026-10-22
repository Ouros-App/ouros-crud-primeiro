package primeirobd.repository;
import primeirobd.model.RecuperarSenha;
import java.time.LocalDateTime;

public interface RecuperarSenhaDAO {
    void salvarToken(int administradorId, String token, LocalDateTime expiraEm);
    RecuperarSenha buscarPorToken(String token);
    void marcarComoUsado(String token);
}
