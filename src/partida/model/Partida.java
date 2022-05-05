package partida.model;

import enums.Cores;
import enums.EstadosPartida;
import java.util.Observable;

public class Partida extends Observable {

    private int id;
    private EstadosPartida estado = EstadosPartida.INICIANDO;

    private Tabuleiro tabuleiro;
    private Cores turno = Cores.BRANCO;

    public Cores getTurno() {
        return turno;
    }

    public void setTurno(Cores turno) {
        this.turno = turno;
    }

    public Partida(int id) {
        this.id = id;
        setEstado(EstadosPartida.INICIANDO);

        tabuleiro = new Tabuleiro(this);
        setEstado(EstadosPartida.EM_ANDAMENTO);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;

        setChanged();
        notifyObservers();
    }

    public EstadosPartida getEstado() {
        return estado;
    }

    public void setEstado(EstadosPartida estado) {
        this.estado = estado;

        setChanged();
        notifyObservers();
    }

    public void trocarTurno() {
        turno = turno == Cores.BRANCO ? Cores.PRETO : Cores.BRANCO;
    }
    
}
