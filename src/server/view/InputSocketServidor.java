package server.view;

import javax.swing.JOptionPane;
import server.model.SocketServidor;

public class InputSocketServidor {

    public static SocketServidor criarServidor() {
        int porta = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a porta na qual você deseja criar o servidor: ", 2917));

        SocketServidor servidor = new SocketServidor();

        new Thread() {
            @Override
            public void run() {
                servidor.iniciar(porta);
            }
        }.start();

        return servidor;
    }

}
