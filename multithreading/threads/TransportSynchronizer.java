package threads;

import Interface.*;

public class TransportSynchronizer {
    private Transport v;
    private volatile int current = 0;
    private Object lock = new Object();
    private boolean set = false;
   
    public TransportSynchronizer(Transport v) {
        this.v = v;
    }
   
    public void printPrice() throws InterruptedException {
        double val;
        synchronized(lock) {
            double [] p = v.getPrices();
            if (!canPrintModel()) throw new InterruptedException();
            while (set)
                lock.wait();
            val = p[current++];
            System.out.println("Price: " + val);
            set = true;
            lock.notifyAll();
        }
    }  
   
    public void printModel() throws InterruptedException {
        synchronized(lock) {
            String [] s = v.getNames();
            if (!canPrintPrice()) throw new InterruptedException();
            while (!set)
                lock.wait();
            System.out.println("Model name: " + s[current]);
            set = false;
            lock.notifyAll();
        }
    }
    
    public boolean canPrintPrice() {
        return (!set && current < v.modelSize()) || (set && current < v.modelSize() - 1);
    }
    
    public boolean canPrintModel() {
        return current < v.modelSize();
    }
}