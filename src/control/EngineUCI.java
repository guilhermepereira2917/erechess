package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import partida.model.Movimento;

public class EngineUCI extends Thread {

    Process engine;
    ArrayList<String> movimentos;

    InputStream input;
    BufferedReader reader;

    OutputStream output;
    BufferedWriter writer;

    public EngineUCI() {
        try {
            engine = Runtime.getRuntime().exec("stockfish.exe");

            input = engine.getInputStream();
            output = engine.getOutputStream();

            reader = new BufferedReader(new InputStreamReader(input));
            writer = new BufferedWriter(new OutputStreamWriter(output));

            movimentos = new ArrayList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void receberMovimento(String movimento) {
        try {

            if (movimento != null) {
                movimentos.add(movimento);

                String parser = "";
                for (String jogado : movimentos) {
                    parser += jogado + " ";
                }

                writer.write("position startpos moves " + parser + "\n");
            } else {
                writer.write("ucinewgame\n");
            }
            
            writer.flush();

            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1000);
                        writer.write("stop\n");
                        writer.flush();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();

            writer.write("go\n");
            writer.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String enviarMovimento() {
        try {

            String line, move = null;
            while ((line = reader.readLine()) != null) {
                if (line.split(" ")[0].equals("bestmove")) {
                    move = line.split(" ")[1];
                    break;
                }
            }

            System.out.println(move);
            movimentos.add(move);

            return move;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
