package partida.model.pecas;

import java.util.ArrayList;
import partida.model.Casa;
import partida.model.Coordenada;
import enums.Cores;
import enums.TiposMovimentoEspecial;
import partida.model.Movimento;
import partida.model.Tabuleiro;
import util.AuxiliarMovimentos;

public class Peao extends Peca {

    private boolean movido = false;

    public Peao(Cores cor) {
        super(cor);
    }

    public Peao(Peca peca) {
        super(peca);
        Peao peao = (Peao) peca;
        this.movido = peao.seMexeu();
    }

    @Override
    public boolean realizarMovimento(Movimento movimento, boolean fazerValidacao) {
        if (!super.realizarMovimento(movimento, false)) {
            return false;
        }

        if (movimento.eEspecial()) {
            TiposMovimentoEspecial tipo = movimento.getTipo();
            if (null != tipo) {
                switch (tipo) {
                    case DUPLO:
                        duplo();
                        break;
                    case EN_PASSANT:
                        enPassant();
                        break;
                    case COROACAO:
                        coroacao(movimento.getArgumentoOpcional());
                        break;
                    default:
                        break;
                }
            }
        }

        return true;
    }

    private void duplo() {
        Casa casaEnPassant = getTabuleiro().getCasas()[getPosicao().getY() + (cor == Cores.BRANCO ? 1 : -1)][getPosicao().getX()];
        getTabuleiro().setCasaEnPassant(casaEnPassant);
    }

    private void enPassant() {
        capturar(getTabuleiro().getCasas()[getPosicao().getY() + (cor == Cores.BRANCO ? 1 : -1)][getPosicao().getX()].getPeca());
    }

    private void coroacao(String opcao) {
        Peca pecaFinal = null;

        switch (opcao) {
            case "Dama":
            case "q":
                pecaFinal = new Dama(cor);
                break;
            case "Torre":
            case "r":
                pecaFinal = new Torre(cor);
                break;
            case "Bispo":
            case "b":
                pecaFinal = new Bispo(cor);
                break;
            case "Cavalo":
            case "n":
                pecaFinal = new Cavalo(cor);
                break;
            default:
                break;
        }

        pecaFinal.setCasa(casa);
        casa.setPeca(pecaFinal);
    }

    @Override
    public ArrayList<Movimento> movimentosValidos(Tabuleiro tabuleiro) {
        ArrayList<Movimento> movimentosValidos = new ArrayList();
        Casa[][] casas = tabuleiro.getCasas();

        ArrayList<Coordenada> direcoes = new ArrayList();
        direcoes.add(new Coordenada(1, 0));
        if (!movido) {
            direcoes.add(new Coordenada(2, 0));
        }

        if (getCor() == Cores.BRANCO) {
            direcoes = AuxiliarMovimentos.inverter(direcoes);
        }

        for (Coordenada direcao : direcoes) {
            int novoY = direcao.getY() + getPosicao().getY();
            int novoX = direcao.getX() + getPosicao().getX();

            if (novoX >= 0 && novoX < 8 && novoY >= 0 && novoY < 8) {
                Casa casa = casas[novoY][novoX];
                Movimento movimento = new Movimento(getCasa(), casa);
                if (casa.estaVazia()) {
                    if (!movimentosValidos.isEmpty()) {
                        movimento.setTipo(TiposMovimentoEspecial.DUPLO);
                    } else if (novoY == 7 || novoY == 0) {
                        movimento.setTipo(TiposMovimentoEspecial.COROACAO);
                    }
                    movimentosValidos.add(movimento);
                }
            }

            /* Termina a checagem de movimentos, pois caso a lista esteja vazia
            significa que alguma peça está bloqueando sua frente */
            if (movimentosValidos.isEmpty()) {
                break;
            }
        }

        ArrayList<Coordenada> capturas = new ArrayList();
        capturas.add(new Coordenada(1, 1));
        capturas.add(new Coordenada(1, -1));

        if (getCor() == Cores.BRANCO) {
            capturas = AuxiliarMovimentos.inverter(capturas);
        }

        for (Coordenada captura : capturas) {

            int novoY = captura.getY() + getPosicao().getY();
            int novoX = captura.getX() + getPosicao().getX();

            if (novoX >= 0 && novoX < 8 && novoY >= 0 && novoY < 8) {
                Casa casa = casas[novoY][novoX];
                Movimento movimento = new Movimento(getCasa(), casa);

                if (casa.equals(tabuleiro.getCasaEnPassant())) {
                    movimento.setTipo(TiposMovimentoEspecial.EN_PASSANT);
                    movimentosValidos.add(movimento);

                } else if (!casa.estaVazia() && casa.getPeca().getCor() != this.getCor()) {

                    if (novoY == 7 || novoY == 0) {
                        movimento.setTipo(TiposMovimentoEspecial.COROACAO);
                    }

                    movimentosValidos.add(movimento);
                }
            }
        }

        return movimentosValidos;
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
