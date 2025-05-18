import threads.AllNames;
import threads.AllPrices;
import threads.BrandRunnable;
import threads.NamesReentrantLock;
import threads.NamesRunnable;
import threads.PricesReentrantLock;
import threads.PricesRunnable;
import threads.ReadFile;
import threads.TransportSynchronizer;
import vehicles.Auto;
import vehicles.Moped;
import vehicles.Quadro;
import vehicles.Scooter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import Interface.*;
import exceptionsPackage.DuplicateModelNameException;

public class Main {
    public static void main(String[] args)
            throws DuplicateModelNameException, InterruptedException, NumberFormatException, IOException {
        boolean flag = false;
        while (!flag) {
            BufferedReader rf = new BufferedReader(new InputStreamReader(System.in));
            int choice = Integer.parseInt(rf.readLine());
            switch (choice) {
                case 1:
                    // 1 task
                    Transport auto1 = new Auto("Mercedes", 100);
                    Thread prices1 = new AllPrices(auto1);
                    Thread names1 = new AllNames(auto1);
                    prices1.setPriority(Thread.MIN_PRIORITY);
                    names1.setPriority(Thread.MAX_PRIORITY);
                    prices1.start();
                    names1.start();
                    break;
                case 2:
                    // 2 task
                    Transport auto2 = new Auto("Mercedes", 10);
                    TransportSynchronizer ts = new TransportSynchronizer(auto2);
                    Thread names2 = new Thread(new NamesRunnable(ts));
                    Thread prices2 = new Thread(new PricesRunnable(ts));
                    prices2.start();
                    names2.start();

                    break;
                case 3:
                    // 3 task
                    Transport auto3 = new Auto("Mercedes", 10);
                    ReentrantLock rLock = new ReentrantLock();
                    Thread names3 = new Thread(new NamesReentrantLock(rLock, auto3));
                    Thread prices3 = new Thread(new PricesReentrantLock(rLock, auto3));
                    names3.start();
                    prices3.start();
                    break;
                case 4:
                    // 4 task
                    Transport auto4 = new Auto("BMW", 5);
                    Transport moped = new Moped("Drundylet", 5);
                    Transport quadro = new Quadro("EdetYra", 5);
                    Transport scooter = new Scooter("Bibika", 5);
                    ExecutorService es = Executors.newFixedThreadPool(2);
                    Transport[] transports = { auto4, moped, quadro, scooter };
                    for (Transport tr : transports) {
                        es.submit(new BrandRunnable(tr));
                    }
                    es.shutdown();
                    break;
                case 5:
                    // 5 task
                    ArrayBlockingQueue<Transport> aBlockingQueue = new ArrayBlockingQueue<Transport>(2);
                    String[] fileNames = { "1.txt", "2.txt", "3.txt", "4.txt", "5.txt" };
                    for (int i = 0; i < fileNames.length; i++) {
                        ReadFile rFile = new ReadFile(aBlockingQueue, fileNames[i]);
                        Thread thread = new Thread(rFile);
                        thread.start();
                    }
                    for (int i = 0; i < fileNames.length; i++) {
                        System.out.println(aBlockingQueue.take().getBrand());
                    }
                    break;
                case 0:
                    System.exit(0);
            }
        }

    }
}
