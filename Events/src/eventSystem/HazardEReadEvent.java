package eventSystem;

import java.util.EventObject;

public class HazardEReadEvent extends EventObject {
    private String cargo;
    public HazardEReadEvent(Object source, String cargo) {
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
