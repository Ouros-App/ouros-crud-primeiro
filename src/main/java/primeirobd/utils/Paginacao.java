package primeirobd.utils;

import jakarta.servlet.http.HttpServletRequest;

public class Paginacao {

    private final int pagina;
    private final int tamanho;
    private final int total;
    private final int totalPaginas;

    public Paginacao(int paginaPedida, int tamanho, int total) {

        this.tamanho = Math.max(1, tamanho);
        this.total = Math.max(0, total);

        this.totalPaginas = Math.max(
                1,
                (int) Math.ceil((double) this.total / this.tamanho)
        );

        this.pagina = Math.min(
                Math.max(1, paginaPedida),
                totalPaginas
        );
    }

    // Lê ?pagina=N da URL com segurança
    public static int lerPagina(HttpServletRequest request) {

        try {
            String parametro = request.getParameter("pagina");

            if (parametro == null || parametro.isBlank()) {
                return 1;
            }

            return Math.max(1, Integer.parseInt(parametro));

        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public int getPagina() {
        return pagina;
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getOffset() {
        return (pagina - 1) * tamanho;
    }

    public int getInicio() {
        return total == 0 ? 0 : getOffset() + 1;
    }

    public int getFim() {
        return Math.min(pagina * tamanho, total);
    }

    public boolean isTemAnterior() {
        return pagina > 1;
    }

    public boolean isTemProxima() {
        return pagina < totalPaginas;
    }

    public int getJanelaInicio() {
        return Math.max(1, pagina - 2);
    }

    public int getJanelaFim() {
        return Math.min(
                totalPaginas,
                getJanelaInicio() + 4
        );
    }
}