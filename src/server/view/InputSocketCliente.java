
package server.view;

import control.ControladorMultiplayer;
import javax.swing.JOptionPane;

public class InputSocketCliente {
    
    public static ControladorMultiplayer conectarPartida() {
        String ip = JOptionPane.showInputDialog(null, "Digite o ip da partida (ex: 192.0.4.2): ", "localhost");
        int porta = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a porta na qual o servidor está localizado: ", 2917));
        return new ControladorMultiplayer(ip, porta);
    }
    
}
