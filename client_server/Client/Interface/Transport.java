package Interface;

import java.io.Serializable;
import exceptionsPackage.DuplicateModelNameException;
import exceptionsPackage.NoSuchModelNameException;

public interface Transport extends Serializable, Cloneable{
    public String getBrand();
    public String getType();
    public void setBrand(String value);
    public void setName(String source, String value) throws NoSuchModelNameException,DuplicateModelNameException;
    public String[] getNames();
    public double getPriceByName(String name) throws NoSuchModelNameException;
    public void setPriceByName(String name, double value) throws NoSuchModelNameException;
    public double[] getPrices();
    public void addModel(String name, double price) throws DuplicateModelNameException;
    public void delModel(String name) throws NoSuchModelNameException;
    public int modelSize();
    public String toString(); 
}
