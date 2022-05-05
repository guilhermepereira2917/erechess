package partida.model.pecas;

import java.util.ArrayList;
import partida.model.Casa;
import partida.model.Coordenada;
import enums.Cores;
import enums.TiposMovimentoEspecial;
import partida.model.*;

public abstract class Peca {

    protected Cores cor; // 0 = branco; 1 = preto;
    protected Casa casa;

    public Peca(Cores cor) {
        this.cor = cor;
    }

    public Peca(Peca peca) {
        this.cor = peca.getCor();
    }

    public boolean validarMovimento(Movimento movimento) {
        ArrayList<Movimento> movimentosValidos = this.movimentosValidos(getTabuleiro());
        for (Movimento valido : movimentosValidos) {
            if (valido.toString().equals(movimento.toString())) {

                Tabuleiro tabuleiroClone = new Tabuleiro(getTabuleiro());
                Movimento movimentoClone = new Movimento(tabuleiroClone, movimento);
                movimentoClone.getCasaInicial().getPeca().realizarMovimento(movimentoClone, false);

                return !tabuleiroClone.verificarXeque(cor);
            }
        }
        return false;
    }

    public boolean realizarMovimento(Movimento movimento) {
        return realizarMovimento(movimento, true);
    }

    public boolean realizarMovimento(Movimento movimento, boolean fazerValidacao) {
        if (fazerValidacao && !validarMovimento(movimento)) {
            return false;
        }

        if (!movimento.getCasaFinal().estaVazia()) {
            capturar(movimento.getCasaFinal().getPeca());
        }

        movimento.getCasaFinal().setPeca(this);
        movimento.getCasaInicial().setPeca(null);

        return true;
    }

    public void capturar(Peca peca) {
        getTabuleiro().removerPeca(peca);
        peca.getCasa().setPeca(null);
    }

    public abstract ArrayList<Movimento> movimentosValidos(Tabuleiro tabuleiro);

    public ArrayList<Movimento> movimentosLinearesValidos(Tabuleiro tabuleiro, ArrayList<Coordenada> direcoes) {
        ArrayList<Movimento> movimentos = new ArrayList();
        Casa[][] casas = tabuleiro.getCasas();

        for (Coordenada direcao : direcoes) {
            int novoY = getPosicao().getY();
            int novoX = getPosicao().getX();
            while (true) {
                novoY += direcao.getY();
                novoX += direcao.getX();

                if (!(novoX >= 0 && novoX < 8 && novoY >= 0 && novoY < 8)) {
                    break;
                }

                Casa casaFinal = casas[novoY][novoX];
                Movimento movimento = new Movimento(getCasa(), casaFinal);

                if (casaFinal.estaVazia()) {
                    movimentos.add(movimento);
                } else {
                    if (casaFinal.getPeca().getCor() != getCor()) {
                        movimentos.add(movimento);
                    }
                    break;
                }

            }
        }

        return movimentos;
    }

    public Cores getCor() {
        return cor;
    }

    public void setCor(Cores cor) {
        this.cor = cor;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Coordenada getPosicao() {
        if (casa == null) {
            return null;
        }
        return casa.getCoordenada();
    }

    public Tabuleiro getTabuleiro() {
        return getCasa().getTabuleiro();
    }

}
