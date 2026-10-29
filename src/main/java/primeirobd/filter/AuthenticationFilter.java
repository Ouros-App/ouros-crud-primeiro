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
import primeirobd.model.Administrador;
import primeirobd.service.AdministradorDAO;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private final AdministradorDAO administradorDao = new AdministradorDAO();

    private static final Set<String> ROTAS_PUBLICAS = Set.of(
            "/",
            "/index",
            "/index.jsp",
            "/login",
            "/login.jsp"
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
                && sessao.getAttribute("administradorId") != null;

        if (!autenticado) {
            res.sendRedirect(contexto + "/login");
            return;
        }

        /*
           Carrega o administrador logado e disponibiliza para todas as JSPs
           (foto e nome na barra lateral). O Perfil.java sobrescreve o atributo
           com a mesma informação, o que não causa problema.
         */
        int id = (Integer) sessao.getAttribute("administradorId");
        Administrador adm = administradorDao.buscarPorId(id);

        if (adm == null) {
            // administrador apagado do banco: encerra a sessão
            sessao.invalidate();
            res.sendRedirect(contexto + "/login");
            return;
        }

        req.setAttribute("administrador", adm);
        chain.doFilter(request, response);
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