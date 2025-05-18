package threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

import Interface.Transport;
import vehicles.*;

public class ReadFile implements Runnable { 
    private ArrayBlockingQueue<Transport> bQueue;
    private String fileName;

    public ReadFile(ArrayBlockingQueue<Transport> bq, String name){
        bQueue = bq;
        fileName = name;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            Transport auto = new Auto(in.readLine());
            in.close();
            bQueue.add(auto);
        } catch (IOException e) {
            System.out.println(e);
        } 
            
    }
    
}
