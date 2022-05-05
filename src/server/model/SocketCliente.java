package server.model;

import enums.Cores;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import partida.model.Casa;
import partida.model.Movimento;
import partida.model.Tabuleiro;
import util.AuxiliarMovimentos;

public class SocketCliente {

    private Socket socket;
    private ObjectOutputStream saidaDeDados;
    private ObjectInputStream entradaDeDados;

    private Cores lado;
    
    final static String IP_OFICIAL = "localhost";
    final static int PORTA = 2917;

    public void iniciarConexao(String ip, int porta) {
        try {
            socket = new Socket(ip, porta);

            saidaDeDados = new ObjectOutputStream(socket.getOutputStream());
            entradaDeDados = new ObjectInputStream(socket.getInputStream());

            String comprimento = (String) entradaDeDados.readObject();
            System.out.println(comprimento);

            lado = ((String) entradaDeDados.readObject()).equals("BRANCO") ? Cores.BRANCO : Cores.PRETO;

            System.out.println(lado);
        } catch (Exception ex) {
            System.out.println("Erro ao iniciar conexão com o servidor.");
        }
    }

    public void terminarConexao() {
        try {
            entradaDeDados.close();
            saidaDeDados.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Erro ao terminar conexão.");
        }
    }

    public void enviarMovimento(Movimento movimento) {
        
        System.out.println(movimento.toString());
        
        Casa primeiraCasa = movimento.getCasaInicial();
        Casa segundaCasa = movimento.getCasaFinal();
        String opcional = movimento.getArgumentoOpcional();
        
        int y1 = primeiraCasa.getCoordenada().getY();
        int x1 = primeiraCasa.getCoordenada().getX();
        int y2 = segundaCasa.getCoordenada().getY();
        int x2 = segundaCasa.getCoordenada().getX();

        try {
            saidaDeDados.writeInt(y1);
            saidaDeDados.flush();
            saidaDeDados.writeInt(x1);
            saidaDeDados.flush();
            saidaDeDados.writeInt(y2);
            saidaDeDados.flush();
            saidaDeDados.writeInt(x2);
            saidaDeDados.flush();
            saidaDeDados.writeObject(opcional);
            saidaDeDados.flush();
        } catch (IOException ex) {
            
        }
    }

    public void receberMovimento(Tabuleiro tabuleiro) {
        new Thread() {
            @Override
            public void run() {
                try {
                    int y1 = entradaDeDados.readInt();
                    int x1 = entradaDeDados.readInt();
                    int y2 = entradaDeDados.readInt();
                    int x2 = entradaDeDados.readInt();
                    String opcional = (String) entradaDeDados.readObject();

                    Casa primeiraCasa = AuxiliarMovimentos.converterCasa(tabuleiro.getCasas(), y1, x1);
                    Casa segundaCasa = AuxiliarMovimentos.converterCasa(tabuleiro.getCasas(), y2, x2);
                    
                    Movimento movimento = tabuleiro.getMovimento(primeiraCasa, segundaCasa);
                    movimento.setArgumentoOpcional(opcional);
                    
                    System.out.println(movimento.toString());
                    
                    tabuleiro.realizarMovimento(movimento);
                } catch (Exception ex) {
                    System.out.println("deu tiziu");
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public Cores getLado() {
        return this.lado;
    }
    
}
