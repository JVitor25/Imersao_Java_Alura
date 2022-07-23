import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {
		/*Fazendo uma conexao Http (Protocolo de Comunicacao por Hipertexto).
		 * Para Buscar os top 250 Filmes. Faremos:
		 * -> Extrair: Titulo, poster, e classificacao.
		 * -> Manipular os dados recebidos.
		 */

		/*Otras url's:
		 * https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060
		 * https://alura-imdb-api.herokuapp.com/movies
		 * https://api.mocki.io/v2/549a5d8b
		 * https://alura-filmes.herokuapp.com/conteudos
		 * https://raw.githubusercontent.com/alexfelipe/imersao-java/json/top250.json
		 */
		
		String url = "https://alura-filmes.herokuapp.com/conteudos";
		URI endereco = URI.create(url);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest requisicao = HttpRequest.newBuilder(endereco).GET().build();
		HttpResponse<String> response = client.send(requisicao, BodyHandlers.ofString());
		String body = response.body();
		// System.out.println(body);

		//Extraindo
		JsonParser parser = new JsonParser();
		List<Map<String,String>> listaDeFilmes = parser.parse(body);

		//Exibição
		for (Map<String,String> filme: listaDeFilmes) {
			System.out.println(filme.get("title"));
			System.out.println(filme.get("image"));
			System.out.println(filme.get("imDbRating"));
			System.out.println();
		}
	}
}