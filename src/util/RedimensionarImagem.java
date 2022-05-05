package util;

import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class RedimensionarImagem {

    public static ImageIcon redimensionarImagem(ImageIcon imageIcon, int novaLargura, int novaAltura) {
        Image image = imageIcon.getImage();
        Image novaImagem = image.getScaledInstance(novaLargura, novaAltura, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(novaImagem);
        return imageIcon;
    }
}
