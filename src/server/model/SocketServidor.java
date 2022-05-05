package server.model;

import java.net.*;
import java.util.ArrayList;

public class SocketServidor {

    private ServerSocket socketServidor;
    ArrayList<ThreadJogador> jogadores;
    ArrayList<ThreadPartida> partidas;

    final String IP_OFICIAL = "localhost";
    final int PORTA = 2917;

    public void iniciar(int porta) {
        try {
            System.out.println("Iniciando servidor!");
            socketServidor = new ServerSocket(porta);

            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket primeiroJogador = socketServidor.accept();
                ThreadJogador threadPrimeiroJogador = new ThreadJogador(primeiroJogador);
                threadPrimeiroJogador.start();

                Socket segundoJogador = socketServidor.accept();
                ThreadJogador threadSegundoJogador = new ThreadJogador(segundoJogador);
                threadSegundoJogador.start();

                ThreadPartida threadPartida = new ThreadPartida(threadPrimeiroJogador, threadSegundoJogador);
                threadPartida.start();
            }
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o servidor.");
        }
    }

    public void stop() {
        try {
            socketServidor.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar o servidor.");
        }
    }

    public static void main(String[] args) {
        SocketServidor servidor = new SocketServidor();
        servidor.iniciar(2917);
    }

}
