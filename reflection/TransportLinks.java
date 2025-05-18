import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import exceptionsPackage.DuplicateModelNameException;
import exceptionsPackage.NoSuchModelNameException;


public class TransportLinks {
    public static double getAveragePrice(Transport trans){
        double[] prices = trans.getPrices();
        double sum = 0;
        for(int i = 0; i<prices.length; i++){
            sum += prices[i];
        }
        return sum/prices.length;
    } 

    public static double getMeanPriceEternal(Transport... transArray){
        double sum = 0;
        int length = 0;
        for(Transport tr : transArray){
            sum += getAveragePrice(tr);
            length += tr.modelSize();
        }
        return sum/length;
    }
    public static void printModels(Transport trans){
        String[] arrNamesModels = trans.getNames();
        for (String name : arrNamesModels) {
            System.out.println("Название модели: " + name);
        }   
    }

    public static void printPrices(Transport trans){
        double[] arrPricesModels = trans.getPrices();
        for (double price : arrPricesModels) {
            System.out.println("Цена модели: " + price);
        }
    }

    public static void outputTransport(Transport transport, OutputStream out) throws IOException{
        DataOutputStream dOS = new DataOutputStream(out);
        dOS.writeUTF(transport.getType());
        dOS.writeUTF(transport.getBrand());        
        dOS.writeInt(transport.modelSize());
        for(int i = 0; i<transport.modelSize(); i++){
            dOS.writeUTF(transport.getNames()[i]);
            dOS.writeDouble(transport.getPrices()[i]);
        }

    } 

    public static Transport inputTransport(InputStream in) throws IOException, DuplicateModelNameException{
        Transport transport = null;
        DataInputStream dIS = new DataInputStream(in);
        switch (dIS.readUTF()){
            case "Auto":
                transport = new Auto(dIS.readUTF());
                break;
            case "Motorcycle":
                transport = new Motorcycle(dIS.readUTF());
                break;
        }
        int numModels = dIS.readInt();
        for(int i = 0; i < numModels; i++){
            String model = dIS.readUTF();
            double price = dIS.readDouble();
            transport.addModel(model,price);
        }
        return transport;
    }

    public static void writeTransport(Transport transport)
    {
        System.out.printf("%s", transport.getBrand());
        System.out.printf("%n", transport.modelSize());
        for (int i = 0; i<transport.modelSize(); i++) {
            System.out.printf(transport.getNames()[i] + " ");
            System.out.printf((transport.getPrices()[i]) + "\n");
        }
    }

    public static Transport readTransport() throws IOException, DuplicateModelNameException, NoSuchModelNameException {
        Transport transport = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название класса: ");
        String type = scanner.nextLine();
        System.out.println("Введите бренд: ");
        String brand = scanner.nextLine();
        System.out.println("Введите количество моделей: ");
        int modelSize = scanner.nextInt();
        scanner.nextLine();
        switch (type){
            case "Auto":
                transport = new Auto(brand);
                break;
            case "Motorcycle":
                transport = new Motorcycle(brand);
                break;
            case "Scooter":
                transport = new Scooter(brand);
            case "Moped":
                transport = new Moped(brand);
                break;
            case "Quadro":
                transport = new Quadro(brand);
                break;
            default:
                System.out.println("No such transport type.");
        }

        for(int i = 0; i < modelSize; i++){
            System.out.println("Введите название модели и цену: ");
            String name = scanner.nextLine();
            double price = scanner.nextDouble();
            scanner.nextLine();
            transport.addModel(name, price);
        }
        scanner.close();
        return transport;
    }
    
    public static Transport createTransport(String brand, int size, Transport transport) {
        try {
            Class<?> classTransport = transport.getClass();
            Constructor<?> classConstructor = classTransport.getConstructor(String.class, int.class);
            Transport tr = (Transport) classConstructor.newInstance(brand, size);
            return  tr;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }
}
