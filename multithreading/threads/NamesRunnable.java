package threads;

public class NamesRunnable implements Runnable{
    private TransportSynchronizer tSynchronizer;

    public NamesRunnable(TransportSynchronizer ts){
        tSynchronizer = ts;
    }
    @Override
    public void run() {
        while(tSynchronizer.canPrintModel()){
            try {
                tSynchronizer.printModel();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
    
}
