package partida.model.pecas;

import java.util.ArrayList;
import partida.model.Casa;
import partida.model.Coordenada;
import enums.Cores;
import partida.model.Movimento;
import partida.model.Tabuleiro;

public class Dama extends Peca {

    public Dama(Cores cor) {
        super(cor);
    }

    
    public Dama(Peca peca) {
        super(peca);
    }
    
    @Override
    public ArrayList<Movimento> movimentosValidos(Tabuleiro tabuleiro) {
        ArrayList<Movimento> movimentosValidos = new ArrayList();

        ArrayList<Coordenada> direcoes = new ArrayList();
        direcoes.add(new Coordenada(0, 1));
        direcoes.add(new Coordenada(0, -1));
        direcoes.add(new Coordenada(-1, 0));
        direcoes.add(new Coordenada(1, 0));
        direcoes.add(new Coordenada(1, 1));
        direcoes.add(new Coordenada(1, -1));
        direcoes.add(new Coordenada(-1, -1));
        direcoes.add(new Coordenada(-1, 1));

        movimentosValidos.addAll(movimentosLinearesValidos(tabuleiro, direcoes));

        return movimentosValidos;
    }

}
