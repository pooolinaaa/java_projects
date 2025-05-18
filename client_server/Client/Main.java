import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import Interface.*;
import exceptionsPackage.*;
import vehicles.*;

public class Main {
    public static void main(String[] args) throws DuplicateModelNameException, UnknownHostException, IOException {

        ObjectOutputStream oOs = null;
        ObjectInputStream oIs = null;
        Transport auto = new Auto("Mercedes", 5);
        Transport motorcycle = new Motorcycle("Motor", 5);
        Transport scooter = new Scooter("Scoo", 5);
        Transport[] transports = new Transport[] { auto, motorcycle, scooter };
        try {
            Socket socket = new Socket("127.0.0.1", 2004);
            oOs = new ObjectOutputStream(socket.getOutputStream());
            oIs = new ObjectInputStream(socket.getInputStream());
            oOs.writeObject(transports);
            oOs.flush();
            double average = oIs.readDouble();
            System.out.println("Среднее значение всех цен моделей: " + average);
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            oOs.close();
            oIs.close();
        }

    }
}
