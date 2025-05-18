package threads;

import java.util.concurrent.locks.ReentrantLock;
import Interface.Transport;

public class PricesReentrantLock implements Runnable {
    private ReentrantLock rLock;
    private Transport transport;

    public PricesReentrantLock(ReentrantLock reentrantLock, Transport tr){
        rLock = reentrantLock;
        transport = tr;
    }
    
    @Override
    public void run() {
        rLock.lock();
        try{
            double[] prices = transport.getPrices();
            for(double price : prices){
                System.out.println(price);
            }
        } finally{
            rLock.unlock();
        }
    }  
}
