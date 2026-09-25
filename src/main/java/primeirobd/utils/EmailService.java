package primeirobd.utils;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/*
Classe responsável por enviar o email de verificação de cadastro.

 * <dependency>
 *     <groupId>org.eclipse.angus</groupId>
 *     <artifactId>angus-mail</artifactId>
 *     <version>2.0.3</version>
 * </dependency>
 */
public class EmailService {
    // carregando o .env
    private static final Dotenv dotenv = Dotenv.load();

    // Endereço e porta do servidor SMTP que vai enviar o email.
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    // Conta de email que vai APARECER como remetente
    private static final String USUARIO = dotenv.get("EMAIL_USUARIO");
    private static final String SENHA = dotenv.get("EMAIL_SENHA");

    /*
      Envia o email de verificação para o destinatário, contendo
      um link com o token gerado para aquele cadastro.
     */
    public static void enviarEmailVerificacao(String destinatario, String token) {

        // "Properties" configura como a conexão com o servidor SMTP deve se comportar
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");            // precisa de usuário/senha
        props.put("mail.smtp.starttls.enable", "true");  // conexão criptografada
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        /*
        "Session" representa a sessão de envio de email configurada acima.
         O "Authenticator" é chamado internamente pela biblioteca sempre
         que ela precisa se autenticar no servidor SMTP.
         */
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USUARIO, SENHA);
            }
        });

        try {
            // Monta o link que o usuário vai clicar.
            String link = "http://localhost:8080/InterModulo/verificar?token=" + token;

            // "MimeMessage" é o objeto que representa o email em si:
            // remetente, destinatário, assunto e corpo da mensagem.
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(USUARIO));

            // Pode receber mais de um destinatário separado por vírgula;
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destinatario)
            );

            message.setSubject("Confirme seu email");

            message.setText(
                    "Clique no link para confirmar seu cadastro:\n\n" + link +
                            "\n\nEsse link expira em 24 horas."
            );

            // Envia o email de fato, conectando no servidor SMTP configurado.
            Transport.send(message);

        } catch (MessagingException e) {

            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar email de verificação", e);
        }
    }
}