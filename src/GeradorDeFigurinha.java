import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradorDeFigurinha {

    public void cria(InputStream inputStream, String nomeArquivo) throws IOException {

        /*
         * Faremos:
         * -> Leitura da imagem
         * -> Criar nova imagem em memória com transparência e redimencionada
         * -> Copiar a imagem original para a novo imagem (em memória)
         * -> Escrever uma frase na nova imagem
         * -> Salvar a nova imagem em um arquivo
         */

        // Lendo a imagem
        // lendo imagem do sistema operacional -->InputStream inputStream = new FileInputStream(new File("entrada/filme.jpg"));
        // lendo imagem pela URL -->InputStream inputStream = new URL("https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@.jpg").openStream();
        BufferedImage imagemOrinal = ImageIO.read(inputStream);

        // Criando nova imagem
        int largura = imagemOrinal.getWidth();
        int altura = imagemOrinal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // Passando ImgOrinal --> ImgNova
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOrinal, 0, 0, null);

        // Escrevendo a frase
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 64);
        graphics.setColor(Color.CYAN);
        graphics.setFont(fonte);
        graphics.drawString("Topster", (largura / 2) - 120, novaAltura - 90);

        // Salvando a nova imagem
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));
    }
}
