package partida.view;

import partida.model.Casa;
import partida.model.pecas.Peca;
import java.awt.Color;
import java.awt.Cursor;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import enums.Cores;
import util.RedimensionarImagem;

public class ButtonCasa extends JButton implements Observer {

    private Casa casa;

    public ButtonCasa(Casa casa) {
        super();
        casa.addObserver(this);

        this.casa = casa;

        // Configurações de aparência do botão permanentes
        setFocusable(false);
        setBackground(casa.getCor() == Cores.BRANCO ? Color.WHITE : Color.BLACK);

        // Configuraçóes de botões não permanentes
        atualizarAparencia();

    }

    @Override
    public void update(Observable o, Object arg) {
        atualizarAparencia();
    }

    public void atualizarAparencia() {
        redimensionarIcone(getSize().width, 0.85);

        setToolTipText(casa.toString());
        if (!casa.estaVazia()) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        
    }

    public ImageIcon carregarIcone() {
        if (casa.estaVazia()) {
            return null;
        }

        Peca peca = casa.getPeca();

        String caminho = "src/img/pieces/";
        caminho += (peca.getCor() == Cores.BRANCO ? "white" : "black");
        caminho += "/" + peca.getClass().getSimpleName() + ".png";

        return new ImageIcon(caminho);
    }

    /* Função necessária para redimensionar os icones, 
    tendo em vista que as imagens padrão são maiores que os botões */
    public void redimensionarIcone(int tamanhoMaximo, double porcentagem) {
        ImageIcon imageIcon = carregarIcone();
        setIcon(imageIcon);

        if (imageIcon == null || tamanhoMaximo == 0) {
            return;
        }

        int tamanhoFinal = (int) (tamanhoMaximo * porcentagem);
        ImageIcon novaImagem = RedimensionarImagem.redimensionarImagem(imageIcon, tamanhoFinal, tamanhoFinal);
        setIcon(novaImagem);
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

}
