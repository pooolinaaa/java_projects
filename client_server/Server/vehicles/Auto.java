package vehicles;
import java.io.Serializable;
import java.util.Arrays;

import Interface.Transport;
import exceptionsPackage.DuplicateModelNameException;
import exceptionsPackage.ModelPriceOutOfBoundException;
import exceptionsPackage.NoSuchModelNameException;

public class Auto implements Transport{
    private static final long serialVersionUID = 1L;
    private String brand;
    private String type = "Auto";
    private Model[] modArr;

    public String getType(){
        return type;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String value) {
        brand = value;
    }
    public Auto(String brand){
        this.brand = brand;
        modArr = new Model[0];
    }
    public Auto(String brand, int size) {
        this.brand = brand;
        this.modArr = new Model[size];
        for (int i=0; i < size; i++) {
            modArr[i] = new Model("Auto"+i,  (int)((i+4)*100*Math.random()));
        }
    }
    public int modelSize(){
       
        return modArr.length;
    }
    private class Model implements Serializable, Cloneable{
        private String name; 
        private double price; 
        Model(String name, double price) {
            this.name = name;
            this.price = price;
        }
        
        @Override
        public Object clone(){
            Model model ;
            try{
                model = (Model)super.clone();
            } catch(CloneNotSupportedException exception){
                return null;
            }
            return model;
        }
    }
    public void setName(String source, String value) throws DuplicateModelNameException, NoSuchModelNameException{
        if (!inArray(source)) {
            throw new NoSuchModelNameException();
        }
        if (inArray(value)) {
            throw new DuplicateModelNameException();
        }
        for (int i=0; i < modArr.length; i++) {
            if (modArr[i].name.equals(source)) {
                modArr[i].name = value;
            }
        } 
    }
    private boolean inArray(String name) {
        boolean output = false;
        for (int i=0; i<modArr.length; i++) {
            if (modArr[i].name.equals(name)) {
                output = true;
            }
        }
        return output;
    }
    public String[] getNames() {
        String[] output = new String[modArr.length];
        for (int i=0; i < modArr.length; i++) {
            output[i] = modArr[i].name;
        }   
        return output;
    }
    public double getPriceByName(String name) throws NoSuchModelNameException{
        double output = 0;
        for (int i=0; i < modArr.length; i++) {
            if (modArr[i].name.equals(name)) {
                output = modArr[i].price;
                break;
            }
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
        Boolean flag = false;
        for (int i=0; i < modArr.length; i++) {
            if (modArr[i].name.equals(name)) { 
                modArr[i].price = value;
                flag = true;
                break;
            }
        }
        if(flag == false){
            throw new NoSuchModelNameException();
        }
    }
    public double[] getPrices() {
        double[] output = new double[modArr.length];
        for (int i=0; i < modArr.length; i++) {
            output[i] = modArr[i].price;
        }
        return output;
    }
    public void addModel(String name, double price) throws DuplicateModelNameException{
        if(price <= 0){
            throw new ModelPriceOutOfBoundException();
        }
        for(int i = 0; i<modArr.length; i++){
            if(modArr[i].name.equals(name)){
                throw new DuplicateModelNameException();
            }
        }
        modArr = Arrays.copyOf(modArr, modArr.length+1);
        modArr[modArr.length-1] = new Model(name, price);
    }
    public void delModel(String name) throws NoSuchModelNameException{
        int len = modArr.length;
        Model[] upModels = Arrays.copyOf(modArr, modArr.length-1);
        for(int i = 0; i<modArr.length; i++){
            if(modArr[i].name.equals(name)){
                System.arraycopy(modArr,0,upModels,0,i);
                System.arraycopy(modArr,i+1, upModels,i, modArr.length-i-1);
                modArr = upModels;
            }
        }
        if (modArr.length == len){
            throw new NoSuchModelNameException();
        }
    }

    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();
        String type = getType();
        str.append(type).append(" ");
        String brand = getBrand();
        str.append(brand).append(" ");
        int amount = modelSize();
        str.append(amount).append(" ");
        for(int i = 0; i<amount; i++){
            str.append(getNames()[i]).append(" ").append(getPrices()[i]).append(" | ");
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
    public int hashCode(){
        int hashCode = brand != null ? brand.hashCode() : 0;
        hashCode = 43 * hashCode + (modArr != null ? Arrays.hashCode(modArr) : 0);
        return hashCode;
    }

    @Override
    public Object clone(){
        Auto auto = null;
        try{
            auto = (Auto) super.clone();
            auto.modArr = modArr.clone();
            for(int i = 0; i < modelSize(); i++){
                auto.modArr[i] = (Model) modArr[i].clone();
            }
            return auto;
        } catch(CloneNotSupportedException exception){
            return null;
        }
    }
    
}
