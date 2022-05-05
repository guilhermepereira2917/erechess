package partida.model.pecas;

import java.util.ArrayList;
import partida.model.Casa;
import partida.model.Coordenada;
import enums.Cores;
import partida.model.Movimento;
import partida.model.Tabuleiro;

public class Torre extends Peca {

    private boolean movido = false;

    public Torre(Cores cor) {
        super(cor);
    }
     
    public Torre(Peca peca) {
        super(peca);
        Torre torre = (Torre) peca;
        this.movido = torre.isMovido();
    }

    @Override
    public ArrayList<Movimento> movimentosValidos(Tabuleiro tabuleiro) {
        ArrayList<Movimento> movimentosValidos = new ArrayList();

        ArrayList<Coordenada> direcoes = new ArrayList();
        direcoes.add(new Coordenada(0, 1));
        direcoes.add(new Coordenada(0, -1));
        direcoes.add(new Coordenada(-1, 0));
        direcoes.add(new Coordenada(1, 0));

        movimentosValidos.addAll(movimentosLinearesValidos(tabuleiro, direcoes));

        return movimentosValidos;
    }

    @Override
    public void setCasa(Casa casa) {
        if (getPosicao() != null) {
            movido = true;
        }

        this.casa = casa;
    }

    protected boolean isMovido() {
        return movido;
    }

}
