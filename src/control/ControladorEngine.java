package control;

import enums.Cores;
import java.awt.event.ActionEvent;
import partida.view.ButtonCasa;

public class ControladorEngine extends Controlador {

    EngineUCI stockfish = new EngineUCI();
    Cores lado;

    public ControladorEngine() {
        super();
        stockfish.start();
        
        if (Math.random() > 0.5) {
            this.lado = Cores.BRANCO;
        } else {
            this.lado = Cores.PRETO;
        }

        if (this.lado == Cores.PRETO) {
            stockfish.receberMovimento(null);
            movimentoEngine();
        }
        
        panelTabuleiro.setPontoDeVistaPadrao(lado);
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

            if (lado != tabuleiro.getTurno()) {
                return;
            }

            super.actionPerformed(e);

            if (sucessoUltimoMovimento) {
                stockfish.receberMovimento(ultimoMovimento.toString());
                movimentoEngine();

                sucessoUltimoMovimento = false;
            }

        }
    }

    public void movimentoEngine() {
        new Thread() {
            @Override
            public void run() {
                String movimento = stockfish.enviarMovimento();
                tabuleiro.realizarMovimento(movimento);
            }
        }.start();
    }

}
