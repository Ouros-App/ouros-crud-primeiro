package primeirobd.model;

import java.time.LocalDateTime;

public class RecuperarSenha {
    private int id;
    private int usuarioId;
    private String token;
    private LocalDateTime expiraEm;
    private boolean usado;

    public RecuperarSenha() {
    }

    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiraEm() {
        return expiraEm;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiraEm(LocalDateTime expiraEm) {
        this.expiraEm = expiraEm;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
}
