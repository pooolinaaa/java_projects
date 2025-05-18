package threads;

import Interface.*;

public class BrandRunnable implements Runnable {
    private Transport transport;

    public BrandRunnable(Transport tr){
        transport = tr;
    }

    @Override
    public void run() {
        System.out.println(transport.getBrand());
    }
    
}
