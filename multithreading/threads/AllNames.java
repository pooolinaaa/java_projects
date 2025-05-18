package threads;
import Interface.Transport;

public class AllNames extends Thread{
    private Transport transport;

    public AllNames(Transport tr){
        transport = tr;
    }

    @Override
    public void run(){
        String[] names = transport.getNames();
        for(String name : names){
            System.out.println(name);
        }
    }
}
