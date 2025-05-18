import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Interface.*;

public class MainPosled {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket sSocket = new ServerSocket(2004);
        Socket cSocket = sSocket.accept();
        ObjectInputStream oIs = new ObjectInputStream(cSocket.getInputStream());
        ObjectOutputStream oOs = new ObjectOutputStream(cSocket.getOutputStream());
        Transport[] transports = (Transport[]) oIs.readObject();
        oOs.writeDouble(TransportLinks.getMeanPriceEternal(transports));
        oOs.flush();
        sSocket.close();
    }
}