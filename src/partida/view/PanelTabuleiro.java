package partida.view;

import java.awt.Color;
import partida.model.Casa;
import partida.model.Tabuleiro;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import enums.Cores;
import partida.model.Movimento;
import partida.model.pecas.Peca;

public class PanelTabuleiro extends JPanel implements Observer {

    private Tabuleiro tabuleiro;
    private final ButtonCasa[][] botoesCasas;

    private int tamanhoCasa;

    Casa casaSelecionada = null;
    Movimento ultimoMovimento;
    ButtonCasa ultimaCasaRei = null;
    boolean reiEmXeque = false;

    ArrayList<Movimento> movimentosPossiveisPrimeiroClique = null;

    final Border bordaMovimentoDisponivel = BorderFactory.createLineBorder(Color.decode("#07a0c3"), 4);
    final Border bordaMovimentoCaptura = BorderFactory.createLineBorder(Color.decode("#dd1c1a"), 4);
    final Border bordaMovimentoEspecial = BorderFactory.createLineBorder(Color.decode("#b491c8"), 4);
    final Border bordaUltimoMovimento = BorderFactory.createLineBorder(Color.decode("#fcdd00"), 4);
    final Border bordaMovimentoIndisponivel = BorderFactory.createLineBorder(Color.decode("#dd1c1a"), 4);
    final Border bordaXeque = BorderFactory.createLineBorder(Color.decode("#dd1c1a"), 4);

    Cores pontoDeVistaPadrao = Cores.BRANCO;
    private boolean girarTabuleiro = false;
    private boolean destacarUltimoMovimento = true;

    public PanelTabuleiro(Tabuleiro tabuleiro) {
        setLayout(new GridLayout(8, 8));

        this.tabuleiro = tabuleiro;
        tabuleiro.addObserver(this);

        botoesCasas = new ButtonCasa[8][8];
        Casa[][] casas = tabuleiro.getCasas();
        for (int y = 0; y < casas.length; y++) {
            for (int x = 0; x < casas[0].length; x++) {
                botoesCasas[y][x] = new ButtonCasa(casas[y][x]);
            }
        }
    }

    @Override
    public void update(Observable observavel, Object argumento) {
        if (observavel instanceof Tabuleiro) {

            if (girarTabuleiro) {
                desenharTabuleiro(tabuleiro.getTurno());
            } else {
                desenharTabuleiro(pontoDeVistaPadrao);
            }
            
            desmarcarCasasUltimoMovimento();
            // desmarcarXeque();

            ultimoMovimento = tabuleiro.getUltimoMovimento();
            reiEmXeque = tabuleiro.isXeque();
            
            marcarCasasUltimoMovimento();
            // marcarXeque();
        }
    }

    public void marcarMovimentosPossiveis(Casa casaSelecionada) {
        if (casaSelecionada.estaVazia()) {
            return;
        }

        this.casaSelecionada = casaSelecionada;
        marcarCasaPecaSelecionada();

        movimentosPossiveisPrimeiroClique = casaSelecionada.getPeca().movimentosValidos(tabuleiro);
        for (Movimento movimento : movimentosPossiveisPrimeiroClique) {
            Casa casaFinal = movimento.getCasaFinal();
            ButtonCasa button = getBotaoCasa(movimento.getCasaFinal());

            if (movimento.eEspecial()) {
                button.setBorder(bordaMovimentoEspecial);
            } else if (casaFinal.estaVazia()) {
                button.setBorder(bordaMovimentoDisponivel);
            } else {
                button.setBorder(bordaMovimentoCaptura);
            }
        }

    }

    public void desmarcarMovimentosPossiveis() {
        if (movimentosPossiveisPrimeiroClique == null) {
            return;
        }

        desmarcarCasaPecaSelecionada();

        for (Movimento movimento : movimentosPossiveisPrimeiroClique) {
            Casa casaFinal = movimento.getCasaFinal();
            ButtonCasa button = getBotaoCasa(casaFinal);
            button.setBorder(null);
        }

        movimentosPossiveisPrimeiroClique = null;

        marcarCasasUltimoMovimento();
        marcarXeque();
    }

    public void marcarCasaPecaSelecionada() {
        ButtonCasa botao = getBotaoCasa(casaSelecionada);

        botao.setBorder(bordaMovimentoDisponivel);
    }

    public void desmarcarCasaPecaSelecionada() {
        ButtonCasa botao = getBotaoCasa(casaSelecionada);
        botao.setBorder(null);

        casaSelecionada = null;

        marcarCasasUltimoMovimento();
    }

