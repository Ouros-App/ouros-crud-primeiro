package primeirobd.controller;

import org.mindrot.jbcrypt.BCrypt;
import primeirobd.model.Administrador;
import primeirobd.service.AdministradorDAO;
import primeirobd.service.TelefoneAdminDAO;
import primeirobd.utils.Validador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/*
    servlet responsavel por receber o cadastro do administrador
    valida os dados e DISPARA O EMAIL(finalmente)

    Validador        -> confere se os campos têm o formato correto (regex)
    AdministradorDAO -> conversa com o banco de dados
 */
@WebServlet(name = "cadastro", value = "/cadastro")
public class CadastroServlet extends HttpServlet {

    // objetos
    private final AdministradorDAO dao = new AdministradorDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {

        request.getRequestDispatcher("/WEB-INF/views/cadastro.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // campos do formulario
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String senha = request.getParameter("senha");

        response.setContentType("text/plain;charset=UTF-8");

        StringBuilder erros = new StringBuilder();

        // validar com o REGEX (eba ele ta aq)
        if (nome == null || nome.trim().isEmpty()) {
            erros.append("- Nome é obrigatório\n");
        }
        if (!Validador.isCpfValido(cpf)) {
            erros.append("- CPF inválido\n");
        }
        if (!Validador.isEmailValido(email)) {
            erros.append("- Email inválido\n");
        }
        if (!Validador.isTelefoneValido(telefone)) {
            erros.append("- Telefone inválido\n");
        }
        if (!Validador.isSenhaValida(senha)) {
            erros.append("- Senha não atende aos requisitos mínimos\n");
        }

        // se der qualquer erro eh pq ta errado
        if (erros.length() > 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Erro no cadastro:\n" + erros);
            return;
        }

        // nao pode ter dois caras com o mesmo email ou cpf neh
        if (dao.existsByEmail(email)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("Já existe um cadastro com esse email.");
            return;
        }
        if (dao.existsByCpf(cpf)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("Já existe um cadastro com esse CPF.");
            return;
        }

        // senha criptografada
        String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

        // salva o administrador
        Administrador adm = new Administrador();
        adm.setNome(nome.trim());
        adm.setCpf(cpf);
        adm.setEmail(email);
        adm.setSenha(senhaHash);

        int administradorId = dao.insert(adm);

        // salva o telefone na tabela telefone_admin
        new TelefoneAdminDAO().insert(administradorId, telefone);


    }
}