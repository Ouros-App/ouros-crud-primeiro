package primeirobd.utils;

import primeirobd.model.ProprietarioGranja;
import primeirobd.service.ProprietarioGranjaDAO;
import primeirobd.service.VerificacaoEmailDAO;
import primeirobd.utils.EmailService;
import primeirobd.utils.TokenUtil;
import primeirobd.validadorREGEX.Validador;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/*
    servlet responsavel por receber o cadastro do usuário
    valida os dados e DISPARA O EMAIL(finalmente)

    Validador      -> confere se os campos têm o formato correto (regex)
    ProprietarioGranjaDAO -> conversa com o banco de dados
    TokenUtil      -> gera o token de verificação
    EmailService   -> envia o email com o link de verificação
 */
@WebServlet(name = "cadastro", value = "/cadastro")
public class CadastroServlet extends HttpServlet {

    // objeto
    private final ProprietarioGranjaDAO dao = new ProprietarioGranjaDAO();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        //campos do formulario
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String senha = request.getParameter("senha");
        String idGranjaParam = request.getParameter("idGranja");

        response.setContentType("text/plain;charset=UTF-8");

        StringBuilder erros = new StringBuilder();


        // validar com o REGEX (eba ele ta aq)
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

        int idGranja = 0;
        if (idGranjaParam == null || !idGranjaParam.matches("\\d+")) {
            erros.append("- Granja inválida\n");
        } else {
            idGranja = Integer.parseInt(idGranjaParam);
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

        //senha criptogradada
        String senhaHash = senha;

        // salva o usuario
        ProprietarioGranja prg = new ProprietarioGranja();
        prg.setNome(nome);
        prg.setCpf(cpf);
        prg.setEmail(email);
        prg.setSenha(senhaHash);
        prg.setIdGranja(idGranja);

        int usuarioId = dao.insert(prg);

        // token aleatorio
        String token = TokenUtil.gerarToken();



        VerificacaoEmailDAO verificacaoDao = new VerificacaoEmailDAO();
        verificacaoDao.salvarToken(usuarioId, token, LocalDateTime.now().plusHours(24));

        // envia o emial (hell yeah)
        EmailService.enviarEmailVerificacao(email, token);

        response.getWriter().write("Cadastro realizado! Verifique seu email para confirmar.");
    }

}