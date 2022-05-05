package partida.model;

import enums.TiposMovimentoEspecial;
import enums.Cores;
import enums.EstadosPartida;
import java.util.ArrayList;
import java.util.Observable;
import partida.model.pecas.Peca;
import partida.model.pecas.Cavalo;
import partida.model.pecas.Peao;
import partida.model.pecas.Dama;
import partida.model.pecas.Rei;
import partida.model.pecas.Bispo;
import partida.model.pecas.Torre;

public class Tabuleiro extends Observable {

    private Partida partida;

    private Casa[][] casas = new Casa[8][8];

    private ArrayList<Peca> pecasBrancas = new ArrayList();
    private ArrayList<Peca> pecasNegras = new ArrayList();
    private Peca reiBranco = new Rei(Cores.BRANCO);
    private Peca reiPreto = new Rei(Cores.PRETO);

    private Casa casaEnPassant = null;

    private Movimento ultimoMovimento;
    private boolean xeque = false;

    public Tabuleiro(Partida partida) {
        this.partida = partida;
        montarTabuleiro();
    }

    public Tabuleiro(Tabuleiro tabuleiro) {
        this.partida = new Partida(-1);

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Casa casa = tabuleiro.getCasas()[y][x];
                this.casas[y][x] = new Casa(this, new Coordenada(y, x));
                if (!casa.estaVazia()) {
                    Peca peca = casa.getPeca();
                    if (peca instanceof Peao) {
                        this.casas[y][x].setPeca(new Peao(peca));
                    } else if (peca instanceof Cavalo) {
                        this.casas[y][x].setPeca(new Cavalo(peca));
                    } else if (peca instanceof Bispo) {
                        this.casas[y][x].setPeca(new Bispo(peca));
                    } else if (peca instanceof Torre) {
                        this.casas[y][x].setPeca(new Torre(peca));
                    } else if (peca instanceof Rei) {
                        Rei rei = new Rei(peca);
                        this.casas[y][x].setPeca(rei);
                        if (peca.getCor() == Cores.BRANCO) {
                            this.reiBranco = rei;
                        } else {
                            this.reiPreto = rei;
                        }
                    } else if (peca instanceof Dama) {
                        this.casas[y][x].setPeca(new Dama(peca));
                    }
                }
            }
        }
    }

    private void montarTabuleiro() {
        // Armazena as cores para maior legibilidade do código
        final Cores BRANCO = Cores.BRANCO;
        final Cores PRETO = Cores.PRETO;

        // Inicializa as casas, com suas respectivas coordenadas e cores
        for (int y = 0; y < casas.length; y++) {
            for (int x = 0; x < casas[0].length; x++) {
                Coordenada coordenada = new Coordenada(y, x);
                Cores cor = (y + x) % 2 == 0 ? BRANCO : PRETO;
                casas[y][x] = new Casa(this, coordenada, cor);
            }
        }

        // Coloca a primeira linha de peças pretas
        casas[0][0].setPeca(new Torre(PRETO));
        casas[0][1].setPeca(new Cavalo(PRETO));
        casas[0][2].setPeca(new Bispo(PRETO));
        casas[0][3].setPeca(new Dama(PRETO));
        casas[0][4].setPeca(reiPreto);
        casas[0][5].setPeca(new Bispo(PRETO));
        casas[0][6].setPeca(new Cavalo(PRETO));
        casas[0][7].setPeca(new Torre(PRETO));

        // Coloca a linha de peões pretos
        for (int x = 0; x < casas.length; x++) {
            casas[1][x].setPeca(new Peao(PRETO));
        }

        // Adiciona as pecas negras em um array para facilitar o acesso
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                pecasNegras.add(casas[y][x].getPeca());
            }
        }

        // Coloca a primeira linha de peças brancas
        casas[7][0].setPeca(new Torre(BRANCO));
        casas[7][1].setPeca(new Cavalo(BRANCO));
        casas[7][2].setPeca(new Bispo(BRANCO));
        casas[7][3].setPeca(new Dama(BRANCO));
        casas[7][4].setPeca(reiBranco);
        casas[7][5].setPeca(new Bispo(BRANCO));
        casas[7][6].setPeca(new Cavalo(BRANCO));
        casas[7][7].setPeca(new Torre(BRANCO));

        // Coloca a linha de peões brancos
        for (int x = 0; x < casas.length; x++) {
            casas[6][x].setPeca(new Peao(BRANCO));
        }

        // Adiciona as pecas negras em um array para facilitar o acesso
        for (int y = 6; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                pecasBrancas.add(casas[y][x].getPeca());
            }
        }

    }

    public boolean realizarMovimento(String movimento) {
        Movimento movimentoConvertido = new Movimento(movimento, this);
        return realizarMovimento(movimentoConvertido);
    }

    public boolean realizarMovimento(Movimento movimento) {

        // O movimento nao foi encontrado
        if (movimento == null) {
            return false;
        }

        Peca peca = movimento.getPeca();

        // Valida a seleção de casas do movimento, antes de realizar a validação do movimento em si
        if (movimento.validarSelecao() && peca.validarMovimento(movimento)) {
            if (!peca.realizarMovimento(movimento)) {
                return false;
            }

            if (movimento.getTipo() != TiposMovimentoEspecial.DUPLO) {
                setCasaEnPassant(null);
            }

            setUltimoMovimento(movimento);
            partida.trocarTurno();

            if (verificarXeque(partida.getTurno())) {
                if (verificarXequeMate(partida.getTurno())) {
                    xequeMate();
                }
            } else {
                if (verificarEmpate(partida.getTurno())) {
                    empate();
                }
            }

            setChanged();
            notifyObservers();

            return true;
        }

        return false;
    }

    public boolean verificarXeque(Cores lado) {
        if (lado == Cores.BRANCO) {
            return getCasa(reiBranco).estaSobAtaque(this, lado);
        } else {
            return getCasa(reiPreto).estaSobAtaque(this, lado);
        }
    }

    public boolean verificarXequeMate(Cores lado) {
        ArrayList<Peca> pecas;

        if (lado == Cores.BRANCO) {
            pecas = pecasBrancas;
        } else {
            pecas = pecasNegras;
        }

        for (Peca peca : pecas) {
            ArrayList<Movimento> movimentosValidos = peca.movimentosValidos(this);

            for (Movimento movimento : movimentosValidos) {
                Tabuleiro tabuleiroClone = new Tabuleiro(this);
                Movimento movimentoClone = new Movimento(tabuleiroClone, movimento);
                movimentoClone.getCasaInicial().getPeca().realizarMovimento(movimentoClone, false);

                if (!tabuleiroClone.verificarXeque(lado)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verificarEmpate(Cores lado) {
        ArrayList<Peca> pecas;

        if (lado == Cores.BRANCO) {
            pecas = pecasBrancas;
        } else {
            pecas = pecasNegras;
        }

        for (Peca peca : pecas) {
            ArrayList<Movimento> movimentosValidos = peca.movimentosValidos(this);
            if (!movimentosValidos.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void xequeMate() {
        if (getTurno() == Cores.BRANCO) {
            partida.setEstado(EstadosPartida.NEGRAS_VENCERAM);
        } else {
            partida.setEstado(EstadosPartida.BRANCAS_VENCERAM);
        }
    }

    public void empate() {
        partida.setEstado(EstadosPartida.EMPATADA);
    }

    public void removerPeca(Peca peca) {
        if (peca.getCor() == Cores.BRANCO) {
            pecasBrancas.remove(peca);
        } else {
            pecasNegras.remove(peca);
        }
    }

    public void adicionarPeca(Peca peca) {
        if (peca.getCor() == Cores.BRANCO) {
            pecasBrancas.add(peca);
        } else {
            pecasNegras.add(peca);
        }
    }

    public Movimento getMovimento(Casa casaInicial, Casa casaFinal) {
        for (Movimento validos : casaInicial.getPeca().movimentosValidos(this)) {
            if (validos.getCasaFinal().toString().equals(casaFinal.toString())) {
                return validos;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String string = "";
        for (int y = 0; y < casas.length; y++) {
            for (int x = 0; x < casas[0].length; x++) {
                Peca peca = casas[y][x].getPeca();

                if (peca != null) {
                    string += String.format("%10s|", peca.getClass().getSimpleName());
                } else {
                    string += String.format("%10s|", "");
                }
            }
            string += "\n";
        }
        return string;
    }

    public Casa[][] getCasas() {
        return casas;
    }

    private Casa getCasa(Peca peca) {
        int y = peca.getPosicao().getY();
        int x = peca.getPosicao().getX();
        return casas[y][x];
    }

    public Cores getTurno() {
        return partida.getTurno();
    }

    public Casa getCasaEnPassant() {
        return casaEnPassant;
    }

    public boolean isXeque() {
        return xeque;
    }

    public Peca getReiPreto() {
        return reiPreto;
    }

    public Peca getReiBranco() {
        return reiBranco;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setCasaEnPassant(Casa casaEnPassant) {
        this.casaEnPassant = casaEnPassant;
    }

    public void setXeque(boolean xeque) {
        this.xeque = xeque;
    }

    public Movimento getUltimoMovimento() {
        return ultimoMovimento;
    }

    public void setUltimoMovimento(Movimento ultimoMovimento) {
        this.ultimoMovimento = ultimoMovimento;
    }

    public ArrayList<Peca> getPecasBrancas() {
        return pecasBrancas;
    }

    public ArrayList<Peca> getPecasNegras() {
        return pecasNegras;
    }

}
