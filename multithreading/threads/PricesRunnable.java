package threads;

public class PricesRunnable implements Runnable{
    private TransportSynchronizer tSynchronizer;

    public PricesRunnable(TransportSynchronizer ts){
        tSynchronizer = ts;
    }

    @Override
    public void run() {
        while(tSynchronizer.canPrintPrice()){
            try {
                tSynchronizer.printPrice();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
    
}
