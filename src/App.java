import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {
		/*
		 * Fazendo uma conexao Http (Protocolo de Comunicacao por Hipertexto).
		 * Para Buscar os top 250 Filmes. Faremos:
		 * -> Extrair: Titulo, poster, e classificacao.
		 * -> Manipular os dados recebidos.
		 */

		/*
		 * Outras url's para o IMDB:
		 * https://alura-filmes.herokuapp.com/conteudos
		 * https://alura-imdb-api.herokuapp.com/movies
		 * https://api.mocki.io/v2/549a5d8b
		 * https://alura-filmes.herokuapp.com/conteudos
		 * https://raw.githubusercontent.com/alexfelipe/imersao-java/json/top250.json
		 */

		Scanner escolha = new Scanner(System.in);
		System.out.println("Você quer gerar figurinhas de qual API?\n\t[1] - IMDB\n\t[2] - NASA");
		String url = "null";
		ExtratorDeConteudo extrator = null;
		char opcao;
		do {
			opcao = escolha.next().charAt(0);
			switch (opcao) {
				case '1':
					url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
					extrator = new ExtratorConteudoIMDB();
					break;

				case '2':
					url = "https://api.nasa.gov/planetary/apod?api_key=6sZ7dQbwKbkXqlUYV51uu2nYkqVyVheS2BTK62fl&start_date=2022-06-12&end_date=2022-06-24";
					extrator = new ExtratorConteudoNasa();
					break;

				default:
					System.out.println("Opção inválida, por favor insira novamente");
			}
			
		} while (opcao != '1' && opcao != '2');
		escolha.close();

		var http = new ClienteHttp();
		String json = http.buscaDados(url);

		// Exibição
		List<Conteudo> conteudos = extrator.extraiConteudos(json);
		var gerador = new GeradorDeFigurinha();
		for (Conteudo conteudo : conteudos) {

			String urlImagem = conteudo.getUrlImagem();
			InputStream inputStream;
			try {
				inputStream = new URL(urlImagem).openStream();
			} catch (FileNotFoundException ex) { // Caso a imagem saia do ar
				inputStream = new FileInputStream(new File("saida/404.jpg"));
			}

			String nomeArquivo = "saida/" + conteudo.getTitulo() + ".png";
			nomeArquivo = nomeArquivo.replaceAll(":", " -");

			System.out.println(conteudo.getTitulo());
			gerador.cria(inputStream, nomeArquivo);
			System.out.println();
		}
	}
}