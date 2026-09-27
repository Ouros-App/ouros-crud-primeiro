package primeirobd.utils;

import primeirobd.model.VerificacaoEmail;
import primeirobd.service.ProprietarioGranjaDAO;
import primeirobd.service.VerificacaoEmailDAO;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/verificar")
public class VerificarEmailServlet extends HttpServlet {

    private final VerificacaoEmailDAO verificacaoDao = new VerificacaoEmailDAO();
    private final ProprietarioGranjaDAO usuarioDao = new ProprietarioGranjaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String token = request.getParameter("token");

        response.setContentType("text/plain;charset=UTF-8");

        if (token == null || token.isBlank()) {
            response.getWriter().write("Token não informado.");
            return;
        }

        VerificacaoEmail registro = verificacaoDao.buscarPorToken(token);

        boolean tokenValido = registro != null
                && !registro.isUsado()
                && registro.getExpiraEm().isAfter(LocalDateTime.now());

        if (tokenValido) {
            // marca o token como usado, pra impedir reaproveitamento do mesmo link
            verificacaoDao.marcarComoUsado(token);

            usuarioDao.marcarEmailComoVerificado(registro.getUsuarioId());

            response.getWriter().write("Email verificado com sucesso!");
        } else {
            response.getWriter().write("Token inválido ou expirado.");
        }
    }
}