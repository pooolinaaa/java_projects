package threads;
import Interface.Transport;

public class AllPrices extends Thread{
    private Transport transport;

    public AllPrices(Transport tr){
        transport = tr;
    }

    @Override
    public void run(){
        double[] prices = transport.getPrices();
        for(double price : prices){
            System.out.println(price);
        }
    }
}
