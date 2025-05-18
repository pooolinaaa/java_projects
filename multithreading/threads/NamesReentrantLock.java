package threads;

import java.util.concurrent.locks.ReentrantLock;
import Interface.Transport;

public class NamesReentrantLock implements Runnable {
    private ReentrantLock rLock;
    private Transport transport;

    public NamesReentrantLock(ReentrantLock rl, Transport tr){
        rLock = rl;
        transport = tr;
    }

    @Override
    public void run() {
        rLock.lock();
        try{
            String[] names = transport.getNames();
            for(String name : names){
                System.out.println(name);
            }
        }finally{
            rLock.unlock();
        }
    }
    
}
