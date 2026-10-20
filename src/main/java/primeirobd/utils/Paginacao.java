package primeirobd.utils;

import jakarta.servlet.http.HttpServletRequest;

public class Paginacao {
    private final int pagina;
    private final int tamanho;
    private final int total;
    private final int totalPaginas;

    public Paginacao(int paginaPedida, int tamanho, int total) {
        this.tamanho = tamanho;
        this.total = total;
        this.totalPaginas = Math.max(1, (int) Math.ceil((double) total / tamanho));
        this.pagina = Math.min(Math.max(1, paginaPedida), totalPaginas);
    }

    // lê ?pagina=N da URL com segurança
    public static int lerPagina(HttpServletRequest request) {
        try {
            return Math.max(1, Integer.parseInt(request.getParameter("pagina")));
        } catch (NumberFormatException e) {
            return 1;
        }
    }
    public int getPagina() { return pagina; }
    public int getTamanho() { return tamanho; }
    public int getTotal() { return total; }
    public int getTotalPaginas() { return totalPaginas; }

    public int getOffset() { return (pagina - 1) * tamanho; }
    public int getInicio() { return total == 0 ? 0 : getOffset() + 1; }
    public int getFim() { return Math.min(pagina * tamanho, total); }

    public boolean isTemAnterior() { return pagina > 1; }
    public boolean isTemProxima() { return pagina < totalPaginas; }

    public int getJanelaFim() { return Math.min(Math.max(1, pagina - 2) + 4, totalPaginas); }
    public int getJanelaInicio() { return Math.max(1, getJanelaFim() - 4); }


}
