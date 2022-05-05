package partida.model.pecas;

import java.util.ArrayList;
import partida.model.Casa;
import partida.model.Coordenada;
import enums.Cores;
import partida.model.Movimento;
import partida.model.Tabuleiro;

public class Cavalo extends Peca {

    public Cavalo(Cores cor) {
        super(cor);
    }

    
    public Cavalo(Peca peca) {
        super(peca);
    }
    
    @Override
    public ArrayList<Movimento> movimentosValidos(Tabuleiro tabuleiro) {
        ArrayList<Movimento> movimentosValidos = new ArrayList();
        Casa[][] casas = tabuleiro.getCasas();

        ArrayList<Coordenada> direcoes = new ArrayList();
        direcoes.add(new Coordenada(2, 1));
        direcoes.add(new Coordenada(1, 2));
        direcoes.add(new Coordenada(-2, 1));
        direcoes.add(new Coordenada(-1, 2));
        direcoes.add(new Coordenada(2, -1));
        direcoes.add(new Coordenada(1, -2));
        direcoes.add(new Coordenada(-2, -1));
        direcoes.add(new Coordenada(-1, -2));

        for (Coordenada direcao : direcoes) {

            int novoY = direcao.getY() + getPosicao().getY();
            int novoX = direcao.getX() + getPosicao().getX();

            if (novoX >= 0 && novoX < 8 && novoY >= 0 && novoY < 8) {
                Casa casa = casas[novoY][novoX];
                Movimento movimento = new Movimento(getCasa(), casa);
                if (casa.estaVazia() || casa.getPeca().getCor() != getCor()) {
                    movimentosValidos.add(movimento);
                }
            }
        }

        return movimentosValidos;
    }

}
