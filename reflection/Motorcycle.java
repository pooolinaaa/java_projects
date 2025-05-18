import java.io.Serializable;
import java.util.Arrays;
import exceptionsPackage.DuplicateModelNameException;
import exceptionsPackage.ModelPriceOutOfBoundException;
import exceptionsPackage.NoSuchModelNameException;

public class Motorcycle implements Transport{
    private static final long serialVersionUID = 1L;
    private String brand; 
    private String type = "Motorcycle";
    private int size = 0;
    private Model head;
    public String getBrand() {
        return brand;
    }
    public void setBrand(String value) {
        brand = value;
    }
    public String getType(){
        return type;
    }
    public Motorcycle(String brand){
        this.brand = brand;
        head = new Model();
        head.prev = head;
        head.next = head;
    }
    public Motorcycle (String brand, int size) throws DuplicateModelNameException {
        this.size = size;
        this.brand = brand;
        Model p;
        head = new Model();
        head.prev = head;
        head.next = head;
        for(int i = 0; i<size; i++){
            p = new Model("Motorcycle" + (i), (int)((i+4)*100*Math.random()));
            p.next = head;
            p.prev = head.prev;
            head.prev.next = p;
            head.prev = p;
        }
    }
    private class Model implements Serializable, Cloneable{
        private String name; 
        private double price = Double.NaN; 
        private Model prev;
        private Model next;
        public Model(){}
        
        public String getNameModel() {
            return name;
        }
        public Double getPriceModel(){
            return price;
        }
        public Model(String name, double price){
            this.name = name; this.price = price; 
        }
        @Override
        public String toString() {
            return getNameModel() + ", " + getPriceModel();
        }

        @Override
        public Object clone() {
            Model model ;
            try {
                model = (Model) super.clone();
            } catch (CloneNotSupportedException exception) {
                return null;
            }
            return model;
        }
    }
    public void setName(String source, String value) throws NoSuchModelNameException, DuplicateModelNameException{
        if (!inList(source)) {
            throw new NoSuchModelNameException();
        }
        if (inList(value)) {
            
            throw new DuplicateModelNameException();
        }
        Model p = head;
        for (int i=0; i < size; i++) {
            if ((p.name != null) && (p.name.equals(source))) {
                p.name = value;
            }
            p = p.next;
        }  
    }
    private boolean inList(String name) {
        Model p = head.next;
        while (p!=head) {
            if (p.name.equals(name)) {
                return true;
            }
            p = p.next;
        }
        return false;
    }
    public String[] getNames(){
        Model q = head.next;
        String[] output = new String[size];
        for(int i = 0; i<size; i++){
            output[i] = q.name;
            q = q.next;
        }
        return output;
    }
    public double getPriceByName(String set) throws NoSuchModelNameException{
        double output = 0;
        Model p = head.next;
        while(!(p.name.equals(set))){
            p = p.next;
        }
        if (p.name.equals(set)) {
            output = p.price;
        }
        if(output == 0){
            throw new NoSuchModelNameException();
        }
        return output;
    }
    public void setPriceByName(String name, double value) throws NoSuchModelNameException{
        if(value <= 0){
            throw new ModelPriceOutOfBoundException();
        }
        if(find(name) == null){
            throw new NoSuchModelNameException();
        }
        else{
            Model q = head;
            for(int i = 0; i<size; i++){
                if(q.name != null && q.name.equals(name)){
                    q.price = value;
                }
                q = q.next;
            }
        }
    }
    public double[] getPrices(){
        Model q = head.next;
        double[] output = new double[size];
        for(int i = 0; i<size; i++){
            output[i] = q.price;
            q = q.next;
        }
        return output;
    }
    public void addModel(String name, double price) throws DuplicateModelNameException{
        if(price <= 0){
            throw new ModelPriceOutOfBoundException();
        }
        if(find(name)==null){
            Model q = new Model(name, price);
            if (size == 0) {
                head = q;
                q.next = q;
                q.prev = q;
            }
            else {
                q.prev = head.prev;
                q.next = head;
                head.prev.next = q;
                head.prev = q;
            }
            size++;
        }
        else{
            throw new DuplicateModelNameException();
        }
    }
    public void delModel(String name) throws NoSuchModelNameException{
        if(find(name) == null){
            throw new NoSuchModelNameException();
        }
        else{
            Model q = head.next;
            while((q != head) && (!q.name.equals(name))){
                q = q.next;
            }
            q.prev.next = q.next;
            q.next.prev = q.prev;
            size --;
        }
    }

    public int modelSize(){
  
        return size;
    }
    private Model find(String value){
        Model q = head.next;
            if (head != null)
            {
                while ((q != head) && (!q.name.equals(value)))
                {
                    q = q.next;
                }
                if (q == head)
                {
                    q = null;
                }
            }
            else
            {
                q = null;
            }
        return q;
    }

    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();
        String type = getType();
        str.append(type).append(" ");
        String brand = getBrand();
        str.append(brand).append(" ");
        int size = modelSize();
        str.append(size).append(" ");

        Model q = head.next;
        while (q != head) {
            str.append(q).append(" | ");
            q = q.next;
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(obj instanceof Transport){
            Transport transport = (Transport) obj;
            return this.getBrand().equals(transport.getBrand()) &&
                Arrays.equals(this.getNames(), transport.getNames()) &&
                Arrays.equals(this.getPrices(), transport.getPrices());
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash  = brand.hashCode() ;
        Model q = head.next;
        while (q != head) {
            hash = hash * 43 * q.hashCode();
            q = q.next;
        }
        return hash;
    }

    @Override
    public Object clone(){
        Motorcycle motorcycle ;
        try{
            motorcycle = (Motorcycle) super.clone();
            motorcycle.head = (Model)head.clone();
            Model cloned = motorcycle.head;
            Model q = head.next;

            while (q != head) {
                cloned.next=(Model) q.clone();
                cloned.next.prev =cloned;
                q = q.next;
                cloned = cloned.next;
            }
            cloned.next=motorcycle.head;
            motorcycle.head.prev=cloned;
            return motorcycle;
        } catch (CloneNotSupportedException exception) {
            return null;
        }
    }
}
