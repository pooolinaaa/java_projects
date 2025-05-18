package vehicles;
import java.util.HashMap;
import java.util.Map;

import Interface.Transport;
import exceptionsPackage.DuplicateModelNameException;
import exceptionsPackage.ModelPriceOutOfBoundException;
import exceptionsPackage.NoSuchModelNameException;

public class Scooter implements Transport{
    private String brand;
    private HashMap<String, Double> modArr;
    private String type = "Scooter";

    public Scooter(String brand) {
        this.brand = brand;
        this.modArr = new HashMap<>();
    }

    public Scooter(String brand, int size) throws DuplicateModelNameException {
        this.brand = brand;
        modArr = new HashMap<>();
        for (int i = 0; i < size; i++) {
            modArr.put("Scooter" + i  , (i+4)*100*Math.random());
        }
    }
    public String getType() {
        return type;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }

    public String[] getNames() {
        String[] names = new String[modArr.size()];
        int i = 0;
        for (Map.Entry<String, Double> name : modArr.entrySet()) {
            names[i] = name.getKey();
            i++;
        }
        return names;
    }
    public double[] getPrices() {
        double[] prices = new double[modArr.size()];
        int i = 0;
        for (Map.Entry<String, Double> price : modArr.entrySet()) {
            prices[i] = price.getValue();
            i++;
        }
        return prices;
    }
    public void setName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException {
        if (modArr.containsKey(newName)){
            throw new DuplicateModelNameException();
        } 
        if (!modArr.containsKey(oldName)){
            throw new NoSuchModelNameException();
        } 
        modArr.put(newName, modArr.get(oldName));
        modArr.remove(oldName);
    }

    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (price <= 0){
            throw new ModelPriceOutOfBoundException();
        }
        if (modArr.containsKey(name)){
            throw new DuplicateModelNameException();
        }
        modArr.put(name, price);
    }

    public void delModel(String name) throws NoSuchModelNameException {
        if (!modArr.containsKey(name)){
            throw new NoSuchModelNameException();
        }
        modArr.remove(name);
    }

    public double getPriceByName(String name) throws NoSuchModelNameException {
        if (!modArr.containsKey(name)){
            throw new NoSuchModelNameException();
        }
        return modArr.get(name);
    }
    public void setPriceByName(String name, double price) throws NoSuchModelNameException {
        if(price <= 0){
            throw new ModelPriceOutOfBoundException();
        }
        if(!modArr.containsKey(name)){
            throw new NoSuchModelNameException();
        } 
        modArr.put(name,price);
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
        for (Map.Entry<String,Double> entry : modArr.entrySet()){
            str.append(entry.getKey()).append(" , ");
            str.append(entry.getValue()).append(" | ");
        }
        return new String(str);
    }
}
