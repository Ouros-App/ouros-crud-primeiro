package primeirobd.utils;

import primeirobd.utils.EmailService;
import primeirobd.utils.TokenUtil;
import primeirobd.validadorREGEX.Validador;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
    servlet responsavel por receber o cadastro do usuário
    valida os dados e DISPARA O EMAIL(finalmente)
 */
@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Pega todos os campos enviados pelo formulário de cadastro.
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String senha = request.getParameter("senha");

        response.setContentType("text/plain;charset=UTF-8");
        // --- Validação de todos os campos, usando a classe Validador ---
        // Cada isXxxValido(...) já existe pronto -- aqui só chamamos.
        StringBuilder erros = new StringBuilder();

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

        // quando um campo falha
        if (erros.length() > 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Erro no cadastro:\n" + erros);
            return;
        }

        // passo 1 salvar o usuario no banco

        // passo 2 token aleatorio
        String token = TokenUtil.gerarToken();

        // passo 3 salvar token no banco

        // passo 4 envia o email (eba)
        EmailService.enviarEmailVerificacao(email, token);

        response.getWriter().write("Cadastro realizado! Verifique seu email para confirmar.");
    }

}
