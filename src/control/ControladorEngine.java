package control;

import java.awt.event.ActionEvent;
import partida.view.ButtonCasa;

public class ControladorEngine extends Controlador {

    EngineUCI stockfish = new EngineUCI();

    public ControladorEngine() {
        super();
        stockfish.start();
    }

    @Override
    protected void adicionarListenerBotoes() {
        movimentoListener = new ControladorEngine.MovimentoEngineListener();

        ButtonCasa[][] buttonsCasas = this.panelTabuleiro.getBotoesCasas();
        for (int y = 0; y < buttonsCasas.length; y++) {
            for (int x = 0; x < buttonsCasas[0].length; x++) {
                buttonsCasas[y][x].addActionListener(movimentoListener);
            }
        }
    }

    class MovimentoEngineListener extends MovimentoListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            super.actionPerformed(e);

            if (sucessoUltimoMovimento) {
                stockfish.receberMovimento(ultimoMovimento.toString());
                
                String movimento = stockfish.enviarMovimento();
                tabuleiro.realizarMovimento(movimento);
                
                sucessoUltimoMovimento = false;
            }

        }
    }

}
