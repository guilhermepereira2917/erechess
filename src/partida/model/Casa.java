package partida.model;

import partida.model.pecas.Dama;
import partida.model.pecas.Rei;
import partida.model.pecas.Cavalo;
import partida.model.pecas.Bispo;
import partida.model.pecas.Torre;
import partida.model.pecas.Peao;
import partida.model.pecas.Peca;
import enums.Cores;
import java.util.ArrayList;
import java.util.Observable;
import util.AuxiliarMovimentos;

public class Casa extends Observable {

    private Coordenada coordenada;
    private Cores cor = null; // 0 = branca; 1 = preta;
    private Peca peca;

    private Tabuleiro tabuleiro;

    public Casa(Tabuleiro tabuleiro, Coordenada coordenada, Cores cor) {
        this.tabuleiro = tabuleiro;
        this.coordenada = coordenada;
        this.cor = cor;
        this.peca = null;
    }

    public Casa(Tabuleiro tabuleiro, Coordenada coordenada) {
        this.tabuleiro = tabuleiro;
        this.coordenada = coordenada;
        this.peca = null;
    }

    public boolean estaVazia() {
        return peca == null;
    }

    public boolean estaSobAtaque(Tabuleiro tabuleiro, Cores cor) {
        /* Verificação se a casa está atacada por: Damas, Torres, Bispos */
        Casa[][] casas = tabuleiro.getCasas();

        ArrayList<Coordenada> direcoes = new ArrayList();
        direcoes.add(new Coordenada(0, 1));
        direcoes.add(new Coordenada(0, -1));
        direcoes.add(new Coordenada(-1, 0));
        direcoes.add(new Coordenada(1, 0));
        direcoes.add(new Coordenada(1, 1));
        direcoes.add(new Coordenada(1, -1));
        direcoes.add(new Coordenada(-1, -1));
        direcoes.add(new Coordenada(-1, 1));

        for (Coordenada direcao : direcoes) {
            int novoY = getCoordenada().getY();
            int novoX = getCoordenada().getX();
            while (true) {
                novoY += direcao.getY();
                novoX += direcao.getX();

                if (!(novoX >= 0 && novoX < 8 && novoY >= 0 && novoY < 8)) {
                    break;
                }

                Casa casa = casas[novoY][novoX];
                if (!casa.estaVazia()) {
                    Peca pecaEncontrada = casa.getPeca();
                    if (pecaEncontrada.getCor() != cor) {
                        /* 
                            Como o método movimentosPossiveis da classe Peca nao retorna 
                            casas com pecas sendo da mesma cor, este if foi necessário pois
                            dependendo de qual direcao se estiver checando, somente peças de
                            classes específicas seriam capazes de defender esta casa.
                         */
                        if (pecaEncontrada instanceof Dama) {
                            return true;
                        } else if (pecaEncontrada instanceof Torre) {
                            if (direcao.getY() == 0 || direcao.getX() == 0) {
                                return true;
                            }
                        } else if (pecaEncontrada instanceof Bispo) {
                            if (direcao.getY() != 0 && direcao.getX() != 0) {
                                return true;
                            }
                        }
                    }
                    break;
                }
            }
        }

        /* Verificação se a casa está atacada por: Cavalos */
        direcoes = new ArrayList();
        direcoes.add(new Coordenada(2, 1));
        direcoes.add(new Coordenada(1, 2));
        direcoes.add(new Coordenada(-2, 1));
        direcoes.add(new Coordenada(-1, 2));
        direcoes.add(new Coordenada(2, -1));
        direcoes.add(new Coordenada(1, -2));
        direcoes.add(new Coordenada(-2, -1));
        direcoes.add(new Coordenada(-1, -2));

        for (Coordenada direcao : direcoes) {
            int novoY = getCoordenada().getY() + direcao.getY();
            int novoX = getCoordenada().getX() + direcao.getX();

            if (!(novoX >= 0 && novoX < 8 && novoY >= 0 && novoY < 8)) {
                continue;
            }

            Casa casa = casas[novoY][novoX];
            if (!casa.estaVazia()) {
                Peca pecaEncontrada = casa.getPeca();
                if (pecaEncontrada.getCor() != cor && pecaEncontrada instanceof Cavalo) {
                    return true;
                }
            }
        }

        /* Verificação se a casa está atacada por: Peões */
        direcoes = new ArrayList();
        direcoes.add(new Coordenada(1, 1));
        direcoes.add(new Coordenada(1, -1));

        // Inverte o array de capturas, pois os peões brancos e negros se movem em direções opostas
        if (cor == Cores.BRANCO) {
            direcoes = AuxiliarMovimentos.inverter(direcoes);
        }

        for (Coordenada direcao : direcoes) {

            int novoY = direcao.getY() + getCoordenada().getY();
            int novoX = direcao.getX() + getCoordenada().getX();

            if (novoX >= 0 && novoX < 8 && novoY >= 0 && novoY < 8) {
                Casa casa = casas[novoY][novoX];
                Peca peca = casa.getPeca();
                if (!casa.estaVazia() && peca instanceof Peao && peca.getCor() != cor) {
                    return true;
                }
            }
        }

        /* Verificação se a casa está atacada pelo rei adversário */
        direcoes = new ArrayList();
        direcoes.add(new Coordenada(1, 0));
        direcoes.add(new Coordenada(1, 1));
        direcoes.add(new Coordenada(0, 1));
        direcoes.add(new Coordenada(-1, 1));
        direcoes.add(new Coordenada(-1, -1));
        direcoes.add(new Coordenada(1, -1));
        direcoes.add(new Coordenada(-1, 0));
        direcoes.add(new Coordenada(0, -1));

        for (Coordenada direcao : direcoes) {

            int novoY = direcao.getY() + getCoordenada().getY();
            int novoX = direcao.getX() + getCoordenada().getX();

            if (novoX >= 0 && novoX < 8 && novoY >= 0 && novoY < 8) {
                Casa casa = casas[novoY][novoX];
                Peca peca = casa.getPeca();
                if (!casa.estaVazia() && peca instanceof Rei && peca.getCor() != cor) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String letras[] = {"a", "b", "c", "d", "e", "f", "g", "h"};
        return letras[getCoordenada().getX()] + (Math.abs(getCoordenada().getY() - 7) + 1);
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
        if (peca != null) {
            peca.setCasa(this);
        }
        setChanged();
        notifyObservers();
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public Cores getCor() {
        return cor;
    }

    public void setCor(Cores cor) {
        this.cor = cor;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }
}
