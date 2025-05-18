import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Interface.Transport;

public class RequestThread extends Thread {
    private Socket cSocket;

    public RequestThread(Socket socket) {
        cSocket = socket;
        start();
    }

    public void run() {
        ObjectInputStream oIs;
        ObjectOutputStream oOs;
        try {
            oIs = new ObjectInputStream(cSocket.getInputStream());
            oOs = new ObjectOutputStream(cSocket.getOutputStream());
            Transport[] transports = (Transport[]) oIs.readObject();
            oOs.writeDouble(TransportLinks.getMeanPriceEternal(transports));
            oOs.flush();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e);
        }
        finally{
            try{
                 cSocket.close();
            }
           catch(IOException e){
            System.out.println(e.getMessage());
           }
        }

    }
}