    public void marcarSelecaoInvalida(Casa casa) {
        ButtonCasa botao = getBotaoCasa(casa);
        botao.setBorder(bordaMovimentoIndisponivel);

        // Cria um timer que espera para remover a borda da casa clicada
        Timer delay = new Timer(500, (ActionEvent e) -> {
            if (bordaMovimentoIndisponivel.equals(botao.getBorder())) {
                botao.setBorder(null);
                marcarCasasUltimoMovimento();
            }
        });
        delay.setRepeats(false);
        delay.start();
    }

    public void marcarCasasUltimoMovimento() {
        if (!destacarUltimoMovimento || ultimoMovimento == null) {
            return;
        }

        ButtonCasa primeiroBotao = getBotaoCasa(ultimoMovimento.getCasaInicial());
        primeiroBotao.setBorder(bordaUltimoMovimento);

        ButtonCasa segundoBotao = getBotaoCasa(ultimoMovimento.getCasaFinal());
        segundoBotao.setBorder(bordaUltimoMovimento);
    }

    public void desmarcarCasasUltimoMovimento() {
        if (!destacarUltimoMovimento || ultimoMovimento == null) {
            return;
        }

        ButtonCasa primeiroBotao = getBotaoCasa(ultimoMovimento.getCasaInicial());
        primeiroBotao.setBorder(null);

        ButtonCasa segundoBotao = getBotaoCasa(ultimoMovimento.getCasaFinal());
        segundoBotao.setBorder(null);
    }

    public void marcarXeque() {
        if (reiEmXeque) {
            Peca rei = tabuleiro.getTurno() == Cores.BRANCO ? tabuleiro.getReiBranco() : tabuleiro.getReiPreto();
            ButtonCasa casaRei = getBotaoCasa(rei);
            ultimaCasaRei = casaRei;
            ultimaCasaRei.setBorder(bordaXeque);
        }
    }

    public void desmarcarXeque() {
        if (ultimaCasaRei != null) {
            ultimaCasaRei.setBorder(null);
            ultimaCasaRei = null;
        }
    }

    public void resize() {
        setSize(getParent().getSize());
        int tamanhoBotao = getSize().height / 8;
        for (int y = 0; y < botoesCasas.length; y++) {
            for (int x = 0; x < botoesCasas[0].length; x++) {
                botoesCasas[y][x].redimensionarIcone(tamanhoBotao, 0.85);
            }
        }
    }

    public void desenharTabuleiro(Cores perspectiva) {
        removeAll();

        if (perspectiva == Cores.PRETO) {
            for (int x = botoesCasas.length - 1; x >= 0; x--) {
                for (int y = botoesCasas[0].length - 1; y >= 0; y--) {
                    add(botoesCasas[x][y]);
                }
            }
        } else {
            for (int y = 0; y < botoesCasas.length; y++) {
                for (int x = 0; x < botoesCasas[0].length; x++) {
                    add(botoesCasas[y][x]);
                }
            }
        }

        repaint();
        revalidate();
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public ButtonCasa[][] getBotoesCasas() {
        return botoesCasas;
    }

    public ButtonCasa getBotaoCasa(Casa casa) {
        int buttonY = casa.getCoordenada().getY();
        int buttonX = casa.getCoordenada().getX();
        return botoesCasas[buttonY][buttonX];
    }

    public ButtonCasa getBotaoCasa(Peca peca) {
        int buttonY = peca.getPosicao().getY();
        int buttonX = peca.getPosicao().getX();
        return botoesCasas[buttonY][buttonX];
    }

    public int getTamanhoCasa() {
        return tamanhoCasa;
    }

    public void setTamanhoCasa(int tamanhoCasa) {
        this.tamanhoCasa = tamanhoCasa;
    }

    public boolean isGirarTabuleiro() {
        return girarTabuleiro;
    }

    public void setGirarTabuleiro(boolean girarTabuleiro) {
        this.girarTabuleiro = girarTabuleiro;
        if (girarTabuleiro) {
            desenharTabuleiro(tabuleiro.getTurno());
        } else {
            desenharTabuleiro(Cores.BRANCO);
        }
    }

    public void setDestacarUltimoMovimento(boolean destacarUltimoMovimento) {

        if (destacarUltimoMovimento) {
            this.destacarUltimoMovimento = destacarUltimoMovimento;
            marcarCasasUltimoMovimento();
        } else {
            desmarcarCasasUltimoMovimento();
            this.destacarUltimoMovimento = destacarUltimoMovimento;
        }
    }

    public void setPontoDeVistaPadrao(Cores pontoDeVistaPadrao) {
        this.pontoDeVistaPadrao = pontoDeVistaPadrao;
        desenharTabuleiro(this.pontoDeVistaPadrao);
    }

}
