package exceptionsPackage;

public class ModelPriceOutOfBoundException extends RuntimeException{
    public ModelPriceOutOfBoundException(){
        super("Некорректное число");
    }
}

