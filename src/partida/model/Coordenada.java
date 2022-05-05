package partida.model;

public class Coordenada {

    private int x;
    private int y;

    public Coordenada(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public Coordenada(Coordenada coordenada) {
        this.x = coordenada.getX();
        this.y = coordenada.getY();
    } 
    
    @Override
    public String toString() {
        return "Coordenada{" + "x=" + x + ", y=" + y + '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordenada adicionar(Coordenada coordenada) {
        return new Coordenada(getY() + coordenada.getY(), getX() + coordenada.getX());
    }

}
