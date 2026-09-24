package primeirobd.utils;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;

/*
Classe responsável por enviar o email de verificação de cadastro.

 Usa a biblioteca Jakarta Mail para se conectar
 a um servidor SMTP e disparar o email
 */
public class EmailService {
    // carregando o .env
    private static final Dotenv dotenv = Dotenv.load();

    // variaveis e credenciais
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String USUARIO = dotenv.get("EMAIL_USUARIO");
    private static final String SENHA = dotenv.get("EMAIL_SENHA");

    public static void enviarEmailVerficacao(String destinatario, String token){
        /*
        "Properties" configura como a conexão com o servidor SMTP deve
        se comportar: exige autenticação, usa criptografia, etc.
        */
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);


        /*
        Session é a sesão de envio do email configurada acima
        o autenticator é chamado internamente pela biblioteca
        sempre que ela precisa se autenticar no SMPT
         */
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USUARIO, SENHA);
            }
        });

        try{
            /*
             o servlet "/verificar", passando o token como parâmetro de URL.
             Em produção, troque "localhost:8080/seuapp" pelo domínio real.
             */

            String link = "http://localhost:8080/seuapp/verificar?token=" + token;
            /*
            MimeMessage representa o email em si
            com remetente, destinatário, assunto e corpo da mensagem
             */
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USUARIO));

            /*
            pode receber mais de um destinatario separado por virgula
             */

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



        }catch(MessagingException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar email de verificação",e);
        }
    }
}
