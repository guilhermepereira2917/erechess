package control;

import enums.Cores;
import java.awt.event.ActionEvent;
import partida.view.ButtonCasa;
import server.model.SocketCliente;

public class ControladorMultiplayer extends Controlador {

    Cores lado;
    SocketCliente socketCliente = new SocketCliente();

    public ControladorMultiplayer(String ip, int porta) {
        super();
        socketCliente.iniciarConexao(ip, porta);

        this.lado = socketCliente.getLado();
        panelTabuleiro.setPontoDeVistaPadrao(this.lado);
        if (this.lado == Cores.PRETO) {
            socketCliente.receberMovimento(tabuleiro);
        }
    }

    @Override
    protected void adicionarListenerBotoes() {
        movimentoListener = new MovimentoMultiplayerListener();

        ButtonCasa[][] buttonsCasas = this.panelTabuleiro.getBotoesCasas();
        for (int y = 0; y < buttonsCasas.length; y++) {
            for (int x = 0; x < buttonsCasas[0].length; x++) {
                buttonsCasas[y][x].addActionListener(movimentoListener);
            }
        }
    }

    class MovimentoMultiplayerListener extends MovimentoListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (partida.getTabuleiro().getTurno() != lado) {
                return;
            }

            super.actionPerformed(e);

            if (sucessoUltimoMovimento) {
                socketCliente.enviarMovimento(ultimoMovimento);
                socketCliente.receberMovimento(tabuleiro);
                sucessoUltimoMovimento = false;
            }
            
        }
    }
}
