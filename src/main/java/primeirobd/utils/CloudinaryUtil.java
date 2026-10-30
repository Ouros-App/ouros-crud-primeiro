package primeirobd.utils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.util.Map;

public class CloudinaryUtil {

    // Carrega as variáveis do arquivo .env.
    // ignoreIfMissing() permite iniciar mesmo se o arquivo não existir, mo dahora ;)
    public static final  Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    // Procura uma configuração primeiro nas variáveis de ambiente.
    // Se não encontrar, procura no arquivo .env.
    private static String config(String chave) {
        String valor = System.getenv(chave);

        // Se a variável não existir no ambiente ou estiver vazia,
        // tenta buscar o mesmo nome dentro do arquivo .env.
        if (valor == null || valor.isBlank()) {
            valor = dotenv.get(chave);
        }

        // Impede que o programa tente conectar ao Cloudinary
        // sem uma das configurações obrigatórias.
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException(
                    "Configuração ausente: " + chave
            );
        }

        return valor;
    }

    // Cria uma única instância do Cloudinary para ser reutilizada.
    private static final Cloudinary CLOUDINARY = criarCloudinary();

    // Monta o objeto responsável pela conexão com o Cloudinary.
    private static Cloudinary criarCloudinary() {

        /*
         ObjectUtils.asMap() organiza as credenciais e opções
         em pares de chave e valor.
         */

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", config("CLOUDINARY_CLOUD_NAME"),
                "api_key", config("CLOUDINARY_API_KEY"),
                "api_secret", config("CLOUDINARY_API_SECRET"),
                "secure", true
        ));
    }

    // Recebe a imagem em formato de bytes e envia ao Cloudinary.
    public static String enviarFoto(byte[] arquivo)
            throws IOException {

        // uploader().upload() envia a imagem para o serviço.
        // O resultado é um mapa com informações do arquivo enviado.
        Map resultado = CLOUDINARY.uploader().upload(
                arquivo,
                ObjectUtils.asMap(

                        // Organiza as imagens dentro desta pasta
                        "folder", "interback/perfis",

                        // Informa que o arquivo deve ser tratado como uma imagem, afinal... eh uma imagem
                        "resource_type", "image"
                )
        );

        // O Cloudinary retorna a URL segura da imagem, o to string permite salvar no banco
        return resultado.get("secure_url").toString();
    }

    // Impede que outras classes criem instâncias desta classe.
    // Como os métodos são estáticos, não precisamos de um objeto •ᴗ•
    private CloudinaryUtil() {
    }
}
