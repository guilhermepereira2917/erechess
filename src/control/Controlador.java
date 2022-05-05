package control;

import enums.EstadosPartida;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import partida.model.Casa;
import partida.model.Partida;
import partida.model.Tabuleiro;
import enums.TiposMovimentoEspecial;
import java.util.ArrayList;
import partida.model.Movimento;
import partida.view.ButtonCasa;
import partida.view.FramePartida;
import partida.view.MenuInicial;
import partida.view.PanelTabuleiro;

public class Controlador {

    protected FramePartida framePartida;
    protected PanelTabuleiro panelTabuleiro;

    protected Partida partida;
    protected Tabuleiro tabuleiro;

    protected boolean sucessoUltimoMovimento;
    protected Movimento ultimoMovimento;
    protected ArrayList<Movimento> movimentosPossiveisParaSelecao;

    protected MovimentoListener movimentoListener;

    public Controlador() {
        this.partida = new Partida(2917);
        this.framePartida = new FramePartida(partida);

        this.tabuleiro = partida.getTabuleiro();
        this.panelTabuleiro = framePartida.getPanelTabuleiro();

        // Adiciona um ActionListener para cada um dos 64 botões do tabuleiro
        adicionarListenerBotoes();

        // Adiciona um ActionListener para itens da lista de menu do jframe
        framePartida.getItemNovaPartida().addActionListener(new NovaPartidaListener());
        framePartida.getItemVoltarAoMenuPrincipal().addActionListener(new RetornarAoMenuPrincipalListener());

        framePartida.setVisible(true);
    }

    protected void adicionarListenerBotoes() {
        movimentoListener = new MovimentoListener();

        ButtonCasa[][] buttonsCasas = this.panelTabuleiro.getBotoesCasas();
        for (int y = 0; y < buttonsCasas.length; y++) {
            for (int x = 0; x < buttonsCasas[0].length; x++) {
                buttonsCasas[y][x].addActionListener(movimentoListener);
            }
        }
    }

    private void novaPartida() {
        this.partida = new Partida(1);
        this.tabuleiro = partida.getTabuleiro();

        framePartida.setPartida(partida);
        panelTabuleiro = framePartida.getPanelTabuleiro();

        adicionarListenerBotoes();
    }

    private Casa primeiraCasaSelecionada, segundaCasaSelecionada;

    class MovimentoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (partida.getEstado() != EstadosPartida.EM_ANDAMENTO) {
                return;
            }

            ButtonCasa botaoClicado = (ButtonCasa) e.getSource();
            Casa casaClicada = botaoClicado.getCasa();

            if (primeiraCasaSelecionada != null) {

                panelTabuleiro.desmarcarMovimentosPossiveis();

                if (primeiraCasaSelecionada.equals(casaClicada)) {
                    primeiraCasaSelecionada = null;
                    return;
                }

                if (Movimento.validarSegundaSelecao(primeiraCasaSelecionada, casaClicada)) {
                    segundaCasaSelecionada = casaClicada;

                    Movimento movimentoSelecionado = tabuleiro.getMovimento(primeiraCasaSelecionada, segundaCasaSelecionada);

                    if (movimentoSelecionado == null) {
                        primeiraCasaSelecionada = null;
                        segundaCasaSelecionada = null;
                        return;
                    }
                    
                    if (movimentoSelecionado.getTipo() == TiposMovimentoEspecial.COROACAO) {
                        String opcao = framePartida.escolherPecaCoroacao();
                        movimentoSelecionado.setArgumentoOpcional(opcao);
                    }

                    sucessoUltimoMovimento = tabuleiro.realizarMovimento(movimentoSelecionado);

                    if (sucessoUltimoMovimento) {
                        sucessoUltimoMovimento = true;
                        ultimoMovimento = movimentoSelecionado;
                    }
                }

                primeiraCasaSelecionada = null;
                segundaCasaSelecionada = null;
            }

            if (primeiraCasaSelecionada == null) {
                if (!Movimento.validarPrimeiraSelecao(casaClicada)) {
                    return;
                }

                primeiraCasaSelecionada = casaClicada;
                movimentosPossiveisParaSelecao = casaClicada.getPeca().movimentosValidos(tabuleiro);
                panelTabuleiro.marcarMovimentosPossiveis(primeiraCasaSelecionada);

                segundaCasaSelecionada = null;
            }

        }

    }

    class NovaPartidaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (framePartida.confirmarCriacaoNovaPartida()) {
                novaPartida();
            }
        }
    }

    class RetornarAoMenuPrincipalListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (framePartida.confirmarVoltarAoMenuPrincipal()) {
                new MenuInicial().setVisible(true);
                framePartida.dispose();
            }
        }
    }

    public Partida getPartida() {
        return partida;
    }

}
