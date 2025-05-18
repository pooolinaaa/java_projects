import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import exceptionsPackage.DuplicateModelNameException;
import exceptionsPackage.NoSuchModelNameException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, DuplicateModelNameException, NoSuchModelNameException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите название класса: ");
        String className = bf.readLine();
        System.out.println("Введите количество моделей: ");
        String modelNumber = bf.readLine();
        System.out.println("Введите название метода: ");
        String methodName = bf.readLine();
        System.out.println("Введите старое название модели: ");
        String oldName = bf.readLine();
        System.out.println("Введите новое название модели: ");
        String newName = bf.readLine();
        Class<?> transport = Class.forName(className);
        Method met = transport.getMethod(methodName, String.class, String.class);
        Object auto = transport.getConstructor(String.class, int.class).newInstance("Mercedes", Integer.parseInt(modelNumber));
        met.invoke(auto, oldName, newName);
        System.out.println(auto);


        Transport autoTest = new Auto("BMW", 3);
        Transport autoResult = TransportLinks.createTransport("Whrum", 6, autoTest);
        System.out.println(autoResult);



        Transport motorcycle = new Motorcycle("Moto", 3);
        System.out.println(motorcycle);
        Transport moped = new Moped("Mop", 3);
        System.out.println(moped);
        Transport quadro = new Quadro("Quad", 3);
        System.out.println(quadro);
        Transport scooter = new Scooter("Scoo", 3);
        System.out.println(scooter);
        System.out.println(TransportLinks.getMeanPriceEternal(motorcycle, moped, quadro, scooter));
        


        Transport tr = TransportLinks.readTransport();
        TransportLinks.writeTransport(tr);
    }
}
