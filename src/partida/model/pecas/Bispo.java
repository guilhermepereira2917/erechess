package partida.model.pecas;

import java.util.ArrayList;
import partida.model.Casa;
import partida.model.Coordenada;
import enums.Cores;
import partida.model.Movimento;
import partida.model.Tabuleiro;

public class Bispo extends Peca {

    public Bispo(Cores cor) {
        super(cor);
    }

    public Bispo(Peca peca) {
        super(peca);
    }
    
    @Override
    public ArrayList<Movimento> movimentosValidos(Tabuleiro tabuleiro) {
        ArrayList<Movimento> movimentosValidos = new ArrayList();

        ArrayList<Coordenada> direcoes = new ArrayList();
        direcoes.add(new Coordenada(1, 1));
        direcoes.add(new Coordenada(1, -1));
        direcoes.add(new Coordenada(-1, -1));
        direcoes.add(new Coordenada(-1, 1));

        movimentosValidos.addAll(movimentosLinearesValidos(tabuleiro, direcoes));

        return movimentosValidos;
    }

}
