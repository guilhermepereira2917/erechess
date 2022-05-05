package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EngineUCI {

    Process engine;
    
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void enviarMovimento(String movimento) {
        try {
            writer.write("position startpos moves" + movimento + "\n");
            writer.flush();
            
            writer.write("go");
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String receberMovimento() {
        
    }

}
