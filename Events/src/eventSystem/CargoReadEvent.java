package eventSystem;

import java.util.EventObject;

public class CargoReadEvent extends EventObject {
    private String cargo;
    public CargoReadEvent(Object source, String cargo) {
        super(source);
        this.cargo = cargo;
    }
    public String getCargoDetails(){
        return cargo;
    }
    
    public String toString(){
        return "Cargo read method is used.";
    }

}
