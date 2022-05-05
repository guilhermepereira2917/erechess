package partida.model.pecas;

import java.util.ArrayList;
import partida.model.Casa;
import partida.model.Coordenada;
import enums.Cores;
import enums.TiposMovimentoEspecial;
import partida.model.Movimento;
import partida.model.Tabuleiro;

public class Rei extends Peca {

    private boolean movido = false;

    public Rei(Cores cor) {
        super(cor);
    }

    public Rei(Peca peca) {
        super(peca);
        Rei rei = (Rei) peca;
        this.movido = rei.seMexeu();
    }

    @Override
    public boolean realizarMovimento(Movimento movimento, boolean fazerValidacao) {
        if (!super.realizarMovimento(movimento, false)) {
            return false;
        }

        if (movimento.eEspecial()) {
            roque(movimento.getTipo());
        }

        return true;
    }

    private void roque(TiposMovimentoEspecial menorMaior) {
        Casa casas[][] = getTabuleiro().getCasas();

        int yRei = getPosicao().getY(), xRei = getPosicao().getX();
        Casa casaTorre, casaFinalTorre;

        if (menorMaior == TiposMovimentoEspecial.ROQUE_MENOR) {
            casaTorre = casas[yRei][7];
            casaFinalTorre = casas[yRei][xRei - 1];
        } else {
            casaTorre = casas[yRei][0];
            casaFinalTorre = casas[yRei][xRei + 1];
        }

        Torre torre = (Torre) casaTorre.getPeca();
        casaFinalTorre.setPeca(torre);
        casaTorre.setPeca(null);
    }

    @Override
    public ArrayList<Movimento> movimentosValidos(Tabuleiro tabuleiro) {
        ArrayList<Movimento> movimentosValidos = new ArrayList();
        Casa[][] casas = tabuleiro.getCasas();

        ArrayList<Coordenada> direcoes = new ArrayList();
        direcoes.add(new Coordenada(1, 0));
        direcoes.add(new Coordenada(1, 1));
        direcoes.add(new Coordenada(0, 1));
        direcoes.add(new Coordenada(-1, 1));
        direcoes.add(new Coordenada(-1, -1));
        direcoes.add(new Coordenada(1, -1));
        direcoes.add(new Coordenada(-1, 0));
        direcoes.add(new Coordenada(0, -1));

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

        if (verificarRoqueMenor(tabuleiro)) {
            Movimento roqueMenor = new Movimento(getCasa(), casas[getPosicao().getY()][getPosicao().getX() + 2]);
            roqueMenor.setTipo(TiposMovimentoEspecial.ROQUE_MENOR);
            movimentosValidos.add(roqueMenor);
        }

        if (verificarRoqueMaior(tabuleiro)) {
            Movimento roqueMenor = new Movimento(getCasa(), casas[getPosicao().getY()][getPosicao().getX() - 2]);
            roqueMenor.setTipo(TiposMovimentoEspecial.ROQUE_MAIOR);
            movimentosValidos.add(roqueMenor);
        }

        return movimentosValidos;
    }

    public boolean verificarRoqueMenor(Tabuleiro tabuleiro) {
        // Verifica se o rei já foi movido
        if (movido) {
            return false;
        }

        Casa[][] casas = tabuleiro.getCasas();
        Casa casaRei = casas[getPosicao().getY()][getPosicao().getX()];

        // Não permite o roque caso esteja em xeque
        if (casaRei.estaSobAtaque(tabuleiro, cor)) {
            return false;
        }

        int yTorre = getPosicao().getY();
        int xTorre = 7;

        // Verifica se a casa inicial da torre está vazia.
        if (casas[yTorre][xTorre].estaVazia()) {
            return false;
        }

        Torre torre = (Torre) casas[yTorre][xTorre].getPeca();

        // Verifica se a torre ja foi movida
        if (torre.isMovido()) {
            return false;
        }

        // Verifica se as casas de movimento estão vazias e não sob ataque
        for (int x = getPosicao().getX() + 1; x < xTorre; x++) {
            Casa casa = casas[yTorre][x];
            if (!casa.estaVazia() || casa.estaSobAtaque(tabuleiro, cor)) {
                return false;
            }
        }

        return true;
    }

    public boolean verificarRoqueMaior(Tabuleiro tabuleiro) {
        // Verifica se o rei já foi movido
        if (movido) {
            return false;
        }

        Casa[][] casas = tabuleiro.getCasas();
        Casa casaRei = casas[getPosicao().getY()][getPosicao().getX()];

        // Não permite o roque caso esteja em xeque
        if (casaRei.estaSobAtaque(tabuleiro, cor)) {
            return false;
        }

        int yTorre = getPosicao().getY();
        int xTorre = 0;

        // Verifica se a casa inicial da torre está vazia.
        if (casas[yTorre][xTorre].estaVazia()) {
            return false;
        }

        Torre torre = (Torre) casas[yTorre][xTorre].getPeca();

        // Verifica se a torre ja foi movida
        if (torre.isMovido()) {
            return false;
        }

        // Verifica se as casas de movimento estão vazias e não sob ataque
        for (int x = getPosicao().getX() - 1; x > xTorre; x--) {
            Casa casa = casas[yTorre][x];
            if (!casa.estaVazia() || casa.estaSobAtaque(tabuleiro, cor)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void setCasa(Casa casa) {
        if (getPosicao() != null) {
            movido = true;
        }

        this.casa = casa;
    }

    protected boolean seMexeu() {
        return movido;
    }

}
