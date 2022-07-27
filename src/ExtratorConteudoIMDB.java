import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorConteudoIMDB implements ExtratorDeConteudo{
    public List<Conteudo> extraiConteudos(String json) {
        // Extraindo
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeAtribudos = parser.parse(json);

        List<Conteudo> conteudos = new ArrayList<>();

        // Popular a lista de conteudos
        for (Map<String, String> atributos : listaDeAtribudos) {
            String titulo = atributos.get("title");
            String urlImagem = "https://m.media-amazon." + atributos.get("image")
                    .split("\\.")[2] + "._V1_UX1383_1383,2048_AL_.jpg"; 
            var conteudo = new Conteudo(titulo, urlImagem);

            conteudos.add(conteudo);
        }
        return conteudos;
    }
}
