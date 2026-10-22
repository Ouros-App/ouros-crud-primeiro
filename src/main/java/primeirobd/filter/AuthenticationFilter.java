
package primeirobd.filter;

import java.io.IOException;
import java.util.Set;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private static final Set<String> ROTAS_PUBLICAS = Set.of(
            "/",
            "/index",
            "/index.jsp",
            "/login",
            "/login.jsp",
            "/cadastro",
            "/cadastro.jsp",
            "/verificar",
            "/recuperar-senha",
            "/redefinir-senha"
    );

    @Override
    public void doFilter(
            jakarta.servlet.ServletRequest request,
            jakarta.servlet.ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contexto = req.getContextPath();
        String caminho = req.getRequestURI()
                .substring(contexto.length());

        // Permite arquivos estáticos, como CSS e imagens.
        if (ehArquivoEstatico(caminho)) {
            chain.doFilter(request, response);
            return;
        }

        // Permite acesso às rotas públicas.
        if (ROTAS_PUBLICAS.contains(caminho)) {
            chain.doFilter(request, response);
            return;
        }

        // Verifica se existe uma sessão autenticada.
        HttpSession sessao = req.getSession(false);

        boolean autenticado = sessao != null
                && sessao.getAttribute("usuarioId") != null;

        if (autenticado) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(contexto + "/login");
        }
    }

    private boolean ehArquivoEstatico(String caminho) {
        String c = caminho.toLowerCase();

        return c.startsWith("/img/")
                || c.startsWith("/css/")
                || c.startsWith("/js/")
                || c.endsWith(".css")
                || c.endsWith(".js")
                || c.endsWith(".png")
                || c.endsWith(".jpg")
                || c.endsWith(".jpeg")
                || c.endsWith(".gif")
                || c.endsWith(".svg")
                || c.endsWith(".ico")
                || c.endsWith(".woff")
                || c.endsWith(".woff2");
    }
}
