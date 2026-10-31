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
   Recebe o cadastro do administrador, valida os dados e grava as informações
   no banco. O telefone é salvo em uma tabela separada depois do cadastro.
 */
@WebServlet(name = "cadastro", value = "/cadastro")
public class CadastroServlet extends HttpServlet {

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

        // Lê os dados enviados pelo formulário de cadastro.
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String senha = request.getParameter("senha");

        response.setContentType("text/plain;charset=UTF-8");

        StringBuilder erros = new StringBuilder();
        String confirmarSenha = request.getParameter("confirmarSenha");

        // Reúne todas as mensagens de validação antes de responder ao usuário.
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
        if (senha == null || !senha.equals(confirmarSenha)) {
            erros.append("- As senhas não coincidem\n");
        }

        // Interrompe o cadastro quando algum campo não atende às regras.
        if (erros.length() > 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Erro no cadastro:\n" + erros);
            return;
        }

        // Evita cadastros duplicados por e-mail ou CPF.
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

        // A senha é armazenada apenas depois de ser protegida por hash.
        String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

        // Grava os dados principais do administrador.
        Administrador adm = new Administrador();
        adm.setNome(nome.trim());
        adm.setCpf(cpf);
        adm.setEmail(email);
        adm.setSenha(senhaHash);

        int administradorId = dao.insert(adm);

        // O telefone fica em uma tabela própria, ligada ao administrador.
        new TelefoneAdminDAO().insert(administradorId, telefone);

        response.getWriter().write("Administrador cadastrado com sucesso!");


    }
}