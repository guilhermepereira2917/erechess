package server.model;

import enums.Cores;
import java.io.*;
import java.net.*;

public class ThreadJogador extends Thread {

    Socket jogador;
    private Cores corDoLado;

    ObjectInputStream entradaDeDados;
    ObjectOutputStream saidaDeDados;

    

    public ThreadJogador(Socket jogador) {
        try {
            this.jogador = jogador;

            entradaDeDados = new ObjectInputStream(jogador.getInputStream());
            saidaDeDados = new ObjectOutputStream(jogador.getOutputStream());

            saidaDeDados.writeObject("Olá, jogador!");
        } catch (IOException ex) {
            System.out.println("Erro Thread jogador.");
        }
    }

    public void enviarMensagem(String mensagem) {
        try {
            saidaDeDados.writeObject(mensagem);
        } catch (IOException ex) {
            System.out.println("Erro ao enviar mensagem ao jogador");
        }
    }

    @Override
    public void run() {
        
    }

    public Cores getCorDoLado() {
        return corDoLado;
    }

    public void setCorDoLado(Cores corDoLado) {
        this.corDoLado = corDoLado;
    }

}
