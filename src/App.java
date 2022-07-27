import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {
		/*Fazendo uma conexao Http (Protocolo de Comunicacao por Hipertexto).
		 * Para Buscar os top 250 Filmes. Faremos:
		 * -> Extrair: Titulo, poster, e classificacao.
		 * -> Manipular os dados recebidos.
		 */

		/*Outras url's:
		 * https://alura-filmes.herokuapp.com/conteudos
		 * https://alura-imdb-api.herokuapp.com/movies
		 * https://api.mocki.io/v2/549a5d8b
		 * https://alura-filmes.herokuapp.com/conteudos
		 * https://raw.githubusercontent.com/alexfelipe/imersao-java/json/top250.json
		 */
		
		String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
		URI endereco = URI.create(url);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest requisicao = HttpRequest.newBuilder(endereco).GET().build();
		HttpResponse<String> response = client.send(requisicao, BodyHandlers.ofString());
		String body = response.body();
		//System.out.println(body);

		//Extraindo
		JsonParser parser = new JsonParser();
		List<Map<String,String>> listaDeFilmes = parser.parse(body);

		//Exibição
		var gerador = new GeradorDeFigurinha();
		for (Map<String,String> filme: listaDeFilmes) {
			
			String urlImagem = "https://m.media-amazon." + filme.get("image").split("\\.")[2] + "._V1_UX1383_1383,2048_AL_.jpg";
			String titulo = filme.get("title");
			InputStream inputStream;
			try{
				inputStream = new URL(urlImagem).openStream();
			}catch (FileNotFoundException e){ //Caso a imagem saia do ar
				inputStream = new FileInputStream(new File("saida/404.jpg"));
			} 
			
			String nomeArquivo = "saida/" + titulo + ".png";
			nomeArquivo = nomeArquivo.replaceAll(":"," -");

			System.out.println(titulo);
			gerador.cria(inputStream, nomeArquivo);
			System.out.println(filme.get("imDbRating"));
			System.out.println();
		}
	}
}