package util;

import java.util.ArrayList;
import partida.model.Casa;
import partida.model.Coordenada;

public abstract class AuxiliarMovimentos {

    public static ArrayList<Coordenada> inverter(ArrayList<Coordenada> coordenadas) {
        ArrayList<Coordenada> invertido = new ArrayList();
        for (Coordenada coordenada : coordenadas) {
            invertido.add(new Coordenada(coordenada.getY() * -1, coordenada.getX() * -1));
        }
        return invertido;
    }

    public static Casa converterCasa(Casa[][] casas, int y, int x) {
        return casas[y][x];
    }
    
}
