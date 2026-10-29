package primeirobd.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Ajuda os formulários que abrem dentro de um modal (iframe).
 * Quando o salvamento dá certo, a resposta fecha o modal e recarrega a listagem
 * na janela inteira. Quando dá erro, o servlet devolve o próprio formulário
 * com as mensagens, que continua aparecendo dentro do modal.
 */
public final class ModalUtil {

    private ModalUtil() {
    }

    public static void concluir(HttpServletRequest request, HttpServletResponse response, String caminho)
            throws IOException {
        String destino = request.getContextPath() + caminho;

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<!DOCTYPE html><html lang=\"pt-br\"><head><meta charset=\"UTF-8\"></head><body>"
                + "<script>window.top.location.replace(\"" + escaparJs(destino) + "\");</script>"
                + "<noscript><a href=\"" + escaparHtml(destino) + "\">Continuar</a></noscript>"
                + "</body></html>");
    }

    private static String escaparJs(String texto) {
        return texto.replace("\\", "\\\\").replace("\"", "\\\"").replace("<", "\\u003c");
    }

    private static String escaparHtml(String texto) {
        return texto.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;");
    }
}
