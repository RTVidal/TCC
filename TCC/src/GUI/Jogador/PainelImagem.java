package GUI.Jogador;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Rafael
 */
public class PainelImagem extends JPanel {

    private Image image;

    public PainelImagem(Image imagem) {

        this.image = imagem;

        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        
        g.drawImage(image, 0, 0, this);
        
        super.paintComponent(g);
    }
    
    //Ajustar a largura da imagem pelo tamanho da mesma
    @Override
    public int getWidth() {
        return image.getWidth(this);
    }
    
    //Ajustar a altura da imagem a partir da altura da mesma
    @Override
    public int getHeight() {
        return image.getHeight(this);
    }
}
