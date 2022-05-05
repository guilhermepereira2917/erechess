package server.model;

import enums.Cores;
import enums.EstadosPartida;
import partida.model.Casa;
import partida.model.Movimento;
import partida.model.Partida;
import partida.model.Tabuleiro;
import util.AuxiliarMovimentos;

public class ThreadPartida extends Thread {

    Partida partida;
    Tabuleiro tabuleiro;
    ThreadJogador jogadorBrancas, jogadorNegras;

    public ThreadPartida(ThreadJogador primeiroJogador, ThreadJogador segundoJogador) {
        jogadorBrancas = primeiroJogador;
        jogadorNegras = segundoJogador;

        jogadorBrancas.setCorDoLado(Cores.BRANCO);
        jogadorBrancas.enviarMensagem("BRANCO");

        jogadorNegras.setCorDoLado(Cores.PRETO);
        jogadorNegras.enviarMensagem("PRETO");

        partida = new Partida((int) (getId()));
        tabuleiro = partida.getTabuleiro();
    }

    @Override
    public void run() {
        while (partida.getEstado() == EstadosPartida.EM_ANDAMENTO) {
            try {
                ThreadJogador jogadorDaVez = tabuleiro.getTurno() == Cores.BRANCO ? jogadorBrancas : jogadorNegras;
                
                int y1 = jogadorDaVez.entradaDeDados.readInt();
                int x1 = jogadorDaVez.entradaDeDados.readInt();
                int y2 = jogadorDaVez.entradaDeDados.readInt();
                int x2 = jogadorDaVez.entradaDeDados.readInt();
                String opcional = (String) jogadorDaVez.entradaDeDados.readObject();

                Casa primeiraCasa = AuxiliarMovimentos.converterCasa(tabuleiro.getCasas(), y1, x1);
                Casa segundaCasa = AuxiliarMovimentos.converterCasa(tabuleiro.getCasas(), y2, x2);
                
                Movimento movimento = tabuleiro.getMovimento(primeiraCasa, segundaCasa);
                movimento.setArgumentoOpcional(opcional);
                
                tabuleiro.realizarMovimento(movimento);
                
                System.out.println(movimento.getTipo());
                System.out.println(tabuleiro.toString());

                ThreadJogador jogadorNaoDaVez = tabuleiro.getTurno() == Cores.BRANCO ? jogadorBrancas : jogadorNegras;
                
                jogadorNaoDaVez.saidaDeDados.writeInt(y1);
                jogadorNaoDaVez.saidaDeDados.flush();
                jogadorNaoDaVez.saidaDeDados.writeInt(x1);
                jogadorNaoDaVez.saidaDeDados.flush();
                jogadorNaoDaVez.saidaDeDados.writeInt(y2);
                jogadorNaoDaVez.saidaDeDados.flush();
                jogadorNaoDaVez.saidaDeDados.writeInt(x2);
                jogadorNaoDaVez.saidaDeDados.flush();
                jogadorNaoDaVez.saidaDeDados.writeObject(opcional);
                jogadorNaoDaVez.saidaDeDados.flush();
                
            } catch (Exception ex) {
                System.out.println("Erro ao receber movimento do jogador.");
                ex.printStackTrace();
                break;
            }
        }
    }

}
