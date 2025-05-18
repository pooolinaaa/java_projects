package vehicles;
import java.util.Arrays;
import java.util.LinkedList;

import Interface.Transport;
import exceptionsPackage.DuplicateModelNameException;
import exceptionsPackage.ModelPriceOutOfBoundException;
import exceptionsPackage.NoSuchModelNameException;

public class Moped implements Transport{

    private String brand;
    private LinkedList<Model> modArr;
    private String type = "Moped";

    public Moped(String brand, int size){
        this.brand = brand;
        modArr = new LinkedList<>();
        for (int i = 0;i < size;i++){
            modArr.add(new Model("Moped" + (i), (int)((i+4)*100 * Math.random())));
        }
    }

    public Moped(String brand) {
        this.brand = brand;
        modArr = new LinkedList<>();
    }

    private class Model{
        private String name;
        private double price; 

        public Model(String name, double price){
            this.name = name;
            this.price = price;
        }
        public String getName(){
            return name;
        }
        public void setName(String value){
            this.name = value;
        }
        public double getPrice(){
            return price;
        }
        public void setPrice(double value) throws NoSuchModelNameException{
            if(value <= 0){
                throw new NoSuchModelNameException();
            }
            this.price = value;
        }

        @Override
        public String toString()
        {
            return  getName() + " , "  + getPrice();
        }
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public String[] getNames() {
        String[] names = new String[modArr.size()];
        for (int i = 0;i < modArr.size();i++) {
            names[i] = modArr.get(i).getName();
        }
        return names;
    }

    public double[] getPrices() {
        double[] prices = new double[modArr.size()];
        for (int i = 0;i < modArr.size();i++) {
            prices[i] = modArr.get(i).getPrice();
        }
        return prices;
    }

    public void setPriceByName(String name, double price) throws NoSuchModelNameException {
        if(price <= 0){
            throw new ModelPriceOutOfBoundException();
        }
        if(!Arrays.asList(getNames()).contains(name)){
            throw new NoSuchModelNameException();
        } 
        modArr.get(Arrays.asList(getNames()).indexOf(name)).setPrice(price);
    }

    public double getPriceByName(String name) throws NoSuchModelNameException {
        if(!Arrays.asList(getNames()).contains(name)){
            throw new NoSuchModelNameException();
        } 
        return modArr.get(Arrays.asList(getNames()).indexOf(name)).getPrice();
    }

    public void setName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException {

        if(Arrays.asList(getNames()).contains(newName)){
            throw new DuplicateModelNameException();
        } 
        if(!Arrays.asList(getNames()).contains(oldName)){
            throw new NoSuchModelNameException();
        } 
        modArr.get(Arrays.asList(getNames()).indexOf(oldName)).setName(newName);
    }

    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (price < 0){
            throw new ModelPriceOutOfBoundException();
        }
        for (Model model : modArr){
            if(model.getName().equals(name)){
                throw new DuplicateModelNameException();
            } 
        }
        modArr.add(new Model(name, price));
    }

    public void delModel(String name) throws NoSuchModelNameException {
        if(!Arrays.asList(getNames()).contains(name)){
            throw new NoSuchModelNameException();
        } 
        modArr.remove(Arrays.asList(getNames()).indexOf(name));
    }

    public int modelSize() {
        return modArr.size();
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        String type = getType();
        str.append(type).append(" ");
        String brand = getBrand();
        str.append(brand).append(" ");
        int amount = modelSize();
        str.append(amount).append(" ");
        for (Model model : modArr){
            str.append(model.getName()).append(" , ");
            str.append(model.getPrice()).append(" | ");
        }
        return new String(str);
    }
}
